package com.example.eider.navigation_drawer.Fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eider.navigation_drawer.R;
import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment  implements Validator.ValidationListener{
    MapView mMapView;
    private GoogleMap googleMap;
    private Button boton_fecha;

    @NotEmpty(message = "debes de llenar este campo")
    MaterialBetterSpinner spinner_publicar_como;
    @NotEmpty(message = "debes de llenar este campo")
    EditText input_origen;
    @NotEmpty(message = "debes de llenar este campo")
    EditText input_destino;
    @NotEmpty(message = "debes de llenar este campo")
    MaterialBetterSpinner spinner_plazas_disponibles;

    String Fechayhora;

    Validator validator;
    ArrayList<String> ListaTipoDeUsuario= new ArrayList<String>() {{
        add("Conductor");
        add("Pasajero");
    }};
    ArrayList<String> ListaPlazasDisponibles= new ArrayList<String>() {{
        add("1");
        add("2");
        add("3");
        add("4");
        add("5");
        add("6");
    }};
    
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View Rootview = inflater.inflate(R.layout.fragment_home, container, false);
        boton_fecha = (Button) Rootview.findViewById(R.id.boton_modal_fecha);
       cargar_Inputs_y_Spinners(Rootview);
        try {
            mMapView = (MapView) Rootview.findViewById(R.id.mapView);
            mMapView.onCreate(savedInstanceState);
            mMapView.onResume();
            MapsInitializer.initialize(getActivity().getApplicationContext());

            mMapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap mMap) {
                    googleMap = mMap;

                    // For showing a move to my location button
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        googleMap.setMyLocationEnabled(true);
                    }
                    LatLng sydney = new LatLng(25.8085, -108.9815);
                    googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));
                    // For zooming automatically to the location of the marker
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(15).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }
            });

            boton_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeLangDialog();
            }
        });
        }
        catch (Exception e){
            Toast.makeText(getContext(), e.getLocalizedMessage()+" error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return Rootview;
    }

    private void cargar_Inputs_y_Spinners(View view) {
        //tipo de user spiner
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, ListaTipoDeUsuario);
        spinner_publicar_como = (MaterialBetterSpinner) view.findViewById(R.id.Spiner_piloto_o_pasajero);
        spinner_publicar_como.setAdapter(arrayAdapter);


        //plazas spiner
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, ListaPlazasDisponibles);
        spinner_plazas_disponibles = (MaterialBetterSpinner) view.findViewById(R.id.Spinner_plazas_disponibles);
        spinner_plazas_disponibles.setAdapter(arrayAdapter2);

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
                Toast.makeText(getContext(), "Fecha selecionada: "+Fechayhora, Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onValidationSucceeded() {
        
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {

    }
}
