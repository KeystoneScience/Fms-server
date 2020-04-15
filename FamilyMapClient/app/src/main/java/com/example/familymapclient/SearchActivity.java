package com.example.familymapclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import java.util.ArrayList;
import java.util.List;

import model.Event;
import model.Person;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView mEvents,mFamily;
    private EditText mSearch;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Iconify.with(new FontAwesomeModule());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mEvents = findViewById(R.id.eventSearchResults);
        mFamily = findViewById(R.id.familySearchResults);
        mSearch = findViewById(R.id.searchTextField);


        mSearch.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SearchQuery searchQuery = new SearchQuery(mSearch.getText().toString());
                postSearchResults(searchQuery);
            }
        });



    }

    private void postSearchResults(SearchQuery searchQuery){
        initAllRecycler(searchQuery.fam,searchQuery.events);
    }


    private void initAllRecycler(List<Family> families, List<Event> events){
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(families,events,this);
        mFamily.setAdapter(adapter);
        mFamily.setLayoutManager(new LinearLayoutManager(this));

    }



    private class SearchQuery{
        private String index;
        List<Event> events = new ArrayList<>();
        private List<Person> people = new ArrayList<>();
        List<Family> fam = new ArrayList<>();

        public SearchQuery(String phrase){
            index=phrase.toLowerCase();
            findMatches();
            generateFam();
        }

        private  void generateFam(){
            for (Person per: people) {
                fam.add(new Family(per,""));
            }
        }

        private void findMatches(){
            //Get People matches
            for (Person per: ClientInfo.getInstance().getPersonResult().getPeople()) {
                if(per.getLast_name().toLowerCase().contains(index)){
                    people.add(per);
                    continue;
                }
                if(per.getFirst_name().toLowerCase().contains(index)){
                    people.add(per);
                    continue;
                }
                if((per.getFirst_name() + " " + per.getLast_name()).toLowerCase().contains(index)){
                    people.add(per);
                    continue;
                }
            }


            //Get matching events

            for (Event ev: ClientInfo.getInstance().getEventResult().getEvents()) {
                if (!ClientInfo.getInstance().filteredEvents.get(ev)) {
                    if (Integer.toString(ev.getYear()).contains(index)) {
                        events.add(ev);
                        continue;
                    }
                    if (ev.getCountry().toLowerCase().contains(index)) {
                        events.add(ev);
                        continue;
                    }
                    if (ev.getCity().toLowerCase().contains(index)) {
                        events.add(ev);
                        continue;
                    }
                    if (Float.toString(ev.getLongitude()).contains(index)) {
                        events.add(ev);
                        continue;
                    }
                    if (Float.toString(ev.getLatitude()).contains(index)) {
                        events.add(ev);
                        continue;
                    }
                    if (ev.getEvent_type().toLowerCase().contains(index)) {
                        events.add(ev);
                        continue;
                    }

                }
            }


        }

    }
}
