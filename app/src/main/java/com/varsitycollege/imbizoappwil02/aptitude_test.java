package com.varsitycollege.imbizoappwil02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class aptitude_test extends AppCompatActivity {

    private WebView testWebview;
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
        setContentView(R.layout.activity_aptitude_test);


        //---------------------------------------Code Attribution------------------------------------------------
        //Author:Coding in Flow
        //Uses:Opening feeback google form in the application

        WebView myWebView = (WebView) findViewById(R.id.Testwebview);
        myWebView.setWebViewClient(new WebViewClient()); //Loads in the application
        myWebView.loadUrl("https://forms.gle/kmxWg66fgqd3xMXc7"); //Url of the loaded website

        myWebView = new WebView(aptitude_test.this);
        setContentView(myWebView);

        myWebView.loadUrl("https://www.aptitude-test.com/free-aptitude-test/quick-test/");


    }
    @Override
    public void onBackPressed() {
        if (testWebview.canGoBack()) {
            testWebview.goBack();
        } else {
            super.onBackPressed();
            {

            }
        }}

//Link:https://www.youtube.com/watch?v=TUXui5ItBkM
//-----------------------------------------------End------------------------------------------------------

}