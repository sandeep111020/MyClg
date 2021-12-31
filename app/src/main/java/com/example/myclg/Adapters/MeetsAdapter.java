package com.example.myclg.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myclg.Models.Meets;
import com.example.myclg.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MeetsAdapter extends FirebaseRecyclerAdapter<Meets, MeetsAdapter.myviewholder> {


    Context context;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    String Sdate;
    private DatabaseReference databaseRef,databaseRef4;

    public MeetsAdapter(@NonNull FirebaseRecyclerOptions<Meets> options, Context context) {
        super(options);
        this.context = context;


    }




    @NonNull
    @Override
    public com.example.myclg.Adapters.MeetsAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.details_layout, parent, false);

        return new com.example.myclg.Adapters.MeetsAdapter.myviewholder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull Meets model) {
        holder.taskdetails.setText("Description: "+model.getMeetlink());
        //holder.empid.setText("Date :"+model.getMeetdate());
        holder.empid.setText("HostId: "+model.getHostid());
        if (!model.getEveryone().equals("")){
            holder.tasktype.setText("Participants: "+model.getEveryone());
        }else {
            holder.tasktype.setText("Participants: "+model.getMeetperson1()+","+model.getMeetperson2());

        }
        holder.name.setText("Meet Title: "+model.getMeettitle());
        holder.status.setText("Time: "+model.getMeettime());

    }

    class myviewholder extends RecyclerView.ViewHolder {

        TextView name, empid,date,tasktype,taskdetails,timetaken,status,remarks;


        public myviewholder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.user_name);
            empid = (TextView) itemView.findViewById(R.id.user_empid);

            tasktype = (TextView) itemView.findViewById(R.id.user_task_type);
            taskdetails = (TextView) itemView.findViewById(R.id.user_task_description);

            status = (TextView) itemView.findViewById(R.id.user_status);

           /* calendar = Calendar.getInstance();
            dateFormat = new SimpleDateFormat("ddMMyyyy");
            Sdate = dateFormat.format(calendar.getTime());

            databaseRef = FirebaseDatabase.getInstance().getReference().child("Employees");
            databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for(DataSnapshot snapshot1 : snapshot.getChildren()) {

                        String tempid= snapshot1.child("userEmpid").getValue(String.class);

                        databaseRef4 = FirebaseDatabase.getInstance().getReference().child("eod").child(Sdate).child(tempid);
                        databaseRef4.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                for(DataSnapshot snapshot1 : snapshot.getChildren()) {
                                    name.setText(snapshot1.child("name").getValue(String.class));
                                    empid.setText(snapshot1.child("empid").getValue(String.class));
                                    tasktype.setText(snapshot1.child("tasktype").getValue(String.class));
                                    taskdetails.setText(snapshot1.child("taskdetails").getValue(String.class));
                                    status.setText(snapshot1.child("status").getValue(String.class));


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
            });*/



        }
    }



}