package com.example.printiteasy;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;

public class SettingsActivity extends AppCompatActivity{
    String EmailHolder; TextView Email;
    FirebaseAuth mAuth;
    //FirebaseAuth.AuthStateListener mAuthListener;
    //FirebaseUser mUser;
    public static final String TAG = "LOGIN";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //TextView email = findViewById(R.id.username);
        Button buttonLogOut = findViewById(R.id.buttonLogout);

        /*Intent intent = getIntent();
        EmailHolder = intent.getStringExtra(LoginActivity.userEmail);
        Email.setText(Email.getText().toString()+EmailHolder);*/

        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                FirebaseAuth.getInstance().signOut();
                Log.d(TAG, "onClick: LoggedOUT");
                Toast.makeText(SettingsActivity.this, "LogOut Successful", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(SettingsActivity.this,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });

    }
}
