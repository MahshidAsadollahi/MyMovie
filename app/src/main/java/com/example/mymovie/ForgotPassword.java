package com.example.mymovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    TextInputEditText forgotemail;
    MaterialCardView newpass;

    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        forgotemail=findViewById(R.id.forgotemail);
        newpass=findViewById(R.id.sendnewpassword);

        mAuth=FirebaseAuth.getInstance();

        newpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgottenpass();
            }
        });

    }

    private void forgottenpass() {
        String email=forgotemail.getText().toString().trim();

        if (email.isEmpty()){
            forgotemail.setError("Email is required!");
            forgotemail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            forgotemail.setError("Please provide a valid Email");
            forgotemail.requestFocus();
            return;
        }
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPassword.this,"Check your Email",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ForgotPassword.this,"Error! Try Again",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}