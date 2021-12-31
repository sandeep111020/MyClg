package com.example.myclg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myclg.Models.User;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class TaskUpdateScreen extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView name,mail,date,taskid;
    Button submit,addother;
    EditText tasktype,taskdetails,timetaken,remarks,Name;
    String Sname,Smail,Stasktype,Staskdetails,Stimetaken,Sstatus,Sremarks,Sempid;
    Spinner status;
    Spinner empid;
    private Calendar calendar;
    private SimpleDateFormat dateFormat,simpleDateFormat;
    private String Sdate,dateTime,employeeid;
    String[] stat={"completed","pending"};
    private String Sparticipant1;
    private DatabaseReference databaseRef;
    StringBuffer parid1;
    ArrayList<String> users = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_update_screen);
        name = findViewById(R.id.name);
        mail = findViewById(R.id.email);
        employeeid=getIntent().getStringExtra("name");
        tasktype=findViewById(R.id.tasktype);
        taskdetails=findViewById(R.id.tasks);
        taskid=findViewById(R.id.taskid);
        empid=findViewById(R.id.employeeid);
        users.add("Please select some one");
        status=findViewById(R.id.status);
        remarks=findViewById(R.id.remarks);
        addother=findViewById(R.id.add_another_task);
        submit= findViewById(R.id.submit);
        date = findViewById(R.id.date);
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("ddMMyyyy");
        Sdate = dateFormat.format(calendar.getTime());
        simpleDateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
        dateTime = simpleDateFormat.format(calendar.getTime());
        date.setText("Date : "+Sdate);
        taskid.setText("Taskid is  :"+dateTime);
        status.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,stat);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        status.setAdapter(aa);
        addother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TaskUpdateScreen.this,TaskUpdateScreen.class);
                startActivity(i);
            }
        });
        databaseRef = FirebaseDatabase.getInstance().getReference().child("CSE").child("Employees");
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
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


        ArrayAdapter ab = new ArrayAdapter(this,android.R.layout.simple_spinner_item,users);
        ab.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        empid.setAdapter(ab);
        empid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Your code here
                String yourvalue = empid.getSelectedItem().toString();
                StringBuffer parid2=new StringBuffer();
                for (int k=0; k<yourvalue.length(); k++)
                {
                    if (!Character.isDigit(yourvalue.charAt(k)))
                        parid2.append(yourvalue.charAt(k));

                }
                name.setText(parid2);
                if(yourvalue.equals("Please select some one")){
                    name.setText("Please enter the name");
                }

            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sname= name.getText().toString();
                Smail= mail.getText().toString();
                Stasktype= tasktype.getText().toString();
                Staskdetails= taskdetails.getText().toString();
                Sparticipant1=empid.getSelectedItem().toString();
                parid1=new StringBuffer();
                for (int i=0; i<Sparticipant1.length(); i++)
                {
                    if (Character.isDigit(Sparticipant1.charAt(i)))
                        parid1.append(Sparticipant1.charAt(i));

                }
                Sname=name.getText().toString();
                Sstatus= status.getSelectedItem().toString().trim();
                Sremarks= remarks.getText().toString();
                storedatainDb();
            }
        });


    }

    private void storedatainDb() {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("CSE").child("dailyreport");


        User addnewUser = new User(parid1.toString(),Sname,Sdate,Smail,Stasktype,Staskdetails,"pending",Sremarks,dateTime);
        reference.child(Sdate).child(parid1.toString()).child(dateTime).setValue(addnewUser);
        Toast.makeText(TaskUpdateScreen.this, "Submission Completed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(TaskUpdateScreen.this,HomeActivity.class);
        i.putExtra("name",employeeid);
        startActivity(i);
    }
}