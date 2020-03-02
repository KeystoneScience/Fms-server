package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import dao.DataAccessException;
import dao.Database;
import model.Person;
import request.RegisterRequest;
import result.PersonResult;
import result.PersonsResult;
import result.RegisterResult;
import service.PersonService;
import service.RegisterService;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class PersonHandler {


    //@Override

    public void handle(HttpExchange exchange) throws IOException {




        try {
            // Determine the HTTP request type (GET, POST, etc.).
            // Only allow POST requests for this operation.
            // This operation requires a POST request, because the
            // client is "posting" information to the server for processing.
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {
                PersonResult pr = new PersonResult();
                PersonService ps = new PersonService();


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
                    UrlRequests.add(partition.toString());

                    if (UrlRequests.size() < 1 || UrlRequests.size() > 2) {
                        pr.setSuccess(false);
                        pr.setMessage("Input is not formatted correctly.");
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        String responseJsonString = new String("{\"message\" : \"" + pr.getMessage() + "\"}");


                        OutputStreamWriter osq = new OutputStreamWriter(exchange.getResponseBody());
                        osq.write(responseJsonString);
                        osq.flush();

                        // output stream, indicating that the response is complete.
                        exchange.getResponseBody().close();


                    } else if (UrlRequests.size() == 1) {
                        pr = ps.Persons(authToken);
                        if (!pr.isSuccess()) {
                            throw new DataAccessException(pr.getMessage());
                        }
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                        Gson gson = new Gson();
                        //Fixme might need to check case work.
                        String responseJsonString = gson.toJson(pr);


                        OutputStreamWriter osq = new OutputStreamWriter(exchange.getResponseBody());
                        osq.write(responseJsonString);
                        osq.flush();

                        // output stream, indicating that the response is complete.
                        exchange.getResponseBody().close();

                    } else if (UrlRequests.size() == 2) {
                        pr = ps.findPerson(UrlRequests.get(0),authToken);
                        if(pr.isSuccess()){
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);


                            Gson gson = new Gson();
                            //Fixme might need to check case work.
                            String responseJsonString = gson.toJson(pr);


                            OutputStreamWriter osq = new OutputStreamWriter(exchange.getResponseBody());
                            osq.write(responseJsonString);
                            osq.flush();

                            // output stream, indicating that the response is complete.
                            exchange.getResponseBody().close();
                        }
                        else{
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);

                            String responseJsonString = "{\"message\" : \"" + pr.getMessage() + "\"}";


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
