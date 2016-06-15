package com.android.framework.demo.activity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.framework.base.BaseCompactActivity;
import com.android.framework.demo.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by meikai on 16/6/15.
 */
public class GlideActivity extends BaseCompactActivity {

    ListView listView;

    List<String> urlList;


    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_glide;
    }

    @Override
    public void findViews() {

        listView = (ListView) findViewById(R.id.list_view);

        urlList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            urlList.add("http://inthecheesefactory.com/uploads/source/glidepicasso/cover.jpg");
            urlList.add("http://u7photo.30edu.com/04116064/img/588aa6aa-c8dc-492a-b848-52ce683781a6.GIF");
        }

        GlideAdapter adapter = new GlideAdapter();
        listView.setAdapter(adapter);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
                    Glide.with(GlideActivity.this).resumeRequests();
                }else{
                    Glide.with(GlideActivity.this).pauseRequests();
                }

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

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


    private class GlideAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return urlList.size();
        }

        @Override
        public Object getItem(int position) {
            return urlList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_glide, parent, false);

            Glide.with(parent.getContext())
            .load(urlList.get(position)).centerCrop().into((ImageView) convertView);


            return convertView;
        }
    }



}
