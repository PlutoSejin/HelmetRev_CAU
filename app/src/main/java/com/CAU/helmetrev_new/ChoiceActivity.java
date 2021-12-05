package com.CAU.helmetrev_new;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ChoiceActivity  extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        ListView listView = findViewById(R.id.listView);
        Intent intent = getIntent();
        final String userID = intent.getStringExtra("userID");
        final String userName = intent.getStringExtra("userName");
        final int machineID= intent.getIntExtra("machineID", 8888);
        final int Hz = intent.getIntExtra("Hz",40);

        List<String> list = new ArrayList<>();
        list.add("HeadSet01");
        list.add("HeadSet02");
        list.add("HeadSet03");
        list.add("HeadSet04");
        list.add("HeadSet05");
        list.add("FB300");

        ArrayAdapter<String> adpater = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adpater);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final String headset = (String) parent.getItemAtPosition(position);
                        AlertDialog.Builder builder = new AlertDialog.Builder(ChoiceActivity.this);
                        builder.setTitle(headset+"를 연결하겠습니다. 유지하시겠습니까?");
                        builder.setPositiveButton("아니오",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(ChoiceActivity.this, "취소했습니다. 다른항목 선택 부탁드립니다.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                        builder.setNegativeButton("예",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(getApplicationContext()
                                                , headset+"헤드셋을 선택하였습니다."
                                                , Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent( ChoiceActivity.this, HomeActivity.class );
                                        intent.putExtra("userID",userID);
                                        intent.putExtra("userName",userName);
                                        intent.putExtra("machineID",machineID);
                                        intent.putExtra("headset",headset);
                                        intent.putExtra("Hz", Hz);
                                        startActivity( intent );
                                    }
                                });
                        if (!isFinishing()) {
                            builder.show();
                        }
                    }
        });
    }
}
