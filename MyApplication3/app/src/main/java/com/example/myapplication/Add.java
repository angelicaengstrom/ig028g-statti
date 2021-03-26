package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Add extends AppCompatActivity implements FirebaseAuth.AuthStateListener, AdapterView.OnItemSelectedListener{
    private FirebaseAuth mAuth;
    private static final String TAG = "Add";
    private EditText otherNoteEditText, feelingEditText, trainsessionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        otherNoteEditText = findViewById(R.id.otherNote);
        feelingEditText = findViewById(R.id.feelingTextView);
        trainsessionEditText = findViewById(R.id.trainsessionTextView);

        Spinner spinner = findViewById(R.id.trainingType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(this, R.array.trainingType, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        FloatingActionButton fab = findViewById(R.id.saveNoteBtn);
        fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "onClick: " + otherNoteEditText.getText()
                        + feelingEditText.getText() + trainsessionEditText.getText());
                        /*
                        Log.d(TAG, "onClick: " + feelingEditText.getText());
                        Log.d(TAG, "onClick: " + trainsessionEditText.getText());*/
                    }

        });


        mAuth = FirebaseAuth.getInstance();

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

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseAuth.getInstance().removeAuthStateListener(this);
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        if (firebaseAuth.getCurrentUser() == null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            this.finish();
            return;
        }
        firebaseAuth.getCurrentUser().getIdToken(true)
                .addOnSuccessListener(new OnSuccessListener<GetTokenResult>() {
                    @Override
                    public void onSuccess(GetTokenResult getTokenResult) {
                        Log.d(TAG, "onSuccess: " + getTokenResult.getToken());
                    }
                });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    /*
    
    public void createDocument(View view){
        FirebaseFirestore.getInstance();

        /*
        Map<String, Object> map = new HashMap<>();
        map.put("text", "sov mycket");
        map.put("feeling", 5);
        map.put("trainsession", 10);
        map.put("created", new Timestamp(new Date()));

        Note note = new Note("sov mycket", 5, 10);

        FirebaseFirestore.getInstance().collection("notes")
                //.add(map)
                .add(note)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "onSuccess: task was succesfull");
                        Log.d(TAG, "onSuccess: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: task was NOT succesfull");
                    }
                });

        /* SKAPAR EN NY COLLECTION
        Map<String, Object> map = new HashMap<>();
        map.put("name", "iPhone11");
        map.put("price", 699);
        map.put("isAvailable", true);

        FirebaseFirestore.getInstance()
                .collection("products")
                .add(map)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "onSuccess: Product is added succesfully");
                        Log.d(TAG, "onSuccess: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }
    */
}