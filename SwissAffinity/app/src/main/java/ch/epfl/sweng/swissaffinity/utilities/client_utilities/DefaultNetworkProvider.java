package ch.epfl.sweng.swissaffinity.utilities.client_utilities;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Joel on 10/26/2015.
 */
public class DefaultNetworkProvider implements NetworkProvider {
    public final static int HTTP_SUCCESS_START = 200;
    public final static int HTTP_SUCCESS_END = 299;
    /**
     * Default implementation handling basic GET and PUT requests.
     */
    @Override
    public HttpURLConnection getConnection(URL url) throws IOException {
        return (HttpURLConnection) url.openConnection();
    }
    public String yieldGETContent(String url) throws IOException{

        URL oUrl = new URL(url);
        HttpURLConnection conn = this.getConnection(oUrl);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.connect();
        int response = conn.getResponseCode();
        if (response < HTTP_SUCCESS_START || response > HTTP_SUCCESS_END) {
            throw new IOException("Invalid HTTP response code");
        }

        return fetchContent(conn);

    }
    public void yieldPUTContent(String url) throws IOException{
        throw new IOException("Not yet implemented");
    }

    private String fetchContent(HttpURLConnection conn) throws IOException {
        StringBuilder out = new StringBuilder();
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line + "\n");
            }

            String result = out.toString();
            Log.d("HTTPFetchContent", "Fetched string of length "
                    + result.length());
            return result;
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }
}
