package com.example.busstationapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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
    Button          tempButton;

    //List<Ticket>    listTicket;

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
        tempButton = new Button(view.getContext());

        user = mAuth.getInstance().getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference();

        //listTicket = new ArrayList<>();

        FirebaseUser user = mAuth.getInstance().getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference();

        myRef.child("users").child(user.getUid()).child("tickets").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Ticket> listTicket = new ArrayList<>();
                for (DataSnapshot dataValues : dataSnapshot.getChildren()){
                    Ticket ticket = dataValues.getValue(Ticket.class);
                    listTicket.add(ticket);
                }
                fillTableOfOrders(listTicket);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }

    public void fillTableOfOrders(final List<Ticket> listTicket) {
        for (int i = 0; i < listTicket.size(); i++) {
            // Строка <пункт_отправления> <пункт прибытия>
            TableRow rowOrderRoute = new TableRow(view.getContext());
            rowOrderRoute.setGravity(Gravity.LEFT);
            TextView textOrderDepart = new TextView(view.getContext());
            TextView textOrderArrival = new TextView(view.getContext());
            textOrderDepart.setWidth(tableOrders.getWidth() / 2);
            textOrderDepart.setText(listTicket.get(i).departure_id + "  ");
            textOrderDepart.setTextSize(18);
            textOrderDepart.setGravity(Gravity.CENTER);
            textOrderArrival.setWidth(tableOrders.getWidth() / 2);
            textOrderArrival.setText(listTicket.get(i).destination_id);
            textOrderArrival.setTextSize(18);
            textOrderArrival.setGravity(Gravity.CENTER);
            rowOrderRoute.addView(textOrderDepart);
            rowOrderRoute.addView(textOrderArrival);
            tableOrders.addView(rowOrderRoute);

            // Строка <время_отправления> <время_прибытия>
            TableRow rowOrderTime = new TableRow(view.getContext());
            rowOrderTime.setGravity(Gravity.LEFT);
            TextView timeOrderDepart = new TextView(view.getContext());
            TextView timeOrderArrival = new TextView(view.getContext());
            timeOrderDepart.setWidth(tableOrders.getWidth() / 2);
            timeOrderDepart.setText(listTicket.get(i).departure_time + "  ");
            timeOrderDepart.setTextSize(14);
            timeOrderDepart.setGravity(Gravity.CENTER);
            timeOrderArrival.setWidth(tableOrders.getWidth() / 2);
            timeOrderArrival.setText(listTicket.get(i).arrival_time);
            timeOrderArrival.setTextSize(14);
            timeOrderArrival.setGravity(Gravity.CENTER);
            rowOrderTime.addView(timeOrderDepart);
            rowOrderTime.addView(timeOrderArrival);
            tableOrders.addView(rowOrderTime);

            // Строка <посадочное место>
            TableRow rowOrderPlace = new TableRow(view.getContext());
            rowOrderPlace.setGravity(Gravity.CENTER_HORIZONTAL);
            TextView textOrderPlace = new TextView(view.getContext());
            textOrderPlace.setText("Посадочное место №" + listTicket.get(i).place_number);
            textOrderPlace.setTextSize(14);
            textOrderPlace.setGravity(Gravity.CENTER);
            rowOrderPlace.addView(textOrderPlace);
            tableOrders.addView(rowOrderPlace);

            // Строка кнопка "отменить"
            TableRow rowBtnCancel = new TableRow(view.getContext());
            rowBtnCancel.setGravity(Gravity.CENTER_HORIZONTAL);
            final Button btnCancel = new Button(view.getContext());
            btnCancel.setText("Отменить");
            btnCancel.setId(i);
            btnCancel.setGravity(Gravity.CENTER);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Intent intent = new Intent(view.getContext(), BuyingActivity.class);
                    intent.putExtra("trip_id", listRes.get(btnBuy.getId()).getTrip_id());
                    intent.putExtra("station_id", "station_id");
                    intent.putExtra("date", "date");
                    intent.putExtra("departure_id", listRes.get(btnBuy.getId()).getDeparture_id());
                    intent.putExtra("destination_id", listRes.get(btnBuy.getId()).getDestination_id());
                    intent.putExtra("departure_time", listRes.get(btnBuy.getId()).getDeparture_time());
                    intent.putExtra("arrival_time", listRes.get(btnBuy.getId()).getArrival_time());
                    startActivity(intent);*/
                    Toast.makeText(view.getContext(), "Поездка отменена",
                            Toast.LENGTH_SHORT).show();
                }
            });
            rowBtnCancel.addView(btnCancel);
            tableOrders.addView(rowBtnCancel);
        }
    }
}


