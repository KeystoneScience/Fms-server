package com.example.familymapclient;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.util.ArrayList;
import java.util.List;

import model.Event;
import model.Person;

import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_GREEN;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_RED;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_VIOLET;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_YELLOW;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {


    SupportMapFragment mapFragment;

    private ClientInformation clientInformation;
    private View view;
    private LinearLayout markerInformation;
    private List<Polyline> familyTree = new ArrayList<>();
    private List<Polyline> lifeStory = new ArrayList<>();
    private Polyline marriageLine;
    private GoogleMap theMap;


    public void setClientInformation(ClientInformation clientInformation) {
        this.clientInformation = clientInformation;
    }

    public MapFragment() {
    }

    private void markerInformationPortal(){
        Toast.makeText(getContext(), "Info Window Clicked", Toast.LENGTH_SHORT).show();

        //Person Activity
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_map, container, false);
        markerInformation = view.findViewById(R.id.markerInformation);
        markerInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                markerInformationPortal();
            }

        });

        ImageView imageView =  view.findViewById(R.id.markerInformationIcon);
        imageView.setImageDrawable(new IconDrawable(getActivity(), FontAwesomeIcons.fa_android)
                .sizeDp(40));

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
            return HUE_VIOLET;
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
        //TODO these are controlled through the settings, add toggle booleans
        for (Polyline pl: familyTree) {
            pl.remove();
        }
        familyTree.clear();
        generateFamilyTreeLines(ev);
        if(marriageLine != null) {
            marriageLine.remove();
        }
        generateMarriageLine(ev);
        for (Polyline pl: lifeStory) {
            pl.remove();
        }
        lifeStory.clear();
        generateLifeStoryLines(ev);
    }


    private Polyline lineBetweenEvents(Event rootBase, Event first, float thickness, int color){
        PolylineOptions polylineOptions = new PolylineOptions();
        LatLng rootLatLng = new LatLng(rootBase.getLatitude(),rootBase.getLongitude());
        LatLng second = new LatLng(first.getLatitude(),first.getLongitude());
        polylineOptions.add(rootLatLng,second).clickable(false).color(color).width(thickness);
        return theMap.addPolyline(polylineOptions);
    }

    private void generateFamilyTreeLines(Event ev){
        float thickness= 25;
        Person root = clientInformation.getPersonFromID(ev.getPerson_id());
        if(!(root.getMother_id() == null)){
            Event first = clientInformation.chronologicalEvents(root.getMother_id()).get(0);
            Person mother = clientInformation.getPersonFromID(root.getMother_id());
            familyTree.add(lineBetweenEvents(ev,first,thickness, Color.GREEN));
            generateFamilyTreeLines(thickness*.55f,mother);
        }
        if(!(root.getFather_id() == null)){
            Event first = clientInformation.chronologicalEvents(root.getFather_id()).get(0);
            familyTree.add(lineBetweenEvents(ev,first,thickness, Color.GREEN));
            Person father = clientInformation.getPersonFromID(root.getFather_id());
            generateFamilyTreeLines(thickness*.55f,father);
        }


    }


    public void generateFamilyTreeLines(float thickness, Person root){
        Event rootBase = clientInformation.chronologicalEvents(root.getPerson_id()).get(0);
        if(!(root.getMother_id() == null)){
            Event first = clientInformation.chronologicalEvents(root.getMother_id()).get(0);
            Person mother = clientInformation.getPersonFromID(root.getMother_id());
            familyTree.add(lineBetweenEvents(rootBase,first,thickness, Color.GREEN));
            generateFamilyTreeLines(thickness*.55f,mother);
        }
        if(!(root.getFather_id() == null)){
            Event first = clientInformation.chronologicalEvents(root.getFather_id()).get(0);
            familyTree.add(lineBetweenEvents(rootBase,first,thickness, Color.GREEN));
            Person father = clientInformation.getPersonFromID(root.getFather_id());
            generateFamilyTreeLines(thickness*.55f,father);
        }

    }

    private void generateLifeStoryLines(Event ev){
        List<Event> lifeEvents = clientInformation.chronologicalEvents(ev.getPerson_id());
        for (int i = 0; i < lifeEvents.size()-1; i++) {
            lifeStory.add(lineBetweenEvents(lifeEvents.get(i),lifeEvents.get(i+1),5f,Color.YELLOW));
        }
    }

    private void generateMarriageLine(Event ev){
        Person root = clientInformation.getPersonFromID(ev.getPerson_id());
        if(root.getSpouse_id() != null) {
            Event spouseBirth = clientInformation.chronologicalEvents(root.getSpouse_id()).get(0);
            marriageLine = lineBetweenEvents(ev,spouseBirth,5f,Color.RED);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap){

        theMap=googleMap;


        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    Event selectedEvent = clientInformation.getEventFromWaypoint(marker);
                   // Toast.makeText(getContext(),selectedEvent.getCity(),Toast.LENGTH_SHORT).show();
                    markLines(selectedEvent);
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
        addAllEvents();

    }

    private void addAllEvents(){
        theMap.clear();
       // clientInformation.clearWaypointToEvent();
        for (Event ev : clientInformation.getEventResult().getEvents()) {
            LatLng latitudeAndLongitude = new LatLng(ev.getLatitude(), ev.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latitudeAndLongitude);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(getColor(ev.getEvent_type())));
            clientInformation.addWaypoint(theMap.addMarker(markerOptions),ev);
        }
    }




//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//
//        //if (mIsMainActivity) {
//            inflater.inflate(R.menu.fragment_maps, menu);
//
//            //ActionBar icon(s)
//            menu.findItem(R.id.searchMenuItem).setIcon(
//                    new IconDrawable(getActivity(), FontAwesomeIcons.fa_search)
//                            .colorRes(R.color.toolbarIcon)
//                            .actionBarSize());
//
//            menu.findItem(R.id.filterMenuItem).setIcon(
//                    new IconDrawable(getActivity(), FontAwesomeIcons.fa_filter)
//                            .colorRes(R.color.toolbarIcon)
//                            .actionBarSize());
//
//            menu.findItem(R.id.settingsMenuItem).setIcon(
//                    new IconDrawable(getActivity(), FontAwesomeIcons.fa_gear)
//                            .colorRes(R.color.toolbarIcon)
//                            .actionBarSize());
////        }
////        else {
////            // Todo: instead of this, make a menu with just the up button
////            inflater.inflate(R.menu.fragment_maps_map_activity, menu);
////        }
//    }
}
