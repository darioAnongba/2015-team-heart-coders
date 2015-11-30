package ch.epfl.sweng.swissaffinity.utilities.parsers.events;

import org.json.JSONException;

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
import static ch.epfl.sweng.swissaffinity.utilities.parsers.SafeJSONObject.DEFAULT_STRING;

/**
 * Parser for the Establishment class.
 */
public class EstablishmentParser extends Parser<Establishment> {

    @Override
    public Establishment parse(SafeJSONObject jsonObject) throws ParserException {

        int id;
        String name;
        Type type;
        SafeJSONObject jsonAddress;
        // Throw exception only on main attributes
        try {
            id = jsonObject.getInt(ID.get());
            name = jsonObject.getString(NAME.get());
            type = Type.getType(jsonObject.getString(TYPE.get()));
            jsonAddress =
                new SafeJSONObject(jsonObject.getJSONObject(ADDRESS.get()));
        } catch (JSONException e) {
            throw new ParserException(e);
        }

        Address address = new AddressParser().parse(jsonAddress);
        String phoneNum = jsonObject.get(PHONE_NUMBER.get(), DEFAULT_STRING);
        String description = jsonObject.get(DESCRIPTION.get(), DEFAULT_STRING);
        String url = jsonObject.get(URL.get(), DEFAULT_STRING);
        int maxSeats = jsonObject.get(MAX_SEATS.get(), 0);
        String logoPath = jsonObject.get(LOGO_PATH.get(), DEFAULT_STRING);

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
    }
}
