package ch.epfl.sweng.swissaffinity.utilities.network;

import org.json.JSONObject;

/**
 * Generic interface that represents parsables we get from the server API.
 *
 * @param <A> generic type to parse out.
 */
public interface Parsable<A> {

    /** Enumertates all the server API tags to use. */
    enum TAGS {
        ID("id"),
        NAME("name"),
        FACEBOOKID("facebook_id"),
        USERNAME("username"),
        EMAIL("email"),
        LAST_NAME("last_name"),
        FIRST_NAME("first_name"),
        MOBILE_PHONE("mobile_phone"),
        HOME_PHONE("home_phone"),
        ADDRESS("address"),
        LOCKED("locked"),
        ENABLED("enabled"),
        GENDER("gender"),
        BIRTH_DATE("birth_date"),
        PROFESSION("profession"),
        PROFILE_PICTURE("profile_picture"),
        LOCATIONS_INTEREST("areas_of_interest"),
        EVENTS_ATTENDED("events_attended"),
        LOCATION("location"),
        MAX_PEOPLE("max_people"),
        DATE_BEGIN("date_start"),
        DATE_END("date_end"),
        BASE_PRICE("base_price"),
        STATE("state"),
        DESCRIPTION("description"),
        IMAGE_PATH("image_path"),
        LAST_UPDATE("updated_at"),
        MEN_SEATS("men_seats"),
        WOMEN_SEATS("women_seats"),
        MEN_REGISTERED("num_men_registered"),
        WOMEN_REGISTERED("num_women_registered"),
        MIN_AGE("min_age"),
        MAX_AGE("max_age"),
        ESTABLISHMENT("establishment"),
        TYPE("discr"),
        SPEED_DATING_TYPE("speed_dating");

        private final String mName;

        TAGS(String name) {
            mName = name;
        }

        public String get() {
            return mName;
        }
    }

    /**
     * Allows to parse a JSON object to get an instance of type A.
     *
     * @param jsonObject
     *
     * @return an instance of type A.
     *
     * @throws ParserException in case a problem occurs while parsing.
     */
    A parseFromJSON(JSONObject jsonObject) throws ParserException;
}
