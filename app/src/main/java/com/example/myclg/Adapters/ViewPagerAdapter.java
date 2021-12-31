
package com.example.myclg.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myclg.R;

import java.util.List;

public class ViewPagerAdapter extends RecyclerView.Adapter<com.example.myclg.Adapters.ViewPagerAdapter.ViewHolder> {

    private List<String> mData;
    private LayoutInflater mInflater;
    private ViewPager2 viewPager2;
    Context context;


    private int[] colorArray = new int[]{R.drawable.clgimg1, R.drawable.clgimg2, R.drawable.clgimg1, R.drawable.clgimg2};

    public ViewPagerAdapter(Context context, List<String> data, ViewPager2 viewPager2) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.viewPager2 = viewPager2;
        this.context=context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.slidebuttons_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String animal = mData.get(position);
        holder.myTextView.setText(animal);
        holder.relativeLayout.setBackgroundResource(colorArray[position]);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView myTextView;
        RelativeLayout relativeLayout;
        Button button;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.tvTitle);
            relativeLayout = itemView.findViewById(R.id.container);

            relativeLayout.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {


                    Intent intent =new Intent(Intent.ACTION_VIEW, Uri.parse("http://wistm.edu.in/"));
                    context.startActivity(intent);

                }
            });


        }
    }

}

