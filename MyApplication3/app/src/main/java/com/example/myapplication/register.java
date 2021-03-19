//CREATED BY MAHMOD MOHAMMAD

package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class register extends AppCompatActivity {
    EditText mFullname,mEmail,mPassword,mPhone;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullname   = findViewById(R.id.Fullname);
        mEmail      = findViewById((R.id.Email);
        mPassword   = findViewById(R.id.Password);
        mPhone      = findViewById(R.id.Phone);
        mRegisterBtn= findViewById(R.id.Register);
        mLoginBtn   = findViewById(R.id.createText);

        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);


        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();


                if(TextUtils.isEmpty(email)){            //coment: the user did not enter his email.
                    mEmail.setError("Email måste skrivas");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Lösenordet måste skrivas");
                    return;
                }
                if(password.length() < 8){
                    mPassword.setError("Lösenordet måste minst vara 8 tecken");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                //The following code is to register the user
            }
        });

}