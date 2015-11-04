package ch.epfl.sweng.swissaffinity.utilities;

import org.json.JSONException;
import org.json.JSONObject;

import ch.epfl.sweng.swissaffinity.utilities.client_utilities.SAParseException;

/**
 * Created by Joel on 10/30/2015.
 */
public abstract class Parseable<E> implements DeepCopyFactory.AllowsDeepCopy {
    //TODO add atts to check
    public abstract E parseFromJSON(JSONObject jsonObject) throws JSONException;
    public abstract void verify() throws SAParseException;

}
