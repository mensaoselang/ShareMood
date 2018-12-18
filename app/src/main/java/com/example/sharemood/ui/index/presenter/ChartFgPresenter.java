package com.example.sharemood.ui.index.presenter;

import android.graphics.Color;

import com.example.sharemood.base.BasePresenter;
import com.example.sharemood.bean.MoodTypeBean;
import com.example.sharemood.chart.bean.ChartMoodSqlBean;
import com.example.sharemood.chart.bean.MyXFormatter;
import com.example.sharemood.chart.bean.PieChartMoodBean;
import com.example.sharemood.ui.index.fragment.ChartFragment;
import com.example.sharemood.ui.login.Bean.MyUserBean;
import com.example.sharemood.utils.ToastUtil;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by acer on 2018/12/8.
 */

public class ChartFgPresenter extends BasePresenter<ChartFragment> {
    List<ChartMoodSqlBean> list= LitePal.findAll(ChartMoodSqlBean.class);
     public void initPieChartView(final PieChart pieChart){

         BmobQuery<PieChartMoodBean> query = new BmobQuery<>();
         query.addWhereEqualTo("myUserBean", BmobUser.getCurrentUser(MyUserBean.class));
         query.findObjects(new FindListener<PieChartMoodBean>() {
             @Override
             public void done(List<PieChartMoodBean> object, BmobException e) {
                 if (e == null) {
                     if (object.size()==0){//如果没有这个表或者这个表为0就重新建表
                         getTitleData(BmobUser.getCurrentUser(MyUserBean.class));
                     }else {
                         initPieChart(object,pieChart);//获取数据成功，初始饼图
                     }
                 } else {
                     ToastUtil.showShort("饼图失败" + e.getMessage());
                 }
             }
         });

     }
    public void initLineChartView(LineChart lineChart){
        initLineData(list,lineChart);
    }
    public void initBarChartView(BarChart barchart){
        initBarData(list,barchart);
    }


    //初始折线图
    public void initLineData(List<ChartMoodSqlBean> list,LineChart lineChart) {
        //1 创建类型的列表Entry ，将保留您的值：
        ArrayList<Entry> entries = new ArrayList<Entry>();
        String[] weekDate = new String[list.size()];
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                entries.add(new BarEntry(i, list.get(i).getMoodIndex()));
                weekDate[i] = list.get(i).getDayOfWeek();
            }
            showLineView(weekDate, entries,lineChart);
        }
    }

    public void showLineView(String[] weekDate, ArrayList<Entry> entries,LineChart lineChart) {
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


    //初始饼图
    //设置各区块的颜色
    public static int[] PIE_COLORS = {};

    //初始饼状图
    public void initPieChart(List<PieChartMoodBean> pieChartMoodBeanList,PieChart pieChart) {

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
        pieChart.setDragDecelerationFrictionCoef(0.95f);//设置摩擦系数（值越小摩擦系数越大）
        pieChart.setCenterText("分享比重");//设置环中的文字
        pieChart.setRotationEnabled(true);//是否可以旋转
        pieChart.setHighlightPerTapEnabled(true);//点击是否放大
        pieChart.setCenterTextSize(15f);//设置环中文字的大小
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



    //初始柱形图
    public void initBarData(List<ChartMoodSqlBean> list,BarChart barchart) {
        String[] weekDate = new String[list.size()];
        ArrayList<BarEntry> barEntryArrayList = new ArrayList<BarEntry>();
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                barEntryArrayList.add(new BarEntry(i, list.get(i).getMoodIndex()));
                weekDate[i] = list.get(i).getDayOfWeek();
            }
            showBarView(weekDate, barEntryArrayList,barchart);
        }

    }
    public void showBarView(String[] weekDate, ArrayList<BarEntry> barEntryArrayList,BarChart barchart) {
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


    public void getTitleData(final MyUserBean myUserBean){
        BmobQuery<MoodTypeBean> typeQuery=new BmobQuery<>();
        typeQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);   // 先从缓存获取数据，如果没有，再从网络获取。
        typeQuery.findObjects(new FindListener<MoodTypeBean>() {
            @Override
            public void done(List<MoodTypeBean> list, BmobException e) {
                if (e==null){
                    if (list.size()>0){
                        for (int i=0;i<list.size();i++){

                            //添加关联，用户关联帖子;
                            PieChartMoodBean pieChartMoodBean=new PieChartMoodBean();
                            pieChartMoodBean.setType(list.get(i).getType());
                            pieChartMoodBean.setSum(0);
                            pieChartMoodBean.setTypeId(list.get(i).getId());
                            pieChartMoodBean.setColor(list.get(i).getColor());
                            pieChartMoodBean.setMyUserBean(myUserBean);
                            pieChartMoodBean.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if (e!=null){
                                        ToastUtil.showShort("心情分享比重建表失败"+e.getMessage());
                                    }
                                }
                            });

                        }
                    }
                }else {
                    ToastUtil.showShort("获取首页类型表失败");
                }

            }
        });
    }
}
