package com.ice.edupatrol;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class ChangepwdActivity extends AppCompatActivity {
    Button button;
    EditText opwd;
    EditText pwd;
    EditText pwds;
    MyRecevier receiver;

    private GoogleApiClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepwd);
       setCustomActionBar(getSupportActionBar());
        button= (Button) findViewById(R.id.btn_change);
        opwd= (EditText) findViewById(R.id.oldpwd);
        pwd= (EditText) findViewById(R.id.upwd);
        pwds= (EditText) findViewById(R.id.upwds);
        setRecevier();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname=((MyApplication)getApplication()).getName();
                String txt_opwd=opwd.getText().toString();
                String txt_pwd=pwd.getText().toString();
                String txt_pwds=pwds.getText().toString();
                if(!txt_pwd.equals(txt_pwds))
                {
                    Toast.makeText(ChangepwdActivity.this,"请确定设定密码前后一致",Toast.LENGTH_SHORT);
                }
                Intent intent=new Intent(ChangepwdActivity.this,PasswordService.class);
                intent.putExtra("u_name", uname);
                intent.putExtra("u_pwd", txt_pwd);
                intent.putExtra("u_opwd", txt_opwd);
                startService(intent);
            }
        });
    }

    private  void  setCustomActionBar(ActionBar actionBar){
        ActionBar.LayoutParams lp=new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        View mActionBarView= LayoutInflater.from(this).inflate(R.layout.actionbar_changepwd,null);
        if(actionBar!=null) {
            actionBar.setCustomView(mActionBarView, lp);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setBackgroundDrawable(getDrawable(R.drawable.bg_actionbar2));
        }
    }
    public void setRecevier()
    {
        //注册广播器
        receiver=new MyRecevier();
        IntentFilter filter=new IntentFilter();
        filter.addAction("com.ice.changepassword");
        registerReceiver(receiver,filter);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    public class MyRecevier extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("com.ice.changepassword")){
                String rs = intent.getStringExtra("result");
                if (rs.equals("true")) {
                    Toast.makeText(ChangepwdActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(ChangepwdActivity.this,"修改失败",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
