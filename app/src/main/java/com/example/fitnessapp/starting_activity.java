package com.example.fitnessapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class starting_activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starting_activity);

        SharedPreferences sh_prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor pref_editor = sh_prefs.edit();

        final Button signOutBtn=(Button) findViewById(R.id.signoutBtn);
        signOutBtn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                pref_editor.clear();
                pref_editor.apply();

                Intent intent = new Intent(starting_activity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        final Button manEntr_Btn=(Button) findViewById(R.id.manual_entry_Btn);
        manEntr_Btn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(starting_activity.this, manual_entry.class);
                startActivity(intent);
            }
        });
    }
}
