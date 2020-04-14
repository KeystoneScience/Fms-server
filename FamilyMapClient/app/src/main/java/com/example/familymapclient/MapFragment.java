package com.example.familymapclient;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Event;
import model.Person;

import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_GREEN;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_MAGENTA;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_RED;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_VIOLET;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_YELLOW;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {


    SupportMapFragment mapFragment;

    private View view;
    private LinearLayout markerInformation;
    private List<Polyline> familyTree = new ArrayList<>();
    private List<Polyline> lifeStory = new ArrayList<>();
    private Polyline marriageLine;
    private GoogleMap theMap;
    private Map<Event,Boolean> filteredEvents = new HashMap<>();
    private boolean firstTime = true;
    private Event lastSelectedEvent;


    public MapFragment() {
    }

    private void markerInformationPortal(){
        Toast.makeText(getContext(), "Info Window Clicked", Toast.LENGTH_SHORT).show();


        //Person Activity
        if(lastSelectedEvent!=null) {
            Intent data;
            data = new Intent(getActivity(), PersonActivity.class);
            //tell who is selected.
            data.putExtra("PersonID",lastSelectedEvent.getPerson_id());
            startActivity(data);
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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

        TextView uText = view.findViewById(R.id.markerInformationUText);
        TextView lText = view.findViewById(R.id.markerInformationLText);
        uText.setText("Click A Marker");
        lText.setText("to learn more");


        ImageView imageView =  view.findViewById(R.id.markerInformationIcon);
        imageView.setImageDrawable(new IconDrawable(getActivity(), FontAwesomeIcons.fa_android).colorRes(R.color.android_green)
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
        if(ClientInfo.getInstance().isFamilyTreeLines()) {
            generateFamilyTreeLines(ev);
        }
        if(marriageLine != null) {
            marriageLine.remove();
        }
        if(ClientInfo.getInstance().isSpouseLine()) {
            generateMarriageLine(ev);
        }
        for (Polyline pl: lifeStory) {
            pl.remove();
        }
        lifeStory.clear();
        if(ClientInfo.getInstance().isLifeStoryLines()) {
            generateLifeStoryLines(ev);
        }
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
        Person root = ClientInfo.getInstance().getPersonFromID(ev.getPerson_id());
        if(!(root.getMother_id() == null)){
            Event first = ClientInfo.getInstance().chronologicalEvents(root.getMother_id()).get(0);
            Person mother = ClientInfo.getInstance().getPersonFromID(root.getMother_id());
            if(!filteredEvents.get(first)) {
                familyTree.add(lineBetweenEvents(ev, first, thickness, Color.GREEN));
                generateFamilyTreeLines(thickness * .55f, mother,first);

            }
            else {
                generateFamilyTreeLines(thickness * .55f, mother,ev);
            }
        }
        if(!(root.getFather_id() == null)){
            Event first = ClientInfo.getInstance().chronologicalEvents(root.getFather_id()).get(0);
            Person father = ClientInfo.getInstance().getPersonFromID(root.getFather_id());
            if(!filteredEvents.get(first)) {
                familyTree.add(lineBetweenEvents(ev, first, thickness, Color.GREEN));
                generateFamilyTreeLines(thickness * .55f, father,first);

            }
            else {
                generateFamilyTreeLines(thickness * .55f, father,ev);
            }
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        if(firstTime){
            firstTime=false;
        }
        else {
            checkFilters();
        }
    }

    public void generateFamilyTreeLines(float thickness, Person root, Event lastDrawable){
        Event rootBase = ClientInfo.getInstance().chronologicalEvents(root.getPerson_id()).get(0);
        if(!(root.getMother_id() == null)){
            Event first = ClientInfo.getInstance().chronologicalEvents(root.getMother_id()).get(0);
            Person mother = ClientInfo.getInstance().getPersonFromID(root.getMother_id());
            if(!filteredEvents.get(first)) {
                familyTree.add(lineBetweenEvents(lastDrawable, first, thickness, Color.GREEN));
                generateFamilyTreeLines(thickness * .55f, mother,first);

            }
            else {
                generateFamilyTreeLines(thickness * .55f, mother,lastDrawable);
            }
        }
        if(!(root.getFather_id() == null)){
            Event first = ClientInfo.getInstance().chronologicalEvents(root.getFather_id()).get(0);
            Person father = ClientInfo.getInstance().getPersonFromID(root.getFather_id());
            if(!filteredEvents.get(first)) {
                familyTree.add(lineBetweenEvents(lastDrawable, first, thickness, Color.GREEN));
                generateFamilyTreeLines(thickness * .55f, father,first);

            }
            else {
                generateFamilyTreeLines(thickness * .55f, father,lastDrawable);
            }
        }

    }

    private void generateLifeStoryLines(Event ev){
        List<Event> lifeEvents = ClientInfo.getInstance().chronologicalEvents(ev.getPerson_id());
        for (int i = 0; i < lifeEvents.size()-1; i++) {
            lifeStory.add(lineBetweenEvents(lifeEvents.get(i),lifeEvents.get(i+1),5f,Color.YELLOW));
        }
    }

    private void generateMarriageLine(Event ev){
        Person root = ClientInfo.getInstance().getPersonFromID(ev.getPerson_id());
        if(root.getSpouse_id() != null) {
            Event spouseBirth = ClientInfo.getInstance().chronologicalEvents(root.getSpouse_id()).get(0);
            if(!filteredEvents.get(spouseBirth)){
                marriageLine = lineBetweenEvents(ev,spouseBirth,5f,Color.RED);
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap){

        theMap=googleMap;


        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    Event selectedEvent = ClientInfo.getInstance().getEventFromWaypoint(marker);
                   // Toast.makeText(getContext(),selectedEvent.getCity(),Toast.LENGTH_SHORT).show();
                    lastSelectedEvent=selectedEvent;
                    markLines(selectedEvent);
                   TextView uText = view.findViewById(R.id.markerInformationUText);
                   TextView lText = view.findViewById(R.id.markerInformationLText);
                   Person person = ClientInfo.getInstance().getPersonFromID(selectedEvent.getPerson_id());
                   uText.setText(person.getFirst_name() + " " + person.getLast_name());
                   lText.setText(selectedEvent.getEvent_type() +": " + selectedEvent.getCity() + ", " + selectedEvent.getCountry()
                                 + " (" + selectedEvent.getYear() +")");

                   ImageView iView = view.findViewById(R.id.markerInformationIcon);
                   if(person.getGender().equals("m")){
                       iView.setImageDrawable(new IconDrawable(getActivity(), FontAwesomeIcons.fa_male).colorRes(R.color.male_color).sizeDp(40));
                   }
                   else{
                       iView.setImageDrawable(new IconDrawable(getActivity(), FontAwesomeIcons.fa_female).colorRes(R.color.female_color).sizeDp(40));
                   }
                    return false;
                }
            });
        ClientInfo.getInstance().filterDefaults();
        checkFilters();

    }

    //@Override
    //public void OnCreateOptions

    private void checkFilters(){ //Function to update map markers based on filters activated.
        theMap.clear();
        filteredEvents.clear();
        ClientInfo.getInstance().clearWaypointToEvent();
        for (Event ev : ClientInfo.getInstance().getEventResult().getEvents()) {
            Person temp = ClientInfo.getInstance().getPersonFromID(ev.getPerson_id());
            //Does gender filtering.
            if(temp.getGender().equals("m") && !ClientInfo.getInstance().isMaleEvents()){
                filteredEvents.put(ev,Boolean.TRUE);
                continue;
            }
            if(temp.getGender().equals("f") && !ClientInfo.getInstance().isFemaleEvents()){
                filteredEvents.put(ev,Boolean.TRUE);
                continue;
            }
            if(ClientInfo.getInstance().getPersonToSideOfFamily().get(temp).equals("mom") && !ClientInfo.getInstance().isMotherSide()){
                filteredEvents.put(ev,Boolean.TRUE);
                continue;
            }
            if(ClientInfo.getInstance().getPersonToSideOfFamily().get(temp).equals("dad") && !ClientInfo.getInstance().isFatherSide()){
                filteredEvents.put(ev,Boolean.TRUE);
                continue;
            }
            filteredEvents.put(ev,Boolean.FALSE);
            LatLng latitudeAndLongitude = new LatLng(ev.getLatitude(), ev.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latitudeAndLongitude);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(getColor(ev.getEvent_type())));
            ClientInfo.getInstance().addWaypoint(theMap.addMarker(markerOptions),ev);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;

        if(item.getItemId() == R.id.search_button_maps){
            Toast.makeText(getContext(), "Search Clicked", Toast.LENGTH_SHORT).show();

//            intent = new Intent(getActivity(), SearchActivity.class);
//            startActivity(intent);
            return true;
        }
        if(item.getItemId() == R.id.settings_button_maps){

            intent = new Intent(getActivity(), SettingsActivity.class);
            startActivity(intent);
            return true;

        }
        return false;

    }




    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //None of this is working.... TODO: create icon in a better way, add listener.

        //if (mIsMainActivity) {
            inflater.inflate(R.menu.map_fragment_action_bar, menu);

            //ActionBar icon(s)
            menu.findItem(R.id.search_button_maps).setIcon(
                    new IconDrawable(getActivity(), FontAwesomeIcons.fa_search)
                            .colorRes(R.color.action_bar)
                            .actionBarSize());

            menu.findItem(R.id.settings_button_maps).setIcon(new IconDrawable(getActivity(), FontAwesomeIcons.fa_gear).colorRes(R.color.action_bar).actionBarSize());
//        }
//        else {
//            // Todo: instead of this, make a menu with just the up button
//            inflater.inflate(R.menu.fragment_maps_map_activity, menu);
//        }
    }
}
