package com.varsitycollege.imbizoappwil02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;

public class CategoryDisplay extends AppCompatActivity {

    VideoView video,audio;
    YouTubePlayerView youtubePlayerView;
    ImageView backAll,categoryImage;
    TextView txt_info,txt_heading;
    YouTubePlayerView youtubeplayer;

    Collection c;

    //Firebase Realtime Database reference
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    Collection collect;

    ArrayList<Collection> data= new ArrayList<>();

    String videoId="";

    private String videoId(String url){
        String id ="";
        if (url.contains("youtube")){
            String[] fields = url.split("=");
            videoId = fields[1];
            id = fields[1];
            //catInfoList.Add(new Category(fields[1],(Int32.Parse(fields[0]))));
          /* for (int k = 1;k>=youtubeVideo.length();k++){
               String[] fields = item.Split(';');
               catInfoList.Add(new Category(fields[1],(Int32.Parse(fields[0]))));
           }*/
        }
        return id;
    }

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
        setContentView(R.layout.activity_category_display);

        //Code to prevent dark mode on users phone
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        Intent i = getIntent();
        c = i.getParcelableExtra("Collection");

        video = findViewById(R.id.videoViewDisplay);
        audio = findViewById(R.id.audioViewDisplay);
        categoryImage=findViewById(R.id.img_categoryImgDisplay);
        backAll=findViewById(R.id.img_backToCatFDisplay);
        txt_info=findViewById(R.id.txt_descriptionDisplay);
        txt_heading=findViewById(R.id.txt_categoryNameDisplay);
        youtubeplayer=findViewById(R.id.YoutubevideoPlayer);

        txt_heading.setText(c.getCategoryName());

        backAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CategoryDisplay.this,userHome.class);
                ListUtils.collectionList.clear();
                startActivity(i);
            }
        });


        if (c.getCategoryVideoUrl().contains("youtube")){
            youtubeplayer.setVisibility(View.VISIBLE);
            video.setVisibility(View.INVISIBLE);
        }else{
            youtubeplayer.setVisibility(View.INVISIBLE);
            video.setVisibility(View.VISIBLE);
            //https://www.youtube.com/watch?v=Zf9pOhlqRXo
            String link= c.getCategoryVideoUrl();
            video.setVideoURI(Uri.parse(link));
            video.setMediaController(new MediaController(this));
            video.requestFocus();
            video.start();
        }

        //https://www.youtube.com/watch?v=gXWXKjR-qII
        //Youtube player
        //https://github.com/PierfrancescoSoffritti/android-youtube-player

        getLifecycle().addObserver(youtubeplayer);
        youtubeplayer.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                videoId = videoId(c.getCategoryVideoUrl());
                youTubePlayer.loadVideo(videoId, 0);
            }
        });

        //---------------------------------------Code Attribution------------------------------------------------
        //Author:CodingSTUFF
        //Uses:Display image fore firebase storage using url
        Glide.with(getApplicationContext()).load(c.getCategoryImageUrl()).into(categoryImage);
        //Link:https://www.youtube.com/watch?v=iEcokZOv5UY
        //-----------------------------------------------End------------------------------------------------------

        txt_heading.setText(c.getCategoryName());
        txt_info.setText(c.getCategoryInformation());

       /* //https://www.youtube.com/watch?v=Zf9pOhlqRXo
        String link= c.getCategoryVideoUrl();
        video.setVideoURI(Uri.parse(link));
        video.setMediaController(new MediaController(this));
        video.requestFocus();
        video.start();*/

        audio.setVideoURI(Uri.parse(c.getCategoryPodcastUrl()));
        audio.setMediaController(new MediaController(this));
        audio.requestFocus();
        audio.start();

    }
}