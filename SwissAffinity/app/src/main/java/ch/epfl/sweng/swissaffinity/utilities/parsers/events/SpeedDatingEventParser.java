package ch.epfl.sweng.swissaffinity.utilities.parsers.events;

import org.json.JSONException;

import ch.epfl.sweng.swissaffinity.events.Establishment;
import ch.epfl.sweng.swissaffinity.events.SpeedDatingEvent;
import ch.epfl.sweng.swissaffinity.utilities.Location;
import ch.epfl.sweng.swissaffinity.utilities.parsers.DateParser;
import ch.epfl.sweng.swissaffinity.utilities.parsers.LocationParser;
import ch.epfl.sweng.swissaffinity.utilities.parsers.Parser;
import ch.epfl.sweng.swissaffinity.utilities.parsers.ParserException;
import ch.epfl.sweng.swissaffinity.utilities.parsers.SafeJSONObject;

import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.BASE_PRICE;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.DATE_BEGIN;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.DATE_END;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.DESCRIPTION;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.ESTABLISHMENT;
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

    @Override
    public SpeedDatingEvent parse(SafeJSONObject jsonObject) throws ParserException {
        SpeedDatingEvent.Builder builder = new SpeedDatingEvent.Builder();
        try {
            int id = jsonObject.getInt(ID.get());
            String name = jsonObject.getString(NAME.get());
            SafeJSONObject jsonLocation =
                    new SafeJSONObject(jsonObject.getJSONObject(LOCATION.get()));
            Location location = new LocationParser().parse(jsonLocation);
            int maxPeople = jsonObject.getInt(MAX_PEOPLE.get());
            String dateBegin = jsonObject.getString(DATE_BEGIN.get());
            String dateEnd = jsonObject.getString(DATE_END.get());
            double basePrice = jsonObject.getDouble(BASE_PRICE.get());
            String state = jsonObject.getString(STATE.get());
            String description = jsonObject.getString(DESCRIPTION.get());
            String imageUrl = jsonObject.getString(IMAGE_PATH.get());
            String lastUpdate = jsonObject.getString(LAST_UPDATE.get());
            int menSeats = jsonObject.getInt(MEN_SEATS.get());
            int womenSeats = jsonObject.getInt(WOMEN_SEATS.get());
            int menRegistered = jsonObject.getInt(MEN_REGISTERED.get());
            int womenRegistered = jsonObject.getInt(WOMEN_REGISTERED.get());
            int minAge = jsonObject.getInt(MIN_AGE.get());
            int maxAge = jsonObject.getInt(MAX_AGE.get());

            SafeJSONObject jsonEstablishment = new SafeJSONObject(
                    jsonObject.getJSONObject(ESTABLISHMENT.get()));
            Establishment establishment =
                    new EstablishmentParser().parse(jsonEstablishment);

            builder.setId(id)
                   .setName(name)
                   .setLocation(location)
                   .setMaxPeople(maxPeople)
                   .setDateBegin(DateParser.parseFromString(dateBegin))
                   .setDateEnd(DateParser.parseFromString(dateEnd))
                   .setBasePrice(basePrice)
                   .setState(state)
                   .setDescription(description)
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
