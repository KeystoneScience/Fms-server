package com.example.familymapclient;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import model.ClientInformation;
import model.Event;

import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_BLUE;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_GREEN;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_RED;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_YELLOW;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {


    SupportMapFragment mapFragment;

    private ClientInformation clientInformation;

    public void setClientInformation(ClientInformation clientInformation) {
        this.clientInformation = clientInformation;
    }

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mapFragment=(SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if(mapFragment == null){
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            fragmentTransaction.replace(R.id.map,mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
        return view;
    }

    private float getColor(String eventType){
        if(eventType.equals("death")){
            return 0;
        }
        if(eventType.equals("marriage")){
            return HUE_YELLOW;
        }
        if(eventType.equals("birth")){
            return HUE_GREEN;
        }
        else{
            return HUE_RED;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap){

            //googleMap.setOnMarkerClickListener(this);

//            for (Event ev : clientInformation.getEventResult().getEvents()) {
//
//                LatLng latLng = new LatLng(ev.getLatitude(), ev.getLongitude());
//
//                Marker marker = googleMap.addMarker(new MarkerOptions().position(latLng)
//                        .icon(BitmapDescriptorFactory
//                                .defaultMarker(getColor(ev.getEvent_type()))));
//
//
//                //TODO Add a mapping between marker and event.
//
//
//
//
//        }





    }
}
