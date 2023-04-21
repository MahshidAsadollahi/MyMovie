package com.example.mymovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class activity_Login extends AppCompatActivity {

    TextInputEditText emailt,passwordt;
    MaterialCardView loginb;
    TextView newusertext,forgotpass;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailt=findViewById(R.id.loginemail);
        passwordt=findViewById(R.id.passwordlogin);
        loginb=findViewById(R.id.loginButton);
        newusertext=findViewById(R.id.newuser);
        forgotpass=findViewById(R.id.forgotpassword);

        mAuth=FirebaseAuth.getInstance();

        newusertext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity_Login.this,signUp.class));

            }
        });

        loginb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();

            }
        });
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity_Login.this,ForgotPassword.class));
            }
        });
    }

    private void userLogin() {
        String email=emailt.getText().toString().trim();
        String password=passwordt.getText().toString().trim();

        if(email.isEmpty()){
            emailt.setError("Email is required!");
            emailt.requestFocus();
            return;

        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailt.setError("Please provide a valid Email!");
            emailt.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            passwordt.setError("Password is required");
            passwordt.requestFocus();
            return;
        }
        if (password.length()<6) {
            passwordt.setError("Password Characters Must be More than 6 Letters!");
            passwordt.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();

                    if(user.isEmailVerified()){
                        startActivity(new Intent(activity_Login.this,MainActivity.class));

                    }else{
                        user.sendEmailVerification();
                        Toast.makeText(activity_Login.this,"Check Your Email.",Toast.LENGTH_SHORT).show();

                    }
                }else{
                    Toast.makeText(activity_Login.this, "Failed to Login Try Again!", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}