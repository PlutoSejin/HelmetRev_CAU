 package com.CAU.helmetrev_new;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


 public class LoginActivity extends AppCompatActivity {
    private Button btn_login, btn_register, btn_find;
    private EditText id, password;
    private ProgressBar loading;
    private AlertDialog dialog;
    private static String URL_LOGIN = "https://brain2020.cafe24.com/login.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        id = findViewById(R.id.home_id);
        password = findViewById(R.id.home_pw);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById( R.id.btn_register );
        btn_find = findViewById(R.id.btn_find);

        btn_find.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( LoginActivity.this, FindActivity.class );
                startActivity( intent );
            }
        });
        //회원가입 하러 가는 버튼
        btn_register.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( LoginActivity.this, SignupActivity.class );
                startActivity( intent );
            }
        });
        //login 버튼
        btn_login .setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userID = id.getText().toString().trim();
                final String userPassword = password.getText().toString().trim();
                final int machineID;

                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            final String userName = jsonObject.getString("userName");
                            final int machineID = jsonObject.getInt("machineID");
                            final int Hz = jsonObject.getInt("Hz");
                            if(success){
                                System.out.println("1");
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                dialog = builder.setMessage("로그인에 성공했습니다")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                                new Handler().postDelayed(new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        System.out.println(userID);
                                        System.out.println(userName);
                                        System.out.println(machineID);
                                        System.out.println(Hz);
                                        Intent intent = new Intent( LoginActivity.this, ChoiceActivity.class );
                                        intent.putExtra("userID",userID);
                                        intent.putExtra("userName",userName);
                                        intent.putExtra("machineID",machineID);
                                        intent.putExtra("Hz", Hz);
                                        startActivity( intent );

                                        System.out.println(userID);
                                        System.out.println(userName);
                                        System.out.println(machineID);
                                        System.out.println(Hz);

                                    }
                                }, 600);

                            }else {
                                System.out.println("0");
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                Toast.makeText(LoginActivity.this, "로그인에 실패했습니다", Toast.LENGTH_SHORT).show();
                                /*new Handler().postDelayed(new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        Intent intent = new Intent( LoginActivity.this, HomeActivity.class );
                                        startActivity( intent );
                                    }
                                }, 600);// 0.6초 정도 딜레이를 준 후 시작*/

                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "JSON: 로그에 실패하셨습니다." + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseListener);
                RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
                requestQueue.add(loginRequest);
            }
        });
    }
    @Override
    protected void onStop(){
        super.onStop();
        if(dialog != null){//다이얼로그가 켜져있을때 함부로 종료가 되지 않게함
            dialog.dismiss();
            dialog = null;
        }
    }
}