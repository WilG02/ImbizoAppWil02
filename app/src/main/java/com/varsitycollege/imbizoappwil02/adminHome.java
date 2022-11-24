package com.varsitycollege.imbizoappwil02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
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

public class adminHome extends AppCompatActivity {

    //navigation drawer components
    DrawerLayout naviA;
    NavigationView navViewA;
    ImageView img_menuIconA;

    //Firebase Realtime Database reference
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    RecyclerView rcyCollectionAdmin;
    Collection collect;
    ArrayList<Collection> collectionList = new ArrayList<Collection>();

    ImageView imgAddCat;

    private FirebaseAuth mAuth;

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
        setContentView(R.layout.activity_admin_home);

        //Code to prevent dark mode on users phone
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        ListUtils.collectionList.clear();

        Intent i = getIntent();
        type = i.getStringExtra("TypeUser");

        //Linking component with User interface
        naviA = findViewById(R.id.drawerAdmin_layout);
        img_menuIconA = findViewById(R.id.img_adminmenu_icon);
        navViewA = findViewById(R.id.navAdmin_view);
        View v = navViewA.getHeaderView(0);
        ImageView img = v.findViewById(R.id.imageView);
        img.setImageResource(R.drawable.imbizologo);

        img_menuIconA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                naviA.openDrawer(GravityCompat.START);
            }
        });


        navViewA.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                naviA.closeDrawer(GravityCompat.START);

                switch (id) {
                    case R.id.profileA:
                        Intent redirectToProfile = new Intent(adminHome.this,Profile.class);
                        redirectToProfile.putExtra("TypeUser" ,type);
                        startActivity(redirectToProfile);
                        //Toast.makeText(adminHome.this, "Managing Profile", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.aptitudeTestA:
                        //Toast.makeText(adminHome.this, "Redirecting to aptitude test", Toast.LENGTH_SHORT).show();
                        Intent redirectToTest = new Intent(adminHome.this,aptitude_test.class);
                        redirectToTest.putExtra("TypeUser" ,type);
                        startActivity(redirectToTest);
                        break;

                    case R.id.aboutA:
                        //Toast.makeText(Categories.this, "About", Toast.LENGTH_SHORT).show();
                        Intent redirectToAbout = new Intent(adminHome.this,About.class);
                        redirectToAbout.putExtra("TypeUser" ,type);
                        startActivity(redirectToAbout);
                        break;

                    case R.id.feedbackA:
                        //Toast.makeText(Categories.this, "Feedback", Toast.LENGTH_SHORT).show();
                        Intent redirectToFeedback = new Intent(adminHome.this,Ratings.class);
                        redirectToFeedback.putExtra("TypeUser" ,type);
                        startActivity(redirectToFeedback);
                        break;

                    case R.id.privacy_policyA:
                        //Toast.makeText(Categories.this, "Privacy Policy", Toast.LENGTH_SHORT).show();
                        Intent redirectToPrivacy = new Intent(adminHome.this,Privacy_policy.class);
                        redirectToPrivacy.putExtra("TypeUser" ,type);
                        startActivity(redirectToPrivacy);
                        break;

                    case R.id.contact_usA:
                       // j.putExtra("TypeUser" ,type);
                       // Toast.makeText(adminHome.this, "Contact our helpline", Toast.LENGTH_SHORT).show();
                        Intent redirectToContact = new Intent(adminHome.this,ContactInfo.class);
                        redirectToContact.putExtra("TypeUser" ,type);
                        startActivity(redirectToContact);
                        break;

                    case R.id.addAdmin:
                        // j.putExtra("TypeUser" ,type);
                        //Toast.makeText(adminHome.this, "Contact our helpline", Toast.LENGTH_SHORT).show();
                        Intent redirectToAddContact= new Intent(adminHome.this,Register.class);
                        redirectToAddContact.putExtra("TypeUser" ,type);
                        startActivity(redirectToAddContact);
                        break;

                    case R.id.sign_outA:
                        //-------------------------------------------Kimaya-----------------------------------------------
                        ListUtils.collectionList.clear();
                        //FirebaseUser user = mAuth.getCurrentUser();

                     /*   if (user != null) {
                            String userEmail = user.getEmail();
                            Toast.makeText(adminHome.this, "Goodbye "+userEmail, Toast.LENGTH_SHORT).show();
                            mAuth.getInstance().signOut();
                            FirebaseAuth.getInstance().signOut();
                        }*/

                        //mAuth.getInstance().signOut();
                        FirebaseAuth.getInstance().signOut();
                        //Toast.makeText(adminHome.this, "Goodbye" , Toast.LENGTH_SHORT).show();
                        Intent returnLogin = new Intent(adminHome.this, Splash.class);
                        startActivity(returnLogin);



                        /* FirebaseAuth.getInstance().signOut();
                        Intent returnLogin = new Intent(Categories.this, Splash.class);
                        startActivity(returnLogin);
                        *//*Intent feedback = new Intent(Categories.this, Ratings.class);
                        startActivity(feedback);*/
                        //-------------------------------------------Kimaya-----------------------------------------------
                        //Toast.makeText(adminHome.this, "Goodbye", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });




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