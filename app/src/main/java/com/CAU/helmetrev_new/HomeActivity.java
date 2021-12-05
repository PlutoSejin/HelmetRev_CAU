package com.CAU.helmetrev_new;

import androidx.appcompat.app.AlertDialog;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;
import app.akexorcist.bluetotohspp.library.BluetoothSPP.AutoConnectionListener;

public class HomeActivity extends AppCompatActivity {
    private static final long START_TIME_IN_MILLIS = 1200000;
    //private static final int REQUEST_ENABLE_BLUETOOTH = 1;
    private TextView countDownTextView, timeView;
    private ProgressBar progressBar;
    private ImageButton  btn_record, btn_setting, btn_support, btn_user, btn_info, btn_stop,btn_start;
    final static int BLUETOOTH_REQUEST_CODE = 100;
    private BluetoothSPP bt= new BluetoothSPP(this);
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning=true;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private ImageView wave, wave_die;
    final int[] attempt = {0};
    final ArrayList<String> arr_filter_name = new ArrayList<String>();
    int k=0;

    public HomeActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        countDownTextView = findViewById(R.id.countDownTextView);
        btn_user = findViewById(R.id.btn_user);
        btn_info = findViewById(R.id.btn_info);
        btn_record = findViewById(R.id.btn_record);
        btn_support = findViewById(R.id.btn_support);
        btn_stop = findViewById(R.id.btn_stop);
        btn_start = findViewById(R.id.btn_start);
        wave = findViewById(R.id.wave);
        wave_die = findViewById(R.id.wave_die);
        progressBar = findViewById(R.id.progressBar);
        Glide.with(this).load(R.raw.wave).into(wave);

        btn_stop.setVisibility(View.INVISIBLE);
        btn_start.setVisibility(View.INVISIBLE);
        wave_die.setVisibility(View.INVISIBLE);
        //String hzz = "H"+hz;
        //bt.send(hzz,true);
        Intent intent = getIntent();
        final String userID = intent.getStringExtra("userID");
        final String userName = intent.getStringExtra("userName");
        final int machineID= intent.getIntExtra("machineID", 8888);
        final String MachineID = Integer.toString(machineID);
        final int Hz = intent.getIntExtra("Hz",40);
        final String headset = intent.getStringExtra("headset");
        final String hz = Integer.toString(Hz);

        System.out.println(userID);
        System.out.println(userName);
        final int[] cnt = {0};
        final String[][] bd = new String[8000][53];
        final int[] cm={0};
        final int[] sum = new int[52];
        final int[] time={0};
        time[0]=0;
        File file = new File(getFilesDir(), "myFile.txt");
        file.delete();

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


        final String getTime = sdf.format(date);
        System.out.println(getTime);
        if(!bt.isBluetoothAvailable()) {
            Toast.makeText(getApplicationContext()
                    , "Bluetooth is not available"
                    , Toast.LENGTH_SHORT).show();
            finish();
        }
        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() { //데이터 수신
            //            TextView pot = findViewById(R.id.pot);
            public void onDataReceived(byte[] data, String message) {
                attempt[0]++;
                FileOutputStream fos = null;
                FileInputStream fis = null;
                File file = new File(getFilesDir(), "myFile.txt");
                try {
                cnt[0]++;
                if(time[0]==1){ cnt[0]=0;}
                BufferedWriter bw = new BufferedWriter(new FileWriter(file,true));
                bw.append(message+"\n");
                bw.flush();
                bw.close();
                System.out.println("file open end");

                FileReader fr  = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                String line;

                while ((line = br.readLine()) != null) {
                    //System.out.println("Output: " + line);
                    String array[] = line.split(" ");
                    for(int i =0 ; i<array.length ;i++){
                        bd[cnt[0]][i] = array[i];
                    }

                }
                file.delete();
                System.out.println("cnt :"+cnt[0]);
                br.close();
                System.out.println("file read end");

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if((mTimerRunning==false && time[0]==0)||cnt[0]==6300){
                    time[0]=1;
                    for(int j =0 ; j<=50 ; j++)
                    {
                        sum[j]=0;
                        System.out.print("CH"+j+": ");
                        for(int i=1 ; i<=cnt[0]-1 ; i++){
                            if(bd[i][j] != null && bd[i][j] != ""){
                                sum[j]+=Integer.parseInt(bd[i][j]);
                            }
                        }
                        sum[j]=sum[j]/cnt[0];

                        System.out.println(sum[j]);
                    }
                    cnt[0]=0;
                    Response.Listener<String> responseListener = new Response.Listener<String>(){
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");
                                if(success){
                                    System.out.println("Data send success");

                                }else {
                                    System.out.println("Data send Fail");
                                }
                            }catch (Exception e){
                                System.out.println("멈춰");
                                Log.e("ERROR", e.getMessage());
                                e.printStackTrace();
                                Toast.makeText(HomeActivity.this, "JSON: 로그에 실패하셨습니다." + e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    };
                    HomeRequest homeRequest = new HomeRequest(userID, MachineID, getTime, sum[0], sum[1], sum[2], sum[3], sum[4], sum[5], sum[6], sum[7], sum[8], sum[9]
                            , sum[10], sum[11], sum[12], sum[13], sum[14], sum[15], sum[16], sum[17], sum[18], sum[19]
                            , sum[20], sum[21], sum[22], sum[23], sum[24], sum[25], sum[26], sum[27], sum[28], sum[29]
                            , sum[30], sum[31], sum[32], sum[33], sum[34], sum[35], sum[36], sum[37], sum[38], sum[39]
                            , sum[40], sum[41], sum[42], sum[43], sum[44], sum[45], sum[46], sum[47], sum[48], sum[49], sum[50], attempt[0]
                            , responseListener);
                    RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
                    requestQueue.add(homeRequest);


                }
            }
        });

        final int[] cnt2 ={0};
        bt.setBluetoothStateListener(new BluetoothSPP.BluetoothStateListener(){
             @Override
             public void onServiceStateChanged(int state) {
                 if(state == BluetoothState.STATE_CONNECTED)
                 {
                     Log.i("Check", "State : Connected");
                     AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                     builder.setTitle(headset+"헤드셋이 자동연결되었습니다. 유지하시겠습니까?");
                     builder.setPositiveButton("아니오",
                             new DialogInterface.OnClickListener() {
                                 public void onClick(DialogInterface dialog, int which) {
                                     bt.stopAutoConnect();
                                 }
                             });
                     builder.setNegativeButton("예",
                             new DialogInterface.OnClickListener() {
                                 public void onClick(DialogInterface dialog, int which) {
                                     Toast.makeText(getApplicationContext()
                                             , "Connected to device"
                                             , Toast.LENGTH_SHORT).show();
                                     startTimer();
                                     btn_stop.setVisibility(View.VISIBLE);
                                     cnt[0]=0;
                                 }
                             });
                     if (!isFinishing()) {
                         builder.show();
                     }
                 }
                 else if(state == BluetoothState.STATE_CONNECTING) {
                     Toast.makeText(HomeActivity.this, "디바이스랑 연결중.", Toast.LENGTH_SHORT).show();
                 }
                 else if(state == BluetoothState.STATE_LISTEN) {
                     Log.i("Check", "State : LISTEN");
                     cnt2[0]++;
                     if(cnt2[0]>3){
                         Toast.makeText(HomeActivity.this, "헤드셋이 사용 중이거나 전원을 키지 않았습니다.", Toast.LENGTH_LONG).show();
                         cnt2[0]=0;
                         Intent intent1 = new Intent( HomeActivity.this, ChoiceActivity.class );
                         intent1.putExtra("userID",userID);
                         intent1.putExtra("userName",userName);
                         intent1.putExtra("machineID",machineID);
                         intent1.putExtra("Hz", Hz);
                         startActivity( intent1 );
                     }

                 }
                 else if(state == BluetoothState.STATE_NONE)
                     Log.i("Check", "State : None");

             }
        });


        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() {
            public void onDeviceConnected(String name, String address) {
                Toast.makeText(HomeActivity.this, address+"에 연결되었습니다.", Toast.LENGTH_LONG).show();
            }

            public void onDeviceDisconnected() { //연결해제
                Toast.makeText(HomeActivity.this, "디바이스랑 연결이 해제되었습니다. 디바이스의 전원을 확인해주세요", Toast.LENGTH_LONG).show();
            }

            public void onDeviceConnectionFailed() { //연결실패
                Toast.makeText(HomeActivity.this, "디바이스랑 연결이 안됩니다. 디바이스의 전원을 확인해주세요. 혹은 다른 사람의 핸드폰과 연결되었는지 확인해주세요", Toast.LENGTH_LONG).show();
            }

        });

        bt.setAutoConnectionListener(new AutoConnectionListener() {
            @Override
            public void onAutoConnectionStarted() {
                Log.i("Check", "Auto connection started");
            }
            @Override
            public void onNewConnection(String name, String address) {
                Log.i("Check", "New Connection - " + name + " - " + address);
            }
        });



        btn_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, GraphActivity.class);
                intent.putExtra("userID",userID);
                intent.putExtra("userName",userName);
                intent.putExtra("machineID",machineID);
                startActivity(intent);

            }
        });
        btn_support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SupportActivity.class);
                intent.putExtra("userID",userID);
                intent.putExtra("userName",userName);
                intent.putExtra("machineID",machineID);
                startActivity(intent);
            }
        });
        btn_user.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, FixInfoActivity.class);
                intent.putExtra("userID",userID);
                intent.putExtra("userName",userName);
                intent.putExtra("machineID",machineID);
                startActivity(intent);
            }
        } );
        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, UserActivity.class);
                intent.putExtra("userID",userID);
                intent.putExtra("userName",userName);
                intent.putExtra("machineID",machineID);
                startActivity(intent);

            }
        });
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
                File file = new File(getFilesDir(), "myFile.txt");
                file.delete();
                cnt[0]=0;
                time[0]=0;
                attempt[0]=0;
                String check="X";
                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            String userIID = jsonObject.getString("userID");
                            if(success){
                                System.out.println("Data update success");
                                System.out.println(userIID +" confirmed");

                            }else {
                                System.out.println("Data update Fail");
                            }
                        }catch (Exception e){
                            System.out.println("멈춰");
                            Log.e("ERROR", e.getMessage());
                            e.printStackTrace();
                            Toast.makeText(HomeActivity.this, "JSON: 로그에 실패하셨습니다." + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                EndTimeRequest endTimeRequest = new EndTimeRequest(userID, MachineID, check , responseListener);
                RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
                requestQueue.add(endTimeRequest);
            }
        });
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
                File file = new File(getFilesDir(), "myFile.txt");
                file.delete();
                cnt[0]=0;
                time[0]=0;
            }
        });
    }

    public void onDestroy() {
        super.onDestroy();
        Intent intent = getIntent();
        final String userID = intent.getStringExtra("userID");
        final String userName = intent.getStringExtra("userName");
        final int machineID= intent.getIntExtra("machineID", 8888);
        final String MachineID = Integer.toString(machineID);
        final int Hz = intent.getIntExtra("Hz",40);
        final String hz = Integer.toString(Hz);
        bt.stopService();
        attempt[0]=0;
        String check="X";
        Response.Listener<String> responseListener = new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    String userIID = jsonObject.getString("userID");
                    if(success){
                        System.out.println("Data update success");
                        System.out.println(userIID +" confirmed");

                    }else {
                        System.out.println("Data update Fail");
                    }
                }catch (Exception e){
                    System.out.println("멈춰");
                    Log.e("ERROR", e.getMessage());
                    e.printStackTrace();
                    Toast.makeText(HomeActivity.this, "JSON: 로그에 실패하셨습니다." + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        };
        EndTimeRequest endTimeRequest = new EndTimeRequest(userID, MachineID, check , responseListener);
        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(endTimeRequest);
        //블루투스 중지
    }
    // 앱이 시작하면
    public void onStart() {
        super.onStart();
        attempt[0]=0;
        if(!bt.isBluetoothEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT);
        } else {
            if(!bt.isServiceAvailable()) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);
                setup();
                if(bt.isAutoConnecting()){
                    String devName =bt.getConnectedDeviceName();
                }
                else if(bt.getServiceState() == BluetoothState.STATE_CONNECTED){
                    Toast.makeText(HomeActivity.this, "디바이스랑 연결이 안됩니다. 디바이스의 전원을 확인해주세요", Toast.LENGTH_LONG).show();
                    String devName =bt.getConnectedDeviceName();
                    System.out.println(devName);
                } else {
                    Toast.makeText(HomeActivity.this, "디바이스랑 연결이 안됩니다. 디바이스의 전원을 확인해주세요", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), DeviceList.class);
                    startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
                }

            }
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if (resultCode == Activity.RESULT_OK)
                bt.connect(data);
        } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);
            } else {
                Toast.makeText(getApplicationContext()
                        , "Bluetooth was not enabled."
                        , Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
    public void setup() {
        Intent intent = getIntent();
        final String userID = intent.getStringExtra("userID");
        final String userName = intent.getStringExtra("userName");
        final int machineID= intent.getIntExtra("machineID", 8888);
        final String headset = intent.getStringExtra("headset");
        final int Hz = intent.getIntExtra("Hz",40);
        System.out.println("Home  Activity headset name is"+headset);
        bt.autoConnect(headset);
        if(bt.getServiceState() == BluetoothState.STATE_CONNECTING) {
            Toast.makeText(HomeActivity.this, "디바이스랑 연결중.", Toast.LENGTH_SHORT).show();
        }else if(bt.getServiceState() != BluetoothState.STATE_CONNECTED){
            Toast.makeText(HomeActivity.this, "주변에 "+headset+" 헤드셋과 일치하는 기종이 없습니다. 다시 한번 확인 부탁드립니다.", Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent( HomeActivity.this, ChoiceActivity.class );
            intent1.putExtra("userID",userID);
            intent1.putExtra("userName",userName);
            intent1.putExtra("machineID",machineID);
            intent1.putExtra("Hz", Hz);
            startActivity( intent1 );
        }
    }
    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d분 %02d초", minutes, seconds);

        countDownTextView.setText(timeLeftFormatted);

    }
    private void startTimer(){
        wave.setVisibility(View.VISIBLE);
        wave_die.setVisibility(View.INVISIBLE);
        btn_start.setVisibility(View.INVISIBLE);
        btn_stop.setVisibility(View.VISIBLE);
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;

                updateCountDownText();
            }

            @Override
            public void onFinish() {
                Intent intent = getIntent();
                final String userID = intent.getStringExtra("userID");
                final String userName = intent.getStringExtra("userName");
                final int machineID= intent.getIntExtra("machineID", 8888);
                final String MachineID = Integer.toString(machineID);
                final int Hz = intent.getIntExtra("Hz",40);
                final String hz = Integer.toString(Hz);
                mTimerRunning = false;
                btn_stop.setVisibility(View.INVISIBLE);
                btn_start.setVisibility(View.VISIBLE);
                wave.setVisibility(View.INVISIBLE);
                wave_die.setVisibility(View.VISIBLE);
                bt.send("X",true);
                attempt[0]=0;
                String check ="O";
                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            String userIID = jsonObject.getString("userID");
                            if(success){
                                System.out.println("Data update success");
                                System.out.println(userIID +" confirmed");

                            }else {
                                System.out.println("Data update Fail");
                            }
                        }catch (Exception e){
                            System.out.println("멈춰");
                            Log.e("ERROR", e.getMessage());
                            e.printStackTrace();
                            Toast.makeText(HomeActivity.this, "JSON: 로그에 실패하셨습니다." + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                EndTimeRequest endTimeRequest = new EndTimeRequest(userID, MachineID, check , responseListener);
                RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
                requestQueue.add(endTimeRequest);
            }

        }.start();

        mTimerRunning = true;
    }
    private void resetTimer() {
        mCountDownTimer.cancel();
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        wave_die.setVisibility(View.VISIBLE);
        wave.setVisibility(View.INVISIBLE);
        btn_start.setVisibility(View.VISIBLE);
        btn_stop.setVisibility(View.INVISIBLE);
        mTimerRunning=true;
        updateCountDownText();
    }


}