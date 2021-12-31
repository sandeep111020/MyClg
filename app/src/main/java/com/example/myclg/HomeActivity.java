 package com.example.myclg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myclg.Adapters.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity {
    TextView todaytask,todaymeet,yesterdaytask,yesterdaymeet,toplogo_layout1,mainText_layout1,completed_value_layout1,completed_layout1,pending_value_layout1,pending_layout1,percent_layout1,toplogo_layout2,mainText_layout2,completed_value_layout2,completed_layout2,pending_value_layout2,pending_layout2;
    LinearLayout layout1,layout2,layout3,layout4;
    TextView toplogo_layout3,mainText_layout3,completed_value_layout3,completed_layout3,pending_value_layout3,pending_layout3,toplogo_layout4,mainText_layout4,completed_value_layout4,completed_layout4,pending_value_layout4,pending_layout4,percent_layout4;
    private ViewPager2 viewPager2;
    Timer timer;
    TextView popupbutton;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private DatabaseReference databaseRef,databaseref2,databaseRef3,databaseRef6;
    private String Sdate;
    ProgressBar progressBar1,progressBar2;

    String completed,yesterdayDate;
    int i=0,j=0,k=0,l=0;
    FloatingActionButton floatingActionButton;
    private String employeeid,clientid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView navView = findViewById(R.id.nav_view);



        progressBar1=findViewById(R.id.simpleProgressBar);
        progressBar2=findViewById(R.id.simpleProgressBar2);
        todaytask=findViewById(R.id.clicktodaytask);
        todaymeet=findViewById(R.id.clicktodaymeet);
        yesterdaytask = findViewById(R.id.clickyesterdaytask);
        yesterdaymeet=findViewById(R.id.clickyesterdaymeet);
        layout1=findViewById(R.id.layout1);
        layout2=findViewById(R.id.layout2);
        layout3 =findViewById(R.id.layout3);
        layout4=findViewById(R.id.layout4);
        viewPager2 = findViewById(R.id.viewpager2);
        floatingActionButton =findViewById(R.id.fab_show_rating_dialog);
        toplogo_layout1 = findViewById(R.id.top_logo_layout1);
        mainText_layout1 = findViewById(R.id.maintext_layout1);
        completed_value_layout1  = findViewById(R.id.completed_value_layout1);
        completed_layout1 = findViewById(R.id.completed_layout1);
        pending_value_layout1 = findViewById(R.id.pending_value_layout1);
        pending_layout1 = findViewById(R.id.pending_layout1);
        percent_layout1 = findViewById(R.id.perc_layout1);
        toplogo_layout2 = findViewById(R.id.top_logo_layout2);
        mainText_layout2 = findViewById(R.id.maintext_layout2);
        completed_value_layout2  = findViewById(R.id.completed_value_layout2);
        completed_layout2 = findViewById(R.id.completed_layout2);
        pending_value_layout2 = findViewById(R.id.pending_value_layout2);
        pending_layout2 = findViewById(R.id.pending_layout2);
        toplogo_layout3 = findViewById(R.id.top_logo_layout3);
        mainText_layout3 = findViewById(R.id.maintext_layout3);
        completed_value_layout3  = findViewById(R.id.completed_value_layout3);
        completed_layout3 = findViewById(R.id.completed_layout3);
        pending_value_layout3 = findViewById(R.id.pending_value_layout3);
        pending_layout3 = findViewById(R.id.pending_layout3);

        toplogo_layout4 = findViewById(R.id.top_logo_layout4);
        mainText_layout4 = findViewById(R.id.maintext_layout4);
        completed_value_layout4  = findViewById(R.id.completed_value_layout4);
        completed_layout4 = findViewById(R.id.completed_layout4);
        pending_value_layout4 = findViewById(R.id.pending_value_layout4);
        pending_layout4 = findViewById(R.id.pending_layout4);
        percent_layout4 = findViewById(R.id.perc_layout4);
        popupbutton= findViewById(R.id.popupbutton);
        employeeid = getIntent().getStringExtra("name");
        clientid=getIntent().getStringExtra("Usernumber");
        if(!employeeid.equals("111")){
            floatingActionButton.setVisibility(View.GONE );
        }


        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("ddMMyyyy");
        Sdate = dateFormat.format(calendar.getTime());


        databaseRef6 = FirebaseDatabase.getInstance().getReference().child("CSE").child("Meets").child(Sdate);
        databaseRef6.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {




                pending_value_layout2.setText(snapshot.getChildrenCount() + "");


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
                    databaseRef3 = FirebaseDatabase.getInstance().getReference().child("CSE").child("dailyreport").child(Sdate).child(tempid);
                    databaseRef3.addListenerForSingleValueEvent(new ValueEventListener() {
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
                            pending_value_layout1.setText(""+j);
                            completed_value_layout1.setText(""+i);

                            int ttotal=i+j;
                            int tval= i*100;
                            if (tval==0){
                                progressBar1.setProgress(tval);
                                percent_layout1.setText(""+tval+"%");
                            } else {
                                tval=tval/ttotal;
                                progressBar1.setProgress(tval);
                                percent_layout1.setText(""+tval+"%");
                            }

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






        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        dateFormat = new SimpleDateFormat("ddMMyyyy");
        yesterdayDate=dateFormat.format(cal.getTime());
        databaseRef6 = FirebaseDatabase.getInstance().getReference().child("CSE").child("Meets").child(yesterdayDate);
        databaseRef6.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {




                pending_value_layout3.setText(snapshot.getChildrenCount() + "");


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

                    databaseref2 = FirebaseDatabase.getInstance().getReference().child("CSE").child("dailyreport").child(yesterdayDate).child(tempid);
                    databaseref2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for(DataSnapshot snapshot1 : snapshot.getChildren()) {
                                completed = snapshot1.child("status").getValue(String.class);
                                if (completed.equals("Completed and reviewed")){
                                    l++;
                                }
                                else if (completed.equals("pending") || completed.equals("completed")){
                                    k++;
                                }
                            }
                            pending_value_layout4.setText(""+k);
                            completed_value_layout4.setText(""+l);
                            int ytotal=k+l;
                            int yval= l*100;
                            if (yval==0){
                                progressBar2.setProgress(yval);
                                percent_layout4.setText(""+yval+"%");
                            }else {
                                yval=yval/ytotal;
                                progressBar2.setProgress(yval);
                                percent_layout4.setText(""+yval+"%");
                            }




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


        popupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showpopup();
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this,TaskUpdateScreen.class);
                i.putExtra("name",employeeid);
                startActivity(i);
            }
        });

        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
       /* viewPager2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Intent.ACTION_VIEW, Uri.parse("http://prabisha.com/"));
                startActivity(intent);
            }
        });*/

        viewPager2.setAdapter(new ViewPagerAdapter(this, list, viewPager2));
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                viewPager2.post(new Runnable(){

                    @Override
                    public void run() {
                        viewPager2.setCurrentItem((viewPager2.getCurrentItem()+1)%list.size());
                    }
                });
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 1000, 2000);





        layout1.setOnTouchListener(new LinearLayout.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:

                        Drawable buttonDrawable = layout1.getBackground();
                        buttonDrawable = DrawableCompat.wrap(buttonDrawable);
                        //the color is a direct color int and not a color resource
                        DrawableCompat.setTint(buttonDrawable, Color.RED);
                        layout1.setBackground(buttonDrawable);
                        Drawable buttonDrawable2 = toplogo_layout1.getBackground();
                        buttonDrawable2 = DrawableCompat.wrap(buttonDrawable2);
                        //the color is a direct color int and not a color resource
                        DrawableCompat.setTint(buttonDrawable2, Color.WHITE);
                        toplogo_layout1.setBackground(buttonDrawable2);
                        Drawable buttonDrawable3 = todaytask.getBackground();
                        buttonDrawable3 = DrawableCompat.wrap(buttonDrawable3);
                        //the color is a direct color int and not a color resource
                        DrawableCompat.setTint(buttonDrawable3, Color.WHITE);
                        todaytask.setBackground(buttonDrawable3);
                        pending_layout1.setTextColor(Color.WHITE);
                        todaytask.setTextColor(Color.WHITE);
                        percent_layout1.setTextColor(Color.WHITE);

                        mainText_layout1.setTextColor(Color.WHITE);
                        completed_value_layout1.setTextColor(Color.WHITE);
                        completed_layout1.setTextColor(Color.WHITE);
                        pending_value_layout1.setTextColor(Color.WHITE);
                        break;
                    case MotionEvent.ACTION_UP:


                        Drawable buttonDrawableback1 = layout1.getBackground();
                        buttonDrawableback1 = DrawableCompat.wrap(buttonDrawableback1);
                        //the color is a direct color int and not a color resource
                        DrawableCompat.setTint(buttonDrawableback1, Color.argb(100,92,89,255));
                        layout1.setBackground(buttonDrawableback1);
                        Drawable buttonDrawableback2 = toplogo_layout1.getBackground();
                        buttonDrawableback2 = DrawableCompat.wrap(buttonDrawableback2);
                        //the color is a direct color int and not a color resource
                        DrawableCompat.setTint(buttonDrawableback2, Color.BLACK);
                        toplogo_layout1.setBackground(buttonDrawableback2);
                        Drawable buttonDrawableback3 = todaytask.getBackground();
                        buttonDrawableback3 = DrawableCompat.wrap(buttonDrawableback3);
                        //the color is a direct color int and not a color resource
                        DrawableCompat.setTint(buttonDrawableback3, Color.BLACK);
                        todaytask.setBackground(buttonDrawableback3);
                        pending_layout1.setTextColor(Color.BLACK);
                        todaytask.setTextColor(Color.BLACK);
                        percent_layout1.setTextColor(Color.BLACK);

                        mainText_layout1.setTextColor(Color.BLACK);
                        completed_value_layout1.setTextColor(Color.BLACK);
                        completed_layout1.setTextColor(Color.BLACK);
                        pending_value_layout1.setTextColor(Color.BLACK);

                        break;
                }

                return true;
            }


        });
        layout2.setOnTouchListener(new LinearLayout.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:

                        Drawable buttonDrawable21 = layout2.getBackground();
                        buttonDrawable21 = DrawableCompat.wrap(buttonDrawable21);
                        //the color is a direct color int and not a color resource
                        DrawableCompat.setTint(buttonDrawable21, Color.RED);
                        layout2.setBackground(buttonDrawable21);
                        Drawable buttonDrawable22 = toplogo_layout2.getBackground();
                        buttonDrawable22 = DrawableCompat.wrap(buttonDrawable22);
                        //the color is a direct color int and not a color resource
                        DrawableCompat.setTint(buttonDrawable22, Color.WHITE);
                        toplogo_layout2.setBackground(buttonDrawable22);
                        Drawable buttonDrawable23 = todaymeet.getBackground();
                        buttonDrawable23 = DrawableCompat.wrap(buttonDrawable23);
                        //the color is a direct color int and not a color resource
                        DrawableCompat.setTint(buttonDrawable23, Color.WHITE);
                        todaymeet.setBackground(buttonDrawable23);
                        pending_layout2.setTextColor(Color.WHITE);

                        mainText_layout2.setTextColor(Color.WHITE);
                        completed_value_layout2.setTextColor(Color.WHITE);
                        completed_layout2.setTextColor(Color.WHITE);
                        pending_value_layout2.setTextColor(Color.WHITE);
                        break;
                    case MotionEvent.ACTION_UP:


                        Drawable buttonDrawableback21 = layout2.getBackground();
                        buttonDrawableback21 = DrawableCompat.wrap(buttonDrawableback21);
                        //the color is a direct color int and not a color resource
                        DrawableCompat.setTint(buttonDrawableback21, Color.argb(100,92,179,255));
                        layout2.setBackground(buttonDrawableback21);
                        Drawable buttonDrawableback22 = toplogo_layout2.getBackground();
                        buttonDrawableback22 = DrawableCompat.wrap(buttonDrawableback22);
                        //the color is a direct color int and not a color resource
                        DrawableCompat.setTint(buttonDrawableback22, Color.BLACK);
                        toplogo_layout2.setBackground(buttonDrawableback22);
                        Drawable buttonDrawableback23 = todaymeet.getBackground();
                        buttonDrawableback23 = DrawableCompat.wrap(buttonDrawableback23);
                        //the color is a direct color int and not a color resource
                        DrawableCompat.setTint(buttonDrawableback23, Color.BLACK);
                        todaymeet.setBackground(buttonDrawableback23);
                        pending_layout2.setTextColor(Color.BLACK);


                        mainText_layout2.setTextColor(Color.BLACK);
                        completed_value_layout2.setTextColor(Color.BLACK);
                        completed_layout2.setTextColor(Color.BLACK);
                        pending_value_layout2.setTextColor(Color.BLACK);

                        break;
                }

                return true;
            }


        });
        layout3.setOnTouchListener(new LinearLayout.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:

                        Drawable buttonDrawable = layout3.getBackground();
                        buttonDrawable = DrawableCompat.wrap(buttonDrawable);
                        //the color is a direct color int and not a color resource
                        DrawableCompat.setTint(buttonDrawable, Color.RED);
                        layout3.setBackground(buttonDrawable);
                        Drawable buttonDrawable2 = toplogo_layout3.getBackground();
                        buttonDrawable2 = DrawableCompat.wrap(buttonDrawable2);
                        //the color is a direct color int and not a color resource
                        DrawableCompat.setTint(buttonDrawable2, Color.WHITE);
                        toplogo_layout3.setBackground(buttonDrawable2);
                        Drawable buttonDrawable3 = yesterdaymeet.getBackground();
                        buttonDrawable3 = DrawableCompat.wrap(buttonDrawable3);
                        //the color is a direct color int and not a color resource
                        DrawableCompat.setTint(buttonDrawable3, Color.WHITE);
                        yesterdaymeet.setBackground(buttonDrawable3);
                        pending_layout3.setTextColor(Color.WHITE);


                        mainText_layout3.setTextColor(Color.WHITE);
                        completed_value_layout3.setTextColor(Color.WHITE);
                        completed_layout3.setTextColor(Color.WHITE);
                        pending_value_layout3.setTextColor(Color.WHITE);
                        break;
                    case MotionEvent.ACTION_UP:


                        Drawable buttonDrawableback1 = layout3.getBackground();
                        buttonDrawableback1 = DrawableCompat.wrap(buttonDrawableback1);
                        //the color is a direct color int and not a color resource
                        DrawableCompat.setTint(buttonDrawableback1, Color.argb(100,92,219,255));
                        layout3.setBackground(buttonDrawableback1);
                        Drawable buttonDrawableback2 = toplogo_layout3.getBackground();
                        buttonDrawableback2 = DrawableCompat.wrap(buttonDrawableback2);
                        //the color is a direct color int and not a color resource
                        DrawableCompat.setTint(buttonDrawableback2, Color.BLACK);
                        toplogo_layout3.setBackground(buttonDrawableback2);
                        Drawable buttonDrawableback3 = yesterdaymeet.getBackground();
                        buttonDrawableback3 = DrawableCompat.wrap(buttonDrawableback3);
                        //the color is a direct color int and not a color resource
                        DrawableCompat.setTint(buttonDrawableback3, Color.BLACK);
                        yesterdaymeet.setBackground(buttonDrawableback3);
                        pending_layout3.setTextColor(Color.BLACK);


                        mainText_layout3.setTextColor(Color.BLACK);
                        completed_value_layout3.setTextColor(Color.BLACK);
                        completed_layout3.setTextColor(Color.BLACK);
                        pending_value_layout3.setTextColor(Color.BLACK);

                        break;
                }

                return true;
            }


        });
        layout4.setOnTouchListener(new LinearLayout.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:

                        Drawable buttonDrawable = layout4.getBackground();
                        buttonDrawable = DrawableCompat.wrap(buttonDrawable);
                        //the color is a direct color int and not a color resource
                        DrawableCompat.setTint(buttonDrawable, Color.RED);
                        layout4.setBackground(buttonDrawable);
                        Drawable buttonDrawable2 = toplogo_layout4.getBackground();
                        buttonDrawable2 = DrawableCompat.wrap(buttonDrawable2);
                        //the color is a direct color int and not a color resource
                        DrawableCompat.setTint(buttonDrawable2, Color.WHITE);
                        toplogo_layout4.setBackground(buttonDrawable2);
                        Drawable buttonDrawable3 = yesterdaytask.getBackground();
                        buttonDrawable3 = DrawableCompat.wrap(buttonDrawable3);
                        //the color is a direct color int and not a color resource
                        DrawableCompat.setTint(buttonDrawable3, Color.WHITE);
                        yesterdaytask.setBackground(buttonDrawable3);
                        pending_layout4.setTextColor(Color.WHITE);

                        percent_layout4.setTextColor(Color.WHITE);

                        mainText_layout4.setTextColor(Color.WHITE);
                        completed_value_layout4.setTextColor(Color.WHITE);
                        completed_layout4.setTextColor(Color.WHITE);
                        pending_value_layout4.setTextColor(Color.WHITE);
                        break;
                    case MotionEvent.ACTION_UP:


                        Drawable buttonDrawableback1 = layout4.getBackground();
                        buttonDrawableback1 = DrawableCompat.wrap(buttonDrawableback1);
                        //the color is a direct color int and not a color resource
                        DrawableCompat.setTint(buttonDrawableback1, Color.argb(100,92,89,255));
                        layout4.setBackground(buttonDrawableback1);
                        Drawable buttonDrawableback2 = toplogo_layout4.getBackground();
                        buttonDrawableback2 = DrawableCompat.wrap(buttonDrawableback2);
                        //the color is a direct color int and not a color resource
                        DrawableCompat.setTint(buttonDrawableback2, Color.BLACK);
                        toplogo_layout4.setBackground(buttonDrawableback2);
                        Drawable buttonDrawableback3 = yesterdaytask.getBackground();
                        buttonDrawableback3 = DrawableCompat.wrap(buttonDrawableback3);
                        //the color is a direct color int and not a color resource
                        DrawableCompat.setTint(buttonDrawableback3, Color.BLACK);
                        yesterdaytask.setBackground(buttonDrawableback3);
                        pending_layout4.setTextColor(Color.BLACK);

                        percent_layout4.setTextColor(Color.BLACK);

                        mainText_layout4.setTextColor(Color.BLACK);
                        completed_value_layout4.setTextColor(Color.BLACK);
                        completed_layout4.setTextColor(Color.BLACK);
                        pending_value_layout4.setTextColor(Color.BLACK);

                        break;
                }

                return true;
            }


        });

        todaytask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!employeeid.equals("111")){

                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                    builder.setMessage("Only HOD have the accessment");
                    builder.setTitle("Alert !");
                    builder.setCancelable(false);


                    builder
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which)
                                {

                                    dialog.cancel();
                                }
                            });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                else{
                    Intent a = new Intent(HomeActivity.this,TaskScreen.class);
                    a.putExtra("date",Sdate);
                    startActivity(a);
                }

            }
        });
        yesterdaytask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!employeeid.equals("111")){

                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                    builder.setMessage("Only HOD have the accessment");
                    builder.setTitle("Alert !");
                    builder.setCancelable(false);


                    builder
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which)
                                {

                                    dialog.cancel();
                                }
                            });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                else {
                    Intent a = new Intent(HomeActivity.this, TaskScreen.class);
                    a.putExtra("date", yesterdayDate);
                    startActivity(a);
                }
            }
        });
        todaymeet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(employeeid==null){

                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                    builder.setMessage("Be an Employee in Prabisha for accessing");
                    builder.setTitle("Alert !");
                    builder.setCancelable(false);


                    builder
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which)
                                {

                                    dialog.cancel();
                                }
                            });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                else {
                    Intent a = new Intent(HomeActivity.this, MeetScreen.class);
                    a.putExtra("name", employeeid);
                    a.putExtra("date", Sdate);
                    startActivity(a);
                }
            }
        });
        yesterdaymeet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(employeeid==null){

                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                    builder.setMessage("Be an Employee in Prabisha for accessing");
                    builder.setTitle("Alert !");
                    builder.setCancelable(false);


                    builder
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which)
                                {

                                    dialog.cancel();
                                }
                            });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                else {
                    Intent a = new Intent(HomeActivity.this, MeetScreen.class);
                    a.putExtra("date", yesterdayDate);
                    a.putExtra("name", employeeid);
                    startActivity(a);
                }
            }
        });

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        /*AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);*/

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case  R.id.navigation_home:

                    case  R.id.navigation_dashboard:
                        Intent b = new Intent(HomeActivity.this,Dashboard.class);
                        b.putExtra("name",employeeid);
                        b.putExtra("date",Sdate);
                        startActivity(b);
                        break;
                    case  R.id.navigation_notifications:
                        Intent c = new Intent(HomeActivity.this,NotificationsScreen.class);
                        startActivity(c);
                        break;

                    case R.id.navigation_account:
                        Intent d = new Intent(HomeActivity.this,Profile.class);
                        d.putExtra("name",employeeid);
                        startActivity(d);
                        break;

                }
                return false;
            }
        });
    }

    private void showpopup(){
        //We need to get the instance of the LayoutInflater, use the context of this activity
        LayoutInflater inflater = (LayoutInflater) HomeActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Inflate the view from a predefined XML layout (no need for root id, using entire layout)
        View layout = inflater.inflate(R.layout.popup_layout,null);

        //load results

        ((TextView)layout.findViewById(R.id.bronzeName)).setText("Notes");
        ((TextView)layout.findViewById(R.id.portalname)).setText("Portals");
        ((TextView)layout.findViewById(R.id.socialmediatext)).setText("Social Media Links");
        //Get the devices screen density to calculate correct pixel sizes
        float density=HomeActivity.this.getResources().getDisplayMetrics().density;
        // create a focusable PopupWindow with the given layout and correct size
        final PopupWindow pw = new PopupWindow(layout, (int)density*440, (int)density*585, true);
        //Button to close the pop-up


        ((LinearLayout) layout.findViewById(R.id.bronze)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(employeeid==null){

                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                    builder.setMessage("Be an Employee in Prabisha for accessing");
                    builder.setTitle("Alert !");
                    builder.setCancelable(false);


                    builder
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which)
                                {

                                    dialog.cancel();
                                }
                            });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                else {
                    Intent i = new Intent(HomeActivity.this, NotesActivity.class);
                    i.putExtra("name", employeeid);
                    startActivity(i);
                }

            }
        });

        ((LinearLayout) layout.findViewById(R.id.portals)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Intent.ACTION_VIEW, Uri.parse("http://wistm.edu.in/"));
                startActivity(intent);

             /*   Intent i = new Intent(HomeActivity.this,MeetScreen.class);
                startActivity(i);*/
            }
        });
        ((LinearLayout) layout.findViewById(R.id.socialmedialayout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Intent.ACTION_VIEW, Uri.parse("http://wistm.edu.in/"));
                startActivity(intent);
/*
                Intent i = new Intent(HomeActivity.this,MeetScreen.class);
                startActivity(i);*/
            }
        });
        ((Button) layout.findViewById(R.id.logoutbutton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences =getSharedPreferences("ckeckbox",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("remember","false");
                editor.apply();

                Intent i = new Intent(HomeActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
        ((Button) layout.findViewById(R.id.close)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pw.dismiss();
            }
        });
        //Set up touch closing outside of pop-up
        pw.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pw.setTouchInterceptor(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    pw.dismiss();
                    return true;
                }
                return false;
            }
        });
        pw.setOutsideTouchable(true);
        // display the pop-up in the center
        pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
    }

    @Override
    public void onBackPressed() {Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        System.exit(1);    }
}