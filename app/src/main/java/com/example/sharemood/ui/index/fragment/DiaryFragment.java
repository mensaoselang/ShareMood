package com.example.sharemood.ui.index.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sharemood.R;
import com.example.sharemood.base.BaseFragment;
import com.example.sharemood.diary.activity.DiaryDetailActivity;
import com.example.sharemood.diary.activity.DiaryWriteActivity;
import com.example.sharemood.diary.bean.DiarySQLBean;
import com.example.sharemood.ui.index.adapter.DiaryFragmentAdapter;
import com.example.sharemood.ui.index.presenter.DiaryFgPresenter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.litepal.LitePal;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by acer on 2018/11/24.
 */

public class DiaryFragment extends BaseFragment<DiaryFgPresenter> {

    @BindView(R.id.xr_ry)
    XRecyclerView xrRy;
    @BindView(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;
    Unbinder unbinder;
    @BindView(R.id.tv_prompt)
    TextView tvPrompt;

    private DiaryFragmentAdapter diaryFragmentAdapter;
    List<DiarySQLBean> diarySQLBeanList;

    public static DiaryFragment newInstance() {
        Bundle args = new Bundle();
        DiaryFragment fragment = new DiaryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xrRy.setLayoutManager(layoutManager);
        //列表刷新监听
        xrRy.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //这里写刷新执行的操作,上拉刷新
                xrRy.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                //下拉加载
                xrRy.loadMoreComplete();
            }
        });
        initView();
    }

    public void initView() {
        diarySQLBeanList = LitePal.order("id desc").find(DiarySQLBean.class);//倒叙查询数据
        if (diarySQLBeanList.size()==0){
            tvPrompt.setVisibility(View.VISIBLE);
        }
        diaryFragmentAdapter = new DiaryFragmentAdapter(context);
        diaryFragmentAdapter.setData(diarySQLBeanList);
        xrRy.setAdapter(diaryFragmentAdapter);
        //点击跳到日记详情页
        diaryFragmentAdapter.SetOnClick(new DiaryFragmentAdapter.ItemOnClick() {
            @Override
            public void OnItemClick(long id) {
                startActivityForResult(new Intent(context, DiaryDetailActivity.class).putExtra("id", id), 2);
            }
        });
    }

    public void changeData() {
        diarySQLBeanList = LitePal.findAll(DiarySQLBean.class);
        diarySQLBeanList = LitePal.order("id desc").find(DiarySQLBean.class);//倒叙查询数据
        diaryFragmentAdapter.setData(diarySQLBeanList);
        diaryFragmentAdapter.notifyDataSetChanged();
        if (diarySQLBeanList.size()>0){
            tvPrompt.setVisibility(View.GONE);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.diary_fragment;
    }

    @Override
    public DiaryFgPresenter newP() {
        return new DiaryFgPresenter();
    }

    @OnClick(R.id.floatingActionButton)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.floatingActionButton:
                startActivityForResult(new Intent(context, DiaryWriteActivity.class), 1);
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
            case 2:    //新建日记
            case 3:    //删除日记
            case 4:  //编辑完成
//                initView();
                changeData();
                break;
            default:
                break;
        }
    }
}
