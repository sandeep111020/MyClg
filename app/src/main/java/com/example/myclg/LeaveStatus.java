package com.example.myclg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class LeaveStatus extends AppCompatActivity {
    String employeeid;
    TextView status;
    private SimpleDateFormat dateFormat;
    private Calendar calendar;
    String Sdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_status);
        status=findViewById(R.id.textstatus);
        employeeid = getIntent().getStringExtra("name");
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("ddMMyyyy");
        Sdate = dateFormat.format(calendar.getTime());
        Query checkuser = FirebaseDatabase.getInstance().getReference("CSE").child("leaves").child(employeeid).child(Sdate);
        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String text = snapshot.child("update").getValue(String.class);
                    if (snapshot.child("update").exists()){

                        if (text.equals("Approved")) {
                            status.setText("Your leave is approved");
                        }
                    }
                    else {
                        status.setText("Your leave is in under Review");
                    }

                }
                else {
                    status.setText("Please apply leave on today's date");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });
    }
}