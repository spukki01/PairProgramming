package se.chalmers.eda397.pairprogramming.core;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ConnectionManager implements IConnectionManager {

    @Override
    public String executeQuery(String url) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        String responseString;

        try {
            //TODO "HttpGet() should be generic.
            response = httpclient.execute(new HttpGet(url));
            StatusLine statusLine = response.getStatusLine();

            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);

                responseString = out.toString();

                out.close();
            } else{
                //Closes the connection.
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }

        } catch (Exception e) {
            e.printStackTrace();
            responseString = e.getMessage();
        }

        return responseString;
    }
}
