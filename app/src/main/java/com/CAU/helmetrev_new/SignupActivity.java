package com.CAU.helmetrev_new;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    private EditText name, id, password, c_password, age, sex;
    private Button btn_regist, btn_id;
    private ProgressBar loading;
    private AlertDialog dialog;
    private boolean validate=false;
    private static String URL_REGIST = "https://brain2020.cafe24.com/register.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        loading = findViewById(R.id.loading);
        name = findViewById(R.id.name);
        id = findViewById(R.id.id);
        password=findViewById(R.id.password);
        c_password=findViewById(R.id.c_password);
        age = findViewById(R.id.age);
        sex = findViewById(R.id.sex);
        btn_regist = findViewById(R.id.btn_regist);
        btn_regist.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Regist();
            }
        });
        btn_id = findViewById(R.id.btn_id);
        btn_id.setOnClickListener(new View.OnClickListener() {//id중복체크
            @Override
            public void onClick(View view) {
                idCheck();
            }
        });
    }

    private void idCheck(){

        final String userID= this.id.getText().toString().trim();

        if(validate)
        {
            return;
        }
        if(userID.equals("")){
            AlertDialog.Builder builder=new AlertDialog.Builder(SignupActivity.this);
            dialog = builder.setMessage("아이디는 빈 칸일 수 없습니다")
                    .setPositiveButton("확인",null)
                    .create();
            dialog.show();
            return;
        }
        Response.Listener<String> responseListener=new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse=new JSONObject(response);
                    Boolean success = jsonResponse.getBoolean("success");
                    if(success){
                        AlertDialog.Builder builder=new AlertDialog.Builder(SignupActivity.this );
                        dialog = builder.setMessage("사용할 수 있는 아이디입니다.")
                                .setPositiveButton("확인",null)
                                .create();
                        dialog.show();
                        id.setEnabled(false);
                        validate=true;
                        btn_id.setText("확인");
                    }
                    else{
                        AlertDialog.Builder builder=new AlertDialog.Builder( SignupActivity.this );
                        dialog=builder.setMessage("사용할 수 없는 아이디입니다.")
                                .setNegativeButton("확인",null)
                                .create();
                        dialog.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(SignupActivity.this, "JSON: 중복확인에 문제가 있습니다." + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        };
        ValidateRequest validateRequest=new ValidateRequest(userID,responseListener);
        RequestQueue queue= Volley.newRequestQueue(SignupActivity.this);
        queue.add(validateRequest);
    }

    private void Regist() {
        final String userName = this.name.getText().toString().trim();
        final String userID = this.id.getText().toString().trim();
        final String userPassword = this.password.getText().toString().trim();
        final String userPassword_c = this.c_password.getText().toString().trim();
        final String userAge = this.age.getText().toString().trim();
        final String userSex = this.sex.getText().toString().trim();

        TextView text1;
        text1 = (TextView)findViewById(R.id.c_password);

        if(!validate){
            AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
            dialog = builder.setMessage("First Check ID plz")
                    .setNegativeButton("OK", null)
                    .create();
            dialog.show();
            return;
        }

        if(userID.equals("")||userPassword.equals("")||userName.equals("")||userPassword_c.equals("")){
            AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
            dialog = builder.setMessage("Empty text exist")
                    .setNegativeButton("OK", null)
                    .create();
            dialog.show();
            return;
        }

        // password 확인 체크
        if(!userPassword.equals(userPassword_c)) {
            loading.setVisibility(View.GONE);
            btn_regist.setVisibility(View.VISIBLE);
            text1.setText("");
            Toast.makeText(SignupActivity.this, "비밀번호가 동일하지 않습니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            try{
                JSONObject jsonObject = new JSONObject(response);
                String userID = jsonObject.getString("userID");
                System.out.println(userID);
                boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        Toast.makeText(SignupActivity.this, "회원가입이 되었습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent( SignupActivity.this, LoginActivity.class );
                        startActivity( intent );
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"회원 등록 실패",Toast.LENGTH_SHORT).show();
                        return;
                    }

            }catch (JSONException e){
                e.printStackTrace();
                Toast.makeText(SignupActivity.this, "JSON: 회원가입에 실패하셨습니다." + e.toString(), Toast.LENGTH_SHORT).show();

                }
            }

            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SignupActivity.this, "ERROR: 회원가입에 실패하셨습니다." + error.toString(), Toast.LENGTH_SHORT).show();
                        }
            })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userID",userID);
                params.put("userPassword",userPassword);
                params.put("userName",userName);
                params.put("userSex",userSex);
                params.put("userAge",userAge);
                return params;
            }
        };

        //RegisterRequest registerRequest=new RegisterRequest(userID,userPassword, userName, responseListener);
        RequestQueue requestQueue = Volley.newRequestQueue(SignupActivity.this);
        requestQueue.add(stringRequest);
    }
}