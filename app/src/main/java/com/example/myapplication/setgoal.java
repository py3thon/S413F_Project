package com.example.myapplication;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

public class setgoal extends AppCompatActivity {
    private EditText targetText, startDateSelect, endDateSelect;
    private Spinner typeSpinner;
    private DatePickerDialog.OnDateSetListener startDateSetListener;
    private DatePickerDialog.OnDateSetListener endDateSetListener;
    private myDb mydb;
    private static final String[] COLUMNS = {"type", "target", "startDate", "endDate"};
    private String formatedstartDate;
    private String formatedendDate;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setgoal);
        mydb = new myDb(this);


        startDateSelect = findViewById(R.id.startDate);
        endDateSelect = findViewById(R.id.endDate);
        typeSpinner = findViewById(R.id.type);
        targetText = findViewById(R.id.target);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                COLUMNS[0] = selectedItemText;
            }

            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        endDateSelect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        setgoal.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        endDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        endDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                formatedendDate = sdf.format(calendar.getTime());
                String date = month + 1 + "/" + day + "/" + year;
                COLUMNS[3] = formatedendDate;
                endDateSelect.setText(date);

            }
        };

        startDateSelect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        setgoal.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        startDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        startDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                formatedstartDate = sdf.format(calendar.getTime());
                String date = month + 1 + "/" + day + "/" + year;
                startDateSelect.setText(date);
                COLUMNS[2] = formatedstartDate;
            }
        };
    }

    public void back(View e) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void save(View e) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM");
        try {
            if (targetText.getText().length() == 0 || targetText.getText().toString().startsWith("0") || startDateSelect.getText().length() == 0 || endDateSelect.getText().length() == 0) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                AlertDialog alert = dialog.setTitle("Incorrect Input")
                        .setMessage("Please fill in all the blanks correctly")
                        .setNeutralButton("OK", null)
                        .create();
                alert.show();
            } else {
                Date start = sdf.parse(formatedstartDate);
                Date end = sdf.parse(formatedendDate);
                if(end.before(start)){ AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                    AlertDialog alert = dialog.setTitle("Incorrect Input")
                            .setMessage("Please fill in all the blanks correctly")
                            .setNeutralButton("OK", null)
                            .create();
                    alert.show();}
                else{
                    COLUMNS[1] = targetText.getText().toString();
                    mydb.addgoal(COLUMNS);
                    Intent intent = new Intent(this, ChallengeActivity.class);
                    startActivity(intent);}

                }

        } catch (ParseException parseException) {
            parseException.printStackTrace();
        }


    }
}


