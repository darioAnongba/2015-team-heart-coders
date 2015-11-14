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
        Establishment est = null;
        try {
            JSONObject estJson = jsonObject.getJSONObject("establishment");
            int id = estJson.getInt("id");
            String name = estJson.getString("name");
            Type type = Type.valueOf(estJson.getString("type").toUpperCase());
            Address address = (new AddressParser()).parseFromJSON(estJson.getJSONObject("address"));
            String phoneNum = estJson.getString("phone_number");
            String description = estJson.getString("description");
            URL url = new URL(estJson.getString("url"));
            int maxSeats = estJson.getInt("max_seats");
            String logoPath = estJson.getString("logo_path");
            Location location = new LocationParser().parseFromJSON(jsonObject.getJSONObject("location"));
            est =  new Establishment(id, name, type, address, phoneNum, description, url, maxSeats,
                                     logoPath, location);
        }
        catch (Exception e){
            new ParserException();
        }
        return est;
    }


}
