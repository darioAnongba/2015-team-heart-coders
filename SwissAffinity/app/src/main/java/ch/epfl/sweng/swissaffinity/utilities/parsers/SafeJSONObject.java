package ch.epfl.sweng.swissaffinity.utilities.parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Lionel on 13/11/15.
 */
public class SafeJSONObject extends JSONObject {

    private <A> A get(String name, A defaultValue) {
        A value = defaultValue;
        try {
            value = (A) super.get(name);
        } catch (JSONException e) {
        }
        return value;
    }

    public boolean getBoolean(String name, boolean defaultValue) {
        return get(name, defaultValue);
    }

    public int getInt(String name, int defaultValue) {
        return get(name, defaultValue);
    }

    public String getString(String name, String defaultValue) throws JSONException {
        return get(name, defaultValue);
    }

    public JSONArray getJSONArray(String name, JSONArray defaultValue) throws JSONException {
        return get(name, defaultValue);
    }

    public JSONObject getJSONObject(String name, JSONObject defaultValue) throws JSONException {
        return get(name, defaultValue);
    }
}
