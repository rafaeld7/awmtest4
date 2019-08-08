package com.AWM.awmtest4;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ToggleButton;

import com.AWM.awmtest4.ui.login.LoginActivity;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Opcion1Fragment extends Fragment {

    public static ArrayList<Dispositivo> misDispositivos;    //Lista de todos los dispositivos
    public Opcion1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_opcion1, container, false);
        final ToggleButton toggleButton = view.findViewById(R.id.toggle_on_off);
        Button btnNombre = view.findViewById(R.id.buttonNombre) ;
        Button btnBotones = view.findViewById(R.id.btnBotones);
        Button btnLogin = view.findViewById(R.id.btnLogin);

        misDispositivos = new ArrayList<>();


       /* Fragmento de codigo que consigue el nombre del sonoff y lo situa en el boton(solo de forma local)

       try {
            btnNombre.setText(new ObtenerNombre().execute().get().substring(13).replaceAll("[\"|}]",""));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override


            /*Funcion para boton on/off */
            public void onClick(View view) {
               /* Fragmento de codigo que publica un mensaje de blink por medio de MQTT

                MQTT cliente = new MQTT("cmnd/sonoff/power","3");
                cliente.publishMssg();

                 "cmnd/sonoff/IRSEND"    Comando para enviar informacion por infrarojo
                */

               //Esta parte hace lo mismo pero por medio de una peticion http  (solo funciona de forma local)
                new GetTaskDone().execute();
                toggleButton.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toggleButton.setChecked(false);
                    }
                },1000);

            }
        });

        btnNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), editarActivity.class) ;
                startActivity(i);
            }
        });


        btnBotones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),BotonesActivity.class);
                startActivity(i);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
            }
        });


        return view;





    }

}
