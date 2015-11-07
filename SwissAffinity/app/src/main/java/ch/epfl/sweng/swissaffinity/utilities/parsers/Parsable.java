package ch.epfl.sweng.swissaffinity.utilities.parsers;

import org.json.JSONObject;

/**
 * Generic interface that represents parsables we get from the server API.
 *
 * @param <A> generic type to parse out.
 */
public interface Parsable<A> {

    /**
     * Allows to parse a JSON object to get an instance of type A.
     *
     * @param jsonObject an instance of JSONObject to parse for A.
     *
     * @return an instance of type A.
     *
     * @throws ParserException in case a problem occurs while parsing.
     */
    A parseFromJSON(JSONObject jsonObject) throws ParserException;
}
