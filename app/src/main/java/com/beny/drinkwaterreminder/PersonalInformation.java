package com.beny.drinkwaterreminder;

import android.util.Log;

public class PersonalInformation {

    private int workOut  = 0 ;
    private String   wakeUpTime , bedTime ;
    private Double Weight ;

    public PersonalInformation(){
        this.Weight = Weight;

    }

    public PersonalInformation(int workOut , Double Weight){
        this.Weight = Weight;
        this.workOut = workOut;

    }

    public PersonalInformation(int workOut ,String wakeUpTime, String bedTime, Double Weight ){
        this.workOut=workOut;
        this.Weight = Weight;
        this.wakeUpTime = wakeUpTime;
        this.bedTime = bedTime;
    }

    public int getworkOut() {
        return workOut;
    }

    public void setworkOut(int workOut) {
        this.workOut = workOut;
    }

    public String getWakeUpTime() {
        return wakeUpTime;
    }

    public void setWakeUpTime(String wakeUpTime) {
        this.wakeUpTime = wakeUpTime;
    }

    public String getBedTime() {
        return bedTime;
    }

    public void setBedTime(String bedTime) {
        this.bedTime = bedTime;
    }

    public Double getWeight() {
        return Weight;
    }

    public void setWeight(Double weight) {
        Weight = weight;
    }

    public double GoalCalculator() {

        double pound ;
        double mililiter ;
        double ounce;
        double temp ;


        Log.d("weigt in goalcalc" , String.valueOf(this.getWeight()));
        Log.d("work out here" , String.valueOf(this.getworkOut()));
        //kg to pound
//        pound = Weight * 2.205;
//
//        pound *=(2/3);
//        //ounce to mil
//        mililiter =  (pound * 29.574);
//        if(workOut == 0 ){
//            return mililiter;
//        }
//        else{
//            ounce = (pound + ((workOut/30)*12));
//            Log.d("ounce" , String.valueOf(ounce));
//            //ounce to mil
//            mililiter = (ounce * 29.574);
//            Log.d("mil" , String.valueOf(mililiter));
//            return mililiter;
//        }
        pound = Weight *2.205 ;

        if ( workOut == 0 ){
          pound *=0.666;
            mililiter = pound*29.574;
            return mililiter;
        }else
        {
            temp = workOut/30;
            temp*=12;
            ounce = pound+temp;
            mililiter = ounce * 29.574;
            return  mililiter;
        }
    }
}
