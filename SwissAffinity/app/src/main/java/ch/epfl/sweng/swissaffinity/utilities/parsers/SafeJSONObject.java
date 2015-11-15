package ch.epfl.sweng.swissaffinity.utilities.parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Lionel on 13/11/15.
 */
public class SafeJSONObject extends JSONObject {

    public SafeJSONObject(String jsonString) throws JSONException {
        super(jsonString);
    }

    public SafeJSONObject(JSONObject jsonObject) throws JSONException {
        super(jsonObject.toString());
    }

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

    public long getLong(String name, long defaultValue) {
        return get(name, defaultValue);
    }

    public String getString(String name, String defaultValue) {
        return get(name, defaultValue);
    }

    public JSONArray getJSONArray(String name, JSONArray defaultValue) {
        return get(name, defaultValue);
    }

    public JSONObject getJSONObject(String name, JSONObject defaultValue) {
        return get(name, defaultValue);
    }
}
