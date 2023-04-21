package com.example.mymovie;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;


public class profileActivity extends AppCompatActivity {

    ImageView imageView6;
    TextView usernametxt;
    TextView logoutbutton;
    AppCompatButton profilePicButton;

    FirebaseUser user;
    DatabaseReference reference;
    String userid;
    String profilePicUrl;
    StorageReference storageReference;

    private  static final  int PICK_IMAGE=1;
    private ActivityResultLauncher<Intent>launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        storageReference=FirebaseStorage.getInstance().getReference().child("profile_pics");

        //barai bottom navigation
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_profile);

        bottomNavigationView.setOnItemSelectedListener(item->{
            switch (item.getItemId()){
                case R.id.bottom_search:
                    startActivity((new Intent(getApplicationContext(),searchActivity.class)));
                    overridePendingTransition(R.anim.slide_in_night,R.anim.slide_cut_left);
                    finish();
                    return true;
                case R.id.bottom_home:
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    overridePendingTransition(R.anim.slide_in_night,R.anim.slide_cut_left);
                    finish();
                    return true;
                case R.id.bottom_profile:

                    return true;

            }
            return false;
        });



        logoutbutton=findViewById(R.id.logoutbutton);
        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(profileActivity.this,signUp.class));

            }
        });


        launcher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),result ->{

            if (result.getResultCode()==RESULT_OK){
                Intent data=result.getData();
                if(data !=null){
                    Uri imageUri=data.getData();
                    StorageReference fileRef=storageReference.child(userid +".jpg");
                    //upload the image to Firebase storage
                    fileRef.putFile(imageUri)
                            .addOnSuccessListener(taskSnapshot -> {
                                // retrieve the download url of the uploaded image
                                fileRef.getDownloadUrl().addOnSuccessListener(uri-> {
                                    // update the user's profile picture url in the database
                                    reference.child(userid).child("profilePicUrl").setValue(uri.toString())
                                            .addOnSuccessListener(aVoid ->{
                                                // update the profile picture in the ImageView
                                                Picasso.get().load(uri).into(imageView6);
                                                Toast.makeText(profileActivity.this, "Profile picture updated", Toast.LENGTH_SHORT).show();

                                            })
                                            .addOnFailureListener(e->{
                                                Toast.makeText(profileActivity.this, "Failed to update profile picture", Toast.LENGTH_SHORT).show();
                                            });
                                });
                            })
                            .addOnFailureListener(e->{
                                Toast.makeText(profileActivity.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                            });
                }
            }
                });

        profilePicButton=findViewById(R.id.profilePicButton);

        //when button for changinge profile gets pressed
        profilePicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open gallery to select image
                Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                launcher.launch(intent);



            }
        });




        imageView6=findViewById(R.id.imageView6);
        user= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users");
        userid=user.getUid();
        storageReference= FirebaseStorage.getInstance().getReference();




        final TextView nametxt=(TextView) findViewById(R.id.usernametxt);
        final TextView emailtxt=(TextView) findViewById(R.id.emailtxt);

        // // load user information from Firebase Realtime Database

        reference.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userp = snapshot.getValue(User.class);
                if (userp != null) {
                    String name = userp.name;
                    String email = userp.email;
                    profilePicUrl = userp.profilePicUrl;

                    nametxt.setText("Welcome," + " " + name);
                    emailtxt.setText(email);

                    ImageView profileImageView = findViewById(R.id.imageView6);
                    Picasso.get().load(profilePicUrl).into(profileImageView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(profileActivity.this,"Error!",Toast.LENGTH_SHORT).show();


            }
        });


    }
}