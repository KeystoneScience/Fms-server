package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class HandlerHelper {
    public void writter(Object cr, HttpExchange ex) throws IOException {
        Gson gson = new Gson();


        OutputStreamWriter osq = new OutputStreamWriter(ex.getResponseBody());
        osq.write(gson.toJson(cr));
        osq.flush();

    }

    /*
    The readString method shows how to read a String from an InputStream.
*/

    //Reads an input stream.
    public String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }


    //Function used to break up a given url into a list of commands
    public List<String> breakUpURL(String theUrl){
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
        return UrlRequests;
    }

}
