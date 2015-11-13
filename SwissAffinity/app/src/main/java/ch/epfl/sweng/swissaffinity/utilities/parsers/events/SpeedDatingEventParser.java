package ch.epfl.sweng.swissaffinity.utilities.parsers.events;

import org.json.JSONException;
import org.json.JSONObject;

import ch.epfl.sweng.swissaffinity.events.AbstractEvent;
import ch.epfl.sweng.swissaffinity.events.Establishment;
import ch.epfl.sweng.swissaffinity.events.SpeedDatingEvent;
import ch.epfl.sweng.swissaffinity.utilities.parsers.EstablishmentParser;
import ch.epfl.sweng.swissaffinity.utilities.parsers.Parsable;
import ch.epfl.sweng.swissaffinity.utilities.parsers.ParserException;

import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.MAX_AGE;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.MEN_REGISTERED;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.MEN_SEATS;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.MIN_AGE;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.WOMEN_REGISTERED;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.WOMEN_SEATS;

/**
 * Parser for getting a speed-dating event instance.
 */
public class SpeedDatingEventParser implements Parsable<SpeedDatingEvent> {

    @Override
    public SpeedDatingEvent parseFromJSON(JSONObject jsonObject) throws ParserException {
        SpeedDatingEvent.Builder builder = new SpeedDatingEvent.Builder();
        try {
            AbstractEvent.Builder abstractEventBuilder =
                    new AbstractEventParser().parseFromJSON(jsonObject);

            int menSeats = jsonObject.getInt(MEN_SEATS.get());
            int womenSeats = jsonObject.getInt(WOMEN_SEATS.get());
            int menRegistered = jsonObject.getInt(MEN_REGISTERED.get());
            int womenRegistered = jsonObject.getInt(WOMEN_REGISTERED.get());
            int minAge = jsonObject.getInt(MIN_AGE.get());
            int maxAge = jsonObject.getInt(MAX_AGE.get());
            // TODO : parse establishement.
            Establishment establishment = (new EstablishmentParser()).
                                            parseFromJSON(jsonObject.getJSONObject("establishment"));

            return builder.setMenSeats(menSeats)
                          .setWomenSeats(womenSeats)
                          .setMenRegistered(menRegistered)
                          .setWomenRegistered(womenRegistered)
                          .setMinAge(minAge)
                          .setMaxAge(maxAge)
                          .setEstablishment(establishment)
                          .build(abstractEventBuilder);
        } catch (JSONException e) {
            throw new ParserException(e);
        }
    }
}
