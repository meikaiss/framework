package com.android.framework.demo.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.framework.customview.viewgroup.WrapHeightViewPager;
import com.android.framework.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by meikai on 2019/03/26.
 */
public class WrapHeightViewPagerActivity extends AppCompatActivity {

    private WrapHeightViewPager wrapHeightViewPager;
    private TestAdapter adapter;
    private List<String> dataList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__wrap_height_view_pager);

        dataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dataList.add("" + i);
        }
        adapter = new TestAdapter(dataList);

        findViewById(R.id.btnStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrapHeightViewPager = findViewById(R.id.wrap_height_view_pager);

                wrapHeightViewPager.initOnGlobalListener();
                wrapHeightViewPager.setOffscreenPageLimit(dataList.size());
                wrapHeightViewPager.setAdapter(adapter);
            }
        });

        findViewById(R.id.btnStart2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.btnStart).callOnClick();
            }
        });
    }

    private static class TestAdapter extends PagerAdapter {

        private List<String> dataList;

        public TestAdapter(List<String> dataList) {
            this.dataList = dataList;
        }

        @Override
        public int getCount() {
            return dataList == null ? 0 : dataList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = LayoutInflater.from(container.getContext()).inflate(
                    R.layout.activity__wrap_height_view_pager_item, container, false);
            TextView textView = view.findViewById(R.id.tvItem);

            String s = "";
            for (int i = 0; i < position; i++) {
                s += "测试测试测试测试测试测试测试测试测试测试";
            }
            textView.setText(s);

            container.addView(view);

            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

            container.removeView((View) object);
        }

    }


}
