package ch.epfl.sweng.swissaffinity.utilities.network.events;

import org.json.JSONException;
import org.json.JSONObject;

import ch.epfl.sweng.swissaffinity.events.AbstractEvent;
import ch.epfl.sweng.swissaffinity.utilities.network.DateParser;
import ch.epfl.sweng.swissaffinity.utilities.network.Parsable;
import ch.epfl.sweng.swissaffinity.utilities.network.ParserException;

import static ch.epfl.sweng.swissaffinity.utilities.network.Parsable.TAGS.BASE_PRICE;
import static ch.epfl.sweng.swissaffinity.utilities.network.Parsable.TAGS.DATE_BEGIN;
import static ch.epfl.sweng.swissaffinity.utilities.network.Parsable.TAGS.DATE_END;
import static ch.epfl.sweng.swissaffinity.utilities.network.Parsable.TAGS.DESCRIPTION;
import static ch.epfl.sweng.swissaffinity.utilities.network.Parsable.TAGS.ID;
import static ch.epfl.sweng.swissaffinity.utilities.network.Parsable.TAGS.IMAGE_PATH;
import static ch.epfl.sweng.swissaffinity.utilities.network.Parsable.TAGS.LAST_UPDATE;
import static ch.epfl.sweng.swissaffinity.utilities.network.Parsable.TAGS.LOCATION;
import static ch.epfl.sweng.swissaffinity.utilities.network.Parsable.TAGS.MAX_PEOPLE;
import static ch.epfl.sweng.swissaffinity.utilities.network.Parsable.TAGS.NAME;
import static ch.epfl.sweng.swissaffinity.utilities.network.Parsable.TAGS.STATE;

/**
 * Represent the parsing of an AbstractEvent.Builder instance.
 */
public class AbstractEventParser implements Parsable<AbstractEvent.Builder> {

    @Override
    public AbstractEvent.Builder parseFromJSON(JSONObject jsonObject) throws ParserException {
        AbstractEvent.Builder builder = new AbstractEvent.Builder();
        try {
            // Check that Strings are correct.
            // TODO: more to check here
            if (!(jsonObject.get("name") instanceof String) ||
                !(jsonObject.get("description") instanceof String)) {
                throw new JSONException("Invalid question structure");
            }
            int id = jsonObject.getInt(ID.get());
            String name = jsonObject.getString(NAME.get());
            String location = jsonObject.getJSONObject(LOCATION.get()).getString(NAME.get());
            int maxPeople = jsonObject.getInt(MAX_PEOPLE.get());
            String dateBegin = jsonObject.getString(DATE_BEGIN.get());
            String dateEnd = jsonObject.getString(DATE_END.get());
            double basePrice = jsonObject.getDouble(BASE_PRICE.get());
            String state = jsonObject.getString(STATE.get());
            String description = jsonObject.getString(DESCRIPTION.get());
            String imageUrl = jsonObject.getString(IMAGE_PATH.get());
            String lastUpdate = jsonObject.getString(LAST_UPDATE.get());

            return builder.setId(id)
                          .setName(name)
                          .setLocation(location)
                          .setMaxPeople(maxPeople)
                          .setDateBegin(DateParser.parseFromString(dateBegin))
                          .setDateEnd(DateParser.parseFromString(dateEnd))
                          .setBasePrice(basePrice)
                          .setState(state)
                          .setDescrition(description)
                          .setImagePath(imageUrl)
                          .setmLastUpdate(DateParser.parseFromString(lastUpdate));
        } catch (JSONException e) {
            throw new ParserException(e);
        }
    }
}
