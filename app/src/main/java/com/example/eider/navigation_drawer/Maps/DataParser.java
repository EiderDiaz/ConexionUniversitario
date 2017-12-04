package com.example.eider.navigation_drawer.Maps;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Eider on 30/10/2017.
 */

public class DataParser {


    private HashMap <String,String> getDuration(JSONArray googleDirectionsJson){
        HashMap<String,String> googleDirectionsMap = new HashMap<>();
        String duration ="";
        String distance = "";
        Log.d("Json response:",googleDirectionsJson.toString());
        try {
            duration= googleDirectionsJson.getJSONObject(0).getJSONObject("duration").getString("legs");
            distance= googleDirectionsJson.getJSONObject(0).getJSONObject("distance").getString("legs");
            googleDirectionsMap.put("duration",duration);
            googleDirectionsMap.put("distance",distance);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return googleDirectionsMap;
    }
    private HashMap<String,String>  getPlace(JSONObject googlePlaceJson){

        HashMap<String,String> googlePlaceMap = new HashMap<>();
        String placeName = "-NA-";
        String vicinity = "-NA-";
        String latitude = "";
        String longitude = "";
        String reference = "";
        try {
        if (!googlePlaceJson.isNull("name")) {
            placeName= googlePlaceJson.getString("name");
        }
        if (!googlePlaceJson.isNull("vicinity")){
            vicinity = googlePlaceJson.getString("vicinity");
        }
        latitude= googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude= googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lng");
            reference = googlePlaceJson.getString("reference");
            googlePlaceMap.put("place_name",placeName);
            googlePlaceMap.put("vicinity",vicinity);
            googlePlaceMap.put("lat",latitude);
            googlePlaceMap.put("lng",longitude);
            googlePlaceMap.put("reference",reference);


        }
            catch (Exception e){

            }


    return googlePlaceMap;
    }

    private List<HashMap<String,String>> getPlaces(JSONArray jsonArray){
        int count = jsonArray.length();
        List<HashMap<String,String>> placeList = new ArrayList<>();
        HashMap<String,String> placeMap = null;

        for (int i=0;i<count;i++ ){
            try {
                placeMap= getPlace((JSONObject) jsonArray.get(i));
                placeList.add(placeMap);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    return placeList;
    }


    public  List<HashMap<String,String>> parse (String jsonData){
        JSONArray jsonArray = null;
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getPlaces(jsonArray);
    }

    public HashMap<String,String> parseDirections(String JsonData){
        JSONArray jsonArray = null;
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(JsonData);
            jsonArray = jsonObject.getJSONArray("routes").getJSONObject(0).getJSONArray("legs");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getDuration(jsonArray);
    }
}
