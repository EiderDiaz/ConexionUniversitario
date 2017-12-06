package com.example.eider.navigation_drawer.Modelos;

/**
 * Created by Eider on 06/12/2017.
 */

public class RutasPublicadas {
    String tipo;     //contuctor o pasajero
    String origen; //cordenadas
    String destino;//cordenadas
    String datetime; //tiempo y hora
    String plazas; //plazas disponibles
    String distancia ; //km de distancia entre origen y destino

    public RutasPublicadas() {
    }

    public RutasPublicadas(String tipo, String origen, String destino, String datetime, String plazas, String distancia) {
        this.tipo = tipo;
        this.origen = origen;
        this.destino = destino;
        this.datetime = datetime;
        this.plazas = plazas;
        this.distancia = distancia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getPlazas() {
        return plazas;
    }

    public void setPlazas(String plazas) {
        this.plazas = plazas;
    }

    public String getDistancia() {
        return distancia;
    }

    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }
}
