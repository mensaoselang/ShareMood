package com.example.sharemood;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.sharemood.base.BaseActivity;
import com.example.sharemood.chart.bean.ChartMoodSqlBean;
import com.example.sharemood.diary.bean.DiarySQLBean;
import com.example.sharemood.ui.index.fragment.ChartFragment;
import com.example.sharemood.ui.index.fragment.DiaryFragment;
import com.example.sharemood.ui.index.fragment.IndexFragment;
import com.example.sharemood.ui.index.fragment.MineFragment;
import com.example.sharemood.ui.index.fragment.SquareFragment;
import com.example.sharemood.utils.SharePreferenceUtil;
import com.example.sharemood.view.NoScrollViewPager;

import org.litepal.LitePal;
import org.litepal.LitePalDB;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.droidlover.xdroidmvp.base.XFragmentAdapter;
import cn.droidlover.xdroidmvp.router.Router;

public class MainActivity extends BaseActivity {
    @BindView(R.id.rdoBtn_index)
    RadioButton rdoBtnIndex;
    @BindView(R.id.rdoBtn_discover)
    RadioButton rdoBtnDiscover;
    @BindView(R.id.rdoBtn_diary)
    RadioButton rdoBtnDiary;
    @BindView(R.id.rdoBtn_mine)
    RadioButton rdoBtnMine;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.vp_main_menu)
    NoScrollViewPager vpMainMenu;
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    public void initData(Bundle savedInstanceState) {
        LitePalDB litePalDB = LitePalDB.fromDefault(SharePreferenceUtil.getPhone());//使用当前登陆用户的数据库
        LitePal.use(litePalDB);
        String[] weekArray = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
        List<ChartMoodSqlBean> chartMoodSqlBeanList=new ArrayList<>();//一开始就为用户创建心情记录表
        chartMoodSqlBeanList = LitePal.findAll(ChartMoodSqlBean.class);
        if (chartMoodSqlBeanList!=null){
            if (chartMoodSqlBeanList.size()!=0){
            }else {
                for (int i=0;i<weekArray.length;i++){
                    ChartMoodSqlBean chartMoodSqlBean=new ChartMoodSqlBean();
                    chartMoodSqlBean.setDayOfWeek(weekArray[i]);
                    chartMoodSqlBean.setMoodIndex(0);
                    chartMoodSqlBean.save();
                }
            }
        }
        fragmentList.add(SquareFragment.newInstance());
        fragmentList.add(ChartFragment.newInstance());
        fragmentList.add(DiaryFragment.newInstance());
        fragmentList.add(MineFragment.newInstance());
        vpMainMenu.setNoScroll(true);//禁止viewpage滑动
        vpMainMenu.setAdapter(new XFragmentAdapter(getSupportFragmentManager(), fragmentList, null));
        vpMainMenu.setOffscreenPageLimit(fragmentList.size() - 1);//实现所有的Fragment都去预加载
        radioGroup.check(R.id.rdoBtn_index);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rdoBtn_index:
                        changeTab(0);
                        break;
                    case R.id.rdoBtn_discover:
                        changeTab(1);
                        break;
                    case R.id.rdoBtn_diary:
                        changeTab(2);
                        break;
                    case R.id.rdoBtn_mine:
                        changeTab(3);
                        break;
                }
            }
        });
//        //vpPage滑动到相应的位置改变底部栏的颜色变化
//        vpMainMenu.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                if (position == 0) {
//                    rdoBtnIndex.setChecked(true);
//                } else if (position == 1) {
//                    rdoBtnDiscover.setChecked(true);
//                } else if (position == 2) {
//                    rdoBtnDiary.setChecked(true);
//                } else if (position == 3) {
//                    rdoBtnMine.setChecked(true);
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//            }
//        });
    }

    //点击切换fragment
    private void changeTab(int position) {
        if (position >= 0 && position < fragmentList.size()) {
            vpMainMenu.setCurrentItem(position, false);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public Object newP() {
        return null;
    }

    public static void toMainActivity(Activity activity) {
        Router.newIntent(activity).to(MainActivity.class).launch();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
