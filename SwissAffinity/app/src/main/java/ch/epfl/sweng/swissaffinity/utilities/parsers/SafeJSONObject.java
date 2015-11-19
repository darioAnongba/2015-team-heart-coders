package ch.epfl.sweng.swissaffinity.utilities.parsers;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Representation of a safe JSONObject class.
 */
public class SafeJSONObject extends JSONObject {

    /**
     * Default constructor
     */
    public SafeJSONObject() {
        super();
    }

    /**
     * Constructor of the class
     *
     * @param json a string JSONObject
     *
     * @throws JSONException if something goes wrong
     */
    public SafeJSONObject(String json) throws JSONException {
        super(json);
    }

    /**
     * Constructor of the class
     *
     * @param jsonObject a JSONObject instance
     *
     * @throws JSONException if something goes wrong
     */
    public SafeJSONObject(JSONObject jsonObject) throws JSONException {
        this(jsonObject.toString());
    }
    //TODO: good initiative to refactor checking, yet invocations are too ad-hoc.
    //
    /**
     * Getter to generically have a default fallback value.
     */
    @SuppressWarnings("unchecked")
    public <A> A get(String name, A defaultValue) {
        A value = defaultValue;
        try {
            Object o = super.get(name);
            if (o.getClass().equals(defaultValue.getClass())) {
                value = (A) o;
            }
        } catch (JSONException e) {
            Log.d("SafeJSONObject", e.getMessage());
        }
        return value;
    }
}
