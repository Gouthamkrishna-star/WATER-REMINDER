package com.beny.drinkwaterreminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.FragmentTransaction;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;
import java.util.logging.SocketHandler;

public class MainActivity extends AppCompatActivity  {
    public static final String PrefTag = "delCup";
    public static Context context;
    String weight ,bedtime,wakeuptime , workout;
    boolean clicked = false ;
    int timeDeference, NumberOfCups = 1 , plusTime = 0 , Notification_Id = 0;
    String Goal , sizeOfCup;
    SharedPreferences progressState ,sp;
    String Channel_ID = "11"  , Channel_Name = "reminder" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        TabItem tabHome = findViewById(R.id.TabHome);
        TabItem tabSetting  = findViewById(R.id.setting);
        ViewPager viewPager = findViewById(R.id.ViewPager);


        checkFirstRun();
        NotificationUtils createchannel = new NotificationUtils(this);
        context = getApplicationContext();
        Intent intent = getIntent();
        int ml = intent.getIntExtra("ml",-1);
        SharedPreferences pref = getSharedPreferences(PrefTag, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("ml" , ml);
        editor.putBoolean("isDelete",true);
        editor.commit();
        Log.d("shared prefrences", " Worked");

        PagerAdapter pagerAdapter = new PagerAdapter(
                getSupportFragmentManager() , tabLayout.getTabCount());

        viewPager.setAdapter(pagerAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());



            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));



        clicked = getIntent().getBooleanExtra("CLicked" , false);

        if(clicked) {
        DailyGoalDialog dailyGoalDialog = new DailyGoalDialog();
        dailyGoalDialog.show(getSupportFragmentManager(), "Daily goal");

        }
//        workout = getIntent().getStringExtra("workout");
////        weight = getIntent().getStringExtra("weight");
//        bedtime = getIntent().getStringExtra("bedtime");
//        wakeuptime = getIntent().getStringExtra("wakeupTime");
//        Goal = progressState.getString("goalState", "2000");
//        sizeOfCup = sp.getString("cupsize", "250");
    }




    //menu
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.notif_menu: {
//                triggerNotification(8,1);
                Toast.makeText(this, "Notification is On!", Toast.LENGTH_SHORT).show();
                return true;
            }
            
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    public void triggerNotification(int wakeHour, int id) {
//
////        Calendar calendar = Calendar.getInstance();
////
////        if(wakeHour>24)
////            return;
////        Log.i("wake hour in func" , String.valueOf(wakeHour));
////        calendar.set(Calendar.HOUR_OF_DAY, 18);
////        calendar.set(Calendar.MINUTE, 03);
////        calendar.set(Calendar.SECOND, 00);
//
//        Intent intent = new Intent(MainActivity.this , MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, id ,intent , 0 );
//        Notification_reciever.id = id;
//
//        NotificationCompat.Builder  builder = new NotificationCompat.Builder(this, String.valueOf(Channel_ID))
//                .setSmallIcon(R.drawable.ic_launcher_background)
//                .setContentTitle("this is Title")
//                .setContentText("this is the txt")
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                .setContentIntent(pendingIntent)
//                .setChannelId(Channel_ID)
//                .setAutoCancel(true);
//
//
//
//
////        AlarmManager alarmManager = (AlarmManager) this.getSystemService(ALARM_SERVICE);
////        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
//
//        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
//        notificationManagerCompat.notify(id, builder.build());
//    }

    public void checkFirstRun() {

        final String PREFS_NAME = "MyPrefsFile";
        final String PREF_VERSION_CODE_KEY = "version_code";
        final int DOESNT_EXIST = -1;

        // Get current version code
        int currentVersionCode = BuildConfig.VERSION_CODE;

        // Get saved version code
       SharedPreferences prefs = this.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int savedVersionCode = prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST);

        // Check for first run or upgrade
        if (currentVersionCode == savedVersionCode) {

            // This is just a normal run
            SharedPreferences sharedPreferences = getSharedPreferences("isFirstTime", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isFirstTime",false);
            editor.commit();
            return;

        } else if (savedVersionCode == DOESNT_EXIST) {

            // TODO This is a new install (or the user cleared the shared preferences)
            Intent intent = new Intent(MainActivity.this ,Pi.class );
            startActivity(intent);
            SharedPreferences sharedPreferences = getSharedPreferences("isFirstTime", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isFirstTime",true);
            editor.commit();

        } else if (currentVersionCode > savedVersionCode) {

            // TODO This is an upgrade
        }

        // Update the shared preferences with the current version code
        prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply();
    }

//    public void makeNotification(int wakeHour , int  id) {
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//
//            NotificationChannel notificationChannel = new NotificationChannel(
//                    "11",
//                    "Notification_Channel_Name",
//                    NotificationManager.IMPORTANCE_DEFAULT );
//
//            notificationChannel.setDescription("Description");
//            notificationChannel.setShowBadge(true);
//
//            NotificationManager notificationManager =  getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(notificationChannel);
//
//
//        }
//
//        Calendar calendar = Calendar.getInstance();
//
//        if(wakeHour>24)
//            return;
//        Log.i("wake hour in func" , String.valueOf(wakeHour));
//        calendar.set(Calendar.HOUR_OF_DAY, wakeHour);
//        calendar.set(Calendar.MINUTE, 00);
//        calendar.set(Calendar.SECOND, 00);
//
//        Notification_reciever.id = id ;
//
//        Intent intent = new Intent(this, Notification_reciever.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,
//                id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        AlarmManager alarmManager = (AlarmManager) this.getSystemService(ALARM_SERVICE);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
//
//    }


}