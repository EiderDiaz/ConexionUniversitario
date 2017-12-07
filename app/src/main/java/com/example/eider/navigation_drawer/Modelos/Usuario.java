package com.example.eider.navigation_drawer.Modelos;

/**
 * Created by Eider on 06/12/2017.
 */

public class Usuario {
    String nombre;
    String apellido;
    String fecha_activacion;
    String status;
    String telefono;
    String correo;
    String fb_id;
    String matriculado;

    public Usuario() {
    }

    public Usuario(String nombre, String apellido, String correo, String fb_id) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.fb_id = fb_id;
    }

    public Usuario(String nombre, String apellido, String fecha_activacion, String status, String telefono, String correo, String fb_id, String matriculado) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fecha_activacion = fecha_activacion;
        this.status = status;
        this.telefono = telefono;
        this.correo = correo;
        this.fb_id = fb_id;
        this.matriculado = matriculado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getFecha_activacion() {
        return fecha_activacion;
    }

    public void setFecha_activacion(String fecha_activacion) {
        this.fecha_activacion = fecha_activacion;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFb_id() {
        return fb_id;
    }

    public void setFb_id(String fb_id) {
        this.fb_id = fb_id;
    }

    public String getMatriculado() {
        return matriculado;
    }

    public void setMatriculado(String matriculado) {
        this.matriculado = matriculado;
    }
}
