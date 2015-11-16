package ch.epfl.sweng.swissaffinity.utilities.parsers.events;

import org.json.JSONException;
import org.json.JSONObject;

import ch.epfl.sweng.swissaffinity.events.Establishment;
import ch.epfl.sweng.swissaffinity.events.Establishment.Type;
import ch.epfl.sweng.swissaffinity.utilities.Address;
import ch.epfl.sweng.swissaffinity.utilities.parsers.AddressParser;
import ch.epfl.sweng.swissaffinity.utilities.parsers.Parser;
import ch.epfl.sweng.swissaffinity.utilities.parsers.ParserException;

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

    public EstablishmentParser(JSONObject jsonObject) {
        super(jsonObject);
    }

    @Override
    public Establishment parse() throws ParserException {
        try {
            int id = mJsonObject.getInt(ID.get());
            String name = mJsonObject.getString(NAME.get());
            Type type = Type.getType(mJsonObject.getString(TYPE.get()));
            JSONObject jsonAddress = mJsonObject.get(ADDRESS.get(), new JSONObject());
            Address address = new AddressParser(jsonAddress).parse();
            String phoneNum = mJsonObject.get(PHONE_NUMBER.get(), "");
            String description = mJsonObject.get(DESCRIPTION.get(), "");
            String url = mJsonObject.get(URL.get(), "");
            int maxSeats = mJsonObject.get(MAX_SEATS.get(), -1);
            String logoPath = mJsonObject.get(LOGO_PATH.get(), "");

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
