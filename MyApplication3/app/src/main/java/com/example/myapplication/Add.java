package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Add extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Button menu_calender = (Button) findViewById(R.id.menu_calender);
        menu_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //Save
        Button saveNoteBtn = (Button) findViewById(R.id.saveNoteBtn);
        menu_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText otherNotesEditText = (Edit) findViewById(R.id.otherNotes);
            }
        });
    }
}