package com.android.framework.demo.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.framework.base.BaseCompactActivity;
import com.android.framework.demo.R;

/**
 *  经测试，在 Android 的 API 21 ( Android 5.0 ) 以下，Crash 会直接退出应用，但是在 API 21 ( Android 5.0 ) 以上，系统会遵循以下原则进行重启：
 * <p>
 * 包含 Service，如果应用 Crash 的时候，运行着Service，那么系统会重新启动 Service。
 * 不包含 Service，只有一个 Activity，那么系统不会重新启动该 Activity。
 * 不包含 Service，但当前堆栈中存在两个 Activity：Act1 -> Act2，如果 Act2 发生了 Crash ，那么系统会重启 Act1。
 * 不包含 Service，但是当前堆栈中存在三个 Activity：Act1 -> Act2 -> Act3，如果 Act3 崩溃，那么系统会重启 Act2，并且 Act1 依然存在，即可以从重启的 Act2 回到 Act1。
 * <p>
 * Created by meikai on 2019/12/13.
 */
public class FragmentRepeatActivity extends BaseCompactActivity {

    private TestFragment fragment;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_fragment_repeate;
    }

    @Override
    public void findViews() {

        findViewById(R.id.btn_crash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = null;
                if (1 == 1) {
                    s = null;
                }
                Log.e("", s.length() + "");
            }
        });

        findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("", "检测");
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

        if (fragment == null) {

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            //所以，transaction不能使用add，改为使用replace。 防止崩溃重启及 内存不够回收进程后重启时 fragment add 两次
            fragment = new TestFragment();
            transaction.replace(R.id.frame_layout, fragment, "mk1");


            fragment = new TestFragment();
            transaction.replace(R.id.frame_layout, fragment, "mk2");

            transaction.commitNowAllowingStateLoss();
        }

    }


    public static class TestFragment extends Fragment {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            TextView textView = new TextView(container.getContext());

            textView.setBackgroundColor(Color.BLACK);
            textView.setTextColor(Color.RED);

            String test = "";

            for (int i = 0; i < 30; i++) {
                test += (int) (Math.random() * 10);
            }

            textView.setText(test);

            return textView;
        }
    }
}

