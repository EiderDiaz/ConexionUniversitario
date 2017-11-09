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
import com.example.eider.navigation_drawer.Adapters.RecyclerAdapter;
import com.example.eider.navigation_drawer.Modelos.AndroidHive_Json_Model;
import com.example.eider.navigation_drawer.Other.HttpHandler;
import com.example.eider.navigation_drawer.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class PhotoFragment extends Fragment {

    private RecyclerView recyclerView;
    //RecyclerAdapter recycler_simple;
    CollapsingToolbarLayout collapsingToolbarLayout;
   /* String [] data = new String[]{
            "Viaje 1","viaje 2","viaje 3","viaje 4","viaje 5","viaje 6","viaje 7","viaje 8",
            "viaje 9","viaje 10","viaje 11","viaje 12","viaje 13"};
*/
    //lo de el JSON
    private String TAG = Fragment.class.getSimpleName();
    private ProgressDialog pDialog;

    // URL to get contacts JSON
    private static String url = "https://api.androidhive.info/contacts/";

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
        try{
            contactList = new ArrayList<>();
            new GetContacts().execute();
        }catch (Exception ex){
            Toast.makeText(getContext(), "error en el la parte del json"+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        try {
            recyclerView = (RecyclerView) Rootview.findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager( new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

            recyclerView.setItemAnimator(new DefaultItemAnimator());
        }
        catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return Rootview;
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpHandler sh = new HttpHandler(getContext());
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);
            Toast.makeText(getContext(), "Response from url:"+ jsonStr, Toast.LENGTH_SHORT).show();


            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("contacts");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String id = c.getString("id");
                        String name = c.getString("name");
                        String email = c.getString("email");
                        String address = c.getString("address");
                        String gender = c.getString("gender");

                        // Phone node is JSON Object
                        JSONObject phone = c.getJSONObject("phone");
                        String mobile = phone.getString("mobile");
                        String home = phone.getString("home");
                        String office = phone.getString("office");

                        // tmp contact object for single contact
                        AndroidHive_Json_Model contact = new AndroidHive_Json_Model(id,name,email,mobile);

                        // adding contact to contact list
                        contactList.add(contact);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            try {

            }
            catch (Exception e){
                Toast.makeText(getContext(), e.getLocalizedMessage()+" error en el postexecute "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            AndroidHive_Json_Adapter recyclerAdapter = new AndroidHive_Json_Adapter(contactList);
            recyclerView.setAdapter(recyclerAdapter);

        }

    }
}
