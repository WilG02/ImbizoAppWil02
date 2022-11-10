package com.varsitycollege.imbizoappwil02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Privacy_policy extends AppCompatActivity {

    private WebView Privacywebview;

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
        setContentView(R.layout.activity_privacy_policy);

        //---------------------------------------Code Attribution------------------------------------------------
        //Author:Coding in Flow
        //Uses:Opening privacy google form in the application

        WebView myWebView = (WebView) findViewById(R.id.Privacywebview);
        myWebView.setWebViewClient(new WebViewClient()); //Loads in the application
        myWebView.loadUrl("https://www.termsfeed.com/live/27e7bf63-9772-4daa-88f2-909397e54e27"); //Url of the loaded website

        myWebView = new WebView(Privacy_policy.this);
        setContentView(myWebView);

        myWebView.loadUrl("https://www.termsfeed.com/live/27e7bf63-9772-4daa-88f2-909397e54e27");
    }

    @Override
    public void onBackPressed() {
        if (Privacywebview.canGoBack()) {
            Privacywebview.goBack();
        } else {
            super.onBackPressed();
            {
                Intent i = new Intent(Privacy_policy.this,Categories.class);
                startActivity(i);
            }
        }}

//Link:https://www.youtube.com/watch?v=TUXui5ItBkM
//-----------------------------------------------End------------------------------------------------------

}