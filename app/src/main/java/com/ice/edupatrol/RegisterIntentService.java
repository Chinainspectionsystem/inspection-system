package com.ice.edupatrol;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;


public class RegisterIntentService extends IntentService {


    public RegisterIntentService() {
        super("RegisterIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String u_name=intent.getStringExtra("u_name");
            String u_pwd=intent.getStringExtra("u_pwd");
            String u_phone=intent.getStringExtra("u_phone");
            String u_sex=intent.getStringExtra("u_sex");
            String u_email=intent.getStringExtra("u_email");
            String u_realname=intent.getStringExtra("u_realname");
            String u_jobid=intent.getStringExtra("u_jobid");
            register(u_name,u_pwd,u_phone,u_sex,u_email,u_jobid,u_realname);
        }
    }

    private void register(String u_name, String u_pwd, String u_phone, String u_sex,String u_email,String u_jobid,String u_realname) {
        final  Intent intent=new Intent("com.ice.register");
        //url
        String url="http://ujitom.55555.io/EduInspectSystem/logic/registerServlet";
        Log.e("bean",url);
        RequestParams params=new RequestParams(url);
        params.addBodyParameter("username",u_name);
        params.addBodyParameter("password",u_pwd);
        params.addBodyParameter("telephone",u_phone);
        params.addBodyParameter("gender",u_sex);
        params.addBodyParameter("email",u_email);
        params.addBodyParameter("teacher_id",u_jobid);
        params.addBodyParameter("realname",u_realname);
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
