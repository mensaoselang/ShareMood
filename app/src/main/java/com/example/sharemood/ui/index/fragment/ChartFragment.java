package com.example.sharemood.ui.index.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sharemood.R;
import com.example.sharemood.base.BaseFragment;
import com.example.sharemood.chart.activity.BarChartActivity;
import com.example.sharemood.chart.activity.LineChartActivity;
import com.example.sharemood.chart.activity.PieChartActivity;
import com.example.sharemood.ui.index.presenter.ChartFgPresenter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by acer on 2018/11/24.
 */

public class ChartFragment extends BaseFragment<ChartFgPresenter> {
    @BindView(R.id.barchart)
    BarChart barchart;
    @BindView(R.id.cv_bar_chart)
    CardView cvBarChart;
    @BindView(R.id.pieChart)
    PieChart pieChart;
    @BindView(R.id.cv_pie_chart)
    CardView cvPieChart;
    @BindView(R.id.lineChart)
    LineChart lineChart;
    @BindView(R.id.cv_line_chart)
    CardView cvLineChart;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    public static ChartFragment newInstance() {
        Bundle args = new Bundle();
        ChartFragment fragment = new ChartFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        getP().initBarChartView(barchart);
        getP().initLineChartView(lineChart);
        getP().initPieChartView(pieChart);
        pieChart.setTouchEnabled(false);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {//刷新布局
            @Override
            public void onRefresh() {
                getP().initPieChartView(pieChart);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.chart_fragment;
    }

    @Override
    public ChartFgPresenter newP() {
        return new ChartFgPresenter();
    }

    @OnClick({R.id.cv_bar_chart, R.id.cv_pie_chart, R.id.cv_line_chart, R.id.pieChart, R.id.lineChart, R.id.barchart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cv_bar_chart:
                startActivity(new Intent(context, BarChartActivity.class));
                break;
            case R.id.barchart:
                startActivity(new Intent(context, BarChartActivity.class));
                break;
            case R.id.cv_pie_chart:
                startActivity(new Intent(context, PieChartActivity.class));
                break;
            case R.id.pieChart:
                startActivity(new Intent(context, PieChartActivity.class));
                break;
            case R.id.lineChart:
                startActivity(new Intent(context, LineChartActivity.class));
                break;
            case R.id.cv_line_chart:
                startActivity(new Intent(context, LineChartActivity.class));
                break;
        }
    }
}
