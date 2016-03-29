package com.android.framework.demo.activity.utildemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.android.framework.demo.R;
import com.android.framework.util.PermissionUtil;

/**
 * Created by meikai on 16/3/28.
 */
public class PermissionUtilDemoActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_util);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_WRITE_EXTERNAL_STORAGE:
                PermissionUtil.getInstance(this).request(new PermissionUtil.RequestPermissionsResultCallBack() {
                    @Override
                    public void onRequestPermissionsResult(String[] permissions, int[] grantResults) {
                        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(PermissionUtilDemoActivity.this, "已获取此权限", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(PermissionUtilDemoActivity.this, "未获取此权限", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError() {
                        Toast.makeText(PermissionUtilDemoActivity.this, "权限检验出错", Toast.LENGTH_SHORT).show();
                    }
                }, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                break;
            case R.id.btn_INTERNET:
                PermissionUtil.getInstance(this).request(new PermissionUtil.RequestPermissionsResultCallBack() {
                    @Override
                    public void onRequestPermissionsResult(String[] permissions, int[] grantResults) {
                        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(PermissionUtilDemoActivity.this, "已获取此权限", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(PermissionUtilDemoActivity.this, "未获取此权限", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError() {
                        Toast.makeText(PermissionUtilDemoActivity.this, "权限检验出错", Toast.LENGTH_SHORT).show();
                    }
                }, Manifest.permission.READ_CONTACTS);
                break;
            default:
                break;
        }
    }

    @Override
    public int checkPermission(String permission, int pid, int uid) {
        return super.checkPermission(permission, pid, uid);
    }
}
