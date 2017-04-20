package com.android.framework.demo.activity.nolib;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.framework.demo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by meikai on 15/11/13.
 */
public class RippleActivity extends Activity {

    private Button btnNewRipple;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ripple);

        listView = (ListView) findViewById(R.id.list_view);

        final List<Map<String, String>> dataList = new ArrayList<>();

        dataList.addAll(getDataList());

        final SimpleAdapter adapter = new SimpleAdapter(this, dataList, R.layout.item_demo, new String[]{"name"}, new int[]{R.id.tv_item_name});
        listView.setAdapter(adapter);

        btnNewRipple = (Button) findViewById(R.id.btn_code_new_ripple);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ){
            ColorStateList colorStateList = new ColorStateList(
                    new int[][]{
                            new int[]{android.R.attr.state_pressed},
                            new int[]{android.R.attr.state_focused},
                            new int[]{android.R.attr.state_activated},
                            new int[]{}
                    },
                    new int[]{
                            Color.parseColor("#e2e2e2"),
                            Color.parseColor("#e2e2e2"),
                            Color.parseColor("#e2e2e2"),
                            Color.parseColor("#fafafa")
                    }
            );
            RippleDrawable rippleDrawable = new RippleDrawable(colorStateList, null, null);
            btnNewRipple.setBackground(rippleDrawable);
        }else{

        }

    }


    private List<Map<String, String>> getDataList(){

        List<Map<String, String>> dataList = new ArrayList<>();

        for (int i = 0 ; i  < 10 ; i ++){
            Map<String, String> map = new HashMap<>();
            map.put("name", "meikai"+i);
            map.put("age", "25");
            dataList.add(map);
        }
        return dataList;
    }
}
