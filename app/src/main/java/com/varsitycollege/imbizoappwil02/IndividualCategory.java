package com.varsitycollege.imbizoappwil02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class IndividualCategory extends AppCompatActivity {

    VideoView video,audio;
    YouTubePlayerView youtubePlayerView;
    ImageView backAll,categoryImage;

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
        setContentView(R.layout.activity_individual_category);

        video = findViewById(R.id.videoView);
        audio = findViewById(R.id.audioView);
        categoryImage=findViewById(R.id.img_categoryImg);
        backAll=findViewById(R.id.img_backToCat);

        backAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(IndividualCategory.this,Categories.class);
                ListUtils.collectionList.clear();
                startActivity(i);
            }
        });

        //---------------------------------------Code Attribution------------------------------------------------
        //Author:CodingSTUFF
        //Uses:Display image fore firebase storage using url
        //Glide.with(getApplicationContext()).load(item.getImgURL()).into(categoryImage);
        //Link:https://www.youtube.com/watch?v=iEcokZOv5UY
        //-----------------------------------------------End------------------------------------------------------

        /*//Display video using a url
        //https://www.youtube.com/watch?v=Zf9pOhlqRXo
        video.setVideoURI(Uri.parse("https://www.youtube.com/watch?v=fLexgOxsZu0"));
        video.setMediaController(new MediaController(this));
        video.requestFocus();
        video.start();

        audio.setVideoURI(Uri.parse("https://www.youtube.com/watch?v=fLexgOxsZu0"));
        audio.setMediaController(new MediaController(this));
        audio.requestFocus();
        audio.start();*/

        //https://www.youtube.com/watch?v=gXWXKjR-qII
        //Youtube player
        //https://github.com/PierfrancescoSoffritti/android-youtube-player

        youtubePlayerView = findViewById(R.id.player);
        getLifecycle().addObserver(youtubePlayerView);

        youtubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = "1EYfOvrJt74";
                youTubePlayer.loadVideo(videoId, 0);
            }
        });




    }
}