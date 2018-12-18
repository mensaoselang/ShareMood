package com.example.sharemood.ui.index.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sharemood.R;
import com.example.sharemood.bean.MoodTypeBean;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * Created by acer on 2018/11/27.
 */

public class RvTitleAdapter extends SimpleRecAdapter<MoodTypeBean, RvTitleAdapter.ViewHolder> {

    private int selectedPosition = 0;

    public RvTitleAdapter(Context context) {
        super(context);
    }


    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.square_titlelist_item;
    }

    //把列表的点击position,传递过来
    public void clearSelection(int position) {
        selectedPosition = position;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvTitle.setText(data.get(position).getType());
        //使用view的post方式,用于在activity的oncreate方法中获取它的宽度和高度
        holder.tvTitle.post(new Runnable() {
            @Override
            public void run() {
                //获取要textview的宽度
                holder.tvTitle.measure(0, 0);
                int width = holder.tvTitle.getWidth();
                //获取线的layout参数
                ViewGroup.LayoutParams lineParams = holder.viewLine.getLayoutParams();
                //将textview的宽度  设置给  线的
                lineParams.width = width;
                holder.viewLine.setLayoutParams(lineParams);
            }
        });

         holder.llItem.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 onClick.OnItemClick(position,data.get(position).getId());
             }
         });
        if (selectedPosition == position) {
            holder.tvTitle.setTextColor(Color.parseColor("#3982FD"));
            holder.viewLine.setVisibility(View.VISIBLE);
        } else {
            holder.tvTitle.setTextColor(Color.parseColor("#333333"));
            holder.viewLine.setVisibility(View.INVISIBLE);
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.view_line)
        View viewLine;
        @BindView(R.id.ll_item)
        LinearLayout llItem;
        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }

    private ItemOnClick onClick;

    public void SetOnClick(ItemOnClick onClick) {
        this.onClick = onClick;
    }

    public interface ItemOnClick {
        void OnItemClick(int position,int id);
    }
}
