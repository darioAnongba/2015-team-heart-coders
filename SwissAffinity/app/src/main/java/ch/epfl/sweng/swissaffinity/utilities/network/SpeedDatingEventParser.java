package ch.epfl.sweng.swissaffinity.utilities.network;

import org.json.JSONException;
import org.json.JSONObject;

import ch.epfl.sweng.swissaffinity.events.Establishment;
import ch.epfl.sweng.swissaffinity.events.SpeedDatingEvent;
import ch.epfl.sweng.swissaffinity.utilities.client_utilities.EventParseException;

import static ch.epfl.sweng.swissaffinity.utilities.network.Parsable.TAGS.MAX_AGE;
import static ch.epfl.sweng.swissaffinity.utilities.network.Parsable.TAGS.MEN_REGISTERED;
import static ch.epfl.sweng.swissaffinity.utilities.network.Parsable.TAGS.MEN_SEATS;
import static ch.epfl.sweng.swissaffinity.utilities.network.Parsable.TAGS.MIN_AGE;
import static ch.epfl.sweng.swissaffinity.utilities.network.Parsable.TAGS.WOMEN_REGISTERED;
import static ch.epfl.sweng.swissaffinity.utilities.network.Parsable.TAGS.WOMEN_SEATS;

public class SpeedDatingEventParser implements Parsable<SpeedDatingEvent> {

    public SpeedDatingEvent parseFromJSON(JSONObject jsonObject) throws EventParseException {
        SpeedDatingEvent speedDatingEvent = null;
        try {
            int menSeats = jsonObject.getInt(MEN_SEATS.get());
            int womenSeats = jsonObject.getInt(WOMEN_SEATS.get());
            int menRegistered = jsonObject.getInt(MEN_REGISTERED.get());
            int womenRegistered = jsonObject.getInt(WOMEN_REGISTERED.get());
            int minAge = jsonObject.getInt(MIN_AGE.get());
            int maxAge = jsonObject.getInt(MAX_AGE.get());
            // TODO : parse establishement.
            Establishment establishment = null;

            speedDatingEvent =
                    new SpeedDatingEvent(new DefaultEventParser().parseFromJSON(jsonObject),
                                         menSeats,
                                         womenSeats,
                                         menRegistered,
                                         womenRegistered,
                                         minAge,
                                         maxAge,
                                         establishment);
        } catch (JSONException e) {
            throw new EventParseException(e);
        }
        return speedDatingEvent;
    }
}
