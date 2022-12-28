package com.amitgujar.eventapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatButton;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.amitgujar.eventapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import es.dmoral.toasty.Toasty;

public class Register extends AppCompatActivity {
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://eventapp-fb89f-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText nameET = findViewById(R.id.nameET);
        final EditText usernameET = findViewById(R.id.usernameET);
        final EditText passwordET = findViewById(R.id.passwordET);
        final AppCompatButton signUpBtn = findViewById(R.id.signUpBtn);
        final ImageView passwordIcon = findViewById(R.id.passwordIcon);

        passwordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordET.getTransformationMethod() == null) {
                    passwordET.setTransformationMethod(new android.text.method.PasswordTransformationMethod());
                    Drawable drawable = AppCompatResources.getDrawable(Register.this, R.drawable.password_hide);
                    passwordIcon.setImageDrawable(drawable);
                }
                else {
                    passwordET.setTransformationMethod(null);
                    Drawable drawable = AppCompatResources.getDrawable(Register.this, R.drawable.password_show);
                    passwordIcon.setImageDrawable(drawable);
                }
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameET.getText().toString();
                String username = usernameET.getText().toString();
                String password = passwordET.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Toasty.info(Register.this, "Please enter all the details", Toast.LENGTH_SHORT).show();
                }

                else {
                    databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(username)) {
                                Toasty.info(Register.this, "Username already exists", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                databaseReference.child("Users").child(username).child("Full Name").setValue(name);
                                databaseReference.child("Users").child(username).child("Password").setValue(password);
                                Toasty.success(Register.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toasty.error(Register.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}