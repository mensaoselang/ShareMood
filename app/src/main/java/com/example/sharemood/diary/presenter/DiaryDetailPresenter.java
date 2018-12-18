package com.example.sharemood.diary.presenter;

import android.content.Context;

import com.example.sharemood.base.BasePresenter;
import com.example.sharemood.bean.DiaryShareBean;
import com.example.sharemood.bean.MoodTypeBean;
import com.example.sharemood.chart.bean.PieChartMoodBean;
import com.example.sharemood.diary.activity.DiaryDetailActivity;
import com.example.sharemood.diary.bean.DiarySQLBean;
import com.example.sharemood.ui.index.bean.ChartMoodBean;
import com.example.sharemood.ui.login.Bean.MyUserBean;
import com.example.sharemood.utils.DialogUtils.DialogUtils;
import com.example.sharemood.utils.ToastUtil;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadBatchListener;

/**
 * Created by acer on 2018/11/27.
 */

public class DiaryDetailPresenter extends BasePresenter<DiaryDetailActivity> {
    public void getTitleData(){
        BmobQuery<MoodTypeBean> typeQuery=new BmobQuery<>();
        typeQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);   // 先从缓存获取数据，如果没有，再从网络获取。
        typeQuery.findObjects(new FindListener<MoodTypeBean>() {
            @Override
            public void done(List<MoodTypeBean> list, BmobException e) {
                getV().setAdapter(list);
            }
        });
    }
    DiaryShareBean diaryShareBean;
    public void saveShare(DiarySQLBean diarySQLBean,int typeId){
        diaryShareBean=new DiaryShareBean();
        diaryShareBean.setSqlId(diarySQLBean.getId());
        diaryShareBean.setTypeId(typeId);
        diaryShareBean.setUserPhone(diarySQLBean.getUserPhone());
        diaryShareBean.setNickName(diarySQLBean.getNickName());
        diaryShareBean.setContent(diarySQLBean.getContent());
        diaryShareBean.setWeather(diarySQLBean.getWeather());
        diaryShareBean.setTemperature(diarySQLBean.getTemperature());
        diaryShareBean.setLocation(diarySQLBean.getLocation());
        diaryShareBean.setMoodColor(diarySQLBean.getMoodColor());
        diaryShareBean.setMoodIndex(diarySQLBean.getMoodIndex());
        diaryShareBean.setTime(diarySQLBean.getTime());
        diaryShareBean.setDayOfWeek(diarySQLBean.getDayOfWeek());
        diaryShareBean.setDateNumber(diarySQLBean.getDateNumber());
        diaryShareBean.setMonth(diarySQLBean.getMonth());
        diaryShareBean.setYear(diarySQLBean.getYear());
        //添加关联，用户关联帖子;
        diaryShareBean.setMyUserBean(BmobUser.getCurrentUser(MyUserBean.class));
        String imagePathOne=diarySQLBean.getImagePathList().get(0);
        String imagePathTwo=diarySQLBean.getImagePathList().get(1);
        String imagePathThree=diarySQLBean.getImagePathList().get(2);
          //先上传文件
        //详细示例可查看BmobExample工程中BmobFileActivity类
//        String filePath_mp3 = "/mnt/sdcard/testbmob/test1.png";
//        String filePath_lrc = "/mnt/sdcard/testbmob/test2.png";
        if (!"".equals(imagePathOne)){
            if (!"".equals(imagePathTwo)){
                   if (!"".equals(imagePathThree)){
                       String[] filePaths = new String[3];
                       filePaths[0] = imagePathOne;
                       filePaths[1] = imagePathTwo;
                       filePaths[2] = imagePathThree;
                       upload(filePaths,typeId);
                }else {
                       String[] filePaths = new String[2];
                       filePaths[0] = imagePathOne;
                       filePaths[1] = imagePathTwo;
                       upload(filePaths,typeId);
                }
            }else {
                String[] filePaths = new String[1];
                filePaths[0] = imagePathOne;
                upload(filePaths,typeId);
            }
        }else {
            save(typeId);
        }
    }
    //上传
    public void upload(final String[] filePaths, final int typeId){
        BmobFile.uploadBatch(filePaths, new UploadBatchListener() {

            @Override
            public void onSuccess(List<BmobFile> files,List<String> urls) {
                //1、files-上传完成后的BmobFile集合，是为了方便大家对其上传后的数据进行操作，例如你可以将该文件保存到表中
                //2、urls-上传文件的完整url地址
                int size1=urls.size();
                int size2=filePaths.length;
                if(urls.size()==filePaths.length){//如果数量相等，则代表文件全部上传完成
                    //do something
                    diaryShareBean.setImageFiles(files);
                    save(typeId);
                }
            }

            @Override
            public void onError(int statuscode, String errormsg) {
                getV().dialogUtilDismiss();//关闭菊花条
                ToastUtil.showShort("错误码"+statuscode +",错误描述："+errormsg);
            }

            @Override
            public void onProgress(int curIndex, int curPercent, int total,int totalPercent) {
                //1、curIndex--表示当前第几个文件正在上传
                //2、curPercent--表示当前上传文件的进度值（百分比）
                //3、total--表示总的上传文件数
                //4、totalPercent--表示总的上传进度（百分比）
            }
        });
    }

    public void save(final int typeId){
        diaryShareBean.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    savePieChart(typeId);
                 //   dialogUtils.dismissLoading();
                    getV().dialogUtilDismiss();//关闭菊花条
                    ToastUtil.showShort("分享成功");
                }else{
                    getV().dialogUtilDismiss();//关闭菊花条
                    ToastUtil.showShort("分享失败"+e.getMessage());
                }
            }
        });
    }
    //删除对应的心情指数表
    public void deleteChartMood(long objectId){
        BmobQuery<ChartMoodBean> chartQuery = new BmobQuery<>();
        chartQuery.addWhereEqualTo("diarySqlId", objectId);
        chartQuery.findObjects(new FindListener<ChartMoodBean>() {
            @Override
            public void done(List<ChartMoodBean> list, BmobException e) {
                if (e==null){
                    ChartMoodBean chartMoodBean=new ChartMoodBean();
                    chartMoodBean.setObjectId(list.get(0).getObjectId());
                    chartMoodBean.delete(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e==null){
                            }else {
                                ToastUtil.showShort("删除心情指数表失败"+e.getMessage());
                            }
                        }
                    });
                }
            }
        });
    }

   //更新保存分享的类型作为PieChart的心情分享数据
    public void savePieChart(int typeId){
        BmobQuery<PieChartMoodBean> query = new BmobQuery<>();
        query.addWhereEqualTo("myUserBean", BmobUser.getCurrentUser(MyUserBean.class));
//        query.order("-updatedAt");
        query.addWhereEqualTo("typeId",typeId );
        query.findObjects(new FindListener<PieChartMoodBean>() {
            @Override
            public void done(List<PieChartMoodBean> object, BmobException e) {
                if (e == null) {
                    //更新Person表里面id为6b6c11c537的数据
                    PieChartMoodBean pieChartMoodBean = new PieChartMoodBean();
                    pieChartMoodBean.setSum(object.get(0).getSum()+1);//每分享一次就加一
                    pieChartMoodBean.update(object.get(0).getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){

                            }else{
                                ToastUtil.showShort("心情饼图更新失败");
                            }
                        }
                    });
                } else {
                    ToastUtil.showShort("饼图失败" + e.getMessage());
                }
            }
        });


    }

}
