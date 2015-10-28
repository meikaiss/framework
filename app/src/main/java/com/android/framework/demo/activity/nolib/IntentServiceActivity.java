package com.android.framework.demo.activity.nolib;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.framework.demo.DemoApplicaton;
import com.android.framework.demo.service.PlayMusicIntentService;

/**
 * Created by meikai on 15/10/28.
 */
public class IntentServiceActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        Button btn = new Button(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        btn.setLayoutParams(lp);
        btn.setText("启动IntentService");

        setContentView(btn, lp);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(DemoApplicaton.getInstance(), "按钮点击", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(IntentServiceActivity.this, PlayMusicIntentService.class);
                i.putExtra("userName", "meikai");
                DemoApplicaton.getInstance().startService(i);
            }
        });


    }
}
