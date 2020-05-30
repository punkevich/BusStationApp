package com.example.busstationapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuyingActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference myRef;
    FirebaseUser user;

    Trip currentTrip;

    TextView textTicketDepart;
    TextView textTicketArr;
    TextView textTicketTimeDepart;
    TextView textTicketTimeArr;
    TextView textTicketPrice;
    TextView textSelectedPlace;
    GridView gridView;
    Button btnTicketBuy;
    EditText editNumCard;
    EditText editDateCard;
    EditText editCVCCard;

    List<Trip> listRes;
    String currentTripId;
    String currentDate;
    String currentStationId;
    int selectedPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buying);
        final Bundle arguments = getIntent().getExtras();

        textTicketDepart = findViewById(R.id.textTicketDepart);
        textTicketArr = findViewById(R.id.textTicketArr);
        textTicketTimeDepart = findViewById(R.id.textTicketTimeDepart);
        textTicketTimeArr = findViewById(R.id.textTicketTimeArr);
        textTicketPrice = findViewById(R.id.textTicketPrice);
        gridView = (GridView) findViewById(R.id.gridView);
        btnTicketBuy = findViewById(R.id.btnTicketBuy);
        editNumCard = findViewById(R.id.editNumCard);
        editDateCard = findViewById(R.id.editDateCard);
        editCVCCard = findViewById(R.id.editCVCCard);
        textSelectedPlace = findViewById(R.id.textSelectedPlace);
        selectedPlace = -1;

        currentTripId = arguments.get("trip_id").toString();
        currentDate = arguments.get("date").toString();
        currentStationId = arguments.get("station_id").toString();

        user = mAuth.getInstance().getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference();
        listRes = new ArrayList<>();

        myRef.child("stations").child("station_id").child("date").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataValues : dataSnapshot.getChildren()){
                    Trip trip = dataValues.getValue(Trip.class);
                    listRes.add(trip);
                }
                fillFields();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void fillFields()
    {
        getCurrentTrip(listRes);
        List<Place> image_details = getListData();
        gridView.setAdapter(new CustomGridAdapter(this, image_details));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                selectedPlace = position + 1;
                textSelectedPlace.setText("Выбранное место: " + selectedPlace);
                Toast.makeText(BuyingActivity.this, "Выбрано место "
                        + Integer.toString(position + 1) , Toast.LENGTH_LONG).show();
            }
        });
        textTicketDepart.setText(currentTrip.getDeparture_id());
        textTicketArr.setText(currentTrip.getDestination_id());
        textTicketTimeDepart.setText(currentTrip.getDeparture_time());
        textTicketTimeArr.setText(currentTrip.getArrival_time());
        textTicketPrice.setText("Стоимость билета: " + currentTrip.getPrice() + "руб.");

        btnTicketBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ticket ticket = new Ticket(currentStationId, currentDate, currentTripId, Integer.toString(selectedPlace));
                Map<String, Object> postValues = ticket.toMap();
                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put(user.getUid() + currentTripId, postValues);
                myRef.child("users").child(user.getUid()).child("tickets").updateChildren(childUpdates);
            }
        });
    }

    public void getCurrentTrip(List<Trip> listRes)
    {
        for (int i = 0; i < listRes.size(); i++) {
            if (listRes.get(i).getTrip_id().equals(currentTripId)) {
                currentTrip = new Trip(listRes.get(i));
                return;
            }
        }
    }

    private List<Place> getListData() {
        int capacity = 0;
        switch (currentTrip.getTypetrans_od()){
            case ("1"):
                capacity = Place.type_1;
                break;
            case("2"):
                capacity = Place.type_2;
                break;
            default:
                capacity = 40;
                break;
        }
        String numberOccup[] = currentTrip.getNumber_of_occup().split(",");
        List<String> listOccup = Arrays.asList(numberOccup);
        List<Place> listPlaces = new ArrayList<Place>();
        for (int i = 1; i <= capacity; i++)
        {
            Place place = new Place(i, true);
            if (listOccup.contains(Integer.toString(i)))
                place.setStatus(false);
            listPlaces.add(place);
        }
        return listPlaces;
    }
}
