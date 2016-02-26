package com.android.framework.demo.activity.design;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.android.framework.demo.R;

/**
 * Created by mike on 16/2/25.
 */
public class AnimatedVectorDrawableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector_drawable);


//        Drawable drawable = androidImageView.getDrawable();
//        if (drawable instanceof Animatable) {
//            ((Animatable) drawable).start();
//        }

//        VectorDrawableCompat v = VectorDrawableCompat.create(getResources(), R.drawable.vector_android, getTheme());
//        androidImageView.setImageDrawable(v);

        ImageView androidImageView = (ImageView) findViewById(R.id.animated_vector_imgv);
        AnimatedVectorDrawableCompat animatedVectorDrawableCompat = AnimatedVectorDrawableCompat.create(this, R.drawable.animated_vector);
        androidImageView.setImageDrawable(animatedVectorDrawableCompat);
        ((Animatable) androidImageView.getDrawable()).start();

    }

}
