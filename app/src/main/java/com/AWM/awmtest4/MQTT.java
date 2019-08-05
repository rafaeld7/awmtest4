package com.AWM.awmtest4;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import static com.AWM.awmtest4.Mqtt.Constants.CLIENT_ID;
import static com.AWM.awmtest4.Mqtt.Constants.MQTT_BROKER_URL;
import static com.AWM.awmtest4.Mqtt.Constants.MQTT_CLIENT_PW;
import static com.AWM.awmtest4.Mqtt.Constants.MQTT_CLIENT_UN;

public class MQTT {
    private MqttClient myClient;
    private String topic;
    private String mensaje;
    private MemoryPersistence persistence;
    private int qos = 0;

    public MQTT (String topic,String mensaje){
        this.topic =topic;
        this.mensaje=mensaje;
    }

    public void publishMssg(){
        try {
            myClient = new MqttClient(MQTT_BROKER_URL,CLIENT_ID,persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setUserName(MQTT_CLIENT_UN);
            connOpts.setPassword(MQTT_CLIENT_PW);
            System.out.println("Connecting to broker: "+MQTT_BROKER_URL);
            myClient.connect(connOpts);
            System.out.println("Connected to broker");
            System.out.println("Publishing message:"+mensaje);
            MqttMessage message = new MqttMessage(mensaje.getBytes());
            message.setQos(qos);
            myClient.publish(topic, message);
            System.out.println("Message published");
            myClient.disconnect();
            myClient.close();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
