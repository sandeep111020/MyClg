package com.example.myclg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myclg.Adapters.MeetsAdapter;
import com.example.myclg.Models.Meets;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MeetScreen extends AppCompatActivity {
    Button Addmeet;
    RecyclerView recyclerView;
    MeetsAdapter adapter3;
    private DatabaseReference databaseRef5;
    TextView pendingmeets;
    String Sdate,Semployeeid;
    SimpleDateFormat dateFormat;
    Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meet_screen);
        Addmeet = findViewById(R.id.addmeet);
        pendingmeets=findViewById(R.id.pendingmeets);
        recyclerView=findViewById(R.id.recycler_manu_meet);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("ddMMyyyy");
        Sdate = dateFormat.format(calendar.getTime());
        Sdate=getIntent().getStringExtra("date");
        Semployeeid=getIntent().getStringExtra("name");
        databaseRef5 = FirebaseDatabase.getInstance().getReference().child("CSE").child("Meets").child(Sdate);
        databaseRef5.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {




                pendingmeets.setText(snapshot.getChildrenCount() + "");


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Addmeet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MeetScreen.this,AddMeetingScreen.class);
                a.putExtra("name",Semployeeid);
                startActivity(a);
            }
        });
        FirebaseRecyclerOptions<Meets> options =
                new FirebaseRecyclerOptions.Builder<Meets>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("CSE").child("Meets").child(Sdate), Meets.class)
                        .build();

        // .child("24052021130648")
        adapter3 = new MeetsAdapter(options,getApplicationContext());
        recyclerView.setAdapter(adapter3);
        onStart();
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter3.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter3.stopListening();
    }

}