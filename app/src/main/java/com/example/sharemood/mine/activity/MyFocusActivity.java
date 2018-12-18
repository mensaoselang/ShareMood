package com.example.sharemood.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sharemood.R;
import com.example.sharemood.base.BaseActivity;
import com.example.sharemood.bean.DiaryShareBean;
import com.example.sharemood.mine.presenter.MyFocusPresenter;
import com.example.sharemood.square.activity.SquareDetailActivity;
import com.example.sharemood.ui.index.adapter.XrContentAdapter;
import com.example.sharemood.view.BackLinearLayout;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyFocusActivity extends BaseActivity<MyFocusPresenter> {
    @BindView(R.id.ll_toolbar_left)
    BackLinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_save)
    TextView tvToolbarSave;
    @BindView(R.id.ll_toolbar_right)
    LinearLayout llToolbarRight;
    @BindView(R.id.xr_ry)
    XRecyclerView xrRy;
    @BindView(R.id.tv_data)
    TextView tvData;
    private XrContentAdapter xrContentAdapter;
    @Override
    public void initData(Bundle savedInstanceState) {
        tvToolbarSave.setVisibility(View.INVISIBLE);
        tvToolbarTitle.setText("我的收藏");
        getP().queryPostMylike();
        //列表刷新监听
        xrRy.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //这里写刷新执行的操作,上拉刷新
                getP().queryPostMylike();
                xrRy.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                //下拉加载
                xrRy.loadMoreComplete();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_focus;
    }

    @Override
    public MyFocusPresenter newP() {
        return new MyFocusPresenter();
    }
    List<DiaryShareBean> rows;
    public void initContentView(List<DiaryShareBean> object) {
        rows = object;
        if (rows.size() == 0) {
            tvData.setVisibility(View.VISIBLE);
        } else {
            tvData.setVisibility(View.GONE);
        }
        if (xrContentAdapter != null) {
            xrContentAdapter.setData(rows);
            xrContentAdapter.notifyDataSetChanged();
        } else {
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            xrContentAdapter = new XrContentAdapter(context);
            xrContentAdapter.setData(object);
            xrRy.setLayoutManager(staggeredGridLayoutManager);
            xrRy.setAdapter(xrContentAdapter);
            xrContentAdapter.setCustomOnClick(new XrContentAdapter.ItemOnClick() {
                @Override
                public void OnItemClick(int position) {
                    startActivityForResult(new Intent(context, SquareDetailActivity.class).putExtra("DiaryShareBean", rows.get(position)), 1);
//                    SquareDetailActivity.toSquareDetailActivity(context, rows.get(position));
                }
            });
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
