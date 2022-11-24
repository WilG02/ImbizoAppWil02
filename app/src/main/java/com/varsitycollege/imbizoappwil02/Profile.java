package com.varsitycollege.imbizoappwil02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Profile extends AppCompatActivity {

    EditText edtName;
    Button btnupdateName;
    private FirebaseAuth mAuth;
    String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
    ImageView imgbackAllCategories;

    Users userloggedIn;
    //Firebase Realtime Database reference
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    String type;

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
        setContentView(R.layout.activity_profile);

        Intent i = getIntent();
        type = i.getStringExtra("TypeUser");

        userloggedIn = ListUtils.userLoggedList.get(0);

        //Code to prevent dark mode on users phone
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        edtName = findViewById(R.id.edtProfileName);
        btnupdateName = findViewById(R.id.btn_UpdateProfile);
        imgbackAllCategories = findViewById(R.id.imgbackAllCategories);

        edtName.setText(userloggedIn.getName());

        btnupdateName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //---------------------------------------Code Attribution------------------------------------------------
                //Author:Foxandroid
                //Uses:Update data in Firebase realtime database
                HashMap profileUpdate = new HashMap();
                profileUpdate.put("admin",userloggedIn.getAdmin());
                profileUpdate.put("email",userloggedIn.getEmail());
                profileUpdate.put("id",userloggedIn.getId());
                profileUpdate.put("name",edtName.getText().toString());
                profileUpdate.put("password",userloggedIn.getPassword());

                Users userlog = new Users(userloggedIn.getId(),edtName.getText().toString(),userloggedIn.getEmail(),userloggedIn.getPassword(),userloggedIn.getAdmin());

                //myRef = database.getReference().child("Users").child(userloggedIn.getId());

                myRef = database.getReference();

                myRef.child("Users").child(userloggedIn.getId()).updateChildren(profileUpdate).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()){
                            ListUtils.userLoggedList.clear();
                            ListUtils.userLoggedList.add(userlog);
                            Toast.makeText(Profile.this, "Update Successful!", Toast.LENGTH_SHORT).show();
                        }
                    }});
                //Link:https://www.youtube.com/watch?v=oNNHR-LeTnI
                //-----------------------------------------------End------------------------------------------------------
            }
        });

        imgbackAllCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListUtils.collectionList.clear();

                if (type.equals("User")){
                    Intent k = new Intent(Profile.this,userHome.class);
                    //Intent k = new Intent(Login.this,userHome.class);
                    k.putExtra("TypeUser",type);
                    startActivity(k);
                }

                if (type.equals("Admin")){
                    Intent j = new Intent(Profile.this,adminHome.class);
                    j.putExtra("TypeUser" ,type);
                    startActivity(j);
                }
            }
        });

    }
}