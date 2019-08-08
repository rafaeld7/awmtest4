package com.AWM.awmtest4;

import java.util.Objects;

public class ConfiguracionDispositivo {
    private String ID;
    private String topicoMQTT;
    private String tipo;

    public ConfiguracionDispositivo(String ID, String topicoMQTT, String tipo) {
        this.ID = ID;
        this.topicoMQTT = topicoMQTT;
        this.tipo = tipo;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTopicoMQTT() {
        return topicoMQTT;
    }

    public void setTopicoMQTT(String topicoMQTT) {
        this.topicoMQTT = topicoMQTT;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfiguracionDispositivo that = (ConfiguracionDispositivo) o;
        return Objects.equals(ID, that.ID) &&
                Objects.equals(topicoMQTT, that.topicoMQTT) &&
                Objects.equals(tipo, that.tipo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, topicoMQTT, tipo);
    }
}
