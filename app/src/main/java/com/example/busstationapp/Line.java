package com.example.busstationapp;

import java.util.ArrayList;
import java.util.List;

public class Line {
    public static int countLines = 2;
    List<String> Stations;

    public Line() {
        Stations = new ArrayList<>();
    }

    public Line(List<String> stations) {
        Stations = stations;
    }

    public static Line fillLineM1() {
        Line result = new Line();
        List<String> temp = new ArrayList<>();
        temp.add("Москва");
        temp.add("Вязьма");
        temp.add("Смоленск");
        temp.add("Борисов");
        temp.add("Минск");
        result.setStations(temp);
        return result;
    }

    public static Line fillLineM5() {
        Line result = new Line();
        List<String> temp = new ArrayList<>();
        temp.add("Москва");
        temp.add("Рязань");
        temp.add("Пенза");
        temp.add("Самара");
        temp.add("Челябинск");
        result.setStations(temp);
        return result;
    }

    public boolean contains(String station) {
        return Stations.contains(station);
    }

    public List<String> getStations() {
        return Stations;
    }

    public void setStations(List<String> stations) {
        Stations = stations;
    }

    public int getLineSize() {
        return Stations.size();
    }
}
