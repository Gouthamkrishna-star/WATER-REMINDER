package com.beny.drinkwaterreminder;

public class Cups {
    //:D
    private int cupSize = 250;

    public Cups(int cupSize){
        this.cupSize = cupSize;
    }
    public Cups(){

    }


    public int getCupSize(){
        return cupSize;
    }
    public void setCupSize(int cupSize){
        this.cupSize = cupSize;
    }
}
