package com.varsitycollege.imbizoappwil02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class Categories extends AppCompatActivity {
//-------------------------------------------MUHAMMAD-----------------------------------------------
//--------------------------------------------START-------------------------------------------------

//navigation drawer components
    DrawerLayout navi;
    Button btnMenu;
    NavigationView navView;


    RecyclerView rcyCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

//Linking component with User interface
        navi = findViewById(R.id.drawer_layout);
        btnMenu = findViewById(R.id.button);
        navView = findViewById(R.id.nav_view);
        View v = navView.getHeaderView(0);
        ImageView img = v.findViewById(R.id.imageView);
        img.setImageResource(R.drawable.imbizo_logo_splash);

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navi.openDrawer(GravityCompat.START);
            }
        });


        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                navi.closeDrawer(GravityCompat.START);

                switch (id){
                    case R.id.manage_profile:
                        Toast.makeText(Categories.this, "Managing Profile", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.aptitude:
                        Toast.makeText(Categories.this, "Redirecting to aptitude test", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.privacy_policy:
                        Toast.makeText(Categories.this, "Viewing Privacy Policy", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.contact_us:
                        Toast.makeText(Categories.this, "Contact our helpline", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.sign_out:
                        Toast.makeText(Categories.this, "Goodbye", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });
    }
}
//----------------------------------------------END-------------------------------------------------
//-------------------------------------------MUHAMMAD-----------------------------------------------

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
 //   }
//}