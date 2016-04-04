package com.android.framework.demo.activity.ndk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.framework.demo.R;
import com.android.framework.ndk.NdkTest;

/**
 * Created by meikai on 16/3/30.
 */
public class NDKTestActivity extends AppCompatActivity {

    EditText edt;
    TextView tvEncrypt;
    TextView tvDecrypt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ndk_test);

        TextView textView = (TextView) findViewById(R.id.tv_string_in_ndk);
        textView.setText(NdkTest.getStringInNDK());

        TextView tvTransmit = (TextView) findViewById(R.id.tv_transmit_string_to_ndk);
        tvTransmit.setText(tvTransmit.getText().toString() + NdkTest.converseInSo("mike"));


        edt = (EditText) findViewById(R.id.edt);
        tvEncrypt = (TextView) findViewById(R.id.tv_ndk_encrypt);
        tvDecrypt = (TextView) findViewById(R.id.tv_ndk_decrypt);


        findViewById(R.id.btn_encrypt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvEncrypt.setText(NdkTest.encryptString(edt.getText().toString()));

            }
        });

        findViewById(R.id.btn_decrypt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvDecrypt.setText(NdkTest.decryptString(tvEncrypt.getText().toString()));

            }
        });

    }
}
