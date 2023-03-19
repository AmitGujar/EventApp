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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amitgujar.eventapp.databinding.ActivityAdminLoginBinding;
import com.amitgujar.eventapp.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import es.dmoral.toasty.Toasty;

public class adminLogin extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://eventappfinal-3f1bb-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        final EditText adminuser = findViewById(R.id.adminuser);
        final EditText adminpass = findViewById(R.id.adminpass);
        final TextView signInBtn = findViewById(R.id.signInBtn);
        final ImageView passwordIcon = findViewById(R.id.passwordIcon);
        ProgressBar progressBar = findViewById(R.id.progressBar);


        firebaseAuth = FirebaseAuth.getInstance();

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
                    Toasty.error(adminLogin.this, "Please enter all the details", Toast.LENGTH_SHORT).show();
                }
                else {
                    progressBar.setVisibility(View.VISIBLE);
                    firebaseAuth.signInWithEmailAndPassword(username, password).addOnSuccessListener(new  OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            progressBar.setVisibility(View.GONE);
                            Toasty.success(adminLogin.this, "Login Successful", Toasty.LENGTH_SHORT).show();
                            Intent intent = new Intent(adminLogin.this, listItem.class);
                            startActivity(intent);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toasty.error(adminLogin.this, "Invalid Username or Password", Toasty.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}