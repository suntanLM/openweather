package com.example.tantan.openweather;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

/**
 * Created by TanTan on 10/30/17.
 */

public class NotificationService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent mIntent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,mIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher_round);
        builder.setColor(Color.MAGENTA);
        builder.setContentTitle("Tử vi hàng ngày");
        builder.setContentText("Hãy cùng xem thời tiết và tử vi của bạn ngày hôm nay nhé!");
        builder.setSound(sound);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0,builder.build());
        return START_NOT_STICKY;
    }
}
