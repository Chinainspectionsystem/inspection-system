package com.ice.edupatrol;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.ice.edupatrol.view.GestureLockView;

public class GestureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture);
        setCustomActionBar(getSupportActionBar());
        GestureLockView gestureLockView = (GestureLockView) findViewById(R.id.gestureView);
        gestureLockView.setGestureListener(new GestureLockView.GestureListener() {
            @Override
            public boolean getGesture(String gestureCode) {
                if (0 == gestureCode.length()) return true;
                if (gestureCode.equals("1478")) {
                    Toast.makeText(GestureActivity.this, "解锁成功", Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent();
                    intent.setClass(GestureActivity.this,MainActivity.class);
                    startActivity(intent);
                    return true;
                }
                //Toast.makeText(GestureActivity.this, gestureCode + "!=1478", Toast.LENGTH_SHORT).show();
                Toast.makeText(GestureActivity.this, "手势不正确，请重试", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
    private  void  setCustomActionBar(ActionBar actionBar){
        ActionBar.LayoutParams lp=new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        View mActionBarView= LayoutInflater.from(this).inflate(R.layout.actionbar_gesture,null);
        if(actionBar!=null) {
            actionBar.setCustomView(mActionBarView, lp);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setBackgroundDrawable(getDrawable(R.drawable.bg_actionbar2));
        }
    }
}
