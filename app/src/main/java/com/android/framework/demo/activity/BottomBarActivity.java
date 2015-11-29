package com.android.framework.demo.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.framework.demo.R;
import com.android.framework.view.BottomBar.BottomBarView;
import com.android.framework.view.BottomBar.BottomButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by meikai on 15/11/29.
 */
public class BottomBarActivity extends AppCompatActivity {

    ViewPager viewPager;
    BottomBarView bottomBarView;
    MainPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_bar);

        viewPager = (ViewPager) findViewById(R.id.content_viewpager);
        bottomBarView = (BottomBarView) findViewById(R.id.bottom_bar);

        adapter = new MainPagerAdapter(getSupportFragmentManager());
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new MyFragment());
        fragmentList.add(new MyFragment());
        fragmentList.add(new MyFragment());
        adapter.setDataList(fragmentList);

        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);

        bottomBarView.setBackgroundColor(Color.parseColor("#fafafa"));
        bottomBarView.addBtn(0, R.drawable.bottom_menu_recruit, "招生");
        bottomBarView.addBtn(1, R.drawable.bottom_menu_teach, "教学");
        bottomBarView.addBtn(2, R.drawable.bottom_menu_mine, "我的");
        bottomBarView.notifyBtnChanged();

        initListeners();
    }


    private int currentPosition = 0;
    private boolean isAlphaChange = true;

    public void initListeners() {
        bottomBarView.setOnBtnClickListener(new BottomBarView.OnBtnClickListener() {
            @Override
            public void onClick(BottomButton arg0) {
                int index = arg0.getIndex();
                viewPager.setCurrentItem(index);
                isAlphaChange = false;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageSelected(int position) {
                bottomBarView.changeLightBtn(position, false);
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e("onPageScrolled", "position=" + position +
                        ", positionOffset=" + positionOffset + ", Pixels =" + positionOffsetPixels);

                if (Math.abs(position - currentPosition) <= 1 && isAlphaChange) {
                    if (positionOffset > 0f) {
                        BottomButton left = bottomBarView.getBtnList().get(position);
                        BottomButton right = bottomBarView.getBtnList().get(position + 1);

                        left.setBtnAlpha(1 - positionOffset);
                        right.setBtnAlpha(positionOffset);
                    }
                }
            }

            public void onPageScrollStateChanged(int arg) {
                if (arg == 2) {//滑动完毕
                    isAlphaChange = true;
                    currentPosition = viewPager.getCurrentItem();
                }
            }
        });
    }

    public class MainPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> dataList = new ArrayList<>();

        public MainPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return dataList.get(position);
        }

        @Override
        public int getCount() {
            return dataList.size();
        }

        public List<Fragment> getDataList() {
            return dataList;
        }

        public void setDataList(List<Fragment> dataList) {
            this.dataList = dataList;
        }

    }

    public class MyFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            return LayoutInflater.from(BottomBarActivity.this).inflate(R.layout.fragment_bottombar, null);

        }

    }


}
