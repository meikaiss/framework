package com.android.framework.demo.activity.design.dragrecycler;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;

import com.android.framework.base.BaseCompactActivity;
import com.android.framework.customview.dragrecyclerview.GridItemTouchCallback;
import com.android.framework.customview.dragrecyclerview.OnRecyclerItemClickListener;
import com.android.framework.demo.R;
import com.android.framework.util.ACache;
import com.android.framework.util.VibratorUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by meikai on 16/5/18.
 */
public class DragRecyclerGridViewActivity extends BaseCompactActivity {

    private List<DragItem> dataList = new ArrayList<>();

    private RecyclerView recyclerView;
    private ItemTouchHelper itemTouchHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView = new RecyclerView(this);
        setContentView(recyclerView);


        ArrayList<DragItem> dragItems = (ArrayList<DragItem>) ACache.get(this).getAsObject("dragItems");
        if (dragItems != null)
            dataList.addAll(dragItems);
        else {
            for (int i = 0; i < 5; i++) {
                dataList.add(new DragItem(i * 8 + 0, "收款", R.drawable.fuse1_color));
                dataList.add(new DragItem(i * 8 + 1, "转账", R.drawable.fuse3_color));
                dataList.add(new DragItem(i * 8 + 2, "余额宝", R.drawable.fuse2_color));
                dataList.add(new DragItem(i * 8 + 3, "手机充值", R.drawable.fuse4_color));
                dataList.add(new DragItem(i * 8 + 4, "医疗", R.drawable.fuse3_color));
                dataList.add(new DragItem(i * 8 + 5, "彩票", R.drawable.fuse1_color));
                dataList.add(new DragItem(i * 8 + 6, "电影", R.drawable.fuse4_color));
                dataList.add(new DragItem(i * 8 + 7, "游戏", R.drawable.fuse2_color));
            }

            dataList.remove(dataList.size() - 1);
            dataList.add(new DragItem(dataList.size(), "更多", R.drawable.avatar));
        }


        DragRecyclerAdapter adapter = new DragRecyclerAdapter(R.layout.item_drag_grid, dataList);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.addItemDecoration(new DividerGridItemDecoration(Color.RED));

        itemTouchHelper = new ItemTouchHelper(new GridItemTouchCallback(adapter).setOnDragListener(new GridItemTouchCallback.OnDragListener() {
            @Override
            public void onFinishDrag() {

                ACache.get(DragRecyclerGridViewActivity.this).put("items", (ArrayList<DragItem>) dataList);
            }
        }));

        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(recyclerView) {
            @Override
            public void onLongClick(RecyclerView.ViewHolder vh) {
                if (vh.getLayoutPosition() != dataList.size() - 1) {
                    itemTouchHelper.startDrag(vh);
                    VibratorUtil.Vibrate(DragRecyclerGridViewActivity.this, 200);   //震动70ms
                }
            }

            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                DragItem item = dataList.get(vh.getLayoutPosition());
                Toast.makeText(DragRecyclerGridViewActivity.this, item.getId() + " " + item.getName(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getContentViewLayoutId() {
        return 0;
    }

    @Override
    public void findViews() {

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


    public class DragItem implements Serializable {
        private int id;
        private String name;
        private int img;

        public DragItem(int id, String name, int img) {
            this.id = id;
            this.name = name;
            this.img = img;
        }

        public DragItem(String name, int img) {
            this.name = name;
            this.img = img;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getImg() {
            return img;
        }

        public void setImg(int img) {
            this.img = img;
        }
    }

}
