package com.example.eider.navigation_drawer.Fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eider.navigation_drawer.Activity.rutaBeta;
import com.example.eider.navigation_drawer.Adapters.Ruta_Econtradas_Adapter;
import com.example.eider.navigation_drawer.Maps.GetDirectionsData;
import com.example.eider.navigation_drawer.Maps.GetNearbyPlacesData;
import com.example.eider.navigation_drawer.Other.RecyclerItemClickListener;
import com.example.eider.navigation_drawer.R;
import com.example.eider.navigation_drawer.REST.ApiInterface;
import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment implements Validator.ValidationListener,
        OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener
        ,GoogleMap.OnMarkerClickListener,GoogleMap.OnMarkerDragListener {
    // MapView mMapView;
    private GoogleMap googleMap;
    private GoogleApiClient client;
    private Location lastlocation;
    private LocationRequest locationRequest;
    private Marker currentLocationMarker;
    private ImageButton BotonOrigen,BotonDestino;
    private Button boton_fecha;
    private FloatingActionButton fab;
    AlertDialog modalorigen;
    double end_latitude,end_longitude;
    Calendar calendar ;
    int Year, Month, Day ;

    // TODO: 29/11/2017 de un principio cargar un tipo de informacion donde despliegue un formulario de mapa para poner la direccion de casa y escuela
    LatLng casa = new LatLng(25.82261, -108.98236);
    LatLng ITLM = new LatLng(25.79869, -108.97675);

    @NotEmpty(message = "debes de llenar este campo")
    MaterialBetterSpinner spinner_publicar_como;
    @NotEmpty(message = "debes de llenar este campo")
    EditText input_origen;
    @NotEmpty(message = "debes de llenar este campo")
    EditText input_destino;
    @NotEmpty(message = "debes de llenar este campo")
    MaterialBetterSpinner spinner_plazas_disponibles;

    public static final int PERMISSION_REQUEST_LOCATION_CODE = 99;

    String Fechayhora;

    Validator validator;
    ArrayList<String> ListaTipoDeUsuario = new ArrayList<String>() {{
        add("Conductor");
        add("Pasajero");
    }};
    ArrayList<String> ListaPlazasDisponibles = new ArrayList<String>() {{
        add("1");
        add("2");
        add("3");
        add("4");
        add("5");
        add("6");
    }};
    String numerodeplazasseleccionado ="";
    String publicarComo="";

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View Rootview = inflater.inflate(R.layout.fragment_home, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            CheckLocationPermission();
        }
        fab = (FloatingActionButton) Rootview.findViewById(R.id.fab);
        fab.setOnClickListener(clickListener);
        boton_fecha = (Button) Rootview.findViewById(R.id.boton_modal_fecha);
        cargar_Inputs_y_Spinners(Rootview);
        try {
            SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            boton_fecha.setOnClickListener(clickListener);
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getLocalizedMessage() + " error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        ModalSeleccionarOrigen();
        return Rootview;
    }

    //https://maps.googleapis.com/maps/api/geocode/json?latlng=40.714224,-73.961452&location_type=ROOFTOP&result_type=street_address&key=AIzaSyA1qIIpoUyhjTtzim7gBA_6gBO2r6hpuNo


    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                // TODO: 17/09/2017 cambiar las clases por las que vas a crear despues
                case R.id.boton_modal_fecha: //si
                    showChangeLangDialog();
                    break;
                case R.id.fab: //si
                    if (input_origen.getText().toString().equals("")) {
                        ModalSeleccionarOrigen();
                    }
                    else{
                        ModalVerificarDatosCorrectos();
                    }
                    break;
                case R.id.origen_casa: //si
                    googleMap.clear();
                    // TODO: 29/11/2017 tu origen es casa
                    Toast.makeText(getContext(), "tu origen es casa", Toast.LENGTH_SHORT).show();
               MarkerOptions markerOrigen= new MarkerOptions();
                    markerOrigen.position(casa);
                    markerOrigen.title("Origen");
                    markerOrigen.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    googleMap.addMarker(markerOrigen);
                    input_origen.setText("Casa");
                    input_destino.setText("Instituo tecnologico de los mochis");
                    MarkerOptions markerDestino= new MarkerOptions();
                    markerDestino.position(ITLM);
                    markerDestino.title("Destino");
                    markerDestino.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                    googleMap.addMarker(markerDestino);
                    modalorigen.dismiss();
                    // googleMap.moveCamera(CameraUpdateFactory.newLatLng(ITLM));
                    //googleMap.animateCamera(CameraUpdateFactory.zoomTo(12));

                    break;
                case R.id.origen_escuela: //si
                    googleMap.clear();
                    // TODO: 29/11/2017 tu origen es casa
                    Toast.makeText(getContext(), "tu origen es casa", Toast.LENGTH_SHORT).show();
                    MarkerOptions markerOrigen2= new MarkerOptions();
                    markerOrigen2.position(ITLM);
                    markerOrigen2.title("Origen");
                    markerOrigen2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    googleMap.addMarker(markerOrigen2);
                    input_destino.setText("Casa");
                    input_origen.setText("Instituo tecnologico de los mochis");
                    MarkerOptions markerDestino2= new MarkerOptions();
                    markerDestino2.position(casa);
                    markerDestino2.title("Destino");
                    markerDestino2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                    googleMap.addMarker(markerDestino2);
                    modalorigen.dismiss();
                    break;
            }
        }
    };

    private void ModalVerificarDatosCorrectos() {
       /* googleMap.clear();
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(end_latitude,end_longitude));
        markerOptions.title("Destination");*/
        float result[] = new float[10];
        Location.distanceBetween(casa.latitude,casa.longitude,ITLM.latitude,ITLM.longitude,result);
        Toast.makeText(getContext(), "distancia :"+result[0], Toast.LENGTH_SHORT).show();

        String url = getDirectionsUrl();
        GetDirectionsData getDirectionsData = new GetDirectionsData(getContext());
        Object dataTranser[] = new Object[3];
        dataTranser[0]= googleMap;
        dataTranser[1]= url;
        dataTranser[2] = casa;
        getDirectionsData.execute(dataTranser);
        if (publicarComo.equals("Conductor")) {
            PublicarRuta("" + result[0]);
        }
        else {
            BuscarRuta();
        }


    }


    private void BuscarRuta() {

        try {
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl("http://104.154.81.114/")
                    .addConverterFactory(GsonConverterFactory.create());

            Retrofit retrofit = builder.build();
            ApiInterface apiInterface= retrofit.create(ApiInterface.class);

            Call<List<rutaBeta>> call= apiInterface.BuscarRuta();
            call.enqueue(new Callback<List<rutaBeta>>() {
                @Override
                public void onResponse(Call<List<rutaBeta>> call, Response<List<rutaBeta>> response) {
                    List<rutaBeta> listaRutas = response.body();
                        for (rutaBeta rutaBeta:listaRutas){
                            Toast.makeText(getContext(), rutaBeta.getDistancia(), Toast.LENGTH_SHORT).show();
                        }
                        if (listaRutas.size()>0){
                            SeleccionarrutaEncontrada(listaRutas);
                        }
                }

                @Override
                public void onFailure(Call<List<rutaBeta>> call, Throwable t) {
                    Toast.makeText(getContext(), ":Ccccccccccccccccccccc"+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }catch (Exception e){
            Toast.makeText(getContext(), "error en el guardado de drone: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void PublicarRuta(String Distancia) {
        try {
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl("http://104.154.81.114/")
                    .addConverterFactory(GsonConverterFactory.create());

            Retrofit retrofit = builder.build();
            ApiInterface apiInterface= retrofit.create(ApiInterface.class);
            String origen = casa.latitude+","+casa.longitude;
            String destino= ITLM.latitude+","+ITLM.longitude;
            String  fecha =  Fechayhora;
            String plazas = numerodeplazasseleccionado;

            String distancia = Distancia;
            calendar = Calendar.getInstance();
            Year = calendar.get(Calendar.YEAR);
            String[] formatearfecha = fecha.split("\\.");
            formatearfecha[1]= formatearfecha[1]+" "+Year;
            String fechafinal= formatearfecha[0]+formatearfecha[1]+formatearfecha[2];
            Toast.makeText(getContext(), plazas, Toast.LENGTH_SHORT).show();

            Call<ResponseBody> call= apiInterface.PublicarRuta("10215159197561640",origen,destino,fechafinal,plazas,"2709.0337");
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Toast.makeText(getContext(), "agregado exitosamente", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getContext(), "error :C "+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }catch (Exception e){
            Toast.makeText(getContext(), "error en el guardado de drone: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private String getDirectionsUrl() {
            StringBuilder GoogeDirections = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?");
            GoogeDirections.append("&origin="+casa.latitude+","+casa.longitude);
        GoogeDirections.append("&destination="+ITLM.latitude+","+ITLM.longitude);
        GoogeDirections.append("&key="+"AIzaSyCPOLTJhVHpTSZZBHhzB9PIgi_Ws917_0Y");

        return  GoogeDirections.toString();
    }


    private void cargar_Inputs_y_Spinners(View view) {
        //tipo de user spiner
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, ListaTipoDeUsuario);
        spinner_publicar_como = (MaterialBetterSpinner) view.findViewById(R.id.Spiner_piloto_o_pasajero);
        spinner_publicar_como.setAdapter(arrayAdapter);
        spinner_publicar_como.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                publicarComo = ListaTipoDeUsuario.get(i);
            }
        });
        //plazas spiner
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, ListaPlazasDisponibles);
        spinner_plazas_disponibles = (MaterialBetterSpinner) view.findViewById(R.id.Spinner_plazas_disponibles);
        spinner_plazas_disponibles.setAdapter(arrayAdapter2);
        spinner_plazas_disponibles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                numerodeplazasseleccionado = ListaPlazasDisponibles.get(position);
                Toast.makeText(getContext(), numerodeplazasseleccionado, Toast.LENGTH_SHORT).show();

            }
        });
        //origen
        input_origen = (EditText) view.findViewById(R.id.input_origen);
        //destino
        input_destino = (EditText) view.findViewById(R.id.input_destino);

    }


    public void showChangeLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.datetime_dialog, null);
        dialogBuilder.setView(dialogView);
        final SingleDateAndTimePicker singleDateAndTimePicker = (SingleDateAndTimePicker) dialogView.findViewById(R.id.single_day_picker);
        singleDateAndTimePicker.setListener(new SingleDateAndTimePicker.Listener() {
            @Override
            public void onDateChanged(String displayed, Date date) {
                Fechayhora = displayed;
            }
        });
        dialogBuilder.setTitle("Fecha y Hora");
        dialogBuilder.setMessage("Selecciona la fecha y hora ");
        dialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Toast.makeText(getContext(), "Fecha selecionada: " + Fechayhora, Toast.LENGTH_SHORT).show();

            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();

    }
    public void SeleccionarrutaEncontrada(List<rutaBeta> rutaBetaList) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.selecciona_ruta_dialog, null);
        dialogBuilder.setView(dialogView);
        final  RecyclerView recyclerView = (RecyclerView) dialogView.findViewById(R.id.recycler_view_rutas_encontradas);
        Ruta_Econtradas_Adapter adapter =new Ruta_Econtradas_Adapter(rutaBetaList);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    public void onItemClick(View view, int position) {
                        Toast.makeText(getContext(), "te has agregado a esta ruta se le notificara al conductor", Toast.LENGTH_SHORT).show();
                    }
                })
        );
        //dialogBuilder.setTitle("Fecha y Hora");
       // dialogBuilder.setMessage("Selecciona la ruta");
       
        AlertDialog b = dialogBuilder.create();
        b.show();

    }

    public  void ModalSeleccionarOrigen() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.origen_dialog, null);
        dialogBuilder.setView(dialogView);

        dialogBuilder.setTitle("Selecciona Tu Origen");
        //dialogBuilder.setMessage("Selecciona la fecha y hora ");
        dialogBuilder.setPositiveButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
               // Toast.makeText(getContext(), "Fecha selecionada: " + Fechayhora, Toast.LENGTH_SHORT).show();
            }
        });
        modalorigen = dialogBuilder.create();
        modalorigen.show();
        final Button BotonOrigenCasa = (Button) dialogView.findViewById(R.id.origen_casa);
        final Button BotonOrigenEscuela = (Button) dialogView.findViewById(R.id.origen_escuela);
        BotonOrigenCasa.setOnClickListener(clickListener);
        BotonOrigenEscuela.setOnClickListener(clickListener);
    }


    @Override
    public void onValidationSucceeded() {

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(locationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        lastlocation = location;
        if (currentLocationMarker != null) {
            currentLocationMarker.remove();
        }
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
       /* MarkerOptions markerOptions= new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("current location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        currentLocationMarker = mMap.addMarker(markerOptions);*/
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(12));

        if (client != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(client, this);
        }

    }

    public Boolean CheckLocationPermission() {

        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(),android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_LOCATION_CODE);
            return false;
        } else return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_LOCATION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        if (client == null) {
                            buildGoogleApiClient();
                        }
                        googleMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(getContext(), "Permision denied", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap = googleMap;
        // For showing a move to my location button
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            buildGoogleApiClient();
            this.googleMap.setMyLocationEnabled(true);
        }


      // this.googleMap.setOnMarkerClickListener(this);
       // this.googleMap.setOnMarkerDragListener(this);
        /* this.googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                googleMap.clear();
                MarkerOptions marker = new MarkerOptions().position(
                        new LatLng(latLng.latitude, latLng.longitude)).title("New Marker");
                googleMap.addMarker(marker);
                // TODO: 13/11/2017 conversion de coordenadas a ciudad o localidad
                Geocoder gcd = new Geocoder(getContext(), Locale.getDefault());
                List<Address> addresses;
                String cityName="";
                try {
                    addresses = gcd.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    if (addresses.size() > 0)
                        cityName = addresses.get(0).getLocality();
                } catch (IOException e) {
                    Toast.makeText(getContext(),"error: "+e.getMessage(), Toast.LENGTH_SHORT).show();;
                }
                Toast.makeText(getContext(), "LATITUD :"+latLng.latitude+", LONGITUD :"+latLng.longitude+"\nCIUDAD:"+cityName, Toast.LENGTH_SHORT).show();
                //showChangeLangDialog(String.valueOf(latLng.latitude),String.valueOf(latLng.longitude),cityName);

            }
        });*/

           /* LatLng sydney = new LatLng(25.8085, -108.9815);
            this.googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));
            // For zooming automatically to the location of the marker
            CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(15).build();
            this.googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));*/
        }




    protected synchronized void buildGoogleApiClient() {
        client = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        client.connect();


    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.setDraggable(true);
        return false;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        end_latitude= marker.getPosition().latitude;
        end_longitude=marker.getPosition().longitude;

    }

    
}