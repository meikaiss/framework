package com.android.framework.demo.activity.fragmentT;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.framework.demo.R;
import com.android.framework.util.LogUtil;

/**
 * Created by meikai on 2017/11/07.
 */
public class AFragment extends Fragment {

    Button button;

    public static AFragment newInstance() {

        Bundle args = new Bundle();

        AFragment fragment = new AFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_fragment, container, false);

        button = (Button) view.findViewById(R.id.btn_start_b);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BActivity.class);

                startActivityForResult(intent, -2);
                LogUtil.e("AFragment.startActivityForResult");
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.e("AFragment.onActivityResult, requestCode=" + requestCode);
    }
}
