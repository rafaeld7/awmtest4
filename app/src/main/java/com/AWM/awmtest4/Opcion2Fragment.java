package com.AWM.awmtest4;


import android.content.Context;
import android.content.Intent;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

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
    DhcpInfo d;
    WifiManager wifi;
    String ip_Add;
    String red;
    DispDatabaseAdapter dispDatabaseAdapter;

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


        wifi= (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        d=wifi.getDhcpInfo();
        ip_Add= intToIp(d.ipAddress);
        red = intToIp(d.ipAddress & d.netmask);


         class EncontrarDisp extends AsyncTask<Void, Integer, String> {

            @Override
            protected void onPreExecute() {
                ipBar = view.findViewById(R.id.ipBar);
                ipBar.setVisibility(View.VISIBLE);
                super.onPreExecute();

            }

            protected String doInBackground(Void... params) {
                StringBuilder sb=null;
                BufferedReader reader=null;
                String serverResponse=null;

                String ip = "";
                System.out.println(red);
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
                ipBar.setVisibility(View.INVISIBLE);
                //System.out.println(result);
                for(String ip: ips){
                    System.out.println(ip);
                    txtIP.setText(ip);
                    Opcion1Fragment.misDispositivos.add(new Dispositivo(ip,"AA-AA-AA-AA", "sonoff","cmnd/sonoff/power"));
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
                System.out.println(new EncontrarDisp().execute());
            }
        });

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MQTT cliente = new MQTT(Opcion1Fragment.misDispositivos.get(0).getTopicMQTT(),"3");
                cliente.publishMssg();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), MqttActivity.class);
                startActivity(i);
            }
        });

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),WiFiScannerActivity.class);
                startActivity(i);
            }
        });

        btnBD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtBD.setText(dispDatabaseAdapter.getSinlgeEntry("sonoff"));
            }
        });

        return view;
    }

    public String intToIp(int i) {

        return ( i & 0xFF) + "." + ((i >> 8 ) & 0xFF) + "." + ((i >> 16 ) & 0xFF) +"." + ((i >> 24 ) & 0xFF ) ;
//        return ((i >> 24 ) & 0xFF ) + "." +
//                ((i >> 16 ) & 0xFF) + "." +
//                ((i >> 8 ) & 0xFF) + "." +
//                ( i & 0xFF) ;
    }



}
