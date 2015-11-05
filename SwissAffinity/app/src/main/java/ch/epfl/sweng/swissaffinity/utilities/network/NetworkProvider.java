package ch.epfl.sweng.swissaffinity.utilities.network;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Constructs {@link HttpURLConnection} objects that can be used to
 * retrieve data from a given {@link URL}.
 */
interface NetworkProvider {
    /**
     * Returns a new {@link HttpURLConnection} object for the given {@link URL}.
     *
     * @param url a valid HTTP or HTTPS URL.
     * @return a new {@link HttpURLConnection} object for successful
     * connections.
     * @throws IOException if the connection could not be established or if the
     *                     URL is not HTTP/HTTPS.
     */
    HttpURLConnection getConnection(URL url) throws IOException;

    /**
     * Get the content of a HTTP GET request to the provided server URL.
     *
     * @param serverURL the server address
     * @return the content of the request
     * @throws IOException if no success with the request.
     */
    String getContent(String serverURL) throws IOException;
}
