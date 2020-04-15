package Activitys;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;

import Fragments.LoginFragment;
import Fragments.MapFragment;
import com.example.familymapclient.R;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

public class MainActivity extends AppCompatActivity {
    private MapFragment mapFragment;
    private FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Iconify.with(new FontAwesomeModule());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoginFragment loginFragment = new LoginFragment();
        loginFragment.setArguments(new Bundle());
        fragmentManager.beginTransaction().replace(R.id.main_activity, loginFragment).commit();
    }


    public void startMap(){
        Iconify.with(new FontAwesomeModule());
        mapFragment = new MapFragment();
        fragmentManager.beginTransaction().replace(R.id.main_activity, mapFragment).commit();
    }


}
