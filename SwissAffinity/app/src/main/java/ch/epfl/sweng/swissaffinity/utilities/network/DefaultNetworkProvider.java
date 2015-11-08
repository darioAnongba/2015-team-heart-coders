package ch.epfl.sweng.swissaffinity.utilities.network;


import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
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

    /**
     * Get the content of a HTTP GET request to the provided server URL.
     *
     * @param serverURL the server address
     *
     * @return the content of the request
     *
     * @throws IOException if no success with the request.
     */
    @Override
    public String getContent(String serverURL) throws IOException {

        URL url = new URL(serverURL);

        HttpURLConnection conn = getConnection(url);
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.connect();
        int response = conn.getResponseCode();
        if (response < HTTP_SUCCESS_START || response > HTTP_SUCCESS_END) {
            throw new IOException("Invalid HTTP response code");
        }

        return fetchContent(conn);
    }

    public void yieldPUTContent(String serverURL) throws IOException {
        //TODO: implement this part
        throw new IOException("Not yet implemented");
    }

    /**
     * make a String out of the GET request to the server
     */
    private String fetchContent(HttpURLConnection conn) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
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
