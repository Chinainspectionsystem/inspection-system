package com.ice.edupatrol;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;


public class PersonIntentService extends IntentService {

    public PersonIntentService() {
        super("PersonIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String u_realname=intent.getStringExtra("u_realname");
            String u_phone=intent.getStringExtra("u_phone");
            String u_sex=intent.getStringExtra("u_sex");
            Person(u_realname,u_phone,u_sex);

        }
    }

    private void Person(final String u_realname, final String u_phone, final String u_sex) {
        final  Intent intent=new Intent("com.ice.person");
        //url
        String url="http://ujitom.55555.io/EduSys/logic/ ";
        Log.e("bean",url);
        RequestParams params=new RequestParams(url);
        params.addBodyParameter("realname",u_realname);
        params.addBodyParameter("phone",u_phone);
        params.addBodyParameter("sex",u_sex);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                intent.putExtra("result","false");
            }
            @Override
            public void onSuccess(String result) {
                Log.e("bean", "s=" + result);
                intent.putExtra("result",result);
                MyApplication app=(MyApplication) getApplication();
                app.setRealname(u_realname);
                app.setSex(u_sex);
                app.setPhone(u_phone);
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
