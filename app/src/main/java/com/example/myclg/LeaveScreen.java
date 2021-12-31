package com.example.myclg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myclg.Models.LeaveUpdate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class LeaveScreen extends AppCompatActivity {
    Button apply,status,approvebtn;
    String employeeid;
    LinearLayout approvel;
    TextView appliedleaves,leavesleft;
    private DatabaseReference databaseRef;
    private DatabaseReference databaseRef3,databaseRef5;
    private SimpleDateFormat dateFormat;

    private String Sdate;
    ArrayList<String> users = new ArrayList<String>();
    TextView leavedetails;
    EditText personid;
    String name,reason;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_screen);
        status=findViewById(R.id.leeavestatus);
        apply=findViewById(R.id.applyleave);
        leavesleft=findViewById(R.id.leavesleft);
        approvel=findViewById(R.id.adminlayoiut);
        leavedetails=findViewById(R.id.leavedetailsunderapprove);
        personid=findViewById(R.id.enterpersonid);
        appliedleaves=findViewById(R.id.appliedleaves);
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("ddMMyyyy");
        Sdate = dateFormat.format(calendar.getTime());
        approvebtn=findViewById(R.id.approve);

        employeeid = getIntent().getStringExtra("name");
        if (employeeid.equals("111")){
            approvel.setVisibility(View.VISIBLE);
        }

        ;
        approvebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Spersonid = personid.getText().toString();
                if (TextUtils.isEmpty(Spersonid)){
                    Toast.makeText(LeaveScreen.this, "Personid is Required", Toast.LENGTH_SHORT).show();
                }
                else {
                    FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
                    DatabaseReference reference = rootNode.getReference("CSE").child("leaves");


                    LeaveUpdate addnewUser = new LeaveUpdate(Spersonid,"Approved");
                    reference.child(Spersonid).child(Sdate).setValue(addnewUser);
                    Toast.makeText(LeaveScreen.this, "Leave is Approved", Toast.LENGTH_SHORT).show();
                }
            }
        });


        databaseRef5 = FirebaseDatabase.getInstance().getReference().child("CSE").child("leaves").child(employeeid);
        databaseRef5.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long left= 10-snapshot.getChildrenCount();


                appliedleaves.setText(snapshot.getChildrenCount() + "");
                leavesleft.setText(left+"");


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseRef = FirebaseDatabase.getInstance().getReference().child("CSE").child("Employees");
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for(DataSnapshot snapshot1 : snapshot.getChildren()) {
                    String tempid= snapshot1.child("userEmpid").getValue(String.class);
                    //users.add(tempid);
                    databaseRef3 = FirebaseDatabase.getInstance().getReference().child("CSE").child("leaves").child(tempid).child(Sdate);
                    databaseRef3.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot5) {



                            if (snapshot5.exists()){



                                name = snapshot5.child("empname").getValue(String.class);
                                reason = snapshot5.child("leavetype").getValue(String.class);
                                users.add(name+"  "+reason);



                                leavedetails.setText(String.valueOf(users));
                                //leavedetails.setText(Sdate);

                            }



                            ;

                            //pendingtasks.setText(snapshot.getChildrenCount() + "");


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    //leavedetails.setText(String.valueOf(users));

                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LeaveScreen.this,ApplyLeave.class);
                i.putExtra("name",employeeid);
                startActivity(i);
            }
        });
        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LeaveScreen.this,LeaveStatus.class);
                i.putExtra("name",employeeid);
                startActivity(i);

            }
        });
    }
}