package com.example.printiteasy;

//import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

//import static java.lang.Integer.parseInt;

public class SelectedShopActivity extends AppCompatActivity implements View.OnClickListener{

    private Uri filepath;
    ProgressBar progressBar;
    private StorageReference mStorageRef;
    private static final String TAG = "SelectedShopActivity";
    private static final int PICK_IMAGE_REQUEST = 23;
    Button selectFile,submit;
    EditText noOfCopies,otherDescription;
    RadioButton bw, color;
    TextView estimatedCost;
    /*int state = 0;*/
    String noc,rate;
    //private ArrayList<Source> mSource;
    private ArrayList rateBlack, rateColor;

    public ArrayList getRateBlack() {
        return rateBlack;
    }

    public ArrayList getRateColor() {
        return rateColor;
    }
    /*public SelectedShopActivity(ArrayList<Source> mSource) {
        this.mSource = mSource;
    }*/

    //@SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectedshop);
        selectFile = findViewById(R.id.fileSelectButton);
        submit = findViewById(R.id.submitButton);
        noOfCopies = findViewById(R.id.editTextNoc);
        otherDescription = findViewById(R.id.otherDescription);
        bw = findViewById(R.id.checkBoxBw);
        color = findViewById(R.id.checkBoxColor);
        estimatedCost = findViewById(R.id.estimatedCost);
        progressBar = findViewById(R.id.progressHorizontal);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        Log.d(TAG, "onCreate: Variables initialised");

        selectFile.setOnClickListener(this);
        submit.setOnClickListener(this);
        Log.d(TAG, "onCreate: button clicked");




        Log.d(TAG, "onCreate: rate extracted");
        //estimatedCost.setText(Integer.toString(computeCost()));
        Log.d(TAG, "onCreate: cost computed");
    }

    private void showFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select file"),PICK_IMAGE_REQUEST);
    }

    private void uploadFile(){

        if(filepath!=null) {

            progressBar.setVisibility(View.VISIBLE);

            StorageReference riversRef = mStorageRef.child("shop1/images/rivers.jpg");

            riversRef.putFile(filepath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(SelectedShopActivity.this, "File Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(SelectedShopActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                           // double progress = (100.0* taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                        }
                    })

            ;
        }else{

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            filepath = data.getData();
            Toast.makeText(this, "Image Selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v==selectFile){
            showFileChooser();

        }
        else if (v==submit){
            uploadFile();
        }
    }



    /*private int computeCost(){
        noc = noOfCopies.getText().toString().trim();
        return parseInt(noc)*parseInt(rate);


    }*/
}
