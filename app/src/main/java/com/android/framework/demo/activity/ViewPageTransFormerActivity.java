package com.android.framework.demo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.framework.customview.transformer.AlphaPageTransformer;
import com.android.framework.customview.transformer.DepthPageTransformer;
import com.android.framework.customview.transformer.NonPageTransformer;
import com.android.framework.customview.transformer.RotateDownPageTransformer;
import com.android.framework.customview.transformer.RotateUpPageTransformer;
import com.android.framework.customview.transformer.RotateYTransformer;
import com.android.framework.customview.transformer.ScaleInTransformer;
import com.android.framework.customview.transformer.ZoomOutPageTransformer;
import com.android.framework.demo.R;

/**
 * Created by meikai on 16/9/7.
 */
public class ViewPageTransFormerActivity extends AppCompatActivity {


    private ViewPager mViewPager;
    private PagerAdapter mAdapter;

    int[] imgRes = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d,
            R.drawable.e, R.drawable.f, R.drawable.g, R.drawable.h, R.drawable.i};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_page_trans_form);
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager_1);
        mViewPager.setPageMargin(40);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(mAdapter = new PagerAdapter() {
            @Override
            public Object instantiateItem(ViewGroup container, int position) {

                if (position == imgRes.length / 2) {
                    View view = LayoutInflater.from(container.getContext()).inflate(R.layout.page_item, null);
                    view.findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(v.getContext(), "测试", Toast.LENGTH_SHORT).show();
                        }
                    });
                    container.addView(view);
                    return view;
                } else {
                    ImageView view = new ImageView(ViewPageTransFormerActivity.this);
                    view.setScaleType(ImageView.ScaleType.FIT_XY);
                    view.setImageResource(imgRes[position < imgRes.length / 2 ? position : position - 1]);
                    container.addView(view);
                    return view;
                }
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

            @Override
            public int getCount() {
                return imgRes.length + 1;
            }

            @Override
            public boolean isViewFromObject(View view, Object o) {
                return view == o;
            }
        });
        mViewPager.setPageTransformer(true, new AlphaPageTransformer());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        String[] effects = this.getResources().getStringArray(R.array.page_trans_menu);
        for (String effect : effects)
            menu.add(effect);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String title = item.getTitle().toString();
        mViewPager.setAdapter(mAdapter);

        if ("RotateDown".equals(title)) {
            mViewPager.setPageTransformer(true, new RotateDownPageTransformer());
        } else if ("RotateUp".equals(title)) {
            mViewPager.setPageTransformer(true, new RotateUpPageTransformer());
        } else if ("RotateY".equals(title)) {
            mViewPager.setPageTransformer(true, new RotateYTransformer(45));
        } else if ("Standard".equals(title)) {
            mViewPager.setPageTransformer(true, NonPageTransformer.INSTANCE);
        } else if ("Alpha".equals(title)) {
            mViewPager.setPageTransformer(true, new AlphaPageTransformer());
        } else if ("ScaleIn".equals(title)) {
            mViewPager.setPageTransformer(true, new ScaleInTransformer());
        } else if ("RotateDown and Alpha".equals(title)) {
            mViewPager.setPageTransformer(true, new RotateDownPageTransformer(new AlphaPageTransformer()));
        } else if ("RotateDown and Alpha And ScaleIn".equals(title)) {
            mViewPager.setPageTransformer(true, new RotateDownPageTransformer(new AlphaPageTransformer(new ScaleInTransformer())));
        } else if ("Depth".equals(title)) {
            mViewPager.setPageTransformer(true, new DepthPageTransformer());
        } else if ("ZoomOut".equals(title)) {
            mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        }

        setTitle(title);

        return true;
    }
}
