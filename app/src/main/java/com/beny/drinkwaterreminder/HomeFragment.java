package com.beny.drinkwaterreminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.logging.SocketHandler;

import static java.text.DateFormat.SHORT;
import static java.text.DateFormat.getTimeInstance;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final int Reset_id = 12;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button drinkbtn, Listbtn, cupsManagerbtn;
    private String weight, bedtime, wakeuptime, workout, sizeOfCup;
    SharedPreferences sp, progressState;
    //    public final String PREFTAG = "delCup";
    TextView goalText, progressText, txtTips;
    ProgressBar drinkProgressBar;
    Context context;


    int timeDeference, NumberOfCups = 1, plusTime = 0, id = 0, deleteCup;


    public boolean personal_Information = true;


    public HomeFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
//
//        MainActivity mainActivity = new MainActivity();
//        mainActivity.checkFirstRun();
        Log.e("OnCreate Home Fragment ", "Called");
        NotificationUtils _notificationUtils = new NotificationUtils(getContext());
        context = getContext();
        setResettingProgressBar();
        //TO DO  just run this line one time


    }


    View rootView;
    String Goal, progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("onCreateView","called");

        // Inflate the layout for this fragment
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);
        //initialize progressbar
        drinkProgressBar = rootView.findViewById(R.id.progressBar);
        cupsManagerbtn = rootView.findViewById(R.id.changeCup);
        txtTips = rootView.findViewById(R.id.tipstxt);
        //setTips text buy random number
        Tips.setArrayList();
        Random random = new Random();
        int randomInteger = random.nextInt(6);
        txtTips.setText(Tips.Tips.get(randomInteger));


        txtTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show more tips
                Intent intent = new Intent(getContext(), Tips.class);
                startActivity(intent);
            }
        });
        if (getArguments() != null) {
            weight = getArguments().getString("weight");
            workout = getArguments().getString("workout");
            wakeuptime = getArguments().getString("wakeupTime");
            bedtime = getArguments().getString("bedtime");
        }
        drinkbtn = rootView.findViewById(R.id.drinkWaterBtn);
        Listbtn = rootView.findViewById(R.id.List);

        sp = getActivity().getApplicationContext().getSharedPreferences("first", Context.MODE_PRIVATE);
        weight = sp.getString("weight", "0");
        workout = sp.getString("workout", "0");
        wakeuptime = sp.getString("wakeupTime", "0");
        bedtime = sp.getString("bedtime", "0");


        progressState = getActivity().getApplicationContext().getSharedPreferences("progress", Context.MODE_PRIVATE);
        progress = progressState.getString("progressState", "0");
        Log.e("Progress:D",progress);
        Goal = progressState.getString("goalState", "0");
        Log.d("goal after  recive ", Goal);
        progressState = getActivity().getApplicationContext().getSharedPreferences("cupsize", Context.MODE_PRIVATE);
        sizeOfCup = progressState.getString("cupsize", "250");
        Log.d("size of cup", sizeOfCup);
        drinkProgressBar.setMax(Integer.parseInt(Goal));
        drinkProgressBar.incrementProgressBy(Integer.parseInt(progress));

        initViews();


        int wakeHour = hourExtracter(wakeuptime);
//        int wakeMinute = minuteExtracter(wakeuptime);
        int bedHour = hourExtracter(bedtime);
        // int bedMinute = minuteExtracter(bedtime);

        if(wakeHour != -1) {
            PersonalInformation pi = new PersonalInformation(Integer.parseInt(workout), wakeuptime, bedtime, Double.parseDouble(weight));
            Goal = String.valueOf((int) pi.GoalCalculator());
        }
        timeDeference = bedHour - wakeHour;
        Log.e("fuckin goal", Goal);
        Log.e("Fuckin size of cup ", sizeOfCup);
        NumberOfCups = Integer.parseInt(Goal) / Integer.parseInt(sizeOfCup);
        if (NumberOfCups == 0)
            NumberOfCups = 1;
        plusTime = timeDeference / NumberOfCups;

        Log.e("wakeHour", String.valueOf(wakeHour));
        Log.e("time diff", String.valueOf(timeDeference));
        Log.e("plus Time", String.valueOf(plusTime));
        Log.e("number of cups", String.valueOf(NumberOfCups));
//            if(wakeHour == -1 ){
//                Log.e("wakeour is :","-1");
//                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("-1",Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putInt("-1",-1);
//                editor.commit();
//            }
//            else {
//
//                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("isFirstTime", Context.MODE_PRIVATE);
//                boolean isFirstTime = sharedPreferences.getBoolean("isFirstTime", false);
//                Log.d("firstTimeBuddy?", String.valueOf(isFirstTime));
//                if (isFirstTime) {
//                    //send data for notification
//                    //create notification channel
//                    NotificationUtils _notificationUtils = new NotificationUtils(getContext());
//                    //
//                    //
//                    Random random1 = new Random();
//                    if (plusTime < 1) {
//                        for (int i = 0; i <= NumberOfCups; i++) {
//                            //generate random number for each notification
//
//                            int randomId = random1.nextInt();
//                            _notificationUtils.setReminder(wakeHour, 00, randomId);
//                            //create an instanse of reminder holder for front end
//                            String time = wakeHour + ":00";
//                            NotificationReminder reminderObject = new NotificationReminder();
//                            NotificationReminder.ReminderHolder reminderHolder = reminderObject.new ReminderHolder(randomId, time);
//                            NotificationReminder.times.add(reminderHolder);
//                            reminderObject.saveData(getContext());
////                              NotificationReminder.ReminderHolder rh = new NotificationReminder.ReminderHolder();
//                            wakeHour += 1;
//
//
//                        }
//                    } else {
//                        for (int i = 0; i <= NumberOfCups; i++) {
//
//
//                            int randomId = random1.nextInt();
//                            _notificationUtils.setReminder(wakeHour, 00, randomId);
//                            //create an instanse of reminder holder for front end
//                            String time = wakeHour + ":00";
//                            NotificationReminder reminderObject = new NotificationReminder();
//                            NotificationReminder.ReminderHolder reminderHolder = reminderObject.new ReminderHolder(randomId, time);
//                            NotificationReminder.times.add(reminderHolder);
//                            reminderObject.saveData(getContext());
//                            wakeHour += plusTime;
//
//                        }
//                    }
//                }
//
//            }
        //if any cup going to delete
        final Handler handler = new Handler();
        final int delay = 5000; // 1000 milliseconds == 1 second

        handler.postDelayed(new Runnable() {
            public void run() {
                Log.d("handler!","hi"); // Do your work here
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("deleteCup",Context.MODE_PRIVATE);
                int ml = sharedPreferences.getInt("deleteCup",0);

                drinkProgressBar.incrementProgressBy(-ml);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("deleteCup",0);
                editor.commit();

                progressState = getActivity().getApplicationContext().getSharedPreferences("progress", Context.MODE_PRIVATE);
                progress = String.valueOf(drinkProgressBar.getProgress());
                initViews();
                saveData();
                handler.postDelayed(this, delay);
            }
        }, delay);


        /*
        SharedPreferences pref = getActivity().getSharedPreferences(MainActivity.PrefTag, Context.MODE_PRIVATE);
        boolean isDeleteCup = pref.getBoolean("isDelete", false);
        int mililiter = pref.getInt("ml", 200);
        Log.e("isDeleteCup", String.valueOf(isDeleteCup));
        if (isDeleteCup) {
            drinkProgressBar.incrementProgressBy(-mililiter);
            isDeleteCup = false;
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean(MainActivity.PrefTag, false);
            editor.commit();
            progressState = getActivity().getApplicationContext().getSharedPreferences("progress", Context.MODE_PRIVATE);
            progress = String.valueOf(drinkProgressBar.getProgress());
            initViews();
                      saveData();
            //this is not working:( so i comment it

        }

         */

            /*if clocked passed 24
            resetting  our progress bar
             */
        SharedPreferences ResetPref = getActivity().getSharedPreferences("Reset", Context.MODE_PRIVATE);
        boolean is24passed = ResetPref.getBoolean("Reset", false);
        Log.d("isPassed24", String.valueOf(is24passed));
        if (is24passed) {
            drinkProgressBar.setProgress(0);
            is24passed = false;
            SharedPreferences.Editor editor = ResetPref.edit();
            editor.putBoolean("Reset", false);
            editor.commit();
            Log.d("isPassed24 changed", String.valueOf(is24passed));
            progress = String.valueOf(drinkProgressBar.getProgress());
            initViews();

        }

        drinkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp = getActivity().getApplicationContext().getSharedPreferences("first", Context.MODE_PRIVATE);
                progressState = getActivity().getApplicationContext().getSharedPreferences("progress", Context.MODE_PRIVATE);
                personal_Information = progressState.getBoolean("personal_Information", true);
                //                personal_Information =  sp.getBoolean("personal_Information" , true);

                Log.e("boolean", String.valueOf(personal_Information));
                checkPersonalInfo(personal_Information);
                drinkProgressBar.setMax(Integer.parseInt(Goal));
                drinkProgressBar.incrementProgressBy(Integer.parseInt(sizeOfCup));

                //////////////////////moooo
//                    if(calendar.after(now)){
//                        //reset drink progressbar //one day passed
//
//                        drinkProgressBar.setProgress(0);
//                        Log.d("new Day", "progress : 0");
//                    }

                Goal = String.valueOf(drinkProgressBar.getMax());
                progress = String.valueOf(drinkProgressBar.getProgress());
                initViews();
                if (Goal.equalsIgnoreCase(progress)) {

                    Toast.makeText(getActivity(), "congratulations you reached your goal", Toast.LENGTH_SHORT).show();

                }

                //                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+4:30"));
                //                Date currentLocalTime = cal.getTime();
                //                DateFormat date = new SimpleDateFormat("HH:mm a");
                //                // you can get seconds by adding  "...:ss" to it
                //                date.setTimeZone(TimeZone.getTimeZone("GMT+1:00"));
                //
                //                String localTime = date.format(currentLocalTime);
                String Time = getTimeInstance().format(new Date());


                //get current time and create new object for saving what we drink
                Date currentTime = Calendar.getInstance().getTime();
                Log.d("current time ", String.valueOf(currentTime));
                //                Log.d("local time" , localTime);
                DrinkedList drinkObj = new DrinkedList(Time, Integer.parseInt(sizeOfCup));

                //add in arrayList
                DrinkedList.Drinked.add(drinkObj);
                Log.d("drinked obj ", drinkObj.toString());


                saveData();


                Log.e("maxprogress", String.valueOf(drinkProgressBar.getMax()));
                Log.e("progress", String.valueOf(drinkProgressBar.getProgress()));
                //                Log.d("Cupsize", defSize + "");
                Log.d("weight", weight + "");
                Log.d("workout", workout + "");
                Log.d("bedtime", bedtime + "");
                Log.d("wake up", wakeuptime + "");
                Log.d("goal", Goal + "");
            }

        });


        Listbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listIntent = new Intent(getContext(), List.class);
                startActivity(listIntent);
            }
        });
        cupsManagerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CupsManager.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    private void setResettingProgressBar() {
        Log.d("setReset", "called");
//        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("isFirstTime", Context.MODE_PRIVATE);
//        boolean isFirstTime = sharedPreferences.getBoolean("isFirstTime", false);
//        Log.d("firstTIme? ", String.valueOf(isFirstTime));
//        if (isFirstTime) {

//        SharedPreferences sharedPreferences = getContext().getSharedPreferences("Reset", Context.MODE_PRIVATE);
//
//
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        Log.d("reset here", String.valueOf(sharedPreferences.getBoolean("Reset",false)));
//        if(!sharedPreferences.getBoolean("Reset",false)) {

            NotificationUtils n = new NotificationUtils(getContext());
            n.setReminder(24, 00, Reset_id);
//        }

//            isFirstTime = false;
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putBoolean("isFirstTime", false);
//            editor.commit();
//        } else
//            return;

    }

    private void checkPersonalInfo(boolean personal_information) {
        if (personal_information) {
            //first time we are open app or change personal or information recently
            SharedPreferences.Editor editor = sp.edit();

            PersonalInformation pi = new PersonalInformation(Integer.parseInt(workout), wakeuptime, bedtime, Double.parseDouble(weight));
            Goal = String.valueOf((int) pi.GoalCalculator());
            editor.putString("goalState", Goal);
            Cups cup = new Cups();

            //check
            if (sizeOfCup.equalsIgnoreCase("0")) {
                sizeOfCup = String.valueOf(cup.getCupSize());
                editor.putString("cupsize", (sizeOfCup));
                editor.commit();
            } else {
                //get previous size from shared prefrences
                sp = getActivity().getSharedPreferences("cupsize", Context.MODE_PRIVATE);
                sizeOfCup = sp.getString("cupsize", String.valueOf(cup.getCupSize()));

            }

        } else {
            /*
            get goal from daily goal dialog
             */
            sp = getActivity().getSharedPreferences("progress", Context.MODE_PRIVATE);
            Goal = sp.getString("goalState", "0");
            //get previous size from shared prefrences
            sp = getActivity().getSharedPreferences("cupsize", Context.MODE_PRIVATE);
            sizeOfCup = sp.getString("cupsize", "200");
        }
    }

    public void saveData() {
        SharedPreferences saveArrayList = getActivity().getSharedPreferences("saveArrayList", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = saveArrayList.edit();
        //save arrayList

        Gson gson = new Gson();
        String json = gson.toJson(DrinkedList.Drinked);
        editor.putString("drinkObj", json);
        editor.apply();
        String out = "";
        if (DrinkedList.Drinked.size() != 0) {
            for (int i = 0; i < DrinkedList.Drinked.size(); i++) {

                out += DrinkedList.Drinked.get(i).toString();
            }
            Log.d("arrayList", out);

        }
    }


    public void initViews() {
        goalText = (TextView) rootView.findViewById(R.id.goaltxt);
        progressText = (TextView) rootView.findViewById(R.id.Progresstxt);

        goalText.setText(Goal);
        progressText.setText(progress);
        Log.e("Progress Init",progress);
        //saving progress state
        SharedPreferences sh = getActivity().getSharedPreferences("progress", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        editor.putString("progressState", progress);
        editor.putString("goalState", Goal);
        editor.commit();


    }

//    public void makeNotification(int wakeHour , int  id) {
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//
//            NotificationChannel notificationChannel = new NotificationChannel(
//                    Notification_ID,
//                    Notification_Channel_Name,
//                    NotificationManager.IMPORTANCE_DEFAULT );
//
//            notificationChannel.setDescription(Description);
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
//        Intent intent = new Intent(getContext(), Notification_reciever.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(),
//                id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
//
//    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public int minuteExtracter(String Wakeup) {
        String extract = "";
        char character;
        boolean flag = false;
        int wakeMinute = -1;
        for (int j = 0; j < Wakeup.length(); j++) {
            character = Wakeup.charAt(j);
            if (flag) {
                extract += character;
            } else if (character == ':') {
                flag = true;
            }
            if (!extract.equalsIgnoreCase("")) {
                wakeMinute = Integer.parseInt(extract);
            }


        }
        return wakeMinute;
    }

    public static int hourExtracter(String Wakeup) {
        String extract = "";
        char character;
        int wakeHour = -1;
        if (Wakeup.equalsIgnoreCase(""))
            return -1;
        for (int i = 0; i < Wakeup.length(); i++) {
            character = Wakeup.charAt(i);
            if (character != ':') {
                extract += character;
                continue;
            }
            if (!extract.equalsIgnoreCase("")) {
                wakeHour = Integer.parseInt(extract);
                return wakeHour;
            } else
                return -1;

        }
        return wakeHour;
    }


    public void deleteCup(int ml) {
        boolean isDeleteCup = true;
        SharedPreferences preferences = MainActivity.context.getSharedPreferences(MainActivity.PrefTag, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("ml", ml);
        editor.putBoolean("isDeleteCup", true);
        editor.apply();
//    Intent intent = new Intent(MainActivity.context,MainActivity.class);
//    MainActivity.context.startActivity(intent);
    }
}