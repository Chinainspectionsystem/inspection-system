package com.ice.edupatrol;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;
import java.util.Map;


public class AnalyzeIntentServic2 extends IntentService {

    public AnalyzeIntentServic2() {
        super("AnalyzeIntentServic2");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String choice2 = intent.getStringExtra("choice2");
            showmap2(choice2);
        }
    }
    private void showmap2(String choice2) {
        final Intent intent = new Intent("com.ice.showmap2");
        String url = "http://ujitom.55555.io/EduInspectSystem/logic/getInspectAverageScoreForAndriodServlet";
        Log.e("bean", url);
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("choice", choice2);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                intent.putExtra("result", "false");
            }

            @Override
            public void onSuccess(String result) {
                Log.e("bean", "s=" + result);
                intent.putExtra("result",result);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                intent.putExtra("result", "false");
            }

            @Override
            public void onFinished() {

                sendBroadcast(intent);
            }
        });
    }
}