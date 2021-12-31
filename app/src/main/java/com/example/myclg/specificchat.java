package com.example.myclg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myclg.Adapters.MeetsAdapter;
import com.example.myclg.Adapters.MessagesAdapter;
import com.example.myclg.Models.Meets;
import com.example.myclg.Models.Messages;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class specificchat extends AppCompatActivity {

    EditText mgetmessage;
    ImageButton msendmessagebutton;
    Spinner person1;
    CardView msendmessagecardview;
    String empid;
    androidx.appcompat.widget.Toolbar mtoolbarofspecificchat;
    ImageView mimageviewofspecificuser;
    TextView mnameofspecificuser;
    ArrayList<String> users = new ArrayList<String>();

    private String enteredmessage;
    Intent intent;
    private FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;

    ImageButton mbackbuttonofspecificchat;

    RecyclerView mmessagerecyclerview;

    String currenttime;
    String sname;
    String Recievertoken;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;

    MessagesAdapter messagesAdapter;
    ArrayList<Messages> messagesArrayList;
    private MessagesAdapter adapter3;
    private DatabaseReference databaseRef;
    private String Sparticipant1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specificchat);

        mgetmessage=findViewById(R.id.getmessage);
        msendmessagecardview=findViewById(R.id.carviewofsendmessage);
        msendmessagebutton=findViewById(R.id.imageviewsendmessage);
        mtoolbarofspecificchat=findViewById(R.id.toolbarofspecificchat);
        person1=findViewById(R.id.participant2);
        mnameofspecificuser=findViewById(R.id.Nameofspecificuser);
        mimageviewofspecificuser=findViewById(R.id.specificuserimageinimageview);
        mbackbuttonofspecificchat=findViewById(R.id.backbuttonofspecificchat);

        messagesArrayList=new ArrayList<>();
        empid=getIntent().getStringExtra("name");
        mmessagerecyclerview=findViewById(R.id.recyclerviewofspecific);
        users.add("ALL");
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        mmessagerecyclerview.setLayoutManager(linearLayoutManager);
        /*messagesAdapter=new MessagesAdapter(specificchat.this,messagesArrayList);
        mmessagerecyclerview.setAdapter(messagesAdapter);*/
        FirebaseRecyclerOptions<Messages> options =
                new FirebaseRecyclerOptions.Builder<Messages>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("chats").child("111").child("messages"), Messages.class)
                        .build();

        // .child("24052021130648")
        adapter3 = new MessagesAdapter(options,getApplicationContext(),empid);
        mmessagerecyclerview.setAdapter(adapter3);




        intent=getIntent();

        setSupportActionBar(mtoolbarofspecificchat);
        mtoolbarofspecificchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Toolbar is Clicked",Toast.LENGTH_SHORT).show();


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

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        calendar=Calendar.getInstance();
        simpleDateFormat=new SimpleDateFormat("hh:mm a");
        if (!empid.equals("111")){
            person1.setVisibility(View.GONE);
        }





/*
        DatabaseReference databaseReference=firebaseDatabase.getReference().child("chats").child("111").child("messages");
        messagesAdapter=new MessagesAdapter(specificchat.this,messagesArrayList);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagesArrayList.clear();
                for(DataSnapshot snapshot1:snapshot.getChildren())
                {
                    Messages messages=snapshot1.getValue(Messages.class);
                    messagesArrayList.add(messages);
                }
                messagesAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(),messagesArrayList.toString()+"hi",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/




        mbackbuttonofspecificchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




        msendmessagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                enteredmessage=mgetmessage.getText().toString();
                Sparticipant1=person1.getSelectedItem().toString();


                StringBuffer parid1=new StringBuffer();
                if (Sparticipant1.equals("ALL")){
                    parid1.append("0");
                }else{
                    for (int i=0; i<Sparticipant1.length(); i++)
                    {
                        if (Character.isDigit(Sparticipant1.charAt(i)))
                            parid1.append(Sparticipant1.charAt(i));

                    }
                }

                if(enteredmessage.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter message first",Toast.LENGTH_SHORT).show();
                }

                else

                {
                    Date date=new Date();
                    currenttime=simpleDateFormat.format(calendar.getTime());
                    Messages messages=new Messages(enteredmessage,empid,date.getTime(),currenttime,parid1.toString());
                    firebaseDatabase=FirebaseDatabase.getInstance();
                    firebaseDatabase.getReference().child("chats")
                            .child("111")
                            .child("messages")
                            .push().setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            /*firebaseDatabase.getReference()
                                    .child("chats")
                                    .child("111")
                                    .child("messages")
                                    .push()
                                    .setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                }
                            });*/
                        }
                    });

                    mgetmessage.setText(null);

                   /* FcmNotificationsSender notificationsSender=new FcmNotificationsSender(Recievertoken,sname,
                            enteredmessage,getApplicationContext(),specificchat.this);

                    notificationsSender.SendNotifications();*/




                }




            }
        });




    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter3.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter3.stopListening();
    }

   /* @Override
    public void onStart() {
        super.onStart();
        messagesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(messagesAdapter!=null)
        {
            messagesAdapter.notifyDataSetChanged();
        }
    }*/



}