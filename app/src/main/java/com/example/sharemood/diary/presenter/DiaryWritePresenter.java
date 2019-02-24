package com.example.sharemood.diary.presenter;

import android.net.Uri;
import android.os.Environment;

import com.example.sharemood.base.BasePresenter;
import com.example.sharemood.chart.bean.ChartMoodSqlBean;
import com.example.sharemood.diary.activity.DiaryDetailActivity;
import com.example.sharemood.diary.activity.DiaryWriteActivity;
import com.example.sharemood.diary.bean.DiarySQLBean;
import com.example.sharemood.ui.index.bean.ChartMoodBean;
import com.example.sharemood.ui.login.Bean.MyUserBean;
import com.example.sharemood.utils.ToastUtil;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.TakePhotoOptions;

import org.litepal.LitePal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by acer on 2018/11/27.
 */

public class DiaryWritePresenter extends BasePresenter<DiaryWriteActivity> {
    //打开相册获取图片
    public void getPhoto(int whitchImage){
//        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
//        if (!file.getParentFile().exists()) {
//            file.getParentFile().mkdirs();
//        }
//        Uri imageUri = Uri.fromFile(file);
//        if (whitchImage==1){
//            getV().getTakePhoto().onPickMultiple(3);//最多能选多少张
//        }else if (whitchImage==2){
//            getV().getTakePhoto().onPickMultiple(2);//最多能选多少张
//        }else if (whitchImage==3){
//            getV().getTakePhoto().onPickMultiple(1);//最多能选多少张
//        }
//        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
//        builder.setWithOwnGallery(true);
//        builder.setCorrectImage(true);
//        getV().getTakePhoto().setTakePhotoOptions(builder.create());
        CompressConfig config;//设置压缩属性
        config = new CompressConfig.Builder().setMaxSize(307200).create();
        getV().getTakePhoto().onEnableCompress(config,true);//压缩
        getV().getTakePhoto().onPickFromGallery();//从相册选择图片
    }


    private DiarySQLBean diarySQLBean;
    private ChartMoodSqlBean chartMoodSqlBean;//默认id1，对应周一,id2对应周二
    public void saveDiary(int type,long id,String imageOnePath,String imageTwoPath,String imageThreePath,String userPhone,String nickName,
                          String content,String weather,String temperature,String location,String moodColor,
                          String moodIndex,String time, String dayOfWeek,String dateNumber,String month,String year){
        if (type== DiaryDetailActivity.DIARYDETAIL) {//
            //更新编辑日记
            List<String> list = new ArrayList<>();
            list.add(imageOnePath);
            list.add(imageTwoPath);
            list.add(imageThreePath);
            diarySQLBean = LitePal.find(DiarySQLBean.class, id);
            diarySQLBean.setImagePathList(list);
        }else {
            diarySQLBean=new DiarySQLBean();
            diarySQLBean.getImagePathList().add(imageOnePath);
            diarySQLBean.getImagePathList().add(imageTwoPath);
            diarySQLBean.getImagePathList().add(imageThreePath);
        }
        diarySQLBean.setUserPhone(userPhone);
        diarySQLBean.setNickName(nickName);
        diarySQLBean.setContent(content);
        diarySQLBean.setWeather(weather);
        diarySQLBean.setTemperature(temperature);
        diarySQLBean.setLocation(location);
        diarySQLBean.setMoodColor(moodColor);
        diarySQLBean.setMoodIndex(moodIndex);
        diarySQLBean.setTime(time);
        diarySQLBean.setDayOfWeek(dayOfWeek);
        diarySQLBean.setDateNumber(dateNumber);
        diarySQLBean.setMonth(month);
        diarySQLBean.setYear(year);
        if (type==DiaryDetailActivity.DIARYDETAIL){
            if (diarySQLBean.save()) {
                changeChartMood(diarySQLBean.getMoodIndex(),diarySQLBean.getId());//编辑日记也把心情指数表一起编辑修改
                saveOrUpdateChartMoodSQL(diarySQLBean.getDayOfWeek(),Integer.valueOf(diarySQLBean.getMoodIndex()));//修改本地心情指数保存
              getV().updateDiarySucceed();
            } else {
                ToastUtil.showShort("更新编辑失败");
            }
        }else {
            if (diarySQLBean.save()){
                //如果本地日记保存成功，就保存心情指数Bean
                saveChartMood(moodIndex,diarySQLBean.getId());
                saveOrUpdateChartMoodSQL(diarySQLBean.getDayOfWeek(),Integer.valueOf(diarySQLBean.getMoodIndex()));//修改本地心情指数保存
                getV().saveDiarySucceed();
            }else {
                ToastUtil.showShort("保存失败");
            }
        }
    }

    //保存心情指数
    public void saveChartMood(String index,long diarySqlId){
        if (!"未知".equals(index)){
            ChartMoodBean chartMoodBean=new ChartMoodBean();
            //添加关联，用户关联心情指数;
            chartMoodBean.setMyUserBean(BmobUser.getCurrentUser(MyUserBean.class));
            chartMoodBean.setIndex(Integer.valueOf(index));
            chartMoodBean.setDiarySqlId(diarySqlId);
            chartMoodBean.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e==null){
                    }else {
                        ToastUtil.showShort("心情指数表保存失败");
                    }
                }
            });
        }
    }
    //修改心情指数
    public void changeChartMood(final String index, long diarySqlId){
        if (!"未知".equals(index)){
            BmobQuery<ChartMoodBean> chartQuery = new BmobQuery<>();
            chartQuery.addWhereEqualTo("diarySqlId", diarySqlId);
            chartQuery.findObjects(new FindListener<ChartMoodBean>() {
                @Override
                public void done(List<ChartMoodBean> list, BmobException e) {
                    if (e==null){
                        if (list.size()>0) {
                            list.get(0).setIndex(Integer.valueOf(index));
                            list.get(0).update(list.get(0).getObjectId(), new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                    } else {
                                        ToastUtil.showShort("心情指数表修改失败" + e.getMessage());
                                    }
                                }
                            });
                        }
                    }
                }
            });
        }
    }

    //保存或更新SQL的心情记录表
    public void saveOrUpdateChartMoodSQL(String week,int index){
        ChartMoodSqlBean chartMoodSqlBean=new ChartMoodSqlBean();
        chartMoodSqlBean.setMoodIndex(index);
        chartMoodSqlBean.updateAll("dayOfWeek=?",week);
    }
}
