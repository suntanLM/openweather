package com.example.tantan.openweather;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by TanTan on 10/30/17.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent mIntent = new Intent(context,NotificationService.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startService(mIntent);
    }
}
