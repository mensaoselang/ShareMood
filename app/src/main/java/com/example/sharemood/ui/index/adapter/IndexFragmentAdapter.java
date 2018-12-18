package com.example.sharemood.ui.index.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sharemood.R;
import com.youth.banner.Banner;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by acer on 2018/11/24.
 */

public class IndexFragmentAdapter extends RecyclerView.Adapter {

    private static final int Banner = 0;
    private static final int TEST = 1;
    private static final int TEST2 = 2;
    private static final int TEST3 = 3;


    private int currentType = Banner;
    private LayoutInflater layoutInflater;

    private Context context;
    private List<String> list;

    public IndexFragmentAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //   View view;
//        //根据 viewType 区分不同的 布局
        if (viewType == Banner) {
            //   view =layoutInflater.inflate(R.layout.index_banner,parent,false);
            return new BannerHolder(layoutInflater.inflate(R.layout.index_banner, parent, false));
        } else {
            return new TestHolder(layoutInflater.inflate(R.layout.index_test, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == Banner) {
            BannerHolder bannerHolder = (BannerHolder) holder;
        } else {
           TestHolder testHolder=(TestHolder) holder;
           //testHolder.text.setText(list.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

//    //此函数在调用 RecyclerView.setAdapter 时调用,设置item占几分之几
//    @Override
//    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(recyclerView);
//
//        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
//        if (manager instanceof GridLayoutManager) {
//            GridLayoutManager gridManager = ((GridLayoutManager) manager);
//
//            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//                @Override
//                public int getSpanSize(int position) {
//                    int type = getItemViewType(position);
//                    switch (type) {
//                        case Banner:
//                            return 6;//占几分之一
//                        default:
//                            return 4;
//                    }
//                }
//            });
//        }
//    }

    //设置对应position的item返回对应itemType
    @Override
    public int getItemViewType(int postion) {
        switch (postion) {
            case Banner: //0
                currentType = Banner;
                break;
            case TEST: //1
                currentType = TEST;
                break;
            case TEST2: //2
                currentType = TEST2;
                break;
            case TEST3: //3
                currentType = TEST3;
                break;
        }
        return currentType;
    }

    //0
    public class BannerHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.banner)
        com.youth.banner.Banner banner;

        public BannerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    //1
    public class TestHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text)
        TextView text;
        public TestHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    /*
     *2
     */
    public class Test2Holder extends RecyclerView.ViewHolder {

        public Test2Holder(View itemView) {
            super(itemView);
        }
    }

    //3
    public class Test3Holder extends RecyclerView.ViewHolder {

        public Test3Holder(View itemView) {
            super(itemView);
        }
    }

}
