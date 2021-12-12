package com.example.fitnessapp;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DialogFragment;


import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TimePicker;

import java.util.Calendar;

public class manual_entry extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    private final static String[] entry_options = new String[]{"Date", "Time", "Duration", "Distance", "Calories", "Heart Rate", "Comment"};
    private ListView entryListV;

    Calendar mDateAndTime = Calendar.getInstance();

    FragmentManager fm = this.getFragmentManager();

    // for database
    public static ExerciseEntry entry;
    private exerciseDBHelper exDbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manual_entry);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



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

                    case 2:
                        DialogFragment dialogFragment = selectorDialogFragment.newInstance(selectorDialogFragment.DIALOG_ID_DURATION);
                        dialogFragment.show(getFragmentManager(), getString(R.string.dialog_fragment_tag_duration));
                        break;
                    case 3:
                        DialogFragment distFragment = selectorDialogFragment.newInstance(selectorDialogFragment.DIALOG_ID_DISTANCE);
                        distFragment.show(getFragmentManager(), getString(R.string.dialog_fragment_tag_distance));
                        break;
                    case 4:
                        DialogFragment calFragment = selectorDialogFragment.newInstance(selectorDialogFragment.DIALOG_ID_CALORIES);
                        calFragment.show(getFragmentManager(), getString(R.string.dialog_fragment_tag_calories));
                        break;
                    case 5:
                        DialogFragment heartFragment = selectorDialogFragment.newInstance(selectorDialogFragment.DIALOG_ID_HEART_RATE);
                        heartFragment.show(getFragmentManager(), getString(R.string.dialog_fragment_tag_heart_rate));
                        break;
                    case 6:
                        DialogFragment commFragment = selectorDialogFragment.newInstance(selectorDialogFragment.DIALOG_ID_COMMENT);
                        commFragment.show(getFragmentManager(), getString(R.string.dialog_fragment_tag_comment));
                        break;
                }
                //Log.d("durationSet",""+ entry.getmDuration());
            }
        });


        final Button cancelBtn=(Button) findViewById(R.id.cancelButton);
        cancelBtn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(manual_entry.this, main_activity.class);
                startActivity(intent);
            }
        });

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater= getMenuInflater();
//        inflater.inflate(R.menu.save_btn, menu);
//        return true;
//    }

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
