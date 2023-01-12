package com.amitgujar.eventapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amitgujar.eventapp.R;
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

public class Login extends AppCompatActivity {
    ActivityLoginBinding binding;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        final TextView signUpBtn = findViewById(R.id.signUpBtn);
        final TextView adminBtn = findViewById(R.id.adminBtn);
        final EditText passwordET = findViewById(R.id.passwordET);
        final ImageView passwordIcon = findViewById(R.id.passwordIcon);

        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = new ProgressBar(this);


        passwordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordET.getTransformationMethod() == null) {
                    passwordET.setTransformationMethod(new android.text.method.PasswordTransformationMethod());
                    Drawable drawable = AppCompatResources.getDrawable(Login.this, R.drawable.password_hide);
                    passwordIcon.setImageDrawable(drawable);
                }
                else {
                    passwordET.setTransformationMethod(null);
                    Drawable drawable = AppCompatResources.getDrawable(Login.this, R.drawable.password_show);
                    passwordIcon.setImageDrawable(drawable);
                }
            }
        });


        binding.signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = binding.usernameET.getText().toString();
                String password = binding.passwordET.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Toasty.error(Login.this, "Please enter all the details", Toast.LENGTH_SHORT).show();
                }
                else {
                    progressBar.setVisibility(View.VISIBLE);
                    firebaseAuth.signInWithEmailAndPassword(username, password).addOnSuccessListener(new  OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            progressBar.setVisibility(View.GONE);
                            Toasty.success(Login.this, "Login Successful", Toasty.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toasty.error(Login.this, "Invalid Username or Password", Toasty.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });


        adminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, adminLogin.class));
            }
        });
    }
}