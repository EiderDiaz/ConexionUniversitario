package com.example.eider.navigation_drawer.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eider.navigation_drawer.Activity.rutaBeta;
import com.example.eider.navigation_drawer.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eider on 06/12/2017.
 */

public class Ruta_Econtradas_Adapter extends  RecyclerView.Adapter<Ruta_Econtradas_Adapter.MyViewHolder>  {
    List<rutaBeta> ListaDeRutas;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView conductor, distancia;

        public MyViewHolder(View view) {
            super(view);
            conductor= (TextView) view.findViewById(R.id.conductor_rutaencontrada);
            distancia= (TextView) view.findViewById(R.id.distancia_rutaencontrada);


        }

    }
    public Ruta_Econtradas_Adapter(List<rutaBeta> ListaDeRutas) {
        this.ListaDeRutas = ListaDeRutas;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.renglon_rutas_encontradas, parent, false);
        return new Ruta_Econtradas_Adapter.MyViewHolder(itemView);    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        rutaBeta ruta = ListaDeRutas.get(position);
        String[] splitconductor = ruta.getConductor().split("/");
        holder.conductor.setText("Conductor:\n"+splitconductor[1]);
        holder.distancia.setText("distancia:\n"+ruta.getDistancia());

    }

    @Override
    public int getItemCount() {
       return ListaDeRutas.size();
    }




}
