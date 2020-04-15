package com.example.familymapclient;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

import java.util.zip.Inflater;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static java.security.AccessController.getContext;

public class SettingsActivity extends AppCompatActivity {

    private Switch mLifeStory,mFamilyTree,mSpouse,mFather,mMother,mMale,mFemale;
    private LinearLayout mLogout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        mLifeStory = findViewById(R.id.LifeStorySwitch);
        mFamilyTree = findViewById(R.id.FamiltTreeLinesSwitch);
        mFather = findViewById(R.id.FatherSideSwitch);
        mMother = findViewById(R.id.MotherSideSwitch);
        mMale = findViewById(R.id.MaleEventSwitch);
        mFemale = findViewById(R.id.FemaleEventSwitch);
        mSpouse = findViewById(R.id.SpouseLinesSwitch);
        mLogout = findViewById(R.id.LogoutSettingsLayout);

        mFamilyTree.setChecked(ClientInfo.getInstance().isFamilyTreeLines());
        mLifeStory.setChecked(ClientInfo.getInstance().isLifeStoryLines());
        mFather.setChecked(ClientInfo.getInstance().isFatherSide());
        mMother.setChecked(ClientInfo.getInstance().isMotherSide());
        mMale.setChecked(ClientInfo.getInstance().isMaleEvents());
        mFemale.setChecked(ClientInfo.getInstance().isFemaleEvents());
        mSpouse.setChecked(ClientInfo.getInstance().isSpouseLine());


        mFamilyTree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ClientInfo.getInstance().setFamilyTreeLines(isChecked);
            }
        });
        mLifeStory.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ClientInfo.getInstance().setLifeStoryLines(isChecked);
            }
        });
        mSpouse.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ClientInfo.getInstance().setSpouseLine(isChecked);
            }
        });

        mFather.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ClientInfo.getInstance().setFatherSide(isChecked);
            }
        });
        mMother.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ClientInfo.getInstance().setMotherSide(isChecked);
            }
        });
        mMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ClientInfo.getInstance().setMaleEvents(isChecked);
            }
        });
        mFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ClientInfo.getInstance().setFemaleEvents(isChecked);
            }
        });

        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(SettingsActivity.this ,MainActivity.class);

                //This is what sets it as the top activity
                main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK) ;
                startActivity(main);

            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}