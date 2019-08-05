package com.AWM.awmtest4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class BotonesActivity extends AppCompatActivity {

    static int numBotones = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_botones);

        LinearLayout llBotonera = findViewById(R.id.llBotonera);

        //Creamos las propiedades de layout que tendrán los botones.
        //Son LinearLayout.LayoutParams porque los botones van a estar en un LinearLayout.
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        //Creamos los botones en bucle
        for (int i = 0; i < numBotones; i++) {
            Button button = new Button(this);
            //Asignamos propiedades de layout al boton
            button.setLayoutParams(lp);
            if(i>1){
                button.setVisibility(View.INVISIBLE);
            }
            //Asignamos Texto al botón
            button.setText("Boton " + String.format("%02d", i+1));
            //Asignamose el Listener
            button.setOnClickListener(new ButtonsOnClickListener(this,i));
            //Añadimos el botón a la botonera
            llBotonera.addView(button);
        }
    }
    class ButtonsOnClickListener implements View.OnClickListener
    {
        Context context;
        int numButton;

        public ButtonsOnClickListener(Context context,int numButton) {
            this.context = context;
            this.numButton = numButton;
        }

        @Override
        public void onClick(View v)
        {
            String mensaje="";
            if (numButton%2 == 0)
                mensaje = "Boton PAR " +String.format("%02d",numButton);
            else
                mensaje="Boton IMPAR "+String.format("%02d",numButton);
            //Button b =(Button) v;
            Toast.makeText(this.context,mensaje, Toast.LENGTH_SHORT).show();
        }

    };
}
