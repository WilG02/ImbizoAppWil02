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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SearchView;
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

public class userHome extends AppCompatActivity  {

    //navigation drawer components
    DrawerLayout navi;
    NavigationView navView;
    ImageView img_menuIcon;

    private FirebaseAuth mAuth;

    String type;

    //implements recyclerCollectionAdapter.CollectionClickListener
    //Firebase Realtime Database reference
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    RecyclerView rcyCollection;
    Collection collect;
    private SearchView userSearch;
    recyclerCollectionAdapter adapter;

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
        setContentView(R.layout.activity_user_home);

        //Code to prevent dark mode on users phone
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        ListUtils.collectionList.clear();

        Intent i = getIntent();
        type = i.getStringExtra("TypeUser");

        //Linking component with User interface
        navi = findViewById(R.id.drawer_layoutU);

        img_menuIcon = findViewById(R.id.img_menu_iconU);
        navView = findViewById(R.id.nav_viewU);
        View v = navView.getHeaderView(0);
        ImageView img = v.findViewById(R.id.imageView);
        img.setImageResource(R.drawable.imbizologo);

        rcyCollection = findViewById(R.id.rcyAllCategoriesDisplay);
        CategoriesData();

        img_menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navi.openDrawer(GravityCompat.START);
            }
        });

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                navi.closeDrawer(GravityCompat.START);

                switch (id) {
                    case R.id.manage_profile:
                        Intent redirectToProfile = new Intent(userHome.this,Profile.class);
                        redirectToProfile.putExtra("TypeUser" ,type);
                        startActivity(redirectToProfile);
                        break;

                    case R.id.aptitude:
                        Intent redirectToTest = new Intent(userHome.this,aptitude_test.class);
                        redirectToTest.putExtra("TypeUser" ,type);
                        startActivity(redirectToTest);
                        break;

                    case R.id.about:
                        Intent redirectToAbout = new Intent(userHome.this,About.class);
                        redirectToAbout.putExtra("TypeUser" ,type);
                        startActivity(redirectToAbout);
                        break;

                    case R.id.feedback:
                        Intent redirectToFeedback = new Intent(userHome.this,Ratings.class);
                        redirectToFeedback.putExtra("TypeUser" ,type);
                        startActivity(redirectToFeedback);
                        break;

                    case R.id.privacy_policy:
                        Intent redirectToPrivacy = new Intent(userHome.this,Privacy_policy.class);
                        redirectToPrivacy.putExtra("TypeUser" ,type);
                        startActivity(redirectToPrivacy);
                        break;

                    case R.id.contact_us:
                        Intent redirectToContact = new Intent(userHome.this,ContactInfo.class);
                        redirectToContact.putExtra("TypeUser" ,type);
                        startActivity(redirectToContact);
                        break;

                    case R.id.sign_out:
                        //-------------------------------------------Kimaya-----------------------------------------------
                        ListUtils.collectionList.clear();
                        FirebaseUser user = mAuth.getCurrentUser();

                        if (user != null) {
                            String userEmail = user.getEmail();
                            Toast.makeText(userHome.this, "Goodbye "+userEmail, Toast.LENGTH_SHORT).show();
                            mAuth.getInstance().signOut();
                            FirebaseAuth.getInstance().signOut();
                        }

                        FirebaseAuth.getInstance().signOut();
                        Intent returnLogin = new Intent(userHome.this, Splash.class);
                        startActivity(returnLogin);
                     break;
                    default:
                        return true;
                }
                return true;
            }
        });


       /* userSearch = findViewById(R.id.userSearch);
        userSearch.clearFocus();
        userSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });*/


    }

    /*private void filterList(String newText) {
        // creating a new array list to filter our data.
        ArrayList<Collection> filteredlist = new ArrayList<Collection>();

        // running a for loop to compare elements.
        for (Collection item : ListUtils.collectionList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getCategoryName().toLowerCase().contains(newText.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            adapter.filterList(filteredlist);
        }
    }*/

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
                adapter = new recyclerCollectionAdapter(ListUtils.collectionList, getApplicationContext());
                //adapter = new recyclerCollectionAdapter(ListUtils.collectionList, getApplicationContext(),userHome.this::selectedCollection);
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
                        Intent i = new Intent(userHome.this, CategoryDisplay.class);

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

/*    @Override
    public void selectedCollection(Collection collect) {
        Toast.makeText(this, "Selected collection " + collect.getCategoryName(), Toast.LENGTH_SHORT).show();
        Intent i = new Intent(userHome.this,CategoryDisplay.class);
        i.putExtra("Collection",collect);
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.searchView){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {

        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.searchView);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String searchText = newText;
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }*/
}