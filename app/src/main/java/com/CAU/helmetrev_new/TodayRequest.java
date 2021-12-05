package com.CAU.helmetrev_new;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class TodayRequest extends StringRequest {

    //서버 url 설정(php파일 연동)

    private static String URL_GRAPH_all_average = "https://brain2020.cafe24.com/getjson_all_average.php";
    private static String URL_GRAPH_recent_5average ="https://brain2020.cafe24.com/getjson_recent_5average.php";
    private Map<String,String> map;

    public TodayRequest(String userID, String machineID, String startDate, String endDate, Response.Listener<String>listener){
        super(Method.POST,URL_GRAPH_all_average,listener,null);

        map=new HashMap<>();
        map.put("userID",userID);
        map.put("machineID",machineID);
        map.put("startDate",startDate);
        map.put("endDate",endDate);

    }
    public TodayRequest(String userID, String machineID, Response.Listener<String>listener){
        super(Method.POST,URL_GRAPH_recent_5average,listener,null);
        map=new HashMap<>();
        map.put("userID",userID);
        map.put("machineID",machineID);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
