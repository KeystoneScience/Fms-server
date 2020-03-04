package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import requests.LoadRequest;
import results.LoadResult;
import service.LoadService;

import java.io.*;
import java.net.HttpURLConnection;

public class LoadHandler extends HandlerHelper implements HttpHandler {

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

                    writter(lr,exchange);

                    // output stream, indicating that the response is complete.
                    exchange.getResponseBody().close();

                } else {
                    // The HTTP request was invalid somehow, so we return a "bad request"
                    // status code to the client.
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);


                    writter(lr,exchange);

                    // output stream, indicating that the response is complete.
                    exchange.getResponseBody().close();
                }


            }
        }
        catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);


            writter(lr,exchange);

            // output stream, indicating that the response is complete.
            exchange.getResponseBody().close();
        }
    }

}
