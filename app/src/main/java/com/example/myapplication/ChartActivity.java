package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;


import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class ChartActivity extends AppCompatActivity{
    TextView food, shopping, health, business,entertainment,transport,other;
    PieChart pieChart;
    myDb db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        food = findViewById(R.id.food);
        shopping = findViewById(R.id.shopping);
        health = findViewById(R.id.health);
        business = findViewById(R.id.business);
        entertainment = findViewById(R.id.entertainment);
        transport = findViewById(R.id.transport);
        other = findViewById(R.id.other);
        db = new myDb(this);
        pieChart = findViewById(R.id.piechart);

        // Creating a method setData()
        // to set the text in text view and pie chart
        setData(0,0);
    }
    private void setData(int startDay, int endDay)
    {
        Date date = new Date(); //END DAY
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd ");
        calendar.add(Calendar.DATE,endDay);
        Date date1 = calendar.getTime();
        String end = formatter.format(date1);

        Date date2 = new Date(); //START DAY
        Calendar calendar1 = Calendar.getInstance();
        calendar.setTime(date2);
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd ");
        calendar1.add(Calendar.DATE,startDay);
        Date date3 = calendar1.getTime();
        String start = formatter1.format(date3);
        // Set the percentage of language used
        Log.v("bbbbbbbbbbbbbb",formatter.format(date1));
        food.setText(Integer.toString(db.getSum("Food / Dining",  start,end)));
        shopping.setText(Integer.toString(db.getSum("Shopping",  start,end)));
        health.setText(Integer.toString(db.getSum("Health / Fitness",  start,end)));
        business.setText(Integer.toString(db.getSum("Business Services",  start,end)));
        entertainment.setText(Integer.toString(db.getSum("Entertainment",  start,end)));
        transport.setText(Integer.toString(db.getSum("Transport",  start,end)));
        other.setText(Integer.toString(db.getSum("Other",  start,end)));

        // Set the data and color to the pie chart
        pieChart.addPieSlice(
                new PieModel(
                        "Food / Dining",
                        Integer.parseInt(food.getText().toString()),
                        Color.parseColor("#FFA726")));
        pieChart.addPieSlice(
                new PieModel(
                        "Shopping",
                        Integer.parseInt(shopping.getText().toString()),
                        Color.parseColor("#66BB6A")));
        pieChart.addPieSlice(
                new PieModel(
                        "Health / Fitness",
                        Integer.parseInt(health.getText().toString()),
                        Color.parseColor("#EF5350")));
        pieChart.addPieSlice(
                new PieModel(
                        "Business Services",
                        Integer.parseInt(business.getText().toString()),
                        Color.parseColor("#29B6F6")));
        pieChart.addPieSlice(
                new PieModel(
                        "Entertainment",
                        Integer.parseInt(entertainment.getText().toString()),
                        Color.parseColor("#FF0000")));
        pieChart.addPieSlice(
                new PieModel(
                        "Transport",
                        Integer.parseInt(transport.getText().toString()),
                        Color.parseColor("#274e13")));
        pieChart.addPieSlice(
                new PieModel(
                        "Other",
                        Integer.parseInt(other.getText().toString()),
                        Color.parseColor("#5b5b5b")));

        // To animate the pie chart
        pieChart.startAnimation();
    }
    public void today(View view){
        pieChart.clearChart();
        setData(0,0);

    }
    public void week(View view){
        pieChart.clearChart();
        setData(-7,0);
    }
    public void year(View view){
        pieChart.clearChart();
        setData(-365,0);
    }

}