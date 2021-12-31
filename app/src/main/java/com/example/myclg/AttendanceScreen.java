package com.example.myclg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myclg.Models.Attendance;
import com.example.myclg.Models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AttendanceScreen extends AppCompatActivity {
    Button Take,View;
    private DatabaseReference databaseRef5;
    private Calendar calendar;
    TextView t[];
    String empid;
    private SimpleDateFormat dateFormat,simpleDateFormat;
    String Sdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_screen);
        Take=findViewById(R.id.takeattendance);
        View=findViewById(R.id.viewattendence);
        empid=getIntent().getStringExtra("name");
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("CSE").child("Attendance");
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("ddMMyyyy");
        Sdate = dateFormat.format(calendar.getTime());
        View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent i = new Intent(AttendanceScreen.this,ViewAttendenceScreen.class);
                i.putExtra("date",Sdate);
                startActivity(i);
            }
        });
        LinearLayout root = findViewById(R.id.rlMain);
        if(empid.equals("111")){
            Take.setVisibility(View.VISIBLE);
        }
        t=new TextView[100];
        LinearLayout.LayoutParams dim=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for(int i=0;i<10;i++)
        {

        }
        Query checkuser = FirebaseDatabase.getInstance().getReference("CSE").child("Attendance");
        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                if(snapshot.exists()){



                    int i=0;
                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                        t[i]=new TextView(AttendanceScreen.this);
                        t[i].setLayoutParams(dim);
                       // t[i].setText("YOHOHO: "+i);
                        root.addView(t[i]);
                        String id = snapshot.child(postSnapshot.getKey()).child(empid).child("id").getValue(String.class);
                        String attendance = snapshot.child(postSnapshot.getKey()).child(empid).child("status").getValue(String.class);
                        if(attendance!=null){
                            if(attendance.equals("true")){
                                attendance="Present";
                            }else if(attendance.equals("false")){
                                attendance="Absent";
                            }
                            t[i].setText(postSnapshot.getKey()+"        Attendance Status: "+attendance);
                        }


                        // t[7].setText(snapshot.getRef().getParent().getKey());
                        // t[6].setText(postSnapshot.toString());

                        //  t[8].setText(snapshot.getRef().getParent().getKey());
                        i++;
                    }



                }
                else{
                    t[6].setText("There is no Account with this id");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });



        Take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query checkuser = FirebaseDatabase.getInstance().getReference("CSE").child("Attendance").child(Sdate);
                checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                        if(snapshot.exists()){




                            Intent i = new Intent(AttendanceScreen.this,TakeAttendanceScreen.class);
                            startActivity(i);


                        }
                        else{
                            databaseRef5 = FirebaseDatabase.getInstance().getReference().child("CSE").child("Employees");
                            databaseRef5.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    for(DataSnapshot snapshot1 : snapshot.getChildren()) {
                                        String tempname= snapshot1.child("userName").getValue(String.class);
                                        String tempid= snapshot1.child("userEmpid").getValue(String.class);
                                        Attendance addnew= new Attendance(tempid,tempname,"false");
                                        reference.child(Sdate).child(tempid).setValue(addnew);

                                    }
                                    Intent i = new Intent(AttendanceScreen.this,TakeAttendanceScreen.class);
                                    startActivity(i);




                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });

            }
        });
    }
}