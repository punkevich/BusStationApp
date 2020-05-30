package com.example.busstationapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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

public class ProfileActivity extends Fragment {

    FirebaseAuth mAuth;
    private DatabaseReference myRef;

    TextView LastName;
    TextView FirstName;
    TextView MiddleName;
    TextView Email;
    ImageView Photo;
    Button btnUnsign;

    public ProfileActivity() {
    }

    public static ProfileActivity newInstance() {
        return new ProfileActivity();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_profile, container, false);
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

        FirebaseUser user = mAuth.getInstance().getCurrentUser();
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
        return view;
    }

}
