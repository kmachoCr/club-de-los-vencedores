package com.lecz.clubdelosvencedores.general;

/**
 * Created by Luis on 9/8/2014.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StarterService extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, NotificationService.class);
        context.startService(service);
    }
}
