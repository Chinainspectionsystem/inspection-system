package com.ice.edupatrol;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment {
TextView textView;
    ListView listView;

    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view=inflater.inflate(R.layout.fragment_message, container, false);
        textView= (TextView) view.findViewById(R.id.text);
        listView= (ListView) view.findViewById(R.id.listView);

        String url = "http://ujitom.55555.io/EduInspectSystem/logic/getTeachingByInspectServlet";
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("username", ((MainActivity)getActivity()).getMyusername());
       /* x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
               // Toast.makeText(view.getContext(), "网络连接失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String result) {
                String stringrecord;
                if (result.equals("false")) {

                    stringrecord="目前还没有巡查内容";
                } else {
                    stringrecord=result;
                }
                textView.setText(stringrecord);
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });

*/
        return view;
    }




}
