package com.beny.drinkwaterreminder;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

public class DailyGoalDialog extends DialogFragment {
    private EditText dailyGoalText;
    private TextView recomendedGoal;


//        @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        try {
//            this.listener = (DialogListener) activity ;
//        } catch (final ClassCastException e) {
//            throw  new ClassCastException(activity.toString() + "must implement DialogListener");
//        }
//
//    }
//    @Override
//    public void onAttach(  Activity activity) {
//        super.onAttach(activity);
//        try {
//            listener = (DialogListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString() + " must implement listeners!");
//        }
//    }


    @NonNull

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);
        dailyGoalText = view.findViewById(R.id.daily_goal_txt);
        recomendedGoal = view.findViewById(R.id.recGoal);

        SharedPreferences sp = getActivity().getApplicationContext().getSharedPreferences("first", Context.MODE_PRIVATE);
        String weight = sp.getString("weight", "0");
        String workout = sp.getString("workout", "0");
        String wakeuptime = sp.getString("wakeupTime", "0");
        String bedtime = sp.getString("bedtime", "0");

        PersonalInformation pi = new PersonalInformation(Integer.parseInt(workout), wakeuptime, bedtime, Double.parseDouble(weight));
        double goal = pi.GoalCalculator();
        recomendedGoal.setText(String.valueOf(goal));


        builder.setView(view).setTitle("Daily goal")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String goalText = dailyGoalText.getText().toString();
                boolean digitsOnly = TextUtils.isDigitsOnly(dailyGoalText.getText());


                if (goalText.equalsIgnoreCase("")) {
                    Toast.makeText(getContext(), "please Enter positive numbers", Toast.LENGTH_SHORT).show();
                    dailyGoalText.setError("This field is empty!");
                } else if (digitsOnly) {
                    Toast.makeText(getContext(), goalText, Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("progress", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("goalState", goalText);
                    editor.putBoolean("personal_Information", false);
                    editor.commit();

                } else {
                    Toast.makeText(getContext(), "please Enter positive numbers", Toast.LENGTH_SHORT).show();
                }


//
            }
        });


        return builder.create();


    }


}
