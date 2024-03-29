package com.AWM.awmtest4;


import android.content.Context;
import android.content.Intent;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.AWM.awmtest4.Mqtt.MqttActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class Opcion2Fragment extends Fragment {

    public Opcion2Fragment() {
        // Required empty public constructor
    }

    private ProgressBar ipBar;
    private ArrayList<String> ips = new ArrayList<>();

    DhcpInfo d;           //Parametros para obtener informacion de wifi al escanear
    WifiManager wifi;
    String ip_Add;
    String red;

    DispDatabaseAdapter dispDatabaseAdapter; // Adaptador para poder utilizar la Base de Datos local (SQLite)

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_opcion2, container, false);
        Button button = view.findViewById(R.id.btnMqtt);
        Button test = view.findViewById(R.id.btnTest);
        FloatingActionButton btnAgregar = view.findViewById(R.id.btnAgregar);
        Button btnIP = view.findViewById(R.id.btnIP);
        final TextView txtIP = view.findViewById(R.id.txtIP);
        dispDatabaseAdapter=new DispDatabaseAdapter(getActivity().getApplicationContext());
        dispDatabaseAdapter=dispDatabaseAdapter.open();
        Button btnBD = view.findViewById(R.id.btnBD);
        final TextView txtBD = view.findViewById(R.id.txtBD);

        /*Fragmento de codigo que obtiene la ip que tiene el movil actualmente conectada y calcula su subred para el escaneo de dispositivos sonoff*/

        wifi= (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        d=wifi.getDhcpInfo();
        ip_Add= intToIp(d.ipAddress);
        red = intToIp(d.ipAddress & d.netmask);


         class EncontrarDisp extends AsyncTask<Void, Integer, String> {

            @Override
            protected void onPreExecute() {

                /*Codigo que se ejecuta antes de iniciar el escaneo. Hace visible la barra de progreso y presenta un texto de buscando dispositivos.*/

                ipBar = view.findViewById(R.id.ipBar);
                ipBar.setVisibility(View.VISIBLE);
              //  Toast.makeText(getContext(), "Buscando Dispositvos..." ,Toast.LENGTH_LONG).show();
               // Toast.(Gravity.CENTER, 5, 5); // last two args are X and Y are used for setting position

                Toast.makeText(getContext(), "Escaneando la Red. Por favor, espere... ", Toast.LENGTH_LONG).show();


              super.onPreExecute();

            }

            protected String doInBackground(Void... params) {

                /*Codigo que escanea la red local para encontrar dispositivos sonoff con tasmota*/

                StringBuilder sb=null;
                BufferedReader reader=null;
                String serverResponse=null;

                String ip = "";
                System.out.println(red);

                /*Dependiendo de la subred del movil se toma el rango para buscar dispositivos*/

                if(red.startsWith("10")){
                    ip = "10.0.0.";
                }else if (red.startsWith("192.168.")){
                    ip="192.168.0";
                }


                int i=1;

                while( i<256) {

                    try {

                        URL url = new URL("http://"+ip+  i + "/cm?cmnd=Module");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                        connection.setConnectTimeout(100);
                        connection.setRequestMethod("GET");
                        connection.connect();
                        int statusCode = connection.getResponseCode();

                        if (statusCode == 200 ) {
                            sb=new StringBuilder();
                            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                            String line;
                            while((line=reader.readLine())!= null){
                                sb.append(line + "\n");
                            }

                        }

                        connection.disconnect();
                        if(sb!=null && statusCode == 200) {
                            serverResponse = sb.toString();
                            if(serverResponse.startsWith("{\"Module\"")){
                                /* Si la respuesta a la peticion empieza con {Module se asume que el dispositivo es el que buscamos.*/
                               ips.add("10.0.0."+i);
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        if (reader != null){
                            try {
                                reader.close();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                    i++;

                }
                return serverResponse;

            }
            @Override
            protected void onPostExecute(String result) {

                /*Luego de terminar el scan se ejecuta esto para poner invisible la barra de progreso y para introducir el dispositivo a la lista y a la base de
                * datos*/

                ipBar.setVisibility(View.INVISIBLE);
                //System.out.println(result);
                for(String ip: ips){

                    /*Este codigo se ejecuta en un bucle por si existe la posibilidad de mas de un dispositivo en la subred*/

                    System.out.println(ip);
                    txtIP.setText(ip);
                    Opcion1Fragment.misDispositivos.add(new Dispositivo(ip,"AA-AA-AA-AA", "sonoff","cmnd/sonoff/power",""));
                    //dispDatabaseAdapter.insertEntry("1" ,"sonoff",ip , "AA-AA-AA-AA","cmnd/sonoff/power");
                }
                super.onPostExecute(result);

            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
            }
        }





        btnIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Se ejecuta el codigo EncontrarDisp que se encuentra en la clase anidada superior*/

                System.out.println(new EncontrarDisp().execute());
            }
        });

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Luego de haber encontrado un dispositivo asumiendo que este esta en la posicion 0 de la lista
                * se publica un mensaje blink al mismo*/

                MQTT cliente = new MQTT(Opcion1Fragment.misDispositivos.get(0).getTopicMQTT(),"3");
                cliente.publishMssg();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {

            /*Este codigo inicia una activada mqtt donde se puede especificar todos los parametros de conexion para conectarse a un broker, publicar y suscribirse
            * a un topico */

            @Override
            public void onClick(View view) {
                /*Codigo para iniciar una nueva actividad en un boton*/

                Intent i = new Intent(getActivity(), MqttActivity.class);
                startActivity(i);
            }
        });

        btnAgregar.setOnClickListener(new View.OnClickListener() {

            /*Actividad para escanear las redes wifi para encontrar un dispositivo a configurar nuevo (En sus primeros pasos)*/

            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),WiFiScannerActivity.class);
                startActivity(i);
            }
        });

        btnBD.setOnClickListener(new View.OnClickListener() {

            /*Codigo para mostrar una entrada de la base de datos donde el nombre de dispositivo sea sonoff*/

            @Override
            public void onClick(View view) {
                txtBD.setText(dispDatabaseAdapter.getSinlgeEntry("sonoff"));
            }
        });

        return view;
    }

    public String intToIp(int i) {
        /*Codigo para convertir la ip que s eencuentra conectado el movil de entero a formato (X.X.X.X) */


        return ( i & 0xFF) + "." + ((i >> 8 ) & 0xFF) + "." + ((i >> 16 ) & 0xFF) +"." + ((i >> 24 ) & 0xFF ) ;

    }



}
