package com.AWM.awmtest4.Mqtt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.AWM.awmtest4.R;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MqttActivity extends AppCompatActivity {

    private EditText etSubTopic ;
    private EditText etPubTopic;
    private EditText etPubMsg;
    private EditText etBroker;
    private EditText etUName;
    private EditText etPWord;
    private Button btnConn, btnPub,btnSub;
    private TextView subscribedMsg;
    private ToggleButton btnMqtt;
    String msg_new="";

    private MqttAndroidClient client;
    private String TAG = "MainActivity";
    private PahoMqttClient pahoMqttClient;
    private String clientid = "";
    private Timer myTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mqtt);

        etSubTopic= findViewById(R.id.etSubscribe);
        etPubTopic =findViewById(R.id.etPublish);
        etPubMsg =  findViewById(R.id.etMsg);
        etBroker =  findViewById(R.id.etBroker);
        etUName =   findViewById(R.id.etClientUN);
        etPWord =   findViewById(R.id.etClientPW);
        btnConn = findViewById(R.id.btnConn);
        btnPub = findViewById(R.id.btnPub);
        btnSub = findViewById(R.id.btnSub);
        subscribedMsg = findViewById(R.id.subscribedMsg);
        btnMqtt = findViewById(R.id.btnMqtt);


        String urlBroker    = etBroker.getText().toString().trim();
        String username     = etUName.getText().toString().trim();
        String password     = etPWord.getText().toString().trim();

        pahoMqttClient = new PahoMqttClient();
        client = pahoMqttClient.getMqttClient(  getApplicationContext(),                        // Connect to MQTT Broker
                urlBroker,
                clientid,
                username,
                password
        );


        //Conectar broker
        btnConn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //String urlBroker = etBroker.getText().toString().trim();
                String urlBroker = "tcp://"+etBroker.getText().toString().trim()+":1883";
                String username  = etUName.getText().toString().trim();
                String password  = etPWord.getText().toString().trim();

                Random r = new Random();        //Unique Client ID for connection
                int i1 = r.nextInt(5000 - 1) + 1;
                clientid = "mqtt" + i1;
                if(pahoMqttClient.mqttAndroidClient.isConnected() ) {
                    //Disconnect and Reconnect to  Broker
                    try {
                        //Disconnect from Broker
                        pahoMqttClient.disconnect(client);
                        //Connect to Broker
                        client = pahoMqttClient.getMqttClient(getApplicationContext(), urlBroker, clientid, username, password);
                        //Set Mqtt Message Callback
                        mqttCallback();
                    }
                    catch (MqttException e) {
                    }
                }
                else {
                    //Connect to Broker
                    client = pahoMqttClient.getMqttClient(getApplicationContext(), urlBroker, clientid, username, password);
                    //Set Mqtt Message Callback
                    mqttCallback();
                }




            }
        });
        //Publicar mensaje
        btnPub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!pahoMqttClient.mqttAndroidClient.isConnected() ) {
                    msg_new = "Currently not connected to MQTT broker: Must be connected to publish message to a topic\r\n";
                    subscribedMsg.append(msg_new);

                }
                //Publish non-blank message
                String pubtopic = etPubTopic.getText().toString().trim();
                String msg      = etPubMsg.getText().toString().trim();
                if (!msg.isEmpty()) {
                    try {
                        pahoMqttClient.publishMessage(client, msg, 1, pubtopic);
                        msg_new = "Message sent to pub topic: " + etPubTopic.getText() + "\r\n";
                        subscribedMsg.append(msg_new);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }



            }
        });
        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!pahoMqttClient.mqttAndroidClient.isConnected() ) {
                    msg_new = "Currently not connected to MQTT broker: Must be connected to subscribe to a topic\r\n";
                    subscribedMsg.append(msg_new);

                }
                String topic = etSubTopic.getText().toString().trim();
                if (!topic.isEmpty()) {
                    try {
                        pahoMqttClient.subscribe(client, topic, 1);
                        msg_new = "Added subscription topic: " + etSubTopic.getText() + "\r\n";
                        subscribedMsg.append(msg_new);

                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        mqttCallback();

        //Create Timer to report MQTT connection status every 1 second
        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                ScheduleTasks();
            }

        }, 0, 1000);


//        scrollView.post(new Runnable()
//        {
//            public void run()
//            {
//                scrollView.smoothScrollTo(0, subscribedMsg.getBottom());
//            }
//        });

        btnMqtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pubtopic = "cmnd/sonoff/power";
                String msg      = "toggle";
                if (!msg.isEmpty()) {
                    try {
                        pahoMqttClient.publishMessage(client, msg, 1, pubtopic);
                        msg_new = "Message sent to pub topic: " + etPubTopic.getText() + "\r\n";
                        subscribedMsg.append(msg_new);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    private void ScheduleTasks()
    {
        //This method is called directly by the timer
        //and runs in the same thread as the timer.

        //We call the method that will work with the UI
        //through the runOnUiThread method.
        this.runOnUiThread(RunScheduledTasks);
    }

    private Runnable RunScheduledTasks = new Runnable() {
        public void run() {
            //This method runs in the same thread as the UI.

            //Check MQTT Connection Status
            TextView tvMessage  =  findViewById(R.id.etStatus);
            String msg_new="";

            if(pahoMqttClient.mqttAndroidClient.isConnected() ) {
                msg_new = "Connected\r\n";
                tvMessage.setTextColor(0xFF00FF00); //Green if connected
                tvMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            }
            else {
                msg_new = "Disconnected\r\n";
                tvMessage.setTextColor(0xFFFF0000); //Red if not connected
                tvMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            }
            tvMessage.setText(msg_new);
        }
    };

    protected void mqttCallback() {
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                //msg("Connection lost...");
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                TextView tvMessage =  findViewById(R.id.subscribedMsg);
                if(topic.equals("mycustomtopic1")) {
                    //Add custom message handling here (if topic = "mycustomtopic1")
                }
                else if(topic.equals("mycustomtopic2")) {
                    //Add custom message handling here (if topic = "mycustomtopic2")
                }
                else {
                    String msg = "topic: " + topic + "\r\nMessage: " + message.toString() + "\r\n";
                    tvMessage.append( msg);

                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }

}
