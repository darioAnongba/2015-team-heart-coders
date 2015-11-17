package ch.epfl.sweng.swissaffinity.utilities.parsers.events;

import org.json.JSONException;
import org.json.JSONObject;

import ch.epfl.sweng.swissaffinity.events.Establishment;
import ch.epfl.sweng.swissaffinity.events.Establishment.Type;
import ch.epfl.sweng.swissaffinity.utilities.Address;
import ch.epfl.sweng.swissaffinity.utilities.parsers.AddressParser;
import ch.epfl.sweng.swissaffinity.utilities.parsers.Parser;
import ch.epfl.sweng.swissaffinity.utilities.parsers.ParserException;
import ch.epfl.sweng.swissaffinity.utilities.parsers.SafeJSONObject;

import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.ADDRESS;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.DESCRIPTION;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.ID;
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

    @Override
    public Establishment parse(SafeJSONObject jsonObject) throws ParserException {
        try {
            int id = jsonObject.getInt(ID.get());
            String name = jsonObject.getString(NAME.get());
            Type type = Type.getType(jsonObject.getString(TYPE.get()));
            SafeJSONObject jsonAddress =
                    new SafeJSONObject(jsonObject.getJSONObject(ADDRESS.get()));
            Address address = new AddressParser().parse(jsonAddress);
            String phoneNum = jsonObject.get(PHONE_NUMBER.get(), "");
            String description = jsonObject.get(DESCRIPTION.get(), "");
            String url = jsonObject.get(URL.get(), "");
            int maxSeats = jsonObject.get(MAX_SEATS.get(), -1);
            String logoPath = jsonObject.get(LOGO_PATH.get(), "");

            return new Establishment(
                    id,
                    name,
                    type,
                    address,
                    phoneNum,
                    description,
                    url,
                    maxSeats,
                    logoPath);
        } catch (JSONException e) {
            throw new ParserException(e);
        }
    }
}
