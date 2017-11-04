package com.example.eider.navigation_drawer.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.eider.navigation_drawer.Adapters.RecyclerAdapter;
import com.example.eider.navigation_drawer.R;


public class PhotoFragment extends Fragment {

    private RecyclerView recyclerView;
    RecyclerAdapter recycler_simple;
    CollapsingToolbarLayout collapsingToolbarLayout;
    String [] data = new String[]{
            "Viaje 1","viaje 2","viaje 3","viaje 4","viaje 5","viaje 6","viaje 7","viaje 8",
            "viaje 9","viaje 10","viaje 11","viaje 12","viaje 13"};

    public PhotoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View Rootview = inflater.inflate(R.layout.fragment_photo, container, false);
        collapsingToolbarLayout =(CollapsingToolbarLayout) Rootview.findViewById(R.id.collapToolbar);
        collapsingToolbarLayout.setTitle("Lista de viajes:");
        try {
            recyclerView = (RecyclerView) Rootview.findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager( new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
            recycler_simple = new RecyclerAdapter(data);
            recyclerView.setAdapter(recycler_simple);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
        }
        catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return Rootview;
    }
}