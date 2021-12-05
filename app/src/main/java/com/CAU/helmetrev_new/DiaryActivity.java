package com.CAU.helmetrev_new;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.json.JSONObject;

public class DiaryActivity extends AppCompatActivity {
    private ListView listView;
    private List<CalendarDay> calevents = new ArrayList<>();
    private List<Event> eventList = new ArrayList<>();
    private HashMap<Integer,List<Event>> map = new HashMap<>();
    private ImageButton btn_record, btn_setting, btn_support, btn_user, btn_info, btn_gohome;

    OneDayDecorator oneDayDecorator = new OneDayDecorator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        btn_user = findViewById(R.id.btn_user);
        btn_info = findViewById(R.id.btn_info);
        btn_record = findViewById(R.id.btn_record);
        btn_support = findViewById(R.id.btn_support);
        btn_gohome = findViewById(R.id.btn_gohome);
        final MaterialCalendarView materialCalendarView = findViewById(R.id.calendarView);

        Intent intent = getIntent();
        final String userName = intent.getStringExtra("userName");
        final String userID = intent.getStringExtra("userID");
        final int machineID= intent.getIntExtra("machineID", 8888);
        final String machineId = String.valueOf(machineID);
        final int[] day = new int[7000];
        btn_gohome.setVisibility(View.GONE);

        btn_support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DiaryActivity.this, SupportActivity.class);
                intent.putExtra("userID",userID);
                intent.putExtra("userName",userName);
                intent.putExtra("machineID",machineID);
                startActivity(intent);
            }
        });
        btn_user.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiaryActivity.this, UserActivity.class);
                intent.putExtra("userID",userID);
                intent.putExtra("userName",userName);
                intent.putExtra("machineID",machineID);
                startActivity(intent);
            }
        } );
        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiaryActivity.this, FixInfoActivity.class);
                intent.putExtra("userID",userID);
                intent.putExtra("userName",userName);
                intent.putExtra("machineID",machineID);
                startActivity(intent);
            }
        });

        btn_gohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiaryActivity.this, HomeActivity.class);
                intent.putExtra("userID",userID);
                intent.putExtra("userName",userName);
                intent.putExtra("machineID",machineID);
                startActivity(intent);
            }
        });

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int Year = date.getYear();
                int Month = date.getMonth() + 1;
                int Day = date.getDay();
                String yymmdd;
                if(Month<=9 && Day<=9){
                    yymmdd = Year + "-0" + Month + "-0" + Day;
                }
                else if(Month>9 && Day<=9){
                    yymmdd = Year + "-" + Month + "-0" + Day;
                }
                else if(Month<=9 && Day>9){
                    yymmdd = Year + "-0" + Month + "-" + Day;
                }
                else {
                    yymmdd = Year + "-" + Month + "-" + Day;
                }

                //String yymmdd = Year + "-" + Month + "-" + Day;
                Intent intent = new Intent( DiaryActivity.this, GraphOldActivity.class );
                intent.putExtra("yymmdd",yymmdd);
                intent.putExtra("userID",userID);
                intent.putExtra("userName",userName);
                intent.putExtra("machineID",machineID);
                startActivity( intent );
            }
        });

        materialCalendarView.setSelectedDate(CalendarDay.today());
        materialCalendarView.state().edit()
                .setMinimumDate(CalendarDay.from(2020, 1, 1))
                .setMaximumDate(CalendarDay.from(2025, 12, 31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();
        materialCalendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator(),
                oneDayDecorator);

        Response.Listener<String> responseListener = new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    final int cnt = jsonObject.getInt("cnt");
                    final int row = jsonObject.getInt("row");
                    final int[] Day = new int[7000];
                    for(int i =1 ; i<=cnt ; i++){
                        Day[i] = jsonObject.getInt(String.valueOf(i));
                    }
                    String year = jsonObject.getString("year");
                    String month = jsonObject.getString("month");
                    String day = jsonObject.getString("day");

                    if(success){
                        System.out.println("Calendar success");
                        Toast.makeText(DiaryActivity.this, "day success",Toast.LENGTH_SHORT).show();
                        /*for(int i=1 ; i<=cnt ; i++){
                            day[i]=Day[i];
                        }*/
                        for(int i=1 ; i<=cnt-2 ; i+=3){
                            materialCalendarView.addDecorators(
                                    new EventDecorator(Color.BLACK, Collections.singleton(CalendarDay.from(Day[i],Day[i+1]-1,Day[i+2]))));
                        }

                        System.out.println(Day[1]);
                        System.out.println(day);
                        System.out.println(month);
                        System.out.println(year);

                    }else {
                        System.out.println("fail");
                        Toast.makeText(DiaryActivity.this, "데이터가 없습니다", Toast.LENGTH_SHORT).show();
                        //System.out.println(day[1]);

                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(DiaryActivity.this, "JSON: 로그에 실패하셨습니다." + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        };
        DiaryRequest diaryRequest = new DiaryRequest(userID, machineId, responseListener);
        RequestQueue requestQueue = Volley.newRequestQueue(DiaryActivity.this);
        requestQueue.add(diaryRequest);




    }

}
