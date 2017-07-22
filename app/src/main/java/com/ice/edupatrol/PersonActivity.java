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

public class PersonActivity extends AppCompatActivity {
    Button button;
    EditText realname;
    EditText phone;
    RadioButton sex;
    MyRecevier receiver;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        setCustomActionBar(getSupportActionBar());
        realname = (EditText) findViewById(R.id.realname);
        phone = (EditText) findViewById(R.id.uphone);
        sex = (RadioButton) findViewById(R.id.man);
        button = (Button) findViewById(R.id.btn_sure);
        init();
        setnew();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_realname = realname.getText().toString();
                String txt_phone = phone.getText().toString();
                String txt_sex = "女";
                if (sex.isChecked()) txt_sex = "男";
                Intent intent=new Intent(PersonActivity.this,PersonIntentService.class);
                intent.putExtra("u_realname", txt_realname);
                intent.putExtra("u_phone", txt_phone);
                intent.putExtra("u_sex", txt_sex);
                startService(intent);
               // Toast.makeText(PersonActivity.this,((MyApplication)getApplication()).getName(),Toast.LENGTH_SHORT).show();
            }
        });



    }
public  void init()
{
    MyApplication app=(MyApplication) getApplication();
    realname.setText(app.getRealname());
    phone.setText(app.getPhone());
    if(app.getSex().equals("男"))
        sex.setChecked(true);
    else sex.setChecked(false);
}
    public void setnew()
    {
        //注册广播器
        receiver=new MyRecevier();
        IntentFilter filter=new IntentFilter();
        filter.addAction("com.ice.person");
        registerReceiver(receiver,filter);
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
    public class MyRecevier extends BroadcastReceiver
    {


        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getAction().equals("com.ice.person")){
                String rs = intent.getStringExtra("result");
                if (rs.equals("true")) {
                    Toast.makeText(PersonActivity.this,"修改成功",Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(PersonActivity.this,"修改失败",Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    private  void  setCustomActionBar(ActionBar actionBar){
        ActionBar.LayoutParams lp=new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        View mActionBarView= LayoutInflater.from(this).inflate(R.layout.actionbar_person,null);
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
