package ch.epfl.sweng.swissaffinity.utilities.client_utilities;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Joel on 10/26/2015.
 */
public interface NetworkProvider {
    /**
     *
     * @param url a valid HTTP or HTTPS URL.
     * @return a new (@link HttpURLConnection) object is succesful.
     * @throws IOException if the connection could not be established
     * or if the url is not HTTP/HTTPS.
     */
    HttpURLConnection getConnection(URL url) throws IOException;
}
