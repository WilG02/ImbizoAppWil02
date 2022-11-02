package com.varsitycollege.imbizoappwil02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
        setContentView(R.layout.activity_admin_home);

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

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
}