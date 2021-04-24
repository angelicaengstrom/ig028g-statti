package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class profile extends AppCompatActivity {
    public static final String TAG = "TAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent data = getIntent();
        String name = data.getStringExtra("name");
        String email = data.getStringExtra("email");
        String age = data.getStringExtra("age");

        Log.d(TAG, "onCreate: " + name + " " + email + " " + age);

    }
}