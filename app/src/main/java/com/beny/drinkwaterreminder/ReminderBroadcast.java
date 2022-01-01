package com.beny.drinkwaterreminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.io.Serializable;

public class ReminderBroadcast extends BroadcastReceiver implements Serializable {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

//        ReminderBroadcast r1 = (ReminderBroadcast) intent.getSerializableExtra("r1");
//        id = r1.getId();
        id = intent.getIntExtra("ID",-1);
        Log.d("OnRecieve","Called");
        Log.d("id is:", String.valueOf(id));
        if (id == HomeFragment.Reset_id) {
            Log.d("in if:", String.valueOf(id));
            //its 24 hours passed let's reset our progressBar:D
            SharedPreferences ResetPref = context.getSharedPreferences("Reset",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = ResetPref.edit();
            editor.putBoolean("Reset",true);
//            editor.commit();
            editor.apply();
        }
        else {
            NotificationUtils _notificationUtils = new NotificationUtils(context);
            NotificationCompat.Builder _builder = _notificationUtils.setNotification("It's time to drink water!", "Confirm that you have just drunk water", id);
            _notificationUtils.getManager().notify(id, _builder.build());

        }
    }
}
