package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Settings extends AppCompatActivity {
    private FirebaseUser user;
    private DatabaseReference reference;

    private String userID;
    private Button logout;

    ImageView profileImageView;
    Button updateProfileBtn;
    ProgressBar progressBar;

    int TAKE_IMAGE_CODE = 10001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        progressBar = findViewById(R.id.progressBar3);
        updateProfileBtn = (Button) findViewById(R.id.updateProfileBtn);
        profileImageView = (ImageView) findViewById(R.id.profileImageView);
        logout = (Button) findViewById(R.id.logoutbtn);



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                FirebaseAuth.getInstance().signOut();
                finish();
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView fullNameTextView = (TextView) findViewById(R.id.ViewFullname);
        final TextView emailTextView = (TextView) findViewById(R.id.ViewEmail);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                user userProfile = snapshot.getValue(user.class);

                if(userProfile != null){
                    String fullName = userProfile.fullName;
                    String email = userProfile.Email;

                    fullNameTextView.setText(fullName);
                    emailTextView.setText(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error){
                Toast.makeText(Settings.this, "Ett fel har inträffat!", Toast.LENGTH_LONG).show();

            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.settings);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.note:
                        startActivity(new Intent(getApplicationContext(), Add.class));
                        overridePendingTransition(0,0);
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
                        return true;
                }
                return false;
            }
        });
    }

    public void updateProfile(View view) {
    }
    /*
    public void handleImageClick(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, TAKE_IMAGE_CODE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == TAKE_IMAGE_CODE) {
            switch(resultCode){
                case RESULT_OK:
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    profileImageView.setI
            }
        }
    }*/
}