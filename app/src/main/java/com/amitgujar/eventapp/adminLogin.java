package com.amitgujar.eventapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import es.dmoral.toasty.Toasty;

public class adminLogin extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://eventapp-fb89f-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        final EditText adminuser = findViewById(R.id.adminuser);
        final EditText adminpass = findViewById(R.id.adminpass);
        final TextView signInBtn = findViewById(R.id.signInBtn);
        final ImageView passwordIcon = findViewById(R.id.passwordIcon);

        passwordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adminpass.getTransformationMethod() == null) {
                    adminpass.setTransformationMethod(new android.text.method.PasswordTransformationMethod());
                    Drawable drawable = AppCompatResources.getDrawable(adminLogin.this, R.drawable.password_hide);
                    passwordIcon.setImageDrawable(drawable);
                }
                else {
                    adminpass.setTransformationMethod(null);
                    Drawable drawable = AppCompatResources.getDrawable(adminLogin.this, R.drawable.password_show);
                    passwordIcon.setImageDrawable(drawable);
                }
            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = adminuser.getText().toString();
                String password = adminpass.getText().toString();
                if (username.isEmpty() || password.isEmpty()) {
                    Toasty.info(adminLogin.this, "Please enter all the details", Toasty.LENGTH_SHORT).show();
                }
                else {
                    databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(username)) {
                                if (snapshot.child("admin").child("Password").getValue().toString().equals(password)) {
                                    Toasty.success(adminLogin.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(adminLogin.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    Toasty.error(adminLogin.this, "You do not have permission", Toast.LENGTH_SHORT, true).show();
                                }
                            }
                            else {
                                Toasty.error(adminLogin.this, "User does not exist", Toast.LENGTH_SHORT, true).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toasty.error(adminLogin.this, "Database Error" + error.getMessage(), Toast.LENGTH_SHORT, true).show();
                        }
                    });
                }
            }
        });
    }
}