package ch.epfl.sweng.swissaffinity.utilities.parsers;

import org.json.JSONObject;

import ch.epfl.sweng.swissaffinity.utilities.Location;

/**
 * Created by sahinfurkan on 13/11/15.
 */
public class LocationParser extends Parser<Location> {

    public LocationParser(JSONObject jsonObject) {
        super(jsonObject);
    }

    @Override
    public Location parse() throws ParserException {
        try {
            int id = mJsonObject.getInt("id");           // TODO id is not used in Location class! Do we really need it?
            String name = mJsonObject.getString("name");
            return new Location(name);
        } catch (Exception e) {
            throw new ParserException(e);
        }
    }
}
