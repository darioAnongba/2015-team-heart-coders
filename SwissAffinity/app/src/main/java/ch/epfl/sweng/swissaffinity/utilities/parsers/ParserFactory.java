package ch.epfl.sweng.swissaffinity.utilities.parsers;

import org.json.JSONException;
import org.json.JSONObject;

import ch.epfl.sweng.swissaffinity.events.Event;
import ch.epfl.sweng.swissaffinity.utilities.parsers.events.SpeedDatingEventParser;

import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.EVENT_TYPE;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.SPEED_DATING_TYPE;

/**
 * Factory for parsers.
 */
public class ParserFactory {

    public static Parser<? extends Event> parserFor(SafeJSONObject jsonObject) throws ParserException {
        String eventType = jsonObject.get(EVENT_TYPE.get(), "");
        if (eventType != null && eventType.equals(SPEED_DATING_TYPE.get())) {
            return new SpeedDatingEventParser();
        }
        throw new ParserException("Unknown parser type.");
    }
}
