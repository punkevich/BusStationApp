package com.example.busstationapp;

public class SelectedDate {
    int Year;
    int Month;
    int Day;

    public SelectedDate() {

    }
    public SelectedDate(int year, int month, int day) {
        Year = year;
        Month = month;
        Day = day;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }

    public int getMonth() {
        return Month;
    }

    public void setMonth(int month) {
        Month = month;
    }

    public int getDay() {
        return Day;
    }

    public void setDay(int day) {
        Day = day;
    }

    public String showDate() {
        String result = Integer.toString(this.Month) + "-" +
                Integer.toString(this.Day) + "-" +
                Integer.toString(this.Year);
        return result;
    }
}