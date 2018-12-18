package com.example.sharemood.square.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sharemood.R;
import com.example.sharemood.bean.CommentBean;
import com.example.sharemood.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by acer on 2018/12/8.
 */

public class CommentAdapter extends SimpleRecAdapter<CommentBean, CommentAdapter.ViewHolder>{
    private int postioN;
    public CommentAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.comment_item;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (data.get(position).getUser() != null) {
            if (data.get(position).getUser().getImagePath() != null) {
                Glide.with(context).load(data.get(position).getUser().getImagePath()).into(holder.ivPortrait);
            }
            holder.tvNickname.setText(data.get(position).getUser().getNickName());
        }
        holder.tvDate.setText(data.get(position).getDate());
        holder.tvComment.setText(data.get(position).getContent());
        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.OnItemClick(position,data.get(position).getObjectId());
            }
        });
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_portrait)
        CircleImageView ivPortrait;
        @BindView(R.id.tv_nickname)
        TextView tvNickname;
        @BindView(R.id.tv_comment)
        TextView tvComment;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.iv_praise)
        ImageView ivPraise;
//        @BindView(R.id.rv_secondary_comment)
//        RecyclerView rvSecondaryComment;
        @BindView(R.id.layout_item)
        ConstraintLayout layoutItem;
        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }

    public ItemOnClick onClick;

    public void SetOnClick(ItemOnClick onClick) {
        this.onClick = onClick;
    }

    public interface ItemOnClick {
        void OnItemClick(int position, String ObjectId);
    }
}
