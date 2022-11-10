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
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Categories extends AppCompatActivity {
//-------------------------------------------MUHAMMAD-----------------------------------------------
//--------------------------------------------START-------------------------------------------------

    //navigation drawer components
    DrawerLayout navi;
    NavigationView navView;
    ImageView img_menuIcon;
    ImageView img_next;

    //Recycler Component
    RecyclerView rcyCollection;

    //Arraylist
    ArrayList<User> userList;
    ArrayList<Collection> collectionList;

    //Firebase Realtime Database reference
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    Collection collect;

    ArrayList<Collection> data= new ArrayList<>();

    private FirebaseAuth mAuth;


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
        setContentView(R.layout.activity_categories);

        ListUtils.collectionList.clear();

        //Linking component with User interface
        navi = findViewById(R.id.drawer_layout);

        img_menuIcon = findViewById(R.id.img_menu_icon);
        navView = findViewById(R.id.nav_view);
        View v = navView.getHeaderView(0);
        ImageView img = v.findViewById(R.id.imageView);
        img.setImageResource(R.drawable.imbizo_logo_splash);

        rcyCollection=findViewById(R.id.rcy_Collection);

        img_menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navi.openDrawer(GravityCompat.START);
            }
        });

        img_next = findViewById(R.id.img_next);

        img_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(Categories.this,IndividualCategory.class);
                startActivity(next);
            }
        });

        //Display category data
        CategoriesData();

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                navi.closeDrawer(GravityCompat.START);

                switch (id) {
                    case R.id.manage_profile:
                        Intent redirectToProfile = new Intent(Categories.this,Profile.class);
                        startActivity(redirectToProfile);
                        Toast.makeText(Categories.this, "Managing Profile", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.aptitude:
                        Toast.makeText(Categories.this, "Redirecting to aptitude test", Toast.LENGTH_SHORT).show();
                        Intent redirectToTest = new Intent(Categories.this,aptitude_test.class);
                        startActivity(redirectToTest);
                        break;

                    case R.id.about:
                        //Toast.makeText(Categories.this, "About", Toast.LENGTH_SHORT).show();
                        Intent redirectToAbout = new Intent(Categories.this,About.class);
                        startActivity(redirectToAbout);
                        break;

                    case R.id.feedback:
                        //Toast.makeText(Categories.this, "Feedback", Toast.LENGTH_SHORT).show();
                        Intent redirectToFeedback = new Intent(Categories.this,Ratings.class);
                        startActivity(redirectToFeedback);
                        break;

                    case R.id.privacy_policy:
                        Toast.makeText(Categories.this, "Privacy Policy", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.contact_us:
                        Toast.makeText(Categories.this, "Contact our helpline", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.sign_out:
                        //-------------------------------------------Kimaya-----------------------------------------------
                        ListUtils.collectionList.clear();
                        FirebaseUser user = mAuth.getCurrentUser();

                        if (user != null) {
                            String userEmail = user.getEmail();
                            Toast.makeText(Categories.this, "Goodbye "+userEmail, Toast.LENGTH_SHORT).show();
                            mAuth.getInstance().signOut();
                            FirebaseAuth.getInstance().signOut();
                        }

                        Toast.makeText(Categories.this, "Goodbye" , Toast.LENGTH_SHORT).show();
                        Intent returnLogin = new Intent(Categories.this, Splash.class);
                        startActivity(returnLogin);



                        /* FirebaseAuth.getInstance().signOut();
                        Intent returnLogin = new Intent(Categories.this, Splash.class);
                        startActivity(returnLogin);
                        *//*Intent feedback = new Intent(Categories.this, Ratings.class);
                        startActivity(feedback);*/
                        //-------------------------------------------Kimaya-----------------------------------------------
                        Toast.makeText(Categories.this, "Goodbye", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });
//----------------------------------------------END-------------------------------------------------
//-------------------------------------------MUHAMMAD-----------------------------------------------

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
                rcyCollection.setLayoutManager(layoutManager);
                rcyCollection.setItemAnimator(new DefaultItemAnimator());
                recyclerCollectionAdapter adapter = new recyclerCollectionAdapter(ListUtils.collectionList, getApplicationContext());
                //                recyclerCollectionAdapter adapter = new recyclerCollectionAdapter(collectionList, getApplicationContext());
                rcyCollection.setAdapter(adapter);
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
                        Intent i = new Intent(Categories.this, IndividualCategory.class);

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

   /* private void CategoriesData() {
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
                rcyCollection.setLayoutManager(layoutManager);
                rcyCollection.setItemAnimator(new DefaultItemAnimator());
                recyclerCollectionAdapter adapter = new recyclerCollectionAdapter(ListUtils.collectionList, getApplicationContext());
                rcyCollection.setAdapter(adapter);
                //Link:https://www.youtube.com/watch?v=__OMnFR-wZU
                //-----------------------------------------------End------------------------------------------------------

                //---------------------------------------Code Attribution------------------------------------------------
                //Author:Coding in Flow
                //Uses:When a specfic item in recyclerview is clicked on,redirect user to their list of items in collection

                adapter.setOnCollectionClickListener(new recyclerCollectionAdapter.OnCollectionClickListener() {
                    @Override
                    public void onCollectionClick(int position) {
                        Intent i = new Intent(Categories.this, IndividualCategory.class);

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
    }*/
}