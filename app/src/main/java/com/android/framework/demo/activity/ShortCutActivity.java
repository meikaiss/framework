package com.android.framework.demo.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import com.android.framework.base.BaseCompactActivity;
import com.android.framework.demo.R;
import com.android.framework.util.BitmapUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

/**
 * Created by meikai on 16/11/4.
 */
public class ShortCutActivity extends BaseCompactActivity {

    public static final String ACTION_ADD_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";


    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity__short_cut;
    }

    @Override
    public void findViews() {

    }

    @Override
    public void setListeners() {

        findViewById(R.id.btn_add_short_cut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addShortcut("我的快捷");
            }
        });

        findViewById(R.id.btn_add_short_cut_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addShortcut("吧啦吧啦");
            }
        });

    }


    @Override
    public void parseBundle(Intent intent) {

    }

    @Override
    public void afterView() {

    }

    private void addShortcut(String name) {

        Glide.with(this)
                .load("http://upload.jianshu.io/users/upload_avatars/1779681/d2a671fe7f07?imageMogr/thumbnail/90x90"
                        + "/quality/100")
                .centerCrop().into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {

                Intent addShortcutIntent = new Intent(ACTION_ADD_SHORTCUT);

                // 不允许重复创建
                addShortcutIntent.putExtra("duplicate", false);// 经测试不是根据快捷方式的名字判断重复的
                // 应该是根据快链的Intent来判断是否重复的,即Intent.EXTRA_SHORTCUT_INTENT字段的value
                // 但是名称不同时，虽然有的手机系统会显示Toast提示重复，仍然会建立快链
                // 屏幕上没有空间时会提示
                // 注意：重复创建的行为MIUI和三星手机上不太一样，小米上似乎不能重复创建快捷方式

                // 名字
                addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);

                // 图标
                Bitmap bitmap = BitmapUtil.drawableToBitmap(resource);
                findViewById(R.id.img_short_icon).setBackgroundDrawable(new BitmapDrawable(bitmap));
                addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON, bitmap);

                Uri uri = Uri.parse("mucang-qichetoutiao://gateway?navUrl=nav.meikai.sayyou8" + name);
                Intent launcherIntent = new Intent();
                launcherIntent.setAction(Intent.ACTION_VIEW);
                launcherIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                launcherIntent.setData(uri);

                addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, launcherIntent);

                // 发送广播
                sendBroadcast(addShortcutIntent);

                Toast.makeText(ShortCutActivity.this, "添加成功", Toast.LENGTH_LONG).show();
            }
        });

        // 设置关联程序
//        Intent launcherIntent = new Intent("com.android.framework.demo.activity.ShortCutActivity");
////        launcherIntent.setClass(ShortCutActivity.this, TouchFloatActivity.class);
//        launcherIntent.addCategory(Intent.CATEGORY_DEFAULT);

    }
}
