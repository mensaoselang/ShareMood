package com.example.sharemood.diary.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sharemood.R;
import com.example.sharemood.base.BaseActivity;
import com.example.sharemood.bean.MoodTypeBean;
import com.example.sharemood.diary.adapter.ShareChooseAdapter;
import com.example.sharemood.diary.bean.DiarySQLBean;
import com.example.sharemood.diary.presenter.DiaryDetailPresenter;
import com.example.sharemood.utils.DialogUtils.DialogUtils;
import com.example.sharemood.view.BackLinearLayout;
import com.example.sharemood.view.NoScrollGridView;
import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.callback.ConfigButton;
import com.mylhyl.circledialog.params.ButtonParams;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.router.Router;

public class DiaryDetailActivity extends BaseActivity<DiaryDetailPresenter> implements View.OnClickListener {
    @BindView(R.id.et_content)
    TextView tvContent;
    @BindView(R.id.iv_weather)
    ImageView ivWeather;
    @BindView(R.id.tv_weather)
    TextView tvWeather;
    @BindView(R.id.tv_temperature)
    TextView tvTemperature;
    @BindView(R.id.iv_location)
    ImageView ivLocation;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.tv_heart_manth)
    TextView tvHeartManth;
    @BindView(R.id.tv_title_heart)
    TextView tvTitleHeart;
    @BindView(R.id.tv_heart_color)
    TextView tvHeartColor;
    @BindView(R.id.iv_heart_color)
    ImageView ivHeartColor;
    @BindView(R.id.tv_date_number)
    TextView tvDateNumber;
    @BindView(R.id.tv_year_month)
    TextView tvYearMonth;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.iv_time)
    ImageView ivTime;
    @BindView(R.id.imageView1)
    ImageView imageView1;
    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.imageView3)
    ImageView imageView3;
    @BindView(R.id.constrainlayout)
    ConstraintLayout constrainlayout;
    @BindView(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;
    @BindView(R.id.ll_toolbar_left)
    BackLinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_date)
    TextView tvToolbarDate;
    @BindView(R.id.tv_toolbar_time)
    TextView tvToolbarTime;
    @BindView(R.id.tv_toolbar_delete)
    ImageView tvToolbarDelete;
    @BindView(R.id.ll_toolbar_right)
    LinearLayout llToolbarRight;
    @BindView(R.id.tv_toolbar_share)
    ImageView tvToolbarShare;
    private long id;
    public static final int DIARYDETAIL = 1;
    private ShareChooseAdapter shareChooseAdapter;
    private PopupWindow mPopWindow;
    DiarySQLBean diarySQLBean;
    private int typeId;

    DialogUtils dialogUtils=new DialogUtils(this);
    @Override
    public void initData(Bundle savedInstanceState) {
        tvToolbarDelete.setVisibility(View.VISIBLE);
        tvToolbarShare.setVisibility(View.VISIBLE);
        id = getIntent().getLongExtra("id", -1);
        initView();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_diary_detail;
    }

    @Override
    public DiaryDetailPresenter newP() {
        return new DiaryDetailPresenter();
    }

    public static void toDiaryDetailActivity(Activity activity) {
        Router.newIntent(activity).to(DiaryDetailActivity.class).launch();
    }

    public void initView() {
        diarySQLBean = new DiarySQLBean();
        diarySQLBean = LitePal.find(DiarySQLBean.class, id);
        tvYearMonth.setText(diarySQLBean.getYear() + "年" + diarySQLBean.getMonth() + "月" + "/" + diarySQLBean.getDayOfWeek());
        tvTime.setText(diarySQLBean.getTime());
        tvDateNumber.setText(diarySQLBean.getDateNumber());
        tvContent.setText(diarySQLBean.getContent());
        tvWeather.setText(diarySQLBean.getWeather());
        tvTemperature.setText(diarySQLBean.getTemperature());
        tvLocation.setText(diarySQLBean.getLocation());
        tvHeartManth.setText(diarySQLBean.getMoodIndex());
        String defaultColor = diarySQLBean.getMoodColor();
        String imageOnePath = diarySQLBean.getImagePathList().get(0);
        String imageTwoPath = diarySQLBean.getImagePathList().get(1);
        String imageThreePath = diarySQLBean.getImagePathList().get(2);
        ivHeartColor.setBackgroundColor(Color.parseColor(defaultColor));
        if (!"".equals(imageOnePath)) {
            Glide.with(this).load(imageOnePath).into(imageView1);
            if (!"".equals(imageTwoPath)) {
                Glide.with(this).load(imageTwoPath).into(imageView2);
                if (!"".equals(imageThreePath)) {
                    Glide.with(this).load(imageThreePath).into(imageView3);
                }
            }
        }
    }

    @OnClick({R.id.imageView1, R.id.imageView2, R.id.imageView3, R.id.floatingActionButton, R.id.ll_toolbar_left, R.id.tv_toolbar_delete,R.id.tv_toolbar_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageView1:
                break;
            case R.id.imageView2:
                break;
            case R.id.imageView3:
                break;
            case R.id.floatingActionButton:
                startActivityForResult(new Intent(this, DiaryWriteActivity.class)
                        .putExtra("type", DIARYDETAIL).putExtra("id", id), 1);
                break;
            case R.id.ll_toolbar_left:
                finish();
                break;
            case R.id.tv_toolbar_delete:
                showDialog();
                break;
            case R.id.tv_toolbar_share:
                showPopupWindow();
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) { //resultCode为回传的标记
            case 4:
                initView();
                DiaryDetailActivity.this.setResult(4);
                break;
            default:
                break;
        }
    }
    //选择弹窗
    GridView noScrollGridView;
    private void showPopupWindow() {
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高
        //设置contentView
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_share, null);
        mPopWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setContentView(contentView);
        mPopWindow.setWidth(d.getWidth());
        mPopWindow.setAnimationStyle(R.style.mypopwindow_anim_center_style);
        //设置各个控件的点击响应
         noScrollGridView=(GridView) contentView.findViewById(R.id.share_gridview);
         TextView tvSure=(TextView)contentView.findViewById(R.id.tv_sure);
         TextView tvCancel=(TextView)contentView.findViewById(R.id.tv_cancel);
         tvSure.setOnClickListener(this);
         tvCancel.setOnClickListener(this);
        getP().getTitleData();
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
    public void setAdapter(final List<MoodTypeBean> list){
        shareChooseAdapter=new ShareChooseAdapter(context,list);
        noScrollGridView.setAdapter(shareChooseAdapter);
        noScrollGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                shareChooseAdapter.clearSelection(position);
                shareChooseAdapter.notifyDataSetChanged();
                typeId=list.get(position).getId();
            }
        });
        //显示PopupWindow
        View rootview = LayoutInflater.from(context).inflate(R.layout.activity_mine_data, null);
        mPopWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);
        setBackgroundAlpha(0.5f);
        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1f);
            }
        });
    }
    //删除Dialog
    public void showDialog() {
        ConfigButton sure = new ConfigButton() {
            @Override
            public void onConfig(ButtonParams params) {
                params.textColor = 0xFFFC3030;
            }
        };
        CircleDialog.Builder builder = new CircleDialog.Builder(this);
        builder.setTitle("日记操作")
                .setWidth(0.7f)
                .setTitleColor(Color.parseColor("#2db4ff"))//标题字体颜值 0x909090 or Color.parseColor("#909090")
                .setText("确认删除")
                .setTextColor(Color.parseColor("#2db4ff"))
                .setPositive("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LitePal.delete(DiarySQLBean.class, id);//删除指定Id的日记
                        getP().deleteChartMood(id);
                        DiaryDetailActivity.this.setResult(3);
                        finish();
                    }
                }).configPositive(sure)//配置确定按钮更多的属性
                .setNegative("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .show();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_cancel:
                mPopWindow.dismiss();
                break;
            case R.id.tv_sure:
                dialogUtils.showLoadingWithLabel("分享上传中...");
               getP().saveShare(diarySQLBean,typeId);
                mPopWindow.dismiss();
                break;
        }
    }
    public void dialogUtilDismiss(){
        dialogUtils.dismissLoading();
    }
    @Override
    protected void onDestroy() {
        dialogUtils.dismissLoading();
        super.onDestroy();
    }
}
