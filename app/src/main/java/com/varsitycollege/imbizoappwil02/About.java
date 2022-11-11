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

public class About extends AppCompatActivity {

    ImageView img_backFromAbout;
    private WebView aboutwebview;


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
        setContentView(R.layout.activity_about);

        img_backFromAbout=findViewById(R.id.img_returnFromAbout);
        img_backFromAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(About.this,Categories.class);
                startActivity(i);
            }
        });

        //---------------------------------------Code Attribution------------------------------------------------
        //Author:Coding in Flow
        //Uses:Opening about,imbizo foundation website

        aboutwebview = (WebView) findViewById(R.id.aboutwebview);
        WebView myWebView = (WebView) findViewById(R.id.aboutwebview);
        myWebView.setWebViewClient(new WebViewClient()); //Loads in the application
        myWebView.loadUrl("https://www.imbizofoundation.com/"); //Url of the loaded website

        myWebView = new WebView(About.this);
        setContentView(myWebView);

        myWebView.loadUrl("https://www.imbizofoundation.com/");

        //Link:https://www.youtube.com/watch?v=TUXui5ItBkM
        //-----------------------------------------------End------------------------------------------------------

    }
    @Override
    public void onBackPressed() {
        if (aboutwebview.canGoBack()) {
            aboutwebview.goBack();
        } else {
            super.onBackPressed();
            {
                Intent i = new Intent(About.this,Categories.class);
                startActivity(i);
            }
        }}
}