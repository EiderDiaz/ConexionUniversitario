package com.example.eider.navigation_drawer.Maps;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Eider on 29/11/2017.
 */

public class GetDirectionsData extends AsyncTask<Object,String,String> {

   GoogleMap mMap;
    String url;
    String GoogleDirectionsData;
    Context context;
    String duration,distance;

    public GetDirectionsData(Context contexto){
        this.context =contexto;
    }
    @Override
    protected String doInBackground(Object... params) {
        mMap = (GoogleMap)params[0];
        url = (String)params[1];

        DownloadUrl downloadUrl = new DownloadUrl();

        try {
            GoogleDirectionsData = downloadUrl.readUrl(url,context);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return GoogleDirectionsData;
    }

    @Override
    protected void onPostExecute(String s) {
        HashMap<String,String> directionsList = null;
        DataParser parser = new DataParser();
        directionsList = parser.parseDirections(s);
        duration = directionsList.get("duration");
        distance =  directionsList.get("distance");
        Toast.makeText(context, "duracion:"+duration+"\ndistancia:"+distance, Toast.LENGTH_SHORT).show();

    }
}
