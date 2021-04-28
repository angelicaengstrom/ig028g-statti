package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class profile extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText profileFullName, profileEmail, profileAge;
    ImageView profileImageView;
    Button saveBtn;
    FirebaseAuth auth;
    FirebaseFirestore fstore;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent data = getIntent();
        String fullname = data.getStringExtra("fullname");
        String email = data.getStringExtra("email");
        String age = data.getStringExtra("age");

        auth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        profileFullName = findViewById(R.id.profileFullName);
        profileEmail = findViewById(R.id.profileEmailAdress);
        profileAge = findViewById(R.id.profileAge);
        profileImageView = findViewById(R.id.profileImageView);
        saveBtn = findViewById(R.id.saveInfo);


        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Toast.makeText(profile.this,"Whatever",Toast.LENGTH_SHORT).show();

            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (profileFullName.getText().toString().isEmpty() || profileEmail.getText().toString().isEmpty() || profileAge.getText().toString().isEmpty()){
                    Toast.makeText(profile.this, "En eller flera är tomma", Toast.LENGTH_SHORT).show();
                return;
            }
                String email = profileFullName.getText().toString().isEmpty();
        }
        });

        profileFullName.setText(fullname);
        profileEmail.setText(email);
        profileAge.setText(age);

        Log.d(TAG, "onCreate: " + fullname + " " + email + " " + age);

    }
}