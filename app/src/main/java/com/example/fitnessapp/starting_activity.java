package com.example.fitnessapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.android.material.tabs.TabLayout;

public class starting_activity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    FragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starting_activity);

        SharedPreferences sh_prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor pref_editor = sh_prefs.edit();

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Start"));
        tabLayout.addTab(tabLayout.newTab().setText("History"));
        tabLayout.addTab(tabLayout.newTab().setText("Settings"));

        viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new FragmentAdapter(getSupportFragmentManager(),
                tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


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
