package ch.epfl.sweng.swissaffinity.utilities.network;


import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
        if (responseCode != HTTP_SUCCESS_START) {
            throw new IOException("Connection bad response code: " + responseCode);
        }
        return fetchContent(connection);
    }

    public void yieldPUTContent(String serverURL) throws IOException {
        //TODO: implement this part
        throw new IOException("Not yet implemented");
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
            Log.d("HTTPFetchContent", "Fetched string of length " + stringBuilder.length());
            return stringBuilder.toString();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }
}
