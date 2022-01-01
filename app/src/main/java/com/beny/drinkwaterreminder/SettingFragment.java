package com.beny.drinkwaterreminder;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<String> settingList;
    boolean isChecked ;
    public SettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
//        changeTheme(isChecked);

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_setting, container, false);
        ListView lv = root.findViewById(R.id.ListView);

        settingList = new ArrayList<String>();
        settingList.add("Notification Manager");
        settingList.add("Cups Manager");
        settingList.add("Personal information");
        settingList.add("Daily goal");
        settingList.add("Contact us");
        settingList.add("Share");


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, settingList);
        lv.setAdapter(arrayAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem = (String) lv.getItemAtPosition(position);
//                Toast.makeText(getContext(),clickedItem,Toast.LENGTH_LONG).show();
                switch (clickedItem) {

                    case "Notification Manager": {
                        Intent intent = new Intent(getContext(), NotificationReminder.class);
                        startActivity(intent);
                        break;
                    }
                    case "Cups Manager": {
                        Intent intent = new Intent(getContext(), CupsManager.class);
                        startActivity(intent);
                        break;
                    }
                    case "Personal information": {

                        Intent piIntent = new Intent(getContext(), Pi.class);
                        startActivity(piIntent);
                        break;
                    }
                    case "Daily goal": {

                        openDialog();
                        break;
                    }
                    case "Contact us": {

                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("message/rfc822");
                        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"drinkwaterreminder@yahoo.com"});
                        i.putExtra(Intent.EXTRA_SUBJECT, "");
                        i.putExtra(Intent.EXTRA_TEXT, "");
                        try {
                            startActivity(Intent.createChooser(i, "Send mail..."));
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(getContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                    case "Share": {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, "Download DrinkWaterReminder \n ");
                        sendIntent.setType("text/plain");

                        Intent shareIntent = Intent.createChooser(sendIntent, null);
                        startActivity(shareIntent);
                        break;
                    }
                }
//                getCheckItemIds()
            }
        });

//        Switch themeSwitch = root.findViewById(R.id.switchBtn);
//        isChecked =  themeSwitch.isChecked();
//        changeTheme(isChecked);
//        changeTheme(isChecked);
//
//
        return root;
    }
//
//    private void changeTheme(boolean isChecked) {
//        if (isChecked) {
//
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
////            getActivity().getApplication().setTheme(R.style.Theme_MaterialComponents_Light_DarkActionBar);
//
//
//
//        } else {
////            getActivity().getApplication().setTheme(R.style.Theme_MaterialComponents_Light_DarkActionBar);
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        }
//    }

    public void openDialog() {

//        DailyGoalDialog dailyGoalDialog = new DailyGoalDialog();
//        dailyGoalDialog.show(getActivity().getSupportFragmentManager(), "Daily goal");
//        Intent intent = new Intent(getContext() , MainActivity.class);
//        intent.putExtra("Clicked" , true) ;
//        startActivity(intent);
//
//
        // From another Fragment or Activity where you wish to show this
        // PurchaseConfirmationDialogFragment.
        new DailyGoalDialog().show(
                getChildFragmentManager(), "Daily goal");
    }
}