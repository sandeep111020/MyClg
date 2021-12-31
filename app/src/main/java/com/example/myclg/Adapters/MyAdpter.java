package com.example.myclg.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myclg.Models.User;
import com.example.myclg.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MyAdpter extends FirebaseRecyclerAdapter<User, com.example.myclg.Adapters.MyAdpter.myviewholder>{


    Context context;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    String Sdate;
    private DatabaseReference databaseRef,databaseRef4;

    public MyAdpter(@NonNull FirebaseRecyclerOptions<User> options, Context context) {
        super(options);
        this.context = context;


    }



    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull User model) {

        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("CSE").child("dailyreport");


        holder.name.setText("Name: "+model.getName());
        holder.empid.setText("EmployeeId :"+model.getEmpid());
        holder.tasktype.setText("Task Type: "+model.getTasktype());
        holder.taskdetails.setText("Task Details: "+model.getTaskdetails());
        holder.status.setText("Status: "+model.getStatus());
        if(model.getStatus()!=null){
            if(model.getStatus().equals("completed")){
                holder.status.setText("Status: "+" In Review");
                holder.check.setVisibility(View.VISIBLE);
                holder.check.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean checked = ((CheckBox) v).isChecked();
                        if (checked){
                            // Do your coding
                            User addnewUser = new User(model.getEmpid(),model.getName(),model.getDate(),model.getEmail(),model.getTasktype(),model.getTaskdetails(),"Completed and reviewed",model.getRemarks(),model.getTaskid());
                            reference.child(model.getDate()).child(model.getEmpid()).child(model.getTaskid()).setValue(addnewUser);
                        }
                        else{
                            // Do your coding
                        }
                    }
                });


            }

        }






    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.details_layout, parent, false);

        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder {

        TextView name, empid,date,tasktype,taskdetails,timetaken,status,remarks;
        CheckBox check;


        public myviewholder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.user_name);
            empid = (TextView) itemView.findViewById(R.id.user_empid);

            tasktype = (TextView) itemView.findViewById(R.id.user_task_type);
            taskdetails = (TextView) itemView.findViewById(R.id.user_task_description);
            check=(CheckBox) itemView.findViewById(R.id.checktask);
            status = (TextView) itemView.findViewById(R.id.user_status);
            check.setVisibility(View.GONE);





        }
    }



}