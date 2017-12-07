package com.example.eider.navigation_drawer.Modelos;

/**
 * Created by Eider on 06/12/2017.
 */

public class Macth {
 String status;
    String match_id;

    public Macth() {
    }

    public Macth(String status, String match_id) {
        this.status = status;
        this.match_id = match_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMatch_id() {
        return match_id;
    }

    public void setMatch_id(String match_id) {
        this.match_id = match_id;
    }
}
