package com.example.familymapclient;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsActivity extends AppCompatActivity {

    private ClientInformation clientInformation;
    private Switch mLifeStory,mFamilyTree,mSpouse,mFather,mMother,mMale,mFemale;
    private LinearLayout mLogout;

    public void setClientInformation(ClientInformation clientInformation) {
        this.clientInformation = clientInformation;
    }

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

        mFamilyTree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                clientInformation.setFamilyTreeLines(isChecked);
            }
        });
        mLifeStory.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                clientInformation.setLifeStoryLines(isChecked);
            }
        });
        mSpouse.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                clientInformation.setSpouseLine(isChecked);
            }
        });

        mFather.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                clientInformation.setFatherSide(isChecked);
            }
        });
        mMother.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                clientInformation.setMotherSide(isChecked);
            }
        });
        mMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                clientInformation.setMaleEvents(isChecked);
            }
        });
        mFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                clientInformation.setFemaleEvents(isChecked);
            }
        });
        //TODO do something here for the logout layout click. IDK what to do yet.



        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }
}