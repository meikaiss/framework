package com.android.framework.demo.activity.viewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.android.framework.demo.R;
import com.android.framework.customview.view.DragScoreView;

/**
 * Created by meikai on 16/3/11.
 */
public class DragScoreViewActivity extends AppCompatActivity {

    private DragScoreView dragScoreView;
    private TextView tvScore;

    private DragScoreView dragScoreView_2;
    private TextView tvScore_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_score_view);

        dragScoreView = (DragScoreView) findViewById(R.id.drag_score_view);
        dragScoreView_2 = (DragScoreView) findViewById(R.id.drag_score_view_2);
        tvScore = (TextView) findViewById(R.id.tv_score);
        tvScore_2 = (TextView) findViewById(R.id.tv_score_2);

        dragScoreView.setOnScoreChangedListener(new DragScoreView.OnScoreChangedListener() {
            @Override
            public void onSelected(int score) {
                tvScore.setText(score + "");
            }
        });

        dragScoreView_2.setOnScoreChangedListener(new DragScoreView.OnScoreChangedListener() {
            @Override
            public void onSelected(int score) {
                tvScore_2.setText(score + "");
            }
        });

    }

}
