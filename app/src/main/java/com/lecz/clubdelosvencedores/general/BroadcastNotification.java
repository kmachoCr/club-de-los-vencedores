package com.lecz.clubdelosvencedores.general;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

public class BroadcastNotification extends BroadcastReceiver {
    private PendingIntent pendingIntent;
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent service1 = new Intent(context, NotificationService.class);
        context.startService(service1);
    }
}