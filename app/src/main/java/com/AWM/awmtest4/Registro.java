package com.AWM.awmtest4;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Registro extends AppCompatActivity implements View.OnClickListener {

        EditText etnombre, etcorreo, etcontrasena, ettelefono;
        Button btn_guardar;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        //Cancelar
        final Button btn_cancelar = (Button) findViewById(R.id.btn_cancelar);
        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


      //boton guardar
        btn_guardar = findViewById(R.id.btn_guardar);
        btn_guardar.setOnClickListener(this);


    }



    @Override
    public void onClick(View view) {

        final String nombre=etnombre.getText().toString();
        final String correo=etcorreo.getText().toString();
        final String contrasena=etcontrasena.getText().toString();
        final String telefono=ettelefono.getText().toString();

        Response.Listener<String> responListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject JsonReponse = new JSONObject(response);
                    boolean SUCCES = JsonReponse.getBoolean("SUCCES");

                    if(SUCCES) {
                        Intent intent = new Intent(Registro.this,MainActivity.class);
                        Registro.this.startActivity(intent);
                    } else {
                        AlertDialog.Builder  builder = new AlertDialog.Builder(Registro.this);
                    builder.setMessage("ERROR en el Registro").setNegativeButton("Retry",null).create().show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };


    }


}