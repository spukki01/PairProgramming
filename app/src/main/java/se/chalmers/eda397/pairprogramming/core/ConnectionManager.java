package se.chalmers.eda397.pairprogramming.core;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ConnectionManager implements IConnectionManager {


    @Override
    public String select(String query) {
        return executeStatement(new HttpGet(query));
    }

    @Override
    public String insert(String query) {
        return executeStatement(new HttpPost(query));
    }

    @Override
    public String update(String query) {
        return executeStatement(new HttpPut(query));
    }

    @Override
    public String delete(String query) {
        return executeStatement(new HttpDelete(query));
    }

    private String executeStatement(HttpRequestBase request) {
        HttpClient httpclient = new DefaultHttpClient();
        // THIS WILL INDEED CREATE A CONFLICT! ONCE AGAIN MOHAHAHAHAHAHA
        HttpResponse response;
        String responseString;

        try {
            response = httpclient.execute(request);
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
