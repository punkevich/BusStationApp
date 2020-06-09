package com.example.busstationapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
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
import java.util.HashSet;
import java.util.List;

public class SearchActivity extends Fragment {

    FirebaseAuth mAuth;
    DatabaseReference myRef;
    FirebaseUser user;

    View view;
    Button btnSearch;
    EditText editDeparture;
    EditText editArrival;
    CalendarView calendarSearch;
    TableLayout tableSearch;

    String currentDeparture;
    String currentDestination;
    List<Trip> foundTrips;
    SelectedDate selectedDate;

    public SearchActivity() {

    }

    public static SearchActivity newInstance() {
        return new SearchActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_search, container, false);
        tableSearch = view.findViewById(R.id.tableSearch);
        btnSearch = view.findViewById(R.id.btnSearch);
        editDeparture = view.findViewById(R.id.editDeparture);
        editArrival = view.findViewById(R.id.editArrival);
        calendarSearch = view.findViewById(R.id.calendarSearch);
        selectedDate = new SelectedDate();
        calendarSearch.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year,
            int month, int dayOfMonth) {
                selectedDate.setDay(dayOfMonth);
                selectedDate.setMonth(month + 1);
                selectedDate.setYear(year);

                Toast.makeText(view.getContext(), selectedDate.showDate(), Toast.LENGTH_LONG).show();
            }
        });
        user = mAuth.getInstance().getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference();

        foundTrips = new ArrayList<>();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //currentDeparture = editDeparture.getText().toString();
                //currentArrival = editArrival.getText().toString();

                currentDeparture = "station_id";
                currentDestination = "Тула";
                myRef.child("stations").child("station_id").child("date").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<Trip> listRes = new ArrayList<>();
                        for (DataSnapshot dataValues : dataSnapshot.getChildren()){
                            Trip trip = dataValues.getValue(Trip.class);
                            listRes.add(trip);
                        }
                        searchTrip(listRes);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        return view;
    }

    public void searchTrip(List<Trip> listRes) {
        for (int i = 0; i < listRes.size(); i++) {
            if (listRes.get(i).getDestination_id().equals(currentDestination))
                foundTrips.add(listRes.get(i));
        }

        for (int i = 0; i < foundTrips.size(); i++) {
            // Строка <пункт_отправления> <пункт прибытия>
            TableRow rowRoute = new TableRow(view.getContext());
            rowRoute.setGravity(Gravity.CENTER_HORIZONTAL);
            TextView textNameDepart = new TextView(view.getContext());
            TextView textNameArrival = new TextView(view.getContext());
            textNameDepart.setText(foundTrips.get(i).getDeparture_id());
            textNameDepart.setTextSize(18);
            textNameDepart.setGravity(Gravity.CENTER);
            textNameArrival.setText(foundTrips.get(i).getDestination_id());
            textNameArrival.setTextSize(18);
            textNameArrival.setGravity(Gravity.CENTER);
            rowRoute.addView(textNameDepart);
            rowRoute.addView(textNameArrival);
            tableSearch.addView(rowRoute);
            // Строка <время_отправления> <время_прибытия>
            TableRow rowTimeDepartArr = new TableRow(view.getContext());
            rowTimeDepartArr.setGravity(Gravity.CENTER_HORIZONTAL);
            TextView textTimeDepart = new TextView(view.getContext());
            TextView textTimeArrival = new TextView(view.getContext());
            textTimeDepart.setText("date" + " " + foundTrips.get(i).getDeparture_time());
            textTimeDepart.setTextSize(14);
            textTimeDepart.setGravity(Gravity.CENTER);
            textTimeArrival.setText("date" + " " + foundTrips.get(i).getArrival_time());
            textTimeArrival.setTextSize(14);
            textTimeArrival.setGravity(Gravity.CENTER);
            rowTimeDepartArr.addView(textTimeDepart);
            rowTimeDepartArr.addView(textTimeArrival);
            tableSearch.addView(rowTimeDepartArr);
            // Строка "мест свободно"
            TableRow rowCountPlaces = new TableRow(view.getContext());
            rowCountPlaces.setGravity(Gravity.CENTER_HORIZONTAL);
            TextView textCountPlaces = new TextView(view.getContext());
            textCountPlaces.setText(foundTrips.get(i).getCount_free());
            textCountPlaces.setTextSize(14);
            textCountPlaces.setGravity(Gravity.CENTER);
            rowCountPlaces.addView(textCountPlaces);
            tableSearch.addView(rowCountPlaces);
            // Строка "цена билета"
            TableRow rowPrice = new TableRow(view.getContext());
            rowPrice.setGravity(Gravity.CENTER_HORIZONTAL);
            TextView textPrice = new TextView(view.getContext());
            textPrice.setText(foundTrips.get(i).getPrice());
            textPrice.setTextSize(14);
            textPrice.setGravity(Gravity.CENTER);
            rowPrice.addView(textPrice);
            tableSearch.addView(rowPrice);
            // Строка кнопка "купить"
            TableRow rowBtnBuy = new TableRow(view.getContext());
            rowBtnBuy.setGravity(Gravity.CENTER_HORIZONTAL);
            final Button btnBuy = new Button(view.getContext());
            btnBuy.setText("Купить");
            btnBuy.setId(i);
            btnBuy.setGravity(Gravity.CENTER);
            btnBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(view.getContext(), BuyingActivity.class);
                    intent.putExtra("trip_id", foundTrips.get(btnBuy.getId()).getTrip_id());
                    intent.putExtra("station_id", "station_id");
                    intent.putExtra("date", "date");
                    intent.putExtra("departure_id", foundTrips.get(btnBuy.getId()).getDeparture_id());
                    intent.putExtra("destination_id", foundTrips.get(btnBuy.getId()).getDestination_id());
                    intent.putExtra("departure_time", foundTrips.get(btnBuy.getId()).getDeparture_time());
                    intent.putExtra("arrival_time", foundTrips.get(btnBuy.getId()).getArrival_time());
                    intent.putExtra("destination_date", foundTrips.get(btnBuy.getId()).getDestination_date());
                    startActivity(intent);
                }
            });
            rowBtnBuy.addView(btnBuy);
            tableSearch.addView(rowBtnBuy);
        }
    }


}
