package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class CalendarActivity extends AppCompatActivity /*implements Runnable*/{
    CalendarView calendar;
    private String today;
    private Date dateObject;

    private myDb db;
    private ItemAdapter itemAdapter;
    private RecyclerView recyclerView;
    private HashMap<String, String> dataArray;
    private ArrayList<HashMap<String,String>> array ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_calendar);

        calendar = (CalendarView)
                findViewById(R.id.calendar);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = new Date();
        System.out.println(dateFormat.format(date));

        today= dateFormat.format(date);
        Toast.makeText(CalendarActivity.this,today, Toast.LENGTH_SHORT).show();

        Calendar newCalendar = Calendar.getInstance();
        dateObject = newCalendar.getTime();
        System.out.println("dateObject " + dateObject);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = new myDb(this);
        array = db.getMonthJob(today);
        //array=db.getAllSpend();
        System.out.println(array);

        recyclerView = (RecyclerView) findViewById(R.id.job_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemAdapter = new ItemAdapter(this,array,db);
        recyclerView.setAdapter(itemAdapter);
        itemAdapter.notifyDataSetChanged();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                // this method is called
                // when the item is moved.
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // this method is called when we swipe our item to right direction.
                // on below line we are getting the item at a particular position.
                dataArray = array.get(viewHolder.getAdapterPosition());

                // below line is to get the position
                // of the item at that position.
                int position = viewHolder.getAdapterPosition();
                array.remove(viewHolder.getAdapterPosition());
                // this method is called when item is swiped.
                // below line is to remove item from our array list.
                db.deleteMoney(dataArray.get("id"));
                // below line is to notify our item is removed from adapter.
                itemAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                itemAdapter.notifyDataSetChanged();
                // below line is to display our snackbar with action.

            }
            // at last we are adding this
            // to our recycler view.
        }).attachToRecyclerView(recyclerView);


        calendar
                .setOnDateChangeListener(
                        new CalendarView
                                .OnDateChangeListener() {
                            @Override

                            // In this Listener have one method
                            // and in this method we will
                            // get the value of DAYS, MONTH, YEARS
                            public void onSelectedDayChange(
                                    @NonNull CalendarView view,
                                    int year,
                                    int month,
                                    int dayOfMonth) {

                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, month, dayOfMonth);
                                dateObject = calendar.getTime();

                                // Store the value of date with
                                // format in String type Variable
                                // Add 1 in month because month
                                // index is start with 0
                                String tempDay = "";
                                String tempMonth = "" ;
                                int aMonth = month +1 ;
                                if (dayOfMonth >= 1 && dayOfMonth <=9){

                                    tempDay += "0" + (dayOfMonth);
                                } else {
                                    tempDay = String.valueOf(dayOfMonth);
                                }



                                if (aMonth  >= 1 &&aMonth <=9){

                                    tempMonth += "0" + (aMonth);
                                } else {
                                    tempMonth = String.valueOf(aMonth);
                                }

                                today = year + "-" +tempMonth +  "-" + tempDay;

                                // set this date in TextView for Display
                                //date_view.setText(Date);

                                Toast.makeText(CalendarActivity.this,today, Toast.LENGTH_SHORT).show();


                                array = db.getDayJob(today);
                                System.out.println("onSelectedDayChange =" +array);
                                //itemAdapter = new ItemAdapter(this,array,db);
                                itemAdapter.setSpendArray(array);
                                //jobDb.getAllJob(jobList);
                                itemAdapter.notifyDataSetChanged();
                            }
                        });

    }

    public void addJob(View view) {

        Intent intent = new Intent(this, SetMoneyUsedActivity.class);
        intent.putExtra("today String", today);
        System.out.println("111 today = " + today);
        intent.putExtra("date object", dateObject);
        System.out.println("111 dateObject = " + dateObject);
        startActivity(intent);
        //startActivityForResult(intent, JOB_ADD_ID);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    public void month(View view) {
        String string = today;
        String[] parts = string.split("-");
        String part1 = parts[0]; // 004
        String part2 = parts[1]; // 034556
        String result = part1 + "-" + part2 + "%";
        System.out.println(result);
        array = db.getMonthJob(result);
        System.out.println(array);
        itemAdapter.setSpendArray(array);
        itemAdapter.notifyDataSetChanged();
    }

    //@RequiresApi(api = Build.VERSION_CODES.O)
    public void all(View view) {
        array = db.getAllSpend();
        System.out.println(array);
        itemAdapter.setSpendArray(array);
        itemAdapter.notifyDataSetChanged();
    }



    /*@Override
    public void run() {
        array = db.getAllSpend();
        System.out.println(array);
        itemAdapter.setSpendArray(array);
        itemAdapter.notifyDataSetChanged();
        System.out.println("This code is running in a thread");
    }*/


    /*protected void onResume() {
        super.onResume();
        //jobDb.getAllJob(jobList);
        db.getDayJob(today);
        itemAdapter.notifyDataSetChanged();
    }*/
}
