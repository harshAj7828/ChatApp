package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.makeramen.roundedimageview.RoundedImageView;

public class SignUpActivity extends AppCompatActivity {
    FirebaseAuth auth;
    RoundedImageView imageView;
    EditText name,email,password,mobileNo;
    MaterialButton signUp;
    FirebaseDatabase database;
    FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        auth = FirebaseAuth.getInstance();
        imageView = findViewById(R.id.imageProfile);
        name = findViewById(R.id.id_name_su);
        email = findViewById(R.id.id_email_su);
        password = findViewById(R.id.id_pass_su);
        mobileNo = findViewById(R.id.id_number);
        signUp = findViewById(R.id.btnSignUp_su);

        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");

        if(!MemoryData.getData(this).isEmpty()){
            Intent intent =new Intent(SignUpActivity.this, MainActivity.class);
            intent.putExtra("name",MemoryData.getName(this));
            intent.putExtra("email",MemoryData.getEmail(this));
            intent.putExtra("mobile",MemoryData.getData(this));
            intent.putExtra("password",MemoryData.getPassword(this));
            startActivity(intent);


        }


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), 10);
            }
        });
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReferenceFromUrl("https://chatapp-9a77f-default-rtdb.firebaseio.com/");

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                String Email = email.getText().toString();
                String Password = password.getText().toString();
                String MobileNo = mobileNo.getText().toString();
                String Name = name.getText().toString();

                if(Email.isEmpty() ||MobileNo.isEmpty() || Name.isEmpty() ||Password.isEmpty() ){
                    Toast.makeText(SignUpActivity.this, "All Field Required !", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
                else {
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            progressDialog.dismiss();
                            if(snapshot.child("users").hasChild(MobileNo)){
                                Toast.makeText(SignUpActivity.this, "Mobile already exists", Toast.LENGTH_SHORT).show();
                            }else {
                                databaseReference.child("users").child(MobileNo).child("email").setValue(Email);
                                databaseReference.child("users").child(MobileNo).child("name").setValue(Name);
                                databaseReference.child("users").child(MobileNo).child("password").setValue(Password);
                                databaseReference.child("users").child(MobileNo).child("profile_pic").setValue("");
                                MemoryData.saveData(MobileNo,SignUpActivity.this);
                                MemoryData.saveName(Name,SignUpActivity.this);

                                Toast.makeText(SignUpActivity.this, "Success", Toast.LENGTH_SHORT).show();

                                Intent intent =new Intent(SignUpActivity.this, MainActivity.class);
                                intent.putExtra("name",Name);
                                intent.putExtra("email",Email);
                                intent.putExtra("mobile",MobileNo);
                                intent.putExtra("password",Password);
                                startActivity(intent);
                                finish();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            progressDialog.dismiss();
                            Toast.makeText(SignUpActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                        }
                    });

                }

            }
        });

    }
}