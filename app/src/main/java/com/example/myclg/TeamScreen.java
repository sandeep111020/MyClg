package com.example.myclg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myclg.Adapters.TeamAdapter;
import com.example.myclg.Models.profileinfo;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class TeamScreen extends AppCompatActivity {




    TeamAdapter adapter;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_screen);
        recyclerView = findViewById(R.id.idRVItems);
      //  recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));




        FirebaseRecyclerOptions<profileinfo> options =
                new FirebaseRecyclerOptions.Builder<profileinfo>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("CSE").child("Employees"), profileinfo.class)
                        .build();

        adapter = new TeamAdapter(options,getApplicationContext());
        recyclerView.setAdapter(adapter);
        ;




    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}