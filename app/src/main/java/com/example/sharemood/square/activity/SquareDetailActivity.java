package com.example.sharemood.square.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sharemood.R;
import com.example.sharemood.base.BaseActivity;
import com.example.sharemood.bean.CommentBean;
import com.example.sharemood.bean.DiaryShareBean;
import com.example.sharemood.square.adapter.MainCommentAdapter;
import com.example.sharemood.square.presenter.SquareDetailPresenter;
import com.example.sharemood.ui.login.Bean.MyUserBean;
import com.example.sharemood.utils.ToastUtil;
import com.example.sharemood.view.BackLinearLayout;
import com.example.sharemood.view.SuperExpandableListView;
import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.callback.ConfigButton;
import com.mylhyl.circledialog.params.ButtonParams;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.droidlover.xdroidmvp.router.Router;
import de.hdodenhof.circleimageview.CircleImageView;

public class SquareDetailActivity extends BaseActivity<SquareDetailPresenter> implements View.OnClickListener {
    @BindView(R.id.ll_toolbar_left)
    BackLinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.ll_toolbar_right)
    LinearLayout llToolbarRight;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.iv_heart)
    CircleImageView ivHeart;
    @BindView(R.id.iv_heart_color)
    ImageView ivHeartColor;
    @BindView(R.id.tv_color_digital)
    TextView tvColorDigital;
    @BindView(R.id.tv_weather_wendu)
    TextView tvWeatherWendu;
    @BindView(R.id.tv_weather)
    TextView tvWeather;
    @BindView(R.id.iv_weather)
    ImageView ivWeather;
    @BindView(R.id.iv_head_portrait)
    CircleImageView ivHeadPortrait;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
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
    @BindView(R.id.tv_all_comment)
    TextView tvAllComment;
    @BindView(R.id.tv_hint_end)
    TextView tvHintEnd;
    @BindView(R.id.constrainlayout)
    ConstraintLayout constrainlayout;
    @BindView(R.id.ll_gray_line)
    LinearLayout llGrayLine;
    @BindView(R.id.et_input_comment)
    EditText etInputComment;
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    DiaryShareBean diaryShareBean;
    @BindView(R.id.tv_heart_temperature)
    TextView tvHeartTemperature;
    @BindView(R.id.iv_focuson)
    ImageView ivFocuson;
    @BindView(R.id.tv_focuson_number)
    TextView tvFocusonNumber;
    @BindView(R.id.expandableListView)
    SuperExpandableListView expandableListView;
    private String imagePathOne;
    private String imagePathTwo;
    private String commentObjectId;
    private MyUserBean userBean;
    private boolean isUser = false;
    private MainCommentAdapter mainCommentAdapter;
    CommentBean comment;
    private boolean secondComment=false;
    private boolean isLike=false;
    @Override
    public void initData(Bundle savedInstanceState) {
        userBean = BmobUser.getCurrentUser(MyUserBean.class);
        diaryShareBean = (DiaryShareBean) getIntent().getSerializableExtra("DiaryShareBean");
        if (userBean.getObjectId().equals(diaryShareBean.getMyUserBean().getObjectId())) {
            llToolbarRight.setVisibility(View.VISIBLE);
            isUser = true;
        } else {
            llToolbarRight.setVisibility(View.INVISIBLE);
            isUser = false;
        }
        initView(diaryShareBean);
//        xrComment.setPullRefreshEnabled(false);
        getP().getComment(diaryShareBean.getObjectId());//一开始去查询该帖子的评论
        getP().whetherLike(diaryShareBean.getObjectId());
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_square_detail;
    }

    @Override
    public SquareDetailPresenter newP() {
        return new SquareDetailPresenter();
    }
    public void setTvFocusonNumber(int number){
        tvFocusonNumber.setText(number+"");
    }

    public void initView(DiaryShareBean diaryShareBean) {
        if (diaryShareBean != null) {
            tvDateNumber.setText(diaryShareBean.getDateNumber());
            tvYearMonth.setText(diaryShareBean.getYear() + "年" + diaryShareBean.getMonth() + "月" + "/" + diaryShareBean.getDayOfWeek());
            tvTime.setText(diaryShareBean.getTime());
            tvHeartTemperature.setText(diaryShareBean.getMoodIndex());
            tvWeather.setText(diaryShareBean.getWeather());
            tvContent.setText(diaryShareBean.getContent());
            tvWeatherWendu.setText(diaryShareBean.getTemperature());
            ivHeartColor.setBackgroundColor(Color.parseColor(diaryShareBean.getMoodColor()));
            if (diaryShareBean.getMyUserBean() != null) {
                if (diaryShareBean.getMyUserBean().getImagePath() != null) {
                    Glide.with(this).load(diaryShareBean.getMyUserBean().getImagePath()).into(ivHeadPortrait);
                }
                tvNickname.setText(diaryShareBean.getMyUserBean().getNickName());
            }
            if (diaryShareBean.getImageFiles() != null) {
                if (diaryShareBean.getImageFiles().size() == 1) {
                    imagePathOne = diaryShareBean.getImageFiles().get(0).getFileUrl();
                    Glide.with(this).load(imagePathOne).into(imageView1);
                } else if (diaryShareBean.getImageFiles().size() == 2) {
                    imagePathOne = diaryShareBean.getImageFiles().get(0).getFileUrl();
                    imagePathTwo = diaryShareBean.getImageFiles().get(1).getFileUrl();
                    Glide.with(this).load(imagePathOne).into(imageView1);
                    Glide.with(this).load(imagePathTwo).into(imageView2);
                } else if (diaryShareBean.getImageFiles().size() == 3) {
                    imagePathTwo = diaryShareBean.getImageFiles().get(1).getFileUrl();
                    imagePathOne = diaryShareBean.getImageFiles().get(0).getFileUrl();
                    String imagePathThree = diaryShareBean.getImageFiles().get(2).getFileUrl();
                    Glide.with(this).load(imagePathOne).into(imageView1);
                    Glide.with(this).load(imagePathTwo).into(imageView2);
                    Glide.with(this).load(imagePathThree).into(imageView3);
                }
            }
        }
    }

    public static void toSquareDetailActivity(Activity activity, DiaryShareBean diaryShareBean) {
        Router.newIntent(activity).to(SquareDetailActivity.class).putSerializable("DiaryShareBean", diaryShareBean).launch();
    }


    @OnClick({R.id.iv_focuson, R.id.imageView1, R.id.imageView2, R.id.imageView3, R.id.tv_send, R.id.ll_toolbar_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_focuson:
                if (!isLike){
                    getP().addLike(diaryShareBean);
                }else {
                    getP().removeLike(diaryShareBean);
                }
                break;
            case R.id.imageView1:
                break;
            case R.id.imageView2:
                break;
            case R.id.imageView3:
                break;
            case R.id.tv_send:
                    if (!TextUtils.isEmpty(etInputComment.getText().toString().trim())){
                        if (secondComment){
                            getP().replyComment(comment,etInputComment.getText().toString(),diaryShareBean.getObjectId());
                        }else {
                            getP().sendComment(diaryShareBean.getObjectId(), etInputComment.getText().toString());
                        }
                    }else {
                        ToastUtil.showShort("内容不能为空");
                    }
                break;
            case R.id.ll_toolbar_right:
                showDialog();
                break;
        }
    }
   //每次添加评论成功执行
    public void addCommentData(CommentBean commentBean){
        /**
         * 隐藏软键盘
         */
        InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        etInputComment.setText("");
        mainCommentAdapter.addTheCommentData(commentBean);//通知适配器改变数据
    }


    private List<CommentBean> commentBeanList;

    //将获取到的评论显示出来
    public void setComment( List<CommentBean> commentList) {
        secondComment=false;
        /**
         * 隐藏软键盘
         */
        InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        etInputComment.setHint("");
        etInputComment.setText("");
        commentBeanList = commentList;
        if (mainCommentAdapter!=null){
            mainCommentAdapter=new MainCommentAdapter(this,commentBeanList);
            mainCommentAdapter.notifyDataSetInvalidated();
            expandableListView.setAdapter(mainCommentAdapter);
            for(int i = 0; i<commentBeanList.size(); i++){//展开子项
                expandableListView.expandGroup(i);
            }
        }else {
            mainCommentAdapter=new MainCommentAdapter(this,commentBeanList);
            expandableListView.setAdapter(mainCommentAdapter);
            expandableListView.setGroupIndicator(null);//隐藏向上箭头
            for(int i = 0; i<commentBeanList.size(); i++){//展开子项
                expandableListView.expandGroup(i);
            }
            expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                    secondComment=false;
                    etInputComment.setHint("");
                    commentObjectId = commentBeanList.get(groupPosition).getObjectId();
                    comment=commentBeanList.get(groupPosition);
                    if (isUser) {
                        showPopupWindow(true);
                    } else {
                        if (commentBeanList.get(groupPosition).getUser().getObjectId().equals(userBean.getObjectId())) {
                            showPopupWindow(true);
                        }else {
                            showPopupWindow(false);
                        }
                    }
                    return true;
                }
            });
            expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                    startActivityForResult(new Intent(SquareDetailActivity.this,CommentActivity.class).putExtra("commentBean",commentBeanList.get(groupPosition)),1);
                    return false;
                }
            });
        }

    }


    private PopupWindow mPopWindow;

    private void showPopupWindow(boolean isUser) {
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
        if (!isUser){
            tv2.setVisibility(View.GONE);
        }
        tv3.setOnClickListener(this);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        textView1.setText("回复");
        textView2.setText("删除");
        //显示PopupWindow
        View rootview = LayoutInflater.from(context).inflate(R.layout.activity_square_detail, null);
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
                secondComment=true;
                etInputComment.setHint("回复@"+comment.getUser().getNickName());
                mPopWindow.dismiss();
                break;
            case R.id.photo_roll2:
                getP().deleteComment(commentObjectId);
                mPopWindow.dismiss();
                break;
            case R.id.photo_roll3:
                mPopWindow.dismiss();
                break;
        }
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
        builder.setTitle("您的帖子")
                .setWidth(0.7f)
                .setTitleColor(Color.parseColor("#2db4ff"))//标题字体颜值 0x909090 or Color.parseColor("#909090")
                .setText("确认删除")
                .setTextColor(Color.parseColor("#2db4ff"))
                .setPositive("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getP().deletePost(diaryShareBean.getObjectId(), diaryShareBean.getTypeId());
//                        LitePal.delete(DiarySQLBean.class, id);//删除指定Id的日记
//                        DiaryDetailActivity.this.setResult(3);
                    }
                }).configPositive(sure)//配置确定按钮更多的属性
                .setNegative("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .show();
    }
   //删除评论成功后的回调
    public void deleteCommentSucceed() {
        ToastUtil.showShort("删除评论成功");
        getP().getComment(diaryShareBean.getObjectId());//查询该帖子的评论
    }
    public void deletePostSucceed() {
        SquareDetailActivity.this.setResult(2);
        finish();
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) { //resultCode为回传的标记
            case 2:
                getP().getComment(diaryShareBean.getObjectId());
                break;
            default:
                break;
        }
    }

    //添加喜欢成功
    public void addLikeSucceed(){
        getP().whetherLike(diaryShareBean.getObjectId());
        ivFocuson.setBackgroundResource(R.mipmap.icon_has_focuson);
    }
    //移除喜欢成功
    public void removeLikeSucceed(){
        getP().whetherLike(diaryShareBean.getObjectId());
        ivFocuson.setBackgroundResource(R.mipmap.icon_no_focuson);
    }
    public void setFocusonBackground(boolean islike){
        isLike=islike;
        if (isLike){
            ivFocuson.setBackgroundResource(R.mipmap.icon_has_focuson);
        }else {
            ivFocuson.setBackgroundResource(R.mipmap.icon_no_focuson);
        }
    }
    public void changBean(DiaryShareBean diaryShareBean){
        this.diaryShareBean=diaryShareBean;
    }
}
