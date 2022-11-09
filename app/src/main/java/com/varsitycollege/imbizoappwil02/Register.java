package com.varsitycollege.imbizoappwil02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText edt_name,edt_email,edt_password;
    Button btn_Register;
    private FirebaseAuth mAuth;
    String name, email,password,admin,userId;
    TextView txt_LoginMessage;
    Spinner spinner;
    boolean isAdmin;
    //Firebase realtime database reference
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

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
        setContentView(R.layout.activity_register);


        Intent i = getIntent();
        type = i.getStringExtra("TypeUser");

        edt_name = findViewById(R.id.edt_name);
        edt_email = findViewById(R.id.edt_email);
        edt_password = findViewById(R.id.edt_password);
        btn_Register = findViewById(R.id.btn_register);
        mAuth = FirebaseAuth.getInstance();
        txt_LoginMessage=findViewById(R.id.txt_existingAccountMessage);
        spinner = findViewById(R.id.spn_Admin);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //create a simple layout resource file for each spinner component
        spinner.setAdapter(adapter);

        //Apply OnItemSelectedListener to the Spinner instance to determine which item of the spinner is clicked.
        spinner.setOnItemSelectedListener(this);

        txt_LoginMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent redirectToLogin = new Intent(Register.this,Login.class);
                redirectToLogin.putExtra("TypeUser" , type);
                startActivity(redirectToLogin);
            }
        });


        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userId = KeyGenerator.getRandomString(8);
                name=edt_name.getText().toString();
                email=edt_email.getText().toString();
                password=edt_password.getText().toString();

                if (admin.equals("Yes")){
                    isAdmin = true;
                }

                if (admin.equals("No")){
                    isAdmin = false;
                }

                Users user = new Users(userId,name,email,password,isAdmin);
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!email.isEmpty() && !password.isEmpty())
                                {
                                    if (task.isSuccessful()) {
                                        myRef.child("Users").child(mAuth.getCurrentUser().getUid()).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(Register.this, "Welcome "+ name +" !", Toast.LENGTH_SHORT).show();

                                                if (isAdmin == false){
                                                    Intent i = new Intent(Register.this,Categories.class);
                                                    startActivity(i);
                                                }

                                                if (isAdmin == true){
                                                    Intent i = new Intent(Register.this,adminHome.class);
                                                    startActivity(i);
                                                }

                                            }
                                        });
                                    }else{
                                        Toast.makeText(Register.this, "Failed to Register!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Register.this,"Failed to Register!",Toast.LENGTH_SHORT).show();
                                }
                            });

            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        try {
            admin = String.valueOf(adapterView.getItemAtPosition(i));

            if (admin.equals("Yes")) {
                isAdmin = true;
            }else{
                isAdmin = false;
            }
        }catch (Exception e){

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        isAdmin = false;
        admin = "No";
    }
}