package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kwtse on 9/9/2019.
 */

// Performing sql command to the supported database
public class myDb extends SQLiteOpenHelper {
    private	static final int DATABASE_VERSION =	3;
    private static final String DATABASE_NAME = "myDb.db"; // db name
    private static final String TABLE_NAME = "goal"; // table name
    private static final String[] COLUMNS = { "id","type", "target", "startDate", "endDate"}; // column names

    private static final String MONEY_TABLE = "setmoney";
    private static final String[] MONEY_COLUMNS = {"id","type","amount","date","note"};

    public myDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Table creation sql statement
        //CHALLENGE TABLE
        final String TABLE_CREATION = "create table if not exists " + TABLE_NAME + "( "
                + COLUMNS[0] + " integer primary key autoincrement ,"
                + COLUMNS[1] + " text , "
                + COLUMNS[2] + " text , "
                + COLUMNS[3] + " text , "
                + COLUMNS[4] + " text " + ")";
        db.execSQL(TABLE_CREATION);
        //MONEY SPEND TABLE
        final String MONEY_TABLE_CREATION = "create table if not exists " + MONEY_TABLE + "( "
                + MONEY_COLUMNS[0] + " integer primary key autoincrement ,"
                + MONEY_COLUMNS[1] + " text , "
                + MONEY_COLUMNS[2] + " text , "
                + MONEY_COLUMNS[3] + " text , "
                + MONEY_COLUMNS[4] + " text " + ")";
        db.execSQL(MONEY_TABLE_CREATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MONEY_TABLE);
        onCreate(db);
    }

    public ArrayList<HashMap<String,String>> getDayJob(String day) {
        ArrayList<HashMap<String,String>> spendArray = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] args = { day };
        Cursor cursor = db.query(MONEY_TABLE, null, MONEY_COLUMNS[3] + " =?", args, null, null, null);
        while (cursor.moveToNext()) {
            HashMap<String,String> spend = new HashMap<>();
            String id = cursor.getString(0);
            String type = cursor.getString(1);
            String amount = cursor.getString(2);
            String date = cursor.getString(3);
            String note = cursor.getString(4);
            spend.put("id",id);
            spend.put("type",type);
            spend.put("amount",amount);
            spend.put("date",date);
            spend.put("note",note);
            spendArray.add(spend);
        }
        cursor.close();
        return spendArray;
    }

    public ArrayList<HashMap<String,String>> getAllSpend() {
        ArrayList<HashMap<String,String>> spendArray = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(MONEY_TABLE, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            HashMap<String,String> spend = new HashMap<>();
            String id = cursor.getString(0);
            String type = cursor.getString(1);
            String amount = cursor.getString(2);
            String date = cursor.getString(3);
            String note = cursor.getString(4);
            spend.put("id",id);
            spend.put("type",type);
            spend.put("amount",amount);
            spend.put("date",date);
            spend.put("note",note);
            spendArray.add(spend);
        }
        cursor.close();
        return spendArray;
    }

    public ArrayList<HashMap<String,String>> getAllGoal() {
        ArrayList<HashMap<String,String>> goalArray = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            HashMap<String,String> goal = new HashMap<>();
            String id = cursor.getString(0);
            String type = cursor.getString(1);
            String target = cursor.getString(2);
            String startDate = cursor.getString(3);
            String endDate = cursor.getString(4);
            goal.put("id",id);
            goal.put("type",type);
            goal.put("target",target);
            goal.put("startDate",startDate);
            goal.put("endDate",endDate);
            goalArray.add(goal);
        }
        cursor.close();
        return goalArray;

    }

    public Integer getSum(String type, String startDate, String endDate){
        Integer total =1000;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM( CAST( " + MONEY_COLUMNS[2] + " as integer ) ) From " + MONEY_TABLE + " where type = '" + type +"' and date between '" + startDate.trim() + "' and '" + endDate.trim() +"'",null );
        if(cursor.moveToFirst()) {
            total = cursor.getInt(0);
        }
        cursor.close();
        Log.v("aaaaaaaaaaaaaaaaaaaaa",total.toString()) ;
        return total;
    };

    public void addgoal(String[] targetArray) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMNS[1], targetArray[0]);
        values.put(COLUMNS[2], targetArray[1]);
        values.put(COLUMNS[3], targetArray[2]);
        values.put(COLUMNS[4], targetArray[3]);
        db.insert(TABLE_NAME, null, values);
    }

    public void addmoneyspend(String[] array){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MONEY_COLUMNS[1], array[0]);
        values.put(MONEY_COLUMNS[2], array[1]);
        values.put(MONEY_COLUMNS[3], array[2]);
        values.put(MONEY_COLUMNS[4], array[3]);
        db.insert(MONEY_TABLE, null, values);
    }

    // Update job details
   /* public void updateJob(Job job){
        SQLiteDatabase db = this.getWritableDatabase();
        // Add code here
        // Task 2: Update an existing job corresponding to the given job
        // i. Create an ContentValues object
        // ii. Setup the ContentValues object with provided job information from the input job and the "put" method
        // iii. Employ the "update" method with the "db" object with respect to the job id of the input job object

        ContentValues values = new ContentValues();
        values.put(COLUMNS[1], job.getTitle());
        values.put(COLUMNS[2], job.getDetails());
        values.put(COLUMNS[3], job.getDate());
        values.put(COLUMNS[4], job.getTime());
        values.put(COLUMNS[5], job.getPriorityString());
        values.put(COLUMNS[6], job.getCategoryString());

        db.update(TABLE_NAME, values, COLUMNS[0]	+ "	= ?", new String[] { String.valueOf(job.getJobid())});
    }*/

    // Search a job with job id
   /* public Job searchJob(int jobId){
        SQLiteDatabase db = this.getReadableDatabase();
        String jobIdStr = Integer.toString(jobId);
        String[] args = { jobIdStr };
        Cursor cursor = db.query(TABLE_NAME, null, COLUMNS[0] + " =?", args, null, null, null);
        Job job = null;
        if	(cursor.moveToFirst()){
            String title = cursor.getString(1);
            String details = cursor.getString(2);
            String date = cursor.getString(3);
            String time = cursor.getString(4);
            String priority = cursor.getString(5);
            String category = cursor.getString(6);
            job = new Job(jobIdStr, title, details, date, time, Job.Priority.valueOf(priority), Job.Category.valueOf(category));
        }
        cursor.close();
        return job;
    }*/
    public void deleteItem(String id){
        SQLiteDatabase db = this.getWritableDatabase();

        // Add code here
        // Task 3: Delete an existing job corresponding to the given job id
        // Employ the "delete" method with the "db" object with respect to the given job id
        db.execSQL("DELETE FROM "+TABLE_NAME +" WHERE id = '" + id + "'");
    }

    public void deleteMoney(String id){
        SQLiteDatabase db = this.getWritableDatabase();

        // Add code here
        // Task 3: Delete an existing job corresponding to the given job id
        // Employ the "delete" method with the "db" object with respect to the given job id
        db.execSQL("DELETE FROM "+MONEY_TABLE +" WHERE id = '" + id + "'");
    }

    // Delete job with job id
    public void delete(){
        SQLiteDatabase db = this.getWritableDatabase();

        // Add code here
        // Task 3: Delete an existing job corresponding to the given job id
        // Employ the "delete" method with the "db" object with respect to the given job id
        db.delete(TABLE_NAME, null	,null);
        db.delete(MONEY_TABLE, null	,null);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + TABLE_NAME + "'");
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + MONEY_TABLE + "'");
    }

    public ArrayList<HashMap<String, String>> getMonthJob(String month) {
        ArrayList<HashMap<String,String>> spendArray = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] args = { month };
        Cursor cursor = db.query(MONEY_TABLE, null, MONEY_COLUMNS[3] + " LIKE?", args, null, null, null);
        while (cursor.moveToNext()) {
            HashMap<String,String> spend = new HashMap<>();
            String id = cursor.getString(0);
            String type = cursor.getString(1);
            String amount = cursor.getString(2);
            String date = cursor.getString(3);
            String note = cursor.getString(4);
            spend.put("id",id);
            spend.put("type",type);
            spend.put("amount",amount);
            spend.put("date",date);
            spend.put("note",note);
            spendArray.add(spend);
        }
        cursor.close();
        return spendArray;
    }
}
