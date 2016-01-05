package com.android.framework.demo.activity.nolib;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.framework.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by meikai on 15/12/16.
 */
public class AppBarLayoutActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private List<String> mDatas;

    private Toolbar toolbar;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_bar);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.bottom_menu_mine);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("开发"));
        tabLayout.addTab(tabLayout.newTab().setText("测试"));
        tabLayout.addTab(tabLayout.newTab().setText("产品"));

        recyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDatas = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            mDatas.add(i + "");
        }


        recyclerView.setAdapter(new HomeAdapter());

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AppBarLayoutActivity.this, "ToolBar左上角导航按钮被点击了", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        menu.findItem(R.id.action_user).setActionView(R.layout.tool_bar_menu_action_view);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_user:
                Toast.makeText(this, "ToolBar用户被点击", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_home:
                Toast.makeText(this, "ToolBar主页被点击", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_teach:
                Toast.makeText(this, "ToolBar详细被点击", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    AppBarLayoutActivity.this).inflate(R.layout.item_coordinator_recylerview, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tv.setText(mDatas.get(position));
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tv;

            public MyViewHolder(View view) {
                super(view);
                tv = (TextView) view.findViewById(R.id.id_num);
            }
        }
    }


}