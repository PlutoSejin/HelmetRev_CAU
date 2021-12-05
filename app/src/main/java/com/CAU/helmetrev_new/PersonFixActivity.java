package com.CAU.helmetrev_new;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class PersonFixActivity extends AppCompatActivity {
    private ImageButton btn_record, btn_setting, btn_support, btn_user, btn_info,btn_fixinfo, btn_gohome;
    private EditText fixinfo_phone, fixinfo_id, fixinfo_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personfix);
        btn_user = findViewById(R.id.btn_user);
        btn_info = findViewById(R.id.btn_info);
        btn_record = findViewById(R.id.btn_record);
        btn_support = findViewById(R.id.btn_support);
        fixinfo_id = findViewById(R.id.fixinfo_id);
        fixinfo_password = findViewById(R.id.fixinfo_password);
        fixinfo_phone = findViewById(R.id.fixinfo_phone);
        btn_fixinfo = findViewById(R.id.btn_fixinfo);
        btn_gohome = findViewById(R.id.btn_gohome);

        Intent intent = getIntent();
        final String userName = intent.getStringExtra("userName");
        final String userID = intent.getStringExtra("userID");
        final int machineID= intent.getIntExtra("machineID", 8888);

        btn_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonFixActivity.this, GraphActivity.class);
                intent.putExtra("userID",userID);
                intent.putExtra("userName",userName);
                intent.putExtra("machineID",machineID);
                startActivity(intent);
            }
        });
        btn_support.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonFixActivity.this, SupportActivity.class);
                intent.putExtra("userID",userID);
                intent.putExtra("userName",userName);
                intent.putExtra("machineID",machineID);
                startActivity(intent);
            }
        } );
        btn_user.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonFixActivity.this, UserActivity.class);
                intent.putExtra("userID",userID);
                intent.putExtra("userName",userName);
                intent.putExtra("machineID",machineID);
                startActivity(intent);
            }
        } );
        btn_gohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonFixActivity.this, HomeActivity.class);
                intent.putExtra("userID",userID);
                intent.putExtra("userName",userName);
                intent.putExtra("machineID",machineID);
                startActivity(intent);
            }
        });
    }
}
