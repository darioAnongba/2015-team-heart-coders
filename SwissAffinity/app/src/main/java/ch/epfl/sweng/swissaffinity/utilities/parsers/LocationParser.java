package ch.epfl.sweng.swissaffinity.utilities.parsers;

import ch.epfl.sweng.swissaffinity.utilities.Location;

import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.ID;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.NAME;

/**
 * Parser for the Location class.
 */
public class LocationParser extends Parser<Location> {

    @Override
    public Location parse(SafeJSONObject jsonObject) throws ParserException {
        int id = jsonObject.get(ID.get(), SafeJSONObject.DEFAULT_INT);
        String name = jsonObject.get(NAME.get(), SafeJSONObject.DEFAULT_STRING);
        return new Location(id, name);
    }
}
