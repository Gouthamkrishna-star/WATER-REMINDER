package com.beny.drinkwaterreminder;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.io.Serializable;
import java.util.Calendar;

public class NotificationUtils extends ContextWrapper implements Serializable {
    String CHANNEL_ID = "notification channel";

    String TIMELINE_CHANNEL_NAME = "Timeline notification";
    private NotificationManager _notificationManager;
    private Context _context;

    public NotificationUtils(Context base) {
        super(base);
        _context = base;
        createChannel();
    }

    public NotificationCompat.Builder setNotification(String title, String body , int id) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_local_drink_24)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        Intent notifyIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, id, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //to be able to launch your activity from the notification
        builder.setContentIntent(pendingIntent);
        return builder;
    }

    private void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, TIMELINE_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            getManager().createNotificationChannel(channel);
        }
    }

    public NotificationManager getManager() {
        if (_notificationManager == null) {
            _notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return _notificationManager;
    }

    public void deleteReminder(int id){
        _notificationManager = new NotificationUtils(getApplicationContext()).getManager();
        _notificationManager.cancel(id);

    }

    public void setReminder(int hour, int minute, int id) {
        ReminderBroadcast r1 = new ReminderBroadcast();
        r1.setId(id);
        Log.d("id in set reminder ", String.valueOf(id));
        Intent _intent = new Intent(_context, ReminderBroadcast.class);
        _intent.putExtra("r1",r1);
        _intent.putExtra("ID",id);

        PendingIntent _pendingIntent = PendingIntent.getBroadcast(_context, id, _intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 00);
        if (calendar.before(now)) {
            Log.d("Hey","Added a day");
            calendar.add(Calendar.DATE, 1);
        }

//        AlarmManager _alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//        _alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMillis, _pendingIntent);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, _pendingIntent);
        Log.d("reminder set:" , String.valueOf(calendar.getTime()));
    }

}