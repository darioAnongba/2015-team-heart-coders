package ch.epfl.sweng.swissaffinity.utilities.parsers;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Representation of a safe JSONObject class.
 */
public class SafeJSONObject extends JSONObject {

    /**
     * Constructor of the class
     *
     * @param jsonString a string JSONObject
     *
     * @throws JSONException if something goes wrong
     */
    public SafeJSONObject(String jsonString) throws JSONException {
        super(jsonString);
    }

    /**
     * Constructor of the class
     *
     * @param jsonObject a JSONObject instance
     *
     * @throws JSONException if something goes wrong
     */
    public SafeJSONObject(JSONObject jsonObject) throws JSONException {
        super(jsonObject.toString());
    }

    /**
     * Default constructor
     */
    public SafeJSONObject() {
        super();
    }

    /**
     * Getter to generically have a default fallback value.
     */
    @SuppressWarnings("unchecked")
    public <A> A get(String name, A defaultValue) {
        A value = defaultValue;
        try {
            value = (A) super.get(name);
        } catch (JSONException e) {
            Log.e("SafeJSONObject", e.getMessage());
        }
        return value;
    }
}
