package com.varsitycollege.imbizoappwil02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.ArrayList;

public class Login extends AppCompatActivity {

    EditText edt_email,edt_password;
    Button btn_Login,btn_googleLogin;
    private FirebaseAuth mAuth;

    private String type;

    public static final String DEFUALT_IMAGE = "https://firebasestorage.googleapis.com/v0/b/vinyl-warehouse.appspot.com/o/default%2Ficon.png?alt=media&token=6495156e-9304-4be2-b2aa-06110afe1bee";
    public static final String SERVER_CLIENT_ID = "589837323767-h1qb0bjrlqkvphphlbad08kg2k8lamee.apps.googleusercontent.com";
    private SignInClient oneTapClient;

    private static final int REQ_ONE_TAP = 2;
    private BeginSignInRequest signInRequest;
    public static ArrayList<User> usersList = new ArrayList<>();

    TextView txt_registerMessage;

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
        setContentView(R.layout.activity_login);

        //Code to prevent dark mode on users phone
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        txt_registerMessage=findViewById(R.id.txt_registerMessage);
        edt_email = findViewById(R.id.edt_loginEmail);
        edt_password = findViewById(R.id.edt_loginPassword);
        btn_Login = findViewById(R.id.btn_login);
        btn_googleLogin = findViewById(R.id.btn_google);

        mAuth = FirebaseAuth.getInstance();

        Intent i = getIntent();
        type = i.getStringExtra("TypeUser");

        if (type.equals("Admin")){
            btn_googleLogin.setVisibility(View.INVISIBLE);
        }
        
        txt_registerMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent redirectToRegister = new Intent(Login.this,Register.class);
                redirectToRegister.putExtra("TypeUser" , type);
                startActivity(redirectToRegister);
            }
        });

        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAuth = FirebaseAuth.getInstance();

                String password=edt_password.getText().toString();
                String email=edt_email.getText().toString();

                if (!email.isEmpty() && !password.isEmpty())
                {
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(Login.this,"Welcome "+ mAuth.getCurrentUser().getEmail(),Toast.LENGTH_SHORT).show();

                                if (type.equals("User")){
                                    Intent k = new Intent(Login.this,userHome.class);
                                    //Intent k = new Intent(Login.this,userHome.class);
                                    startActivity(k);
                                }

                                if (type.equals("Admin")){
                                    Intent j = new Intent(Login.this,adminHome.class);
                                    startActivity(j);
                                }
                            }else
                            {
                                Toast.makeText(Login.this,"Login Failed!"+ mAuth.getCurrentUser().getEmail(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Login.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });


        //get client
        oneTapClient = Identity.getSignInClient(this);

        signInRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(SERVER_CLIENT_ID)
                        // Only show accounts previously used to sign in.
                        .setFilterByAuthorizedAccounts(false)
                        .build())
                .build();

        btn_googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    private void signIn() {

        oneTapClient.beginSignIn(signInRequest).addOnSuccessListener(this, new OnSuccessListener<BeginSignInResult>() {
            @Override
            public void onSuccess(BeginSignInResult beginSignInResult) {

                try {
                    startIntentSenderForResult(beginSignInResult.getPendingIntent().getIntentSender(), REQ_ONE_TAP,
                            null, 0, 0, 0);
                } catch (IntentSender.SendIntentException e) {
                    Log.e("Google", "Couldn't start One Tap UI: " + e.getLocalizedMessage());
                }
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // No saved credentials found. Launch the One Tap sign-up flow, or
                // do nothing and continue presenting the signed-out UI.
                Log.d("Google", "sigInw" + e.getLocalizedMessage());
            }
        });

    }

   /* @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);}

    private void updateUI(FirebaseUser currentUser) {
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            String googleCredential = oneTapClient.getSignInCredentialFromIntent(data).getGoogleIdToken();
            AuthCredential credential = GoogleAuthProvider.getCredential(googleCredential, null);
            //here we are checking the Authentication Credential and checking the task is successful or not and display the message
            //based on that.
            mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        //Toast.makeText(Login.this, "successful", Toast.LENGTH_LONG).show();
                        //get signed in user
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        //update details and go to main activity
                        UpdateUI(firebaseUser);
                    } else {
                        Toast.makeText(Login.this, "Failed!", Toast.LENGTH_LONG).show();
                    }
                }
            });

        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
       /* //Get login user
        FirebaseUser currentUser = mAuth.getCurrentUser();

        //if null then no user logged in
        //else login the user and go to main activity
        if (currentUser != null) {
            UpdateUI(currentUser);
        }*/
    }

    private void UpdateUI(FirebaseUser firebaseUser) {

        // TODO: 2022/04/30 Convert to id base not google https://medium.com/@juliomacr/10-firebase-realtime-database-rule-templates-d4894a118a98

        if (firebaseUser != null) {
            //userID
            String id = firebaseUser.getUid();
            //Username
            String personName = firebaseUser.getDisplayName();
            //Email address
            String personEmail = firebaseUser.getEmail();
            //profile picture
            Uri personPhoto = firebaseUser.getPhotoUrl();
            //check if user has profile picture
            if (personPhoto == null) {
                personPhoto = Uri.parse(DEFUALT_IMAGE);
            }
            //Update user details
            User user = new User(personName, personEmail, id);
            //Add user to arraylist
            usersList.add(user);
            //Store user basic details in runtime
            CurrentUser.user = user;

            Toast.makeText(this, "Welcome " + user.getUserName(), Toast.LENGTH_SHORT).show();
            //Go to main activity
            Intent i = new Intent(Login.this, Categories.class);
            startActivity(i);
            finish();
        }
    }
}