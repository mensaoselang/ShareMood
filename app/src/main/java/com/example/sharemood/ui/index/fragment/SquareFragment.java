package com.example.sharemood.ui.index.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sharemood.R;
import com.example.sharemood.base.BaseFragment;
import com.example.sharemood.bean.DiaryShareBean;
import com.example.sharemood.bean.MoodTypeBean;
import com.example.sharemood.square.activity.SquareDetailActivity;
import com.example.sharemood.ui.index.adapter.RvTitleAdapter;
import com.example.sharemood.ui.index.adapter.XrContentAdapter;
import com.example.sharemood.ui.index.presenter.SquareFgPresenter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by acer on 2018/11/27.
 */

public class SquareFragment extends BaseFragment<SquareFgPresenter> {
    @BindView(R.id.xr_ry)
    XRecyclerView xrRy;
    @BindView(R.id.rv_title)
    RecyclerView rvTitle;
    @BindView(R.id.tv_data)
    TextView tvData;

    private RvTitleAdapter rvTitleAdapter;
    private XrContentAdapter xrContentAdapter;
    private int typeId;
   private boolean isdelete=false;
   private List<MoodTypeBean> moodTypeBeanList;
    public static SquareFragment newInstance() {
        Bundle args = new Bundle();
        SquareFragment fragment = new SquareFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        getP().getTitleData();
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        xrContentAdapter = new XrContentAdapter(context);
        xrRy.setLayoutManager(staggeredGridLayoutManager);
        xrRy.setAdapter(xrContentAdapter);
        xrContentAdapter.setCustomOnClick(new XrContentAdapter.ItemOnClick() {
            @Override
            public void OnItemClick(int position) {
                startActivityForResult(new Intent(context, SquareDetailActivity.class).putExtra("DiaryShareBean",rows.get(position)), 1);
            }
        });
        //列表刷新监听
        xrRy.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //这里写刷新执行的操作,上拉刷新
                if (moodTypeBeanList==null){
                    getP().getTitleData();
                }
                getP().getContentData(typeId,false);//获取对应类型的内容
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
        return R.layout.square_fragment;
    }

    @Override
    public SquareFgPresenter newP() {
        return new SquareFgPresenter();
    }

    public void initTitleView(List<MoodTypeBean> titleList) {
        moodTypeBeanList=titleList;
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvTitle.setLayoutManager(layoutManager);
        rvTitleAdapter = new RvTitleAdapter(context);
        rvTitleAdapter.setData(titleList);
        rvTitle.setAdapter(rvTitleAdapter);
        if (titleList!=null) {
            if (titleList.size() > 0) {
                typeId = titleList.get(0).getId();
                getP().getContentData(typeId, true);//默认加载第一个title
            }
        }
        //获取对应类型的内容
        rvTitleAdapter.SetOnClick(new RvTitleAdapter.ItemOnClick() {
            @Override
            public void OnItemClick(int position, int id) {
                rvTitleAdapter.clearSelection(position);
                rvTitleAdapter.notifyDataSetChanged();
                typeId = id;
                if (isdelete){
                    getP().getContentData(typeId,false);//获取对应类型的内容
                    isdelete=false;
                }else {
                    getP().getContentData(typeId,true);//获取对应类型的内容
                }
            }
        });
    }

    List<DiaryShareBean> rows;

    public void initContentView(List<DiaryShareBean> object) {
        rows = object;
        if (rows.size()== 0) {
            tvData.setVisibility(View.VISIBLE);
        }else {
            tvData.setVisibility(View.GONE);
        }
        if (xrContentAdapter != null) {
            xrContentAdapter.setData(rows);
            xrContentAdapter.notifyDataSetChanged();
        } else {
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            xrContentAdapter = new XrContentAdapter(context);
            xrRy.setLayoutManager(staggeredGridLayoutManager);
            xrRy.setAdapter(xrContentAdapter);
            xrContentAdapter.setData(object);
            xrContentAdapter.setCustomOnClick(new XrContentAdapter.ItemOnClick() {
                @Override
                public void OnItemClick(int position) {
                    startActivityForResult(new Intent(context, SquareDetailActivity.class).putExtra("DiaryShareBean",rows.get(position)), 1);
//                    SquareDetailActivity.toSquareDetailActivity(context, rows.get(position));
                }
            });
        }

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) { //resultCode为回传的标记
            case 2:
                //用户删除帖子后，重新获取帖子内容
                isdelete=true;
                getP().getContentData(typeId,false);//获取对应类型的内容
                break;
            default:
                break;
        }
    }

}
