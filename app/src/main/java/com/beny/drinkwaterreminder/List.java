package com.beny.drinkwaterreminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class List extends AppCompatActivity {
    RecyclerView recyclerView;
    Button delete;
    MyAdapter myAdapter = new MyAdapter(this);
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

//        delete = findViewById(R.id.deleteBtn);
        recyclerView = findViewById(R.id.recycleView);
        LoadData();

//        myAdapter.setArrayList(DrinkedList.Drinked);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        context = getApplicationContext();
        if(context==null)
            Log.e("null",null);
        Log.d("Contex ", String.valueOf(context));


    }

    public void LoadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("saveArrayList", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("drinkObj", null);
        Type type = new TypeToken<ArrayList<DrinkedList>>() {
        }.getType();
        DrinkedList.Drinked = gson.fromJson(json, type);
        if (DrinkedList.Drinked == null) {
            DrinkedList.Drinked = new ArrayList<>();
        }
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//            myAdapter.getArrayList().remove(viewHolder.getAbsoluteAdapterPosition());
            int ml = DrinkedList.Drinked.get(viewHolder.getAbsoluteAdapterPosition()).getMl();
            DrinkedList.Drinked.remove(viewHolder.getAbsoluteAdapterPosition());
            myAdapter.notifyItemRemoved(viewHolder.getAbsoluteAdapterPosition());
            saveData(getBaseContext());

//            List l = new List();
            transfer_ml(ml);
//            HomeFragment homeFragment = new HomeFragment();
//            homeFragment.saveData();


        }
    };

    public  void saveData(Context context) {
        SharedPreferences saveArrayList = context.getSharedPreferences("saveArrayList", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = saveArrayList.edit();
        //save arrayList

        Gson gson = new Gson();
        String json = gson.toJson(DrinkedList.Drinked);
        editor.putString("drinkObj",json);
        editor.apply();


    }

    public void transfer_ml(int ml) {
//        SharedPreferences sharedPreferences = getSharedPreferences("delCup", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putInt("delCup" , ml);
//        editor.commit();
        SharedPreferences sharedPreferences = context.getSharedPreferences("deleteCup", Context.MODE_PRIVATE);
        int mililitr = sharedPreferences.getInt("deleteCup",0);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        ml+=mililitr;
        editor.putInt("deleteCup",ml);
        editor.commit();


//        Log.d("contex in method", String.valueOf(context));
//        Intent intent = new Intent(context, MainActivity.class);
//
//        intent.putExtra("ml", ml);
//        context.startActivity(intent);




    }
}