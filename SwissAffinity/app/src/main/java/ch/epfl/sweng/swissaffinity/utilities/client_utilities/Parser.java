package ch.epfl.sweng.swissaffinity.utilities.client_utilities;

import org.json.JSONObject;

import ch.epfl.sweng.swissaffinity.utilities.Parseable;

/**
 * Created by Joel on 10/29/2015.
 */
public interface Parser<E extends Parseable<E>> {
    E parse (JSONObject jsonObject) throws SAParseException;
}
