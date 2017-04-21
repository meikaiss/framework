package com.android.framework.demo.activity;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.framework.base.BaseCompactActivity;
import com.android.framework.customview.viewgroup.GalleryRecyclerView;
import com.android.framework.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by meikai on 17/4/20.
 */
public class GalleryRecyclerViewActivity extends BaseCompactActivity {


    private List<Item> dataList = new ArrayList<>();

    private TextView mPosition;
    private GalleryRecyclerView mGalleryRecyclerView;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_gallery_recycler_view;
    }

    @Override
    public void findViews() {

        mPosition = f(R.id.position);
        mGalleryRecyclerView = f(R.id.gallery);

        mGalleryRecyclerView.setCanAlpha(true);
        mGalleryRecyclerView.setCanScale(true);
        mGalleryRecyclerView.setBaseScale(0.6f);
        mGalleryRecyclerView.setBaseAlpha(0.5f);

    }

    @Override
    public void setListeners() {
        findViewById(R.id.btn_set_pos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGalleryRecyclerView.setSelectPosition(10);
            }
        });
    }

    @Override
    public void parseBundle(Intent intent) {

        for (int i = 0; i < 30; i++) {
            Item item = new Item();
            item.name = "麦克" + i;
            item.img = R.drawable.avatar;
            item.position = i * 3 + i;

            dataList.add(item);
        }

    }

    @Override
    public void afterView() {
        MyAdapter adapter = new MyAdapter(dataList);
        adapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mGalleryRecyclerView.setSelectPosition(position);
            }
        });
        mGalleryRecyclerView.setAdapter(adapter);

    }

    public static class MyAdapter extends RecyclerView.Adapter {

        private List<Item> dataList = new ArrayList<>();
        private AdapterView.OnItemClickListener onItemClickListener;

        public MyAdapter(List<Item> dataList) {
            this.dataList = dataList;
        }

        public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((MyViewHolder) holder).imgAvatar.setImageResource(dataList.get(position).img);
            ((MyViewHolder) holder).tvName.setText(dataList.get(position).name);

            if (onItemClickListener != null){
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(null, holder.itemView, position, position);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imgAvatar;
        TextView tvName;

        public MyViewHolder(View itemView) {
            super(itemView);
            imgAvatar = (ImageView) itemView.findViewById(R.id.profile_image);
            tvName = (TextView) itemView.findViewById(R.id.name);
        }

    }

    public static class Item {
        public int position;
        public int img;
        public String name;
        public boolean isSelected;
    }

}
