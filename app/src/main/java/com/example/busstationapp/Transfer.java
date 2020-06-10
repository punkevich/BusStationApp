package com.example.busstationapp;

import java.util.ArrayList;
import java.util.List;

public class Transfer {
    List<Line> Lines;

    List<String> transferStation;
    String departure;
    String destination;

    public Transfer() {
        transferStation = new ArrayList<>();
        Lines = new ArrayList<>();
    }

    public Transfer(String departure, String destination) {
        transferStation = new ArrayList<>();
        Lines = new ArrayList<>();
        Lines.add(Line.fillLineM1());
        Lines.add(Line.fillLineM5());
        this.departure = departure;
        this.destination = destination;
    }

    public boolean buildRoute() {
        boolean Result = false;
        List<String> lineOfDeparture = new ArrayList<>();
        int indexLineOfDeparture = -1;
        for (int i = 0; i < Lines.size(); i++) {
            if (Lines.get(i).contains(departure)) {
                lineOfDeparture = Lines.get(i).getStations();
                indexLineOfDeparture = i;
                break;
            }
        }

        for (int i = 0; i < lineOfDeparture.size(); i++) {
            for (int j = 0; j < Lines.size(); j++) {
                if (j == indexLineOfDeparture)
                    continue;
                if (Lines.get(j).contains(lineOfDeparture.get(i))) {
                    for (int q = 0; q < Lines.get(j).getStations().size(); q++) {
                        if (Lines.get(j).getStations().get(q).equals(destination)) {
                            transferStation.add(lineOfDeparture.get(i));
                            Result = true;
                            return Result;
                        }
                    }
                }
            }
        }
        return Result;
    }

}
