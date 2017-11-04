package com.example.eider.navigation_drawer.Maps;

import android.content.Context;
import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Eider on 30/10/2017.
 */

public class GetNearbyPlacesData extends AsyncTask<Object,String,String> {

    String GooglePlacesData;
    GoogleMap mMap;
    String Url;
    Context context;

    public GetNearbyPlacesData(Context contexto){
        this.context =contexto;
    }

    @Override
    protected String doInBackground(Object... params) {
        mMap = (GoogleMap)params[0];
        Url = (String)params[1];

        DownloadUrl downloadUrl = new DownloadUrl();

        try {
            GooglePlacesData = downloadUrl.readUrl(Url,context);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return GooglePlacesData;
    }

    @Override
    protected void onPostExecute(String s) {

List<HashMap<String,String>> nearbyPlacesList = null;
        DataParser parser = new DataParser();
        nearbyPlacesList= parser.parse(s);
        showNearbyPlaces(nearbyPlacesList);
    }

private  void  showNearbyPlaces (List<HashMap<String,String>> nearbyPlacesList){

    for (int i=0; i<nearbyPlacesList.size();i++) {
        MarkerOptions markerOptions = new MarkerOptions();
        HashMap<String, String> googlePlace = nearbyPlacesList.get(i);
        String placeName = googlePlace.get("place_name");
        String vicinity = googlePlace.get("vicinity");
        double lat = Double.parseDouble(googlePlace.get("lat"));
        double lng = Double.parseDouble(googlePlace.get("lng"));
        LatLng latLng = new LatLng(lat, lng);
        markerOptions.position(latLng);
        markerOptions.title(placeName + "::" + vicinity);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(10));


    }

    }
}


