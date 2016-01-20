package com.android.framework.demo.activity.nolib;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;

import com.android.framework.demo.MainActivity;
import com.android.framework.demo.R;
import com.android.framework.demo.service.NotificationService;

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
        builder.setSmallIcon(R.drawable.avatar)
                .setContentTitle("通知的标题").setContentText("通知的内容").setAutoCancel(true)
                .setContentIntent(intent);

        NotificationManager notificationManager = (NotificationManager) this.getSystemService(this.NOTIFICATION_SERVICE);
        notificationManager.notify("mk", new Random().nextInt(10000), builder.build());

    }

    public void btnClick2(View view) {
        f2();
    }


    public void f0() {
        CharSequence title = "i am new";
        int icon = R.mipmap.ic_launcher;
        long when = System.currentTimeMillis();
        Notification noti = new Notification(icon, title, when + 10000);
        noti.flags = Notification.FLAG_INSISTENT;

        // 1、创建一个自定义的消息布局 view.xml
        // 2、在程序代码中使用RemoteViews的方法来定义image和text。然后把RemoteViews对象传到contentView字段
        RemoteViews remoteView = new RemoteViews(this.getPackageName(), R.layout.notification_layout);
        remoteView.setImageViewResource(R.id.iv, R.drawable.avatar);
        remoteView.setTextViewText(R.id.tvNotiTitle, "通知类型为：自定义View");
        remoteView.setImageViewResource(R.id.btNotiLast, R.drawable.explosion_02);
        remoteView.setImageViewResource(R.id.btNotiPlay, R.drawable.explosion_02);
        remoteView.setImageViewResource(R.id.btNotiNext, R.drawable.explosion_02);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            noti.bigContentView = remoteView;
        }
        // 3、为Notification的contentIntent字段定义一个Intent(注意，使用自定义View不需要setLatestEventInfo()方法)

        //这儿点击后简单启动Settings模块
        PendingIntent contentIntent = PendingIntent.getActivity
                (this, 0, new Intent("android.settings.SETTINGS"), 0);
        noti.contentIntent = contentIntent;

        NotificationManager mnotiManager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
        mnotiManager.notify(0, noti);
    }

    public void f1() {
        NotificationCompat.BigTextStyle textStyle = new android.support.v4.app.NotificationCompat.BigTextStyle();
        textStyle.setBigContentTitle("大标题")
                .setSummaryText("小标题")
                .bigText("这是大通知的内容这是大通知的内容这是大通知的内容这是大通知的内容这是大通知的内容这是大通知的内容这是大通知的内容这是大通知的内容这是大通知的内容");
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.avatar);
        Notification notification = new NotificationCompat.Builder(this)
                .setLargeIcon(largeIcon).setSmallIcon(R.drawable.avatar)
                .setTicker("showBigView_Text").setContentInfo("contentInfo")
                .setContentTitle("ContentTitle").setContentText("ContentText")
                .setStyle(textStyle)
                .setAutoCancel(true).setDefaults(Notification.DEFAULT_ALL)
                .build();
        NotificationManager mnotiManager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
        mnotiManager.notify(1000, notification);

    }

    public void f2() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            Notification notification;

            RemoteViews remoteView = new RemoteViews(getApplicationContext().getPackageName(), R.layout.notification_layout);
            remoteView.setImageViewResource(R.id.iv, R.drawable.avatar);
            remoteView.setTextViewText(R.id.tvNotiTitle, "通知类型为：自定义View");

            Intent intent = new Intent(this, NotificationService.class);
            intent.putExtra("param", 888);
            PendingIntent pendingIntent = PendingIntent.getService(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteView.setOnClickPendingIntent(R.id.iv, pendingIntent);

            Intent intent1 = new Intent(this, NotificationService.class);
            intent1.putExtra("param", 1);
            PendingIntent pendingIntent1 = PendingIntent.getService(this, 1, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteView.setOnClickPendingIntent(R.id.btNotiLast, pendingIntent1);

            Intent intent2 = new Intent(this, NotificationService.class);
            intent2.putExtra("param", 2);
            PendingIntent pendingIntent2 = PendingIntent.getService(this, 1, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteView.setOnClickPendingIntent(R.id.btNotiPlay, pendingIntent2);

            Intent intent3 = new Intent(this, NotificationService.class);
            intent3.putExtra("param", 33);
            PendingIntent pendingIntent3 = PendingIntent.getService(this, 1, intent3, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteView.setOnClickPendingIntent(R.id.btNotiNext, pendingIntent3);

            Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.avatar);

            android.support.v7.app.NotificationCompat.Builder builder = new android.support.v7.app.NotificationCompat.Builder(this);
            builder.setContentTitle("some string")
                    .setContentText("Slide down on note to expand")
                    .setSmallIcon(R.drawable.avatar)
                    .setLargeIcon(largeIcon);

            notification = builder.build();
            notification.bigContentView = remoteView;

            NotificationManager mNotifyManager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
            mNotifyManager.notify(1, notification);
        }
    }

}
