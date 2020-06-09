package com.example.busstationapp;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Ticket {
    public String station_id;
    public String date;
    public String trip_id;
    public String place_number;
    public String departure_id;
    public String destination_id;
    public String departure_time;
    public String destination_date;
    public String arrival_time;


    public Ticket() {

    }


    public Ticket(String station_id, String date, String trip_id, String place_number,
                  String departure_id, String destination_id, String departure_time,
                  String destination_date, String arrival_time) {
        this.station_id = station_id;
        this.date = date;
        this.trip_id = trip_id;
        this.place_number = place_number;
        this.departure_id = departure_id;
        this.destination_id = destination_id;
        this.departure_time = departure_time;
        this.destination_date = destination_date;
        this.arrival_time = arrival_time;
    }

    public String getDestination_date() {
        return destination_date;
    }

    public void setDestination_date(String destination_date) {
        this.destination_date = destination_date;
    }

    public String getDeparture_id() {
        return departure_id;
    }

    public void setDeparture_id(String departure_id) {
        this.departure_id = departure_id;
    }

    public String getDestination_id() {
        return destination_id;
    }

    public void setDestination_id(String destination_id) {
        this.destination_id = destination_id;
    }

    public String getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(String departure_time) {
        this.departure_time = departure_time;
    }

    public String getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(String arrival_time) {
        this.arrival_time = arrival_time;
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
        result.put("departure_id", departure_id);
        result.put("destination_id", destination_id);
        result.put("departure_time", departure_time);
        result.put("destination_date", destination_date);
        result.put("arrival_time", arrival_time);

        return result;
    }
}
