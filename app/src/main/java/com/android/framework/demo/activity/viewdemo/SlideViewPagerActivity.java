package com.android.framework.demo.activity.viewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.framework.demo.R;
import com.android.framework.viewgroup.SlideViewPager;

/**
 * Created by meikai on 15/12/7.
 */
public class SlideViewPagerActivity extends AppCompatActivity {

    SlideViewPager slideViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_view_pager);

        slideViewPager = (SlideViewPager) findViewById(R.id.slide_view_pager);
        slideViewPager.setOnPagerChangeListener(new SlideViewPager.OnPagerChangeListener() {
            @Override
            public void onPageSelected(int position) {
                Log.e("onPageSelected", "onPageSelected  pos="+position);
            }

            @Override
            public void onPageScrollStateChanged(SlideViewPager.ScrollState scrollState) {
                Log.e("onStateChanged", "onPageScrollStateChanged  state="+scrollState);
            }
        });

    }
}
