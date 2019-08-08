package com.AWM.awmtest4;

/*Clase dispositivo la cual tiene todos los parametros que puede tener un sonoff incluyendo su topico para publicar en MQTT*/

import java.util.Objects;

public class Dispositivo {
    private String ip;
    private String macAdd;
    private String nombre;
    private String topicMQTT;
    private String idConfiguracion;

    public Dispositivo(String ip, String macAdd, String nombre, String topicMQTT, String idConfiguracion) {
        this.ip = ip;
        this.macAdd = macAdd;
        this.nombre = nombre;
        this.topicMQTT = topicMQTT;
        this.idConfiguracion = idConfiguracion;
    }

    public String getIdConfiguracion() {
        return idConfiguracion;
    }

    public void setIdConfiguracion(String idConfiguracion) {
        this.idConfiguracion = idConfiguracion;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dispositivo that = (Dispositivo) o;
        return Objects.equals(ip, that.ip) &&
                Objects.equals(macAdd, that.macAdd) &&
                Objects.equals(nombre, that.nombre) &&
                Objects.equals(topicMQTT, that.topicMQTT) &&
                Objects.equals(idConfiguracion, that.idConfiguracion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, macAdd, nombre, topicMQTT, idConfiguracion);
    }
}
