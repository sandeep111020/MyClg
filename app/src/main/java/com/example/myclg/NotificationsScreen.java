package com.example.myclg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myclg.Adapters.NotificationsAdapter;
import com.example.myclg.Models.notifcations;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class NotificationsScreen extends AppCompatActivity {
    Button sendscrren;
    RecyclerView recyclerView;
    private NotificationsAdapter adapter3;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_screen);
        sendscrren=findViewById(R.id.sendnotifscreen);
        recyclerView=findViewById(R.id.recycler_manu_meet);
        mLinearLayoutManager = new LinearLayoutManager(this);
        //mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<notifcations> options =
                new FirebaseRecyclerOptions.Builder<notifcations>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Notifications"), notifcations.class)
                        .build();

        // .child("24052021130648")
        adapter3 = new NotificationsAdapter(options,getApplicationContext());
        recyclerView.setAdapter(adapter3);
        onStart();
        sendscrren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(NotificationsScreen.this,sendNotification.class);
                startActivity(i);
            }
        });
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