package ch.epfl.sweng.swissaffinity.utilities.network;

import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Constructs {@link HttpURLConnection} objects that can be used to
 * retrieve data from a given {@link URL}.
 */
public interface NetworkProvider {
    String SERVER_URL = "http://beecreative.ch";

    /**
     * Returns a new {@link HttpURLConnection} object for the given {@link URL}.
     *
     * @param serverURL a valid HTTP or HTTPS URL.
     * @return a new {@link HttpURLConnection} object for successful
     * connections.
     * @throws IOException if the connection could not be established or if the
     *                     URL is not HTTP/HTTPS.
     */
    HttpURLConnection getConnection(String serverURL) throws IOException;

    /**
     * Get the content of a HTTP GET request to the provided server URL.
     *
     * @param serverURL the server address
     * @return the content of the request
     * @throws IOException if no success with the request.
     */
    String getContent(String serverURL) throws IOException;

    /**
     * Post the content to a HTTP POST request to the provided server URL.
     *
     * @param serverURL the server address
     * @param json      the content of the post
     * @return the content of the server response
     * @throws IOException if no success with the request.
     */
    String postContent(String serverURL, JSONObject json) throws IOException;

    /**
     * Post a HTTP DELETE request to the provided server URL.
     *
     * @param serverURL the server address
     * @return the response code from the server
     * @throws IOException if no success with the request.
     */
    String deleteContent(String serverURL) throws IOException;
}
