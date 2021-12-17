package com.CAU.helmetrev_new;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;

public class GraphOldActivity extends AppCompatActivity {
    private BluetoothSPP bt;
    private List<Float> pot_values = new ArrayList<>();
    private TextView graph_label, graph_date, nodata_label;

    private static final String TAG = "MainActivity";
    private LineChart mChart;
    private Button btn_confirm;
    private Thread thread;
    private int mFillColor = Color.argb(150,51,181,229);
    private Toolbar toolbar;
    private ImageButton btn_record, btn_setting, btn_support, btn_user, btn_info,btn_calendar,btn_right, btn_left, btn_gohome;
    private final int[] CH =new int[51];
    private final double[] av = new double[51];
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphold);

        btn_user = findViewById(R.id.btn_user);
        btn_info = findViewById(R.id.btn_info);
        btn_record = findViewById(R.id.btn_record);
        btn_support = findViewById(R.id.btn_support);
        btn_calendar = findViewById(R.id.btn_calendar);
        graph_label = findViewById(R.id.graph_label);
        btn_right= findViewById(R.id.btn_right);
        btn_left = findViewById(R.id.btn_left);
        btn_confirm = findViewById(R.id.btn_confirm);
        nodata_label= (TextView) findViewById(R.id.nodata_label);
        btn_gohome = findViewById(R.id.btn_gohome);
        //graph_date= findViewById(R.id.graph_date);
        ArrayList<Entry> entry1 = new ArrayList<>();
        ArrayList<Entry> entry2 = new ArrayList<>();
        Intent intent = getIntent();
        final String userName = intent.getStringExtra("userName");
        final String userID = intent.getStringExtra("userID");
        final int machineID= intent.getIntExtra("machineID", 8888);
        final int[] k = {0,1};

        final String machineId = String.valueOf(machineID);
        final String[] move = new String[1];
        final String inputDate;
        Intent dateIntent = getIntent();
        inputDate = dateIntent.getStringExtra("yymmdd");
        graph_label.setText(inputDate);
        nodata_label.setVisibility(View.INVISIBLE);

        preferences =  PreferenceManager.getDefaultSharedPreferences(this);

        editor = preferences.edit();

        String startDate = preferences.getString("startdate","2021-02-01");
        String endDate = preferences.getString("enddate","2021-12-06");

        final String[] hz = new String[1];
        btn_gohome.setVisibility(View.GONE);

        hz[0]="CH0";



        btn_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GraphOldActivity.this, DiaryActivity.class);
                intent.putExtra("userID",userID);
                intent.putExtra("userName",userName);
                intent.putExtra("machineID",machineID);
                startActivity(intent);
            }
        });
        btn_support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GraphOldActivity.this, SupportActivity.class);
                intent.putExtra("userID",userID);
                intent.putExtra("userName",userName);
                intent.putExtra("machineID",machineID);
                startActivity(intent);
            }
        });
        btn_user.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GraphOldActivity.this, FixInfoActivity.class);
                intent.putExtra("userID",userID);
                intent.putExtra("userName",userName);
                intent.putExtra("machineID",machineID);
                startActivity(intent);
            }
        } );
        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GraphOldActivity.this, UserActivity.class);
                intent.putExtra("userID",userID);
                intent.putExtra("userName",userName);
                intent.putExtra("machineID",machineID);
                startActivity(intent);
            }
        });
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GraphOldActivity.this, DetailGraphActivity.class);
                intent.putExtra("channel",hz[0]);
                intent.putExtra("userID",userID);
                intent.putExtra("userName",userName);
                intent.putExtra("machineID",machineID);
                intent.putExtra("inputdate",inputDate);
                startActivity(intent);
            }
        });
        btn_gohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GraphOldActivity.this, HomeActivity.class);
                intent.putExtra("userID",userID);
                intent.putExtra("userName",userName);
                intent.putExtra("machineID",machineID);
                startActivity(intent);
            }
        });

        move[0] ="0";


        final AnyChartView anyChartView =  findViewById(R.id.any_chart_view);
        final Cartesian cartesian = AnyChart.line();

        cartesian.animation(true);

        cartesian.padding(10d, 40d, 5d, 20d);

        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                // TODO ystroke
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.xScroller(true);
        cartesian.xZoom(true);
        cartesian.annotations().horizontalLine("average").valueAnchor("400");
        cartesian.annotations().verticalLine(move[0]).xAnchor(move[0]);
        cartesian.annotations().verticalLine(move[0]).enabled(false);


        //cartesian.title("Trend of Sales of the Most Popular Products of ACME Corp.");

        //cartesian.yAxis(0).title("Number of Bottles Sold (thousands)");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

        final List<DataEntry> seriesData = new ArrayList<>();

        Response.Listener<String> responseListener = new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    final int cnt = jsonObject.getInt("cnt");
                    final int row = jsonObject.getInt("row");
                    final int CH0 = jsonObject.getInt("CH0");
                    final int CH1 = jsonObject.getInt("CH1");
                    final int CH2 = jsonObject.getInt("CH2");
                    final int CH3 = jsonObject.getInt("CH3");
                    final int CH4 = jsonObject.getInt("CH4");
                    final int CH5 = jsonObject.getInt("CH5");
                    final int CH6 = jsonObject.getInt("CH6");
                    final int CH7 = jsonObject.getInt("CH7");
                    final int CH8 = jsonObject.getInt("CH8");
                    final int CH9 = jsonObject.getInt("CH9");
                    final int CH10 = jsonObject.getInt("CH10");
                    final int CH11 = jsonObject.getInt("CH11");
                    final int CH12 = jsonObject.getInt("CH12");
                    final int CH13 = jsonObject.getInt("CH13");
                    final int CH14 = jsonObject.getInt("CH14");
                    final int CH15 = jsonObject.getInt("CH15");
                    final int CH16 = jsonObject.getInt("CH16");
                    final int CH17 = jsonObject.getInt("CH17");
                    final int CH18 = jsonObject.getInt("CH18");
                    final int CH19 = jsonObject.getInt("CH19");
                    final int CH20 = jsonObject.getInt("CH20");
                    final int CH21 = jsonObject.getInt("CH21");
                    final int CH22 = jsonObject.getInt("CH22");
                    final int CH23 = jsonObject.getInt("CH23");
                    final int CH24 = jsonObject.getInt("CH24");
                    final int CH25 = jsonObject.getInt("CH25");
                    final int CH26 = jsonObject.getInt("CH26");
                    final int CH27 = jsonObject.getInt("CH27");
                    final int CH28 = jsonObject.getInt("CH28");
                    final int CH29 = jsonObject.getInt("CH29");
                    final int CH30 = jsonObject.getInt("CH30");
                    final int CH31 = jsonObject.getInt("CH31");
                    final int CH32 = jsonObject.getInt("CH32");
                    final int CH33 = jsonObject.getInt("CH33");
                    final int CH34 = jsonObject.getInt("CH34");
                    final int CH35 = jsonObject.getInt("CH35");
                    final int CH36 = jsonObject.getInt("CH36");
                    final int CH37 = jsonObject.getInt("CH37");
                    final int CH38 = jsonObject.getInt("CH38");
                    final int CH39 = jsonObject.getInt("CH39");
                    final int CH40 = jsonObject.getInt("CH40");
                    final int CH41 = jsonObject.getInt("CH41");
                    final int CH42 = jsonObject.getInt("CH42");
                    final int CH43 = jsonObject.getInt("CH43");
                    final int CH44 = jsonObject.getInt("CH44");
                    final int CH45 = jsonObject.getInt("CH45");
                    final int CH46 = jsonObject.getInt("CH46");
                    final int CH47 = jsonObject.getInt("CH47");
                    final int CH48 = jsonObject.getInt("CH48");
                    final int CH49 = jsonObject.getInt("CH49");
                    final int CH50 = jsonObject.getInt("CH50");

                    final int av0 = jsonObject.getInt("av0");
                    final int av1 = jsonObject.getInt("av1");
                    final int av2 = jsonObject.getInt("av2");
                    final int av3 = jsonObject.getInt("av3");
                    final int av4 = jsonObject.getInt("av4");
                    final int av5 = jsonObject.getInt("av5");
                    final int av6 = jsonObject.getInt("av6");
                    final int av7 = jsonObject.getInt("av7");
                    final int av8 = jsonObject.getInt("av8");
                    final int av9 = jsonObject.getInt("av9");
                    final int av10 = jsonObject.getInt("av10");
                    final int av11 = jsonObject.getInt("av11");
                    final int av12 = jsonObject.getInt("av12");
                    final int av13 = jsonObject.getInt("av13");
                    final int av14 = jsonObject.getInt("av14");
                    final int av15 = jsonObject.getInt("av15");
                    final int av16 = jsonObject.getInt("av16");
                    final int av17 = jsonObject.getInt("av17");
                    final int av18 = jsonObject.getInt("av18");
                    final int av19 = jsonObject.getInt("av19");
                    final int av20 = jsonObject.getInt("av20");
                    final int av21 = jsonObject.getInt("av21");
                    final int av22 = jsonObject.getInt("av22");
                    final int av23 = jsonObject.getInt("av23");
                    final int av24 = jsonObject.getInt("av24");
                    final int av25 = jsonObject.getInt("av25");
                    final int av26 = jsonObject.getInt("av26");
                    final int av27 = jsonObject.getInt("av27");
                    final int av28 = jsonObject.getInt("av28");
                    final int av29 = jsonObject.getInt("av29");
                    final int av30 = jsonObject.getInt("av30");
                    final int av31 = jsonObject.getInt("av31");
                    final int av32 = jsonObject.getInt("av32");
                    final int av33 = jsonObject.getInt("av33");
                    final int av34 = jsonObject.getInt("av34");
                    final int av35 = jsonObject.getInt("av35");
                    final int av36 = jsonObject.getInt("av36");
                    final int av37 = jsonObject.getInt("av37");
                    final int av38 = jsonObject.getInt("av38");
                    final int av39 = jsonObject.getInt("av39");
                    final int av40 = jsonObject.getInt("av40");
                    final int av41 = jsonObject.getInt("av41");
                    final int av42 = jsonObject.getInt("av42");
                    final int av43 = jsonObject.getInt("av43");
                    final int av44 = jsonObject.getInt("av44");
                    final int av45 = jsonObject.getInt("av45");
                    final int av46 = jsonObject.getInt("av46");
                    final int av47 = jsonObject.getInt("av47");
                    final int av48 = jsonObject.getInt("av48");
                    final int av49 = jsonObject.getInt("av49");
                    final int av50 = jsonObject.getInt("av50");

                    System.out.println("OOOld graph cnt is "+cnt);
                    System.out.println(av1);
                    System.out.println("OOOld graph row is "+row);
                    if(success){
                        CH[0]= CH0; av[0]= av0/row;
                        CH[1]= CH1; av[1]= av1/row;
                        CH[2]= CH2; av[2]= av2/row;
                        CH[3]= CH3; av[3]= av3/row;
                        CH[4]= CH4; av[4]= av4/row;
                        CH[5]= CH5; av[5]= av5/row;
                        CH[6]= CH6; av[6]= av6/row;
                        CH[7]= CH7; av[7]= av7/row;
                        CH[8]= CH8; av[8]= av8/row;
                        CH[9]= CH9; av[9]= av9/row;
                        CH[10]= CH10; av[10]= av10/row;
                        CH[11]= CH11; av[11]= av11/row;
                        CH[12]= CH12; av[12]= av12/row;
                        CH[13]= CH13; av[13]= av13/row;
                        CH[14]= CH14; av[14]= av14/row;
                        CH[15]= CH15; av[15]= av15/row;
                        CH[16]= CH16; av[16]= av16/row;
                        CH[17]= CH17; av[17]= av17/row;
                        CH[18]= CH18; av[18]= av18/row;
                        CH[19]= CH19; av[19]= av19/row;
                        CH[20]= CH20; av[20]= av20/row;
                        CH[21]= CH21; av[21]= av21/row;
                        CH[22]= CH22; av[22]= av22/row;
                        CH[23]= CH23; av[23]= av23/row;
                        CH[24]= CH24; av[24]= av24/row;
                        CH[25]= CH25; av[25]= av25/row;
                        CH[26]= CH26; av[26]= av26/row;
                        CH[27]= CH27; av[27]= av27/row;
                        CH[28]= CH28; av[28]= av28/row;
                        CH[29]= CH29; av[29]= av29/row;
                        CH[30]= CH30; av[30]= av30/row;
                        CH[31]= CH31; av[31]= av31/row;
                        CH[32]= CH32; av[32]= av32/row;
                        CH[33]= CH33; av[33]= av33/row;
                        CH[34]= CH34; av[34]= av34/row;
                        CH[35]= CH35; av[35]= av35/row;
                        CH[36]= CH36; av[36]= av36/row;
                        CH[37]= CH37; av[37]= av37/row;
                        CH[38]= CH38; av[38]= av38/row;
                        CH[39]= CH39; av[39]= av39/row;
                        CH[40]= CH40; av[40]= av40/row;
                        CH[41]= CH41; av[41]= av41/row;
                        CH[42]= CH42; av[42]= av42/row;
                        CH[43]= CH43; av[43]= av43/row;
                        CH[44]= CH44; av[44]= av44/row;
                        CH[45]= CH45; av[45]= av45/row;
                        CH[46]= CH46; av[46]= av46/row;
                        CH[47]= CH47; av[47]= av47/row;
                        CH[48]= CH48; av[48]= av48/row;
                        CH[49]= CH49; av[49]= av49/row;
                        CH[50]= CH50; av[50]= av50/row;

                        for(int i =0 ; i< CH.length ; i++) {
                            seriesData.add(new GraphActivity.CustomDataEntry(i, CH[i], av[i]));
                        }

                        Set set = Set.instantiate();
                        set.data(seriesData);
                        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
                        Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");

                        Line series1 = cartesian.line(series1Mapping);
                        series1.name("today");
                        series1.markers().enabled(true);
                        series1.stroke(preferences.getString("today","#1e90ff"),preferences.getInt("thick",2),"null","null","null");
                        series1.markers()
                                .size(5d)
                                .type("circle");
                        series1.seriesType("spline");
                        series1.hovered().markers().enabled(true);
                        series1.hovered().markers()
                                .type(MarkerType.CIRCLE)
                                .size(4d);
                        series1.tooltip()
                                .position("right")
                                .anchor(Anchor.LEFT_CENTER)
                                .offsetX(5d)
                                .offsetY(5d);

                        Line series2 = cartesian.line(series2Mapping);
                        series2.name("recent 5 average");
                        series2.markers().enabled(true);
                        series2.stroke(preferences.getString("recent5","#CED4DA"),preferences.getInt("thick",2),"null","null","null");
                        series2.markers()
                                .size(5d)
                                .type("circle");
                        series2.seriesType("spline");
                        series2.hovered().markers().enabled(true);
                        series2.hovered().markers()
                                .type(MarkerType.CIRCLE)
                                .size(4d);
                        series2.tooltip()
                                .position("right")
                                .anchor(Anchor.LEFT_CENTER)
                                .offsetX(5d)
                                .offsetY(5d);

                        cartesian.legend().enabled(true);
                        cartesian.legend().fontSize(13d);
                        cartesian.legend().padding(0d, 0d, 10d, 0d);
                        anyChartView.setChart(cartesian);


                    }else {
                        System.out.println("fail");
                        Toast.makeText(GraphOldActivity.this, "데이터가 없습니다", Toast.LENGTH_SHORT).show();
                        nodata_label.setVisibility(View.VISIBLE);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(GraphOldActivity.this, "JSON: 로그에 실패하셨습니다." + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        };
        if(preferences.getBoolean("all_days_average_old",false) && !preferences .getBoolean("recent_5days_average_old",false)){
            OldRequest oldRequest = new OldRequest(userID, machineId, inputDate, startDate, endDate, responseListener);
            RequestQueue requestQueue = Volley.newRequestQueue(GraphOldActivity.this);
            requestQueue.add(oldRequest);
            System.out.println("all_days_average");
        }
        else if(!preferences.getBoolean("all_days_average_old",false) &&preferences.getBoolean("recent_5days_average_old",false)){
            OldRequest oldRequest = new OldRequest(userID, machineId, inputDate,responseListener);
            RequestQueue requestQueue = Volley.newRequestQueue(GraphOldActivity.this);
            requestQueue.add(oldRequest);
            System.out.println("recent_5days_average");
        }
        /*OldRequest oldRequest = new OldRequest(userID, machineId, inputDate, responseListener);
        RequestQueue requestQueue = Volley.newRequestQueue(GraphOldActivity.this);
        requestQueue.add(oldRequest);
        hz[0] ="CH"+String.valueOf(k[0]);*/


        /*if(inputDate.equals("2021/1/21")){
            for(int i =0 ; i< CH.length ; i++) {
                        seriesData.add(new CustomDataEntry(i, CH[i], 400));
                  }
        }
        else {
            for (int i = 0; i < 51; i++) {
                seriesData.add(new NoData(i,400,400));
            }
        }*/


        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartesian.annotations().removeAllAnnotations();
                int a =  Integer.parseInt(move[0]);
                a +=1;
                System.out.println(a);
                move[0] = String.valueOf(a);
                System.out.println(move[0]);
                cartesian.annotations().verticalLine(move[0]).xAnchor(move[0]);
                cartesian.annotations().horizontalLine("average").valueAnchor("400");
                k[0] +=1;
                if(k[0] >50){
                    k[0]=50;
                }
                hz[0] ="CH"+String.valueOf(k[0]);
                btn_confirm.setText("CH "+String.valueOf(k[0])+"Hz");
            }
        });
        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartesian.annotations().removeAllAnnotations();
                int a =  Integer.parseInt(move[0]);
                a -=1;
                System.out.println(a);
                move[0] = String.valueOf(a);
                System.out.println(move[0]);
                cartesian.annotations().verticalLine(move[0]).xAnchor(move[0]);
                cartesian.annotations().horizontalLine("average").valueAnchor("400");
                k[0] -= 1;
                if(k[0] <0){
                    k[0]=0;
                }
                hz[0] ="CH"+String.valueOf(k[0]);
                btn_confirm.setText("CH "+String.valueOf(k[0])+"Hz");
            }
        });
    }

    static class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(int x, Number value, Number value2) {
            super(x, value);
            setValue("value2", value2);
        }

    }

    private class NoData extends ValueDataEntry{

        public NoData(int x, Number value, Number value2) {
            super(x, value);
            setValue("value2", value2);
        }
    }


}

