package com.AWM.awmtest4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        Button btnInicio = findViewById(R.id.login);
        Button btn_registrar = findViewById(R.id.btn_registrar);
        Button btn_Auth = findViewById(R.id.btn_Auth);

        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),RegistroFirebase2Activity.class);
                startActivity(i);

            }
        });

        btn_Auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),EmailPassword.class);
                startActivity(i);

            }
        });


    }


}
