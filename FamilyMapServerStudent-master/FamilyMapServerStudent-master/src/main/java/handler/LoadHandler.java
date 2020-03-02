package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import request.LoadRequest;
import request.RegisterRequest;
import result.LoadResult;
import result.RegisterResult;
import service.LoadService;
import service.RegisterService;

import java.io.*;
import java.net.HttpURLConnection;

public class LoadHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        LoadResult lr = new LoadResult();

        try {
            // Determine the HTTP request type (GET, POST, etc.).
            // Only allow POST requests for this operation.
            // This operation requires a POST request, because the
            // client is "posting" information to the server for processing.
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                LoadService ls = new LoadService();
                LoadRequest lq;


                // Get the request body input stream
                InputStream reqBody = exchange.getRequestBody();
                // Read JSON string from the input stream
                String reqData = readString(reqBody);

                // Display/log the request JSON data
                System.out.println(reqData);

                //Reader toBeRead = new InputStreamReader(exchange.getRequestBody());
                Gson gson = new Gson();

                lq = gson.fromJson(reqData, LoadRequest.class); //reqData was toBeRead

                lr = ls.load(lq);

                if (lr.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);



                    /*
                    Do some handling with oerhaps an output stream, then write that
                    string to a response body
                     */

                    String responseJsonString = "{\"message\" : \"" + lr.getMessage() + "\"}";



                    OutputStreamWriter osq = new OutputStreamWriter(exchange.getResponseBody());
                    osq.write(responseJsonString);
                    osq.flush();

                    // output stream, indicating that the response is complete.
                    exchange.getResponseBody().close();


                } else {
                    // The HTTP request was invalid somehow, so we return a "bad request"
                    // status code to the client.
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);


                    // We are not sending a response body, so close the response body
                    /*
                    Do some handling with oerhaps an output stream, then write that
                    string to a response body
                     */

                    String responseJsonString = "{\"message\" : \"" + lr.getMessage() + "\"}";



                    OutputStreamWriter osq = new OutputStreamWriter(exchange.getResponseBody());
                    osq.write(responseJsonString);
                    osq.flush();

                    // output stream, indicating that the response is complete.
                    exchange.getResponseBody().close();
                }


            }
        }
        catch (IOException e) {
            // Some kind of internal error has occurred inside the server (not the
            // client's fault), so we return an "internal server error" status code
            // to the client.
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            String responseJsonString = "{\"message\" : \"" + "Internal server error" + "\"}";



            OutputStreamWriter osq = new OutputStreamWriter(exchange.getResponseBody());
            osq.write(responseJsonString);
            osq.flush();

            // output stream, indicating that the response is complete.
            exchange.getResponseBody().close();
            //e.printStackTrace();
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
