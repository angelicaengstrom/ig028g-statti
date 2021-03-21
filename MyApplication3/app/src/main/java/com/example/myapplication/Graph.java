package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class Graph extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        Button menu_calenderBtn = findViewById(R.id.menu_calender);
        menu_calenderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), Calender.class);
                startActivity(startIntent);
            }
        });

        Button menu_addBtn = findViewById(R.id.menu_add);
        menu_addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), Add.class);
                startActivity(startIntent);
            }
        });

        Button menu_settingsBtn = findViewById(R.id.menu_settings);
        menu_settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), Settings.class);
                startActivity(startIntent);
            }
        });
    }
}