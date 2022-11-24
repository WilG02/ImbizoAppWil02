package com.varsitycollege.imbizoappwil02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class ContactInfo extends AppCompatActivity {

    ImageView imgFContact;
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
        setContentView(R.layout.activity_contact_info);

        //Code to prevent dark mode on users phone
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        Intent i = getIntent();
        type = i.getStringExtra("TypeUser");

        imgFContact = findViewById(R.id.imgBackFContact);

        imgFContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("User")){
                    Intent k = new Intent(ContactInfo.this,userHome.class);
                    //Intent k = new Intent(Login.this,userHome.class);
                    k.putExtra("TypeUser",type);
                    startActivity(k);
                }

                if (type.equals("Admin")){
                    Intent j = new Intent(ContactInfo.this,adminHome.class);
                    j.putExtra("TypeUser" ,type);
                    startActivity(j);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (type.equals("User")) {
            Intent k = new Intent(ContactInfo.this, userHome.class);
            //Intent k = new Intent(Login.this,userHome.class);
            k.putExtra("TypeUser", type);
            startActivity(k);
        }

        if (type.equals("Admin")) {
            Intent j = new Intent(ContactInfo.this, adminHome.class);
            j.putExtra("TypeUser", type);
            startActivity(j);
        }

    }

}