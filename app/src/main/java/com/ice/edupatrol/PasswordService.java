package com.ice.edupatrol;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;


public class PasswordService extends IntentService {


    public PasswordService() {
        super("PasswordService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String u_name=intent.getStringExtra("u_name");
            String u_pwd=intent.getStringExtra("u_pwd");
            String u_opwd=intent.getStringExtra("u_opwd");
            Changepassword(u_name,u_opwd,u_pwd);

        }
    }

    private void Changepassword(String u_name, String u_opwd, String u_pwd) {
        final  Intent intent=new Intent("com.ice.changepassword");
       String url="http://ujitom.55555.io/EduInspectSystem/logic/updatePasswordServlet";
        Log.e("bean",url);
        RequestParams params=new RequestParams(url);
        params.addBodyParameter("newpassword",u_pwd);
        params.addBodyParameter("oldpassword",u_opwd);
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



