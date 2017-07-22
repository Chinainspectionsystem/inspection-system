package com.ice.edupatrol;

import android.app.IntentService;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;


public class LoginIntentService extends IntentService {


    public LoginIntentService() {
        super("LoginIntentService");
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String u_name=intent.getStringExtra("u_name");
            String u_pwd=intent.getStringExtra("u_pwd");
            login(u_name,u_pwd);
        }
    }

    public void login(String u_name,String u_pwd)
    {
        final  Intent intent=new Intent("com.ice.login");
        String url="http://ujitom.55555.io/EduInspectSystem/logic/loginServlet";
        Log.e("bean",url);
        RequestParams params=new RequestParams(url);
        params.addBodyParameter("username",u_name);
        params.addBodyParameter("password",u_pwd);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                intent.putExtra("result","false");
            }

            @Override
            public void onSuccess(String result) {
                Log.e("bean", "s=" + result);
            intent.putExtra("result",result);
            }
            @Override
            public void onCancelled(CancelledException cex) {
                intent.putExtra("result","false");
            }

            @Override
            public void onFinished() {
                sendBroadcast(intent);
            }
        });

    }
}
