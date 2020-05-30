package com.example.busstationapp;

import com.google.firebase.database.DatabaseReference;

public class Trip {
    private DatabaseReference mDatabase;

    String  arrival_time;
    String  count_free;
    String  count_occup;
    String  departure_id;
    String  departure_time;
    String  destination_id;
    String  number_of_occup;
    String  price;
    String  trip_id;
    String  typetrans_od;

    public Trip() {
    }

    public Trip(DatabaseReference mDatabase, String arrival_time, String count_free, String count_occup, String departure_id, String departure_time, String destination_id, String number_of_occup, String price, String trip_id, String typetrans_od) {
        this.mDatabase = mDatabase;
        this.arrival_time = arrival_time;
        this.count_free = count_free;
        this.count_occup = count_occup;
        this.departure_id = departure_id;
        this.departure_time = departure_time;
        this.destination_id = destination_id;
        this.number_of_occup = number_of_occup;
        this.price = price;
        this.trip_id = trip_id;
        this.typetrans_od = typetrans_od;
    }

    public Trip(Trip u)
    {
        this(
                u.mDatabase,
                u.arrival_time,
                u.count_free,
                u.count_occup,
                u.departure_id,
                u.departure_time,
                u.destination_id,
                u.number_of_occup,
                u.price,
                u.trip_id,
                u.typetrans_od
        );
    }



    public DatabaseReference getmDatabase() {
        return mDatabase;
    }

    public void setmDatabase(DatabaseReference mDatabase) {
        this.mDatabase = mDatabase;
    }

    public String getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(String arrival_time) {
        this.arrival_time = arrival_time;
    }

    public String getCount_free() {
        return count_free;
    }

    public void setCount_free(String count_free) {
        this.count_free = count_free;
    }

    public String getCount_occup() {
        return count_occup;
    }

    public void setCount_occup(String count_occup) {
        this.count_occup = count_occup;
    }

    public String getDeparture_id() {
        return departure_id;
    }

    public void setDeparture_id(String departure_id) {
        this.departure_id = departure_id;
    }

    public String getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(String departure_time) {
        this.departure_time = departure_time;
    }

    public String getDestination_id() {
        return destination_id;
    }

    public void setDestination_id(String destination_id) {
        this.destination_id = destination_id;
    }

    public String getNumber_of_occup() {
        return number_of_occup;
    }

    public void setNumber_of_occup(String number_of_occup) {
        this.number_of_occup = number_of_occup;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(String trip_id) {
        this.trip_id = trip_id;
    }

    public String getTypetrans_od() {
        return typetrans_od;
    }

    public void setTypetrans_od(String typetrans_od) {
        this.typetrans_od = typetrans_od;
    }
}
