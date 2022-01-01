package com.beny.drinkwaterreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Random;

public class Pi extends AppCompatActivity {

    EditText weight , bedtime, wakeupTime, workout;
    Button startbtn ,selectwakeup , selectbedTime;
    TextView wakeuptxt , bedtimetxt;
    int wakeHour ,bedhour;
    boolean isClicked = false , isClicked1 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pi);

        weight = findViewById(R.id.weighttxt);
//        bedtime = findViewById(R.id.bedtimetxt);
//        wakeupTime = findViewById( R.id.wakeuptxt);
        workout = findViewById(R.id.workoutid);
        startbtn = findViewById(R.id.startbtn);
        selectbedTime = findViewById(R.id.selectBedtime);
        selectwakeup = findViewById(R.id.selectWakeup);
        wakeuptxt = findViewById(R.id.wakeUptxt);
        bedtimetxt = findViewById(R.id.bedTimetxt);


        Bundle bundle = new Bundle();

// set Fragmentclass Arguments
        HomeFragment fragobj = new HomeFragment();
        SharedPreferences sp = getSharedPreferences("first" , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();


        SharedPreferences sharedPreferences = getSharedPreferences("progress" , Context.MODE_PRIVATE);
        SharedPreferences.Editor booleanEditor = sharedPreferences.edit();
        boolean is24HourFormat = DateFormat.is24HourFormat(this);

        selectwakeup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //select time picker and show in txt view

                Calendar calendar = Calendar.getInstance();
                int Hour = calendar.get(Calendar.HOUR);
                int Minute = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(Pi.this, new TimePickerDialog.OnTimeSetListener() {
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
                        wakeuptxt.setText((String) charSequence);
                        selectwakeup.setText("Edit");
                        wakeHour = hourOfDay;
                         Log.d("wakeHour is ", String.valueOf(wakeHour));
                        isClicked = true;




                    }
                }, Hour, Minute, true);
                timePickerDialog.show();

            }
        });

        selectbedTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //select time picker and show in txt view

                Calendar calendar = Calendar.getInstance();
                int Hour = calendar.get(Calendar.HOUR);
                int Minute = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(Pi.this, new TimePickerDialog.OnTimeSetListener() {
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
                        bedtimetxt.setText((String)charSequence);
                        selectbedTime.setText("Edit");
                        bedhour = hourOfDay;
                         isClicked1 = true;




                    }
                }, Hour, Minute, true);
                timePickerDialog.show();

            }
        });

        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // passing data with shared pref


                if (weight.getText().toString().equalsIgnoreCase("")) {
                    weight.setError("This field can not be blank");
                    return;
                }

                if (workout.getText().toString().equalsIgnoreCase("")) {
                    workout.setText("0");

                }
//
//                if (bedtime.getText().toString().equalsIgnoreCase("")) {
//                        bedtime.setText("0");
//                }
//
//                if (wakeupTime.getText().toString().equalsIgnoreCase("")) {
//                    wakeupTime.setText("0");
//                }
                editor.putString("weight" , weight.getText().toString());
                editor.putString("workout" , workout.getText().toString());
                editor.putString("wakeupTime" ,wakeuptxt.getText().toString());
                editor.putString("bedtime" , bedtimetxt.getText().toString());
                booleanEditor.putBoolean("personal_Information",true);
                booleanEditor.commit();
                editor.commit();



                bundle.putString("weight" , weight.getText().toString());
                bundle.putString("workout" , workout.getText().toString());
                bundle.putString("bedtime" , wakeuptxt.getText().toString());
                bundle.putString("wakeupTime" , bedtimetxt.getText().toString());



                if(!isClicked || !isClicked1 ){
                    Log.e("wakeour is :","-1");
                    SharedPreferences sharedPreferences = getSharedPreferences("-1",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("-1",-1);
                    editor.commit();

                    PersonalInformation pi = new PersonalInformation(Integer.parseInt(workout.getText().toString()), wakeuptxt.getText().toString(), bedtimetxt.getText().toString(),Double.parseDouble(weight.getText().toString()));
                    String Goal = String.valueOf((int) pi.GoalCalculator());
                    SharedPreferences sharedPreferences1 = getSharedPreferences("progress", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                    editor1.putString("goalState", Goal);
                    editor1.commit();
                }
                else {

//                    SharedPreferences sharedPreferences = getSharedPreferences("isFirstTime", Context.MODE_PRIVATE);
//                    boolean isFirstTime = sharedPreferences.getBoolean("isFirstTime", false);
//                    Log.d("firstTimeBuddy?", String.valueOf(isFirstTime));
//                    if (isFirstTime) {
                        //send data for notification
                        //create notification channel
                        NotificationUtils _notificationUtils = new NotificationUtils(Pi.this);
                        int timeDeference = bedhour - wakeHour;
                        Log.d("timeDiffrence", String.valueOf(timeDeference));

                        PersonalInformation pi = new PersonalInformation(Integer.parseInt(workout.getText().toString()), wakeuptxt.getText().toString(), bedtimetxt.getText().toString(),Double.parseDouble(weight.getText().toString()));
                        String Goal = String.valueOf((int) pi.GoalCalculator());
                        Cups c = new Cups();

                        int sizeOfCup = c.getCupSize();
                        int NumberOfCups = Integer.parseInt(Goal) / Integer.parseInt(String.valueOf(sizeOfCup));
                        int plusTime = timeDeference / NumberOfCups;
                        Log.d("sizeOfCup", String.valueOf(sizeOfCup));
                            Log.d("NumberOfCups", String.valueOf(NumberOfCups));
                            Log.d("plusTime", String.valueOf(plusTime));
                        //
                        //
                        Random random1 = new Random();
                        if (plusTime < 1) {
                            for (int i = 0; i <= NumberOfCups; i++) {
                                //generate random number for each notification

                                int randomId = random1.nextInt();
                                _notificationUtils.setReminder(wakeHour, 00, randomId);
                                //create an instanse of reminder holder for front-end
                                String time = wakeHour + ":00";
                                Log.e("Time",time);
                                NotificationReminder reminderObject = new NotificationReminder();
                                NotificationReminder.ReminderHolder reminderHolder = reminderObject.new ReminderHolder(randomId, time);
                                NotificationReminder.times.add(reminderHolder);
                                reminderObject.saveData(Pi.this);
                                if(wakeHour == 24 )
                                    wakeHour = 0;
//                              NotificationReminder.ReminderHolder rh = new NotificationReminder.ReminderHolder();
                                wakeHour += 1;


                            }
                        } else {
                            for (int i = 0; i <= NumberOfCups; i++) {


                                int randomId = random1.nextInt();
                                _notificationUtils.setReminder(wakeHour, 00, randomId);
                                //create an instanse of reminder holder for front end

                                String time = wakeHour + ":00";
                                Log.e("Time",time);
                                NotificationReminder reminderObject = new NotificationReminder();
                                NotificationReminder.ReminderHolder reminderHolder = reminderObject.new ReminderHolder(randomId, time);
                                NotificationReminder.times.add(reminderHolder);
                                reminderObject.saveData(Pi.this);
                                if(wakeHour == 24)
                                    wakeHour = 0;
                                wakeHour += plusTime;

                            }
                        }
                    }





                fragobj.setArguments(bundle);
                Intent startintent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(startintent);
            }
        });

    }
}