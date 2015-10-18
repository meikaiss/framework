package com.android.framework.demo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created by meikai on 15/10/16.
 */
public class AppCompatTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appcompat);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_appcompat, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.ac_toolbar_copy:
                Toast.makeText(this, "点击了按钮1", Toast.LENGTH_LONG).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("AlertDialog的标题");
                builder.setMessage("菜单被点击");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AppCompatTestActivity.this, "选择了OK", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AppCompatTestActivity.this, "选择了取消", Toast.LENGTH_LONG).show();
                    }
                });

                builder.show();
                break;
            case R.id.ac_toolbar_cut:

                break;
            case R.id.ac_toolbar_del:

                break;
            case R.id.ac_toolbar_edit:

                break;
            case R.id.ac_toolbar_email:

                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
