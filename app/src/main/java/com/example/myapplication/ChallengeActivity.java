package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;

public class ChallengeActivity extends AppCompatActivity {
    private myDb db;
    private goalAdapter goalAdapter;
    private RecyclerView recyclerView;
    private HashMap<String, String> dataArray;
    private ArrayList<HashMap<String,String>> array ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cardview);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = new myDb(this);
        array = db.getAllGoal();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        goalAdapter = new goalAdapter(this,array,db);
        recyclerView.setAdapter(goalAdapter);
        goalAdapter.notifyDataSetChanged();
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
                db.deleteItem(dataArray.get("id"));
                // below line is to notify our item is removed from adapter.
                goalAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                goalAdapter.notifyDataSetChanged();
                // below line is to display our snackbar with action.

            }
            // at last we are adding this
            // to our recycler view.
        }).attachToRecyclerView(recyclerView);


    }

    public void ADD(View e){
        Intent intent = new Intent(this, setgoal.class);
        startActivity(intent);
    }
}