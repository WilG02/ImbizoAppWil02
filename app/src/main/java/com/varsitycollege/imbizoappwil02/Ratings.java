package com.varsitycollege.imbizoappwil02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

public class Ratings extends AppCompatActivity {

    ImageView img_backFromFeedback;

    RatingBar ratingBar;
    Button btnRate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings);

        img_backFromFeedback=findViewById(R.id.img_returnFromFeedback);
        img_backFromFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Ratings.this,Categories.class);
                startActivity(i);
            }
        });

        //-----------------------------------Lunga-------------------------
        ratingBar = findViewById(R.id.rating_Bar);
        btnRate = findViewById(R.id.btn_Rate);

        btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = String.valueOf(ratingBar.getRating());
                Toast.makeText(getApplicationContext(),s+"Star", Toast.LENGTH_SHORT).show();
            }
        });
        //-----------------------------------Lunga--------------------------

    }
}