package handler;

import com.sun.net.httpserver.HttpExchange;


import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.net.HttpURLConnection;

import java.nio.file.Path;

import com.sun.net.httpserver.HttpHandler;



public class BaseHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        //determines if handle was done successfully
        boolean wasSuccessful = false;

        try {

            // Determine the HTTP request type (GET, POST, etc.).
            // Only allow POST requests for this operation.
            // This operation requires a POST request, because the
            // client is "posting" information to the server for processing.
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {

                //String containing the url desired
                String URLrequested = exchange.getRequestURI().toString();

                //Checks to see if it is just the open URL as ex: localhost:8080, this is mapped to index.html, this is the case for nothing added.
                if (URLrequested.length() == 1){


                    String location = "web/index.html";
                    Path path = FileSystems.getDefault().getPath(location);

                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                    Files.copy(path, exchange.getResponseBody());

                    exchange.getResponseBody().close();

                } else if (URLrequested.equals("/")) {

                    //
                    String location = "web" + URLrequested;

                    //Obtain the file path, this is the same method used for the name and locations data draw
                    Path filePath = FileSystems.getDefault().getPath(location);

                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                    //Copies Files into the exchange's responsive body header
                    Files.copy(filePath, exchange.getResponseBody());

                    exchange.getResponseBody().close();

                }
                else if(URLrequested.equals("/css/main.css")){
                    String location = "web/css/main.css";
                    //Obtain the file path, this is the same method used for the name and locations data draw
                    Path filePath = FileSystems.getDefault().getPath(location);

                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                    //Copies Files into the response body
                    Files.copy(filePath, exchange.getResponseBody());

                    exchange.getResponseBody().close();

                }
                else{
                    String location = "web/HTML/404.html";

                    //Obtain the file path, this is the same method used for the name and locations data draw
                    Path filePath = FileSystems.getDefault().getPath(location);

                    //Response header needs to come first.
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);

                    //Copies Files into the response body
                    Files.copy(filePath, exchange.getResponseBody());


                    //Completes the exchange
                    exchange.getResponseBody().close();
                }
                wasSuccessful = true;


            }

            if (!wasSuccessful) {
                //Bad Server Response
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);

                //Completes the exchange
                exchange.getResponseBody().close();
            }
        }
        catch (IOException e) {
            //Bad Server Response
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            //Completes the exchange
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }


}