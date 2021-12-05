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

public class DetailGraphActivity extends AppCompatActivity {
    private BluetoothSPP bt;
    private List<Float> pot_values = new ArrayList<>();
    private TextView graph_label, graph_date;
    private Button btn_back;

    private static final String TAG = "MainActivity";
    private LineChart mChart;
    private Thread thread;
    private int mFillColor = Color.argb(150,51,181,229);
    private Toolbar toolbar;
    private ImageButton btn_record, btn_setting, btn_support, btn_user, btn_info,btn_right, btn_left,btn_gohome;
    private final int[] CH =new int[51];
    private final double[] av = new double[51];
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailgraph);
        btn_user = findViewById(R.id.btn_user);
        btn_info = findViewById(R.id.btn_info);
        btn_record = findViewById(R.id.btn_record);
        btn_support = findViewById(R.id.btn_support);
        graph_label = findViewById(R.id.graph_label);
        btn_right= findViewById(R.id.btn_right);
        btn_left = findViewById(R.id.btn_left);
        btn_back= findViewById(R.id.btn_back);
        btn_gohome = findViewById(R.id.btn_gohome);
        //graph_date= findViewById(R.id.graph_date);
        ArrayList<Entry> entry1 = new ArrayList<>();
        Intent intent = getIntent();
        final String userName = intent.getStringExtra("userName");
        final String userID = intent.getStringExtra("userID");
        final int machineID= intent.getIntExtra("machineID", 8888);
        final String channel = intent.getStringExtra("channel");
        final String inputDate = intent.getStringExtra("inputdate");
        System.out.println(inputDate);
        graph_label.setText(channel);
        final String machineId = String.valueOf(machineID);
        btn_gohome.setVisibility(View.GONE);

        preferences =  PreferenceManager.getDefaultSharedPreferences(this);

        editor = preferences.edit();

        btn_support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailGraphActivity.this, SupportActivity.class);
                intent.putExtra("userID",userID);
                intent.putExtra("userName",userName);
                intent.putExtra("machineID",machineID);
                startActivity(intent);
            }
        });
        btn_user.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailGraphActivity.this, FixInfoActivity.class);
                intent.putExtra("userID",userID);
                intent.putExtra("userName",userName);
                intent.putExtra("machineID",machineID);
                startActivity(intent);
            }
        } );
        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailGraphActivity.this, UserActivity.class);
                intent.putExtra("userID",userID);
                intent.putExtra("userName",userName);
                intent.putExtra("machineID",machineID);
                startActivity(intent);
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailGraphActivity.this, GraphActivity.class);
                intent.putExtra("userID",userID);
                intent.putExtra("userName",userName);
                intent.putExtra("machineID",machineID);
                startActivity(intent);
            }
        });
        btn_gohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailGraphActivity.this, HomeActivity.class);
                intent.putExtra("userID",userID);
                intent.putExtra("userName",userName);
                intent.putExtra("machineID",machineID);
                startActivity(intent);
            }
        });


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
                    final int channel = jsonObject.getInt("channel");
                    final int channel2 = jsonObject.getInt("channel2");
                    final int channel3 = jsonObject.getInt("channel3");
                    final int channel4 = jsonObject.getInt("channel4");
                    final int channel5 = jsonObject.getInt("channel5");
                    final String inputdate= jsonObject.getString("INPUT_DATE");
                    final String inputdate2= jsonObject.getString("INPUT_DATE2");
                    final String inputdate3= jsonObject.getString("INPUT_DATE3");
                    final String inputdate4= jsonObject.getString("INPUT_DATE4");
                    final String inputdate5= jsonObject.getString("INPUT_DATE5");
                    System.out.println(cnt);
                    System.out.println(row);
                    if(success){
                        System.out.print("graph success");
                        System.out.println(channel);
                        System.out.println(channel2);
                        System.out.println(channel3);
                        System.out.println(channel4);
                        System.out.println(channel5);
                        //System.out.print(av1);
                        Toast.makeText(DetailGraphActivity.this, "success", Toast.LENGTH_SHORT).show();
                        //System.out.println(av1);
                        if(channel!=0 ) {
                            seriesData.add(new CustomDataEntry2("5",channel));
                        }
                        if(channel2!=0 ) {
                            seriesData.add(new CustomDataEntry2("4",channel2));
                        }
                        if(channel3!=0 ) {
                            seriesData.add(new CustomDataEntry2("3",channel3));
                        }
                        if(channel4!=0 ) {
                            seriesData.add(new CustomDataEntry2("2",channel4));
                        }
                        if(channel5!=0 ) {
                            seriesData.add(new CustomDataEntry2("1",channel5));
                        }


                        Set set = Set.instantiate();
                        set.data(seriesData);
                        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");

                        Line series1 = cartesian.line(series1Mapping);
                        series1.name("today");
                        series1.markers().enabled(true);
                        series1.stroke(preferences.getString("detail","#1e90ff"),preferences.getInt("thick",2),"null","null","null");
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


                        cartesian.legend().enabled(true);
                        cartesian.legend().fontSize(13d);
                        cartesian.legend().padding(0d, 0d, 10d, 0d);



                        anyChartView.setChart(cartesian);


                    }else {
                        System.out.println("fail");
                        Toast.makeText(DetailGraphActivity.this, "데이터가 존재하지 않습니다", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(DetailGraphActivity.this, "JSON: 로그에 실패하셨습니다." + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        };
        DetailRequest detailRequest = new DetailRequest(userID, machineId,inputDate, channel, responseListener);
        RequestQueue requestQueue = Volley.newRequestQueue(DetailGraphActivity.this);
        requestQueue.add(detailRequest);

    }

    static class CustomDataEntry2 extends ValueDataEntry {
        public CustomDataEntry2(String x, Number value) {
            super(x, value);
        }
    }
    private class NoData extends ValueDataEntry{

        public NoData(int x, Number value) {
            super(x, value);
        }
    }

}
