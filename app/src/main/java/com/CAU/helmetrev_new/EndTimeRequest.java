package com.CAU.helmetrev_new;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class EndTimeRequest extends StringRequest {

    //서버 url 설정(php파일 연동)
    private static String URL_LOGIN = "https://brain2020.cafe24.com/endtime.php";
    private Map<String, String> map;

    public EndTimeRequest(String userID, String machineID, String check, Response.Listener<String>listener){
        super(Method.POST,URL_LOGIN,listener,null);
        //System.out.println("HomeRequest: "+String.valueOf(CH0));
        map=new HashMap<>();
        map.put("userID",userID);
        map.put("machineID",machineID);
        map.put("check",check);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}