package com.lecz.clubdelosvencedores.general;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.text.format.Time;

import com.lecz.clubdelosvencedores.DatabaseManagers.PlanDetailsDataSource;
import com.lecz.clubdelosvencedores.R;
import com.lecz.clubdelosvencedores.objects.PlanDetail;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Luis on 9/8/2014.
 */
public class NotificationService extends Service {

    private final IBinder mBinder = new MyBinder();
    private ArrayList<String> list = new ArrayList<String>();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int icon = R.drawable.ic_launcher;
        CharSequence tickerText = "Notification Bar";
        long when = System.currentTimeMillis();
        Time now = new Time();
        now.setToNow();

        PlanDetailsDataSource dspd = new PlanDetailsDataSource(this);

        dspd.open();
        PlanDetail plan = dspd.getCurrentPlanDetail();
        dspd.close();

        Notification notification = new Notification(icon, tickerText, when);

        Context context = getApplicationContext();
        CharSequence contentTitle = "Plan" + plan.getNumber_day();
        CharSequence contentText = "Time:" + now.format("%k:%M:%S");

        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;


        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);

        int min = 0;
        int max = 1000;

        Random r = new Random();
        int i1 = r.nextInt(max - min + 1) + min;
        mNotificationManager.notify(i1, notification);

        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class MyBinder extends Binder {
        NotificationService getService() {
            return NotificationService.this;
        }
    }

}
