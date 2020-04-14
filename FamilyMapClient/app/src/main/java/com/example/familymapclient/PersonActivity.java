package com.example.familymapclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import java.util.ArrayList;
import java.util.List;

import model.Event;
import model.Person;

public class PersonActivity extends AppCompatActivity {

    private Person targetPerson;
    private TextView mFirstName, mLastName, mGender,mFamilyText,mEventText;
    private RecyclerView mEvents,mFamily;
    private List<Family> family = new ArrayList<>();
    private boolean familyExpand = false;
    private boolean eventExpand = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Iconify.with(new FontAwesomeModule());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        Intent data = getIntent();
        targetPerson =  ClientInfo.getInstance().getPersonFromID((String) data.getSerializableExtra("PersonID"));
        mFirstName = findViewById(R.id.personActivityFirstName);
        mLastName = findViewById(R.id.personActivityLastName);
        mGender = findViewById(R.id.personActivityGender);
        mFamilyText = findViewById(R.id.personActivityFamily);
        mEventText = findViewById(R.id.personActivityLifeEvents);

        mEvents = findViewById(R.id.personActivityLifeEventsRecycler);
        mFamily = findViewById(R.id.personActivityFamilyRecycler);




        mFirstName.setText(targetPerson.getFirst_name());
        mLastName.setText(targetPerson.getLast_name());

        if(targetPerson.getGender().equals("m")){
            mGender.setText("Male");
        }
        else{
            mGender.setText("Female");
        }

        initRecyclerViewEvents();
        prepareFamilyIDs();
        initRecyclerViewFamily();
        mFamily.setVisibility(View.INVISIBLE);
        mEvents.setVisibility(View.INVISIBLE);
        mFamilyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(familyExpand){
                    familyExpand=false;
                    mFamily.setVisibility(View.INVISIBLE);

                }
                else{
                    familyExpand=true;
                    mFamily.setVisibility(View.VISIBLE);
                }

            }
        });

        mEventText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(eventExpand){
                    eventExpand=false;
                    mEvents.setVisibility(View.INVISIBLE);

                }
                else{
                    eventExpand=true;
                    mEvents.setVisibility(View.VISIBLE);
                }

            }
        });

    }

    private void prepareFamilyIDs(){
        family=ClientInfo.getInstance().getFamily(targetPerson);
    }

    private void initRecyclerViewFamily(){
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(family,this);
        mFamily.setAdapter(adapter);
        mFamily.setLayoutManager(new LinearLayoutManager(this));



    }

    private void initRecyclerViewEvents(){
        List<Event> events = ClientInfo.getInstance().chronologicalEvents(targetPerson.getPerson_id());
        RecyclerViewAdapter eventAdapter = new RecyclerViewAdapter(events,this,"event");
        mEvents.setAdapter(eventAdapter);
        mEvents.setLayoutManager(new LinearLayoutManager(this));
    }





}
