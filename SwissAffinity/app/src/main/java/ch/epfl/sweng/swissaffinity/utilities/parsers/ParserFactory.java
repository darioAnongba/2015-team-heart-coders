package ch.epfl.sweng.swissaffinity.utilities.parsers;

import org.json.JSONException;
import org.json.JSONObject;

import ch.epfl.sweng.swissaffinity.utilities.parsers.events.SpeedDatingEventParser;

import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.EVENT_TYPE;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.SPEED_DATING_TYPE;

public class ParserFactory {

    public static Parsable parserFor(JSONObject jsonObject) throws ParserException {
        try {
            String eventType = jsonObject.getString(EVENT_TYPE.get());
            if (eventType != null && eventType.equals(SPEED_DATING_TYPE.get())) {
                return new SpeedDatingEventParser();
            }
        } catch (JSONException e) {
            throw new ParserException(e);
        }
        throw new ParserException("Unknown parsing type.");
    }
}
