package com.example.sharemood.diary.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sharemood.R;
import com.example.sharemood.bean.MoodTypeBean;

import java.util.List;
/**
 * Created by acer on 2018/9/18.
 */

public class ShareChooseAdapter extends BaseAdapter {
    private Context context;
    private List<MoodTypeBean> list;
    private int selectedPosition=-1;
    public ShareChooseAdapter(Context context, List<MoodTypeBean> list){
        this.context=context;
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    //把Gridview的点击position,传递过来
    public void clearSelection(int position) {
        selectedPosition = position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HolderView holderView = null;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_share_classify,null);
            holderView = new HolderView();
            holderView.tvmood=convertView.findViewById(R.id.tv_mood);
            convertView.setTag(holderView);
        }else {
            holderView = (HolderView) convertView.getTag();
        }

        holderView.tvmood.setText(list.get(position).getType());

        //判断点击了哪个item,然后判断，让他的textview变色
        if(selectedPosition==position){
            holderView.tvmood.setBackgroundResource(R.drawable.text_blue_bg);
           holderView.tvmood.setTextColor(Color.parseColor("#FFFFFF"));
        }else{
            holderView.tvmood.setBackgroundResource(R.drawable.text_roung_blue);
            holderView.tvmood.setTextColor(Color.parseColor("#8E8E8E"));
        }
        return convertView;
    }
    class HolderView{
        TextView tvmood;
    }
    private OnItemClick onItemClick;
    public void SetOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }
    public interface OnItemClick {
        void onClick(int position,int id);
    }
}
