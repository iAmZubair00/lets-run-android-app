package com.example.fitnessapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class Login extends AppCompatActivity {

    private EditText passField;
    private EditText emailField;
    String email;
    String password;
    String email_pref;
    String password_pref;

    userDBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        SharedPreferences sh_prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor pref_editor = sh_prefs.edit();
        String email_pref= sh_prefs.getString("email","");
        String password_pref=sh_prefs.getString("password","");
        long userId=sh_prefs.getLong("userId",0);
        if(!email_pref.isEmpty() && !password_pref.isEmpty() && userId!=0){
            Intent intent = new Intent(Login.this, starting_activity.class);
            startActivity(intent);
        }


        DB = new userDBHelper(this);

        final RelativeLayout regBtn=(RelativeLayout) findViewById(R.id.signin_registerBtn);
        regBtn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Login.this, profile.class);
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

                ArrayList<User> allUsers= DB.fetchUsers();

                //Log.d("saved email=",userData.getString(0));
                //Log.d("saved password=",userData.getString(1));
                try{
                    if(allUsers.isEmpty()){
                        Toast.makeText(Login.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        boolean found=false;
                        for (User user: allUsers) {
                            /*Log.d("email in database:",userData.getString(0).getClass().getSimpleName());

                            Log.d("email entered:",email);
                            Log.d("password in database:",userData.getString(1));
                            Log.d("password entered:",password);*/
                            if(email.equals(user.getEmail()) && password.equals(user.getPassword())){
                                Toast.makeText(getApplicationContext(),"you successfully logged In",Toast.LENGTH_LONG).show();
                                found=true;
                                //Log.d("matched:","everything");

                                if(email_pref.isEmpty() && password_pref.isEmpty() && userId==0){
                                    pref_editor.putLong("userId", user.getId());
                                    pref_editor.putString("email", user.getEmail());
                                    pref_editor.putString("password", user.getPassword());
                                    pref_editor.apply();
                                }

                                Intent intent = new Intent(Login.this, starting_activity.class);
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

