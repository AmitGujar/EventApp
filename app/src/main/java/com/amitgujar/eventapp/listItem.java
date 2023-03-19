package com.amitgujar.eventapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class listItem extends AppCompatActivity {
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://eventappfinal-3f1bb-default-rtdb.firebaseio.com/");

    private TextView eventname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);
        eventname = findViewById(R.id.eventname);
        getdata();
    }

    private void getdata() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    String value = snapshot.getValue(String.class);
                    HashMap<String, Object> map = (HashMap<String, java.lang.Object>) dataSnapshot.getValue();
                    eventname.setText(map.toString());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(listItem.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}