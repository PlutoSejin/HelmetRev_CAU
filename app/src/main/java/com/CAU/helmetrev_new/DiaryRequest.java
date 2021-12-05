package com.CAU.helmetrev_new;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DiaryRequest extends StringRequest {

    //서버 url 설정(php파일 연동)
    private static String URL_GRAPH = "https://brain2020.cafe24.com/calendardot.php";
    private Map<String,String> map;

    public DiaryRequest(String userID, String machineID, Response.Listener<String>listener){
        super(Method.POST,URL_GRAPH,listener,null);
        map=new HashMap<>();
        map.put("userID",userID);
        map.put("machineID",machineID);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
