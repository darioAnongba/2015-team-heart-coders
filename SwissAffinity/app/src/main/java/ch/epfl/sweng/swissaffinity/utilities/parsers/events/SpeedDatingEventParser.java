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
        int id;
        double basePrice;
        String name;
        SafeJSONObject jsonLocation;
        SafeJSONObject jsonEstablishment;

        //Should only throw exceptions when main attributes are not present.
        try {
            id = jsonObject.getInt(ID.get());
            name = jsonObject.get(NAME.get(), SafeJSONObject.DEFAULT_STRING);
            jsonLocation = new SafeJSONObject(jsonObject.getJSONObject(LOCATION.get()));
            jsonEstablishment = new SafeJSONObject(
                    jsonObject.getJSONObject(ESTABLISHMENT.get()));
            basePrice = jsonObject.getDouble(BASE_PRICE.get());
        } catch (JSONException e) {
            throw new ParserException(e);
        }


        Location location = new LocationParser().parse(jsonLocation);
        Establishment establishment =
                new EstablishmentParser().parse(jsonEstablishment);
        int maxPeople = jsonObject.get(MAX_PEOPLE.get(), SafeJSONObject.DEFAULT_COUNT);
        String dateBegin = jsonObject.get(DATE_BEGIN.get(), SafeJSONObject.DEFAULT_STRING);
        String dateEnd = jsonObject.get(DATE_END.get(), SafeJSONObject.DEFAULT_STRING);
        String state = jsonObject.get(STATE.get(), SafeJSONObject.DEFAULT_STRING);
        String description = jsonObject.get(DESCRIPTION.get(), SafeJSONObject.DEFAULT_STRING);
        String imageUrl = jsonObject.get(IMAGE_PATH.get(), SafeJSONObject.DEFAULT_STRING);
        String lastUpdate = jsonObject.get(LAST_UPDATE.get(), SafeJSONObject.DEFAULT_STRING);
        int menSeats = jsonObject.get(MEN_SEATS.get(), SafeJSONObject.DEFAULT_COUNT);
        int womenSeats = jsonObject.get(WOMEN_SEATS.get(), SafeJSONObject.DEFAULT_COUNT);
        int menRegistered = jsonObject.get(MEN_REGISTERED.get(), SafeJSONObject.DEFAULT_COUNT);
        int womenRegistered = jsonObject.get(WOMEN_REGISTERED.get(), SafeJSONObject.DEFAULT_COUNT);
        int minAge = jsonObject.get(MIN_AGE.get(), SafeJSONObject.DEFAULT_COUNT);
        int maxAge = jsonObject.get(MAX_AGE.get(), SafeJSONObject.DEFAULT_COUNT);


        builder.setId(id)
               .setName(name)
               .setLocation(location)
               .setMaxPeople(maxPeople)
               .setDateBegin(DateParser.parseFromString(dateBegin, DateParser.SERVER_DATE_FORMAT))
               .setDateEnd(DateParser.parseFromString(dateEnd, DateParser.SERVER_DATE_FORMAT))
               .setBasePrice(basePrice)
               .setState(state)
               .setDescription(description)
               .setImagePath(imageUrl)
               .setLastUpdate(
                       DateParser.parseFromString(
                               lastUpdate,
                               DateParser.SERVER_DATE_FORMAT));
        return builder.setMenSeats(menSeats)
                      .setWomenSeats(womenSeats)
                      .setMenRegistered(menRegistered)
                      .setWomenRegistered(womenRegistered)
                      .setMinAge(minAge)
                      .setMaxAge(maxAge)
                      .setEstablishment(establishment)
                      .build();
    }
}
