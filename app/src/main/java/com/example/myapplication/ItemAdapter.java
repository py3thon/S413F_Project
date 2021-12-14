package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private Context context;
    private ArrayList<HashMap<String,String>> spendArray;
    private myDb db;


    ItemAdapter(Context context, ArrayList<HashMap<String,String>> spendArray, myDb db){
        this.context = context;
        this.spendArray = spendArray;
        this.db = db;
    }

    public void setSpendArray(ArrayList<HashMap<String, String>> spendArray) {
        this.spendArray = spendArray;
    }

    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycleview_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
        final HashMap<String,String> a  = spendArray.get(position);
        holder.type.setText(a.get("type"));
        holder.amount.setText(a.get("amount"));
        holder.date.setText(a.get("date"));
        holder.note.setText(a.get("note"));
        //holder.progressBar.setMax(Integer.parseInt(a.get("target")));
        //holder.progressBar.setProgress(db.getSum(a.get("type"),a.get("startDate"),a.get("endDate")));
        //holder.moneyUsed.setText(db.getSum(a.get("type"),a.get("startDate"),a.get("endDate")).toString());
        //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-dd-MM");

        /*try {
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
        }*/

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
        return spendArray.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView type, amount, date, note;
        //ProgressBar progressBar;
        //ImageView failimage,successimage ;
        ViewHolder(View itemView) {
            super(itemView);
            type =  itemView.findViewById(R.id.type_textview);
            amount = itemView.findViewById(R.id.amount_textview);
            date = (TextView) itemView.findViewById(R.id.date_textview);
            note = (TextView) itemView.findViewById(R.id.note_textview);
            //progressBar = itemView.findViewById(R.id.progressBar);
            //moneyUsed = itemView.findViewById(R.id.moneyUsed);
            //failimage = itemView.findViewById(R.id.failimage);
            //successimage = itemView.findViewById(R.id.successimage);
        }
    }
}
