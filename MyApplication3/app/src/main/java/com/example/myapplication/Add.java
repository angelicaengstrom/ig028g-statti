package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Add extends AppCompatActivity {
    private EditText editNote1;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add);

        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        mAuth = FirebaseAuth.getInstance();
        editNote1 = (EditText) findViewById(R.id.Note1);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.note);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.note:
                        return true;
                    case R.id.calender:
                        startActivity(new Intent(getApplicationContext(), Calender.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.graph:
                        startActivity(new Intent(getApplicationContext(), Graph.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext(), Settings.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }
    /*

    @Override
    public void onClick(View view) {
        String note1 = editNote1.getText().toString().trim();
        String note2 = editNote2.getText().toString().trim();
        String note3 = editNote3.getText().toString().trim();

        mAuth.
    }*/
}