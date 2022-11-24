package com.varsitycollege.imbizoappwil02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Profile extends AppCompatActivity {

    TextView txtName,txtEmail;
    private FirebaseAuth mAuth;
    String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
    ImageView imgbackAllCategories;

    String type;

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


        Intent i = getIntent();
        type = i.getStringExtra("TypeUser");

        //Code to prevent dark mode on users phone
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        txtName = findViewById(R.id.txtProfileName);
        txtEmail = findViewById(R.id.txtProfileEmail);
        imgbackAllCategories = findViewById(R.id.imgbackAllCategories);

        imgbackAllCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListUtils.collectionList.clear();

                if (type.equals("User")){
                    Intent k = new Intent(Profile.this,userHome.class);
                    //Intent k = new Intent(Login.this,userHome.class);
                    k.putExtra("TypeUser",type);
                    startActivity(k);
                }

                if (type.equals("Admin")){
                    Intent j = new Intent(Profile.this,adminHome.class);
                    j.putExtra("TypeUser" ,type);
                    startActivity(j);
                }
              /*  Intent i = new Intent(Profile.this,Categories.class);
                startActivity(i);*/
            }
        });

        txtName.setText(user_id);
        //txtEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());


    }
}