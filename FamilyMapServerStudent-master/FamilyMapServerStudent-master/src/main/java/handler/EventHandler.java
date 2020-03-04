package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.DataAccessException;
import results.EventResult;
import service.EventService;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class EventHandler implements HttpHandler {


    @Override

    public void handle(HttpExchange exchange) throws IOException {

        EventResult er = new EventResult();


        try {
            // Determine the HTTP request type (GET, POST, etc.).
            // Only allow POST requests for this operation.
            // This operation requires a POST request, because the
            // client is "posting" information to the server for processing.
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {

                EventService es = new EventService();


                Headers reqHeaders = exchange.getRequestHeaders();

                //Checks If AuthKey is given
                if (exchange.getRequestHeaders().containsKey("Authorization")) {
                    String authToken = reqHeaders.getFirst("Authorization");

                    String theUrl = exchange.getRequestURI().toString();
                    List<String> UrlRequests = new ArrayList<>();
                    StringBuilder partition = new StringBuilder();

                    //Breaks up the Url to be handled
                    for (int i = 1; i < theUrl.length(); i++) {
                        if (theUrl.charAt(i) != '/') {
                            partition.append(theUrl.charAt(i) + "");
                        } else {
                            UrlRequests.add(partition.toString());
                            partition.delete(0, partition.length());
                        }

                    }
                    if(!partition.toString().equals("")) {
                        UrlRequests.add(partition.toString());
                    }

                    if (UrlRequests.size() < 1 || UrlRequests.size() > 2) {
                        er.setSuccess(false);
                        er.setMessage("Input is not formatted correctly.");
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        Gson gson = new Gson();

                        String responseJsonString = gson.toJson(er);


                        OutputStreamWriter osq = new OutputStreamWriter(exchange.getResponseBody());
                        osq.write(responseJsonString);
                        osq.flush();

                        // output stream, indicating that the response is complete.
                        exchange.getResponseBody().close();


                    } else if (UrlRequests.size() == 1) {
                        er = es.getAllUserEvents(authToken);
                        if (!er.isSuccess()) {
                            throw new DataAccessException(er.getMessage());
                        }
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                        Gson gson = new Gson();
                        //Fixme might need to check case work.
                        String responseJsonString = gson.toJson(er);


                        OutputStreamWriter osq = new OutputStreamWriter(exchange.getResponseBody());
                        osq.write(responseJsonString);
                        osq.flush();

                        // output stream, indicating that the response is complete.
                        exchange.getResponseBody().close();

                    } else if (UrlRequests.size() == 2) {
                        er = es.getEvent(UrlRequests.get(1),authToken);
                        if(er.isSuccess()){
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);


                            Gson gson = new Gson();
                            //Fixme might need to check case work.
                            String responseJsonString = gson.toJson(er);


                            OutputStreamWriter osq = new OutputStreamWriter(exchange.getResponseBody());
                            osq.write(responseJsonString);
                            osq.flush();

                            // output stream, indicating that the response is complete.
                            exchange.getResponseBody().close();
                        }
                        else{
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);

                            Gson gson = new Gson();
                            String responseJsonString = gson.toJson(er);

                            OutputStreamWriter osq = new OutputStreamWriter(exchange.getResponseBody());
                            osq.write(responseJsonString);
                            osq.flush();

                            // output stream, indicating that the response is complete.
                            exchange.getResponseBody().close();
                        }

                    }


                }

            }
        }
        catch (IOException | DataAccessException e) {
            er.setSuccess(false);
            // Some kind of internal error has occurred inside the server (not the
            // client's fault), so we return an "internal server error" status code
            // to the client.
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            er.setMessage(e.getMessage());
            Gson gson = new Gson();
            String responseJsonString = gson.toJson(er);

            OutputStreamWriter osq = new OutputStreamWriter(exchange.getResponseBody());
            osq.write(responseJsonString);
            osq.flush();

            // output stream, indicating that the response is complete.
            exchange.getResponseBody().close();
        }
    }





}
