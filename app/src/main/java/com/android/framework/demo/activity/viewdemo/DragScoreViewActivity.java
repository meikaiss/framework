package com.android.framework.demo.activity.viewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.android.framework.demo.R;
import com.android.framework.libui.view.DragScoreView;

/**
 * Created by meikai on 16/3/11.
 */
public class DragScoreViewActivity extends AppCompatActivity {

    private TextView tvScore;
    private DragScoreView dragScoreView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_score_view);

        dragScoreView = (DragScoreView) findViewById(R.id.drag_score_view);
        tvScore = (TextView) findViewById(R.id.tv_score);

        dragScoreView.setOnScoreChangedListener(new DragScoreView.OnScoreChangedListener() {
            @Override
            public void onSelected(int score) {
                tvScore.setText(score + "");
            }
        });
    }
}
