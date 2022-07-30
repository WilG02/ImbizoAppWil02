package com.varsitycollege.imbizoappwil02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

public class Categories extends AppCompatActivity {

    RecyclerView rcyCollection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

       /* //---------------------------------------Code Attribution------------------------------------------------
        //Author:Ben O'Brien
        //Uses:set the recycleCollectionAdapter and display users data in the recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rcyCollection.setLayoutManager(layoutManager);
        rcyCollection.setItemAnimator(new DefaultItemAnimator());
        recyclerCollectionAdapter adapter = new recyclerCollectionAdapter(ListUtils.collectionList,getApplicationContext());
        rcyCollection.setAdapter(adapter);
        //Link:https://www.youtube.com/watch?v=__OMnFR-wZU
        //-----------------------------------------------End------------------------------------------------------

        //---------------------------------------Code Attribution------------------------------------------------
        //Author:Coding in Flow
        //Uses:When a specfic item in recyclerview is clicked on,redirect user to their list of items in collection
        adapter.setOnCollectionClickListerner(new recyclerCollectionAdapter.OnCollectionClickListerner() {
            @Override
            public void onCollectionClick(int position) {
                Intent i = new Intent(Categories.this,Category.class);

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
        //-----------------------------------------------End------------------------------------------------------*/
    }
}