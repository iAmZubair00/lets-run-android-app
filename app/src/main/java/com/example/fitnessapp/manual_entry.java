package com.example.fitnessapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TimePicker;

import java.util.Calendar;

public class manual_entry extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    private final static String[] entry_options = new String[]{"Date", "Time", "Duration", "Distance", "Calories", "Heart Rate", "Comment"};
    private ListView entryListV;

    Calendar mDateAndTime = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manual_entry);

        entryListV = (ListView) findViewById(R.id.entryListView);

        ArrayAdapter<String> entryAdapter = new ArrayAdapter<String>
                (getApplicationContext(), android.R.layout.simple_list_item_1,
                        android.R.id.text1, entry_options);

        entryListV.setAdapter(entryAdapter);

        entryListV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {

                    case 0:
                        new DatePickerDialog(
                                manual_entry.this,
                                manual_entry.this,
                                mDateAndTime.get(Calendar.YEAR),
                                mDateAndTime.get(Calendar.MONTH),
                                mDateAndTime.get(Calendar.DAY_OF_MONTH)
                        ).show();
                        break;

                    case 1:
                        new TimePickerDialog(
                                manual_entry.this,
                                manual_entry.this,
                                mDateAndTime.get(Calendar.HOUR),
                                mDateAndTime.get(Calendar.MINUTE),
                                true
                        ).show();
                        break;
                }
            }
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        mDateAndTime.set(Calendar.YEAR, year);
        mDateAndTime.set(Calendar.MONTH, monthOfYear);
        mDateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        mDateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        mDateAndTime.set(Calendar.MINUTE, minute);
    }
}
