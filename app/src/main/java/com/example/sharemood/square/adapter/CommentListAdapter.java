package com.example.sharemood.square.adapter;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sharemood.R;
import com.example.sharemood.bean.ReplyCommentBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by acer on 2018/12/16.
 */

public class CommentListAdapter extends BaseAdapter {
    private Context context;
    private List<ReplyCommentBean> list;
    public CommentListAdapter(Context context, List<ReplyCommentBean> list) {
        this.context = context;
        this.list = list;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HolderView holderView = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.comment_replayitem_layout, null);
            holderView = new HolderView();
          holderView.userName=convertView.findViewById(R.id.comment_item_userName);
          holderView.content=convertView.findViewById(R.id.comment_item_content);
          holderView.circleImageView=convertView.findViewById(R.id.comment_item_logo);
            convertView.setTag(holderView);
        } else {
            holderView = (HolderView) convertView.getTag();
        }
        if (list.get(position).isSecond()){
            String content="回复@"+list.get(position).getOtherNickname()+":"+list.get(position).getContent();
            //改变昵称颜色
            SpannableStringBuilder style = new SpannableStringBuilder(content);
            style.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.main_color)),content.indexOf("@"),content.lastIndexOf(":"), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holderView.content.setText(style);
        }else {
            holderView.content.setText(list.get(position).getContent());
        }
        if (list.get(position).getMyUserBean()!=null){
            holderView.userName.setText(list.get(position).getMyUserBean().getNickName());
            if (list.get(position).getMyUserBean().getImagePath()!=null){
                Glide.with(context).load(list.get(position).getMyUserBean().getImagePath()).into(holderView.circleImageView);
            }
        }
        return convertView;
    }
    class HolderView {
        TextView content;
        TextView userName;
        CircleImageView circleImageView;
    }
}
