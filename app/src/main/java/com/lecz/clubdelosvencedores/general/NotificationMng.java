package com.lecz.clubdelosvencedores.general;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.text.format.Time;

import com.lecz.clubdelosvencedores.MyActivity;
import com.lecz.clubdelosvencedores.R;

import java.util.Random;

/**
 * Created by Luis on 10/21/2014.
 */
public class NotificationMng {
    NotificationManager mNotificationManager;

    public NotificationMng(Context context){
        mNotificationManager = (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void createNotification(Context context, Integer icon, String tickerText, String contentTitle, String contentText, long when, Object dopClass, String type){

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.pulmones)
                        .setContentTitle(contentTitle)
                        .setContentText(contentText)
                        .setTicker(tickerText)
                        .setWhen(when)
                        .setAutoCancel(true)
                        .setDefaults(Notification.DEFAULT_ALL )
                        .setShowWhen(true);


        Intent openHomePageActivity = new Intent(context, (Class) dopClass);
        if(type.equals("logro")){
            openHomePageActivity.putExtra("contentTitle", tickerText);
            openHomePageActivity.putExtra("contentText", contentText);
            openHomePageActivity.putExtra("icon", icon);
        }

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(openHomePageActivity);

        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        int min = 0;
        int max = 1000;

        Random r = new Random();
        int i1 = r.nextInt(max - min + 1) + min;

        mNotificationManager.notify(i1, mBuilder.build());

        /*Notification notification = new Notification(R.drawable.pulmones, tickerText, when);

        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;


        Intent notificationIntent = new Intent(context, (Class<?>) dopClass);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);


        mNotificationManager.notify(i1, notification);*/
    }
}
