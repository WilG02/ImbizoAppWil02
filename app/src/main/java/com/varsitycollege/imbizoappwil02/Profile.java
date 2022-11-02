package com.varsitycollege.imbizoappwil02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Profile extends AppCompatActivity {


    TextView txtName,txtEmail;
    private FirebaseAuth mAuth;
    String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtName = findViewById(R.id.txtProfileName);
        txtEmail = findViewById(R.id.txtProfileEmail);

        txtName.setText(user_id);
        //txtEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());


    }
}