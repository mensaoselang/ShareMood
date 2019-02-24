package com.example.sharemood.chart.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.sharemood.R;
import com.example.sharemood.base.BaseActivity;
import com.example.sharemood.chart.bean.ChartMoodSqlBean;
import com.example.sharemood.chart.bean.MyXFormatter;
import com.example.sharemood.chart.presenter.LineChartPresenter;
import com.example.sharemood.utils.ToastUtil;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LineChartActivity extends BaseActivity<LineChartPresenter> {
    @BindView(R.id.lineChart)
    LineChart lineChart;
    @BindView(R.id.tv_toolbar_save)
    TextView tvToolbarSave;

    //    protected String[] weekDate = new String[]{"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
//    //1 创建类型的列表Entry ，将保留您的值：
//    ArrayList<Entry> entries = new ArrayList<Entry>();
    @Override
    public void initData(Bundle savedInstanceState) {
        tvToolbarSave.setText("截图");
        getP().getData();

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_line_chart;
    }

    @Override
    public LineChartPresenter newP() {
        return new LineChartPresenter();
    }

    public void initLineData(List<ChartMoodSqlBean> list) {
        //1 创建类型的列表Entry ，将保留您的值：
        ArrayList<Entry> entries = new ArrayList<Entry>();
        String[] weekDate = new String[list.size()];
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                entries.add(new BarEntry(i, list.get(i).getMoodIndex()));
                weekDate[i] = list.get(i).getDayOfWeek();
            }
            showView(weekDate, entries);
        }
    }

    public void showView(String[] weekDate, ArrayList<Entry> entries) {
        //自定义x轴显示
        MyXFormatter formatter = new MyXFormatter(weekDate);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        //显示个数
        xAxis.setLabelCount(8);
        xAxis.setValueFormatter(formatter);
        //3 有了 Entry 对象的 lists 集合，再创建 LineDataSet 对象：
        LineDataSet barDataSet = new LineDataSet(entries, "心情指数");
        //4  DataSets 集合和 x-axis entries 集合，来创建我们的 ChartData 对象：
        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(barDataSet);
        LineData data = new LineData(dataSets);
        data.setDrawValues(true);
        lineChart.setData(data);
        lineChart.invalidate();
    }
    @OnClick(R.id.tv_toolbar_save)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.tv_toolbar_save:
                saveImageth();
                break;
        }
    }
    public void saveImageth() {
        if (lineChart.saveToGallery("barchart"+ System.currentTimeMillis(),50)) {
            ToastUtil.showShort("图片已保存");
        }else {
            ToastUtil.showShort("图片保存失败");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
