package com.varsitycollege.imbizoappwil02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    EditText edt_name,edt_email,edt_password;
    Button btn_Register;
    private FirebaseAuth mAuth;
    String name, email,password;
    TextView txt_LoginMessage;


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

        edt_name = findViewById(R.id.edt_name);
        edt_email = findViewById(R.id.edt_email);
        edt_password = findViewById(R.id.edt_password);
        btn_Register = findViewById(R.id.btn_register);
        mAuth = FirebaseAuth.getInstance();
        txt_LoginMessage=findViewById(R.id.txt_existingAccountMessage);

        txt_LoginMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent redirectToLogin = new Intent(Register.this,Login.class);
                startActivity(redirectToLogin);
            }
        });


        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=edt_name.getText().toString();
                email=edt_email.getText().toString();
                password=edt_password.getText().toString();
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!email.isEmpty() && !password.isEmpty())
                                {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Register.this, "Welcome "+ name +" !", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(Register.this,Categories.class);
                                        startActivity(i);
                                    }else{
                                        Toast.makeText(Register.this, "Failed to login!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Register.this,"Failed to login!",Toast.LENGTH_SHORT).show();
                                }
                            });

            }
        });


    }
}