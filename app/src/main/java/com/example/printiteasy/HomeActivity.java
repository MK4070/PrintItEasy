package com.example.printiteasy;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity implements ShopAdapter.OnNoteListener {

    //****16/5//sources>>mSource
    ArrayList<Source> sources;
    String shopName;
    //****16/5

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
                Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
                String email=null;
                intent.putExtra(LoginActivity.userEmail,email);
                startActivity(intent);
            }
        });
        Log.d(TAG, "onCreate: Login Successful");


        //*****************************16/5**********************************//
        RecyclerView rvShops = findViewById(R.id.rvShop);
        LinearLayoutManager horizontalLM = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false);
        sources = Source.createShopList("ShopName_1");
        ShopAdapter adapter = new ShopAdapter(sources,this);
        rvShops.setAdapter(adapter);
        //rvShops.setLayoutManager(new LinearLayoutManager(this));
        rvShops.setLayoutManager(horizontalLM);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("shopName"));

    }
        //******************16/5****************
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
       /* Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();*/
    }

    @Override
    public void onNoteClick(int position) {
        Log.d(TAG, "onNoteClick: clicked");
        Intent intent = new Intent(this, SelectedShopActivity.class);

        intent.putExtra("name", shopName);
        //sources.get(position);
        startActivity(intent);

        Log.d(TAG, "onNoteClick: Started Activity SelectedShop");
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            shopName = intent.getStringExtra("name");


        }
    };

}





