package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.DataAccessException;
import results.PersonResult;
import service.PersonService;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class PersonHandler extends HandlerHelper implements HttpHandler {


    @Override

    public void handle(HttpExchange exchange) throws IOException {

        PersonResult pr = new PersonResult();


        try {
            // Determine the HTTP request type (GET, POST, etc.).
            // Only allow POST requests for this operation.
            // This operation requires a POST request, because the
            // client is "posting" information to the server for processing.
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {

                PersonService ps = new PersonService();


                Headers reqHeaders = exchange.getRequestHeaders();

                //Checks If AuthKey is given
                if (exchange.getRequestHeaders().containsKey("Authorization")) {
                    String authToken = reqHeaders.getFirst("Authorization");

                    String theUrl = exchange.getRequestURI().toString();
                    List<String> UrlRequests = breakUpURL(theUrl);

                    if (UrlRequests.size() < 1 || UrlRequests.size() > 2) {
                        pr.setSuccess(false);
                        pr.setMessage("Input is not formatted correctly.");
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);

                        writter(pr,exchange);


                        // output stream, indicating that the response is complete.
                        exchange.getResponseBody().close();


                    } else if (UrlRequests.size() == 1) {
                        pr = ps.Persons(authToken);
                        if (!pr.isSuccess()) {
                            throw new DataAccessException(pr.getMessage());
                        }
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                        writter(pr,exchange);

                        // output stream, indicating that the response is complete.
                        exchange.getResponseBody().close();

                    } else if (UrlRequests.size() == 2) {
                        pr = ps.findPerson(UrlRequests.get(1),authToken);
                        if(pr.isSuccess()){
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);


                           writter(pr,exchange);
                            // output stream, indicating that the response is complete.
                            exchange.getResponseBody().close();
                        }
                        else{
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);

                            writter(pr,exchange);

                            // output stream, indicating that the response is complete.
                            exchange.getResponseBody().close();
                        }

                    }


                }

            }
        }
        catch (IOException | DataAccessException e) {
            pr.setSuccess(false);
            // Some kind of internal error has occurred inside the server (not the
            // client's fault), so we return an "internal server error" status code
            // to the client.
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);

            writter(pr,exchange);

            // output stream, indicating that the response is complete.
            exchange.getResponseBody().close();
        }
    }



}
