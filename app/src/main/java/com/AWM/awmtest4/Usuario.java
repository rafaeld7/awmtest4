package com.AWM.awmtest4;

import java.util.ArrayList;
import java.util.Objects;

public class Usuario {
     private String nombre;
     private String idUsuario;
     private String correo;
     private String contrasena;
     private String telefono;
     private ArrayList<Dispositivo> misDispositivos;

    public Usuario(String nombre, String idUsuario, String correo, String contrasena, String telefono, ArrayList<Dispositivo> misDispositivos) {
        this.nombre = nombre;
        this.idUsuario = idUsuario;
        this.correo = correo;
        this.contrasena = contrasena;
        this.telefono = telefono;
        this.misDispositivos = misDispositivos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(nombre, usuario.nombre) &&
                Objects.equals(idUsuario, usuario.idUsuario) &&
                Objects.equals(correo, usuario.correo) &&
                Objects.equals(contrasena, usuario.contrasena) &&
                Objects.equals(telefono, usuario.telefono) &&
                Objects.equals(misDispositivos, usuario.misDispositivos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, idUsuario, correo, contrasena, telefono, misDispositivos);
    }

    public ArrayList<Dispositivo> getMisDispositivos() {
        return misDispositivos;
    }

    public void setMisDispositivos(ArrayList<Dispositivo> misDispositivos) {
        this.misDispositivos = misDispositivos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
