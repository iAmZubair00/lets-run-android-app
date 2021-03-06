package com.example.fitnessapp;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DialogFragment;


import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class manual_entry extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    private final static String[] entry_options = new String[]{"Date", "Time", "Duration", "Distance", "Calories", "Heart Rate", "Comment"};
    private ListView entryListV;

    Calendar mDateAndTime = Calendar.getInstance();

    FragmentManager fm = this.getFragmentManager();

    public static final double MILES2KM = 1.60934;

    // for database
    ExerciseEntry entry= new ExerciseEntry();
    public static exerciseDBHelper exDbHelper;

    public static final String[] ID_TO_ACTIVITY = {"Running", "Walking", "Standing",
            "Cycling", "Hiking", "Downhill Skiing"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manual_entry);
        Bundle bundle = getIntent().getExtras();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button btnSave =(Button) findViewById(R.id.saveButton);
        btnSave.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                entry.setmDateTime(mDateAndTime.getTimeInMillis());
                Log.w("entry is:",entry.toString());
                saveEntryToDb(entry);
                Toast.makeText(manual_entry.this, "data inserted", Toast.LENGTH_SHORT).show();
            }
        });

        exDbHelper =new exerciseDBHelper(this);

        entry.setmInputType(bundle.getInt("input_type",0));
        entry.setmActivityType(bundle.getInt("activity_type", 0));

        TextView activityView=(TextView) findViewById(R.id.activityShow);
        activityView.setText(ID_TO_ACTIVITY[entry.getmActivityType()]);


//        entryListV = (ListView) findViewById(R.id.entryListView);
//
//        ArrayAdapter<String> entryAdapter = new ArrayAdapter<String>
//                (getApplicationContext(), android.R.layout.simple_list_item_1,
//                        android.R.id.text1, entry_options);
//
//        entryListV.setAdapter(entryAdapter);
//
//        entryListV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                switch (position) {
//
//                    case 0:
//                        new DatePickerDialog(
//                                manual_entry.this,
//                                manual_entry.this,
//                                mDateAndTime.get(Calendar.YEAR),
//                                mDateAndTime.get(Calendar.MONTH),
//                                mDateAndTime.get(Calendar.DAY_OF_MONTH)
//                        ).show();
//                        break;
//
//                    case 1:
//                        new TimePickerDialog(
//                                manual_entry.this,
//                                manual_entry.this,
//                                mDateAndTime.get(Calendar.HOUR),
//                                mDateAndTime.get(Calendar.MINUTE),
//                                true
//                        ).show();
//                        break;
//
//                    case 2:
//                        DialogFragment dialogFragment = selectorDialogFragment.newInstance(selectorDialogFragment.DIALOG_ID_DURATION);
//                        dialogFragment.show(getFragmentManager(), getString(R.string.dialog_fragment_tag_duration));
//                        break;
//                    case 3:
//                        DialogFragment distFragment = selectorDialogFragment.newInstance(selectorDialogFragment.DIALOG_ID_DISTANCE);
//                        distFragment.show(getFragmentManager(), getString(R.string.dialog_fragment_tag_distance));
//                        break;
//                    case 4:
//                        DialogFragment calFragment = selectorDialogFragment.newInstance(selectorDialogFragment.DIALOG_ID_CALORIES);
//                        calFragment.show(getFragmentManager(), getString(R.string.dialog_fragment_tag_calories));
//                        break;
//                    case 5:
//                        DialogFragment heartFragment = selectorDialogFragment.newInstance(selectorDialogFragment.DIALOG_ID_HEART_RATE);
//                        heartFragment.show(getFragmentManager(), getString(R.string.dialog_fragment_tag_heart_rate));
//                        break;
//                    case 6:
//                        DialogFragment commFragment = selectorDialogFragment.newInstance(selectorDialogFragment.DIALOG_ID_COMMENT);
//                        commFragment.show(getFragmentManager(), getString(R.string.dialog_fragment_tag_comment));
//                        break;
//                }
//                //Log.d("durationSet",""+ entry.getmDuration());
//            }
//        });
//
//
//        final Button cancelBtn=(Button) findViewById(R.id.cancelButton);
//        cancelBtn.setOnClickListener(new View.OnClickListener()
//        {
//
//            @Override
//            public void onClick(View v)
//            {
//                Intent intent = new Intent(manual_entry.this, main_activity.class);
//                startActivity(intent);
//            }
//        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.save_btn, menu);
        return true;
    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        mDateAndTime.set(Calendar.YEAR, year);
        mDateAndTime.set(Calendar.MONTH, monthOfYear);
        mDateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        TextView dateView=(TextView) findViewById(R.id.dateShow);
        dateView.setText(format.format(mDateAndTime.getTime()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        mDateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        mDateAndTime.set(Calendar.MINUTE, minute);
        SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
        TextView timeView=(TextView) findViewById(R.id.timeShow);
        timeView.setText(format.format(mDateAndTime.getTime()));
    }


    // for testing

    public void handleDateSelect(View v){
        new DatePickerDialog(
                manual_entry.this,
                manual_entry.this,
                mDateAndTime.get(Calendar.YEAR),
                mDateAndTime.get(Calendar.MONTH),
                mDateAndTime.get(Calendar.DAY_OF_MONTH)
        ).show();
    }
    public void handleTimeSelect(View v){
        new TimePickerDialog(
                manual_entry.this,
                manual_entry.this,
                mDateAndTime.get(Calendar.HOUR),
                mDateAndTime.get(Calendar.MINUTE),
                true
        ).show();
    }

    public void handleDurationSelect(View v){
        DialogFragment dialogFragment = selectorDialogFragment.newInstance(selectorDialogFragment.DIALOG_ID_DURATION);
        dialogFragment.show(getFragmentManager(), getString(R.string.dialog_fragment_tag_duration));
        TextView durationView=(TextView) findViewById(R.id.durationShow);
        Log.w("duration is:",Double.toString(entry.getmDuration()));
        durationView.setText(entry.getmDuration()==0? "": Double.toString(entry.getmDuration()));
    }

    public void handleDistanceSelect(View v){
        DialogFragment distFragment = selectorDialogFragment.newInstance(selectorDialogFragment.DIALOG_ID_DISTANCE);
        distFragment.show(getFragmentManager(), getString(R.string.dialog_fragment_tag_distance));
        TextView distanceView=(TextView) findViewById(R.id.distanceShow);
        distanceView.setText(entry.getmDistance()==0? "": Double.toString(entry.getmDistance()));
    }

    public void handleCaloriesSelect(View v){
        DialogFragment calFragment = selectorDialogFragment.newInstance(selectorDialogFragment.DIALOG_ID_CALORIES);
        calFragment.show(getFragmentManager(), getString(R.string.dialog_fragment_tag_calories));
        TextView calorieView=(TextView) findViewById(R.id.calorieShow);
        calorieView.setText(entry.getmCalorie()==0? "": Integer.toString(entry.getmCalorie()));
    }

    public void handleHeartSelect(View v){
        DialogFragment heartFragment = selectorDialogFragment.newInstance(selectorDialogFragment.DIALOG_ID_HEART_RATE);
        heartFragment.show(getFragmentManager(), getString(R.string.dialog_fragment_tag_heart_rate));
        TextView heartView=(TextView) findViewById(R.id.heartShow);
        heartView.setText(entry.getmHeartRate()==0? "": Integer.toString(entry.getmHeartRate()));
    }

    public void handleCommentSelect(View v){
        DialogFragment commFragment = selectorDialogFragment.newInstance(selectorDialogFragment.DIALOG_ID_COMMENT);
        commFragment.show(getFragmentManager(), getString(R.string.dialog_fragment_tag_comment));
        TextView calorieView=(TextView) findViewById(R.id.commentShow);
        calorieView.setText(entry.getmComment());
    }

    private void saveEntryToDb(ExerciseEntry entry){

        //Log.i("email is",email);
        //Log.i("password is",password);
        long inserted = exDbHelper.insertEntry(entry);
        if(inserted!=-1){
            Toast.makeText(manual_entry.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();

        }
        else{
            Toast.makeText(manual_entry.this, "New Entry Not Inserted", Toast.LENGTH_SHORT).show();
        }
    }

}
