package com.example.printiteasy;


import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;



public class Home extends AppCompatActivity {


    //private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    private static final String TAG = "Home";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        ImageButton settings = findViewById(R.id.settings);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
        Log.d(TAG, "onCreate: Login Successful");

    }
    //***************************************************************//





}





