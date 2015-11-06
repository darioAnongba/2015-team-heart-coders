package ch.epfl.sweng.swissaffinity.utilities.network;

import org.json.JSONObject;

interface Parsable<A> {

    enum TAGS {
        ID("id"),
        NAME("name"),
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

        String get() {
            return mName;
        }
    }

    A parseFromJSON(JSONObject jsonObject) throws ParserException;
}
