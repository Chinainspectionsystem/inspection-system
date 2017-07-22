package com.ice.edupatrol;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FlashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);
        setActionBar setActionBar=new setActionBar();
        setActionBar.setActionBar2(getSupportActionBar(),getResources());
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences=   getSharedPreferences("myFlag", Context.MODE_PRIVATE);
                String str= sharedPreferences.getString("flag","0");
                Intent intent = new Intent();
                if(str.equals("0"))
                    intent.setClass(FlashActivity.this, GuideActivity.class);
                else
                    intent.setClass(FlashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}
