package com.example.mymovie;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseAppLifecycleListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class signUp extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    TextInputEditText signname, signemail, signpassword;
    MaterialCardView slogin;
    TextView alreadyuser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);




        mAuth = FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();

        signname = findViewById(R.id.signupname);
        signemail = findViewById(R.id.signupemail);
        signpassword = findViewById(R.id.signuppassword);


        slogin = findViewById(R.id.sloginButton);






        alreadyuser = findViewById(R.id.alreadyuser);


        alreadyuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(signUp.this, activity_Login.class));

            }
        });

        slogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                registerUser();
            }
        });


    }






    private void registerUser() {
        String name = signname.getText().toString().trim();
        String email = signemail.getText().toString().trim();
        String password = signpassword.getText().toString().trim();
        String profilePicUrl = ""; //initialize to an empty string


        if (name.isEmpty()) {
            signname.setError("Name is required");
            signname.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            signemail.setError("Email is required");
            signemail.requestFocus();
            return;

        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            signemail.setError("Please provide a valid Email!");
            signemail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            signpassword.setError("Password is required");
            signpassword.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            if (firebaseUser != null) {
                                User user = new User(name, email);
                                user.profilePicUrl = profilePicUrl;
                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {

                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                            if (user.isEmailVerified()) {
                                                startActivity(new Intent(signUp.this, MainActivity.class));

                                            } else {
                                                user.sendEmailVerification();
                                                Toast.makeText(signUp.this, "Check Your Email.", Toast.LENGTH_SHORT).show();

                                            }

                                        } else {
                                            Toast.makeText(signUp.this, "Failed to register Try Again!", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });
                            }
                        }
                    }
                });
    }
}


