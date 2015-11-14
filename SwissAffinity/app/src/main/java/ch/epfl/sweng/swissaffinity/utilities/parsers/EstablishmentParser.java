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

public class EstablishmentParser extends Parser<Establishment> {

    public EstablishmentParser(JSONObject jsonObject) {
        super(jsonObject);
    }

    @Override
    public Establishment parse() throws ParserException {
        Establishment est = null;
        try {
            JSONObject estJson = mJsonObject.getJSONObject("establishment");
            int id = estJson.getInt("id");
            String name = estJson.getString("name");
            Type type = Type.valueOf(estJson.getString("type").toUpperCase());
            Address address = new AddressParser(estJson.getJSONObject("address")).parse();
            String phoneNum = estJson.getString("phone_number");
            String description = estJson.getString("description");
            URL url = new URL(estJson.getString("url"));
            int maxSeats = estJson.getInt("max_seats");
            String logoPath = estJson.getString("logo_path");
            Location location = new LocationParser(mJsonObject.getJSONObject("location")).parse();
            est = new Establishment(id, name, type, address, phoneNum, description, url, maxSeats,
                    logoPath, location);
        } catch (Exception e) {
            new ParserException();
        }
        return est;
    }


}
