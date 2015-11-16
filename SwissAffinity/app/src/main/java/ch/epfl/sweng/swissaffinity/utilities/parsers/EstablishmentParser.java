package ch.epfl.sweng.swissaffinity.utilities.parsers;

import org.json.JSONObject;

import ch.epfl.sweng.swissaffinity.events.Establishment;
import ch.epfl.sweng.swissaffinity.events.Establishment.Type;
import ch.epfl.sweng.swissaffinity.utilities.Address;
import ch.epfl.sweng.swissaffinity.utilities.Location;

import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.ADDRESS;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.DESCRIPTION;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.ID;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.LOCATION;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.LOGO_PATH;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.MAX_SEATS;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.NAME;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.PHONE_NUMBER;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.TYPE;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.URL;

/**
 * Parser for the Establishment class.
 */
public class EstablishmentParser extends Parser<Establishment> {

    public EstablishmentParser(JSONObject jsonObject) {
        super(jsonObject);
    }

    @Override
    public Establishment parse() throws ParserException {
        try {
            int id = mJsonObject.getInt(ID.get());
            String name = mJsonObject.getString(NAME.get());
            Type type = Type.getType(mJsonObject.getString(TYPE.get(), "bar"));
            Address address = new AddressParser(mJsonObject.get(
                    ADDRESS.get(),
                    new JSONObject())).parse();
            String phoneNum = mJsonObject.getString(PHONE_NUMBER.get(), "");
            String description = mJsonObject.getString(DESCRIPTION.get(), "");
            String url = mJsonObject.getString(URL.get(), "");
            int maxSeats = mJsonObject.getInt(MAX_SEATS.get(), -1);
            String logoPath = mJsonObject.getString(LOGO_PATH.get(), "");
            Location location =
                    new LocationParser(mJsonObject.getJSONObject(LOCATION.get())).parse();

            return new Establishment(
                    id,
                    name,
                    type,
                    address,
                    phoneNum,
                    description,
                    url,
                    maxSeats,
                    logoPath,
                    location);
        } catch (Exception e) {
            throw new ParserException(e);
        }
    }
}
