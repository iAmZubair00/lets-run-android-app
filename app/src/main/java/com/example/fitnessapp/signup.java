package com.example.fitnessapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;


public class signup extends AppCompatActivity {

    private EditText passField;
    private EditText emailField;
    private ImageView imgView;
    private Button changeBtn;
    private Button goToLoginBtn;
    String email;
    String password;
    String email_pref;
    String password_pref;

    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);



        emailField = (EditText) findViewById(R.id.signup_email);
        passField = (EditText) findViewById(R.id.signup_password);

        imgView = (ImageView) findViewById(R.id.signup_imageView);
        changeBtn = (Button) findViewById(R.id.signup_camerabtn);

        DB = new DBHelper(this);

        final Button regBtn=(Button) findViewById(R.id.signup_registerBtn);
        regBtn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                saveUserInfo();

                Intent intent = new Intent(signup.this, MainActivity.class);
                startActivity(intent);

            }
        });

        goToLoginBtn=(Button) findViewById(R.id.signup_gotoLoginBtn);
        goToLoginBtn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(signup.this, MainActivity.class);
                startActivity(intent);
            }
        });

        changeBtn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                ImagePicker.Companion.with(signup.this)
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

    private void saveUserInfo(){

        SharedPreferences sh_prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor pref_editor = sh_prefs.edit();
        email_pref= sh_prefs.getString("email","");
        password_pref=sh_prefs.getString("password","");

        email=emailField.getText().toString();
        password =passField.getText().toString();




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
    }

}