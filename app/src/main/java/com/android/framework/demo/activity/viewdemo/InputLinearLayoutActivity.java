package com.android.framework.demo.activity.viewdemo;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.android.framework.demo.R;
import com.android.framework.viewgroup.InputLinearLayout;

/**
 * Created by meikai on 15/12/8.
 */
public class InputLinearLayoutActivity extends AppCompatActivity {

//    在清单文件Manifest.xml中的对应的Activity标签内设置
//    android:windowSoftInputMode=”adjustResize”
//    作用是当软键盘显示或隐藏时，该Activity主窗口总是会被调整大小以便留出软键盘的空间。唯有这样才能保证布局触发onSizeChanged()方法。

    private InputLinearLayout inputLinearLayout;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_layout);

        inputLinearLayout = (InputLinearLayout) findViewById(R.id.root_input_layout);
        imageView = (ImageView) findViewById(R.id.avater_img);

        inputLinearLayout.setiOnKeyboardStateChangedListener(new InputLinearLayout.IOnKeyboardStateChangedListener() {
            @Override
            public void onKeyboardStateChanged(boolean state) {
                Log.e("onKeyboardStateChanged", "state=" + state);

                if(state == true){
                    AnimatorSet set = new AnimatorSet();
                    ObjectAnimator anim = ObjectAnimator .ofFloat(imageView, "scaleX", 0f, 1f).setDuration(300);
                    ObjectAnimator anim2 = ObjectAnimator .ofFloat(imageView, "scaleY", 0f, 1f).setDuration(300);
                    set.play(anim).with(anim2);
                    set.start();
                }else{
                    AnimatorSet set = new AnimatorSet();
                    ObjectAnimator anim = ObjectAnimator .ofFloat(imageView, "scaleX", 1f, 0f).setDuration(300);
                    ObjectAnimator anim2 = ObjectAnimator .ofFloat(imageView, "scaleY", 1f, 0f).setDuration(300);
                    set.play(anim).with(anim2);
                    set.start();
                }

            }
        });
    }
}
