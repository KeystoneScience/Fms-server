package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.DataAccessException;
import requests.FillRequest;
import results.FillResult;
import service.FillService;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class FillHandler extends HandlerHelper implements HttpHandler {


    @Override

    public void handle(HttpExchange exchange) throws IOException {

        FillResult fr = new FillResult();


        try {
            // Determine the HTTP request type (GET, POST, etc.).
            // Only allow POST requests for this operation.
            // This operation requires a POST request, because the
            // client is "posting" information to the server for processing.
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                FillService fs = new FillService();



                    String theUrl = exchange.getRequestURI().toString();
                    List<String> UrlRequests = breakUpURL(theUrl);

                    if (UrlRequests.size() <= 1 || UrlRequests.size() > 3) {
                        fr.setSuccess(false);
                        fr.setMessage("Input is not formatted correctly.");
                    } else {
                        int numGenerations = 4;
                        if(UrlRequests.size() == 3){
                            numGenerations = Integer.parseInt(UrlRequests.get(2));
                        }//FIXME add some try catch for non-integer given.
                        FillRequest fR = new FillRequest(UrlRequests.get(1),numGenerations);

                        fr = fs.fill(fR);
                        if(!fr.isSuccess()){
                            throw new DataAccessException(fr.getMessage());
                        }
                        fr.setMessage("Successfully added " + fr.getNumPeople() + " persons and "+ fr.getNumEvents()+" events to the database.");

                    }
                    if (fr.isSuccess()){
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);


                        fr.nullify();
                        writter(fr,exchange);

                        // output stream, indicating that the response is complete.
                        exchange.getResponseBody().close();

                    }
                    else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);



                        fr.nullify();
                        writter(fr,exchange);

                        // output stream, indicating that the response is complete.
                        exchange.getResponseBody().close();
                    }
            }
        }
        catch (IOException | DataAccessException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);



            fr.nullify();
            writter(fr,exchange);

            // output stream, indicating that the response is complete.
            exchange.getResponseBody().close();
        }
    }





}
