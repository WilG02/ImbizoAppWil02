package com.varsitycollege.imbizoappwil02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Profile extends AppCompatActivity {

    TextView txtName,txtEmail;
    private FirebaseAuth mAuth;
    String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //---------------------------------------Code Attribution------------------------------------------------
        //Author:geeksforgeeks
        //Uses:Hides the action bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide(); //Hide the action bar
        //Link:https://www.geeksforgeeks.org/different-ways-to-hide-action-bar-in-android-with-examples/#:~:text=If%20you%20want%20to%20hide,AppCompat
        //-----------------------------------------------End------------------------------------------------------
        setContentView(R.layout.activity_profile);
        txtName = findViewById(R.id.txtProfileName);
        txtEmail = findViewById(R.id.txtProfileEmail);

        txtName.setText(user_id);
        //txtEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());


    }
}