package com.example.chatapp.Chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chatapp.Adapter.ChatAdapter;
import com.example.chatapp.HelperClass.ChatHelperClass;
import com.example.chatapp.MemoryData;

import com.example.chatapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class ChatActivity extends AppCompatActivity {
    ImageView backBtn,sendBtn;
    EditText messageET;
    TextView UserName,Activity;
    RoundedImageView userProfile;
    String chatKey;
    String getUserMobile = "";
    RecyclerView recyclerView;
    ChatAdapter chatAdapter;
    boolean loadingFirstTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        backBtn = findViewById(R.id.backBtn);
        sendBtn = findViewById(R.id.sendBtn);
        messageET = findViewById(R.id.id_messageType);
        UserName = findViewById(R.id.userName);
        userProfile = findViewById(R.id.profilePic);
        Activity = findViewById(R.id.id_activity);
        recyclerView = findViewById(R.id.chatRecyclerView);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReferenceFromUrl("https://chatapp-9a77f-default-rtdb.firebaseio.com/");

        List<ChatHelperClass> chatLists = new ArrayList<>();


        String getName = getIntent().getStringExtra("name");
        String getProfile = getIntent().getStringExtra("profile_pic");
        chatKey = getIntent().getStringExtra("chat_key");
        String getMobile = getIntent().getStringExtra("mobile");
        getUserMobile = MemoryData.getData(ChatActivity.this);


        UserName.setText(getName);
        Picasso.get().load(getProfile).into(userProfile);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
        chatAdapter = new ChatAdapter(chatLists,ChatActivity.this);
        recyclerView.setAdapter(chatAdapter);



            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(chatKey.isEmpty()) {
                        chatKey = "1";
                        if (snapshot.hasChild("chat")) {
                            chatKey = String.valueOf((snapshot.child("chat").getChildrenCount() + 1));
                        }
                    }
                    if(snapshot.child("chat").child(chatKey).hasChild("message")){
                        chatLists.clear();
                        for (DataSnapshot messageSnapshot : snapshot.child("chat").child(chatKey).child("message").getChildren()){

                            if(messageSnapshot.hasChild("msg")&&messageSnapshot.hasChild("mobile")){
                                String messageTimeStamp =  messageSnapshot.getKey();
                                String getMobile = messageSnapshot.child("mobile").getValue(String.class);
                                String getMsg = messageSnapshot.child("mobile").getValue(String.class);

                                Timestamp timestamp = new Timestamp(Long.parseLong(messageTimeStamp));
                                Date date = new Date(timestamp.getTime());
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                                SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("hh:mm aa", Locale.getDefault());


                                ChatHelperClass chatList = new ChatHelperClass(getMobile,getName,getMsg,
                                        simpleDateFormat.format(date),simpleTimeFormat.format(date));
                                chatLists.add(chatList);

                                if(loadingFirstTime || Long.parseLong(messageTimeStamp)>Long.
                                        parseLong(MemoryData.getLastMsg(ChatActivity.this,chatKey))){
                                    MemoryData.saveLastMsg(messageTimeStamp,chatKey,ChatActivity.this);

                                    loadingFirstTime = false;

                                    chatAdapter.updateChatList(chatLists);
                                    recyclerView.scrollToPosition(chatLists.size()-1);


                                }
                            }

                        }
                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getMessage = messageET.getText().toString();
                String currentTimeStamp = String.valueOf(System.currentTimeMillis()).substring(0,10);


                databaseReference.child("chat").child(chatKey).child("user_1").setValue(getUserMobile);
                databaseReference.child("chat").child(chatKey).child("user_2").setValue(getMobile);
                databaseReference.child("chat").child(chatKey).child("message")
                        .child(currentTimeStamp).child("msg").setValue(getMessage);
                databaseReference.child("chat").child(chatKey).child("message")
                        .child(currentTimeStamp).child("mobile").setValue(getUserMobile);

                messageET.setText("");

            }
        });



        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }
}