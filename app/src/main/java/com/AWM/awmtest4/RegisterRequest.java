package com.AWM.awmtest4;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    private  static final String REGISTER_RECUEST_URL="http://10.0.2.190/register.php";
    private Map<String,String> Params;

    public RegisterRequest(String nombre, String correo, String contrasena, String telefono, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_RECUEST_URL, listener,null);
        Params = new HashMap<>();
        Params.put("nombre",nombre );
        Params.put("correo",correo );
        Params.put("contrasena",contrasena );
        Params.put("telefono",telefono );
    }

    @Override
    public Map<String, String> getParams() {
        return Params;
    }
}
