package com.example.printiteasy;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SelectedShopActivity extends AppCompatActivity implements View.OnClickListener{


    private Uri filepath;
    ProgressBar progressBar;
    private StorageReference mStorageRef;
    private static final String TAG = "SelectedShopActivity";
    private static final int PICK_FILE_REQUEST = 23;
    Button selectFile,submit;
    EditText noOfCopies,otherDescription;
    RadioButton bw, color;
    String shop, uri1, strOther, noc,userID;
    FirebaseDatabase database;
    FirebaseUser mUser;





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
        progressBar = findViewById(R.id.progressHorizontal);


        mStorageRef = FirebaseStorage.getInstance().getReference();
        database = FirebaseDatabase.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        //DatabaseReference mRefPreference = database.getReference("users/Preference");
        userID = mUser.getDisplayName();
        Log.d(TAG, "onCreate: displayName="+mUser.getDisplayName());






        Log.d(TAG, "onCreate: Variables initialised");

        selectFile.setOnClickListener(this);
        submit.setOnClickListener(this);



        //to know which shop was selected
        Bundle extras = getIntent().getExtras();
        assert extras != null;
        shop = extras.getString("name");
        Log.d(TAG, "onCreate: shop value received ="+shop);





    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select file"), PICK_FILE_REQUEST);
        }


    private void uploadFile(){

        if(filepath!=null) {
            Log.d(TAG, "uploadFile: file selected");
            if (!shop.matches("Saraswati Gate, MNNIT")){
                progressBar.setVisibility(View.VISIBLE);

                final StorageReference imageRef = mStorageRef.child("/shop1/images/img.jpg");


                imageRef.putFile(filepath)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(SelectedShopActivity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        uri1 = uri.toString();
                                        Log.d(TAG, "onSuccess: download url generated");
                                        DatabaseReference mRefDownload = database.getReference("users/DownloadURLs");
                                        mRefDownload.push().setValue(uri1);

                                    }
                                });


                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(SelectedShopActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })

                ;
            }else if(shop.matches("Saraswati Gate, MNNIT")){
                progressBar.setVisibility(View.VISIBLE);

                final StorageReference imageRef = mStorageRef.child("shop2/images/img.jpg");

                imageRef.putFile(filepath)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(SelectedShopActivity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        uri1 = uri.toString();
                                        Log.d(TAG, "onSuccess: download url generated");
                                        DatabaseReference mRefDownload = database.getReference("users/DownloadURLs");
                                        mRefDownload.push().setValue(uri1);
                                    }
                                });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(SelectedShopActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })

                ;
            }
            }
        else{
            Toast.makeText(this, "Select a File", Toast.LENGTH_SHORT).show();

        }


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            filepath = data.getData();
            Toast.makeText(this, "File Selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v==selectFile){
            showFileChooser();

        }
        else if (v==submit){
            noc = noOfCopies.getText().toString().trim();
            strOther = otherDescription.getText().toString().trim();
            DatabaseReference mRefPreference = database.getReference("users/Preferences/");
            Log.d(TAG, "onCreate: noc="+noc);

            Log.d(TAG, "onClick: strOther ="+strOther);
            if (!shop.matches("Saraswati Gate, MNNIT")){
                //shop1
                if (color.isChecked()){
                    mRefPreference.setValue("Shop1 "+"Color"+"  "+noc+"  "+strOther);
                }else{
                    mRefPreference.setValue("Shop1 "+"B&W"+"  "+noc+"  "+strOther);
                }
            }else if (shop.matches("Saraswati Gate, MNNIT")){
                //shop2
                if (color.isChecked()){
                    mRefPreference.setValue("Shop2 "+"Color  "+noc+"  "+strOther);
                }else{
                    mRefPreference.setValue("Shop2 "+"B&W  "+noc+"  "+strOther);
                }
            }


            uploadFile();



        }
    }
}
