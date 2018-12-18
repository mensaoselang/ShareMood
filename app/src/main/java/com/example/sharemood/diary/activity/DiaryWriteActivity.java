package com.example.sharemood.diary.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bumptech.glide.Glide;
import com.example.sharemood.App;
import com.example.sharemood.MainActivity;
import com.example.sharemood.R;
import com.example.sharemood.base.BaseActivity;
import com.example.sharemood.diary.bean.DiarySQLBean;
import com.example.sharemood.diary.bean.WeatherBean;
import com.example.sharemood.diary.presenter.DiaryWritePresenter;
import com.example.sharemood.utils.MJsonRequest;
import com.example.sharemood.utils.MyLocation;
import com.example.sharemood.utils.SharePreferenceUtil;
import com.example.sharemood.utils.ToastUtil;
import com.example.sharemood.view.BackLinearLayout;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.callback.ConfigButton;
import com.mylhyl.circledialog.params.ButtonParams;

import org.litepal.LitePal;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.router.Router;
import cn.qqtheme.framework.picker.ColorPicker;
import cn.qqtheme.framework.util.ConvertUtils;

public class DiaryWriteActivity extends BaseActivity<DiaryWritePresenter> implements View.OnClickListener, View.OnLongClickListener {

    @BindView(R.id.ll_toolbar_left)
    BackLinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_date)
    TextView tvToolbarDate;
    @BindView(R.id.tv_toolbar_time)
    TextView tvToolbarTime;
    @BindView(R.id.ll_toolbar_right)
    LinearLayout llToolbarRight;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.imageView1)
    ImageView imageView1;
    @BindView(R.id.imageView3)
    ImageView imageView3;
    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.iv_weather)
    ImageView ivWeather;
    @BindView(R.id.tv_weather)
    TextView tvWeather;
    @BindView(R.id.tv_temperature)
    TextView tvTemperature;
    @BindView(R.id.iv_location)
    ImageView ivLocation;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.tv_heart_manth)
    TextView tvHeartManth;
    @BindView(R.id.tv_title_heart)
    TextView tvTitleHeart;
    @BindView(R.id.tv_heart_color)
    TextView tvHeartColor;
    @BindView(R.id.iv_heart_color)
    ImageView ivHeartColor;
    @BindView(R.id.constrainlayout)
    ConstraintLayout constrainlayout;
    @BindView(R.id.floatingActionButton_save)
    FloatingActionButton floatingActionButtonSave;

    private int whitchImage = 0;
    private String imageOnePath = "";
    private String imageTwoPath = "";
    private String imageThreePath = "";
    private String defaultColor = "#2db4ff";
    private AlertDialog dialog;
    private EditText etLocation;

    private final int WEATHER=1;
    private final int TEMPERATURE=2;
    private final int HEARTDIGITAL=3;
    private final int TIME=4;
    private List<String> strList;

    private String Month="";
    private String Year="";
    private String Week="";
    private String Day="";
    private String Time="";
    SimpleDateFormat simpleTime = new SimpleDateFormat( "HH:mm");
    SimpleDateFormat simpleWeek=new SimpleDateFormat("E");
    SimpleDateFormat simpleDay=new SimpleDateFormat("dd");
    SimpleDateFormat simpleMonth=new SimpleDateFormat("MM");
    SimpleDateFormat simpleYear=new SimpleDateFormat("yyyy");

    private final int MY_PERMISSIONS_REQUEST_CALL_PHONE=1;
    private MyLocation myLocation;
    long now=System.currentTimeMillis();
    private int type;
    private long id;
    @Override
    public void initData(Bundle savedInstanceState) {
        type=getIntent().getIntExtra("type",0);
        id=getIntent().getLongExtra("id",-1);
        initView(type,id);//判断是那个Activity跳过来的进行初始化视图
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_diary_write;
    }

    @Override
    public DiaryWritePresenter newP() {
        return new DiaryWritePresenter();
    }

    public static void toDiaryWrite(Activity activity) {
        Router.newIntent(activity).to(DiaryWriteActivity.class).launch();
    }
     public void initView(int type,long id){
         if (type==DiaryDetailActivity.DIARYDETAIL){
             DiarySQLBean diarySQLBean=new DiarySQLBean();
             diarySQLBean=LitePal.find(DiarySQLBean.class,id);
             tvToolbarDate.setText(diarySQLBean.getMonth()+"月"+diarySQLBean.getDateNumber()+"日");
             tvToolbarTime.setText("/"+diarySQLBean.getTime());
             etContent.setText(diarySQLBean.getContent());
             tvWeather.setText(diarySQLBean.getWeather());
             tvTemperature.setText(diarySQLBean.getTemperature());
             tvLocation.setText(diarySQLBean.getLocation());
             tvHeartManth.setText(diarySQLBean.getMoodIndex());
             defaultColor=diarySQLBean.getMoodColor();
             ivHeartColor.setBackgroundColor(Color.parseColor(defaultColor));
             Year=diarySQLBean.getYear();
             Week=diarySQLBean.getDayOfWeek();
             Month=diarySQLBean.getMonth();
             Day=diarySQLBean.getDateNumber();
             Time=diarySQLBean.getTime();
             imageOnePath=diarySQLBean.getImagePathList().get(0);
             imageTwoPath=diarySQLBean.getImagePathList().get(1);
             imageThreePath=diarySQLBean.getImagePathList().get(2);
            if (!"".equals(imageOnePath)){
                Glide.with(this).load(imageOnePath).into(imageView1);
                imageView2.setVisibility(View.VISIBLE);
                if (!"".equals(imageTwoPath)){
                    Glide.with(this).load(imageTwoPath).into(imageView2);
                    imageView3.setVisibility(View.VISIBLE);
                    if (!"".equals(imageThreePath)){
                        Glide.with(this).load(imageThreePath).into(imageView3);
                    }
                }
            }
         }else {
             requirePermission();
             String time=simpleTime.format(now);
             String dayOfWeak=simpleWeek.format(now);
             String month=simpleMonth.format(now);
             String year=simpleYear.format(now);
             String day=simpleDay.format(now);
             tvToolbarDate.setText(month+"月"+day+"日");
             tvToolbarTime.setText("/"+time);
             Year=year;
             Week=dayOfWeak;
             Month=month;
             Day=day;
             Time=time;
         }



         imageView1.setOnLongClickListener(this);
         imageView2.setOnLongClickListener(this);
         imageView3.setOnLongClickListener(this);


     }

    @OnClick({R.id.ll_toolbar_left, R.id.tv_toolbar_date, R.id.tv_toolbar_time, R.id.et_content, R.id.imageView1, R.id.imageView3, R.id.imageView2, R.id.iv_weather,R.id.iv_location,
            R.id.tv_weather, R.id.tv_temperature, R.id.tv_location, R.id.tv_heart_manth, R.id.tv_title_heart, R.id.tv_heart_color, R.id.iv_heart_color, R.id.floatingActionButton_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                break;
            case R.id.tv_toolbar_date:
                showTimePickerView(TIME);
                break;
            case R.id.tv_toolbar_time:
                showTimePickerView(TIME);
                break;
            case R.id.et_content:
                break;
            case R.id.imageView1:
                whitchImage = 1;
                getP().getPhoto(1);
                break;
            case R.id.imageView2:
                whitchImage = 2;
                getP().getPhoto(2);
                break;
            case R.id.imageView3:
                whitchImage = 3;
                getP().getPhoto(3);
                break;
            case R.id.iv_weather:
                showOptionPickerView(WEATHER);
                break;
            case R.id.tv_weather:
                showOptionPickerView(WEATHER);
                break;
            case R.id.tv_temperature:
                showOptionPickerView(TEMPERATURE);
                break;
            case R.id.iv_location:
                showDialog();
                break;
            case R.id.tv_location:
                showDialog();
                break;
            case R.id.tv_heart_manth:
                showOptionPickerView(HEARTDIGITAL);
                break;
            case R.id.tv_title_heart:
                showOptionPickerView(HEARTDIGITAL);
                break;
            case R.id.tv_heart_color:
                showColorPicker();
                break;
            case R.id.iv_heart_color:
                showColorPicker();
                break;
            case R.id.floatingActionButton_save:
             //   LitePal.deleteDatabase("Diary");//删除对应的数据库
             //   LitePal.deleteDatabase("18475140875");
                String userPhone = SharePreferenceUtil.getPhone();
                String nickName = SharePreferenceUtil.getNickName();
                String content = etContent.getText().toString();
                String weather = tvWeather.getText().toString();
                String temperature = tvTemperature.getText().toString();
                String location = tvLocation.getText().toString();
                String moodColor = defaultColor;//心情色彩
                String moodindex = tvHeartManth.getText().toString();//心情指数
                String time = Time;
                String dayOfWeek = Week;
                String dayNumber =Day;
                String month=Month;
                String year =Year;
                    //保存日记或更新日记
                    getP().saveDiary(type,id,imageOnePath, imageTwoPath, imageThreePath, userPhone, nickName, content, weather,temperature,
                            location, moodColor, moodindex, time, dayOfWeek, dayNumber,month,year);


                break;
        }
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        showImg(result.getImages());
    }
    //显示时间滚轮选择器
    public void showTimePickerView(int type){
        Calendar startDate = Calendar.getInstance();//起始
        startDate.set(2000,0,1);
        Calendar endDate = Calendar.getInstance();//结束

        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(DiaryWriteActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                String time=simpleTime.format(date);
                String dayOfWeak=simpleWeek.format(date);
                String month=simpleMonth.format(date);
                String year=simpleYear.format(date);
                String day=simpleDay.format(date);
                tvToolbarDate.setText(month+"月"+day+"日");
                tvToolbarTime.setText("/"+time);
                Year=year;
                Week=dayOfWeak;
                Month=month;
                Day=day;
                Time=time;
            }
        }) .setTextColorCenter(Color.parseColor("#2db4ff"))//设置选中项的颜色
                .setSubmitColor(Color.parseColor("#2db4ff"))
                .setCancelColor(Color.parseColor("#2db4ff"))
                .setTitleSize(18)
                .setTitleText("日期")
                .setRangDate(startDate,endDate)//设置时间范围
                .setDate(endDate)//设置默认选中时间。不设置就默认当前时间
                .setTitleColor(Color.parseColor("#2db4ff"))
                .setDividerColor(Color.parseColor("#ffffff"))//分割线颜色
                .setType(new boolean[]{true, true, true, true, true, false})
                .setLabel("年","月","日","时","分","秒")//默认设置为年月日时分秒
                .build();
        pvTime.show();
    }

     //显示条件滚轮选择器
    public void showOptionPickerView(final int type){
        strList=new ArrayList<>();
        String [] weatherStr={"晴","多云","阴","雨","雪","霾","雾","未知"};
        String title="";
        if (type==WEATHER){
            strList.addAll(Arrays.asList(weatherStr));
            title="天气";
        }else if (type==TEMPERATURE){
            for (int x=-52;x<=50;x+=2){
                strList.add(x+"℃");
            }
            title="温度";
        }else if (type==HEARTDIGITAL){
            for (int x=-100;x<=100;x+=2){
                strList.add(x+"");
            }
            title="心情指数";
        }
        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(DiaryWriteActivity.this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                //返回的分别是三个级别的选中位置
                String tx = strList.get(options1);
                judgeType(type,tx);
            }
        }).setSelectOptions(strList.size()/2)//设置默认选中位置
                .setTextColorCenter(Color.parseColor("#2db4ff"))//设置选中项的颜色
                .setSubmitColor(Color.parseColor("#2db4ff"))
                .setCancelColor(Color.parseColor("#2db4ff"))
                .setTitleSize(18)
                .setTitleColor(Color.parseColor("#2db4ff"))
                .setDividerColor(Color.parseColor("#ffffff"))//分割线颜色
                .setTitleText(title)
                .build();
        pvOptions.setPicker(strList);
        pvOptions.show();
    }
    //判断是那个按钮点开
    public void judgeType(int type,String str){
        if (type==WEATHER){
            tvWeather.setText(str);
        }else if (type==TEMPERATURE){
              tvTemperature.setText(str);
        }else if (type==HEARTDIGITAL){
              tvHeartManth.setText(str);
        }
    }
    //显示色彩选择器
    public void showColorPicker() {
        ColorPicker picker = new ColorPicker(DiaryWriteActivity.this);
        picker.setInitColor(0x2db4ff);//默认初始颜色为蓝色
        picker.setOnColorPickListener(new ColorPicker.OnColorPickListener() {
            @Override
            public void onColorPicked(int pickedColor) {
                defaultColor = "#" + ConvertUtils.toColorString(pickedColor);
                ivHeartColor.setBackgroundColor(pickedColor);
            }
        });
        picker.show();
    }

    //显示自定义Dialog填写地址
    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DiaryWriteActivity.this);
        View view = View.inflate(DiaryWriteActivity.this, R.layout.dialog_location, null);
        TextView tvSure=(TextView) view.findViewById(R.id.tv_sure);
        TextView tvCancel=(TextView)view.findViewById(R.id.tv_cancel);
        etLocation=(EditText)view.findViewById(R.id.et_location);
        dialog = builder.create();
        dialog.show();
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//        lp.width = 900;//设置宽度
//        lp.width = MainUtils.getScreenWidth(activity)/10*6;
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(lp);
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);//解决dialog的Edittext无法弹出软键盘
        tvSure.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
    }

    private void showImg(ArrayList<TImage> images) {
        if (images.size() == 1) {
            if (whitchImage == 1) {
                Glide.with(this).load(images.get(0).getCompressPath()).into(imageView1);
                imageOnePath = images.get(0).getCompressPath();
                imageView2.setVisibility(View.VISIBLE);
            } else if (whitchImage == 2) {
                Glide.with(this).load(images.get(0).getCompressPath()).into(imageView2);
                imageTwoPath = images.get(0).getCompressPath();
                imageView3.setVisibility(View.VISIBLE);
            } else if (whitchImage == 3) {
                imageThreePath = images.get(0).getCompressPath();
                Glide.with(this).load(images.get(0).getCompressPath()).into(imageView3);
            }
        } else if (images.size() == 2) {
            if (whitchImage == 1) {
                imageView2.setVisibility(View.VISIBLE);
                imageView3.setVisibility(View.VISIBLE);
                imageOnePath = images.get(0).getCompressPath();
                imageTwoPath = images.get(1).getCompressPath();
                Glide.with(this).load(images.get(0).getCompressPath()).into(imageView1);
                Glide.with(this).load(images.get(1).getCompressPath()).into(imageView2);
            } else if (whitchImage == 2) {
                imageView2.setVisibility(View.VISIBLE);
                imageView3.setVisibility(View.VISIBLE);
                imageTwoPath = images.get(0).getCompressPath();
                imageThreePath = images.get(1).getCompressPath();
                Glide.with(this).load(images.get(0).getCompressPath()).into(imageView2);
                Glide.with(this).load(images.get(1).getCompressPath()).into(imageView3);
            }

        } else {
            imageView2.setVisibility(View.VISIBLE);
            imageView3.setVisibility(View.VISIBLE);
            imageOnePath = images.get(0).getCompressPath();
            imageTwoPath = images.get(1).getCompressPath();
            imageThreePath = images.get(2).getCompressPath();
            Glide.with(this).load(images.get(0).getCompressPath()).into(imageView1);
            Glide.with(this).load(images.get(1).getCompressPath()).into(imageView2);
            Glide.with(this).load(images.get(2).getCompressPath()).into(imageView3);
        }
    }

    //保存日记成功后
    public void saveDiarySucceed() {
        myLocation.cancelTimer();
        DiaryWriteActivity.this.setResult(2);
        finish();
    }
    //更新日记成功后
    public void updateDiarySucceed(){
        ToastUtil.showShort("更新编辑成功");
        DiaryWriteActivity.this.setResult(4);
        finish();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_cancel:
                dialog.dismiss();
                break;
            case R.id.tv_sure:
                tvLocation.setText(etLocation.getText().toString());
                dialog.dismiss();
                break;
        }
    }


    private void requirePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(App.getInstance(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},MY_PERMISSIONS_REQUEST_CALL_PHONE);
            }else{
                MyLocation.LocationResult locationResult = new MyLocation.LocationResult(){
                    @Override
                    public void gotLocation(Location location){
                        setLocation(location);
                    }
                };
                myLocation = new MyLocation();
                myLocation.getLocation(DiaryWriteActivity.this, locationResult);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE){
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    //判断是否勾选禁止后不再询问
                    boolean showRequestPermission = ActivityCompat.shouldShowRequestPermissionRationale(DiaryWriteActivity.this, permissions[i]);
                    if (showRequestPermission) {
                        ToastUtil.showShort("权限未申请");
                    }
                }else {
                    MyLocation.LocationResult locationResult = new MyLocation.LocationResult(){
                        @Override
                        public void gotLocation(Location location){
                            setLocation(location);
                        }
                    };
                    myLocation = new MyLocation();
                    myLocation.getLocation(DiaryWriteActivity.this, locationResult);
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
 //   输入经纬度查询到地址
    private void setLocation(Location location) {
        if (location!=null) {
            String mfeatureName = null;
            Geocoder geocoder = new Geocoder(DiaryWriteActivity.this);
            List<Address> addList = null;// 解析经纬度
            try {
                addList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (addList != null && addList.size() > 0) {
                for (int i = 0; i < addList.size(); i++) {
                    Address add = addList.get(i);
                    mfeatureName = add.getFeatureName();
                    tvLocation.setText(mfeatureName);
                    myLocation.cancelTimer();
                    VolleyGet(add.getLocality());//输入城市名称获取天气
                }
            }
        }
    }
    //查询天气
    //天气
    private void VolleyGet(String cityCode) {
        // 1 创建一个请求队列
        com.android.volley.RequestQueue requestQueue = com.android.volley.toolbox.Volley.newRequestQueue(this);
        // 2 创建一个请求
        String url = "http://wthrcdn.etouch.cn/weather_mini?city="+cityCode;
        MJsonRequest jsonObjectRequest = new MJsonRequest(url, null, new Response.Listener<org.json.JSONObject>() {
            @Override
            public void onResponse(org.json.JSONObject jsonObject) {
                WeatherBean weatherBean = JSON.parseObject(jsonObject.toString(), new TypeReference<WeatherBean>() {});
                String temperature= weatherBean.getData().getWendu();//温度
                String type=weatherBean.getData().getForecast().get(0).getType();//当天天气状况
                tvWeather.setText(type);
                tvTemperature.setText(temperature+"℃");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                  ToastUtil.showShort("获取天气失败");
            }
        });
        // 3 将创建的请求添加到请求队列中
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()){
            case R.id.imageView1:
                if (!"".equals(imageOnePath)){
                    showImageDialog(1);
                }
                break;
            case R.id.imageView2:
                if (!"".equals(imageTwoPath)){
                    showImageDialog(2);
                }
                break;
            case R.id.imageView3:
                if (!"".equals(imageThreePath)){
                    showImageDialog(3);
                }
                break;
        }
        return true;
    }
    //删除Dialog
    public void showImageDialog(final int whitchImage) {
        ConfigButton sure = new ConfigButton() {
            @Override
            public void onConfig(ButtonParams params) {
                params.textColor = 0xFFFC3030;
            }
        };
        CircleDialog.Builder builder = new CircleDialog.Builder(this);
        builder.setTitle("图片操作")
                .setWidth(0.7f)
                .setTitleColor(Color.parseColor("#2db4ff"))//标题字体颜值 0x909090 or Color.parseColor("#909090")
                .setText("确认删除该图片")
                .setTextColor(Color.parseColor("#2db4ff"))
                .setPositive("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        afterdelete(whitchImage);
                      //  getP().deleteChartMood(id);
                    }
                }).configPositive(sure)//配置确定按钮更多的属性
                .setNegative("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .show();
    }
    public void afterdelete(int whitchImage){
        if (!"".equals(imageThreePath)){
            if (whitchImage==1){
                imageOnePath=imageTwoPath;
                imageTwoPath=imageThreePath;
                Glide.with(this).load(imageOnePath).into(imageView1);
                Glide.with(this).load(imageTwoPath).into(imageView2);
            }else if (whitchImage==2){
                imageTwoPath=imageThreePath;
                Glide.with(this).load(imageTwoPath).into(imageView2);
            }else if (whitchImage==3){
            }
            imageThreePath="";
            Glide.with(this).load(R.mipmap.icon_add_picture).into(imageView3);
        }else if (!"".equals(imageTwoPath)){
            if (whitchImage==1){
                imageOnePath=imageTwoPath;
                Glide.with(this).load(imageOnePath).into(imageView1);
                Glide.with(this).load(R.mipmap.icon_add_picture).into(imageView2);
            }else if (whitchImage==2){
                Glide.with(this).load(R.mipmap.icon_add_picture).into(imageView2);
            }
            imageTwoPath="";
            imageView3.setVisibility(View.INVISIBLE);
        }else if (!"".equals(imageOnePath)){
            imageOnePath="";
            Glide.with(this).load(R.mipmap.icon_add_picture).into(imageView1);
            imageView2.setVisibility(View.INVISIBLE);
        }
    }

}

