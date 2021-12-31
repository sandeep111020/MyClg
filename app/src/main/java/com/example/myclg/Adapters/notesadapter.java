package com.example.myclg.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myclg.Models.firebasemodel;
import com.example.myclg.R;
import com.example.myclg.editnoteactivity;
import com.example.myclg.notedetails;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class notesadapter extends FirebaseRecyclerAdapter<firebasemodel, notesadapter.myviewholder> {


    Context context;
    private DatabaseReference mDatabase;

    public notesadapter(@NonNull FirebaseRecyclerOptions<firebasemodel> options, Context context) {
        super(options);
        this.context = context;

    }
    @NonNull
    @Override
    public com.example.myclg.Adapters.notesadapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_layout, parent, false);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Notes");
        return new com.example.myclg.Adapters.notesadapter.myviewholder(view);
    }



    private int getRandomColor()
    {
        List<Integer> colorcode=new ArrayList<>();
        colorcode.add(R.color.gray);
        colorcode.add(R.color.pink);
        colorcode.add(R.color.lightgreen);
        colorcode.add(R.color.skyblue);
        colorcode.add(R.color.color1);
        colorcode.add(R.color.color2);
        colorcode.add(R.color.color3);

        colorcode.add(R.color.color4);
        colorcode.add(R.color.color5);
        colorcode.add(R.color.green);

        Random random=new Random();
        int number=random.nextInt(colorcode.size());
        return colorcode.get(number);



    }




    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onBindViewHolder(@NonNull myviewholder noteViewHolder, int position, @NonNull firebasemodel model) {

        ImageView popupbutton=noteViewHolder.itemView.findViewById(R.id.menupopbutton);

        int colourcode=getRandomColor();
        noteViewHolder.mnote.setBackgroundColor(noteViewHolder.itemView.getResources().getColor(colourcode,null));

        noteViewHolder.notetitle.setText(model.getTitle());
        noteViewHolder.notecontent.setText(model.getContent());

        noteViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //we have to open note detail activity


                Intent intent=new Intent(v.getContext(), notedetails.class);
                intent.putExtra("title",model.getTitle());
                intent.putExtra("content",model.getContent());
                intent.putExtra("name", model.getId());

                v.getContext().startActivity(intent);

                // Toast.makeText(getApplicationContext(),"This is Clicked",Toast.LENGTH_SHORT).show();
            }
        });


        popupbutton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu=new PopupMenu(v.getContext(),v);
                popupMenu.setGravity(Gravity.END);
                popupMenu.getMenu().add("Edit").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        Intent intent=new Intent(v.getContext(), editnoteactivity.class);
                        intent.putExtra("title",model.getTitle());
                        intent.putExtra("content",model.getContent());
                        intent.putExtra("name", model.getId());
                        v.getContext().startActivity(intent);
                        return false;
                    }
                });

                final DatabaseReference itemRef = getRef(position);
                final String myKey = itemRef.getKey();
                popupMenu.getMenu().add("Delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        mDatabase.child(model.getId()).child(myKey).removeValue();
                        //Toast.makeText(v.getContext(),"This note is deleted",Toast.LENGTH_SHORT).show();
                               /* DocumentReference documentReference=firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("myNotes").document(docId);
                                documentReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(v.getContext(),"This note is deleted",Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(v.getContext(),"Failed To Delete",Toast.LENGTH_SHORT).show();
                                    }
                                });*/


                        return false;
                    }
                });

                popupMenu.show();
            }
        });
    }


    public class myviewholder extends RecyclerView.ViewHolder {
        private TextView notetitle;
        private TextView notecontent;
        LinearLayout mnote;


        public myviewholder(@NonNull View itemView) {
            super(itemView);
            notetitle = itemView.findViewById(R.id.notetitle);
            notecontent = itemView.findViewById(R.id.notecontent);
            mnote = itemView.findViewById(R.id.note);


        }
    }
}
