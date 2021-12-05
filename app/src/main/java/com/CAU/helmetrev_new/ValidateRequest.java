package com.CAU.helmetrev_new;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ValidateRequest extends StringRequest {
    private static String URL_REGIST = "https://brain2020.cafe24.com/UserValidate.php";
    private Map<String,String> map;

    public ValidateRequest(String userID, Response.Listener<String>listener){
        super(Request.Method.POST,URL_REGIST,listener,null);

        map = new HashMap<>();
        map.put("userID",userID);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}