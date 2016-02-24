package com.android.framework.demo.activity.design;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.SwipeDismissBehavior;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.framework.demo.R;

/**
 * Created by mike on 16/2/24.
 */
public class SwipeDismissBehaviorActivity extends AppCompatActivity {


    TextView swipeView;
    Button buttonReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swip_dismiss_behavior);


        swipeView = (TextView) findViewById(R.id.swip);

        final SwipeDismissBehavior<View> swipe = new SwipeDismissBehavior();
        swipe.setSwipeDirection(SwipeDismissBehavior.SWIPE_DIRECTION_START_TO_END);

        swipe.setListener(
                new SwipeDismissBehavior.OnDismissListener() {
                    @Override
                    public void onDismiss(View view) {

                    }

                    @Override
                    public void onDragStateChanged(int state) {
                    }
                });

        CoordinatorLayout.LayoutParams coordinatorParams =
                (CoordinatorLayout.LayoutParams) swipeView.getLayoutParams();

        coordinatorParams.setBehavior(swipe);


        buttonReset = (Button) findViewById(R.id.btn_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ViewCompat.setAlpha(swipeView, 1.0f);
                ViewCompat.offsetLeftAndRight(swipeView, -swipeView.getMeasuredWidth());


            }
        });

    }


}
