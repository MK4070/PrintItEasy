package com.example.printiteasy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

//import android.annotation.SuppressLint;
//import android.content.Context;
import android.content.Intent;
//import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    Button login;
    TextView singUp;
    private EditText passwordEditText;
    private FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseUser mUser;
    String email,password;
    public static final String userEmail="";
    public static final String TAG="LOGIN";
    private ProgressBar progressBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.login);
        progressBar = findViewById(R.id.progressBar);
        emailEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        singUp = findViewById(R.id.signUp);
        mAuth = FirebaseAuth.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (mUser != null) {
                    Intent intent = new Intent(LoginActivity.this, Home.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                } else {
                    Log.d(TAG, "AuthStateChanged:Logout");
                }
            }
        };



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });
        singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUp.class);
                startActivity(intent);
            }
        });

    }

    private void userLogin(){
        email = emailEditText.getText().toString().trim();
        password = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            //email is empty
            emailEditText.setError("Email is required");
            emailEditText.requestFocus();
            return;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Enter a valid email");
            emailEditText.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)){
            //password is empty
            passwordEditText.setError("Enter your password");
            passwordEditText.requestFocus();
            return;
        }


        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(!task.isSuccessful()){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this, "Check Login Credentials", Toast.LENGTH_SHORT).show();
                        } else{
                            progressBar.setVisibility(View.GONE);
                            checkIfEmailVerified();
                        }
                    }
                });
    }

    private void checkIfEmailVerified() {
        FirebaseUser users= FirebaseAuth.getInstance().getCurrentUser();
        //assert users != null;//*********************************************************************
        boolean emailVerified = users.isEmailVerified();
        if (!emailVerified){
            Toast.makeText(this,"Verify your Email",Toast.LENGTH_SHORT).show();
            mAuth.signOut();
            finish();
        }else{
            emailEditText.getText().clear();
            passwordEditText.getText().clear();
            Intent intent = new Intent(LoginActivity.this, Home.class);
            intent.putExtra(userEmail,email);
            startActivity(intent);

        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.removeAuthStateListener(mAuthListener);
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener!=null){
            mAuth.removeAuthStateListener(mAuthListener);

        }
    }

    @Override
    public void onBackPressed() {
        LoginActivity.super.finish();
    }


    //************************************************//****************************************//
    /*protected void onResume() {
        super.onResume();
        // Checking for user session
        // if user is already logged in, take him to main activity
        if (pref.isLoggedIn()) {
            //here, pref is the instance of your preference manager
            Intent intent = new Intent(LoginActivity.this, Home.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

            finish();
        }

    }

    public static class PrefManager {
        // Shared Preferences
        SharedPreferences pref;

        // Editor for Shared preferences
        SharedPreferences.Editor editor;

        // Context
        Context _context;

        // Shared pref mode
        int PRIVATE_MODE = 0;

        // Shared preferences file name
        private static final String PREF_NAME = "YourAppName";

        // All Shared Preferences Keys
        private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
        private static final String KEY_NAME = "name";
        private static final String KEY_PICTURE = "picture";
        private static final String KEY_MOBILE = "mobile";

        //initializing sharedPreferences
        @SuppressLint("CommitPrefEdits")
        public PrefManager(Context context) {
            this._context = context;
            pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
            editor = pref.edit();
        }

        public void setMobileNumber(String mobileNumber) {
            editor.putString(KEY_MOBILE, mobileNumber);
            editor.commit();
        }

        public void setName(String name) {
            editor.putString(KEY_NAME, name);
            editor.commit();
        }

        public void setPicture(String picture) {
            editor.putString(KEY_PICTURE, picture);
            editor.commit();
        }

        public String getMobileNumber() {
            return pref.getString(KEY_MOBILE, null);
        }

        //Logging in user and setting the name and profile picture
        public void createLogin(final String mobile) {

            //here, handle the mobile number or email or any details that you
            //use for the login. Then do this:

            editor.putBoolean(KEY_IS_LOGGED_IN, true);
            editor.commit();
        }

        public boolean isLoggedIn() {
            return pref.getBoolean(KEY_IS_LOGGED_IN, false);//false is the default value in case there's nothing found with the key
        }

        public void clearSession() {
            editor.clear();
            editor.commit();
        }
    }*/



}
