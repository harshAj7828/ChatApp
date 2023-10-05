package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import com.example.chatapp.Adapter.MessageAdapter;
import com.example.chatapp.HelperClass.MessageHelperClass;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String Name,Mobile,Password,Email;
    RecyclerView messageRecyclerView;
    RoundedImageView profilePic;
    List<MessageHelperClass> messageHelperClassList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReferenceFromUrl("https://chatapp-9a77f-default-rtdb.firebaseio.com/");



        messageRecyclerView = findViewById(R.id.recyclerView);

        profilePic = findViewById(R.id.userProfile);

        Name = getIntent().getStringExtra("name");
        Mobile = getIntent().getStringExtra("mobile");
        Password = getIntent().getStringExtra("password");
        Email = getIntent().getStringExtra("email");

        messageRecyclerView.setHasFixedSize(true);
        messageRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Context context;
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String profileUrl = snapshot.child("users").child(Mobile)
                            .child("profile_pic").getValue(String.class);

                    if(!profileUrl.isEmpty()){

                        Picasso.get().load(profileUrl).into(profilePic);
                    }
                    progressDialog.dismiss();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();

            }
        });

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageHelperClassList.clear();

                for (DataSnapshot dataSnapshot: snapshot.child("users").getChildren()){
                    final  String getMobile = dataSnapshot.getKey();

                    if(!getMobile.equals(Mobile)){
                        final String getName = dataSnapshot.child("name").getValue(String.class);
                        final String getProfilePic = dataSnapshot.child("profile_pic")
                                .getValue(String.class);
                        final String getEmail = dataSnapshot.child("email").getValue(String.class);
                        MessageHelperClass messageHelperClass = new MessageHelperClass(getName,getMobile,
                                "",getEmail,getProfilePic,"",0);
                        messageHelperClassList.add(messageHelperClass);


                    }
                }
                messageRecyclerView.setAdapter(new MessageAdapter(messageHelperClassList,MainActivity.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}