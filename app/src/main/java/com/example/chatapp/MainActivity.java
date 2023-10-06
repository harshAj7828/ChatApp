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
    int unseenMessage = 0;
    String lastMessage = "";
    String chatKey = "";
    MessageAdapter messageAdapter;
    boolean dataSet= false;

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
        messageAdapter = new MessageAdapter(messageHelperClassList,MainActivity.this);
        messageRecyclerView.setAdapter(messageAdapter);


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
                unseenMessage = 0;
                lastMessage = "";

                for (DataSnapshot dataSnapshot: snapshot.child("users").getChildren()){

                    final String getMobile = dataSnapshot.getKey();
                    dataSet = false;

                    if(!getMobile.equals(Mobile)){
                        final String getName = dataSnapshot.child("name").getValue(String.class);
                        final String getProfilePic = dataSnapshot.child("profile_pic")
                                .getValue(String.class);
                        final String getEmail = dataSnapshot.child("email").getValue(String.class);


                        databaseReference.child("chat").addListenerForSingleValueEvent
                                (new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int getChatCount = (int)snapshot.getChildrenCount();
                                if(getChatCount>0){
                                    for (DataSnapshot dataSnapshot1: snapshot.getChildren()){
                                        String getKey = dataSnapshot1.getKey();

                                        chatKey = getKey;
                                        if(dataSnapshot1.hasChild("user_1")&& dataSnapshot1.hasChild("user_2")&&dataSnapshot1.hasChild("message")){
                                            String getUserOne = dataSnapshot1.child("user_1")
                                                    .getValue(String.class);
                                            String getUserTwo = dataSnapshot1.child("user_2")
                                                    .getValue(String.class);
                                            if((getUserOne.equals(getMobile) && getUserTwo.equals(Mobile))||
                                                    (getUserOne.equals(Mobile) && getUserTwo.equals(getMobile))){
                                                for (DataSnapshot chatsnapshot: dataSnapshot1
                                                        .child("message")
                                                        .getChildren()){
                                                    long getMessagekey = Long.parseLong(chatsnapshot.getKey());
                                                    long getLastSeenMessage = Long.parseLong(MemoryData.
                                                            getLastMsg(MainActivity.this,getKey));
                                                    lastMessage = chatsnapshot.child("msg").getValue(String.class);

                                                    if(getMessagekey>getLastSeenMessage){
                                                        unseenMessage++;

                                                    }
                                                }

                                            }

                                        }

                                    }
                                }
                                if(!dataSet){
                                    dataSet = true;

                                    MessageHelperClass messageHelperClass = new MessageHelperClass(getName,getMobile,
                                            lastMessage,getEmail,getProfilePic,"",unseenMessage,chatKey);
                                    messageHelperClassList.add(messageHelperClass);
                                    messageAdapter.updateData(messageHelperClassList);
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}