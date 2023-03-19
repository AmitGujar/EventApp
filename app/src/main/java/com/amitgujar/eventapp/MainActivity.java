package com.amitgujar.eventapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.amitgujar.eventapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {
DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://eventappfinal-3f1bb-default-rtdb.firebaseio.com/");
    EditText date;
    int year;
    int month;
    int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText eventname = findViewById(R.id.eventname);
        final EditText venue = findViewById(R.id.venue);
        final EditText coordinatorname = findViewById(R.id.coordinatorname);
        final AppCompatButton submit = findViewById(R.id.signUpBtn);

        date = findViewById(R.id.date);
        Calendar calendar = Calendar.getInstance();
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eventname1 = eventname.getText().toString();
                String venue1 = venue.getText().toString();
                String coordinatorname1 = coordinatorname.getText().toString();
                String date1 = date.getText().toString();

                databaseReference.child("All Events").child(eventname1).child("Venue").setValue(venue1);
                databaseReference.child("All Events").child(eventname1).child("Coordinator").setValue(coordinatorname1);
                databaseReference.child("All Events").child(eventname1).child("Date").setValue(date1);
                Toasty.success(MainActivity.this, "Event Added Successfully", Toasty.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, listItem.class);
                startActivity(intent);
                finish();
            }
        });

    }
}