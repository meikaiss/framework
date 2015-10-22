package com.android.framework.demo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;

import java.util.Random;

/**
 * Created by meikai on 15/10/22.
 */
public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
    }

    public void btnClick(View view) {

        PendingIntent intent = PendingIntent.getActivity(this, 100, new Intent(this, MainActivity.class), PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.avater)
                .setContentTitle("通知的标题").setContentText("通知的内容").setAutoCancel(true)
                .setContentIntent(intent);

        NotificationManager notificationManager = (NotificationManager) this.getSystemService(this.NOTIFICATION_SERVICE);
        notificationManager.notify("mk", new Random().nextInt(10000), builder.build());

    }

    public void btnClick2(View view) {

        Notification notification = new Notification();
        notification.icon = R.drawable.avater;
        notification.tickerText = "当前播放：123";
        notification.when = System.currentTimeMillis();
        notification.flags = Notification.FLAG_NO_CLEAR; // 不能够自动清除

        // 设置自定义notification视图
        RemoteViews rv = new RemoteViews(getPackageName(),
                R.layout.notification_layout);
        rv.setImageViewResource(R.id.iv, R.drawable.avater);
        rv.setTextViewText(R.id.tvNotiTitle, "title123123");
        rv.setImageViewResource(R.id.btNotiLast, R.drawable.explosion_02);
        rv.setImageViewResource(R.id.btNotiPlay, R.drawable.explosion_02);
        rv.setImageViewResource(R.id.btNotiNext, R.drawable.explosion_02);
        notification.contentView = rv;

        NotificationManager notificationManager = (NotificationManager) this.getSystemService(this.NOTIFICATION_SERVICE);
        notificationManager.notify("mk", new Random().nextInt(10000), notification);

    }

}
