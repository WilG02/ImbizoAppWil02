package com.varsitycollege.imbizoappwil02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;

public class IndividualCategory extends AppCompatActivity {

    VideoView video,audio;
    YouTubePlayerView youtubePlayerView;
    ImageView backAll,categoryImage,imgDelete,imgEdit;
    TextView txt_info,txt_heading;
    Button btnSave;

    Collection c;

    //Firebase Realtime database Reference
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    
    //storage
    StorageReference storage = FirebaseStorage.getInstance().getReference();
  

    Collection collect;

    ArrayList<Collection> data= new ArrayList<>();

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

        Intent i = getIntent();
        c = i.getParcelableExtra("Collection");

        video = findViewById(R.id.videoView);
        audio = findViewById(R.id.audioView);
        categoryImage=findViewById(R.id.img_categoryImg);
        backAll=findViewById(R.id.img_backToCat);
        txt_info=findViewById(R.id.txt_Information);
        txt_heading=findViewById(R.id.txt_singleCategory);
        imgDelete = findViewById(R.id.imgDelete);
        imgEdit = findViewById(R.id.imgEdit);
        btnSave = findViewById(R.id.btn_Save);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        //---------------------------------------Code Attribution------------------------------------------------
        //Author:Foxandroid
        //Uses:Delete data from firebase realtime database
        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
               /* storage.child("Image/").child(c.getCategoryImageUrl()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(IndividualCategory.this, "Image Deleted!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(IndividualCategory.this, "Failed Imaged Delete!", Toast.LENGTH_SHORT).show();
                    }
                });
*/

                myRef.child("Categories").child(c.getCategoryId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(IndividualCategory.this, "Category Successfully Deleted!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(IndividualCategory.this,adminHome.class);
                            startActivity(i);
                        }else {
                            Toast.makeText(IndividualCategory.this, "Delete Failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        //Link:https://www.youtube.com/watch?v=L3u6T8uzT58
        //-----------------------------------------------End------------------------------------------------------


        txt_heading.setText(c.getCategoryName());

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
        Glide.with(getApplicationContext()).load(c.getCategoryImageUrl()).into(categoryImage);
        //Link:https://www.youtube.com/watch?v=iEcokZOv5UY
        //-----------------------------------------------End------------------------------------------------------

        txt_heading.setText(c.getCategoryName());
        txt_info.setText(c.getCategoryInformation());

        //https://www.youtube.com/watch?v=Zf9pOhlqRXo
        String link= c.getCategoryVideoUrl();
        video.setVideoURI(Uri.parse(link));
        video.setMediaController(new MediaController(this));
        video.requestFocus();
        video.start();

        audio.setVideoURI(Uri.parse(c.getCategoryPodcastUrl()));
        audio.setMediaController(new MediaController(this));
        audio.requestFocus();
        audio.start();


      /*  *//*youtubePlayerView = findViewById(R.id.player);
        getLifecycle().addObserver(youtubePlayerView);*//*

        //---------------------------------------Code Attribution------------------------------------------------
        //Author:Sarina Till
        //Uses:Read data from firebase realtime database
        // reference for data in firebase
        myRef = database.getReference().child("Categories");

        //get data from firebase whilst using reference
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // instance of collection class
                collect = new Collection();

                //pulling data from realtime firebase
                for (DataSnapshot collectFirebase : snapshot.getChildren()) {
                    // snapshot is assigned to the collection instance
                    collect = collectFirebase.getValue(Collection.class);
                    //Add instance to arraylist collectionList
                    ListUtils.CategoryDataList.add(collect);
                }
                //Link:https://www.youtube.com/watch?v=Ydn5cXn1j-0&list=PL480DYS-b_kdor_f0IFgS7iiEsOwxdx6w&index=26
                //-----------------------------------------------End------------------------------------------------------

                //---------------------------------------Code Attribution------------------------------------------------
                //Author:CodingSTUFF
                //Uses:Display image fore firebase storage using url
                Glide.with(getApplicationContext()).load(ListUtils.CategoryDataList.get(0).getCategoryImageUrl()).into(categoryImage);
                //Link:https://www.youtube.com/watch?v=iEcokZOv5UY
                //-----------------------------------------------End------------------------------------------------------

                txt_heading.setText(ListUtils.CategoryDataList.get(0).getCategoryName());
                txt_info.setText(ListUtils.CategoryDataList.get(0).getCategoryInformation());

                String link= ListUtils.CategoryDataList.get(0).getCategoryVideoUrl();

                *//*for (int i = 0; i < link.length(); i++) {
                    char ch = link[i];
                   if (link[i].equals("=")){

                   }
                }*//*
               *//* youtubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                        String videoId = "1EYfOvrJt74";
                        youTubePlayer.loadVideo(videoId, 0);
                    }
                });*//*

                //https://www.youtube.com/watch?v=Zf9pOhlqRXo
                video.setVideoURI(Uri.parse(link));


            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(video);
        video.setMediaController(mediaController);
        video.requestFocus();
        video.start();*/


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
    }
}