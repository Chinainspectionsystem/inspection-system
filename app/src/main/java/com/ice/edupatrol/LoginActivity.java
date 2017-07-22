package com.ice.edupatrol;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class LoginActivity extends AppCompatActivity {
    private Tencent mTencent;
    private BaseUiListener mIUiListener;
    private UserInfo mInfo;
    private String APPID="1105692541";
    EditText username;
    EditText pwd;
    CheckBox remeberPwd;
    CheckBox showPwd;
    Button btn_login;
    SharedPreferences sp;
    ImageView imageView;
    TextView txt_numresult;
    Button btn_register;
    ImageButton QQ_login;
    ProgressDialog pd;
    //服务器地址
    String url5 = "http://ujitom.55555.io/EduSys/logic/loginServlet";
    private MyRegisterRecevier receiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //设置ActionBar
        setActionBar setActionBar = new setActionBar();
        setActionBar.setActionBar2(getSupportActionBar(), getResources());
        //初始化
        mTencent = Tencent.createInstance(APPID,LoginActivity.this.getApplication());
        username = (EditText) findViewById(R.id.txt_username);
        pwd = (EditText) findViewById(R.id.txt_pwd);
        remeberPwd = (CheckBox) findViewById(R.id.chkBox_remeberPwd);
        showPwd = (CheckBox) findViewById(R.id.chkBox_showPwd);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register = (Button) findViewById(R.id.register);
        QQ_login= (ImageButton) findViewById(R.id.qq);
        //修改本机设置Flag
        sp = getSharedPreferences("MyFlag", Context.MODE_PRIVATE);
        String remeberPwdFlag = sp.getString("remeberPwdFlag", "0");
        if (remeberPwdFlag.equals("1")) {
            String rmbUsername = sp.getString("remeberUsername", "");
            String rmbPwd = sp.getString("remeberPwd", "");
            username.setText(rmbUsername);
            pwd.setText(rmbPwd);
            remeberPwd.setChecked(true);
        } else {
            remeberPwd.setChecked(false);
        }
        setRecevier();
        //QQ_Login
        QQ_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIUiListener = new BaseUiListener();
                mTencent.login(LoginActivity.this,"all", mIUiListener);
            }
        });
        //单击登录事件
        showPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showPwd.isChecked()) {
                    pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String txt_username = username.getText().toString();
                String txt_pwd = pwd.getText().toString();
                /*Intent intent=new Intent();
                intent.setClass(LoginActivity.this,MainActivity.class);
                startActivity(intent);
*/
              Intent intent = new Intent(LoginActivity.this, LoginIntentService.class);
                intent.putExtra("u_name", txt_username);
                intent.putExtra("u_pwd", txt_pwd);
                startService(intent);
                pd=new ProgressDialog(LoginActivity.this);
                pd.setTitle("正在登陆");
                pd.setMessage("登录中");
                pd.setCancelable(true);
                pd.show();
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

        public void setRecevier()
        {
            //注册广播器
            receiver=new MyRegisterRecevier();
            IntentFilter filter=new IntentFilter();
            filter.addAction("com.ice.login");
            registerReceiver(receiver,filter);

        }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
        public class MyRegisterRecevier extends BroadcastReceiver{
            @Override
            public void onReceive(Context context, Intent intent) {
            //    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                if(intent.getAction().equals("com.ice.login")) {
                    String rs = intent.getStringExtra("result");
                    if (rs.equals("管理员")||rs.equals("巡查员")) {
                        if (remeberPwd.isChecked()) {
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("remeberPwdFlag", "1");
                            editor.putString("remeberUsername", username.getText().toString());
                            editor.putString("remeberPwd", pwd.getText().toString());
                            editor.commit();
                        } else {
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("remeberPwdFlag", "0");
                            editor.putString("remeberPwd", "");
                            editor.putString("remeberUsername", "");
                            editor.commit();
                        }
                        //记住密码
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("current_username", username.getText().toString());
                        editor.commit();
                        pd.dismiss();
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        ((MyApplication) getApplication()).setName(username.getText().toString());
                        Intent intent2 = new Intent();
                        intent2.setClass(LoginActivity.this, MainActivity.class);
                        startActivity(intent2);
                    } else {
                        pd.dismiss();
                        Toast.makeText(LoginActivity.this, "用户名密码或者权限错误！", Toast.LENGTH_SHORT).show();
                        remeberPwd.setChecked(false);
                    }
                    } else {
                    pd.dismiss();
                    Toast.makeText(LoginActivity.this, "登录失败！", Toast.LENGTH_SHORT).show();
                    }
                }}




    private class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(Object response) {
            Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
            Log.e("tag", "response:" + response);
            JSONObject jo = (JSONObject) response;
            Intent intent=new Intent();
            intent.setClass(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            try {
                String openID = jo.getString("openid");
                String accessToken = jo.getString("access_token");
                String expires = jo.getString("expires_in");
                mTencent.setOpenId(openID);
                mTencent.setAccessToken(accessToken, expires);
                QQToken qqToken = mTencent.getQQToken();
                mInfo = new UserInfo(getApplicationContext(), qqToken);
                mInfo.getUserInfo(new IUiListener() {
                    @Override
                    public void onComplete(Object response) {
                        Log.e("BaseUiListener", "成功"+response.toString());
                    }

                    @Override
                    public void onError(UiError uiError) {
                        Log.e("BaseUiListener", "失败"+uiError.toString());
                    }

                    @Override
                    public void onCancel() {
                        Log.e("BaseUiListener", "取消");
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(UiError uiError) {
            Toast.makeText(getApplicationContext(), "登录失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel() {
            Toast.makeText(getApplicationContext(), "登录取消", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("TAG", "-->onActivityResult " + requestCode  + " resultCode=" + resultCode);
        if (requestCode == Constants.REQUEST_LOGIN ||
                requestCode == Constants.REQUEST_APPBAR) {
            Tencent.onActivityResultData(requestCode,resultCode,data,mIUiListener);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


}
