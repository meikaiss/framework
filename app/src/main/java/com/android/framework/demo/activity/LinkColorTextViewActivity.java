package com.android.framework.demo.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.framework.base.BaseCompactActivity;
import com.android.framework.demo.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by meikai on 2019/12/30.
 */
public class LinkColorTextViewActivity extends BaseCompactActivity {

    RecyclerView recyclerView;

    RVAdapter adapter;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.text_link_color_activity;
    }

    @Override
    public void findViews() {
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        adapter = new RVAdapter();

        for (int i = 0; i < 100; i++) {
            adapter.dataList.add("http://img.zcool.cn/community/01cb3e5ddb3705a8012129e2923986.jpg@1280w_1l_2o_100sh.jpg");
        }
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void setListeners() {

    }

    @Override
    public void parseBundle(Intent intent) {

    }

    @Override
    public void afterView() {

    }


    public static class RVAdapter extends RecyclerView.Adapter<VH> {

        public List<String> dataList = new ArrayList<>();

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_glide_image, viewGroup, false);

            VH vh = new VH(itemView);

            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull VH viewHolder, int i) {

            Glide.with(viewHolder.itemView.getContext())
                    .load("https://img3.qjy168.com/provide/2013/08/10/4322132_20130810171010.jpg").into(viewHolder.item_img);

        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

    }

    public static class VH extends RecyclerView.ViewHolder {

        ImageView item_img;

        public VH(@NonNull View itemView) {
            super(itemView);
            item_img = itemView.findViewById(R.id.item_img);
        }
    }

}
