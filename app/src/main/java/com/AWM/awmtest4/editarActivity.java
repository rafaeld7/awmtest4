package com.AWM.awmtest4;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class editarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DispDatabaseAdapter d = new DispDatabaseAdapter(getApplicationContext());
        d = d.open();
       String str= d.getSinlgeEntry("sonoff");
        Opcion1Fragment.misDispositivos.add(new Dispositivo(str,"AA-AA-AA-AA", "sonoff","cmnd/sonoff/power",""));
        TextView txtNombre = findViewById(R.id.editTextname);


        if (getIntent().getBooleanExtra("EXIT", false))
        {
            Opcion1Fragment.misDispositivos.get(0).setNombre(txtNombre.getText().toString());

        }


    }

}
