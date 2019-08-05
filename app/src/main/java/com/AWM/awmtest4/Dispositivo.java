package com.AWM.awmtest4;

public class Dispositivo {
    private String ip;
    private String macAdd;
    private String nombre;
    private String topicMQTT;

    public Dispositivo(String ip, String macAdd, String nombre, String topicMQTT) {
        this.ip = ip;
        this.macAdd = macAdd;
        this.nombre = nombre;
        this.topicMQTT = topicMQTT;
    }

    public String getTopicMQTT() {
        return topicMQTT;
    }

    public void setTopicMQTT(String topicMQTT) {
        this.topicMQTT = topicMQTT;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMacAdd() {
        return macAdd;
    }

    public void setMacAdd(String macAdd) {
        this.macAdd = macAdd;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
