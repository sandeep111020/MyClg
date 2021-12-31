package com.example.myclg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myclg.Models.notifcations;
import com.example.myclg.NotificationsPackage.FcmNotificationsSender;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class sendNotification extends AppCompatActivity {
    EditText title,message;
    Button sendbtn;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String currenttime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notification);
        title=findViewById(R.id.notificationtitle);
        message=findViewById(R.id.notificationmessage);
        sendbtn=findViewById(R.id.sendnotificationmsg);

        FirebaseMessaging.getInstance().subscribeToTopic("all");

        calendar= Calendar.getInstance();
        simpleDateFormat=new SimpleDateFormat("hh:mm a");

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!title.getText().toString().isEmpty() && !message.getText().toString().isEmpty()){

                    FcmNotificationsSender notificationsSender=new FcmNotificationsSender("/topics/all",title.getText().toString(),
                            message.getText().toString(),getApplicationContext(),sendNotification.this);

                    notificationsSender.SendNotifications();

                    FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
                    DatabaseReference reference = rootNode.getReference("Notifications");


                    Date date=new Date();
                    currenttime=simpleDateFormat.format(calendar.getTime());
                    notifcations addnewUser = new notifcations(title.getText().toString(),message.getText().toString(),currenttime);
                    reference.child(String.valueOf(date.getTime())).setValue(addnewUser);
                }
                else {
                    Toast.makeText(sendNotification.this,"Enter Details",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}