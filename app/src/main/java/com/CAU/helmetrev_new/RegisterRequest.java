package com.CAU.helmetrev_new;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    //서버 url 설정(php파일 연동)
    final static  private String URL_REGIST="https://brain2020.cafe24.com/register.php";
    private Map<String,String> params;

    public RegisterRequest(String userID, String userPassword, String userName, Response.Listener<String>listener){
        super(Method.POST,URL_REGIST,listener,null);//위 url에 post방식으로 값을 전송

        params=new HashMap<>();
        params.put("userID",userID);
        params.put("userPassword",userPassword);
        params.put("userName",userName);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
}