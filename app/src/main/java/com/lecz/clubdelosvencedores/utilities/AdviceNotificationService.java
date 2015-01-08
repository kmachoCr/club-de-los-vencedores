package com.lecz.clubdelosvencedores.utilities;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

import com.lecz.clubdelosvencedores.DatabaseManagers.ActivityDataSource;
import com.lecz.clubdelosvencedores.MyActivity;
import com.lecz.clubdelosvencedores.R;
import com.lecz.clubdelosvencedores.general.NotificationMng;

public class AdviceNotificationService extends Service {
    public AdviceNotificationService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle extras = intent.getExtras();

        String ticket = (String) extras.get("ticket");
        String title = (String) extras.get("title");
        String body = (String) extras.get("body");

        ActivityDataSource acds = new ActivityDataSource(this);
        acds.open();
        acds.createActivity(new com.lecz.clubdelosvencedores.objects.Activity(R.drawable.checkmark, System.currentTimeMillis(), body, "consejo", title));
        acds.close();

        NotificationMng notificationManager = new NotificationMng(this);
        notificationManager.createNotification(this, R.drawable.pulmones, ticket, title, body, System.currentTimeMillis(), MyActivity.class, "consejo");


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
        AdviceNotificationService getService() {
            return AdviceNotificationService.this;
        }
    }
}
