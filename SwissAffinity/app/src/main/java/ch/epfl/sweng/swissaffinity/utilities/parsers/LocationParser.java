package ch.epfl.sweng.swissaffinity.utilities.parsers;

import org.json.JSONObject;

import ch.epfl.sweng.swissaffinity.utilities.Location;

/**
 * Created by sahinfurkan on 13/11/15.
 */
public class LocationParser implements Parsable<Location> {
    @Override
    public Location parseFromJSON(JSONObject jsonObject) throws ParserException {
        try{
            int id = jsonObject.getInt("id");           // TODO id is not used in Location class! Do we really need it?
            String name = jsonObject.getString("name");
            return new Location(name);
        }catch (Exception e){
            new ParserException();
        }
        return null;
    }
}
