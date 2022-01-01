package com.beny.drinkwaterreminder;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class Notification_reciever extends BroadcastReceiver {

    public static int id ;

    @Override
    public void onReceive(Context context, Intent intent ) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent repeating_intent = new Intent(context , HomeFragment.class);
        repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent  pendingIntent = PendingIntent.getActivity(context
                , id , repeating_intent , PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_baseline_local_drink_24)
                .setContentTitle("Title")
                .setContentText("hi this is notif")
                .setAutoCancel(true);
        notificationManager.notify(id,builder.build());
    }
}
