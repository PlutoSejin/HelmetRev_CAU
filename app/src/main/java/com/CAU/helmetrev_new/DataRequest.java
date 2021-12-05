package com.CAU.helmetrev_new;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DataRequest extends StringRequest {

    //서버 url 설정(php파일 연동)
    private static String URL_LOGIN = "https://brain2020.cafe24.com/getjson.php";
    private Map<String,String> map;

    public DataRequest(String userID, Response.Listener<String>listener){
        super(Request.Method.POST,URL_LOGIN,listener,null);
        map=new HashMap<>();
        map.put("userID",userID);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}