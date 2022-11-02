package com.varsitycollege.imbizoappwil02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {

    Button btnAdmin,btnUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnUser = findViewById(R.id.btn_loginUser);
        btnAdmin = findViewById(R.id.btn_loginAdmin);

        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this,Login.class);
                i.putExtra("TypeUser" , "User");
                startActivity(i);
            }
        });


        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this,Login.class);
                i.putExtra("TypeUser" , "Admin");
                startActivity(i);
            }
        });
    }
}