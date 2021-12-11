package com.example.fitnessapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class userDBHelper extends SQLiteOpenHelper {

    // Important table information
    public static final String TABLE_NAME = "user";
    private static final String log_title = "Storing in Database";

    // Keys for table
    public static final String KEY_ROWID = "_id";
    public static final String KEY_NAME = "input_type";
    public static final String KEY_EMAIL = "activity_type";
    public static final String KEY_PASSWORD = "date_time";
    public static final String KEY_PHONE = "duration";
    public static final String KEY_ISMALE = "ismale";


    // Database creation SQL statement
    public static final String CREATE_TABLE_ENTRIES = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME + " ("
            + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_NAME + " STRING NOT NULL, "
            + KEY_EMAIL + " STRING NOT NULL, "
            + KEY_PASSWORD + " STRING NOT NULL, "
            + KEY_PHONE + " STRING, "
            + KEY_ISMALE + " BOOLEAN " + ");";

    // Constructor
    public userDBHelper(Context context) {
        super(context, "entry.db", null, 1);
    }

    // Create table schema if not exists
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ENTRIES);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(userDBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    // Insert a item given each column value
    public long insertEntry(User entry) {

        // Insert all the values
        ContentValues values = new ContentValues();
        values.put(KEY_NAME,entry.getName());
        values.put(KEY_EMAIL,entry.getEmail());
        values.put(KEY_PASSWORD, entry.getPassword());
        values.put(KEY_PHONE,entry.getPhone());
        values.put(KEY_ISMALE,entry.getIsMale());

        // Insert to database
        SQLiteDatabase database = getWritableDatabase();
        long insertId = database.insert(TABLE_NAME, null, values);
        database.close();
        return insertId;
    }


    private User cursorToUserEntry(Cursor cursor) {

        User entry = new User();
        entry.setId(cursor.getLong(0));
        entry.setName(cursor.getString(1));
        entry.setEmail(cursor.getString(2));
        entry.setPassword(cursor.getString(3));
        entry.setPhone(cursor.getString(4));
        entry.setIsMale((cursor.getInt(5)) > 0);
        return entry;
    }

    public ArrayList<User> fetchUsers() {

        ArrayList<User> entries = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();

        Cursor cursor = database.query(TABLE_NAME,
                null, null, null, null, null, null);

        cursor.moveToFirst();

        // Fetch the entries one by one until cursor reaches the end
        while (!cursor.isAfterLast()) {
            User entry = cursorToUserEntry(cursor);
            Log.d(log_title, cursorToUserEntry(cursor).toString());
            entries.add(entry);
            cursor.moveToNext();
        }

        cursor.close();
        database.close();

        return entries;
    }

    public User fetchUserByIndex(long rowId) {
        SQLiteDatabase database = getReadableDatabase();

        Cursor cursor = database.query(TABLE_NAME, null,
                KEY_ROWID + " = " + rowId, null, null, null, null);
        cursor.moveToFirst();
        User entry = cursorToUserEntry(cursor);
        Log.d(log_title, cursorToUserEntry(cursor).toString());

        cursor.close();
        database.close();

        return entry;
    }


}

