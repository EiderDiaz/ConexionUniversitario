package com.example.eider.navigation_drawer.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eider.navigation_drawer.R;

/**
 * Created by Eider on 02/11/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private String myData[];
    public  RecyclerAdapter (String[] data){
            myData = data;
        }


    public class MyViewHolder  extends RecyclerView.ViewHolder {
        public TextView nombre;

        public MyViewHolder(View view) {
            super(view);
            nombre = (TextView) view.findViewById(R.id.titulo);

        }

    }

    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.renglon_recyclerview_simple, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(itemView);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.MyViewHolder holder, int position) {
            holder.nombre.setText(myData[position]);
    }



    @Override
    public int getItemCount() {
        return  myData.length;
    }


}
