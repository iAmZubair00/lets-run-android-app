package com.example.fitnessapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText passField;
    private EditText emailField;
    String email;
    String password;
    String email_pref;
    String password_pref;

    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sh_prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor pref_editor = sh_prefs.edit();
        email_pref= sh_prefs.getString("email","");
        password_pref=sh_prefs.getString("password","");
        if(!email_pref.isEmpty() && !password_pref.isEmpty()){
            Intent intent = new Intent(MainActivity.this, starting_activity.class);
            startActivity(intent);
        }


        DB = new DBHelper(this);

        final RelativeLayout regBtn=(RelativeLayout) findViewById(R.id.signin_registerBtn);
        regBtn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, signup.class);
                startActivity(intent);
            }
        });

        emailField = (EditText) findViewById(R.id.signin_email);
        passField = (EditText) findViewById(R.id.signin_password);

        final RelativeLayout loginBtn=(RelativeLayout) findViewById(R.id.signin_loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                email=emailField.getText().toString();
                password =passField.getText().toString();

                Cursor userData = DB.getdata();
                userData.moveToFirst();
                //Log.d("saved email=",userData.getString(0));
                //Log.d("saved password=",userData.getString(1));
                try{
                    if(userData.getCount()==0){
                        Toast.makeText(MainActivity.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        boolean found=false;
                        while (userData.moveToNext()) {
                            /*Log.d("email in database:",userData.getString(0).getClass().getSimpleName());

                            Log.d("email entered:",email);
                            Log.d("password in database:",userData.getString(1));
                            Log.d("password entered:",password);*/
                            if(email.equals(userData.getString(1)) && password.equals(userData.getString(0))){
                                Toast.makeText(getApplicationContext(),"you successfully logged In",Toast.LENGTH_LONG).show();
                                found=true;
                                //Log.d("matched:","everything");

                                if(email_pref.isEmpty() && password_pref.isEmpty()){
                                    pref_editor.putString("email", email);
                                    pref_editor.putString("password", password);
                                    pref_editor.apply();
                                }

                                Intent intent = new Intent(MainActivity.this, starting_activity.class);
                                startActivity(intent);

                            }
                        }
                        if(!found){
                            Toast.makeText(getApplicationContext(),"wrong credentials",Toast.LENGTH_LONG).show();
                        }

                    }
                }
                catch (Exception e){
                    Log.d("Error", e.getMessage());
                }




                //Toast.makeText(getApplicationContext(),email,Toast.LENGTH_LONG).show();

            }

        });
    }
}

