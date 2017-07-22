package com.ice.edupatrol;


import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class FindFragment extends Fragment {
    Button select;
    Button search;
    TextView textView;
    ListView listView;
    Spinner spinner;
    Spinner spinner2;
    Spinner class_time;
    Spinner college;
    TextView classroom;
    String teachingid = "授课号：";
    String teachername = "教师姓名：";
    String studentnumber = "学生人数：";
    String classname = "课程名： ";
    String classtime = "开课时间：";
    String otherinfo = "其他信息：";
    String eorr = "此教室还没有安排课";
    String txt_collage;
    String txt_classtime;
    int i=0;
    public FindFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_find, container, false);
        super.onCreate(savedInstanceState);
        search= (Button) view.findViewById(R.id.btn_search);
        select= (Button) view.findViewById(R.id.btn_select);
        spinner= (Spinner)view.findViewById(R.id.collage);
        spinner2= (Spinner)view.findViewById(R.id.class_time);
        classroom=(TextView)view.findViewById(R.id.classroom);
        listView= (ListView) view.findViewById(R.id.listView);
        listView.setDivider(null);
        String[] data_list=new String[]{"计算机","理学院","机械","纺织","艺术","电气","经管","外语","文法"};
        ArrayAdapter<String> array_adapter;
        array_adapter=new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_item,data_list);
        array_adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(array_adapter);
        String[] data_list2=new String[]{"第一节","第二节","第三节","第四节","第五节"};
        ArrayAdapter<String> array_adapter2;
        super.onCreate(savedInstanceState);
        array_adapter2=new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_item,data_list2);
        array_adapter2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner2.setAdapter(array_adapter2);
        select.setVisibility(View.GONE);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                txt_collage=spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Date d = new Date();
        int hour = d.getHours();
        if(hour<10) i=0;
        else if(hour>10&&hour<12)i=1;
        else if(hour>12&&hour<16)i=2;
        else if(hour>16&&hour<18)i=3;
        else i=4;
        spinner2.setSelection(i,true);
        txt_classtime=spinner2.getSelectedItem().toString();
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                txt_classtime=spinner2.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        search.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(final View view) {
                                          if(classroom.getText().toString().equals(""))
                                          {
                                              Toast.makeText(view.getContext(),"填写格式不正确",Toast.LENGTH_SHORT).show();
                                          }else {
                                              //String url="http://jiping.imwork.net/TJPUWeb/searchclass";
                                              String url = "http://ujitom.55555.io/EduInspectSystem/logic/getTeachingByInspectServlet";
                                              RequestParams params = new RequestParams(url);
                                              params.addBodyParameter("classroom", classroom.getText().toString());
                                              params.addBodyParameter("collage", txt_collage);
                                              params.addBodyParameter("classtime",txt_classtime);
                                              x.http().post(params, new Callback.CommonCallback<String>() {
                                                  @Override
                                                  public void onError(Throwable ex, boolean isOnCallback) {
                                                      Toast.makeText(view.getContext(), "网络连接失败", Toast.LENGTH_SHORT).show();
                                                  }

                                                  @Override
                                                  public void onSuccess(String result) {
                                                      String arr[];
                                                      if (result.equals("false")) {
                                                          Toast.makeText(view.getContext(),"该教室没有排课",Toast.LENGTH_SHORT).show();
                                                      } else {
                                                          ((MainActivity)getActivity()).setMyteaching_id(JSON.parseObject(result).get("teaching_id").toString());
                                                          ((MainActivity)getActivity()).setMyteaching_time(txt_classtime);
                                                          String teachingid1=teachingid+JSON.parseObject(result).get("teaching_id").toString();
                                                          String teachername1 =teachername+ JSON.parseObject(result).get("teacher_name").toString();
                                                          String studentnumber1 =studentnumber+ JSON.parseObject(result).get("student_population").toString();
                                                          String classtime1= classname+JSON.parseObject(result).get("teaching_time").toString();
                                                          String  otherinfo1 = otherinfo+JSON.parseObject(result).get("teaching_remark").toString();
                                                          String classname1= classtime+JSON.parseObject(result).get("course_name").toString();
                                                          arr = new String[]{teachingid1,teachername1, studentnumber1, classname1, classtime1, otherinfo1};
                                                          select.setVisibility(View.VISIBLE);
                                                          search.setVisibility(View.GONE);
                                                          ArrayAdapter adapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_list_item_1, arr);
                                                          listView.setAdapter(adapter);

                                                      }

                                                  }
                                                  @Override
                                                  public void onCancelled(CancelledException cex) {
                                                  }

                                                  @Override
                                                  public void onFinished() {
                                                  }
                                              });

                                          }
            }
        });

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm=getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.mainPanel,new SelectFragment()).addToBackStack(null).commit();
            }
        });
        return view;
    }


}
