package com.ice.edupatrol;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class RegisterActivity extends AppCompatActivity {
@ViewInject(R.id.btn_register)
    Button button;
    @ViewInject(R.id.uname)
    EditText uname;
    @ViewInject(R.id.upwd)
    EditText upwd;
    @ViewInject(R.id.upwds)
    EditText upwds;
    @ViewInject(R.id.radioButton2)
    RadioButton usex;
    @ViewInject(R.id.uphone)
    EditText uphone;
    @ViewInject(R.id.realname)
    EditText realname;
    @ViewInject(R.id.jobid)
    EditText jobid;
    @ViewInject(R.id.uemail)
    EditText uemail;
    private RegisterRecevier receiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setCustomActionBar(getSupportActionBar());
        x.view().inject(this);
        setRecevier();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_username=uname.getText().toString();
                String txt_pwd=upwd.getText().toString();
                String txt_sex;
                String txt_phone=uphone.getText().toString();
                String txt_uemail=uemail.getText().toString();
                String txt_realrname=realname.getText().toString();
                String txt_jobid=jobid.getText().toString();
                if(usex.isChecked()) {
                    txt_sex = "男";
                }
                else txt_sex="女";
                if(!txt_pwd.equals(upwds.getText().toString()))
                {
                    Toast.makeText(RegisterActivity.this,"请确定设定密码前后一致",Toast.LENGTH_LONG).show();
                }
                else if(txt_username.equals("")||txt_phone.equals("")||txt_jobid.equals("")||txt_realrname.equals("")||txt_uemail.equals("")){
                    Toast.makeText(RegisterActivity.this,"必填信息不能为空",Toast.LENGTH_LONG).show();
                }
                else {
                Intent intent = new Intent(RegisterActivity.this, RegisterIntentService.class);
                intent.putExtra("u_name", txt_username);
                intent.putExtra("u_pwd", txt_pwd);
                intent.putExtra("u_sex", txt_sex);
                intent.putExtra("u_phone", txt_phone);
                intent.putExtra("u_email", txt_uemail);
                intent.putExtra("u_realname", txt_realrname);
                intent.putExtra("u_jobid", txt_jobid);
                startService(intent);}
            }
        });
    }

    public void setRecevier()
    {
        //注册广播器
        receiver=new RegisterRecevier();
        IntentFilter filter=new IntentFilter();
        filter.addAction("com.ice.register");
        registerReceiver(receiver,filter);
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
    public class RegisterRecevier extends BroadcastReceiver
    {


        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("com.ice.register")){
                String rs = intent.getStringExtra("result");
                if (rs.equals("true")) {
                    Toast.makeText(RegisterActivity.this,"您的申请已提交，等在管理员审核。",Toast.LENGTH_SHORT).show();
                    Intent intent2 = new Intent();
                    intent2.setClass(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent2);
                }
                else {
                    Toast.makeText(RegisterActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private  void  setCustomActionBar(ActionBar actionBar){
        ActionBar.LayoutParams lp=new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        View mActionBarView= LayoutInflater.from(this).inflate(R.layout.actionbar_register,null);
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
