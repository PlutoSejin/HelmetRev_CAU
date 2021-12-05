package com.CAU.helmetrev_new;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.preference.PreferenceManager;

public class FixInfoActivity extends AppCompatActivity {
    private ImageButton btn_record, btn_setting, btn_support, btn_user, btn_info,btn_fixinfo, btn_gohome;
    private EditText fixinfo_phone, fixinfo_id, fixinfo_password;
    private SeekBar seekBar_thick;
    private Switch switch1;
    private RadioGroup radioGroup1, radioGroup2, radioGroup3;
    private RadioButton red_rb_btn_today, blue_rb_btn_today, green_rb_btn_today,  black_rb_btn_today;
    private RadioButton red_rb_btn_detail, blue_rb_btn_detail, green_rb_btn_detail,  black_rb_btn_detail;
    private RadioButton gray_rb_btn_rc5, sky_rb_btn_rc5, bgreen_rb_btn_rc5, purple_rb_btn_rc5;
    private RadioButton recent_5days_average_old,all_days_average_old,all_days_average, recent_5days_average;
    private TextView startdate_year,startdate_month,startdate_day,enddate_year,enddate_month,enddate_day;
    private ImageButton startdate,enddate;
    private TextView startdate_year_old,startdate_month_old,startdate_day_old,enddate_year_old,enddate_month_old,enddate_day_old;
    private ImageButton startdate_old,enddate_old;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixinfo);
        btn_user = findViewById(R.id.btn_user);
        btn_info = findViewById(R.id.btn_info);
        btn_record = findViewById(R.id.btn_record);
        btn_support = findViewById(R.id.btn_support);
        fixinfo_id = findViewById(R.id.fixinfo_id);
        fixinfo_password = findViewById(R.id.fixinfo_password);
        fixinfo_phone = findViewById(R.id.fixinfo_phone);
        btn_fixinfo = findViewById(R.id.btn_fixinfo);
        btn_gohome = findViewById(R.id.btn_gohome);
        seekBar_thick = findViewById(R.id.seekbar_thick);
        switch1 = findViewById(R.id.switch1);
        radioGroup1 = findViewById(R.id.radiogroup1);
        radioGroup2 = findViewById(R.id.radiogroup2);
        radioGroup3 = findViewById(R.id.radiogroup3);
        red_rb_btn_today =findViewById(R.id.red_rb_btn_today);
        blue_rb_btn_today =findViewById(R.id.blue_rb_btn_today);
        green_rb_btn_today = findViewById(R.id.green_rb_btn_today);
        black_rb_btn_today = findViewById(R.id.black_rb_btn_today);
        gray_rb_btn_rc5 = findViewById(R.id.gray_rb_btn_r5);
        sky_rb_btn_rc5 = findViewById(R.id.sky_rb_btn_r5);
        bgreen_rb_btn_rc5 = findViewById(R.id.bgreen_rb_btn_r5);
        purple_rb_btn_rc5 = findViewById(R.id.purple_rb_btn_r5);
        red_rb_btn_detail = findViewById(R.id.red_rb_btn_detail);
        blue_rb_btn_detail = findViewById(R.id.blue_rb_btn_detail);
        green_rb_btn_detail = findViewById(R.id.green_rb_btn_detail);
        black_rb_btn_detail = findViewById(R.id.black_rb_btn_detail);
        all_days_average_old = findViewById(R.id.all_days_average_old);
        all_days_average = findViewById(R.id.all_days_average);
        recent_5days_average_old = findViewById(R.id.recent_5days_average_old);
        recent_5days_average = findViewById(R.id.recent_5days_average);
        startdate_day= findViewById(R.id.startdate_day);
        startdate_year= findViewById(R.id.startdate_year);
        startdate_month= findViewById(R.id.startdate_month);
        enddate_day= findViewById(R.id.enddate_day);
        enddate_year= findViewById(R.id.enddate_year);
        enddate_month= findViewById(R.id.enddate_month);
        enddate = findViewById(R.id.enddate);
        startdate = findViewById(R.id.startdate);
        startdate_day_old= findViewById(R.id.startdate_day_old);
        startdate_year_old= findViewById(R.id.startdate_year_old);
        startdate_month_old= findViewById(R.id.startdate_month_old);
        enddate_day_old= findViewById(R.id.enddate_day_old);
        enddate_year_old= findViewById(R.id.enddate_year_old);
        enddate_month_old= findViewById(R.id.enddate_month_old);
        enddate_old = findViewById(R.id.enddate_old);
        startdate_old = findViewById(R.id.startdate_old);

        radioGroup1.setOnCheckedChangeListener(radioGroupButtonChangeListener);
        radioGroup2.setOnCheckedChangeListener(radioGroupButtonChangeListener2);
        radioGroup3.setOnCheckedChangeListener(radioGroupButtonChangeListener3);
        all_days_average.setOnClickListener(radioButtonClickListener);
        all_days_average_old.setOnClickListener(radioButtonClickListener3);
        recent_5days_average.setOnClickListener(radioButtonClickListener2);
        recent_5days_average_old.setOnClickListener(radioButtonClickListener4);
        preferences =  PreferenceManager.getDefaultSharedPreferences(this);

        editor = preferences.edit();
        btn_gohome.setVisibility(View.GONE);



        final int battery =50;
        final int[] thick = {0};



        Intent intent = getIntent();
        final String userName = intent.getStringExtra("userName");
        final String userID = intent.getStringExtra("userID");
        final int machineID= intent.getIntExtra("machineID", 8888);

        seekBar_thick.setProgress(preferences.getInt("thick",2));
        radioGroup1.clearCheck();
        radioGroup2.clearCheck();
        radioGroup3.clearCheck();
        System.out.println(preferences.getBoolean("black_rb_btn_today",false));
        red_rb_btn_today.setChecked(preferences.getBoolean("red_rb_btn_today",false));
        blue_rb_btn_today.setChecked(preferences.getBoolean("blue_rb_btn_today",false));
        green_rb_btn_today.setChecked(preferences.getBoolean("green_rb_btn_today",false));
        black_rb_btn_today.setChecked(preferences.getBoolean("black_rb_btn_today",false));
        red_rb_btn_detail.setChecked(preferences.getBoolean("red_rb_btn_detail",false));
        blue_rb_btn_detail.setChecked(preferences.getBoolean("blue_rb_btn_detail",false));
        green_rb_btn_detail.setChecked(preferences.getBoolean("green_rb_btn_detail",false));
        black_rb_btn_detail.setChecked(preferences.getBoolean("black_rb_btn_detail",false));
        sky_rb_btn_rc5.setChecked(preferences.getBoolean("sky_rb_btn_rc5",false));
        bgreen_rb_btn_rc5.setChecked(preferences.getBoolean("bgreen_rb_btn_rc5",false));
        gray_rb_btn_rc5.setChecked(preferences.getBoolean("gray_rb_btn_rc5",false));
        purple_rb_btn_rc5.setChecked(preferences.getBoolean("purple_rb_btn_rc5",false));

        all_days_average.setChecked(preferences.getBoolean("all_days_average",false));
        recent_5days_average.setChecked(preferences.getBoolean("recent_5days_average",false));
        all_days_average_old.setChecked(preferences.getBoolean("all_days_average_old",false));
        recent_5days_average_old.setChecked(preferences.getBoolean("recent_5days_average_old",false));

        startdate_day.setText(preferences.getString("startdate_day","01"));
        startdate_month.setText(preferences.getString("startdate_month","02"));
        startdate_year.setText(preferences.getString("startdate_year","2021"));
        enddate_day.setText(preferences.getString("enddate_day","16"));
        enddate_month.setText(preferences.getString("enddate_month","05"));
        enddate_year.setText(preferences.getString("enddate_year","2021"));

        startdate_day_old.setText(preferences.getString("startdate_day_old","01"));
        startdate_month_old.setText(preferences.getString("startdate_month_old","02"));
        startdate_year_old.setText(preferences.getString("startdate_year_old","2021"));
        enddate_day_old.setText(preferences.getString("enddate_day_old","16"));
        enddate_month_old.setText(preferences.getString("enddate_month_old","05"));
        enddate_year_old.setText(preferences.getString("enddate_year_old","2021"));



        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(FixInfoActivity.this, "현재 배터리 상태는 "+battery+"%입니다",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(FixInfoActivity.this,"배터리 부족상태에서 알림이 오지 않습니다", Toast.LENGTH_LONG).show();
                }
            }
        });

        seekBar_thick.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                thick[0] = progress;
                Toast.makeText(FixInfoActivity.this,"그래프의 굵기를 "+ thick[0] +"으로 바꿉니다.", Toast.LENGTH_LONG).show();
                editor.putInt("thick",thick[0]);
                editor.apply();
                editor.commit();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                System.out.println("seekbar moving");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                System.out.println("seekbar stop");
            }
        });


        btn_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FixInfoActivity.this, GraphActivity.class);
                intent.putExtra("userID",userID);
                intent.putExtra("userName",userName);
                intent.putExtra("machineID",machineID);
                startActivity(intent);
            }
        });
        btn_support.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FixInfoActivity.this, SupportActivity.class);
                intent.putExtra("userID",userID);
                intent.putExtra("userName",userName);
                intent.putExtra("machineID",machineID);
                startActivity(intent);
            }
        } );
        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FixInfoActivity.this, UserActivity.class);
                intent.putExtra("userID",userID);
                intent.putExtra("userName",userName);
                intent.putExtra("machineID",machineID);
                startActivity(intent);
            }
        });
        btn_gohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FixInfoActivity.this, HomeActivity.class);
                intent.putExtra("userID",userID);
                intent.putExtra("userName",userName);
                intent.putExtra("machineID",machineID);
                startActivity(intent);
            }
        });
    }
    class boldSwitchListener implements CompoundButton.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        }
    }

    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            if(i == R.id.red_rb_btn_today){
                //Toast.makeText(FixInfoActivity.this, "빨간색 눌렸습니다.", Toast.LENGTH_SHORT).show();
                editor.putString("today","#ff0000");
                editor.putBoolean("red_rb_btn_today",red_rb_btn_today.isChecked());
                editor.putBoolean("blue_rb_btn_today",blue_rb_btn_today.isChecked());
                editor.putBoolean("black_rb_btn_today",black_rb_btn_today.isChecked());
                editor.putBoolean("green_rb_btn_today",green_rb_btn_today.isChecked());
                editor.apply();
                editor.commit();
            }
            else if(i == R.id.blue_rb_btn_today){
                //Toast.makeText(FixInfoActivity.this, "파란색 눌렸습니다.", Toast.LENGTH_SHORT).show();
                editor.putString("today","#1e90ff");
                editor.putBoolean("red_rb_btn_today",red_rb_btn_today.isChecked());
                editor.putBoolean("blue_rb_btn_today",blue_rb_btn_today.isChecked());
                editor.putBoolean("black_rb_btn_today",black_rb_btn_today.isChecked());
                editor.putBoolean("green_rb_btn_today",green_rb_btn_today.isChecked());
                editor.apply();
                editor.commit();
            }
            else if(i == R.id.black_rb_btn_today){
                //Toast.makeText(FixInfoActivity.this, "검정색 눌렸습니다.", Toast.LENGTH_SHORT).show();
                editor.putString("today","#000000");
                editor.putBoolean("red_rb_btn_today",red_rb_btn_today.isChecked());
                editor.putBoolean("blue_rb_btn_today",blue_rb_btn_today.isChecked());
                editor.putBoolean("black_rb_btn_today",black_rb_btn_today.isChecked());
                editor.putBoolean("green_rb_btn_today",green_rb_btn_today.isChecked());

                editor.apply();
                editor.commit();
            }
            else if(i == R.id.green_rb_btn_today){
                //Toast.makeText(FixInfoActivity.this, "초록색 눌렸습니다.", Toast.LENGTH_SHORT).show();
                editor.putString("today","#32cd32");
                editor.putBoolean("red_rb_btn_today",red_rb_btn_today.isChecked());
                editor.putBoolean("blue_rb_btn_today",blue_rb_btn_today.isChecked());
                editor.putBoolean("black_rb_btn_today",black_rb_btn_today.isChecked());
                editor.putBoolean("green_rb_btn_today",green_rb_btn_today.isChecked());
                editor.apply();
                editor.commit();
            }
        }
    };
    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener2 = new RadioGroup.OnCheckedChangeListener() {
        @Override public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            if(i == R.id.sky_rb_btn_r5){
                //Toast.makeText(FixInfoActivity.this, "하늘색 눌렸습니다.", Toast.LENGTH_SHORT).show();
                editor.putString("recent5","#87cefa");
                editor.putBoolean("sky_rb_btn_rc5",sky_rb_btn_rc5.isChecked());
                editor.putBoolean("bgreen_rb_btn_rc5",bgreen_rb_btn_rc5.isChecked());
                editor.putBoolean("gray_rb_btn_rc5",gray_rb_btn_rc5.isChecked());
                editor.putBoolean("purple_rb_btn_rc5",purple_rb_btn_rc5.isChecked());
                editor.apply();
                editor.commit();
            }
            else if(i == R.id.bgreen_rb_btn_r5){
                //Toast.makeText(FixInfoActivity.this, "밝은 초록색 눌렸습니다.", Toast.LENGTH_SHORT).show();
                editor.putString("recent5","#adff2f");
                editor.putBoolean("bgreen_rb_btn_rc5",bgreen_rb_btn_rc5.isChecked());
                editor.putBoolean("sky_rb_btn_rc5",sky_rb_btn_rc5.isChecked());
                editor.putBoolean("gray_rb_btn_rc5",gray_rb_btn_rc5.isChecked());
                editor.putBoolean("purple_rb_btn_rc5",purple_rb_btn_rc5.isChecked());
                editor.apply();
                editor.commit();
            }
            else if(i == R.id.gray_rb_btn_r5){
                //Toast.makeText(FixInfoActivity.this, "회색 눌렸습니다.", Toast.LENGTH_SHORT).show();
                editor.putString("recent5","#bebebe");
                editor.putBoolean("gray_rb_btn_rc5",gray_rb_btn_rc5.isChecked());
                editor.putBoolean("sky_rb_btn_rc5",sky_rb_btn_rc5.isChecked());
                editor.putBoolean("bgreen_rb_btn_rc5",bgreen_rb_btn_rc5.isChecked());
                editor.putBoolean("purple_rb_btn_rc5",purple_rb_btn_rc5.isChecked());
                editor.apply();
                editor.commit();
            }
            else if(i == R.id.purple_rb_btn_r5){
                //Toast.makeText(FixInfoActivity.this, "보라색 눌렸습니다.", Toast.LENGTH_SHORT).show();
                editor.putString("recent5","#a020f0");
                editor.putBoolean("purple_rb_btn_rc5",purple_rb_btn_rc5.isChecked());
                editor.putBoolean("sky_rb_btn_rc5",sky_rb_btn_rc5.isChecked());
                editor.putBoolean("bgreen_rb_btn_rc5",bgreen_rb_btn_rc5.isChecked());
                editor.putBoolean("gray_rb_btn_rc5",gray_rb_btn_rc5.isChecked());
                editor.apply();
                editor.commit();
            }
        }
    };
    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener3 = new RadioGroup.OnCheckedChangeListener() {
        @Override public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            if(i == R.id.red_rb_btn_detail){
                //Toast.makeText(FixInfoActivity.this, "빨간색 눌렸습니다.", Toast.LENGTH_SHORT).show();
                editor.putString("detail","#ff0000");
                editor.putBoolean("red_rb_btn_detail",red_rb_btn_detail.isChecked());
                editor.putBoolean("blue_rb_btn_detail",blue_rb_btn_detail.isChecked());
                editor.putBoolean("black_rb_btn_detail",black_rb_btn_detail.isChecked());
                editor.putBoolean("green_rb_btn_detail",green_rb_btn_detail.isChecked());
                editor.apply();
                editor.commit();
            }
            else if(i == R.id.blue_rb_btn_detail){
                //Toast.makeText(FixInfoActivity.this, "파란색 눌렸습니다.", Toast.LENGTH_SHORT).show();
                editor.putString("detail","#1e90ff");
                editor.putBoolean("blue_rb_btn_detail",blue_rb_btn_detail.isChecked());
                editor.putBoolean("red_rb_btn_detail",red_rb_btn_detail.isChecked());
                editor.putBoolean("black_rb_btn_detail",black_rb_btn_detail.isChecked());
                editor.putBoolean("green_rb_btn_detail",green_rb_btn_detail.isChecked());
                editor.apply();
                editor.commit();
            }
            else if(i == R.id.black_rb_btn_detail){
                //Toast.makeText(FixInfoActivity.this, "검정색 눌렸습니다.", Toast.LENGTH_SHORT).show();
                editor.putString("detail","#000000");
                editor.putBoolean("black_rb_btn_detail",black_rb_btn_detail.isChecked());
                editor.putBoolean("red_rb_btn_detail",red_rb_btn_detail.isChecked());
                editor.putBoolean("blue_rb_btn_detail",blue_rb_btn_detail.isChecked());
                editor.putBoolean("black_rb_btn_detail",black_rb_btn_detail.isChecked());
                editor.putBoolean("green_rb_btn_detail",green_rb_btn_detail.isChecked());
                editor.apply();
                editor.commit();
            }
            else if(i == R.id.green_rb_btn_detail){
                //Toast.makeText(FixInfoActivity.this, "초록색 눌렸습니다.", Toast.LENGTH_SHORT).show();
                editor.putString("detail","#32cd32");
                editor.putBoolean("green_rb_btn_detail",green_rb_btn_detail.isChecked());
                editor.putBoolean("red_rb_btn_detail",red_rb_btn_detail.isChecked());
                editor.putBoolean("blue_rb_btn_detail",blue_rb_btn_detail.isChecked());
                editor.putBoolean("black_rb_btn_detail",black_rb_btn_detail.isChecked());
                editor.apply();
                editor.commit();
            }
        }
    };
    RadioButton.OnClickListener radioButtonClickListener = new RadioButton.OnClickListener(){
        @Override public void onClick(View view) {
            recent_5days_average.setChecked(false);
            all_days_average.setChecked(true);
            editor.putBoolean("all_days_average",all_days_average.isChecked());
            editor.putBoolean("recent_5days_average",recent_5days_average.isChecked());
            editor.apply();
            editor.commit();
        }
    };
    RadioButton.OnClickListener radioButtonClickListener2 = new RadioButton.OnClickListener(){
        @Override public void onClick(View view) {
            recent_5days_average.setChecked(true);
            all_days_average.setChecked(false);
            editor.putBoolean("all_days_average",all_days_average.isChecked());
            editor.putBoolean("recent_5days_average",recent_5days_average.isChecked());
            editor.apply();
            editor.commit();
        }
    };
    RadioButton.OnClickListener radioButtonClickListener3 = new RadioButton.OnClickListener(){
        @Override public void onClick(View view) {
            recent_5days_average_old.setChecked(false);
            all_days_average_old.setChecked(true);
            editor.putBoolean("all_days_average_old",all_days_average_old.isChecked());
            editor.putBoolean("recent_5days_average_old",recent_5days_average_old.isChecked());
            editor.apply();
            editor.commit();
        }
    };
    RadioButton.OnClickListener radioButtonClickListener4 = new RadioButton.OnClickListener(){
        @Override public void onClick(View view) {
            recent_5days_average_old.setChecked(true);
            all_days_average_old.setChecked(false);
            editor.putBoolean("all_days_average_old",all_days_average_old.isChecked());
            editor.putBoolean("recent_5days_average_old",recent_5days_average_old.isChecked());
            editor.apply();
            editor.commit();
        }
    };

    public void showDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment_start();
        newFragment.show(getSupportFragmentManager(),"datePicker");
    }

    public void processDatePickerResult(int year, int month, int day){
        String month_string = Integer.toString(month+1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        if(day<10){
            day_string = "0"+day_string;
        }
        if(month<10){
            month_string = "0"+month_string;
        }
        String dateMessage = (year_string+"-"+ month_string + "-" + day_string);
        startdate_day.setText(day_string);
        startdate_month.setText(month_string);
        startdate_year.setText(year_string);
        editor.putString("startdate",dateMessage);
        editor.putString("startdate_year",year_string);
        editor.putString("startdate_month",month_string);
        editor.putString("startdate_day",day_string);
        editor.apply();
        editor.commit();
        Toast.makeText(this,"시작하는 날: "+dateMessage,Toast.LENGTH_SHORT).show();
    }
    public void showDatePicker2(View view) {
        DialogFragment newFragment = new DatePickerFragment_end();
        newFragment.show(getSupportFragmentManager(),"datePicker");
    }

    public void processDatePickerResult2(int year, int month, int day){
        String month_string = Integer.toString(month+1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        if(day<10){
            day_string = "0"+day_string;
        }
        if(month<10){
            month_string = "0"+month_string;
        }
        String dateMessage = (year_string+"-"+ month_string + "-" + day_string);
        enddate_day.setText(day_string);
        enddate_month.setText(month_string);
        enddate_year.setText(year_string);
        editor.putString("enddate",dateMessage);
        editor.putString("enddate_year",year_string);
        editor.putString("enddate_month",month_string);
        editor.putString("enddate_day",day_string);
        editor.apply();
        editor.commit();
        Toast.makeText(this,"마지막 날: "+dateMessage,Toast.LENGTH_SHORT).show();
    }
    public void showDatePicker3(View view) {
        DialogFragment newFragment = new DatePickerFragment_start_old();
        newFragment.show(getSupportFragmentManager(),"datePicker");
    }

    public void processDatePickerResult3(int year, int month, int day){
        String month_string = Integer.toString(month+1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        if(day<10){
            day_string = "0"+day_string;
        }
        if(month<10){
            month_string = "0"+month_string;
        }
        String dateMessage = (year_string+"-"+ month_string + "-" + day_string);
        startdate_day_old.setText(day_string);
        startdate_month_old.setText(month_string);
        startdate_year_old.setText(year_string);
        editor.putString("startdate_old",dateMessage);
        editor.putString("startdate_year_old",year_string);
        editor.putString("startdate_month_old",month_string);
        editor.putString("startdate_day_old",day_string);
        editor.apply();
        editor.commit();
        Toast.makeText(this,"과거 Data 시작하는 날: "+dateMessage,Toast.LENGTH_SHORT).show();
    }
    public void showDatePicker4(View view) {
        DialogFragment newFragment = new DatePickerFragment_end_old();
        newFragment.show(getSupportFragmentManager(),"datePicker");
    }

    public void processDatePickerResult4(int year, int month, int day){
        String month_string = Integer.toString(month+1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        if(day<10){
            day_string = "0"+day_string;
        }
        if(month<10){
            month_string = "0"+month_string;
        }
        String dateMessage = (year_string+"-"+ month_string + "-" + day_string);
        enddate_day_old.setText(day_string);
        enddate_month_old.setText(month_string);
        enddate_year_old.setText(year_string);
        editor.putString("enddate_old",dateMessage);
        editor.putString("enddate_year_old",year_string);
        editor.putString("enddate_month_old",month_string);
        editor.putString("enddate_day_old",day_string);
        editor.apply();
        editor.commit();
        Toast.makeText(this,"과거 Data 마지막 날: "+dateMessage,Toast.LENGTH_SHORT).show();
    }

}
