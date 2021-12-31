package com.example.myclg.Adapters;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myclg.Models.Attendance;
import com.example.myclg.Models.Messages;
import com.example.myclg.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MessagesAdapter extends FirebaseRecyclerAdapter<Messages, MessagesAdapter.myviewholder> {

    Context context;


    String empid;
    int ITEM_SEND=1;
    int ITEM_RECIEVE=2;

    public MessagesAdapter(@NonNull FirebaseRecyclerOptions<Messages> options, Context context,String empid) {
        super(options);
        this.context = context;
        this.empid=empid;

    }

    @NonNull
    @Override
    public com.example.myclg.Adapters.MessagesAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==ITEM_SEND)
        {
            View view= LayoutInflater.from(context).inflate(R.layout.senderchatlayout,parent,false);
            return  new com.example.myclg.Adapters.MessagesAdapter.myviewholder(view);
        }
        else
        {
            View view= LayoutInflater.from(context).inflate(R.layout.recieverchatlayout,parent,false);
            return  new com.example.myclg.Adapters.MessagesAdapter.myviewholder(view);
        }
    }

    @Override
    protected void onBindViewHolder(@NonNull com.example.myclg.Adapters.MessagesAdapter.myviewholder holder, int position, @NonNull Messages model) {

        holder.textViewmessaage.setText(model.getMessage());
        holder.timeofmessage.setText(model.getCurrenttime());
        if(model.getSenderId().equals(empid)){
            holder.space.setVisibility(View.VISIBLE);
            holder.lay.setBackgroundColor(Color.parseColor("#00CD00"));

        }
        if(!model.getParid().equals(empid)){
            holder.lay.setVisibility(View.GONE);
        }
        if(model.getParid().equals(empid) ||(!model.getParid().equals("0")&& empid.equals("111"))){
            holder.tv.setVisibility(View.VISIBLE);
            holder.lay.setVisibility(View.VISIBLE);

        }
       if (model.getParid().equals("0")){
            holder.lay.setVisibility(View.VISIBLE);

        }

    /*    if(holder.getClass()==SenderViewHolder.class)
        {
            SenderViewHolder viewHolder=(SenderViewHolder)holder;
            viewHolder.textViewmessaage.setText(messages.getMessage());
            viewHolder.timeofmessage.setText(messages.getCurrenttime());
        }
        else
        {
            RecieverViewHolder viewHolder=(RecieverViewHolder)holder;
            viewHolder.textViewmessaage.setText(messages.getMessage());
            viewHolder.timeofmessage.setText(messages.getCurrenttime());
        }*/








    }


   /* @Override
    public int getItemViewType(int position) {
        if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(messages.getSenderId()))

        {
            return  ITEM_SEND;
        }
        else
        {
            return ITEM_RECIEVE;
        }
    }

    @Override
    public int getItemCount() {
        return messagesArrayList.size();
    }
*/







    class myviewholder extends RecyclerView.ViewHolder
    {

        TextView textViewmessaage;
        TextView timeofmessage;
        LinearLayout lay;
        Space space;
        TextView tv;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            textViewmessaage=itemView.findViewById(R.id.sendermessage);
            timeofmessage=itemView.findViewById(R.id.timeofmessage);
            space=itemView.findViewById(R.id.spacer);
            tv= itemView.findViewById(R.id.privateview);
            lay=itemView.findViewById(R.id.layoutformessage);
        }
    }

/*
    class RecieverViewHolder extends RecyclerView.ViewHolder
    {

        TextView textViewmessaage;
        TextView timeofmessage;


        public RecieverViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewmessaage=itemView.findViewById(R.id.sendermessage);
            timeofmessage=itemView.findViewById(R.id.timeofmessage);
        }
    }
*/




}