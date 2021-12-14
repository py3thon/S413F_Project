package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Member;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class goalAdapter extends RecyclerView.Adapter<goalAdapter.ViewHolder> {
    private Context context;
    private ArrayList<HashMap<String,String>> goalArray;
    private myDb db;


    goalAdapter(Context context, ArrayList<HashMap<String,String>> goalArray, myDb db){
        this.context = context;
        this.goalArray = goalArray;
        this.db = db;
    }

    @Override
    public goalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.challenge_activity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull goalAdapter.ViewHolder holder, int position) {
        final HashMap<String,String> a  = goalArray.get(position);
        holder.type.setText(a.get("type"));
        holder.target.setText(a.get("target"));
        holder.startDate.setText(a.get("startDate"));
        holder.endDate.setText(a.get("endDate"));
        holder.progressBar.setMax(Integer.parseInt(a.get("target")));
        holder.progressBar.setProgress(db.getSum(a.get("type"),a.get("startDate"),a.get("endDate")));
        holder.moneyUsed.setText(db.getSum(a.get("type"),a.get("startDate"),a.get("endDate")).toString());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-dd-MM");

        try {
            Date end = formatter.parse(a.get("endDate"));
            Date today = new Date();
            if(end.before(today)&&Integer.parseInt(a.get("target")) > db.getSum(a.get("type"),a.get("startDate"),a.get("endDate"))){
                Log.v("aababababab","Binghogosdf dsfo");
                holder.successimage.setVisibility(View.VISIBLE);
                holder.failimage.setVisibility(View.INVISIBLE);
            }
            else if(Integer.parseInt(a.get("target")) < db.getSum(a.get("type"),a.get("startDate"),a.get("endDate"))){
                holder.failimage.setVisibility(View.VISIBLE);
                holder.successimage.setVisibility(View.INVISIBLE);
            }
            else{
                holder.failimage.setVisibility(View.INVISIBLE);
                holder.successimage.setVisibility(View.INVISIBLE);
            };


        } catch (ParseException e) {
            e.printStackTrace();
        }

        setAnimation(holder.itemView, position);
    }
    private void setAnimation(View viewToAnimate, int position)
    {            int lastPosition=0;

        // If the bound view wasn't previously displayed on screen, it's animated
        if (position >= lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            animation.setDuration(700);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return goalArray.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView type, target, startDate, endDate,moneyUsed;
        ProgressBar progressBar;
        ImageView failimage,successimage ;
        ViewHolder(View itemView) {
            super(itemView);
            type =  itemView.findViewById(R.id.type);
            target = itemView.findViewById(R.id.target);
            startDate = (TextView) itemView.findViewById(R.id.startDate);
            endDate = (TextView) itemView.findViewById(R.id.endDate);
            progressBar = itemView.findViewById(R.id.progressBar);
            moneyUsed = itemView.findViewById(R.id.moneyUsed);
            failimage = itemView.findViewById(R.id.failimage);
            successimage = itemView.findViewById(R.id.successimage);
        }
    }
}
