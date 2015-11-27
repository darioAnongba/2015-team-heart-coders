package ch.epfl.sweng.swissaffinity.utilities.parsers;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import ch.epfl.sweng.swissaffinity.users.User;
import ch.epfl.sweng.swissaffinity.utilities.Address;
import ch.epfl.sweng.swissaffinity.utilities.Location;

/**
 * Representation of a safe JSONObject class.
 */
public class SafeJSONObject extends JSONObject {
    public static final int DEFAULT_ID = Integer.MAX_VALUE;
    public static final long DEFAULT_FB_ID = -1L;
    public static final int DEFAULT_COUNT = 0;
    public static final String DEFAULT_STRING = "default_string";
    public static final Address DEFAULT_ADDRESS = new Address("",0,"","",0,"");
    public static final Location DEFAULT_LOCATION = new Location(0, "");
    public static final User.Gender DEFAULT_GENDER = User.Gender.MALE;
    public static final Date DEFAULT_DATE = new Date();
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
    public <A> A get(String name, A defaultValue){
        A value = defaultValue;
        try {
            Object o = super.get(name);
            if (o.getClass().equals(defaultValue.getClass())) {
                value = (A) o;
            }
        } catch (JSONException e){
            Log.d("SafeJSONObject", e.getMessage());
        }
        return value;
    }
}
