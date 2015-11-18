package ch.epfl.sweng.swissaffinity.utilities.parsers;

import org.json.JSONException;
import org.json.JSONObject;

import ch.epfl.sweng.swissaffinity.utilities.Location;

import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.ID;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.NAME;

/**
 * Parser for the Location class.
 */
public class LocationParser extends Parser<Location> {

    @Override
    public Location parse(SafeJSONObject jsonObject) throws ParserException {
        try {
            int id = jsonObject.getInt(ID.get());
            String name = jsonObject.getString(NAME.get());
            return new Location(id, name);
        } catch (JSONException e) {
            throw new ParserException(e);
        }
    }
}
