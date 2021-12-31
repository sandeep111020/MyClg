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

import com.example.myclg.Adapters.MyAdpter;
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


public class TaskScreen extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    RecyclerView recyclerView;
    MyAdpter adapter1;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private DatabaseReference databaseRef,databaseRef4,databaseRef5;
    private String Sdate;
    TextView pendingtasks,completedtasks;
    int  size;
    Spinner person;

    ImageView calview;
    EditText dob;
    String Sparticipant1;
    String completed;
    ArrayList<String> users = new ArrayList<String>();


    private Query databaseRef2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_screen);
        recyclerView=findViewById(R.id.recycler_manu);
        person=(Spinner) findViewById(R.id.empidspin);
        pendingtasks=findViewById(R.id.taskspending);
        users.add("None");
        calview=findViewById(R.id.calenderview1);
        dob=findViewById(R.id.dob);
        completedtasks=findViewById(R.id.completedtasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("ddMMyyyy");
        Sdate = dateFormat.format(calendar.getTime());
        Sdate=getIntent().getStringExtra("date");
        newfunction();
        calview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDatePickerDialog();
            }
        });







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
        databaseRef5 = FirebaseDatabase.getInstance().getReference().child("CSE").child("Employees");
        databaseRef5.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snapshot1 : snapshot.getChildren()) {
                    String tempname= snapshot1.child("userName").getValue(String.class);
                    String tempid= snapshot1.child("userEmpid").getValue(String.class);
                    users.add(tempid+" "+tempname);

                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ArrayAdapter abc = new ArrayAdapter(this,android.R.layout.simple_spinner_item,users);
        abc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        person.setAdapter(abc);

        person.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Sparticipant1=person.getSelectedItem().toString();
                StringBuffer parid1=new StringBuffer();
                for (int i=0; i<Sparticipant1.length(); i++)
                {
                    if (Character.isDigit(Sparticipant1.charAt(i)))
                        parid1.append(Sparticipant1.charAt(i));

                }
                String participantid1= String.valueOf(parid1);
                FirebaseRecyclerOptions<User> options =
                        new FirebaseRecyclerOptions.Builder<User>()
                                .setQuery(FirebaseDatabase.getInstance().getReference().child("CSE").child("dailyreport").child(Sdate).child(participantid1), User.class)
                                .build();

                // .child("24052021130648")
                adapter1 = new MyAdpter(options,getApplicationContext());
                recyclerView.setAdapter(adapter1);
                onStart();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        databaseRef = FirebaseDatabase.getInstance().getReference().child("CSE").child("Employees");
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            int i=0,j=0;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for(DataSnapshot snapshot1 : snapshot.getChildren()) {

                    String tempid= snapshot1.child("userEmpid").getValue(String.class);

                    databaseRef4 = FirebaseDatabase.getInstance().getReference().child("CSE").child("dailyreport").child(Sdate).child(tempid);
                    databaseRef4.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for(DataSnapshot snapshot1 : snapshot.getChildren()) {
                                completed = snapshot1.child("status").getValue(String.class);

                                if (completed.equals("Completed and reviewed")){
                                    i++;
                                }
                                else if (completed.equals("pending") || completed.equals("completed")){
                                    j++;
                                }
                            }
                            pendingtasks.setText(""+j);
                            completedtasks.setText(""+i);
                            //pendingtasks.setText(snapshot.getChildrenCount() + "");


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






        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("CSE").child("dailyreport").child(Sdate), User.class)
                        .build();

        // .child("24052021130648")
        adapter1 = new MyAdpter(options,getApplicationContext());
        recyclerView.setAdapter(adapter1);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}