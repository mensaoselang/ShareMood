package com.example.sharemood.mine.activity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sharemood.R;
import com.example.sharemood.base.BaseActivity;
import com.example.sharemood.mine.presenter.MineDataPresenter;
import com.example.sharemood.ui.login.Bean.MyUserBean;
import com.example.sharemood.ui.login.activity.LoginActivity;
import com.example.sharemood.utils.SharePreferenceUtil;
import com.example.sharemood.utils.ToastUtil;
import com.example.sharemood.view.BackLinearLayout;
import com.jph.takephoto.model.TResult;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MineDataActivity extends BaseActivity<MineDataPresenter> implements View.OnClickListener {
    @BindView(R.id.ll_toolbar_left)
    BackLinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.ll_toolbar_right)
    LinearLayout llToolbarRight;
    @BindView(R.id.iv_heart_image)
    CircleImageView ivHeartImage;
    @BindView(R.id.et_nickname)
    EditText etNickname;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.et_quotations)
    EditText etQuotations;
    @BindView(R.id.ll_gray_line)
    LinearLayout llGrayLine;
    @BindView(R.id.tv_toolbar_save)
    TextView tvToolbarSave;
    @BindView(R.id.tv_login_out)
    TextView tvLoginOut;
    private PopupWindow mPopWindow;
    private int choose = 0;
    private String imagePath = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        tvToolbarTitle.setText("个人资料");
        tvToolbarSave.setVisibility(View.VISIBLE);
        MyUserBean myUserBean=(MyUserBean) getIntent().getSerializableExtra("myUserBean") ;
        initView(myUserBean);
      //  getP().getMyData();
    //    getP().queryPostAuthor();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_data;
    }

    public void initView(MyUserBean myUserBean) {
        if (myUserBean!=null){
            etNickname.setText(myUserBean.getNickName());
            tvSex.setText(myUserBean.getSex());
            etQuotations.setText(myUserBean.getQuotations());
            Glide.with(this).load(myUserBean.getImagePath()).into(ivHeartImage);
        }
    }

    @Override
    public MineDataPresenter newP() {
        return new MineDataPresenter();
    }

    private void showPopupWindow(int choose) {
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高
        //设置contentView
        View contentView = LayoutInflater.from(context).inflate(R.layout.show_window, null);
        mPopWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setContentView(contentView);
        mPopWindow.setWidth(d.getWidth());
        mPopWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        //设置各个控件的点击响应
        LinearLayout tv1 = (LinearLayout) contentView.findViewById(R.id.photo_roll1);
        LinearLayout tv2 = (LinearLayout) contentView.findViewById(R.id.photo_roll2);
        LinearLayout tv3 = (LinearLayout) contentView.findViewById(R.id.photo_roll3);
        TextView textView1 = (TextView) contentView.findViewById(R.id.textView1);
        TextView textView2 = (TextView) contentView.findViewById(R.id.textView2);
        tv3.setOnClickListener(this);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        if (choose == 2) {
            textView1.setText("男");
            textView2.setText("女");
        }
        //显示PopupWindow
        View rootview = LayoutInflater.from(context).inflate(R.layout.activity_mine_data, null);
        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
        setBackgroundAlpha(0.5f);
        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1f);
            }
        });
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha 屏幕透明度0.0-1.0 1表示完全不透明
     */
    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = (context).getWindow().getAttributes();
        lp.alpha = bgAlpha;
        (context).getWindow().setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.photo_roll1:
                if (choose == 2) {
                    tvSex.setText("男");
                } else {
                    //takePicture(context);
                }
                mPopWindow.dismiss();
                break;
            case R.id.photo_roll2:
                if (choose == 2) {
                    tvSex.setText("女");
                } else {
                    // fromCamera(context);
                }
                mPopWindow.dismiss();
                break;
            case R.id.photo_roll3:
                mPopWindow.dismiss();
                break;
        }
    }

    @OnClick({R.id.ll_toolbar_left, R.id.iv_heart_image, R.id.tv_sex, R.id.tv_toolbar_save,R.id.tv_login_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
            case R.id.iv_heart_image:
                getP().getPhoto(1);
                break;
            case R.id.tv_sex:
                choose = 2;
                showPopupWindow(2);
                break;
            case R.id.tv_toolbar_save:
                String nickName = etNickname.getText().toString();
                String sex = tvSex.getText().toString();
                String say = etQuotations.getText().toString();
                getP().saveData(imagePath, nickName, sex, say);
                break;
            case R.id.tv_login_out:
                SharePreferenceUtil.setUserInfo("","","","","",false);
                LoginActivity.toLoginActivity(context);
                finish();
                break;
        }
    }

    public void save(String imagePath) {
        //File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        File file = new File(Environment.getExternalStorageDirectory() + "ShareMood" + "headportrait.jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        Uri imageUri = Uri.fromFile(file);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        imagePath = result.getImage().getCompressPath();
        Glide.with(context).load(result.getImage().getCompressPath()).into(ivHeartImage);
    }

    @Override
    public void takeFail(TResult result, String msg) {
        Toast.makeText(context, "失败" + result + "消息" + msg, Toast.LENGTH_LONG).show();
        super.takeFail(result, msg);
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    //修改用户信息保存成功
    public void saveSuccess(){
        MineDataActivity.this.setResult(2);
        ToastUtil.showShort("保存成功");
    }

}
