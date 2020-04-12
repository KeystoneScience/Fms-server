package com.example.familymapclient;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.drawable.Drawable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import model.Event;
import model.Person;

import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_GREEN;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_RED;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_YELLOW;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {


    SupportMapFragment mapFragment;

    private ClientInformation clientInformation;
    private View view;

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
        view = inflater.inflate(R.layout.fragment_map, container, false);
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
    private void markLines(Event ev){


    }

    @Override
    public void onMapReady(GoogleMap googleMap){


        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    Event selectedEvent = clientInformation.getEventFromWaypoint(marker);
                   // Toast.makeText(getContext(),selectedEvent.getCity(),Toast.LENGTH_SHORT).show();
                    //Create Lines

                    //Show event info.
                   TextView uText = view.findViewById(R.id.markerInformationUText);
                   TextView lText = view.findViewById(R.id.markerInformationLText);
                   Person person = clientInformation.getPersonFromID(selectedEvent.getPerson_id());
                   uText.setText(person.getFirst_name() + " " + person.getLast_name());
                   lText.setText(selectedEvent.getEvent_type() +": " + selectedEvent.getCity() + ", " + selectedEvent.getCountry()
                                 + " (" + selectedEvent.getYear() +")");

                   ImageView iView = view.findViewById(R.id.markerInformationIcon);
                   if(person.getGender().equals("m")){
                       iView.setImageDrawable(new IconDrawable(getActivity(), FontAwesomeIcons.fa_male).sizeDp(40));
                   }
                   else{
                       iView.setImageDrawable(new IconDrawable(getActivity(), FontAwesomeIcons.fa_female).sizeDp(40));
                   }
                    return false;
                }
            });

            for (Event ev : clientInformation.getEventResult().getEvents()) {
                LatLng latitudeAndLongitude = new LatLng(ev.getLatitude(), ev.getLongitude());
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latitudeAndLongitude);
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(getColor(ev.getEvent_type())));
                clientInformation.addWaypoint(googleMap.addMarker(markerOptions),ev);
        }





    }
}
