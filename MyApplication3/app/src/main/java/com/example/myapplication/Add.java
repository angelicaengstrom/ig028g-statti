package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.protobuf.StringValue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class Add extends AppCompatActivity implements FirebaseAuth.AuthStateListener, AdapterView.OnItemSelectedListener {
    private FirebaseAuth mAuth;
    private static final String TAG = "Add";
    private EditText otherNoteEditText;
    private Button dateBtn;
    private DatePickerDialog datePickerDialog;
    private SeekBar feelingSeekbar, trainsessionSeekbar;
    private RowAdapter rowRecyclerAdapter;
    private RecyclerView.Adapter dataRecyclerAdapter;
    private RecyclerView rowRecyclerView, dataRecyclerView;
    private RecyclerView.LayoutManager rowLayoutManager, dataLayoutManager;
    List<Row> titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //Date
        initDatePicker();
        dateBtn = findViewById(R.id.dateBtn);
        dateBtn.setText(getTodaysDate());


        //Obligatoriska frågor
        otherNoteEditText = findViewById(R.id.otherNote);
        feelingSeekbar = (SeekBar) findViewById(R.id.feelingSeekbar);
        trainsessionSeekbar = (SeekBar) findViewById(R.id.trainingsessionSeekbar);


        //Traintype
        Spinner spinner = findViewById(R.id.trainingType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(this, R.array.trainingType, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        //Add exercises
        Button addRow = findViewById(R.id.newTrainsessionBtn);

        titles = new ArrayList<>();

        //Row
        rowRecyclerView = findViewById(R.id.rowRecyclerView);
        //rowRecyclerView.setHasFixedSize(true);
        rowLayoutManager = new LinearLayoutManager(this);
        rowRecyclerAdapter = new RowAdapter(titles);
        rowRecyclerView.setLayoutManager(rowLayoutManager);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rowRecyclerView);

        //Data
        dataRecyclerView = findViewById(R.id.dataRecyclerView);
        dataLayoutManager = new LinearLayoutManager(this);

        //add Data
        rowRecyclerAdapter.setOnItemClickListener(new RowAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d("demo", "onClick: position = " + position);
                titles.get(position).addTitleItem(new Data());
                rowRecyclerView.setAdapter(rowRecyclerAdapter);
            }
        });

        //add Row
        addRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trainType = spinner.getSelectedItem().toString();
                AlertDialog.Builder newTitle = new AlertDialog.Builder(Add.this);
                newTitle.setTitle("Lägg till övning: ");

                final EditText titleInput = new EditText(Add.this);
                titleInput.setInputType(InputType.TYPE_CLASS_TEXT);
                switch(trainType){
                    case "Kondition":
                        titleInput.setHint("ex. Löpning");
                        break;
                    case "Styrka":
                        titleInput.setHint("ex. Squats");
                        break;
                    case "Teknik":
                        titleInput.setHint("ex. Fotboll");
                        break;
                }

                newTitle.setView(titleInput).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        List<Data> data = new ArrayList<>();
                        data.add(new Data());
                        String title = titleInput.getText().toString();
                        titles.add(new Row(title, data));
                        rowRecyclerView.setAdapter(rowRecyclerAdapter);
                        dataRecyclerAdapter = new DataAdapter(data);
                    }
                }).setNegativeButton("AVBRYT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
            }
        });


        //Save Note
        Button fab = findViewById(R.id.saveNoteBtn);
        fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "onClick: " + otherNoteEditText.getText()
                        + feelingSeekbar.getProgress() + trainsessionSeekbar.getProgress() + spinner.getSelectedItem().toString()
                        + dateBtn.getText());

                        addNote(otherNoteEditText.getText().toString(), feelingSeekbar.getProgress(),
                                trainsessionSeekbar.getProgress(), spinner.getSelectedItem().toString(),
                                dateBtn.getText().toString(), titles);

                        otherNoteEditText.setText("");
                        dateBtn.setText(getTodaysDate());
                        trainsessionSeekbar.setProgress(5);
                        feelingSeekbar.setProgress(5);
                        titles.clear();
                        rowRecyclerView.setAdapter(rowRecyclerAdapter);
                    }

        });


        mAuth = FirebaseAuth.getInstance();

        //Navigation menu
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

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        month = month + 1;
        String date = dayOfMonth + "/" + month + " - " + year;
        return date;
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + " - " + year;
                dateBtn.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, dayOfMonth);
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
        //Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void addNote(String text, int feeling, int trainsession, String trainingType, String date, List<Row> titles){

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Map<String, Object> map = new HashMap<>();
        map.put("text", text);
        map.put("feeling", feeling);
        map.put("trainsession", trainsession);
        map.put("trainingType", trainingType);
        map.put("created", date);
        map.put("userId", userId);
        map.put("title", titles);


        FirebaseFirestore.getInstance()
                .collection("notes")
                //.add(note)
                .add(map)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "onSuccess: Succesfully added the note");
                        Toast.makeText(Add.this, "Bra jobbat!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Add.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }


    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            if(direction == ItemTouchHelper.LEFT){
                titles.remove(position);
                rowRecyclerAdapter.notifyItemRemoved(position);
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(Add.this, R.color.teal_700))
                    .addActionIcon(R.drawable.ic_baseline_delete_24)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

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