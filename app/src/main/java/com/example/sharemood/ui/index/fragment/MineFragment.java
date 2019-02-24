package com.example.sharemood.ui.index.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sharemood.R;
import com.example.sharemood.base.BaseFragment;
import com.example.sharemood.mine.activity.MineDataActivity;
import com.example.sharemood.mine.activity.MineShareActivity;
import com.example.sharemood.mine.activity.MyFocusActivity;
import com.example.sharemood.ui.index.presenter.MinePresenter;
import com.example.sharemood.ui.login.Bean.MyUserBean;
import com.example.sharemood.ui.login.activity.LoginActivity;
import com.example.sharemood.utils.SharePreferenceUtil;
import com.jph.takephoto.app.TakePhoto;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by acer on 2018/11/23.
 */

public class MineFragment extends BaseFragment<MinePresenter> {
    @BindView(R.id.iv_head_bg)
    ImageView ivHeadBg;
    @BindView(R.id.iv_portrait)
    CircleImageView ivPortrait;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_say)
    TextView tvSay;
    @BindView(R.id.tv_login_out)
    TextView tvLoginOut;
    @BindView(R.id.tv_usetdata)
    TextView tvUsetdata;
    @BindView(R.id.tv_diary)
    TextView tvDiary;
    @BindView(R.id.tv_focus_on)
    TextView tvFocusOn;
    @BindView(R.id.tv_share)
    TextView tvShare;
    @BindView(R.id.tv_set_up)
    TextView tvSetUp;
    Unbinder unbinder;
    @BindView(R.id.tv_say_content)
    TextView tvSayContent;
    private TakePhoto takePhoto;
    private MyUserBean myUserBean;
    public static MineFragment newInstance() {
        Bundle args = new Bundle();
        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        getP().getMyData();//获取个人信息
    }

    @Override
    public int getLayoutId() {
        return R.layout.mine_fragment;
    }

    @Override
    public MinePresenter newP() {
        return new MinePresenter();
    }
    public void initView(MyUserBean myUserBean) {
        this.myUserBean=myUserBean;
        tvNickname.setText(myUserBean.getNickName());
        tvSex.setText(myUserBean.getSex());
        tvSayContent.setText(myUserBean.getQuotations());
        if (!"".equals(myUserBean.getImagePath())&&myUserBean.getImagePath()!=null){
            Glide.with(this).load(myUserBean.getImagePath()).into(ivPortrait);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.tv_login_out, R.id.tv_usetdata, R.id.tv_diary, R.id.tv_focus_on, R.id.tv_share, R.id.tv_set_up,R.id.iv_portrait})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_login_out:
                LoginActivity.toLoginActivity(context);
                context.finish();
                break;
            case R.id.tv_usetdata:
                startActivityForResult(new Intent(context, MineDataActivity.class).putExtra("myUserBean",myUserBean), 1);
                break;
            case R.id.tv_diary:
                break;
            case R.id.tv_focus_on:
                startActivity(new Intent(context, MyFocusActivity.class));
                break;
            case R.id.tv_share:
                startActivity(new Intent(context, MineShareActivity.class));
                break;
            case R.id.tv_set_up:
                break;
            case R.id.iv_portrait:
                startActivityForResult(new Intent(context, MineDataActivity.class).putExtra("myUserBean",myUserBean), 1);
                break;
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) { //resultCode为回传的标记
            case 2:
                //用户资料修改后，重新获取个人信息
                getP().getMyData();
                break;
            default:
                break;
        }
    }
}
