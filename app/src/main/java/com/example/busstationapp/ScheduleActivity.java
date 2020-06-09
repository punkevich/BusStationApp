package com.example.busstationapp;

import android.content.Intent;
import android.os.Bundle;
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
import java.util.List;

public class ScheduleActivity extends Fragment {

    FirebaseAuth mAuth;
    DatabaseReference myRef;

    View view;
    EditText editSchedule;
    CalendarView calendarSchedule;
    Button showSchedule;
    TableLayout table;
    Trip buyingTrip;
    SelectedDate selectedDate;

    public ScheduleActivity() {
    }

    public static ScheduleActivity newInstance() {
        return new ScheduleActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_schedule, container, false);

        editSchedule = view.findViewById(R.id.editSchedule);
        calendarSchedule = view.findViewById(R.id.calendarSchedule);
        showSchedule = view.findViewById(R.id.btnShowSchedule);
        table = view.findViewById(R.id.tableSchedule);

        FirebaseUser user = mAuth.getInstance().getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference();
        selectedDate = new SelectedDate(6,9,2020); // дефолтное значение для безопасности

        calendarSchedule.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year,
                                            int month, int dayOfMonth) {
                selectedDate.setDay(dayOfMonth);
                selectedDate.setMonth(month + 1);
                selectedDate.setYear(year);

                Toast.makeText(view.getContext(), selectedDate.showDate(), Toast.LENGTH_LONG).show();
            }
        });

        showSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editSchedule.getText().equals("")) {
                    myRef.child("stations").child(editSchedule.getText().toString())
                            .child(selectedDate.showDate())
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            List<Trip> listRes = new ArrayList<>();
                            for (DataSnapshot dataValues : dataSnapshot.getChildren()) {
                                Trip trip = dataValues.getValue(Trip.class);
                                listRes.add(trip);
                            }
                            fillTable(listRes);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        return view;
    }

    public void fillTable(final List<Trip> listRes) {

        for (int i = 0; i < listRes.size(); i++) {
            // Строка <пункт_отправления> <пункт прибытия>
            buyingTrip = listRes.get(i);
            TableRow rowRoute = new TableRow(view.getContext());
            rowRoute.setGravity(Gravity.CENTER_HORIZONTAL);
            TextView textNameDepart = new TextView(view.getContext());
            TextView textNameArrival = new TextView(view.getContext());
            textNameDepart.setText(listRes.get(i).getDeparture_id());
            textNameDepart.setTextSize(18);
            textNameDepart.setGravity(Gravity.CENTER);
            textNameArrival.setText(listRes.get(i).getDestination_id());
            textNameArrival.setTextSize(18);
            textNameArrival.setGravity(Gravity.CENTER);
            rowRoute.addView(textNameDepart);
            rowRoute.addView(textNameArrival);
            table.addView(rowRoute);
            // Строка <время_отправления> <время_прибытия>
            TableRow rowTimeDepartArr = new TableRow(view.getContext());
            rowTimeDepartArr.setGravity(Gravity.CENTER_HORIZONTAL);
            TextView textTimeDepart = new TextView(view.getContext());
            TextView textTimeArrival = new TextView(view.getContext());
            textTimeDepart.setText(selectedDate.showDate() + " " + listRes.get(i).getDeparture_time());
            textTimeDepart.setTextSize(14);
            textTimeDepart.setGravity(Gravity.CENTER);
            textTimeArrival.setText(listRes.get(i).getDestination_date() + " " + listRes.get(i).getArrival_time());
            textTimeArrival.setTextSize(14);
            textTimeArrival.setGravity(Gravity.CENTER);
            rowTimeDepartArr.addView(textTimeDepart);
            rowTimeDepartArr.addView(textTimeArrival);
            table.addView(rowTimeDepartArr);
            // Строка "мест свободно"
            TableRow rowCountPlaces = new TableRow(view.getContext());
            rowCountPlaces.setGravity(Gravity.CENTER_HORIZONTAL);
            TextView textCountPlaces = new TextView(view.getContext());
            textCountPlaces.setText("Свбодных мест: " + listRes.get(i).getCount_free());
            textCountPlaces.setTextSize(14);
            textCountPlaces.setGravity(Gravity.CENTER);
            rowCountPlaces.addView(textCountPlaces);
            table.addView(rowCountPlaces);
            // Строка "цена билета"
            TableRow rowPrice = new TableRow(view.getContext());
            rowPrice.setGravity(Gravity.CENTER_HORIZONTAL);
            TextView textPrice = new TextView(view.getContext());
            textPrice.setText("Цена: " + listRes.get(i).getPrice() + " руб.");
            textPrice.setTextSize(14);
            textPrice.setGravity(Gravity.CENTER);
            rowPrice.addView(textPrice);
            table.addView(rowPrice);
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
                    intent.putExtra("trip_id", listRes.get(btnBuy.getId()).getTrip_id());
                    intent.putExtra("station_id", editSchedule.getText());
                    intent.putExtra("date", selectedDate.showDate());
                    intent.putExtra("departure_id", listRes.get(btnBuy.getId()).getDeparture_id());
                    intent.putExtra("destination_id", listRes.get(btnBuy.getId()).getDestination_id());
                    intent.putExtra("departure_time", listRes.get(btnBuy.getId()).getDeparture_time());
                    intent.putExtra("arrival_time", listRes.get(btnBuy.getId()).getArrival_time());
                    intent.putExtra("destination_date", listRes.get(btnBuy.getId()).getDestination_date());
                    startActivity(intent);
                }
            });
            rowBtnBuy.addView(btnBuy);
            table.addView(rowBtnBuy);
        }
    }

}
