package com.varsitycollege.imbizoappwil02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

public class Ratings extends AppCompatActivity {

    ImageView img_backFromFeedback;
    private WebView Feedbackwebview;

    RatingBar ratingBar;
    Button btnRate;
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


        //---------------------------------------Code Attribution------------------------------------------------
        //Author:Coding in Flow
        //Uses:Opening feeback google form in the application

        WebView myWebView = (WebView) findViewById(R.id.Feedbackwebview);
        myWebView.setWebViewClient(new WebViewClient()); //Loads in the application
        myWebView.loadUrl("https://forms.gle/kmxWg66fgqd3xMXc7"); //Url of the loaded website

        myWebView = new WebView(Ratings.this);
        setContentView(myWebView);

        myWebView.loadUrl("https://forms.gle/Ga14csS5QXu6ABzq6");


    }
    @Override
    public void onBackPressed() {
        if (Feedbackwebview.canGoBack()) {
            Feedbackwebview.goBack();
        } else {
            super.onBackPressed();
            {
                Intent i = new Intent(Ratings.this,Categories.class);
                startActivity(i);
            }
        }}

//Link:https://www.youtube.com/watch?v=TUXui5ItBkM
//-----------------------------------------------End------------------------------------------------------

}