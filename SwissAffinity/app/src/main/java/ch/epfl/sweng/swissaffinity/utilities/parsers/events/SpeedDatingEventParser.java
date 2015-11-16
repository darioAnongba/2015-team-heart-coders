package ch.epfl.sweng.swissaffinity.utilities.parsers.events;

import org.json.JSONException;
import org.json.JSONObject;

import ch.epfl.sweng.swissaffinity.events.Establishment;
import ch.epfl.sweng.swissaffinity.events.SpeedDatingEvent;
import ch.epfl.sweng.swissaffinity.utilities.Location;
import ch.epfl.sweng.swissaffinity.utilities.parsers.DateParser;
import ch.epfl.sweng.swissaffinity.utilities.parsers.EstablishmentParser;
import ch.epfl.sweng.swissaffinity.utilities.parsers.LocationParser;
import ch.epfl.sweng.swissaffinity.utilities.parsers.Parser;
import ch.epfl.sweng.swissaffinity.utilities.parsers.ParserException;
import ch.epfl.sweng.swissaffinity.utilities.parsers.SafeJSONObject;

import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.BASE_PRICE;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.DATE_BEGIN;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.DATE_END;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.DESCRIPTION;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.ID;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.IMAGE_PATH;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.LAST_UPDATE;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.LOCATION;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.MAX_AGE;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.MAX_PEOPLE;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.MEN_REGISTERED;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.MEN_SEATS;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.MIN_AGE;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.NAME;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.STATE;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.WOMEN_REGISTERED;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.WOMEN_SEATS;

/**
 * Parser for the SpeedDatingEvent class.
 */
public class SpeedDatingEventParser extends Parser<SpeedDatingEvent> {

    public SpeedDatingEventParser(JSONObject jsonObject) {
        super(jsonObject);
    }

    @Override
    public SpeedDatingEvent parse() throws ParserException {
        SpeedDatingEvent.Builder builder = new SpeedDatingEvent.Builder();
        try {
            int id = mJsonObject.getInt(ID.get());
            String name = mJsonObject.getString(NAME.get());
            Location location =
                    new LocationParser(new SafeJSONObject(mJsonObject.getJSONObject(LOCATION.get())))
                            .parse();
            int maxPeople = mJsonObject.getInt(MAX_PEOPLE.get());
            String dateBegin = mJsonObject.getString(DATE_BEGIN.get());
            String dateEnd = mJsonObject.getString(DATE_END.get());
            double basePrice = mJsonObject.getDouble(BASE_PRICE.get());
            String state = mJsonObject.getString(STATE.get());
            String description = mJsonObject.getString(DESCRIPTION.get());
            String imageUrl = mJsonObject.getString(IMAGE_PATH.get());
            String lastUpdate = mJsonObject.getString(LAST_UPDATE.get());
            int menSeats = mJsonObject.getInt(MEN_SEATS.get());
            int womenSeats = mJsonObject.getInt(WOMEN_SEATS.get());
            int menRegistered = mJsonObject.getInt(MEN_REGISTERED.get());
            int womenRegistered = mJsonObject.getInt(WOMEN_REGISTERED.get());
            int minAge = mJsonObject.getInt(MIN_AGE.get());
            int maxAge = mJsonObject.getInt(MAX_AGE.get());

            Establishment establishment = new EstablishmentParser(mJsonObject).parse();

            builder.setId(id)
                   .setName(name)
                   .setLocation(location)
                   .setMaxPeople(maxPeople)
                   .setDateBegin(DateParser.parseFromString(dateBegin))
                   .setDateEnd(DateParser.parseFromString(dateEnd))
                   .setBasePrice(basePrice)
                   .setState(state)
                   .setDescrition(description)
                   .setImagePath(imageUrl)
                   .setLastUpdate(DateParser.parseFromString(lastUpdate));
            return builder.setMenSeats(menSeats)
                          .setWomenSeats(womenSeats)
                          .setMenRegistered(menRegistered)
                          .setWomenRegistered(womenRegistered)
                          .setMinAge(minAge)
                          .setMaxAge(maxAge)
                          .setEstablishment(establishment)
                          .build();
        } catch (JSONException e) {
            throw new ParserException(e);
        }
    }
}
