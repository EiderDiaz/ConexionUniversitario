package com.example.eider.navigation_drawer.REST;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import com.example.eider.navigation_drawer.Activity.rutaBeta;
import com.example.eider.navigation_drawer.Modelos.MovieResponse;

import java.util.List;

/**
 * Created by Eider on 08/11/2017.
 */

public interface ApiInterface {
    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<MovieResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);


    @GET("/api/insertar_ruta")
    Call<ResponseBody> PublicarRuta(
            @Query("fb_id") String fb_id,
            @Query("origen") String origen,
            @Query("destino") String destino,
            @Query("datetime") String datetime,
            @Query("plazas") String plazas,
            @Query("distancia") String distancia

    );

    @GET("/api/buscar_ruta/")
    Call<List<rutaBeta>> BuscarRuta();

}
