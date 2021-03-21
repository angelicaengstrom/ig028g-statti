package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;

public class Add extends AppCompatActivity {
    EditText otherNotesEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Button menu_calenderBtn = findViewById(R.id.menu_calender);
        menu_calenderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), Calender.class);
                startActivity(startIntent);
            }
        });

        Button menu_graphBtn = findViewById(R.id.menu_graph);
        menu_graphBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), Graph.class);
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


        Button saveNoteBtn = findViewById(R.id.saveNoteBtn);
        saveNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otherNotesEditText = findViewById(R.id.otherNotes);
            }
        });
    }
}