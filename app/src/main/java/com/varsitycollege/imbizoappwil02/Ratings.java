package com.varsitycollege.imbizoappwil02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Ratings extends AppCompatActivity {

    ImageView img_backFromFeedback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings);

        /*img_backFromFeedback.findViewById(R.id.img_returnFromFeedback);
        img_backFromFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Ratings.this,Categories.class);
                startActivity(i);
            }
        });*/

    }
}