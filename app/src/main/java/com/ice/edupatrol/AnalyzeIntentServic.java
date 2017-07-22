package com.ice.edupatrol;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;


public class AnalyzeIntentServic extends IntentService {


    public AnalyzeIntentServic() {
        super("AnalyzeIntentServic");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String choice=intent.getStringExtra("choice");
            String text=intent.getStringExtra("text");
            showmap1(choice,text);
        }
    }

    private void showmap1(String choice, String text) {
        final  Intent intent=new Intent("com.ice.showmap1");
        String url="http://ujitom.55555.io/EduInspectSystem/logic/getInspectScoreByTeacherServlet";
        Log.e("bean",url);
        RequestParams params=new RequestParams(url);
        params.addBodyParameter("choice",choice);
        params.addBodyParameter("text",text);
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
