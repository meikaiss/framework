package com.android.framework.demo.activity.viewdemo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.framework.base.BaseCompactActivity;
import com.android.framework.customview.viewgroup.pinnedheaderlistview.PinnedHeaderListView;
import com.android.framework.customview.viewgroup.pinnedheaderlistview.SectionedBaseAdapter;
import com.android.framework.demo.R;

/**
 * Created by meikai on 16/5/5.
 */
public class PinnedHeaderListViewActivity extends BaseCompactActivity {

    PinnedHeaderListView pinnedHeaderListView;

    TestSectionedAdapter adapter;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_pinned_header_list_view;
    }

    @Override
    public void findViews() {

        pinnedHeaderListView = (PinnedHeaderListView) findViewById(R.id.pinned_header_list_view);

        adapter = new TestSectionedAdapter();

        pinnedHeaderListView.setAdapter(adapter);
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


    public class TestSectionedAdapter extends SectionedBaseAdapter {

        @Override
        public Object getItem(int section, int position) {
            return null;
        }

        @Override
        public long getItemId(int section, int position) {
            return 0;
        }

        @Override
        public int getSectionCount() {
            return 7;
        }

        @Override
        public int getCountForSection(int section) {
            return 15;
        }

        @Override
        public View getItemView(int section, int position, View convertView, ViewGroup parent) {
            LinearLayout layout = null;
            if (convertView == null) {
                LayoutInflater inflator = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                layout = (LinearLayout) inflator.inflate(R.layout.list_item, null);
            } else {
                layout = (LinearLayout) convertView;
            }
            ((TextView) layout.findViewById(R.id.textItem)).setText("Section " + section + " DragItem " + position);
            return layout;
        }

        @Override
        public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
            LinearLayout layout = null;
            if (convertView == null) {
                LayoutInflater inflator = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                layout = (LinearLayout) inflator.inflate(R.layout.header_item, null);
            } else {
                layout = (LinearLayout) convertView;
            }
            ((TextView) layout.findViewById(R.id.textItem)).setText("Header for section " + section);
            return layout;
        }

    }
}
