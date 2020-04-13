package com.example.familymapclient;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

public class MainActivity extends AppCompatActivity {
    private LoginFragment loginFragment;
    private MapFragment mapFragment;
    private FragmentManager fragmentManager = getSupportFragmentManager();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Iconify.with(new FontAwesomeModule());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginFragment = new LoginFragment();
        loginFragment.setArguments(new Bundle());
        fragmentManager.beginTransaction().replace(R.id.main_activity, loginFragment).addToBackStack("login").commit();


    }


    public void startMap(){
        Iconify.with(new FontAwesomeModule());
        mapFragment = new MapFragment();
        fragmentManager.beginTransaction().replace(R.id.main_activity, mapFragment).addToBackStack("mapFragment").commit();

    }


}
