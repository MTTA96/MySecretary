package com.stak.mysecretary;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;

import com.stak.mysecretary.model.Hoatdong;

import java.util.ArrayList;

/**
 * Created by zzzzz on 4/13/2017.
 */

public class Reminder extends BroadcastReceiver {
    MainActivity mainActivity;
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Hoatdong HoatDong = (Hoatdong) intent.getSerializableExtra("hoatdong");
        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.appicon2)
                .setContentTitle(HoatDong.getTenhd())
                .setContentText(HoatDong.getDiadiem())
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                .setContentInfo(HoatDong.getTgbd());
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }
}
