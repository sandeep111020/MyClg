package com.example.myclg.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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


public class TodoAdapter extends FirebaseRecyclerAdapter<User, TodoAdapter.myviewholder> {

    String date,taskid,empid;

    Context context;
    DatabaseReference databaseRef;
    String checj;

    public TodoAdapter(@NonNull FirebaseRecyclerOptions<User> options, Context context) {
        super(options);
        this.context = context;

    }



    @Override
    protected void onBindViewHolder(@NonNull com.example.myclg.Adapters.TodoAdapter.myviewholder holder, int position, @NonNull User model) {

        String chek;

        holder.taskdetails.setText("Task Details: "+model.getTaskdetails());
        date=model.getDate();
        chek=model.getStatus();
        empid=model.getEmpid();
        taskid=model.getTaskid();
        if (chek.equals("completed") || chek.equals("Completed and reviewed")){
            holder.status.setChecked(true);
        }
        else {
            holder.status.setChecked(false);
        }
        holder.status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                if (checked){

                    checj="completed";
                    Log.d("1", "onClick: and completed "+model.getTaskdetails()+model.getTasktype());
                    databaseRef = FirebaseDatabase.getInstance().getReference().child("CSE").child("dailyreport").child(date).child(empid);
                    databaseRef.child(model.getTaskid()).child("status").setValue(checj);
                }
                else{
                    checj="pending";
                    databaseRef = FirebaseDatabase.getInstance().getReference().child("CSE").child("dailyreport").child(date).child(empid);
                    databaseRef.child(model.getTaskid()).child("status").setValue(checj);

                }
            }
        });









    }

    @NonNull
    @Override
    public com.example.myclg.Adapters.TodoAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todorecyclerlayout, parent, false);

        return new com.example.myclg.Adapters.TodoAdapter.myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder {

        TextView taskdetails;
        CheckBox status;



        public myviewholder(@NonNull View itemView) {
            super(itemView);



            taskdetails = (TextView) itemView.findViewById(R.id.taskdescript);
            status = (CheckBox) itemView.findViewById(R.id.taskstatus);





        }
    }



}