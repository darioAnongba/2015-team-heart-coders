package ch.epfl.sweng.swissaffinity.utilities.parsers;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Representation of a parser.
 *
 * @param <A> generic type to parse out.
 */
public abstract class Parser<A> {

    protected final SafeJSONObject mJsonObject;

    protected Parser(JSONObject jsonObject) {
        SafeJSONObject safeJSONObject = null;
        try {
            safeJSONObject = new SafeJSONObject(jsonObject.toString());
        } catch (JSONException e) {
            Log.e("Parser", e.getMessage());
        }
        mJsonObject = safeJSONObject;
    }

    /**
     * Allows to parse to get an instance of type A.
     *
     * @return an instance of type A.
     * @throws ParserException in case a problem occurs while parsing.
     */
    public abstract A parse() throws ParserException;
}
