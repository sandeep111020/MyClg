package com.example.myclg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.myclg.Adapters.AttendanceAdapter;
import com.example.myclg.Adapters.MyAdpter;
import com.example.myclg.Models.Attendance;
import com.example.myclg.Models.User;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ViewAttendenceScreen extends AppCompatActivity implements DatePickerDialog.OnDateSetListener  {
    RecyclerView recyclerView;
    AttendanceAdapter adapter1;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private DatabaseReference databaseRef,databaseRef4,databaseRef5;
    private String Sdate;

    int  size;


    ImageView calview;
    EditText dob;
    String Sparticipant1;
    String completed;



    private Query databaseRef2;
    private LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendence_screen);
        recyclerView=findViewById(R.id.recycler_manu);

        calview=findViewById(R.id.calenderview1);
        dob=findViewById(R.id.dob);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("ddMMyyyy");
        lottieAnimationView=findViewById(R.id.lav_actionBar);
        Sdate = dateFormat.format(calendar.getTime());
        Sdate=getIntent().getStringExtra("date");
        databaseRef5 = FirebaseDatabase.getInstance().getReference().child("CSE").child("Attendance").child(Sdate);
        databaseRef5.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {



                if (snapshot.getChildrenCount() == 0) {

                    //FinalValue finval= new FinalValue("0");
                    //databaseRef5.child("Final").setValue(finval);
                    lottieAnimationView.setVisibility(View.VISIBLE);
                    lottieAnimationView.playAnimation();
                }else{
                }
                // totalprice = snapshot.child("Final").child("finalprice").getValue(String.class);
                // place.setText(totalprice);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        calview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDatePickerDialog();
            }
        });
        newfunction();







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

    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog =new DatePickerDialog(this,this,Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        String[] monthName={"January","February","March", "April", "May", "June", "July",
                "August", "September", "October", "November",
                "December"};
        String Smonth= monthName[month];
        month=month+1;
        String day,newmonth;
        if(Integer.valueOf(dayOfMonth)<10){
            day="0"+dayOfMonth;
        }else {
            day= String.valueOf(dayOfMonth);
        }
        if(Integer.valueOf(month)<10){
            newmonth="0"+month;
        }else {
            newmonth= String.valueOf(month);
        }
        String date = day+"/"+Smonth+"/"+year;
        // dob.setText(date);
        Sdate= day+""+newmonth+""+year;
        dob.setText(Sdate);
        newfunction();
    }

    private void newfunction() {
        databaseRef5 = FirebaseDatabase.getInstance().getReference().child("CSE").child("Attendance").child(Sdate);
        databaseRef5.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {



                if (snapshot.getChildrenCount() == 0) {

                    //FinalValue finval= new FinalValue("0");
                    //databaseRef5.child("Final").setValue(finval);
                    lottieAnimationView.setVisibility(View.VISIBLE);
                    lottieAnimationView.playAnimation();
                }else{
                }
                // totalprice = snapshot.child("Final").child("finalprice").getValue(String.class);
                // place.setText(totalprice);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseRecyclerOptions<Attendance> options =
                new FirebaseRecyclerOptions.Builder<Attendance>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("CSE").child("Attendance").child(Sdate), Attendance.class)
                        .build();

        // .child("24052021130648")
        adapter1 = new AttendanceAdapter(options,getApplicationContext());
        recyclerView.setAdapter(adapter1);
        onStart();







      /*  FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("CSE").child("dailyreport").child(Sdate), User.class)
                        .build();

        // .child("24052021130648")
        adapter1 = new MyAdpter(options,getApplicationContext());
        recyclerView.setAdapter(adapter1);*/
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}