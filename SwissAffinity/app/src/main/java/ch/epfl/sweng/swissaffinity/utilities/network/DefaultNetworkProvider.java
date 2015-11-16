package ch.epfl.sweng.swissaffinity.utilities.network;


import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.HttpRetryException;
import java.net.HttpURLConnection;
import java.net.URL;

import ch.epfl.sweng.swissaffinity.utilities.network.users.UserClientException;

/**
 * The class that get the HTTP connection from the server URL.
 */
public class DefaultNetworkProvider implements NetworkProvider {
    public final static int HTTP_SUCCESS_START = 200;
    public final static int HTTP_SUCCESS_END = 299;

    @Override
    public HttpURLConnection getConnection(URL url) throws IOException {
        return (HttpURLConnection) url.openConnection();
    }

    @Override
    public String getContent(String serverURL) throws IOException {
        URL url = new URL(serverURL);
        HttpURLConnection connection = getConnection(url);
        connection.setReadTimeout(10000 /* milliseconds */);
        connection.setConnectTimeout(15000 /* milliseconds */);
        connection.setRequestMethod("GET");
        connection.setDoInput(true);
        connection.connect();
        int responseCode = connection.getResponseCode();
        if (responseCode < HTTP_SUCCESS_START || responseCode > HTTP_SUCCESS_END) {
            throw new IOException("Connection bad response code: " + responseCode);
        }
        return fetchContent(connection);
    }

    public Boolean checkCode(String serverURL) throws IOException{
        URL url = new URL(serverURL);

        HttpURLConnection conn = getConnection(url);
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.connect();
        int response = conn.getResponseCode();
        return (response==HttpURLConnection.HTTP_OK);
    }

    public  String postContent(String serverURL, JSONObject json) throws IOException {
        URL url = new URL(serverURL);
        String response = null;
        HttpURLConnection conn = getConnection(url);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.connect();
        OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
        out.write(json.toString());
        out.flush();
        out.close();
        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
           response =fetchContent(conn);
        } else if(responseCode == HttpURLConnection.HTTP_INTERNAL_ERROR){
            throw new ConnectException();
        } else if(responseCode == HttpURLConnection.HTTP_BAD_REQUEST) {
            response =fetchErrorContent(conn);
        } else {
            throw new ConnectException();
        }
        return response;
    }

    /**
     * make a String out of the GET request to the server
     */
    private String fetchContent(HttpURLConnection connection) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            return stringBuilder.toString();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    private String fetchErrorContent(HttpURLConnection connection) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            return stringBuilder.toString();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

}
