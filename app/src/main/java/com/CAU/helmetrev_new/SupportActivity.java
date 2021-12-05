package com.CAU.helmetrev_new;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SupportActivity extends AppCompatActivity {
    private TextView textView,machine,usedtime;
    private ImageButton btn_record, btn_setting, btn_support, btn_user, btn_info, btn_gohome;
    private ImageButton devicecarebtn, miencebtn, supportbtn, callbtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        btn_user = findViewById(R.id.btn_user);
        btn_info = findViewById(R.id.btn_info);
        btn_record = findViewById(R.id.btn_record);
        btn_support = findViewById(R.id.btn_support);
        devicecarebtn = findViewById(R.id.devicecarebtn);
        miencebtn = findViewById(R.id.miencetbtn);
        supportbtn = findViewById(R.id.supportbtn);
        callbtn = findViewById(R.id.callbtn);
        machine = findViewById(R.id.machine);
        usedtime = findViewById(R.id.usedtime);

        btn_gohome = findViewById(R.id.btn_gohome);
        btn_gohome.setVisibility(View.GONE);

        Intent intent = getIntent();
        final String userName = intent.getStringExtra("userName");
        final String userID = intent.getStringExtra("userID");
        final int machineID= intent.getIntExtra("machineID", 8888);
        machine.setVisibility(View.GONE);
        usedtime.setVisibility(View.GONE);

        btn_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SupportActivity.this, GraphActivity.class);
                intent.putExtra("userID",userID);
                intent.putExtra("userName",userName);
                intent.putExtra("machineID",machineID);
                startActivity(intent);
            }
        });
        btn_user.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupportActivity.this, FixInfoActivity.class);
                intent.putExtra("userID",userID);
                intent.putExtra("userName",userName);
                intent.putExtra("machineID",machineID);
                startActivity(intent);
            }
        } );
        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupportActivity.this, UserActivity.class);
                intent.putExtra("userID",userID);
                intent.putExtra("userName",userName);
                intent.putExtra("machineID",machineID);
                startActivity(intent);
            }
        });
        btn_gohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupportActivity.this, HomeActivity.class);
                intent.putExtra("userID",userID);
                intent.putExtra("userName",userName);
                intent.putExtra("machineID",machineID);
                startActivity(intent);
            }
        });
        devicecarebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                machine.setText("Machine ID: "+machineID);
                usedtime.setText("Used Time: 100분"); //DB 불러오는 작업할것
                if(machine.getVisibility()==View.GONE){
                    machine.setVisibility(View.VISIBLE);
                    usedtime.setVisibility(View.VISIBLE);
                }
                else if(machine.getVisibility()==View.VISIBLE){
                    machine.setVisibility(View.GONE);
                    usedtime.setVisibility(View.GONE);
                }


            }
        });

    }
}
