package com.example.myclg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myclg.Adapters.AttendanceAdapter;
import com.example.myclg.Adapters.TodoAdapter;
import com.example.myclg.Models.Attendance;
import com.example.myclg.Models.User;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TakeAttendanceScreen extends AppCompatActivity {
    RecyclerView recyclerView;
    AttendanceAdapter adapter1;
    String employeeid;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String Sdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance_screen);
        recyclerView=findViewById(R.id.recycler_menu);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        calendar = Calendar.getInstance();
        employeeid = getIntent().getStringExtra("name");
        dateFormat = new SimpleDateFormat("ddMMyyyy");
        Sdate = dateFormat.format(calendar.getTime());

        FirebaseRecyclerOptions<Attendance> options =
                new FirebaseRecyclerOptions.Builder<Attendance>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("CSE").child("Attendance").child(Sdate), Attendance.class)
                        .build();

        // .child("24052021130648")
        adapter1 = new AttendanceAdapter(options,getApplicationContext());
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