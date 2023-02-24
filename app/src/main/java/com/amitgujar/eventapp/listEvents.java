package com.amitgujar.eventapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class listEvents extends AppCompatActivity {
//    ActivityReadDatabinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("All Events");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_events);

        final EditText eventname = findViewById(R.id.eventname);
        final EditText venue = findViewById(R.id.venue);
        final EditText date = findViewById(R.id.date);
        final EditText coordinatorname = findViewById(R.id.coordinatorname);


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                eventname.setText(snapshot.child("eventname").getValue().toString());
                venue.setText(snapshot.child("venue").getValue().toString());
                date.setText(snapshot.child("date").getValue().toString());
                coordinatorname.setText(snapshot.child("coordinatorname").getValue().toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}