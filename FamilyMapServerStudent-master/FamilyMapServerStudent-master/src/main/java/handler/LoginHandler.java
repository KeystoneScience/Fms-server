package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import requests.LoginRequest;
import results.LoginResult;
import service.LoginService;

import java.io.*;
import java.net.HttpURLConnection;

public class LoginHandler extends HandlerHelper implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        LoginResult lr = new LoginResult();

        try {
            // Determine the HTTP request type (GET, POST, etc.).
            // Only allow POST requests for this operation.
            // This operation requires a POST request, because the
            // client is "posting" information to the server for processing.
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                LoginService ls = new LoginService();
                LoginRequest rq;


                // Get the request body input stream
                InputStream reqBody = exchange.getRequestBody();
                // Read JSON string from the input stream
                String reqData = readString(reqBody);

                // Display/log the request JSON data
                System.out.println(reqData);

                //Reader toBeRead = new InputStreamReader(exchange.getRequestBody());
                Gson gson = new Gson();

                rq = gson.fromJson(reqData, LoginRequest.class); //reqData was toBeRead

                lr = ls.login(rq);

                if (lr.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);




                } else {
                    // The HTTP request was invalid somehow, so we return a "bad request"
                    // status code to the client.
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);

                }

                writter(lr,exchange);

                exchange.getResponseBody().close();


            }
        }
        catch (IOException e) {
            // Some kind of internal error has occurred inside the server (not the
            // client's fault), so we return an "internal server error" status code
            // to the client.
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            // We are not sending a response body, so close the response body
            // output stream, indicating that the response is complete.
            lr.setMessage(e.getMessage());

            writter(lr,exchange); //WAS CHANGED

            exchange.getResponseBody().close();

//            String responseJsonString ="{\"message\" : \"" + e.getMessage() + "\"}";
//
//
//
//            OutputStreamWriter osq = new OutputStreamWriter(exchange.getResponseBody());
//            osq.write(responseJsonString);
//            osq.flush();
//
//            // output stream, indicating that the response is complete.
//            exchange.getResponseBody().close();
        }
    }

    /*
        The readString method shows how to read a String from an InputStream.
    */

}
