package com.lecz.clubdelosvencedores.general;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.text.format.Time;

import com.lecz.clubdelosvencedores.R;

import java.util.Random;

/**
 * Created by Luis on 10/21/2014.
 */
public class NotifictionManager {
    NotificationManager mNotificationManager;

    public NotifictionManager(Context context){
        mNotificationManager = (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void createNotification(Context context, Integer icon, String tickerText, String contentTitle, String contentText, long when, Object dopClass){
        Notification notification = new Notification(icon, tickerText, when);

        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;


        Intent notificationIntent = new Intent(context, (Class<?>) dopClass);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);

        int min = 0;
        int max = 1000;

        Random r = new Random();
        int i1 = r.nextInt(max - min + 1) + min;
        mNotificationManager.notify(i1, notification);
    }
}
