package com.example.busstationapp;

public class Place {

    public static int type_1 = 20;
    public static int type_2 = 30;

    private int number;
    private boolean status;

    public Place(int number, boolean status) {
        this.number = number;
        this.status = status;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
