package com.example.busstationapp;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Ticket {
    public String station_id;
    public String date;
    public String trip_id;
    public String place_number;

    public Ticket() {

    }

    public Ticket(String station_id, String date, String trip_id, String place_number) {
        this.station_id = station_id;
        this.date = date;
        this.trip_id = trip_id;
        this.place_number = place_number;
    }

    public String getStation_id() {
        return station_id;
    }

    public void setStation_id(String station_id) {
        this.station_id = station_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(String trip_id) {
        this.trip_id = trip_id;
    }

    public String getPlace_number() {
        return place_number;
    }

    public void setPlace_number(String place_number) {
        this.place_number = place_number;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("station_id", station_id);
        result.put("date", date);
        result.put("trip_id", trip_id);
        result.put("place_number", place_number);

        return result;
    }
}
