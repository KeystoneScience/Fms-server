package com.example.familymapclient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.google.gson.Gson;


import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.familymapclient.R;

import com.example.familymapclient.R;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import model.ClientInformation;
import model.User;
import requests.LoginRequest;
import requests.RegisterRequest;
import results.LoginResult;
import results.RegisterResult;

public class LoginFragment extends Fragment {

    private EditText mServerHost, mServerPort, mUserName,
            mPassword, mFirstName, mLastName, mEmail;
    private RadioGroup mGender;
    private RadioButton mMale,mFemale;
    private Button mSignIn, mRegister;
    private String serverHost, serverPort, userName,
            password, firstName, lastName, email, gender;
    private User user;
    private ClientInformation clientInfo = new ClientInformation();



    public LoginFragment() { }



    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    //@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.login_fragment, container, false);


        //May need to cast to EditText with (EditText) before each... or the appropriate type casts.
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

        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serverHost = mServerHost.getText().toString();
                serverPort = mServerPort.getText().toString();
                userName = mUserName.getText().toString();
                password = mPassword.getText().toString();
                boolean temp = userName.isEmpty();
                if(userName.isEmpty() || password.isEmpty()){
                    Toast.makeText(getContext(),"Fill In missing Parts.",Toast.LENGTH_SHORT).show();
                    return;
                }
                LoginFetchr login = new LoginFetchr();
                login.execute();
//                if(clientInfo.getLoginResult().isSuccess()){
//                    Toast.makeText(getContext(),"Login Was Successful",Toast.LENGTH_SHORT).show();
//
//                }
//                else{
//                    Toast.makeText(getContext(),"Login Failed.",Toast.LENGTH_SHORT).show();
//
//                }



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
                if(userName.isEmpty()|| password.isEmpty() || firstName.isEmpty()|| lastName.isEmpty()  || email.isEmpty()|| gender.isEmpty()){
                    Toast.makeText(getContext(),"Fill In missing Parts.",Toast.LENGTH_SHORT).show();
                    return;
                }
                RegisterFetchr register = new RegisterFetchr();
                register.execute();
            }
        });

        return view;
    }



    public class LoginFetchr extends AsyncTask<Void, String, LoginResult> {


        public LoginResult login(String serverHost, String serverPort, LoginRequest loginRequest) {

            // This method shows how to send a POST request to a server
            LoginResult loginResult = new LoginResult();

            try {
                // Create a URL indicating where the server is running, and which
                // web API operation we want to call
                URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/login");

                // Start constructing our HTTP request
                HttpURLConnection http = (HttpURLConnection)url.openConnection();

                // Specify that we are sending an HTTP POST request
                http.setRequestMethod("POST");
                // Indicate that this request will contain an HTTP request body
                http.setDoOutput(true);	// There is a request body

                // Add an auth token to the request in the HTTP "Authorization" header
                // http.addRequestProperty("Authorization", "afj232hj2332");

                // Specify that we would like to receive the server's response in JSON
                // format by putting an HTTP "Accept" header on the request (this is not
                // necessary because our server only returns JSON responses, but it
                // provides one more example of how to add a header to an HTTP request).
                http.addRequestProperty("Accept", "application/json");

                // Connect to the server and send the HTTP request
                http.connect();

                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                // This is the JSON string we will send in the HTTP request body
                String reqData = gson.toJson(loginRequest);

                // Get the output stream containing the HTTP request body
                OutputStream reqBody = http.getOutputStream();
                // Write the JSON data to the request body

                OutputStreamWriter sw = new OutputStreamWriter(reqBody);
                sw.write(reqData);
                sw.flush();


                // Close the request body output stream, indicating that the
                // request is complete
                reqBody.close();

                // By the time we get here, the HTTP response has been received from the server.
                // Check to make sure that the HTTP response from the server contains a 200
                // status code, which means "success".  Treat anything else as a failure.
                if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    // The HTTP response status code indicates success,
                    // so print a success message
                    Log.v("SERVER PROXY", "HTTP_OK");

                    // Get the input stream containing the HTTP response body
                    InputStream respBody = http.getInputStream();
                    // Extract JSON data from the HTTP response body


                    StringBuilder sb = new StringBuilder();
                    InputStreamReader sr = new InputStreamReader(respBody);
                    char[] buf = new char[1024];
                    int len;
                    while ((len = sr.read(buf)) > 0) {
                        sb.append(buf, 0, len);
                    }
                    sb.toString();
                    String respData = sb.toString();


                    // Display the JSON data returned from the server
                    Log.v("SERVER PROXY", "RESPDATA: " + respData);

                    loginResult = gson.fromJson(respData, LoginResult.class);

                }
                else {
                    // The HTTP response status code indicates an error
                    // occurred, so print out the message from the HTTP response
                    Log.v("SERVER PROXY", "HTTP_NOT_OK");

                    loginResult.setMessage(null);
                }

            }
            catch (IOException e) {
                // An exception was thrown, so display the exception's stack trace
                e.printStackTrace();
            }

            return loginResult;
        }


        //protected Long doInBackground(URL... urls) {
        protected LoginResult doInBackground(Void... voids) {
            //   loginSuccess = false;
            //make the login info into a LoginRequest
            LoginRequest loginRequest = new LoginRequest();

            loginRequest.setuserName(userName);
            loginRequest.setPassword(password);

            publishProgress("Attempting to login");



            LoginResult loginResult = login(serverHost, serverPort, loginRequest);

            Log.v("doinbackground", "AUTHTOKEN: " + loginResult.getAutherizationToken());
            return loginResult;
        }

        protected void onProgressUpdate(String... updateMessage) {
            Toast.makeText(getContext(), updateMessage[0], Toast.LENGTH_SHORT).show();
        }

        protected void onPostExecute(LoginResult loginResult) {
            //do the UI stuff
            if (loginResult.getAutherizationToken() == null) {
                Log.v("LOGIN FRAGMENT", "AUTHTOKEN NULL");
                //loginSuccess = false;
                Toast.makeText(getContext(), "Login Failed.", Toast.LENGTH_SHORT).show();
            }
            else {
                //loginSuccess = true;
                Log.v("LOGINFRAGMENT", "LOGIN SUCESSFUL.");


                clientInfo.setLoginResult(loginResult);
                clientInfo.setAuthToken(loginResult.getAutherizationToken());
                clientInfo.setUserPersonID(loginResult.getpersonID());
                clientInfo.setServerHost(serverHost);
                clientInfo.setServerPort(serverPort);
            }

            if (loginResult.isSuccess()) {
                Toast.makeText(getContext(),
                        "Login Successful.",
                        Toast.LENGTH_SHORT).show();
                //Get Family Data
//                GetFamilyDataTask getFamilyDataTask = new GetFamilyDataTask();
//                getFamilyDataTask.execute();
            }
        }
    }



    public class RegisterFetchr extends AsyncTask<Void, String, RegisterResult> {


        public RegisterResult register(String serverHost, String serverPort, RegisterRequest registerRequest) {
            RegisterResult registerResult = new RegisterResult();

            try {
                URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/register");

                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setDoOutput(true);
                http.setRequestMethod("POST");

                http.addRequestProperty("Accept", "application/json");

                http.connect();

                Gson gson = new Gson();

                String reqData = gson.toJson(registerRequest);

                OutputStream reqBody = http.getOutputStream();

                OutputStreamWriter sw = new OutputStreamWriter(reqBody);
                sw.write(reqData);
                sw.flush();


                // Close the request body output stream, indicating that the
                // request is complete
                reqBody.close();

                // By the time we get here, the HTTP response has been received from the server.
                // Check to make sure that the HTTP response from the server contains a 200
                // status code, which means "success".  Treat anything else as a failure.
                if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    // The HTTP response status code indicates success,
                    // so print a success message
                    Log.v("SERVER PROXY", "HTTP_OK");

                    // Get the input stream containing the HTTP response body
                    InputStream respBody = http.getInputStream();
                    // Extract JSON data from the HTTP response body


                    StringBuilder sb = new StringBuilder();
                    InputStreamReader sr = new InputStreamReader(respBody);
                    char[] buf = new char[1024];
                    int len;
                    while ((len = sr.read(buf)) > 0) {
                        sb.append(buf, 0, len);
                    }
                    sb.toString();
                    String respData = sb.toString();


                    // Display the JSON data returned from the server
                    Log.v("SERVER PROXY", "RESPDATA: " + respData);

                    registerResult = gson.fromJson(respData, RegisterResult.class);

                } else {
                    // The HTTP response status code indicates an error
                    // occurred, so print out the message from the HTTP response
                    Log.v("SERVER PROXY", "HTTP_NOT_OK");

                    registerResult.setMessage("Server Error");
                }

            } catch (IOException e) {
                // An exception was thrown, so display the exception's stack trace
                e.printStackTrace();
            }

            return registerResult;
        }


        //protected Long doInBackground(URL... urls) {
        protected RegisterResult doInBackground(Void... voids) {
            //   loginSuccess = false;
            //make the login info into a LoginRequest
            RegisterRequest rr = new RegisterRequest();

            rr.setuserName(userName);
            rr.setPassword(password);
            rr.setEmail(email);
            rr.setFirstName(firstName);
            rr.setLastName(lastName);
            rr.setGender(gender);


            RegisterResult rR = register(serverHost, serverPort, rr);

            return rR;
        }

        protected void onPostExecute(RegisterResult rR) {
            //do the UI stuff
            if (!rR.isSuccess()) {
                Toast.makeText(getContext(), "Registration Failed.", Toast.LENGTH_SHORT).show();
            } else {
                clientInfo.setRegisterResult(rR);
                clientInfo.setAuthToken(rR.getauthToken());
                clientInfo.setUserPersonID(rR.getpersonID());
                clientInfo.setServerHost(serverHost);
                clientInfo.setServerPort(serverPort);
            }

            if (rR.isSuccess()) {
                Toast.makeText(getContext(),
                        "Registration Successful.",
                        Toast.LENGTH_SHORT).show();
                //Get Family Data
//                GetFamilyDataTask getFamilyDataTask = new GetFamilyDataTask();
//                getFamilyDataTask.execute();
            }
        }
    }





}


