package com.example.eider.navigation_drawer.Activity;

/**
 * Created by Eider on 06/12/2017.
 */

public class rutaBeta {
    String distancia;
    String conductor;
    String destino;
    String plazas;
    String origen;
    String datetime;

    public rutaBeta() {
    }

    public rutaBeta(String distancia, String conductor, String destino, String plazas, String origen, String datetime) {
        this.distancia = distancia;
        this.conductor = conductor;
        this.destino = destino;
        this.plazas = plazas;
        this.origen = origen;
        this.datetime = datetime;
    }

    public String getDistancia() {
        return distancia;
    }

    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }

    public String getConductor() {
        return conductor;
    }

    public void setConductor(String conductor) {
        this.conductor = conductor;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getPlazas() {
        return plazas;
    }

    public void setPlazas(String plazas) {
        this.plazas = plazas;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
