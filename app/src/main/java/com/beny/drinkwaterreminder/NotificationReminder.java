package com.beny.drinkwaterreminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class NotificationReminder extends AppCompatActivity {
    RecyclerView mRecyclerView;
    ListView lv;
    FloatingActionButton floatingButton;
    public static final int ID_1 = 1379;
    public static final int ID_2 = 1380;
    public static final int ID_3 = 1376;
    public static final int ID_4 = 1395;
    public static final int ID_5 = 1400;
    public static final int ID_6 = 1401;
    boolean isDefaultSetting = true;
    Context context;

    public static ArrayList<ReminderHolder> times = new ArrayList<>();

    ReminderAdapter Adapter = new ReminderAdapter(this);


    public NotificationReminder(){}
    public NotificationReminder(Context context){
        this.context = context;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        floatingButton = findViewById(R.id.floating_action_button);
        mRecyclerView = findViewById(R.id.Reminder_recycleView);
        SharedPreferences sharedPreferences = getSharedPreferences("-1",Context.MODE_PRIVATE);
        int def = sharedPreferences.getInt("-1",0);
        if(def==-1)
            DefultSetting();
        context = getApplicationContext();
        Log.d("OnCreate","Notification reminder");

        ReminderHolder rh = new ReminderHolder();
          boolean is24HourFormat = DateFormat.is24HourFormat(this);

        rh.LoadData();
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //generate random id
                int id;
                Random random = new Random();
                id = random.nextInt();
                Log.d("id is random", String.valueOf(id));
                //time picker

                Calendar calendar = Calendar.getInstance();
                int Hour = calendar.get(Calendar.HOUR);
                int Minute = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(NotificationReminder.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        String am_pm = "";

                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(Calendar.HOUR, hourOfDay);
                        calendar1.set(Calendar.MINUTE, minute);

                        if (calendar1.get(Calendar.AM_PM) == Calendar.AM)
                            am_pm = "AM";
                        else if (calendar1.get(Calendar.AM_PM) == Calendar.PM)
                            am_pm = "PM";

                        String time = hourOfDay + ":" + minute + " " + am_pm;

                        CharSequence charSequence = DateFormat.format("hh:mm a", calendar1);
//                       times.add((String) charSequence);
                        Log.d("log", (String) charSequence);

                        //create a object from reminder holder

                        ReminderHolder reminderHolder = new ReminderHolder(id, (String) charSequence);
                        times.add(reminderHolder);

                        Log.d("list", String.valueOf(times));

                        mRecyclerView.post(new Runnable() {
                                               @Override
                                               public void run() {
                                                   Adapter.notifyDataSetChanged();
                                               }
                                           }
                        );
                        setNotification(hourOfDay, minute, id);
                        saveData(getApplicationContext());
//                        isDefaultSetting = false;
//                        SharedPreferences sharedPreferences = context.getSharedPreferences("isDefaultSetting",Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putBoolean("isDefaultSetting",false);
//                        editor.commit();
                    }
                }, Hour, Minute, is24HourFormat);
                timePickerDialog.show();
            }
        });

        new ItemTouchHelper(simpleCallback).attachToRecyclerView(mRecyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(Adapter);


    }

    private void DefultSetting() {
        SharedPreferences sharedPreferences = getSharedPreferences("isDefaultSetting",Context.MODE_PRIVATE);

         isDefaultSetting = sharedPreferences.getBoolean("isDefaultSetting",true);
        Log.e("default", String.valueOf(isDefaultSetting));
        if(isDefaultSetting) {
            //use fuckin loop idiot
            ReminderHolder obj0 = new ReminderHolder(ID_1, "21:00 PM");
            ReminderHolder obj1 = new ReminderHolder(ID_2, "18:00 PM");
            ReminderHolder obj2 = new ReminderHolder(ID_3, "15:00 PM");
            ReminderHolder obj3 = new ReminderHolder(ID_4, "13:00 PM");
            ReminderHolder obj4 = new ReminderHolder(ID_5, "11:00 AM");
            ReminderHolder obj5 = new ReminderHolder(ID_6, "9:30 AM");
            times.add(obj0);
            times.add(obj1);
            times.add(obj2);
            times.add(obj3);
            times.add(obj4);
            times.add(obj5);

            setNotification(21, 00, ID_1);
            setNotification(18, 00, ID_2);
            setNotification(15, 00, ID_3);
            setNotification(13, 00, ID_4);
            setNotification(11, 00, ID_5);
            setNotification(9, 30, ID_6);
//            setNotification(24,00,12);
            saveData(getApplicationContext());
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isDefaultSetting", false);
            editor.commit();



        }
        else
            return;

    }



    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            NotificationUtils obj = new NotificationUtils(getBaseContext());
            obj.deleteReminder(NotificationReminder.times.get(viewHolder.getAbsoluteAdapterPosition()).getId());

            NotificationReminder.times.remove(viewHolder.getAbsoluteAdapterPosition());
            Toast.makeText(getBaseContext(), "reminder removed!", Toast.LENGTH_SHORT).show();

            Adapter.notifyItemRemoved(viewHolder.getAbsoluteAdapterPosition());
            saveData(getBaseContext());


        }
    };

    public void setNotification(int hour, int minute, int id) {

        NotificationUtils notifObj = new NotificationUtils(this);
        notifObj.setReminder(hour, minute, id);

    }

    public void saveData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("saveArrayList2", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //save arrayList

        Gson gson = new Gson();
        String json = gson.toJson(NotificationReminder.times);
        editor.putString("timeObj", json);
        editor.apply();

    }

    public class ReminderHolder {
        private int id;
        private String time;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }


        public ReminderHolder(int id, String time) {
            this.id = id;
            this.time = time;
        }

        public ReminderHolder() {

        }

        @NonNull
        @Override
        public String toString() {
            return time;
        }


        public void LoadData() {
            SharedPreferences sharedPreferences = getSharedPreferences("saveArrayList2", Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString("timeObj", null);
            Type type = new TypeToken<ArrayList<ReminderHolder>>() {
            }.getType();
            NotificationReminder.times = gson.fromJson(json, type);
            if (NotificationReminder.times == null) {
                NotificationReminder.times = new ArrayList<>();
            }
        }
    }


}