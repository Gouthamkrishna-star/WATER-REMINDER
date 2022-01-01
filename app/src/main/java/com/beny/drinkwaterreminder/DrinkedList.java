package com.beny.drinkwaterreminder;

import java.util.ArrayList;

public class DrinkedList {
    private String time;
    private int ml;
    public static ArrayList<DrinkedList> Drinked = new ArrayList<>();

    public DrinkedList( ) {

    }

    public DrinkedList(String time, int ml) {
        this.time = time;
        this.ml = ml;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getMl() {
        return ml;
    }

    public void setMl(int ml) {
        this.ml = ml;
    }


    public String toString() {
      String result = "";
      result =  time +" , "+  ml + "ml" ;
        return result;
    }




//    public  void LoadData() {
//        SharedPreferences sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("saveArrayList", Context.MODE_PRIVATE);
//        Gson gson = new Gson();
//        String json = sharedPreferences.getString("drinkObj",null);
//        Type type = new TypeToken<ArrayList<DrinkedList>>() {}.getType();
//        Drinked = gson.fromJson(json ,type);
//        if(Drinked == null)
//        {
//            Drinked = new ArrayList<>();
//        }
//    }
}