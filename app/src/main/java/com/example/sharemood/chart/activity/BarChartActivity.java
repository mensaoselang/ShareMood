package com.example.sharemood.chart.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.sharemood.R;
import com.example.sharemood.base.BaseActivity;
import com.example.sharemood.chart.bean.ChartMoodSqlBean;
import com.example.sharemood.chart.bean.MyXFormatter;
import com.example.sharemood.chart.presenter.BarChartPresenter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BarChartActivity extends BaseActivity<BarChartPresenter> {
    @BindView(R.id.barchart)
    BarChart barchart;
    @BindView(R.id.tv_toolbar_save)
    TextView tvToolbarSave;
    private BarChart barChart;

    //    protected String[] weekDate = new String[]{"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
//    //1 创建类型的列表Entry ，将保留您的值：
//    ArrayList<BarEntry> barEntryArrayList = new ArrayList<BarEntry>();
    @Override
    public void initData(Bundle savedInstanceState) {
        tvToolbarSave.setVisibility(View.INVISIBLE);
        getP().getData();//从本地数据库获取数据
    }

    public void initBarData(List<ChartMoodSqlBean> list) {
        String[] weekDate = new String[list.size()];
        ArrayList<BarEntry> barEntryArrayList = new ArrayList<BarEntry>();
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                barEntryArrayList.add(new BarEntry(i, list.get(i).getMoodIndex()));
                weekDate[i] = list.get(i).getDayOfWeek();
            }
            showView(weekDate, barEntryArrayList);
        }
    }

    public void showView(String[] weekDate, ArrayList<BarEntry> barEntryArrayList) {
        //自定义x轴显示
        MyXFormatter formatter = new MyXFormatter(weekDate);
        XAxis xAxis = barchart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        xAxis.setDrawGridLines(false);
        //显示个数
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(formatter);
        //3 有了 Entry 对象的 lists 集合，再创建 LineDataSet 对象：
        BarDataSet barDataSet = new BarDataSet(barEntryArrayList, "心情指数");
        //4  DataSets 集合和 x-axis entries 集合，来创建我们的 ChartData 对象：
        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(barDataSet);
        BarData data = new BarData(dataSets);
        data.setDrawValues(true);
        barchart.setData(data);
        barchart.invalidate();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_bar_chart;
    }

    @Override
    public BarChartPresenter newP() {
        return new BarChartPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
