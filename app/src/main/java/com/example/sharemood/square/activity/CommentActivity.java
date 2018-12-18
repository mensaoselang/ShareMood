package com.example.sharemood.square.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sharemood.R;
import com.example.sharemood.base.BaseActivity;
import com.example.sharemood.bean.CommentBean;
import com.example.sharemood.bean.ReplyCommentBean;
import com.example.sharemood.square.adapter.CommentListAdapter;
import com.example.sharemood.square.presenter.CommentPresenter;
import com.example.sharemood.ui.login.Bean.MyUserBean;
import com.example.sharemood.utils.ToastUtil;
import com.example.sharemood.view.BackLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import de.hdodenhof.circleimageview.CircleImageView;

public class CommentActivity extends BaseActivity<CommentPresenter> implements View.OnClickListener {
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_save)
    TextView tvToolbarSave;
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
    @BindView(R.id.comment_list)
    ListView commentList;
    CommentBean commentBean;
    @BindView(R.id.et_input_comment)
    EditText etInputComment;
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.ll_toolbar_left)
    BackLinearLayout llToolbarLeft;
    private CommentListAdapter commentListAdapter;
    private boolean second = false;
    private String otherName = "";
    MyUserBean userBean = BmobUser.getCurrentUser(MyUserBean.class);
    ReplyCommentBean replyCommentBean;
    private boolean hasChange=false;
    @Override
    public void initData(Bundle savedInstanceState) {

        tvToolbarSave.setVisibility(View.INVISIBLE);
        commentBean = (CommentBean) getIntent().getSerializableExtra("commentBean");
        initView();
//        etInputComment.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if(!TextUtils.isEmpty(charSequence) && charSequence.length()>2){
//                   // bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));
//                }else {
//                    //bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
    }

    public void initView() {
        if (commentBean != null) {
            if (commentBean.getUser().getImagePath() != null) {
                Glide.with(this).load(commentBean.getUser().getImagePath()).into(ivPortrait);
            }
            tvNickname.setText(commentBean.getUser().getNickName());
            tvComment.setText(commentBean.getContent());
            tvDate.setText(commentBean.getDate());
        }
        commentListAdapter = new CommentListAdapter(this, commentBean.getReplyList());
        commentList.setAdapter(commentListAdapter);
        commentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (userBean.getObjectId().equals(commentBean.getReplyList().get(position).getMyUserBean().getObjectId())) {
                    showPopupWindow(true);
                } else {
                    showPopupWindow(false);
                }
                /**
                 * 隐藏软键盘
                 */
                InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                mInputMethodManager.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), 0);
                replyCommentBean = commentBean.getReplyList().get(position);//获取对应replyBena
                otherName = commentBean.getReplyList().get(position).getMyUserBean().getNickName();
            }
        });

        //解决Scroview嵌套ListView只显示一个item的高度的问题
        int totalHeight = 0;
        for (int i = 0, len = commentListAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = commentListAdapter.getView(i, null, commentList);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = commentList.getLayoutParams();
        params.height = totalHeight + (commentList.getDividerHeight() * (commentListAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        commentList.setLayoutParams(params);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_comment;
    }

    @Override
    public CommentPresenter newP() {
        return new CommentPresenter();
    }

    @OnClick({R.id.tv_send,R.id.ll_toolbar_left})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.tv_send:
                if (!TextUtils.isEmpty(etInputComment.getText().toString())) {
                    getP().replyComment(commentBean, etInputComment.getText().toString(), second, otherName);
                } else {
                    ToastUtil.showShort("内容不能为空");
                }
                break;
            case R.id.ll_toolbar_left:
                 finish();
                break;
        }
    }

    public void replySucceed(CommentBean commentBean) {
        hasChange=true;
        CommentActivity.this.setResult(2);
        second = false;
        /**
         * 隐藏软键盘
         */
        InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        etInputComment.setHint("");
        etInputComment.setText("");
        if (commentBean != null) {
            if (commentBean.getReplyList() != null) {
                commentListAdapter = new CommentListAdapter(this, commentBean.getReplyList());
                commentListAdapter.notifyDataSetChanged();
                commentList.setAdapter(commentListAdapter);
                //解决Scroview嵌套ListView只显示一个item的高度的问题
                int totalHeight = 0;
                for (int i = 0, len = commentListAdapter.getCount(); i < len; i++) {
                    // listAdapter.getCount()返回数据项的数目
                    View listItem = commentListAdapter.getView(i, null, commentList);
                    // 计算子项View 的宽高
                    listItem.measure(0, 0);
                    // 统计所有子项的总高度
                    totalHeight += listItem.getMeasuredHeight();
                }

                ViewGroup.LayoutParams params = commentList.getLayoutParams();
                params.height = totalHeight + (commentList.getDividerHeight() * (commentListAdapter.getCount() - 1));
                // listView.getDividerHeight()获取子项间分隔符占用的高度
                // params.height最后得到整个ListView完整显示需要的高度
                commentList.setLayoutParams(params);
            }
        }
    }


    private PopupWindow mPopWindow;

    private void showPopupWindow(boolean isUser) {
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高
        //设置contentView
        View contentView = LayoutInflater.from(context).inflate(R.layout.show_window, null);
        mPopWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setContentView(contentView);
        mPopWindow.setWidth(d.getWidth());
        mPopWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        //设置各个控件的点击响应
        LinearLayout tv1 = (LinearLayout) contentView.findViewById(R.id.photo_roll1);
        LinearLayout tv2 = (LinearLayout) contentView.findViewById(R.id.photo_roll2);
        LinearLayout tv3 = (LinearLayout) contentView.findViewById(R.id.photo_roll3);
        TextView textView1 = (TextView) contentView.findViewById(R.id.textView1);
        TextView textView2 = (TextView) contentView.findViewById(R.id.textView2);
        if (!isUser) {
            tv2.setVisibility(View.GONE);
        }
        tv3.setOnClickListener(this);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        textView1.setText("回复");
        textView2.setText("删除");
        //显示PopupWindow
        View rootview = LayoutInflater.from(context).inflate(R.layout.activity_square_detail, null);
        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
        setBackgroundAlpha(0.5f);
        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1f);
            }
        });
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha 屏幕透明度0.0-1.0 1表示完全不透明
     */
    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = (context).getWindow().getAttributes();
        lp.alpha = bgAlpha;
        (context).getWindow().setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.photo_roll1:
                second = true;
                etInputComment.setHint("回复@" + otherName);
                mPopWindow.dismiss();
                break;
            case R.id.photo_roll2:
                getP().deleteReplay(commentBean, replyCommentBean);
                mPopWindow.dismiss();
                break;
            case R.id.photo_roll3:
                mPopWindow.dismiss();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
