package com.example.sharemood.square.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sharemood.R;
import com.example.sharemood.bean.CommentBean;
import com.example.sharemood.bean.ReplyCommentBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.kit.KnifeKit;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by acer on 2018/12/15.
 */

public class MainCommentAdapter extends BaseExpandableListAdapter {
    private static final String TAG = "CommentExpandAdapter";
    private List<CommentBean> commentBeanList;
    private Context context;

    public MainCommentAdapter(Context context, List<CommentBean> commentBeanList) {
        this.context = context;
        this.commentBeanList = commentBeanList;
    }

    @Override
    public int getGroupCount() {
        return commentBeanList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (commentBeanList.get(groupPosition).getReplyList() == null) {
            return 0;
        } else if (commentBeanList.get(groupPosition).getReplyList().size() > 3) {
            return 3;
        } else {
            return commentBeanList.get(groupPosition).getReplyList().size() > 0 ? commentBeanList.get(groupPosition).getReplyList().size() : 0;
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return commentBeanList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return commentBeanList.get(groupPosition).getReplyList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return getCombinedChildId(groupPosition, childPosition);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final GroupHolder groupHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.comment_item, parent, false);
            groupHolder = new GroupHolder(convertView);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        if (commentBeanList.get(groupPosition).getUser() != null) {
            if (commentBeanList.get(groupPosition).getUser().getImagePath() != null) {
                Glide.with(context).load(commentBeanList.get(groupPosition).getUser().getImagePath()).into(groupHolder.ivPortrait);
            }
            groupHolder.tvNickname.setText(commentBeanList.get(groupPosition).getUser().getNickName());
        }
        groupHolder.tvDate.setText(commentBeanList.get(groupPosition).getDate());
        groupHolder.tvComment.setText(commentBeanList.get(groupPosition).getContent());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildHolder childHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.comment_reply_item_layout, parent, false);
            childHolder = new ChildHolder(convertView);
            convertView.setTag(childHolder);
        } else {
            childHolder = (ChildHolder) convertView.getTag();
        }
        if (commentBeanList.get(groupPosition).getReplyList().get(childPosition).getMyUserBean()!=null){
            childHolder.replyItemUser.setText(commentBeanList.get(groupPosition).getReplyList().get(childPosition).getMyUserBean().getNickName());
        }
        if (commentBeanList.get(groupPosition).getReplyList().get(childPosition).isSecond()){
               String otherName=commentBeanList.get(groupPosition).getReplyList().get(childPosition).getOtherNickname();
                String content="回复@"+otherName+":"+commentBeanList.get(groupPosition).getReplyList().get(childPosition).getContent();
               //改变昵称颜色
            SpannableStringBuilder style = new SpannableStringBuilder(content);
            style.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.main_color)),content.indexOf("@"),content.lastIndexOf(":"), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            childHolder.replyItemContent.setText(style);
        }else {
            childHolder.replyItemContent.setText(":"+commentBeanList.get(groupPosition).getReplyList().get(childPosition).getContent());
        }
        if (commentBeanList.get(groupPosition).getReplyList().size() > 3) {
            if (childPosition == 2) {
               childHolder.tvSeeMore.setVisibility(View.VISIBLE);
            }else {
                childHolder.tvSeeMore.setVisibility(View.GONE);
            }
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public class GroupHolder {
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
        @BindView(R.id.layout_item)
        ConstraintLayout layoutItem;

        public GroupHolder(View view) {
            KnifeKit.bind(this, view);
        }
    }

    public class ChildHolder {
        @BindView(R.id.reply_item_user)
        TextView replyItemUser;
        @BindView(R.id.reply_item_content)
        TextView replyItemContent;
        @BindView(R.id.tv_see_more)
        TextView tvSeeMore;
        public ChildHolder(View view) {
            KnifeKit.bind(this, view);
        }
    }

    /**
     * by moos on 2018/12/16
     * func:评论成功后插入一条数据
     *
     * @param commentBean 新的评论数据
     */
    public void addTheCommentData(CommentBean commentBean) {
        if (commentBean != null) {
            commentBeanList.add(commentBean);
            notifyDataSetChanged();
        } else {
            throw new IllegalArgumentException("评论数据为空!");
        }
    }

    /**
     * by moos on 2018/12/16
     * func:评论删除后移除一条数据
     */
    public void removeTheCommentData(int postion) {
        commentBeanList.remove(postion);
        notifyDataSetChanged();
//        if (commentBean != null) {
//            commentBeanList.add(commentBean);
//            notifyDataSetChanged();
//        } else {
//            throw new IllegalArgumentException("评论数据为空!");
//        }

    }

    /**
     * by moos on 2018/12/16
     * func:回复成功后插入一条数据
     *
     * @param replyCommentBean 新的回复数据
     */

    public void addTheReplyData(ReplyCommentBean replyCommentBean, int groupPosition) {
        if (replyCommentBean != null) {
            Log.e(TAG, "addTheReplyData: >>>>该刷新回复列表了:" + replyCommentBean.toString());
            if (commentBeanList.get(groupPosition).getReplyList() != null) {
                commentBeanList.get(groupPosition).getReplyList().add(replyCommentBean);
            } else {
                List<ReplyCommentBean> replyList = new ArrayList<>();
                replyList.add(replyCommentBean);
                commentBeanList.get(groupPosition).setReplyList(replyList);
            }
            notifyDataSetChanged();
        } else {
            throw new IllegalArgumentException("回复数据为空!");
        }

    }
}
