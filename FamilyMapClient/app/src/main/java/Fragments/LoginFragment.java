package Fragments;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import com.example.familymapclient.R;
import com.google.gson.Gson;


import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import Activitys.MainActivity;
import Client_Information.ClientInfo;
import model.User;
import requests.LoginRequest;
import requests.RegisterRequest;
import results.EventResult;
import results.LoginResult;
import results.PersonResult;
import results.RegisterResult;

public class LoginFragment extends Fragment {

    private EditText mServerHost, mServerPort, mUserName,
            mPassword, mFirstName, mLastName, mEmail;
    private RadioGroup mGender;
    private RadioButton mMale,mFemale;
    private Button mSignIn, mRegister;
    private String serverHost, serverPort, userName,
            password, firstName, lastName, email, gender;


    public LoginFragment() { }


    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void signInAvalible(){
        serverHost = mServerHost.getText().toString();
        serverPort = mServerPort.getText().toString();
        userName = mUserName.getText().toString();
        password = mPassword.getText().toString();
        if(serverPort.isEmpty() || serverHost.isEmpty() || userName.isEmpty() || password.isEmpty() ){
            mSignIn.setEnabled(false);
            return;
        }
        mSignIn.setEnabled(true);
    }
    private void registerAvalible(){

        serverHost = mServerHost.getText().toString();
        serverPort = mServerPort.getText().toString();


        userName = mUserName.getText().toString();
        password = mPassword.getText().toString();
        firstName = mFirstName.getText().toString();
        lastName = mLastName.getText().toString();
        email = mEmail.getText().toString();

        //Checks to see what button was pressed.

        if(mGender.getCheckedRadioButtonId() == mMale.getId()){
            gender = "m";
        }
        else if (mGender.getCheckedRadioButtonId() == mFemale.getId()){
            gender = "f";
        }
        else{
            gender = "";
        }
        if(serverHost.isEmpty()||serverPort.isEmpty()||userName.isEmpty()|| password.isEmpty() ||
                firstName.isEmpty()|| lastName.isEmpty()  || email.isEmpty()|| gender.isEmpty()){
            mRegister.setEnabled(false);
            return;
        }
        mRegister.setEnabled(true);

    }



    //@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);


        mServerHost = view.findViewById(R.id.server_host);
        mServerPort = view.findViewById(R.id.server_port);

        mUserName = view.findViewById(R.id.user_name);
        mPassword = view.findViewById(R.id.password);
        mGender = view.findViewById(R.id.genderRadioGroup);
        mMale = view.findViewById(R.id.radio_male);
        mFemale = view.findViewById(R.id.radio_female);

        mEmail = view.findViewById(R.id.email);

        mFirstName = view.findViewById(R.id.first_name);
        mLastName = view.findViewById(R.id.last_name);
        mSignIn = view.findViewById(R.id.sign_in_button);
        mRegister = view.findViewById(R.id.register_button);

        mSignIn.setEnabled(false);
        mRegister.setEnabled(false);

        mGender.setOnClickListener(new View.OnClickListener() { //Put one for m and f buttons, then it may be more responsive
            @Override
            public void onClick(View view) {
                registerAvalible();

            }
        });


        mEmail.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                signInAvalible();
                registerAvalible();

            }
        });

        mLastName.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                signInAvalible();
                registerAvalible();

            }
        });

        mFirstName.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                signInAvalible();
                registerAvalible();

            }
        });

        mServerPort.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                signInAvalible();
                registerAvalible();

            }
        });

        mServerHost.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                signInAvalible();
                registerAvalible();

            }
        });


        mUserName.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                signInAvalible();
                registerAvalible();

            }
        });

        mPassword.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                signInAvalible();
                registerAvalible();

            }
        });


        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serverHost = mServerHost.getText().toString();
                serverPort = mServerPort.getText().toString();
                userName = mUserName.getText().toString();
                password = mPassword.getText().toString();
                boolean temp = userName.isEmpty();
                if(serverPort.isEmpty() || serverHost.isEmpty()||userName.isEmpty() || password.isEmpty()){
                    Toast.makeText(getContext(),"Fill In missing Parts.",Toast.LENGTH_SHORT).show();
                    return;
                }
                LoginFetchr login = new LoginFetchr();
                login.execute();
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                serverHost = mServerHost.getText().toString();
                serverPort = mServerPort.getText().toString();


                userName = mUserName.getText().toString();
                password = mPassword.getText().toString();
                firstName = mFirstName.getText().toString();
                lastName = mLastName.getText().toString();
                email = mEmail.getText().toString();

                //Checks to see what button was pressed.

                if(mGender.getCheckedRadioButtonId() == mMale.getId()){
                    gender = "m";
                }
                else if (mGender.getCheckedRadioButtonId() == mFemale.getId()){
                    gender = "f";
                }
                else{
                    gender = null;
                }
                if(serverHost.isEmpty()||serverPort.isEmpty()||userName.isEmpty()|| password.isEmpty() || firstName.isEmpty()|| lastName.isEmpty()  || email.isEmpty()|| gender.isEmpty()){
                    return;
                }
                RegisterFetchr register = new RegisterFetchr();
                register.execute();
            }
        });

        return view;
    }

    //Handles all post server proxy commands
    public Object taskPostProxy(String serverHost, String serverPort, Object obj,String pathExtension, Object resultType) {
        Object result = new Object();

        try {

            HttpURLConnection connection = (HttpURLConnection) new URL("http://" + serverHost + ":" + serverPort + pathExtension).openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.addRequestProperty("Accept", "application/json");
            connection.connect();

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            String requestStream = gson.toJson(obj);

            OutputStream reqBody = connection.getOutputStream();

            OutputStreamWriter streamWriter = new OutputStreamWriter(reqBody);
            streamWriter.write(requestStream);
            streamWriter.flush();

            reqBody.close();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStream serverResponse = connection.getInputStream();



                StringBuilder sb = new StringBuilder();
                InputStreamReader sr = new InputStreamReader(serverResponse);
                char[] buffer = new char[1024];
                int length;
                while ((length = sr.read(buffer)) > 0) {
                    sb.append(buffer, 0, length);
                }
                String respData = sb.toString();


                result = gson.fromJson(respData, resultType.getClass());


            }
            else{
                return resultType;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    //Handles all get server proxy commands
    public Object taskGetProxy(String serverHost, String serverPort,String pathExtension, Object resultType) {
        Object result = new Object();

        try {

            HttpURLConnection connection = (HttpURLConnection) new URL("http://" + serverHost + ":" + serverPort + pathExtension).openConnection();
            connection.setDoOutput(false);
            connection.setRequestMethod("GET");
            connection.addRequestProperty("Authorization", ClientInfo.getInstance().getAuthToken());
            connection.addRequestProperty("Accept", "application/json");

            connection.connect();

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStream serverResponse = connection.getInputStream();

                StringBuilder sb = new StringBuilder();
                InputStreamReader sr = new InputStreamReader(serverResponse);
                char[] buffer = new char[1024];
                int length;
                while ((length = sr.read(buffer)) > 0) {
                    sb.append(buffer, 0, length);
                }
                String respData = sb.toString();


                result = gson.fromJson(respData, resultType.getClass());


            }
            else{
                return resultType;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }


    public class LoginFetchr extends AsyncTask<Void, String, LoginResult> {


        protected LoginResult doInBackground(Void... voids) {

            LoginRequest loginRequest = new LoginRequest();

            loginRequest.setuserName(userName);
            loginRequest.setPassword(password);

            LoginResult loginResult = (LoginResult) taskPostProxy(serverHost, serverPort, loginRequest, "/user/login", new LoginResult());
            return loginResult;
        }

        protected void onPostExecute(LoginResult lR) {
            if (!lR.isSuccess()) {
                Toast.makeText(getContext(), "Login Failed.", Toast.LENGTH_SHORT).show();
            }
            else {

                ClientInfo.getInstance().setLoginResult(lR);
                ClientInfo.getInstance().setAuthToken(lR.getAutherizationToken());
                ClientInfo.getInstance().setServerHost(serverHost);
                ClientInfo.getInstance().setServerPort(serverPort);
                ClientInfo.getInstance().setUserPersonID(lR.getpersonID());
                Toast.makeText(getContext(), "Login Successful.", Toast.LENGTH_SHORT).show();

                UserDataFetchr udf = new UserDataFetchr();
                udf.execute();
            }

        }
    }



    public class RegisterFetchr extends AsyncTask<Void, String, RegisterResult> {


        protected RegisterResult doInBackground(Void... voids) {

            RegisterRequest rr = new RegisterRequest();

            rr.setuserName(userName);
            rr.setPassword(password);
            rr.setEmail(email);
            rr.setFirstName(firstName);
            rr.setLastName(lastName);
            rr.setGender(gender);


            RegisterResult rR =  (RegisterResult) taskPostProxy(serverHost, serverPort,rr,"/user/register",new RegisterResult());

            return rR;
        }

        protected void onPostExecute(RegisterResult rR) {

            if (!rR.isSuccess()) {
                Toast.makeText(getContext(), "Registration Failed.", Toast.LENGTH_SHORT).show();
            } else {
                ClientInfo.getInstance().setRegisterResult(rR);
                ClientInfo.getInstance().setAuthToken(rR.getauthToken());
                ClientInfo.getInstance().setUserPersonID(rR.getpersonID());
                ClientInfo.getInstance().setServerHost(serverHost);
                ClientInfo.getInstance().setServerPort(serverPort);
                Toast.makeText(getContext(),
                        "Registration Successful.",
                        Toast.LENGTH_SHORT).show();

                UserDataFetchr udf = new UserDataFetchr();
                udf.execute();
            }
        }
    }



    public class UserDataFetchr extends AsyncTask<Void, String, Boolean> {


        protected Boolean doInBackground(Void... voids) {

            PersonResult pR =  (PersonResult) taskGetProxy(serverHost, serverPort,"/person",new PersonResult());
            EventResult eR =  (EventResult) taskGetProxy(serverHost, serverPort,"/event",new EventResult());


            ClientInfo.getInstance().setPersonResult(pR);
            ClientInfo.getInstance().setEventResult(eR);
            return true;
        }

        protected void onPostExecute(Boolean bool) {

            if (!ClientInfo.getInstance().getPersonResult().isSuccess()) {
                Toast.makeText(getContext(), "Person Gather failed Failed.", Toast.LENGTH_SHORT).show();
            }
            else{
                String output = "First Name: " + ClientInfo.getInstance().getPersonResult().getPeople().get(0).getFirst_name() + '\n' + "Last Name: " + ClientInfo.getInstance().getPersonResult().getPeople().get(0).getLast_name();
                Toast.makeText(getContext(), output, Toast.LENGTH_SHORT).show();
            }

            MainActivity main = (MainActivity) getContext();
            main.startMap();



        }
    }






}


