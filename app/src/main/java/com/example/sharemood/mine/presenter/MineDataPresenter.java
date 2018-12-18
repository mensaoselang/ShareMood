package com.example.sharemood.mine.presenter;

import android.net.Uri;
import android.support.design.widget.Snackbar;

import com.example.sharemood.base.BasePresenter;
import com.example.sharemood.bean.DiaryShareBean;
import com.example.sharemood.mine.activity.MineDataActivity;
import com.example.sharemood.ui.login.Bean.MyUserBean;
import com.example.sharemood.utils.SharePreferenceUtil;
import com.example.sharemood.utils.ToastUtil;
import com.jph.takephoto.compress.CompressConfig;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by acer on 2018/12/5.
 */

public class MineDataPresenter extends BasePresenter<MineDataActivity> {
//    public void getMyData(){
//        BmobQuery<MyUserBean> bmobQuery = new BmobQuery<MyUserBean>();
//        bmobQuery.getObject(SharePreferenceUtil.getObjectId(), new QueryListener<MyUserBean>() {
//            @Override
//            public void done(MyUserBean myUserBean, BmobException e) {
//                if(e==null){
//                    getV().initView(myUserBean);
//                }else{
//                    ToastUtil.showShort("获取个人信息失败:"+e.getMessage());
//                }
//            }
//        });
//    }
    //打开相册获取图片
    public void getPhoto(int type){
        CompressConfig config;//设置压缩属性
        config = new CompressConfig.Builder().setMaxSize(204800).create();
        getV().getTakePhoto().onEnableCompress(config,true);//压缩
        getV().getTakePhoto().onPickFromGallery();
    }
    //保存个人资料
    private MyUserBean userBean=new MyUserBean();;
    String oldImagePath;
    public void saveData(String imagePath, final String nickName, final String sex, final String say){
       //查询旧的头像地址便于删除旧头像文件
        BmobQuery<MyUserBean> bmobQuery = new BmobQuery<MyUserBean>();
        bmobQuery.getObject(SharePreferenceUtil.getObjectId(), new QueryListener<MyUserBean>() {
            @Override
            public void done(MyUserBean myUserBean, BmobException e) {
                if(e==null){
                    oldImagePath=myUserBean.getImagePath();
                }else{
                    ToastUtil.showShort("查询旧头像地址失败"+e.getMessage());
                }
            }
        });

//        之所以会出现”数据上传失败107 invalid file: filename empty.”
//        是因为原因是，在Bmob中，必须先把文件上传到Bmob的后台文件管理中才能再添加到后台的数据库的表中。否则会报错
        if (!"".equals(imagePath)){
            final BmobFile bmobFile = new BmobFile(new File(imagePath));
            bmobFile.uploadblock(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if(e==null){
                        //bmobFile.getFileUrl()--返回的上传文件的完整地址
                        //上传文件成功后更新头像
//                        userBean.setImageFile(bmobFile);
                        userBean.setImagePath(bmobFile.getFileUrl());
                        //更新用户信息
                        userBean.setNickName(nickName);
                        userBean.setSex(sex);
                        userBean.setQuotations(say);
                        userBean.update(SharePreferenceUtil.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    getV().saveSuccess();
//                    //删除文件，删除旧头像文件
                   if (oldImagePath!=null&&!"".equals(oldImagePath)){
                    BmobFile file = new BmobFile();
                    file.setUrl(oldImagePath);
                    file.delete(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                ToastUtil.showShort("旧头像删除成功");
                            }else{
                                ToastUtil.showShort("旧头像删除失败"+e.getMessage());
                            }
                        }
                    });                }
                                }else{
                                    ToastUtil.showShort("保存失败"+e.getMessage());
                                }
                            }
                        });
                    }else{
                        ToastUtil.showShort("上传头像失败"+e.getMessage());
                    }
                }
                @Override
                public void onProgress(Integer value) {
                    // 返回的上传进度（百分比）
                }
            });
        }else {
            //更新用户信息
            userBean.setNickName(nickName);
            userBean.setSex(sex);
            userBean.setQuotations(say);
            userBean.update(SharePreferenceUtil.getObjectId(), new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if(e==null){
                        getV().saveSuccess();
                    }else{
                        ToastUtil.showShort("保存失败"+e.getMessage());
                    }
                }
            });
        }
    }

//    /**
//     * 查询一对一关联，查询当前用户发表的所有帖子
//     */
//    public void queryPostAuthor() {
//        if (BmobUser.isLogin()) {
//            BmobQuery<DiaryShareBean> query = new BmobQuery<>();
//            query.addWhereEqualTo("myUserBean", BmobUser.getCurrentUser(MyUserBean.class));
//            query.order("-updatedAt");
//            //包含作者信息
//            query.include("myUserBean");
//            query.findObjects(new FindListener<DiaryShareBean>() {
//                @Override
//                public void done(List<DiaryShareBean> object, BmobException e) {
//                    if (e == null) {
//                    } else {
//                        ToastUtil.showShort("查询失败" + e.getMessage());
//                    }
//                }
//
//            });
//        } else {
//            ToastUtil.showShort("请先登录");
//            //  Snackbar.make(mFabAddPost, , Snackbar.LENGTH_LONG).show();
//        }
//    }
}
