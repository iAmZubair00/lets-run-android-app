package com.example.fitnessapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class manual_entry extends AppCompatActivity {

    private final static String[] ENTRIES = new String[]{"Date", "Time", "Duration", "Distance",
            "Calories", "Heart Rate", "Comment"};
    private ListView entryListV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manual_entry);

        entryListV = (ListView) findViewById(R.id.entryListView);

        ArrayAdapter<String> entryAdapter = new ArrayAdapter<String>
                (getApplicationContext(), android.R.layout.simple_list_item_1,
                        android.R.id.text1, ENTRIES);

        entryListV.setAdapter(entryAdapter);



    }
}
