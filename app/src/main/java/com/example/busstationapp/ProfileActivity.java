package com.example.busstationapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends Fragment {

    FirebaseAuth mAuth;
    private DatabaseReference myRef;
    FirebaseUser user;

    View view;
    TextView LastName;
    TextView FirstName;
    TextView MiddleName;
    TextView Email;
    ImageView Photo;
    Button btnUnsign;
    TableLayout tableAdminPanel;

    public ProfileActivity() {
    }

    public static ProfileActivity newInstance() {
        return new ProfileActivity();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_profile, container, false);
        tableAdminPanel = view.findViewById(R.id.tableAdminPanel);
        LastName = view.findViewById(R.id.profileLastName);
        FirstName = view.findViewById(R.id.profileFirstName);
        MiddleName = view.findViewById(R.id.profileMiddleName);
        Email = view.findViewById(R.id.profileEmail);
        Photo = view.findViewById(R.id.profilePhoto);
        btnUnsign = view.findViewById(R.id.btnUnsign);

        btnUnsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
            }
        });

        user = mAuth.getInstance().getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference gsReference = storage.getReferenceFromUrl("gs://busstationapp-dd602.appspot.com/ryan.jpg");

        final long ONE_MEGABYTE = 1024 * 1024;
        gsReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                Photo.setImageBitmap(bitmap);
            }
        });

        myRef.child("users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User appuser = dataSnapshot.getValue(User.class);
                LastName.setText(appuser.last_name);
                FirstName.setText(appuser.first_name);
                MiddleName.setText(appuser.middle_name);
                Email.setText(appuser.email);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.d("1", "get data from DB error");
            }
        });

        if (user.getEmail().equals("test@mail.ru")) {
            // Строка кнопка "купить"
            TableRow rowBtnSendData = new TableRow(view.getContext());
            rowBtnSendData.setGravity(Gravity.CENTER_HORIZONTAL);
            final Button btnSendData = new Button(view.getContext());
            btnSendData.setText("Send data");
            btnSendData.setGravity(Gravity.CENTER);
            btnSendData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String  new_departure_date =    "6-8-2020";

                    String  new_arrival_time =      "06:00";
                    String  new_count_free =        "14";
                    String  new_count_occup =       "6";
                    String  new_departure_id =      "Москва";
                    String  new_departure_time =    "23:30";
                    String  new_destination_date =  "6-9-2020";
                    String  new_destination_id =    "Смоленск";
                    String  new_number_of_occup =   "1,2,3,4,5,6,7,8,9,10,11,12,13,14";
                    String  new_price =             "1090";
                    String  new_trip_id =           "msk54556";
                    String  new_typetrans_od =      "1";
                    Trip trip = new Trip(
                            myRef,
                            new_arrival_time,
                            new_count_free,
                            new_count_occup,
                            new_departure_id,
                            new_departure_time,
                            new_destination_date,
                            new_destination_id,
                            new_number_of_occup,
                            new_price,
                            new_trip_id,
                            new_typetrans_od
                    );
                    Map<String, Object> postValues = trip.toMap();
                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put(new_trip_id, postValues);
                    myRef.child("stations").child(new_departure_id)
                            .child(new_departure_date).updateChildren(childUpdates);
                }
            });
            rowBtnSendData.addView(btnSendData);
            tableAdminPanel.addView(rowBtnSendData);
        }

        return view;
    }

}
