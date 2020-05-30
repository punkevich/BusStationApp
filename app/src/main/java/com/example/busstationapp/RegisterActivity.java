package com.example.busstationapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    DatabaseReference myRef;
    static final String TAG = "EmailPassword";

    Button btnReg;
    Button btnBack;
    EditText editLastName;
    EditText editFirstName;
    EditText editMiddleName;
    EditText editEmail;
    EditText editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
        btnReg = findViewById(R.id.btnReg);
        btnBack = findViewById(R.id.btnBack);
        editLastName = findViewById(R.id.editLastName);
        editFirstName = findViewById(R.id.editFirstName);
        editMiddleName = findViewById(R.id.editMiddleName);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);

        mAuth = FirebaseAuth.getInstance();

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

        private void createAccount(){
            Log.d(TAG, "createAccount:" + editEmail.getText().toString());
            mAuth.createUserWithEmailAndPassword(editEmail.getText().toString(), editPassword.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Вы зарегистрированы",
                                        Toast.LENGTH_SHORT).show();
                                mAuth.signInWithEmailAndPassword(editEmail.getText().toString(), editPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            FirebaseUser user = mAuth.getInstance().getCurrentUser();
                                            // добавление информации о пользователе в БД
                                            myRef = FirebaseDatabase.getInstance().getReference();
                                            myRef.child("users").child(user.getUid()).child("email").setValue(user.getEmail());
                                            myRef.child("users").child(user.getUid()).child("last_name").setValue(editLastName.getText().toString());
                                            myRef.child("users").child(user.getUid()).child("first_name").setValue(editFirstName.getText().toString());
                                            myRef.child("users").child(user.getUid()).child("middle_name").setValue(editMiddleName.getText().toString());
                                        }
                                    }

                                });
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(RegisterActivity.this, "Ошибка",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

