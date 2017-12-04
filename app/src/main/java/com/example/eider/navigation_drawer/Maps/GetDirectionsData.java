package com.example.eider.navigation_drawer.Maps;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

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
        String[] directionsList = null;
        DataParser parser = new DataParser();
        directionsList = parser.parseDirections(s);
        displayDirections(directionsList);
       // Toast.makeText(context, "duracion:"+duration+"\ndistancia:"+distance, Toast.LENGTH_SHORT).show();

    }

    private void displayDirections(String[] directionsList) {
        int count = directionsList.length;
        for (int i=0;i<count;i++){
            PolylineOptions options = new PolylineOptions();
            options.color(Color.RED);
            options.width(10);
            options.addAll(PolyUtil.decode(directionsList[i]));
            mMap.addPolyline(options);

        }
    }
}
