package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SetMoneyUsedActivity extends AppCompatActivity {
    private DatePickerDialog.OnDateSetListener moneyDateSetListener;
    private EditText moneyDate, amount, note;
    private Spinner moneyType ;
    private myDb db;
    private String[] MONEY_COLUMNS = {"type","amount","date","note"};

    String newString;
    Date dateObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_money_used);
        moneyDate = findViewById(R.id.setmoneydate);
        amount = findViewById(R.id.setamount);
        note = findViewById(R.id.setmoneynote);
        moneyType = findViewById(R.id.setmoneytype);
        db = new myDb(this);

        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            newString= null;
        } else {
            newString= extras.getString("today String");
            System.out.println("newString " + newString);
            dateObject= (Date) extras.get("date object");
            System.out.println("showDate " + dateObject);
        }
        moneyDate.setText(newString);

        MONEY_COLUMNS[2]=newString;

        Calendar newCalendar = Calendar.getInstance();
        newCalendar.setTime(dateObject);
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        String showDate = dateFormat.format(newCalendar.getTime());
        moneyDate.setText(showDate);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        moneyType.setAdapter(adapter);
        moneyType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                MONEY_COLUMNS[0] = selectedItemText;
            }

            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        moneyDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        SetMoneyUsedActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        moneyDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        moneyDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String formatedDate = sdf.format(calendar.getTime());
                String date = month + 1 + "/" + day + "/" + year;
                MONEY_COLUMNS[2]=formatedDate;
                //2021-12-13
                System.out.println(formatedDate);
                moneyDate.setText(date);

            }
        };
    }

    public void set(View view) {
        if (amount.getText().toString().startsWith("0")||amount.getText().toString().length() == 0 || moneyDate.getText().length() == 0) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            AlertDialog alert = dialog.setTitle("Incorrect Input")
                    .setMessage("Please fill in all the blanks correctly")
                    .setNeutralButton("OK", null)
                    .create();
            alert.show();
        } else {
            MONEY_COLUMNS[1] = amount.getText().toString();
            MONEY_COLUMNS[3] = note.getText().toString();
            db.addmoneyspend(MONEY_COLUMNS);
            Toast.makeText(this, "SAVED",
                    Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, CalendarActivity.class);
            startActivity(intent);
            //close
            //finish();
        }
    }

    //missing back function
    public void back(View view) {
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
        //close
        finish();
    }
}