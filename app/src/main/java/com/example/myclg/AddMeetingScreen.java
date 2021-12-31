package com.example.myclg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.myclg.Models.Meets;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class AddMeetingScreen extends AppCompatActivity {


    private DatabaseReference databaseRef;
    Spinner person1,person2;
    Button submit,btn_time, btn_date;
    CheckBox checkBox;
    String everyone="";
    String timeTonotify;
    String Smeetlink,Smeettitle,Sparticipant1,Sparticipant2,Stime,Sdate;
    EditText meetlink,meettitle;
    ArrayList<String> users = new ArrayList<String>();
    private Calendar calendar;
    private SimpleDateFormat dateFormat;

    String employeeid;
    private String Sdob;
    private String date,time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting_screen);
        meetlink=findViewById(R.id.meetlink);
        meettitle=findViewById(R.id.meettitle);
        btn_time = findViewById(R.id.btn_time);
        btn_date = findViewById(R.id.btn_date);
        person1=findViewById(R.id.participant2);
        person2=findViewById(R.id.participant3);
        submit=findViewById(R.id.submitbutton);
        checkBox=findViewById(R.id.checkBox);
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("ddMMyyyy");

        employeeid = getIntent().getStringExtra("name");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Smeetlink=meetlink.getText().toString();
                Smeettitle=meettitle.getText().toString();
                Sparticipant1=person1.getSelectedItem().toString();
                Sdate=btn_date.getText().toString();
                Stime=btn_time.getText().toString();
                StringBuffer parid1=new StringBuffer();
                for (int i=0; i<Sparticipant1.length(); i++)
                {
                    if (Character.isDigit(Sparticipant1.charAt(i)))
                        parid1.append(Sparticipant1.charAt(i));

                }
                String participantid1= String.valueOf(parid1);
                Sparticipant2=person2.getSelectedItem().toString();
                StringBuffer parid2=new StringBuffer();
                for (int i=0; i<Sparticipant2.length(); i++)
                {
                    if (Character.isDigit(Sparticipant2.charAt(i)))
                        parid2.append(Sparticipant2.charAt(i));

                }
                String participantid2= String.valueOf(parid2);
                if(TextUtils.isEmpty(Smeettitle)){
                    Toast.makeText(AddMeetingScreen.this, "Meeet title is Required", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(Smeetlink)){
                    Toast.makeText(AddMeetingScreen.this,"Please enter Meet link",Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(Sparticipant1)){
                    Toast.makeText(AddMeetingScreen.this,"Please enter Meet Participant",Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(Sparticipant2)){
                    Toast.makeText(AddMeetingScreen.this,"Please enter Meet Participant",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(Stime)){
                    Toast.makeText(AddMeetingScreen.this,"Please pick Meet time",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(Sdate)){
                    Toast.makeText(AddMeetingScreen.this,"Date error",Toast.LENGTH_SHORT).show();
                }else{
                    if(checkBox.isChecked()){
                        everyone="Every Faculty in the department";
                    }
                    FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
                    DatabaseReference reference = rootNode.getReference("CSE").child("Meets");


                    Meets addnewUser = new Meets(Sdate,Stime,Smeettitle,Smeetlink,Sparticipant1,Sparticipant2,employeeid,everyone);
                    reference.child(Sdate).child(Sdate+Stime).setValue(addnewUser);
                    setAlarm(Smeettitle, date, time);
                }

                Toast.makeText(AddMeetingScreen.this,"Submited",Toast.LENGTH_SHORT).show();

            }

        });
        users.add("None");
        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate();

            }
        });
        btn_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTime();
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

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,users);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        person1.setAdapter(aa);
        ArrayAdapter ab = new ArrayAdapter(this,android.R.layout.simple_spinner_item,users);
        ab.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        person2.setAdapter(ab);

    }
    private void selectTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                timeTonotify = i + ":" + i1;
                btn_time.setText(FormatTime(i, i1));
                time=FormatTime(i,i1);
            }
        }, hour, minute, false);
        timePickerDialog.show();

    }


    private void selectDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String day1;
                if(day<=9){
                   day1 ="0"+day;
                }else {
                    day1=""+day;
                }
                date=day + "-" + (month + 1) + "-" + year;
                btn_date.setText(day1+"" + (month + 1)+""+ year+"");
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    public String FormatTime(int hour, int minute) {

        String time;
        time = "";
        String formattedMinute;

        if (minute / 10 == 0) {
            formattedMinute = "0" + minute;
        } else {
            formattedMinute = "" + minute;
        }


        if (hour == 0) {
            time = "12" + ":" + formattedMinute + " AM";
        } else if (hour < 12) {
            time = hour + ":" + formattedMinute + " AM";
        } else if (hour == 12) {
            time = "12" + ":" + formattedMinute + " PM";
        } else {
            int temp = hour - 12;
            time = temp + ":" + formattedMinute + " PM";
        }


        return time;
    }


    private void setAlarm(String text, String date, String time) {
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(getApplicationContext(), AlarmBroadcast.class);
        intent.putExtra("event", text+"  Meeting on "+  date+"at"+time);
        intent.putExtra("time", date);
        intent.putExtra("date", time);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String dateandtime = date + " " + timeTonotify;
        DateFormat formatter = new SimpleDateFormat("d-M-yyyy hh:mm");
        try {
            Date date1 = formatter.parse(dateandtime);
            am.set(AlarmManager.RTC_WAKEUP, date1.getTime(), pendingIntent);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        finish();

    }

}