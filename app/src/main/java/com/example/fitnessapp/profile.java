package com.example.fitnessapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;


public class profile extends AppCompatActivity {

    private ImageView imgView;
    private Button changeBtn;
    private Button goToLoginBtn;

    userDBHelper DB;
    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EditText emailField = (EditText) findViewById(R.id.signup_email);
        EditText passField = (EditText) findViewById(R.id.signup_password);
        EditText nameField =(EditText) findViewById(R.id.signup_name);
        EditText phoneField = (EditText) findViewById(R.id.signup_phone);
        RadioButton rbField = (RadioButton) findViewById(R.id.signup_maleBtn);

        imgView = (ImageView) findViewById(R.id.signup_imageView);
        changeBtn = (Button) findViewById(R.id.signup_camerabtn);

        DB = new userDBHelper(this);

        SharedPreferences sh_prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor pref_editor = sh_prefs.edit();
        String email_pref= sh_prefs.getString("email","");
        String password_pref=sh_prefs.getString("password","");
        long userId=sh_prefs.getLong("userId",0);

        if(!email_pref.isEmpty() && !password_pref.isEmpty() && userId!=0){
            loadUserInfo(userId);
        }


        final Button regBtn=(Button) findViewById(R.id.signup_registerBtn);
        regBtn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                user.setName(nameField.getText().toString());
                user.setEmail(emailField.getText().toString());
                user.setPassword(passField.getText().toString());
                user.setPhone(phoneField.getText().toString());
                user.setIsMale(rbField.isChecked());
                saveUserInfo(user);

                Intent intent = new Intent(profile.this, Login.class);
                startActivity(intent);

            }
        });

        final Button editBtn=(Button) findViewById(R.id.signup_editBtn);
        editBtn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                user.setId(userId);
                user.setName(nameField.getText().toString());
                user.setEmail(emailField.getText().toString());
                user.setPassword(passField.getText().toString());
                user.setPhone(phoneField.getText().toString());
                user.setIsMale(rbField.isChecked());
                editUserInfo(user);

                Intent intent = new Intent(profile.this, Login.class);
                startActivity(intent);

            }
        });

        goToLoginBtn=(Button) findViewById(R.id.signup_gotoLoginBtn);
        goToLoginBtn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(profile.this, Login.class);
                startActivity(intent);
            }
        });

        changeBtn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                ImagePicker.Companion.with(profile.this)
                       /* .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)*/
                        .start();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri uri=data.getData();
        imgView.setImageURI(uri);
    }

    //Method to show "save" button in app bar by inflating the save_btn.xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        SharedPreferences sh_prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor pref_editor = sh_prefs.edit();
        String email_pref= sh_prefs.getString("email","");
        String password_pref=sh_prefs.getString("password","");
        long userId=sh_prefs.getLong("userId",0);

        if(!email_pref.isEmpty() && !password_pref.isEmpty() && userId!=0){
            MenuInflater inflater= getMenuInflater();
            inflater.inflate(R.menu.save_btn, menu);
        }

        return true;
    }

    private void saveUserInfo(User user){

        SharedPreferences sh_prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor pref_editor = sh_prefs.edit();
        String email_pref= sh_prefs.getString("email","");
        String password_pref=sh_prefs.getString("password","");
        long userId=sh_prefs.getLong("userId",0);

        //Log.i("email is",email);
        //Log.i("password is",password);
        long inserted = DB.insertUser(user);
        if(inserted!=-1){
            Toast.makeText(profile.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();

            if(email_pref.isEmpty() && password_pref.isEmpty() && userId==0){
                pref_editor.putLong("userId", user.getId());
                pref_editor.putString("email", user.getEmail());
                pref_editor.putString("password", user.getPassword());
                pref_editor.apply();
            }
        }
        else{
            Toast.makeText(profile.this, "New Entry Not Inserted", Toast.LENGTH_SHORT).show();
        }
    }

    private void editUserInfo(User user){

        SharedPreferences sh_prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor pref_editor = sh_prefs.edit();
        String email_pref= sh_prefs.getString("email","");
        String password_pref=sh_prefs.getString("password","");
        long userId=sh_prefs.getLong("userId",0);

        //Log.i("email is",email);
        //Log.i("password is",password);
        long updated = DB.updateUser(user);
        if(updated!=-1){
            Toast.makeText(profile.this, "Entry updated", Toast.LENGTH_SHORT).show();

            if(email_pref.isEmpty() && password_pref.isEmpty() && userId==0){
                pref_editor.putLong("userId", user.getId());
                pref_editor.putString("email", user.getEmail());
                pref_editor.putString("password", user.getPassword());
                pref_editor.apply();
            }
        }
        else{
            Toast.makeText(profile.this, "Entry Not updated", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadUserInfo(long userId){

        User thisUser =DB.fetchUserByIndex(userId);

        EditText nameField = (EditText) findViewById(R.id.signup_name);
        nameField.setText(thisUser.getName());

        EditText emailField = (EditText) findViewById(R.id.signup_email);
        emailField.setText(thisUser.getEmail());

        EditText passwordField = (EditText) findViewById(R.id.signup_password);
        passwordField.setText(thisUser.getPassword());

        EditText phoneField = (EditText) findViewById(R.id.signup_phone);
        phoneField.setText(thisUser.getPhone());

        RadioGroup rg = (RadioGroup) findViewById(R.id.rgGender);

        if(thisUser.getIsMale()){
            Log.d("getIsmale is","true");
            rg.check(R.id.signup_maleBtn);
        }else{
            Log.d("getIsmale is","false");
            rg.check(R.id.signup_femaleBtn);
        }

    }


}