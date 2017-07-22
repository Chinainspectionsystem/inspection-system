package com.ice.edupatrol;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lecho.lib.hellocharts.animation.ChartAnimationListener;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AnalyzeFragment extends Fragment {
    String choice;
    String choice2;
    Spinner spinner;
    Spinner spinner2;
    Button button;
    Button button2;
    EditText text;
    MyRecevier receiver;
    lecho.lib.hellocharts.view.LineChartView lineChart;
    lecho.lib.hellocharts.view.ColumnChartView columnChartView;
    LineChartData chartData;
    String[] data={"10","50","50","30","40","70","82","100"};
    String[] data2={"10","50","50","60","40","80","82","100"};
    String[] data3={"计算机","电气","材料","纺织","自动化","艺术","文法","研究生"};
    static int LineChartNums = 8;
    int numberOfLines = 1;
    int maxNumberOfLines = 4;
    boolean isCubic = false;
    public AnalyzeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_analyze, container, false);
        spinner= (Spinner) view.findViewById(R.id.items);
        spinner2= (Spinner) view.findViewById(R.id.items2);
        button= (Button) view.findViewById(R.id.search1);
        button2= (Button) view.findViewById(R.id.search2);
        text= (EditText) view.findViewById(R.id.text);
        lineChart = (LineChartView) view.findViewById(R.id.chart);
        columnChartView= (ColumnChartView) view.findViewById(R.id.chart2);
        setnew();
        String[] data_list=new String[]{"     教师","     学院","     课程"};
        ArrayAdapter<String> array_adapter;
        super.onCreate(savedInstanceState);
        array_adapter=new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_item,data_list);
        array_adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(array_adapter);
        String[] data_list2=new String[]{"   学院排名","   优秀教师","   优秀课程"," 时间段情况"};
        ArrayAdapter<String> array_adapter2;
        super.onCreate(savedInstanceState);
        array_adapter2=new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_item,data_list2);
        array_adapter2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner2.setAdapter(array_adapter2);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                choice=spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                choice2=spinner2.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //个体
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String flag;
                String t_text;
                t_text=text.getText().toString();
                if(t_text.equals("")){
                    Toast.makeText(view.getContext(),"请先输入搜索对象",Toast.LENGTH_SHORT).show();
                }
                else {
                choice=choice.trim();
                switch (choice){
                    case "教师": flag="teacher";
                        break;
                    case "课程": flag="class";
                        break;
                    case "学院":flag="collage";
                        break;
                    default: flag="eorr";
                        Toast.makeText(view.getContext(),choice.trim(),Toast.LENGTH_SHORT).show();
                        break;
                }
                    Intent intent = new Intent(view.getContext(), AnalyzeIntentServic.class);
                    intent.putExtra("choice", flag);
                    intent.putExtra("text", t_text);
                    view.getContext().startService(intent);
            }}
        });
        //整体
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String flag;
                    choice2=choice2.trim();
                    switch (choice2) {
                        case "优秀教师":
                            flag = "teacher";
                            break;
                        case "优秀课程":
                            flag = "class";
                            break;
                        case "学院排名":
                            flag = "collage";
                            break;
                        case "时间段情况":flag="time";
                            break;
                        default:
                            flag = "eorr";
                            break;
                    }
                    Intent intent = new Intent(view.getContext(), AnalyzeIntentServic2.class);
                    intent.putExtra("choice2", flag);
                    view.getContext().startService(intent);
            }
        });

        lineChart.setInteractive(true);//是否可以缩放
        lineChart.setValueSelectionEnabled(true);//设置节点点击后动画
        toggleCubic();

        generateDefaultData(data,data3);
        return view;
    }


    public void setnew()
    {
        //注册广播器
        receiver=new MyRecevier();
        IntentFilter filter=new IntentFilter();
        filter.addAction("com.ice.showmap1");
        filter.addAction("com.ice.showmap2");
       getActivity().registerReceiver(receiver,filter);
    }
    @Override
    public void onDestroyView()
    {
        getActivity().unregisterReceiver(receiver);
        super.onDestroyView();
    }
    public class MyRecevier extends BroadcastReceiver
    {


        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getAction().equals("com.ice.showmap1")){
                String rs = intent.getStringExtra("result");
                if (rs.equals("false")) {
                    Log.e("bean", "s=" + rs);
                    Toast.makeText(getContext(),"请确认搜索内容是否正确",Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.e("bean", "s=" + rs);
                    List<Map<String,Object>> list = JSON.parseObject(rs, new TypeReference<List<Map<String,Object>>>(){});
                    int i=0;
                    for (Map<String, Object> map : list) {
                        for (Map.Entry<String, Object> m : map.entrySet()) {
                            data[i]=m.getValue().toString();
                            Log.e("bean-data", data[i] +"   "+String.valueOf(i));
                            i++;
                        }
                    }
                    if(i<8)
                    {
                        for(;i<8;i++)
                        {
                            data[i]="0";
                        }
                    }
                    AddLineChartDate(data);
                }
            }
            if(intent.getAction().equals("com.ice.showmap2")){
                String rs = intent.getStringExtra("result");

               if (rs.equals("false")) {
                   Log.e("bean", "s=" + rs);
                   Toast.makeText(getContext(),"数据异常",Toast.LENGTH_SHORT).show();
                }
                else {
                   Log.e("bean", "s=" + rs);
                   List<Map<String,Object>> list = JSON.parseObject(rs, new TypeReference<List<Map<String,Object>>>(){});
                   int i=0;
                   for (Map<String, Object> map : list) {
                       for (Map.Entry<String, Object> m : map.entrySet()) {
                           data3[i]=m.getValue().toString();
                           data2[i]=m.getKey().toString();
                           Log.e("bean", data2[i] +"   "+String.valueOf(i));
                           i++;
                       }
                   }
                   if(i<8)
                   {
                       for(;i<8;i++)
                       {
                           data2[i]="0";
                           data3[i]="无";
                       }
                   }
                   generateDefaultData(data2,data3);
                }
            }
        }
    }
    /*折线图*/
    public void AddLineChartDate(String data[]){
        List<Line> lines = new ArrayList<Line>();
        for (int i = 0; i < numberOfLines; i++) {
            List<PointValue> pointValues = new ArrayList<PointValue>();//节点数据结合
            Axis axisY = new Axis();//Y轴属性
            Axis axisX = new Axis();//X轴属性
            axisY.setName("评分");
            axisX.setName("最近8次巡查结果");
            ArrayList<AxisValue> axisValuesY = new ArrayList<AxisValue>();
            ArrayList<AxisValue> axisValuesX = new ArrayList<AxisValue>();
            for (int j = 0; j < LineChartNums; j++) {
                pointValues.add(new PointValue(j,Float.parseFloat(data[j])));//添加节点数据
                axisValuesY.add(new AxisValue(j*10*(i+1)).setLabel(j*10+""));//添加Y轴显示的刻度值
                axisValuesX.add(new AxisValue(j).setLabel("第"+(j+1)+"周"));//添加X轴显示的刻度值
            }
            axisValuesY.add(new AxisValue(8*10*(i+1)).setLabel(8*10+""));
            axisValuesY.add(new AxisValue(9*10*(i+1)).setLabel(9*10+""));
            axisValuesY.add(new AxisValue(10*10*(i+1)).setLabel(10*10+""));
            axisY.setValues(axisValuesY);
            axisX.setValues(axisValuesX);
            axisX.setLineColor(Color.BLACK);//无效果
            axisY.setLineColor(Color.BLACK);//无效果
            axisX.setTextColor(Color.BLACK);//设置X轴文字颜色
            axisY.setTextColor(Color.rgb(62,205,201));//设置Y轴文字颜色
            axisY.setTextSize(10);
            axisX.setTextSize(9);//设置X轴文字大小
            axisX.setTypeface(Typeface.SERIF);//设置文字样式
            axisX.setHasTiltedLabels(false);//设置X轴文字向左旋转45度
            axisX.setHasLines(false);//是否显示X轴网格线
            axisY.setHasLines(true);
            Line line = new Line(pointValues);
            line.setColor(Color.rgb(62,205,201));//设置折线颜色
            line.setStrokeWidth(3);//设置折线宽度
            line.setFilled(true);//设置折线覆盖区域颜色
            line.setCubic(isCubic);//节点之间的过渡
            line.setPointColor(Color.RED);//设置节点颜色
            line.setPointRadius(3);//设置节点半径
            line.setHasLabels(true);//是否显示节点数据
            line.setHasLines(true);//是否显示折线
            line.setHasPoints(true);//是否显示节点
            line.setShape(ValueShape.CIRCLE);//节点图形样式 DIAMOND菱形、SQUARE方形、CIRCLE圆形
            line.setHasLabelsOnlyForSelected(false);//隐藏数据，触摸可以显示
            lines.add(line);//将数据集合添加到线
            chartData = new LineChartData(lines);
            chartData.setAxisYLeft(axisY);//将Y轴属性设置到左边
            chartData.setAxisXBottom(axisX);//将X轴属性设置到底部
            chartData.setBaseValue(20);//设置反向覆盖区域颜色
            chartData.setValueLabelBackgroundAuto(false);//设置数据背景是否跟随节点颜色
            chartData.setValueLabelBackgroundColor(Color.BLUE);//设置数据背景颜色
            chartData.setValueLabelBackgroundEnabled(false);//设置是否有数据背景
            chartData.setValueLabelsTextColor(Color.RED);//设置数据文字颜色
            chartData.setValueLabelTextSize(10);//设置数据文字大小
            chartData.setValueLabelTypeface(Typeface.DEFAULT);//设置数据文字样式
        }
        lineChart.setLineChartData(chartData);//将数据添加到控件中
    }
    private void resetViewport() {
        // Reset viewport height range to (0,100)
        final Viewport v = new Viewport(lineChart.getMaximumViewport());
        v.bottom = 0;
        v.top = 100;
        v.left = 0;
        v.right = LineChartNums - 1;
        lineChart.setMaximumViewport(v);
        lineChart.setCurrentViewport(v);
    }
    private void toggleCubic() {
        isCubic = !isCubic;
        AddLineChartDate(data);
        if (isCubic) {
            final Viewport v = new Viewport(lineChart.getMaximumViewport());
            v.bottom = -5;
            v.top = 105;
            lineChart.setMaximumViewport(v);
            lineChart.setCurrentViewportWithAnimation(v);
        } else {
            // If not cubic restore viewport to (0,100) range.
            final Viewport v = new Viewport(lineChart.getMaximumViewport());
            v.bottom = 0;
            v.top = 100;
            lineChart.setViewportAnimationListener(new ChartAnimationListener() {

                @Override
                public void onAnimationStarted() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onAnimationFinished() {
                    lineChart.setMaximumViewport(v);
                    lineChart.setViewportAnimationListener(null);
                }
            });
            lineChart.setCurrentViewportWithAnimation(v);
        }
    }
    /*柱状图*/
    private void generateDefaultData(String[] data2,String[] data3){

        int numColumns = 8;
        ColumnChartData columnChartData;
        List<Column> columns =new ArrayList<>();
        List<SubcolumnValue>  values;
        for(int i=0;i<numColumns;i++){
            values=new ArrayList<>();
            values.add(new SubcolumnValue(Float.parseFloat(data2[i]), ChartUtils.pickColor()));
            Column column = new Column(values);
            //给每一个柱子表上值
            column.setHasLabels(true);
            columns.add(column);

        }
        columnChartData = new ColumnChartData(columns);

        Axis axisBootom = new Axis();
        Axis axisLeft = new Axis();

        List<AxisValue> axisValuess=new ArrayList<>();
        for(int i=0;i<numColumns;i++){
            axisValuess.add(new AxisValue(i).setLabel(data3[i]));
        }
        axisBootom.setValues(axisValuess);
        axisLeft.setName("平均分");
        //加入横线
        axisBootom.setHasLines(true);
        axisLeft.setHasLines(true);
        columnChartData.setAxisXBottom(axisBootom);
        columnChartData.setAxisYLeft(axisLeft);

        columnChartView.setColumnChartData(columnChartData);
    }

    }


