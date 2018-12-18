package com.example.sharemood.ui.index.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sharemood.R;
import com.example.sharemood.bean.DiaryShareBean;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by acer on 2018/11/27.
 */

public class XrContentAdapter extends SimpleRecAdapter<DiaryShareBean, XrContentAdapter.ViewHolder> {


    public XrContentAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.square_contentlist_item;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvContent.setText(data.get(position).getContent());
        holder.ivHeartTemperature.setText(data.get(position).getMoodIndex());
        holder.ivHeartColor.setBackgroundColor(Color.parseColor(data.get(position).getMoodColor()));
        holder.tvWeather.setText(data.get(position).getWeather());
        holder.tvWeatherWendu.setText(data.get(position).getTemperature());
        holder.tvNickname.setText(data.get(position).getNickName());
        if (data.get(position).getObjectLike()!=null){
            holder.tvFocusonNumber.setText(data.get(position).getObjectLike().size()+"");
        }else {
            holder.tvFocusonNumber.setText("0");
        }

        if (data.get(position).getImageFiles() != null) {
            if (data.get(position).getImageFiles().size() > 0) {
                holder.ivShow.setVisibility(View.VISIBLE);
                Glide.with(context).load(data.get(position).getImageFiles().get(0).getUrl()).into(holder.ivShow);
            }
        } else {
            holder.ivShow.setVisibility(View.GONE);
        }
        if (data.get(position).getMyUserBean() != null) {
            if (data.get(position).getMyUserBean().getImagePath() != null) {
                Glide.with(context).load(data.get(position).getMyUserBean().getImagePath()).into(holder.ivHeadPortrait);
            }
            holder.tvNickname.setText(data.get(position).getMyUserBean().getNickName());
        }
        holder.constrainlayoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCustomClick.OnItemClick(position);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_show)
        ImageView ivShow;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.iv_heart)
        CircleImageView ivHeart;
        @BindView(R.id.iv_heart_temperature)
        TextView ivHeartTemperature;
        @BindView(R.id.iv_heart_color)
        ImageView ivHeartColor;
        @BindView(R.id.tv_color_digital)
        TextView tvColorDigital;
        @BindView(R.id.tv_weather_wendu)
        TextView tvWeatherWendu;
        @BindView(R.id.tv_weather)
        TextView tvWeather;
        @BindView(R.id.iv_weather)
        ImageView ivWeather;
        @BindView(R.id.iv_head_portrait)
        CircleImageView ivHeadPortrait;
        @BindView(R.id.tv_nickname)
        TextView tvNickname;
        @BindView(R.id.constrainlayout_item)
        ConstraintLayout constrainlayoutItem;
        @BindView(R.id.iv_focuson)
        ImageView ivFocuson;
        @BindView(R.id.tv_focuson_number)
        TextView tvFocusonNumber;
        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }

    private ItemOnClick onCustomClick;

    public void setCustomOnClick(ItemOnClick onCustomClick) {
        this.onCustomClick = onCustomClick;
    }

    public interface ItemOnClick {
        void OnItemClick(int position);
    }
}
