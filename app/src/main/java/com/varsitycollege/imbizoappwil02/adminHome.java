package com.varsitycollege.imbizoappwil02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class adminHome extends AppCompatActivity {
    //Firebase Realtime Database reference
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    RecyclerView rcyCollectionAdmin;
    Collection collect;
    ArrayList<Collection> collectionList = new ArrayList<Collection>();

    ImageView imgAddCat;

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
        setContentView(R.layout.activity_admin_home);

        ListUtils.collectionList.clear();

        rcyCollectionAdmin = findViewById(R.id.rcyAllCategories);
        imgAddCat = findViewById(R.id.imgAddCategory);

        imgAddCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent i = new Intent(adminHome.this,AddCategory.class);
            startActivity(i);
            }
        });

        CategoriesData();
    }

    private void CategoriesData() {
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
                    ListUtils.collectionList.add(collect);
                }
                //Link:https://www.youtube.com/watch?v=Ydn5cXn1j-0&list=PL480DYS-b_kdor_f0IFgS7iiEsOwxdx6w&index=26
                //-----------------------------------------------End------------------------------------------------------

                //---------------------------------------Code Attribution------------------------------------------------
                //Author:Ben O'Brien
                //Uses:set the recycleCollectionAdapter and display users data in the recyclerview
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                rcyCollectionAdmin.setLayoutManager(layoutManager);
                rcyCollectionAdmin.setItemAnimator(new DefaultItemAnimator());
                recyclerCollectionAdapter adapter = new recyclerCollectionAdapter(ListUtils.collectionList, getApplicationContext());
                //                recyclerCollectionAdapter adapter = new recyclerCollectionAdapter(collectionList, getApplicationContext());
                rcyCollectionAdmin.setAdapter(adapter);
                //recyclerCollectionAdapter adapter = new recyclerCollectionAdapter(collectionList);
                //                recyclerCollectionAdapter adapter = new recyclerCollectionAdapter(collectionList, getApplicationContext());
                //rcyCollection.setAdapter(adapter);
                //Link:https://www.youtube.com/watch?v=__OMnFR-wZU
                //-----------------------------------------------End------------------------------------------------------


                //---------------------------------------Code Attribution------------------------------------------------
                //Author:Coding in Flow
                //Uses:When a specfic item in recyclerview is clicked on,redirect user to their list of items in collection

                adapter.setOnCollectionClickListener(new recyclerCollectionAdapter.OnCollectionClickListener() {
                    @Override
                    public void onCollectionClick(int position) {
                        Intent i = new Intent(adminHome.this, IndividualCategory.class);

                        //---------------------------------------Code Attribution------------------------------------------------
                        //Author:Coding in Flow
                        //Uses:Passing a collection object to the CollectionItem class using an intent
                        i.putExtra("Collection",ListUtils.collectionList.get(position));
                        //Link:https://www.youtube.com/watch?v=WBbsvqSu0is
                        //-----------------------------------------------End------------------------------------------------------

                        startActivity(i);
                    }
                });
                //Link:https://www.youtube.com/watch?v=bhhs4bwYyhc&list=PLrnPJCHvNZuBtTYUuc5Pyo4V7xZ2HNtf4&index=4
                //-----------------------------------------------End------------------------------------------------------

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
}