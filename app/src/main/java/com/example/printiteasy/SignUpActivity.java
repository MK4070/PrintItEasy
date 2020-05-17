package com.example.printiteasy;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.text.TextUtils;
import android.util.Patterns;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.Objects;


public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

     Button singUpButton;
     EditText usernameEditText, fullNameEditText;
     EditText passwordEditText;
     TextView loginTv;
     ProgressBar progressBar;
     FirebaseAuth mAuth;
     DatabaseReference mDatabase;
     String name, email, password;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        mAuth = FirebaseAuth.getInstance();


        if(mAuth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(this, HomeActivity.class));
        }


        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        fullNameEditText = findViewById(R.id.fullName);
        progressBar =  findViewById(R.id.progressBar);
        singUpButton = findViewById(R.id.signUpButton);
        loginTv = findViewById(R.id.login_tv);
        singUpButton.setOnClickListener(this);
        loginTv.setOnClickListener(this);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

    }
    private void registerUser(){
        email = usernameEditText.getText().toString().trim();
        name = fullNameEditText.getText().toString().trim();
        password = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(name)){
            Toast.makeText(this, "Enter name", Toast.LENGTH_SHORT).show();
            return;
        }

        else if (email.isEmpty()){
            //email is empty
            usernameEditText.setError("Email is required");
            usernameEditText.requestFocus();
            return;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            usernameEditText.setError("Enter a valid email address");
            usernameEditText.requestFocus();
            return;
        }

        else if (password.isEmpty()){
            //password is empty
            passwordEditText.setError("Password is required");
            passwordEditText.requestFocus();
            return;
        }
        else if (password.length()<6){
            passwordEditText.setError("Minimum length should be 6 characters");
            passwordEditText.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()){
                    sendEmailVerification();
                    OnAuth(Objects.requireNonNull(task.getResult().getUser()));//********************
                    mAuth.signOut();

                }
                else{
                    Toast.makeText(getApplicationContext(), "Registration Unsuccessful", Toast.LENGTH_SHORT).show();

                }            }
        });




    }
    private void sendEmailVerification(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Check your Email for verification",Toast.LENGTH_LONG).show();
                        FirebaseAuth.getInstance().signOut();
                    }                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        if(v==singUpButton){
            registerUser();
        }
        else if (v==loginTv){
            //Login activity
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

    }
    private void OnAuth(FirebaseUser user) {
        createAnewUser(user.getUid());
    }

    private void createAnewUser(String uid) {
         User user = BuildNewUser();
        mDatabase.child(uid).setValue(user);
    }


    private User BuildNewUser(){
        return new User(
                getDisplayName(),
                getUserEmail(),
                new Date().getTime()
        );
    }

    public String getDisplayName() {
        return name;
    }

    public String getUserEmail() {
        return email;
    }


}
