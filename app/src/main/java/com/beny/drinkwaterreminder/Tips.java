package com.beny.drinkwaterreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Tips extends AppCompatActivity {
    public static ArrayList<String> Tips ;
    ListView listView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
        listView = findViewById(R.id.tipsListView);
        ArrayAdapter<String> adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1 ,Tips);
        listView.setAdapter(adapter);
    }

    public static void setArrayList() {
        Tips = new ArrayList<>();
        Tips.add("Drink your glass of water slowly with\n some small sips");
        Tips.add("Hold the water in your mouth for a while \nbefore swallowing");
        Tips.add("Drinking water in a sitting posture is better \n than in a standing or runnning position");
        Tips.add("Do not drink water immediately after eating");
        Tips.add("Do not drink cold water immediately after\n hot drinks like tea or coffee");
        Tips.add("Do not drink cold water or water with ice");

    }
}