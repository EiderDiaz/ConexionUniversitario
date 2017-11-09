package com.example.eider.navigation_drawer.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eider.navigation_drawer.Modelos.AndroidHive_Json_Model;
import com.example.eider.navigation_drawer.R;

import java.util.ArrayList;

/**
 * Created by Eider on 06/11/2017.
 */

public class AndroidHive_Json_Adapter  extends RecyclerView.Adapter<AndroidHive_Json_Adapter.MyViewHolder>{


    private ArrayList<AndroidHive_Json_Model> ListaModelo;
public AndroidHive_Json_Adapter(ArrayList<AndroidHive_Json_Model> listamodelo){
    ListaModelo= listamodelo;

}

    public class MyViewHolder  extends RecyclerView.ViewHolder {
        public TextView nombre,correo,telefono;
        public MyViewHolder(View view){
            super(view);
            nombre = (TextView) view.findViewById(R.id.name);
            correo = (TextView) view.findViewById(R.id.email);
            telefono = (TextView) view.findViewById(R.id.mobile);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AndroidHive_Json_Model datosvuelo = ListaModelo.get(position);
        holder.nombre.setText(datosvuelo.getNombre());
        holder.correo.setText(datosvuelo.getCorreo());
        holder.telefono.setText(datosvuelo.getTelefono());
    }

    @Override
    public int getItemCount() {
        return ListaModelo.size();
    }


}
