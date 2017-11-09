package com.example.eider.navigation_drawer.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.eider.navigation_drawer.Adapters.AndroidHive_Json_Adapter;
import com.example.eider.navigation_drawer.Adapters.MoviesAdapter;
import com.example.eider.navigation_drawer.Adapters.RecyclerAdapter;
import com.example.eider.navigation_drawer.Modelos.AndroidHive_Json_Model;
import com.example.eider.navigation_drawer.Modelos.Movie;
import com.example.eider.navigation_drawer.Modelos.MovieResponse;
import com.example.eider.navigation_drawer.Other.HttpHandler;
import com.example.eider.navigation_drawer.R;
import com.example.eider.navigation_drawer.REST.ApiClient;
import com.example.eider.navigation_drawer.REST.ApiInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PhotoFragment extends Fragment {
    private String TAG = Fragment.class.getSimpleName();

    private RecyclerView recyclerView;
    //RecyclerAdapter recycler_simple;
    CollapsingToolbarLayout collapsingToolbarLayout;
    private final static String API_KEY = "1ab15817353555a79ecfc10362b60413";
    //lo de el JSON
    private ProgressDialog pDialog;

    // URL to get contacts JSON
   // private static String url = "https://api.androidhive.info/contacts/";

    ArrayList<AndroidHive_Json_Model> contactList;
    // TODO: 06/11/2017 cambiar hashmap por un arraylist de modelo
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
            recyclerView.setItemAnimator(new DefaultItemAnimator());
        }
        catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        try{
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<MovieResponse> call = apiService.getTopRatedMovies(API_KEY);
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    List<Movie> movies = response.body().getResults();
                    recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getContext()));

                }

                @Override
                public void onFailure(Call<MovieResponse>call, Throwable t) {
                    // Log error here since request failed
                    Log.e(TAG, t.toString());
                    Toast.makeText(getContext(), "error: "+t.toString(), Toast.LENGTH_SHORT).show();
                }
            });

        }catch (Exception ex){
            Toast.makeText(getContext(), "error en el la parte del json"+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return Rootview;
    }

}
