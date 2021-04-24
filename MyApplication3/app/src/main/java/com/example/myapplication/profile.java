package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class profile extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText profileFullName, profileEmail, profileAge;
    ImageView profileImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent data = getIntent();
        String fullname = data.getStringExtra("fullname");
        String email = data.getStringExtra("email");
        String age = data.getStringExtra("age");

        profileFullName = findViewById(R.id.profileFullName);
        profileEmail = findViewById(R.id.profileEmailAdress);
        profileAge = findViewById(R.id.profileAge);
        profileImageView = findViewById(R.id.profileImageView);

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Toast.makeText(profile.this,"Whatever",Toast.LENGTH_SHORT).show();
            
            }
        });

        profileFullName.setText(fullname);
        profileEmail.setText(email);
        profileAge.setText(age);

        Log.d(TAG, "onCreate: " + fullname + " " + email + " " + age);

    }
}