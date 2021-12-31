package com.example.myclg.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myclg.Models.Attendance;
import com.example.myclg.Models.User;
import com.example.myclg.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class AttendanceAdapter extends FirebaseRecyclerAdapter<Attendance, AttendanceAdapter.myviewholder> {

    String date,taskid,empid,checj;

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String Sdate;
    Context context;
    private DatabaseReference databaseRef;

    public AttendanceAdapter(@NonNull FirebaseRecyclerOptions<Attendance> options, Context context) {
        super(options);
        this.context = context;

    }



    @Override
    protected void onBindViewHolder(@NonNull com.example.myclg.Adapters.AttendanceAdapter.myviewholder holder, int position, @NonNull Attendance model) {

        String chek;
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("ddMMyyyy");
        Sdate = dateFormat.format(calendar.getTime());
        holder.taskdetails.setText("Name: "+model.getName());

        chek=model.getStatus();

        if (chek.equals("true")){
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
                    // Do your coding
                    checj="true";
                    databaseRef = FirebaseDatabase.getInstance().getReference().child("CSE").child("Attendance").child(Sdate).child(model.getId());
                    Attendance add= new Attendance(model.getId(),model.getName(),checj);
                    databaseRef.setValue(add);
                }
                else{
                    checj="false";
                    databaseRef = FirebaseDatabase.getInstance().getReference().child("CSE").child("Attendance").child(Sdate).child(model.getId());
                    Attendance add= new Attendance(model.getId(),model.getName(),checj);
                    databaseRef.setValue(add);
                    // Do your coding
                }
            }
        });









    }

    @NonNull
    @Override
    public com.example.myclg.Adapters.AttendanceAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todorecyclerlayout, parent, false);

        return new com.example.myclg.Adapters.AttendanceAdapter.myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder {

        TextView taskdetails;
        CheckBox status;
        private DatabaseReference databaseRef;
        Button sumit;
        String checj;


        public myviewholder(@NonNull View itemView) {
            super(itemView);



            taskdetails = (TextView) itemView.findViewById(R.id.taskdescript);
            status = (CheckBox) itemView.findViewById(R.id.taskstatus);






        }
    }



}