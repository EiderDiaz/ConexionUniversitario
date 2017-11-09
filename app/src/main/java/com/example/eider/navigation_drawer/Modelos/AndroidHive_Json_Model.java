package com.example.eider.navigation_drawer.Modelos;

/**
 * Created by Eider on 06/11/2017.
 */

public class AndroidHive_Json_Model {
String Nombre,Correo,Telefono,id;

    public AndroidHive_Json_Model(String id,String nombre, String correo, String telefono) {
        Nombre = nombre;
        Correo = correo;
        Telefono = telefono;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AndroidHive_Json_Model() {

    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }
}
