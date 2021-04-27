package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.squareup.okhttp.internal.DiskLruCache;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Calender extends AppCompatActivity implements FirebaseAuth.AuthStateListener{
    private static final String TAG = "Calender";
    CalenderRecyclerAdapter calenderRecyclerAdapter;
    RecyclerView recyclerView;
    private int currentYear = 0;
    private int currentMonth = 0;
    private int currentDay = 0;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        mAuth = FirebaseAuth.getInstance();

        //Anteckningar
        recyclerView = findViewById(R.id.recyclerView);

        //Calender
        String date = getTodaysDate();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        initRecyclerView(userId, date);

        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + " - " + year;

                initRecyclerView(userId, date);
            }
        });

        //Navigeringsmeny
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.calender);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.note:
                        startActivity(new Intent(getApplicationContext(), Add.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.calender:
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
                        Log.d(TAG, "onSuccess: " + getTokenResult.getToken());
                    }
                });

    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        month = month + 1;
        String date = dayOfMonth + "/" + month + " - " + year;
        return date;
    }

    private void initRecyclerView(String user, String date){
        Query query = FirebaseFirestore.getInstance()
                .collection("notes")
                .whereEqualTo("userId", user)
                .whereEqualTo("created", date);

        FirestoreRecyclerOptions<Note> options = new FirestoreRecyclerOptions.Builder<Note>()
                .setQuery(query, Note.class)
                .build();

        calenderRecyclerAdapter = new CalenderRecyclerAdapter(options);

                /*
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                titles.clear();
                for(DocumentSnapshot snapshot : value){
                    titles.add(new Row(snapshot.getString("title"), data));
                    Log.d(TAG, "onEvent: Title: " + snapshot.contains("exercise"));
                }
                titlesRecyclerAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(calenderRecyclerAdapter);

            }
        });
*/
        calenderRecyclerAdapter.setOnDeleteClickListener(new CalenderRecyclerAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(DocumentSnapshot Snapshot) {
                Snapshot.getReference().delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "onSuccess: Note deleted");
                            }
                        });
            }
        });
        recyclerView.setAdapter(calenderRecyclerAdapter);
        calenderRecyclerAdapter.startListening();
    }
    /*
    public void readDocument(View view){
        /*
        FirebaseFirestore.getInstance() //ta ut alla dokument
                .collection("notes")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.d(TAG, "onSuccess: We're getting the data");
                        List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot snapshot: snapshotList){
                            Log.d(TAG, "onSuccess: " + snapshot.getData().toString());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: ", e);
                    }
                });*/
        /*
        //TA UT EN SPECIFIK DATA
        FirebaseFirestore.getInstance()
                .collection("notes")
                .whereEqualTo("created", new Timestamp(new Date()))
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.d(TAG, "onSuccess: We're getting the data");
                        List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot snapshot: snapshotList){
                            Log.d(TAG, "onSuccess: " + snapshot.getData().toString());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: ", e);
                    }
                });

        FirebaseFirestore.getInstance()
                .collection("notes")
                .document("4BVVZBlzaCfVY2zF9fd8")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        /*
                        Log.d(TAG, "onSuccess: " + documentSnapshot.getId());
                        Log.d(TAG, "onSuccess: " + documentSnapshot.getData());
                        Log.d(TAG, "onSuccess: " + documentSnapshot.getTimestamp("created"));

                        Note note = documentSnapshot.toObject(Note.class);
                        Log.d(TAG, "onSuccess: " + note.toString());
                        Log.d(TAG, "onSuccess: " + note.getText()); //Ger endast namn osv

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: ", e);
                    }
                });
    }
    public void getAllDocumentsWithRealtimeUpdates(View view){

        /* //Ger en lista på alla dokument
        FirebaseFirestore.getInstance()
                .collection("notes")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            Log.e(TAG, "onEvent: ", error);
                            return;
                        }
                        if(value != null){
                            Log.d(TAG, "onEvent: ---------------------");
                            //List<DocumentSnapshot> snapshotList = value.getDocuments();
                            //for(DocumentSnapshot snapshot : snapshotList){
                              //  Log.d(TAG, "onEvent: " + snapshot.getData());
                            //}
                            List<DocumentChange> documentChangeList = value.getDocumentChanges();
                            for(DocumentChange documentChange : documentChangeList){
                                Log.d(TAG, "onEvent: " + documentChange.getDocument().getData());
                            }
                        } else {
                            Log.e(TAG, "onEvent: query snapshot was null");
                        }
                    }
                });

        //Är bara attached till 4BVVZBlzaCfVY2zF9fd8
        FirebaseFirestore.getInstance()
                .collection("notes")
                .document("4BVVZBlzaCfVY2zF9fd8")
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            Log.e(TAG, "onEvent: ", error);
                            return;
                        }
                        if(value != null){
                            Log.d(TAG, "onEvent: ----------------------");
                            Log.d(TAG, "onEvent: " + value.getData());
                        } else{
                            Log.e(TAG, "onEvent: query snapshot was null");
                        }
                    }
                });
    }

    public void updateDocument(View view){
        DocumentReference docRef = FirebaseFirestore.getInstance()
                .collection("notes")
                .document("4BVVZBlzaCfVY2zF9fd8");

        Map<String, Object> map = new HashMap<>();
        map.put("feeling", 3);
        map.put("condition", false); //lägger till brand som inte finns
        map.put("condition", FieldValue.delete()); //tar bort brand
        map.put("feeling", FieldValue.increment(2)); //ökar värdet med 2


        /*docRef.update(map) //till ett specifikt dokument
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: updated the doc");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: ", e);
                    }
                });

        docRef.set(map, SetOptions.merge()) //Kan skapa dokument som inte finns också
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: set the doc");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: ", e);
                    }
                });

    }
    public void deleteDocument(View view){
        FirebaseFirestore.getInstance()
                .collection("notes")
                .document("123")
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: We have deleted the document");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: ", e);
                    }
                });
    }*/
}