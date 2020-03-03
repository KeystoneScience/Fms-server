package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import request.RegisterRequest;
import result.RegisterResult;
import service.RegisterService;

import java.io.*;
import java.net.HttpURLConnection;

public class RegisterHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {


        try {
            // Determine the HTTP request type (GET, POST, etc.).
            // Only allow POST requests for this operation.
            // This operation requires a POST request, because the
            // client is "posting" information to the server for processing.
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                RegisterService rs = new RegisterService();

                RegisterRequest rq;


                // Get the request body input stream
                InputStream reqBody = exchange.getRequestBody();
                // Read JSON string from the input stream
                String reqData = readString(reqBody);

                // Display/log the request JSON data
                System.out.println("New Register Request JSON input:");

                System.out.println(reqData);

                //Reader toBeRead = new InputStreamReader(exchange.getRequestBody());
                Gson gson = new Gson();

                rq = gson.fromJson(reqData, RegisterRequest.class); //reqData was toBeRead

                RegisterResult rr = rs.register(rq);

                if (rr.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);



                    /*
                    Do some handling with oerhaps an output stream, then write that
                    string to a response body
                     */


                } else {
                    // The HTTP request was invalid somehow, so we return a "bad request"
                    // status code to the client.
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);


                    // We are not sending a response body, so close the response body
                    /*
                    Do some handling with oerhaps an output stream, then write that
                    string to a response body
//                     */
//
//                    String responseJsonString = "{\"message\" : \"" + rr.getMessage() + "\"}";
//
//
//
//                    OutputStreamWriter osq = new OutputStreamWriter(exchange.getResponseBody());
//                    osq.write(responseJsonString);
//                    osq.flush();
//
//                    // output stream, indicating that the response is complete.
//                    exchange.getResponseBody().close();
                }

                String responseJsonString = gson.toJson(rr);



                OutputStreamWriter osq = new OutputStreamWriter(exchange.getResponseBody());
                osq.write(responseJsonString);
                osq.flush();

                // output stream, indicating that the response is complete.
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
            exchange.getResponseBody().close();

            // Display/log the stack trace
            e.printStackTrace();
        }
    }

    /*
        The readString method shows how to read a String from an InputStream.
    */
    private String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }
}
