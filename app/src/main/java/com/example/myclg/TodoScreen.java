package com.example.myclg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.myclg.Adapters.TodoAdapter;
import com.example.myclg.Models.User;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TodoScreen extends AppCompatActivity {
    RecyclerView recyclerView;
    TodoAdapter adapter1;
    String employeeid;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String Sdate;
    TextView text;
    LottieAnimationView lottieAnimationView;
    private DatabaseReference databaseRef5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_screen);
        recyclerView=findViewById(R.id.recycler_menu);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        calendar = Calendar.getInstance();
        text= findViewById(R.id.test);
        employeeid = getIntent().getStringExtra("name");
        dateFormat = new SimpleDateFormat("ddMMyyyy");
        Sdate = dateFormat.format(calendar.getTime());
        lottieAnimationView=findViewById(R.id.lav_actionBar);

        databaseRef5 = FirebaseDatabase.getInstance().getReference().child("CSE").child("dailyreport").child(Sdate);
        databaseRef5.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {



                if (snapshot.child(employeeid).getChildrenCount() == 0) {

                    //FinalValue finval= new FinalValue("0");
                    //databaseRef5.child("Final").setValue(finval);
                    lottieAnimationView.setVisibility(View.VISIBLE);
                    lottieAnimationView.playAnimation();
                    text.setText("No tasks on today");
                }else{
                }
                // totalprice = snapshot.child("Final").child("finalprice").getValue(String.class);
                // place.setText(totalprice);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("CSE").child("dailyreport").child(Sdate).child(employeeid), User.class)
                        .build();

        // .child("24052021130648")
        adapter1 = new TodoAdapter(options,getApplicationContext());
        recyclerView.setAdapter(adapter1);
        ;
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter1.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter1.stopListening();
    }
}