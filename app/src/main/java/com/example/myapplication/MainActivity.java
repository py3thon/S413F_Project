package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.app.FragmentTransaction;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //preference
        //FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        //MyPref_Frag mypref_frag = new MyPref_Frag();
        //fragmentTransaction.replace(android.R.id.content,mypref_frag);
        //fragmentTransaction.commit();

    }
    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(MainActivity.this, MyPref_Frag.class);
                startActivity(intent);
                break;
            case R.id.action_about:
                new AlertDialog.Builder(this)
                        .setTitle("Money Management")
                        .setMessage("You can keep tracking your daily spending and income.")
                        .setNeutralButton(android.R.string.ok, null)
                        .show();
                break;
        }
        return false;
    }
}