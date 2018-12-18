package com.example.sharemood.ui.index.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sharemood.R;
import com.example.sharemood.base.BaseFragment;
import com.example.sharemood.ui.index.adapter.IndexFragmentAdapter;
import com.example.sharemood.ui.index.presenter.IndexFgPresenter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by acer on 2018/11/23.
 */

public class IndexFragment extends BaseFragment<IndexFgPresenter> {

    @BindView(R.id.xr_ry)
    XRecyclerView xrRy;

    private IndexFragmentAdapter indexFragmentAdapter;
    private List<String> list=new ArrayList<>();
    public static IndexFragment newInstance() {
        Bundle args = new Bundle();
        IndexFragment fragment = new IndexFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
//        //设置布局管理者，才能显示视图GridLayoutManager有setSpanSizeLookup()
        GridLayoutManager manager = new GridLayoutManager(getActivity(),1);
        indexFragmentAdapter = new IndexFragmentAdapter(context,list);
        xrRy.setLayoutManager(manager);
        xrRy.setAdapter(indexFragmentAdapter);
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
    }

    @Override
    public int getLayoutId() {
        return R.layout.index_fragment;
    }

    @Override
    public IndexFgPresenter newP() {
        return new IndexFgPresenter();
    }
}
