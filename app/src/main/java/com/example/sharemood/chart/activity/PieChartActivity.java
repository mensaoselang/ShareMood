package com.example.sharemood.chart.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.sharemood.R;
import com.example.sharemood.base.BaseActivity;
import com.example.sharemood.chart.bean.PieChartMoodBean;
import com.example.sharemood.chart.presenter.PieChartPresenter;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PieChartActivity extends BaseActivity<PieChartPresenter> {
    @BindView(R.id.pieChart)
    PieChart pieChart;
    @BindView(R.id.tv_toolbar_save)
    TextView tvToolbarSave;

    @Override
    public void initData(Bundle savedInstanceState) {
        tvToolbarSave.setVisibility(View.INVISIBLE);
        getP().getData();//获取心情饼图的数据
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_pie_chart;
    }

    @Override
    public PieChartPresenter newP() {
        return new PieChartPresenter();
    }

    //设置各区块的颜色
    public static int[] PIE_COLORS = {};

    //初始饼状图
    public void initPieChart(List<PieChartMoodBean> pieChartMoodBeanList) {

        HashMap dataMap = new LinkedHashMap();//LinKedHashMap是有顺序的，
        int time = 1;
        for (int i = 0; i < pieChartMoodBeanList.size(); i++) {
            if (pieChartMoodBeanList.get(i).getSum() > 0) {
                time++;
            }
        }
        PIE_COLORS = new int[time];//设置颜色
        int index = 0;
        for (int i = 0; i < pieChartMoodBeanList.size(); i++) {
            if (pieChartMoodBeanList.get(i).getSum() > 0) {
                dataMap.put(pieChartMoodBeanList.get(i).getType(), pieChartMoodBeanList.get(i).getSum());
                PIE_COLORS[index] = Color.parseColor("#" + pieChartMoodBeanList.get(i).getColor());
                index++;
            }
        }

        pieChart.setUsePercentValues(true);//设置使用百分比（后续有详细介绍）
        pieChart.getDescription().setEnabled(false);//设置描述
        pieChart.setExtraOffsets(25, 10, 25, 25); //设置边距
        pieChart.setDragDecelerationFrictionCoef(0.95f);//设置摩擦系数（值越小摩擦系数越大）
        pieChart.setCenterText("分享比重");//设置环中的文字
        pieChart.setRotationEnabled(true);//是否可以旋转
        pieChart.setHighlightPerTapEnabled(true);//点击是否放大
        pieChart.setCenterTextSize(22f);//设置环中文字的大小
        pieChart.setDrawCenterText(true);//设置绘制环中文字
        pieChart.setRotationAngle(120f);//设置旋转角度
        pieChart.setTransparentCircleRadius(61f);//设置半透明圆环的半径,看着就有一种立体的感觉
        //这个方法为true就是环形图，为false就是饼图
        pieChart.setDrawHoleEnabled(true);
        //设置环形中间空白颜色是白色
        pieChart.setHoleColor(Color.WHITE);
        //设置半透明圆环的颜色
        pieChart.setTransparentCircleColor(Color.WHITE);
        //设置半透明圆环的透明度
        pieChart.setTransparentCircleAlpha(110);
        //图例设置
        Legend legend = pieChart.getLegend();
        if (true) {//色彩说明的那一栏
            legend.setEnabled(true);//是否显示图例
            legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);//图例相对于图表横向的位置
            legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);//图例相对于图表纵向的位置
            legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);//图例显示的方向
            legend.setDrawInside(false);
            legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        } else {
            legend.setEnabled(false);
        }
        //设置饼图数据
        setPieChartData(pieChart, dataMap);
        pieChart.animateX(1500, Easing.EasingOption.EaseInOutQuad);//数据显示动画
    }

    //设置饼图数据
    private void setPieChartData(PieChart pieChart, Map<String, Float> pieValues) {

        Set set = pieValues.entrySet();
        Iterator it = set.iterator();
        List<PieEntry> entries = new ArrayList<>();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            entries.add(new PieEntry(Float.valueOf(entry.getValue().toString()), entry.getKey().toString()));
        }
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(3f);//设置饼块之间的间隔
        dataSet.setSelectionShift(5f);//设置饼块选中时偏离饼图中心的距离
        dataSet.setColors(PIE_COLORS);//设置饼块的颜色
        //设置数据显示方式有见图
        dataSet.setValueLinePart1OffsetPercentage(80f);//数据连接线距图形片内部边界的距离，为百分数
        dataSet.setValueLinePart1Length(0.3f);
        dataSet.setValueLinePart2Length(0.4f);
        dataSet.setValueLineColor(Color.GREEN);//设置连接线的颜色
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        PieData pieData = new PieData(dataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(11f);
        pieData.setValueTextColor(Color.DKGRAY);
        pieChart.setData(pieData);
        pieChart.highlightValues(null);
        pieChart.invalidate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
