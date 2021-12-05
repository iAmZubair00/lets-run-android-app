package com.example.fitnessapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class signup extends AppCompatActivity {

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
        setContentView(R.layout.signup);

        SharedPreferences sh_prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor pref_editor = sh_prefs.edit();
        email_pref= sh_prefs.getString("email","");
        password_pref=sh_prefs.getString("password","");

        emailField = (EditText) findViewById(R.id.signup_email);
        passField = (EditText) findViewById(R.id.signup_password);

        DB = new DBHelper(this);

        final RelativeLayout regBtn=(RelativeLayout) findViewById(R.id.signup_registerBtn);
        regBtn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                email=emailField.getText().toString();
                password =passField.getText().toString();

                SharedPreferences.Editor pref_editor = sh_prefs.edit();




                //Log.i("email is",email);
                //Log.i("password is",password);
                Boolean checkinsertdata = DB.insertuserdata(email, password);
                if(checkinsertdata==true){
                    Toast.makeText(signup.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();

                    if(email_pref.isEmpty() && password_pref.isEmpty()){
                        pref_editor.putString("email", email);
                        pref_editor.putString("password", password);
                        pref_editor.apply();
                    }
                }
                else{
                    Toast.makeText(signup.this, "New Entry Not Inserted", Toast.LENGTH_SHORT).show();
                }


                Intent intent = new Intent(signup.this, MainActivity.class);
                startActivity(intent);

            }
        });



    }


}