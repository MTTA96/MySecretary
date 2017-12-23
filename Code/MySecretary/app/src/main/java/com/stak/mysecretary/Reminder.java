package com.stak.mysecretary;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import com.stak.mysecretary.View.Activity.MainActivity;
import com.stak.mysecretary.model.HoatDong;

/**
 * Created by zzzzz on 4/13/2017.
 */

public class Reminder extends BroadcastReceiver {
    MainActivity mainActivity;
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        HoatDong hoatDong = (com.stak.mysecretary.model.HoatDong) intent.getSerializableExtra("hoatDong");
        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.appicon2)
                .setContentTitle(hoatDong.getTenhd())
                .setContentText(hoatDong.getDiadiem())
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                .setContentInfo(hoatDong.getTgbd());
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }
}
