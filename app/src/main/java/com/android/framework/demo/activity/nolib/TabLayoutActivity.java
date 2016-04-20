package com.android.framework.demo.activity.nolib;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.framework.base.BaseCompactActivity;
import com.android.framework.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by meikai on 15/12/3.
 */
public class TabLayoutActivity extends BaseCompactActivity {

    TabLayout tabLayout;
    TabFragmentPagerAdapter adapter;
    ViewPager viewPager;


    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_design_tab_layout;
    }

    @Override
    public void findViews() {

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new TabFragment());
        fragmentList.add(new TabFragment());

        List<String> titleList = new ArrayList<>();
        titleList.add("标题1");
        titleList.add("标题2");

        adapter = new TabFragmentPagerAdapter(getSupportFragmentManager(), fragmentList, titleList);

        viewPager.setAdapter(adapter);
        tabLayout.setTabsFromPagerAdapter(adapter);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
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


    private class TabFragmentPagerAdapter extends FragmentPagerAdapter{

        private List<Fragment> fragmentList;
        private List<String> titleList;

        public TabFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> titleList) {
            super(fm);
            this.fragmentList = fragmentList;
            this.titleList = titleList;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

    public static class TabFragment extends Fragment{

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            TextView textView = new TextView(getContext());
            textView.setText("11");

            return textView;
        }
    }

}
