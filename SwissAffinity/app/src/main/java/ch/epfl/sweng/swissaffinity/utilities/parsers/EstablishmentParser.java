package ch.epfl.sweng.swissaffinity.utilities.parsers;

import org.json.JSONObject;

import java.net.URL;

import ch.epfl.sweng.swissaffinity.events.Establishment;
import ch.epfl.sweng.swissaffinity.events.Establishment.Type;
import ch.epfl.sweng.swissaffinity.utilities.Address;
import ch.epfl.sweng.swissaffinity.utilities.Location;

/**
 * Created by sahinfurkan on 13/11/15.
 */

public class EstablishmentParser implements Parsable<Establishment> {
    @Override
    public Establishment parseFromJSON(JSONObject jsonObject) throws ParserException {
        try {
            int id = jsonObject.getInt("id");
            String name = jsonObject.getString("name");
            Type type = Type.valueOf(jsonObject.getString("type"));
            Address address = (new AddressParser()).parseFromJSON(jsonObject.getJSONObject("address"));
            String phoneNum = jsonObject.getString("phone_number");
            String description = jsonObject.getString("description");
            URL url = new URL(jsonObject.getString("url"));
            int maxSeats = jsonObject.getInt("max_seats");
            String logoPath = jsonObject.getString("logo_path");
            Location location = new LocationParser().parseFromJSON(jsonObject.getJSONObject("location"));
            return new Establishment(id, name, type, address, phoneNum, description, url, maxSeats,
                                     logoPath, location);
        }
        catch (Exception e){
            new ParserException();
        }
        return null;
    }


}
