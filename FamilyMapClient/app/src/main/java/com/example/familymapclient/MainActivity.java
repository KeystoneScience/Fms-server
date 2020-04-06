package com.example.familymapclient;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.graphics.ColorSpace;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.familymapclient.R;

import model.ClientInformation;
import model.User;

public class MainActivity extends AppCompatActivity {
    private LoginFragment loginFragment;
    private MapFragment mapFragment;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private ClientInformation clientInformation;

    public void setClientInformation(ClientInformation clientInformation) {
        this.clientInformation = clientInformation;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginFragment = new LoginFragment();
        loginFragment.setArguments(new Bundle());
        fragmentManager.beginTransaction().replace(R.id.main_activity, loginFragment).addToBackStack("login").commit();


    }


    public void startMap(){
        mapFragment = new MapFragment();
        mapFragment.setClientInformation(clientInformation);
        fragmentManager.beginTransaction().replace(R.id.main_activity, mapFragment).addToBackStack("mapFragment").commit();

    }


}
