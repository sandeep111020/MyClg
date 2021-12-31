package com.example.myclg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myclg.Models.Leave;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ApplyLeave extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    Spinner leavetype;
    Button apply;
    String name;
    EditText comment;
    String date,Sleavetype,Scomment,employeeid;
    String[] arr = { "Personal Leave", "Official Leave","Health Issue",};
    CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_leave);
        employeeid = getIntent().getStringExtra("name");
        calendarView=findViewById(R.id.calender);
        apply=findViewById(R.id.leeavestatus);
        comment=findViewById(R.id.comment);

        Query checkuser = FirebaseDatabase.getInstance().getReference("CSE").child("Employees").child(employeeid);
        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                name = snapshot.child("userName").getValue(String.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {


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
                date= String.valueOf(day+""+newmonth+""+year);

            }
        });

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validatemeetdetails();

            }
        });
        leavetype = (Spinner)
                findViewById(R.id.autoCompleteTextView1);

        leavetype.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,arr);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        leavetype.setAdapter(aa);
    }

    private void validatemeetdetails() {
        Sleavetype=leavetype.getSelectedItem().toString();
        Scomment=comment.getText().toString();
        if(TextUtils.isEmpty(date)){
            Toast.makeText(this,"Please pick date ",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Sleavetype)){
            Toast.makeText(this,"Please enter the leave type ",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Scomment)){
            Toast.makeText(this,"Please enter the comment ",Toast.LENGTH_SHORT).show();
        }
        else{
            FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
            DatabaseReference reference = rootNode.getReference("CSE").child("leaves");


            Leave addnewUser = new Leave(date,employeeid,name,Scomment,Sleavetype);
            reference.child(employeeid).child(date).setValue(addnewUser);
            Intent i =new Intent(ApplyLeave.this,LeaveStatus.class);
            i.putExtra("name",employeeid);
            startActivity(i);
        }

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}