package com.beny.drinkwaterreminder;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder> {
    Context context;

    public static final String Tag  = "Reminder Adapter";
    public ReminderAdapter(Context context) {
        this.context = context;

    }

    @NonNull

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reminder_row, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        boolean is24HourFormat = DateFormat.is24HourFormat(context);
        Log.d(Tag , "OnBindViewHilder: called");
        holder.txtTime.setText(NotificationReminder.times.get(position).toString());
        Log.d("list in Adapter" , String.valueOf(NotificationReminder.times));
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                NotificationUtils obj = new NotificationUtils(context);
//                obj.deleteReminder(NotificationReminder.times.get(position).getId());
//
//                NotificationReminder.times.remove(position);
                Toast.makeText(context, "Swipe items to  removed!", Toast.LENGTH_SHORT).show();
//
//                notifyItemRemoved(position);
//                notifyItemChanged(position);
//                NotificationReminder notificationReminder = new NotificationReminder();
//                notificationReminder.saveData(context);



            }
        });

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationReminder newObj = new NotificationReminder(context);
                //show a time picker
                Calendar calendar = Calendar.getInstance();
                int Hour = calendar.get(Calendar.HOUR);
                int Minute = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        String am_pm = "";

                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(Calendar.HOUR, hourOfDay);
                        calendar1.set(Calendar.MINUTE, minute);

                        CharSequence charSequence = DateFormat.format("hh:mm a", calendar1);
                        Log.d("log", (String) charSequence);

                        //edit object
                        int ObjectID = NotificationReminder.times.get(position).getId();
                        NotificationReminder.times.get(position).setTime((String) charSequence);
                        Log.d("id in adapter", String.valueOf(ObjectID));
                        Log.d("Object time ", NotificationReminder.times.get(position).getTime());
                        //TO DO
                        //edit view when clicked ok save and load data



//                        Log.d("list", String.valueOf(times));

//                        newObj.mRecyclerView.post(new Runnable() {
//                                               @Override
//                                               public void run() {
//                                                   Adapter.notifyDataSetChanged();
//                                               }
//                                           }
//                        );
                        setNotification(hourOfDay, minute, ObjectID);
                        newObj.saveData(context);
                        notifyItemChanged(position);
                        Toast.makeText(context, "your reminder successfully edited", Toast.LENGTH_SHORT).show();
                    }
                }, Hour, Minute, is24HourFormat);
                timePickerDialog.show();


                SharedPreferences sharedPreferences = context.getSharedPreferences("isDefaultSetting",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isDefaultSetting",false);
                editor.commit();
                newObj.saveData(context);



            }



//
//            }
//        });
//
    });
    }

    @Override
    public int getItemCount() {
        return NotificationReminder.times.size();
    }

    public void setNotification(int hour, int minute, int id) {

        NotificationUtils notifObj = new NotificationUtils(context);
        notifObj.setReminder(hour, minute, id);

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        Button deleteBtn , editBtn;
        TextView txtTime;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            deleteBtn = itemView.findViewById(R.id.delBtn);
            txtTime = itemView.findViewById(R.id.txtTime);
            parentLayout = itemView.findViewById(R.id.parentLayout);
            editBtn = itemView.findViewById(R.id.editBtn);
        }
    }
}
