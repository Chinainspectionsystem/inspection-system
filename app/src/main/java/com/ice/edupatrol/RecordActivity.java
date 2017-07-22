package com.ice.edupatrol;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;
import java.util.Map;

public class RecordActivity extends AppCompatActivity {
ListView listView;
    String[] arrl=new String[]{"1    ","2    ","3    ","4    ","5    ","6    ","7    ","8    ","9    ","10    "};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        setCustomActionBar(getSupportActionBar());
        listView= (ListView) findViewById(R.id.listView);
        String url = "http://ujitom.55555.io/EduInspectSystem/logic/getAllInspectRecordByPageServlet";
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("username", ((MyApplication)getApplication()).getName());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                //Toast.makeText(RecordActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String result) {
                if (result.equals("false")) {
                    Toast.makeText(RecordActivity.this,"目前还没有巡查记录",Toast.LENGTH_SHORT).show();
                    //arrl[0]="目前还没有巡查记录";
                } else {
                 int i=0;
                    List<Map<String,Object>> list = JSON.parseObject(result, new TypeReference<List<Map<String,Object>>>(){});
                    for (Map<String, Object> map : list) {
                        for (Map.Entry<String, Object> m : map.entrySet()) {
                            arrl[i]+=m.getKey().toString()+": ";
                            arrl[i]+=m.getValue().toString()+"           ";
                        }
                        i++;
                    }
                   // Toast.makeText(RecordActivity.this,arr[0],Toast.LENGTH_SHORT).show();
                }


            }
            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {

                ArrayAdapter adapter = new ArrayAdapter(RecordActivity.this, android.R.layout.simple_list_item_1, arrl);
                listView.setAdapter(adapter);
            }
        });

    }



    private  void  setCustomActionBar(ActionBar actionBar){
        ActionBar.LayoutParams lp=new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        View mActionBarView= LayoutInflater.from(this).inflate(R.layout.actionbar_record,null);
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
