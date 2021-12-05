package com.CAU.helmetrev_new;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;
public class HomeRequest extends StringRequest {

    //서버 url 설정(php파일 연동)
    private static String URL_LOGIN = "https://brain2020.cafe24.com/homedetail.php";
    private Map<String,String> map;

    public HomeRequest(String userID, String machineID, String time, int CH0, int CH1, int CH2, int CH3, int CH4, int CH5, int CH6, int CH7, int CH8,int CH9, int CH10,
                       int CH11, int CH12, int CH13, int CH14, int CH15, int CH16, int CH17, int CH18,int CH19, int CH20,
                       int CH21, int CH22, int CH23, int CH24, int CH25, int CH26, int CH27, int CH28,int CH29, int CH30,
                       int CH31, int CH32, int CH33, int CH34, int CH35, int CH36, int CH37, int CH38,int CH39, int CH40,
                       int CH41, int CH42, int CH43, int CH44, int CH45, int CH46, int CH47, int CH48,int CH49, int CH50, int attempt,
                       Response.Listener<String>listener){
        super(Method.POST,URL_LOGIN,listener,null);
        //System.out.println("HomeRequest: "+String.valueOf(CH0));
        map=new HashMap<>();
        map.put("userID",userID);
        map.put("machineID",machineID);
        map.put("time",time);
        map.put("CH0",String.valueOf(CH0));
        map.put("CH1",String.valueOf(CH1));
        map.put("CH2",String.valueOf(CH2));
        map.put("CH3",String.valueOf(CH3));
        map.put("CH4",String.valueOf(CH4));
        map.put("CH5",String.valueOf(CH5));
        map.put("CH6",String.valueOf(CH6));
        map.put("CH7",String.valueOf(CH7));
        map.put("CH8",String.valueOf(CH8));
        map.put("CH9",String.valueOf(CH9));
        map.put("CH10",String.valueOf(CH10));
        map.put("CH11",String.valueOf(CH11));
        map.put("CH12",String.valueOf(CH12));
        map.put("CH13",String.valueOf(CH13));
        map.put("CH14",String.valueOf(CH14));
        map.put("CH15",String.valueOf(CH15));
        map.put("CH16",String.valueOf(CH16));
        map.put("CH17",String.valueOf(CH17));
        map.put("CH18",String.valueOf(CH18));
        map.put("CH19",String.valueOf(CH19));
        map.put("CH20",String.valueOf(CH20));
        map.put("CH21",String.valueOf(CH21));
        map.put("CH22",String.valueOf(CH22));
        map.put("CH23",String.valueOf(CH23));
        map.put("CH24",String.valueOf(CH24));
        map.put("CH25",String.valueOf(CH25));
        map.put("CH26",String.valueOf(CH26));
        map.put("CH27",String.valueOf(CH27));
        map.put("CH28",String.valueOf(CH28));
        map.put("CH29",String.valueOf(CH29));
        map.put("CH30",String.valueOf(CH30));
        map.put("CH31",String.valueOf(CH31));
        map.put("CH32",String.valueOf(CH32));
        map.put("CH33",String.valueOf(CH33));
        map.put("CH34",String.valueOf(CH34));
        map.put("CH35",String.valueOf(CH35));
        map.put("CH36",String.valueOf(CH36));
        map.put("CH37",String.valueOf(CH37));
        map.put("CH38",String.valueOf(CH38));
        map.put("CH39",String.valueOf(CH39));
        map.put("CH40",String.valueOf(CH40));
        map.put("CH41",String.valueOf(CH41));
        map.put("CH42",String.valueOf(CH42));
        map.put("CH43",String.valueOf(CH43));
        map.put("CH44",String.valueOf(CH44));
        map.put("CH45",String.valueOf(CH45));
        map.put("CH46",String.valueOf(CH46));
        map.put("CH47",String.valueOf(CH47));
        map.put("CH48",String.valueOf(CH48));
        map.put("CH49",String.valueOf(CH49));
        map.put("CH50",String.valueOf(CH50));
        map.put("attempt",String.valueOf(attempt));
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
