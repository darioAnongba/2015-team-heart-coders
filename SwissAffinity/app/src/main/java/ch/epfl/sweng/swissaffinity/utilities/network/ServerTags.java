package ch.epfl.sweng.swissaffinity.utilities.network;

/**
 * Represents the tags values used by the server API.
 */
public enum ServerTags {
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
    BIRTHDAY("birthday"),
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
    EVENT_TYPE("discr"),
    SPEED_DATING_TYPE("speed_dating");

    private final String mName;

    ServerTags(String name) {
        mName = name;
    }

    public String get() {
        return mName;
    }
}
