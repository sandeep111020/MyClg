package com.example.myclg.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myclg.Models.profileinfo;
import com.example.myclg.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class TeamAdapter extends FirebaseRecyclerAdapter<profileinfo, TeamAdapter.myviewholder> {


    Context context;

    public TeamAdapter(@NonNull FirebaseRecyclerOptions<profileinfo> options, Context context) {
        super(options);
        this.context = context;

    }
    @NonNull
    @Override
    public com.example.myclg.Adapters.TeamAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teammate_details, parent, false);

        return new com.example.myclg.Adapters.TeamAdapter.myviewholder(view);
    }




    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull profileinfo model) {
        holder.Name.setText(model.getUserName());
        holder.Designation.setText(model.getUserDept());
        holder.Empid.setText(model.getUserEmpid());
        // we are using Picasso to load images
        // from URL inside our image view.
        Picasso.get().load(model.getImageURL()).into(holder.Profile);
    }


    public class myviewholder extends RecyclerView.ViewHolder {
        // creating variables for our
        // views of recycler items.
        private TextView Name,Designation,Empid;
        private ImageView Profile;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            // initializing the views of recycler views.
            Name = itemView.findViewById(R.id.name);
            Designation=itemView.findViewById(R.id.designation);
            Empid=itemView.findViewById(R.id.empid);
            Profile = itemView.findViewById(R.id.profile);

        }
    }
}
