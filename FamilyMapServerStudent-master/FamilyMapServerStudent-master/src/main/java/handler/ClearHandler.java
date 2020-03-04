package handler;


import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import results.ClearResult;
import service.ClearService;


public class ClearHandler implements HttpHandler {


    public ClearHandler(){
        //To be implemented later
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {

            if (exchange.getRequestMethod().toLowerCase().equals("post")) {



                ClearService cs = new ClearService();

                ClearResult cr = cs.clear();

                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                Gson gson = new Gson();

                String responseJsonString = gson.toJson(cr);


                OutputStreamWriter osq = new OutputStreamWriter(exchange.getResponseBody());
                osq.write(responseJsonString);
                osq.flush();

                // output stream, indicating that the response is complete.
                exchange.getResponseBody().close();

            }

        }
        catch (IOException e) {

            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);

            exchange.getResponseBody().close();

            // Display/log the stack trace
            e.printStackTrace();
        }
    }

}