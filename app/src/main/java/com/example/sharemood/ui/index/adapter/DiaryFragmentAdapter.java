package com.example.sharemood.ui.index.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sharemood.R;
import com.example.sharemood.diary.bean.DiarySQLBean;

import java.util.Objects;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * Created by acer on 2018/11/24.
 */

public class DiaryFragmentAdapter extends SimpleRecAdapter<DiarySQLBean, DiaryFragmentAdapter.ViewHolder> {


    public DiaryFragmentAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.diary_recycle_item;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvContent.setText(data.get(position).getContent());
        Glide.with(context).load(data.get(position).getImagePathList().get(0)).into(holder.imageView1);
        Glide.with(context).load(data.get(position).getImagePathList().get(1)).into(holder.imageView2);
        Glide.with(context).load(data.get(position).getImagePathList().get(2)).into(holder.imageView3);
        holder.tvDate.setText(data.get(position).getDateNumber());
        holder.tvMonth.setText(data.get(position).getMonth()+"月");
        holder.tvWeek.setText("/"+data.get(position).getDayOfWeek());
        holder.tvHeartMath.setText("心情指数:"+data.get(position).getMoodIndex());
        holder.ivHeartColor.setBackgroundColor(Color.parseColor(data.get(position).getMoodColor()));
        holder.tvWeather.setText(data.get(position).getWeather());
        holder.tvTime.setText(data.get(position).getTime());
        holder.tvLocation.setText(data.get(position).getLocation());

        if ("".equals(data.get(position).getImagePathList().get(0))){
            holder.imageView1.setVisibility(View.GONE);
            holder.imageView2.setVisibility(View.GONE);
            holder.imageView3.setVisibility(View.GONE);
        }else {
            holder.imageView1.setVisibility(View.VISIBLE);
            holder.imageView2.setVisibility(View.VISIBLE);
            holder.imageView3.setVisibility(View.VISIBLE);
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.OnItemClick(data.get(position).getId());
            }
        });
        //如果是最后一个item就把END显示出来
        if (position == data.size() - 1) {
            holder.tvEnd.setVisibility(View.VISIBLE);
        }else {
            holder.tvEnd.setVisibility(View.GONE);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_month)
        TextView tvMonth;
        @BindView(R.id.tv_week)
        TextView tvWeek;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.imageView1)
        ImageView imageView1;
        @BindView(R.id.imageView2)
        ImageView imageView2;
        @BindView(R.id.imageView3)
        ImageView imageView3;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.iv_time)
        ImageView ivTime;
        @BindView(R.id.tv_weather)
        TextView tvWeather;
        @BindView(R.id.iv_location)
        ImageView ivLocation;
        @BindView(R.id.tv_location)
        TextView tvLocation;
        @BindView(R.id.tv_heart_math)
        TextView tvHeartMath;
        @BindView(R.id.imageView7)
        ImageView imageView7;
        @BindView(R.id.iv_heart_color)
        ImageView ivHeartColor;
        @BindView(R.id.tv_heart_colormath)
        TextView tvHeartColormath;
        @BindView(R.id.tv_end)
        TextView tvEnd;
        @BindView(R.id.card_view)
        CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            //ButterKnife.bind(this, itemView);
            KnifeKit.bind(this, itemView);
        }
    }

    private ItemOnClick onClick;

    public void SetOnClick(ItemOnClick onClick) {
        this.onClick = onClick;
    }

    public interface ItemOnClick {
        void OnItemClick(long id);
    }
}
