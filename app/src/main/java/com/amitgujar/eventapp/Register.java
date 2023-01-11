package com.amitgujar.eventapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.amitgujar.eventapp.R;
import com.amitgujar.eventapp.databinding.ActivityMainBinding;
import com.amitgujar.eventapp.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import es.dmoral.toasty.Toasty;

public class Register extends AppCompatActivity {

    ActivityRegisterBinding binding;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final EditText passwordET = findViewById(R.id.passwordET);
        final ImageView passwordIcon = findViewById(R.id.passwordIcon);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        progressBar=new ProgressBar(this);


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

        binding.signUpBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String username = binding.usernameET.getText().toString();
                String password = binding.passwordET.getText().toString();
                String phonenumber = binding.phonenumber.getText().toString();
                String fullname = binding.fullname.getText().toString();

                progressBar.setVisibility(View.VISIBLE);


                if (username.isEmpty() || password.isEmpty() || phonenumber.isEmpty() || fullname.isEmpty()) {
                    Toasty.error(Register.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    firebaseAuth.createUserWithEmailAndPassword(username, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            progressBar.setVisibility(View.GONE);

                            firebaseFirestore.collection("User").document(FirebaseAuth.getInstance().getUid()).set(new UserModel(fullname,username,phonenumber));

                            Toasty.success(Register.this, "Registration Successful", Toasty.LENGTH_SHORT).show();
                            Intent intent = new Intent(Register.this, Login.class);
                            startActivity(intent);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            progressBar.setVisibility(View.GONE);
                            Toasty.error(Register.this, e.getMessage(), Toasty.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}