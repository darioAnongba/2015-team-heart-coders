package ch.epfl.sweng.swissaffinity.users;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.List;

import ch.epfl.sweng.swissaffinity.utilities.Client;
import ch.epfl.sweng.swissaffinity.utilities.ClientException;
import ch.epfl.sweng.swissaffinity.utilities.client_utilities.DefaultNetworkProvider;

/**
 * Created by Joel on 10/26/2015.
 */
public class NetworkUserClient implements Client<User> {
    private final String mUserServerURL; //= "http://beecreative.ch/api/users/";
    private final DefaultNetworkProvider mNetworkProvider;

    /**
     * @param serverUrl       normally is "http://beecreative.ch/api/users/"
     * @param networkProvider a NetworkProvider object through which to channel the
     *                        server communication.
     */
    public NetworkUserClient(String serverUrl, DefaultNetworkProvider networkProvider) {
        mUserServerURL = serverUrl;
        mNetworkProvider = networkProvider;
    }

    @Override
    public User fetchByName(String userName) throws ClientException {
        final String url = mUserServerURL + userName + ".json";
        try {
            final String contentStr = mNetworkProvider.yieldGETContent(url);
            //TODO polymorphism should save the day
            //Parser<AbstractUser> parser = new AbstractParserFactory<>((AbstractUser)new User())
            //        .makeParser();
            //return (User)parser.parse(new JSONObject(contentStr));
            return null;
        } catch (IOException e) {
            throw new ClientException(e);
        }
    }

    @Override
    public User fetchById(int id) throws ClientException {
        throw new ClientException("Method not implemented.");
    }

    @Override
    public List<User> fetchAll() throws ClientException {
        throw new ClientException("Method not implemented.");
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
