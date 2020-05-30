package com.example.busstationapp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class User {

    private DatabaseReference mDatabase;

    public String last_name;    // Фамилия
    public String first_name;   // Имя
    public String middle_name;  // Отчество
    public String email;       // Email

    public User() {

    }

    public User(String lastName, String firstName, String middleName, String email) {
        this.mDatabase = FirebaseDatabase.getInstance().getReference();
        this.last_name = lastName;
        this.first_name = firstName;
        this.middle_name = middleName;
        this.email = email;
    }

}
