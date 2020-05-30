package com.example.busstationapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends Fragment {

    FirebaseAuth        mAuth;
    DatabaseReference   myRef;
    FirebaseUser        user;

    View            view;
    TableLayout     tableOrders;
    TextView        viewTemp;

    List<Ticket>    listTicket;
    List<Trip>      listUserTrips;

    public OrdersActivity() {
    }

    public static OrdersActivity newInstance() {
        return new OrdersActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_orders, container, false);
        tableOrders = view.findViewById(R.id.tableOrders);
        viewTemp = view.findViewById(R.id.viewTemp);

        user = mAuth.getInstance().getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference();

        listTicket = new ArrayList<>();
        listUserTrips = new ArrayList<>();

        myRef.child("users").child(user.getUid()).child("tickets").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataValues : dataSnapshot.getChildren()){
                    Ticket ticket = dataValues.getValue(Ticket.class);
                    listTicket.add(ticket);
                }
                fillOrdersFields();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }

    public void fillOrdersFields(){
        for (int i = 0; i < listTicket.size(); i++) {
            getTrip(i);
        }
        viewTemp.setText(listUserTrips.get(0).getPrice() + " " + listUserTrips.get(1).getPrice());
    }

    public void getTrip(int i) {
        myRef.child("stations").child(listTicket.get(i).station_id)
                .child(listTicket.get(i).date).child(listTicket.get(i).trip_id)
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Trip resultTrip = dataSnapshot.getValue(Trip.class);
                listUserTrips.add(resultTrip);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}


