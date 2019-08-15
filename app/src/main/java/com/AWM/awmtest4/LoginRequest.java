package com.AWM.awmtest4;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest{


    private  static final String LOGIN_RECUEST_URL="http://10.0.2.190/login.php";
    private Map<String,String> Params;

    public LoginRequest(String correo, String contrasena, Response.Listener<String> listener) {
        super(Request.Method.POST, LOGIN_RECUEST_URL, listener,null);
        Params = new HashMap<>();
        Params.put("correo",correo );
        Params.put("contrasena",contrasena );

    }

    @Override
    public Map<String, String> getParams() {
        return Params;
    }
}
