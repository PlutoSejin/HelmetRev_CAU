package com.CAU.helmetrev_new;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserActivity extends AppCompatActivity {
    private ImageButton btn_record, btn_setting, btn_support, btn_user, btn_logout;
    private Button btn_fix;
    private TextView user_name, user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        btn_user = findViewById(R.id.btn_user);
        btn_record = findViewById(R.id.btn_record);
        btn_support = findViewById(R.id.btn_support);
        btn_logout = findViewById(R.id.btn_logout);
        btn_fix = findViewById(R.id.btn_fix);
        user_name = findViewById(R.id.user_name);
        user_id = findViewById(R.id.user_id);

        Intent intent = getIntent();
        final String userName = intent.getStringExtra("userName");
        final String userID = intent.getStringExtra("userID");
        final int machineID= intent.getIntExtra("machineID", 8888);

        user_id.setText(userID);
        System.out.println(userID);
        System.out.println(userName);

        btn_fix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivity.this, PersonFixActivity.class);
                intent.putExtra("userID",userID);
                intent.putExtra("userName",userName);
                intent.putExtra("machineID",machineID);
                startActivity(intent);
            }
        });

        btn_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivity.this, GraphActivity.class);
                intent.putExtra("userID",userID);
                intent.putExtra("userName",userName);
                intent.putExtra("machineID",machineID);
                startActivity(intent);
            }
        });
        btn_support.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, SupportActivity.class);
                intent.putExtra("userID",userID);
                intent.putExtra("userName",userName);
                intent.putExtra("machineID",machineID);
                startActivity(intent);
            }
        } );
        btn_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, FixInfoActivity.class);
                intent.putExtra("userID",userID);
                intent.putExtra("userName",userName);
                intent.putExtra("machineID",machineID);
                startActivity(intent);
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, LoginActivity.class);
                intent.putExtra("userID",userID);
                intent.putExtra("userName",userName);
                intent.putExtra("machineID",machineID);
                startActivity(intent);
            }
        });
    }
}
