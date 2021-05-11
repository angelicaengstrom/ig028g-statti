package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class CalenderPopUp extends AppCompatActivity implements FirebaseAuth.AuthStateListener{
    private Button goBack;
    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private TextView dateTextView;
    CalenderRecyclerAdapter calenderRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_pop_up);

        mAuth = FirebaseAuth.getInstance();
        goBack = findViewById(R.id.backToCalenderBtn);
        recyclerView = findViewById(R.id.recyclerView);
        dateTextView = findViewById(R.id.dateTextView);
        Intent intent = new Intent(this, Calender.class);

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String date = Calender.date;
        dateTextView.setText(date);
        showNote(userId, date);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
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
        if(calenderRecyclerAdapter != null){
            calenderRecyclerAdapter.stopListening();
        }
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
                        Log.d("CALPOPUP", "onSuccess: " + getTokenResult.getToken());
                    }
                });

    }
    private void showNote(String user, String date){
        Query query = FirebaseFirestore.getInstance()
                .collection("notes")
                .whereEqualTo("userId", user)
                .whereEqualTo("created", date);

        FirestoreRecyclerOptions<Note> options = new FirestoreRecyclerOptions.Builder<Note>()
                .setQuery(query, Note.class)
                .build();

        calenderRecyclerAdapter = new CalenderRecyclerAdapter(options);

        calenderRecyclerAdapter.setOnDeleteClickListener(new CalenderRecyclerAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(DocumentSnapshot Snapshot) {
                Snapshot.getReference().delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("CALPOPUP", "onSuccess: Note deleted");
                            }
                        });
            }
        });
        recyclerView.setAdapter(calenderRecyclerAdapter);
        calenderRecyclerAdapter.startListening();
    }
}