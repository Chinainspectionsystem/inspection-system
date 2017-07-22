package com.ice.edupatrol;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


/**
 * A simple {@link Fragment} subclass.
 */
public class SelectFragment extends Fragment {
    EditText s_sleep;
    EditText s_phone;
    EditText s_absent;
    EditText  others;
    RatingBar t_score;
    RatingBar s_score;
    SeekBar s_seek;
    SeekBar t_seek;
    TextView teacher;
    TextView student;
    Button button;
    public SelectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_select, container, false);
        s_sleep= (EditText) view.findViewById(R.id.number2);
        s_phone= (EditText) view.findViewById(R.id.number3);
        s_absent= (EditText) view.findViewById(R.id.number1);
        others= (EditText) view.findViewById(R.id.number4);
        s_seek= (SeekBar) view.findViewById(R.id.seekBar2);
        t_seek= (SeekBar) view.findViewById(R.id.seekBar);
        s_score= (RatingBar) view.findViewById(R.id.ratingBar_s);
        t_score= (RatingBar) view.findViewById(R.id.ratingBar_t);
        teacher= (TextView) view.findViewById(R.id.score_t);
        student= (TextView) view.findViewById(R.id.score_s);
        button= (Button) view.findViewById(R.id.btn_send);
        s_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b){
                student.setText(String.valueOf(i));
            }


            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        t_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b){
                teacher.setText(String.valueOf(i));
            }


            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String num_absent=s_absent.getText().toString();
                String num_sleep=s_sleep.getText().toString();
                String num_phone=s_phone.getText().toString();
                String teacherscore1=teacher.getText().toString();
                String studentscore1=student.getText().toString();
                String teacherscore2=String.valueOf(t_score.getRating());
                String studentscore2=String.valueOf(s_score.getRating());
                String other=others.getText().toString();
                String url = "http://ujitom.55555.io/EduInspectSystem/logic/addInspectRecordServlet";
                RequestParams params = new RequestParams(url);
                params.addBodyParameter("teaching_time",((MainActivity)getActivity()).getMyteaching_time());
                params.addBodyParameter("username",((MainActivity)getActivity()).getMyusername());
                params.addBodyParameter("teaching_id",((MainActivity)getActivity()).getMyteaching_id());
                params.addBodyParameter("num_absent",num_absent);
                params.addBodyParameter("num_sleep",num_sleep);
                params.addBodyParameter("num_phone",num_phone);
                params.addBodyParameter("teacherscore1",teacherscore1);
                params.addBodyParameter("studentscore1",studentscore1);
                params.addBodyParameter("teacherscore2",teacherscore2);
                params.addBodyParameter("studentscore2",studentscore2);
                params.addBodyParameter("other",other);
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Toast.makeText(view.getContext(),"网络连接失败",Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onSuccess(String result) {
                    if(result.equals("true"))
                    {
                Toast.makeText(getActivity(),"提交成功",Toast.LENGTH_SHORT).show();}
                        else       Toast.makeText(getActivity(),"提交失败",Toast.LENGTH_SHORT).show();
            }
                    public void onCancelled(CancelledException cex) {
                    }
                    @Override
                    public void onFinished() {
                    }
                });}
        });
        return view;
    }

}
