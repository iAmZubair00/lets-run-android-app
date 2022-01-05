package com.example.fitnessapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class exerciseDBHelper extends SQLiteOpenHelper {

    // Important table information
    public static final String TABLE_NAME = "exercise";
    private static final String log_title = "Storing in Database";

    // Keys for table
    public static final String KEY_ROWID = "_id";
    public static final String KEY_INPUT_TYPE = "input_type";
    public static final String KEY_ACTIVITY_TYPE = "activity_type";
    public static final String KEY_DATE_TIME = "date_time";
    public static final String KEY_DURATION = "duration";
    public static final String KEY_DISTANCE = "distance";
    public static final String KEY_CALORIES = "calories";
    public static final String KEY_HEARTRATE = "heartrate";
    public static final String KEY_COMMENT = "comment";


    // Database creation SQL statement
    public static final String CREATE_TABLE_ENTRIES = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME + " ("
            + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_INPUT_TYPE + " INTEGER NOT NULL, "
            + KEY_ACTIVITY_TYPE + " INTEGER NOT NULL, "
            + KEY_DATE_TIME + " LONG NOT NULL, "
            + KEY_DURATION + " DOUBLE, "
            + KEY_DISTANCE + " DOUBLE, "
            + KEY_CALORIES + " INTEGER, "
            + KEY_HEARTRATE + " INTEGER, "
            + KEY_COMMENT + " STRING " + ");";

    // Constructor
    public exerciseDBHelper(Context context) {
        super(context, "exercise.db", null, 1);
    }

    // Create table schema if not exists
    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(CREATE_TABLE_ENTRIES);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(exerciseDBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    // Insert a item given each column value
    public long insertEntry(ExerciseEntry entry) {

        // Insert all the values
        ContentValues values = new ContentValues();
        values.put(KEY_INPUT_TYPE,entry.getmInputType());
        values.put(KEY_ACTIVITY_TYPE,entry.getmActivityType());
        values.put(KEY_DATE_TIME, entry.getmDateTime());
        values.put(KEY_DURATION,entry.getmDuration());
        values.put(KEY_DISTANCE,entry.getmDistance());
        values.put(KEY_CALORIES,entry.getmCalorie());
        values.put(KEY_HEARTRATE,entry.getmHeartRate());
        values.put(KEY_COMMENT,entry.getmComment());


        // Insert to database
        SQLiteDatabase database = getWritableDatabase();
        long insertId = database.insert(TABLE_NAME, null, values);
        database.close();
        return insertId;
    }


    // Remove an entry by giving its index
    public void removeEntry(long rowIndex) {

        SQLiteDatabase database = getWritableDatabase();
        database.delete(TABLE_NAME, KEY_ROWID
                + " = " + rowIndex, null);
        database.close();
    }


    // Query a specific entry by its index.
    public ExerciseEntry fetchEntryByIndex(long rowId) {
        SQLiteDatabase database = getReadableDatabase();

        Cursor cursor = database.query(TABLE_NAME, null,
                KEY_ROWID + " = " + rowId, null, null, null, null);
        cursor.moveToFirst();
        ExerciseEntry entry = cursorToExerciseEntry(cursor);
        Log.d(log_title, cursorToExerciseEntry(cursor).toString());

        cursor.close();
        database.close();

        return entry;
    }


    // Query the entire table, return all rows
    public ArrayList<ExerciseEntry> fetchEntries() {

        ArrayList<ExerciseEntry> entries = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();

        Cursor cursor = database.query(TABLE_NAME,
                null, null, null, null, null, null);

        cursor.moveToFirst();

        // Fetch the entries one by one until cursor reaches the end
         do{
            ExerciseEntry entry = cursorToExerciseEntry(cursor);
            Log.d(log_title, cursorToExerciseEntry(cursor).toString());
            entries.add(entry);
        }while (cursor.moveToNext());

        cursor.close();
        database.close();

        return entries;
    }


    private ExerciseEntry cursorToExerciseEntry(Cursor cursor) {

        ExerciseEntry entry = new ExerciseEntry();
        entry.setmId(cursor.getLong(0));
        entry.setmInputType(cursor.getInt(1));
        entry.setmActivityType(cursor.getInt(2));
        entry.setmDateTime(cursor.getLong(3));
        entry.setmDuration(cursor.getInt(4));
        entry.setmDistance(cursor.getDouble(5));
        entry.setmCalorie(cursor.getInt(6));
        entry.setmHeartRate(cursor.getInt(7));
        entry.setmComment(cursor.getString(8));

        return entry;
    }
}

