package ch.epfl.sweng.swissaffinity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import ch.epfl.sweng.swissaffinity.events.Establishment;
import ch.epfl.sweng.swissaffinity.events.Event;
import ch.epfl.sweng.swissaffinity.events.SpeedDatingEvent;
import ch.epfl.sweng.swissaffinity.users.User;
import ch.epfl.sweng.swissaffinity.utilities.Address;
import ch.epfl.sweng.swissaffinity.utilities.Location;
import ch.epfl.sweng.swissaffinity.utilities.network.events.EventClientException;
import ch.epfl.sweng.swissaffinity.utilities.parsers.DateParser;
import ch.epfl.sweng.swissaffinity.utilities.parsers.Parser;
import ch.epfl.sweng.swissaffinity.utilities.parsers.ParserException;
import ch.epfl.sweng.swissaffinity.utilities.parsers.ParserFactory;
import ch.epfl.sweng.swissaffinity.utilities.parsers.SafeJSONObject;

/**
 * Class created to test events display and eventParser
 */
public class DataForTesting {

    public static String event4JSONcontent =
            "{\"id\":4,\"name\":\"First Speed Dating Event !\",\"location\":{\"id\":3,\"name\":\"Lausanne\"}," +
                    "\"max_people\":30,\"date_start\":\"2015-11-19T20:00:00+0100\"," +
                    "\"date_end\":\"2015-11-19T22:00:00+0100\",\"base_price\":49.5," +
                    "\"state\":\"pending\",\"description\":\"The super description\"," +
                    "\"image_path\":\"563c01a709848.jpg\",\"updated_at\":\"2015-11-06T02:25:59+0100\"," +
                    "\"men_seats\":15,\"women_seats\":15,\"num_men_registered\":0," +
                    "\"num_women_registered\":0,\"min_age\":25,\"max_age\":45,\"establishment\":{\"id\":1,\"name\":\"King Size Pub\"," +
                    "\"address\":{\"id\":20,\"street\":\"Lausanne Flon\"," +
                    "\"street_number\":27,\"province\":\"Vaud\",\"city\":\"Lausanne\",\"zip_code\":1004,\"country\":\"CH\"}," +
                    "\"phone_number\":\"079 000 00 00\",\"type\":\"bar\"," +
                    "\"description\":\"Irish Pub in Lausanne !\",\"url\":\"http:\\/\\/kingsizepub.com\"," +
                    "\"max_seats\":200,\"logo_path\":\"563c0126e5dea.jpg\"," +
                    "\"updated_at\":\"2015-11-06T02:23:50+0100\"},\"discr\":\"speed_dating\"}";

    public static String event5JSONcontent =
            "{\"id\":5,\"name\":\"Speed dating\",\"location\":{\"id\":3,\"name\":\"Lausanne\"}," +
                    "\"max_people\":30,\"date_start\":\"2015-11-13T20:00:00+0100\",\"date_end\":\"2015-11-13T23:00:00+0100\"," +
                    "\"base_price\":50,\"state\":\"pending\",\"description\":\"It'a good day to meet with a beer.\"," +
                    "\"image_path\":\"563cc882884f6.png\",\"updated_at\":\"2015-11-06T16:34:26+0100\",\"men_seats\":15,\"women_seats\":15," +
                    "\"num_men_registered\":0,\"num_women_registered\":0,\"min_age\":20,\"max_age\":30,\"establishment\":" +
                    "{\"id\":1,\"name\":\"King Size Pub\",\"address\":{\"id\":20,\"street\":\"Lausanne Flon\",\"street_number\":27," +
                    "\"province\":\"Vaud\",\"city\":\"Lausanne\",\"zip_code\":1004,\"country\":\"CH\"}," +
                    "\"phone_number\":\"079 000 00 00\",\"type\":\"bar\",\"description\":\"Irish Pub in Lausanne !\"," +
                    "\"url\":\"http:\\/\\/kingsizepub.com\",\"max_seats\":200,\"logo_path\":\"563c0126e5dea.jpg\"," +
                    "\"updated_at\":\"2015-11-06T02:23:50+0100\"},\"discr\":\"speed_dating\"}";

    public static String eventsLausanneJSONcontent =
            "[" + event4JSONcontent + "," + event5JSONcontent + "]";


    public static String allEventsJSONcontent =
            "[{\"id\":6,\"name\":\"Let's celebrate Oktoberfest\",\"location\":{\"id\":6,\"name\":\"Z\\u00fcrich\"},\"max_people\":40,\"date_start\":\"2016-10-15T21:00:00+0200\",\"date_end\":\"2016-10-16T00:00:00+0200\",\"base_price\":35,\"state\":\"pending\",\"description\":\"Come and join us to celebrate the Oktoberfest , and use this occasion to meet new People . The event will take place to Forum , a bar in the center of Z\\u00fcrich.\",\"image_path\":\"5647627162808.jpg\",\"updated_at\":\"2015-11-14T17:33:53+0100\",\"men_seats\":20,\"women_seats\":20,\"num_men_registered\":0,\"num_women_registered\":0,\"min_age\":21,\"max_age\":32,\"establishment\":{\"id\":2,\"name\":\"Forum\",\"address\":{\"id\":21,\"street\":\"Badenerstrasse\",\"street_number\":120,\"province\":\"Z\\u00fcrich\",\"city\":\"Z\\u00fcrich\",\"zip_code\":8004,\"country\":\"CH\"},\"phone_number\":\"+41 43 243 88 88\",\"type\":\"bar\",\"description\":\"Located at the corner of Badenerstrasse, Forum is an airy lounge bar and restaurant, ideal for kicking back and unwinding.\",\"max_seats\":250},\"discr\":\"speed_dating\"}," +
                    "{\"id\":7,\"name\":\"Xmas Dating\",\"location\":{\"id\":3,\"name\":\"Lausanne\"},\"max_people\":20,\"date_start\":\"2015-12-22T20:00:00+0100\",\"date_end\":\"2015-12-22T23:00:00+0100\",\"base_price\":30,\"state\":\"pending\",\"description\":\"Christmas is coming ! To celebrate , we will organize an event at the King Size pub in Lausanne . The goal is to bring a little present to offer the person you like the most at the end of the Date !\",\"image_path\":\"5647639f22cad.jpg\",\"updated_at\":\"2015-11-14T17:38:55+0100\",\"men_seats\":10,\"women_seats\":10,\"num_men_registered\":0,\"num_women_registered\":0,\"min_age\":26,\"max_age\":46,\"establishment\":{\"id\":1,\"name\":\"King Size Pub\",\"address\":{\"id\":20,\"street\":\"Lausanne Flon\",\"street_number\":27,\"province\":\"Vaud\",\"city\":\"Lausanne\",\"zip_code\":1004,\"country\":\"CH\"},\"phone_number\":\"079 000 00 00\",\"type\":\"bar\",\"description\":\"Irish Pub in Lausanne !\",\"url\":\"http:\\/\\/kingsizepub.com\",\"max_seats\":200,\"logo_path\":\"563c0126e5dea.jpg\",\"updated_at\":\"2015-11-06T02:23:50+0100\"},\"discr\":\"speed_dating\"}," +
                    "{\"id\":9,\"name\":\"Let's be class\",\"location\":{\"id\":6,\"name\":\"Z\\u00fcrich\"},\"max_people\":50,\"date_start\":\"2015-12-20T20:00:00+0100\",\"date_end\":\"2015-12-20T23:45:00+0100\",\"base_price\":57.5,\"state\":\"pending\",\"description\":\"Do you want to meet a guy with a good looking suit ? Or are you looking for a girl that look perfect in a dress ? This event is done for you . The dress code is primordial , so be sure to be the most elegant , and to enjoy you're drink at the Forum .\",\"image_path\":\"5647670481907.jpg\",\"updated_at\":\"2015-11-14T17:53:24+0100\",\"men_seats\":25,\"women_seats\":25,\"num_men_registered\":0,\"num_women_registered\":0,\"min_age\":21,\"max_age\":32,\"establishment\":{\"id\":2,\"name\":\"Forum\",\"address\":{\"id\":21,\"street\":\"Badenerstrasse\",\"street_number\":120,\"province\":\"Z\\u00fcrich\",\"city\":\"Z\\u00fcrich\",\"zip_code\":8004,\"country\":\"CH\"},\"phone_number\":\"+41 43 243 88 88\",\"type\":\"bar\",\"description\":\"Located at the corner of Badenerstrasse, Forum is an airy lounge bar and restaurant, ideal for kicking back and unwinding.\",\"max_seats\":250},\"discr\":\"speed_dating\"}," +
                    "{\"id\":8,\"name\":\"Let's diner Meeting\",\"location\":{\"id\":3,\"name\":\"Lausanne\"},\"max_people\":10,\"date_start\":\"2015-11-25T20:00:00+0100\",\"date_end\":\"2015-11-25T22:30:00+0100\",\"base_price\":70,\"state\":\"pending\",\"description\":\"Do you want a fantastic meal while meeting new people ? This event is for you , it takes place in one of the best restaurant of Lausanne ( from internet users point of view's) and you will have plenty of time to maybe meet the love of your life.\",\"image_path\":\"564765c648b6f.jpg\",\"updated_at\":\"2015-11-14T17:48:06+0100\",\"men_seats\":5,\"women_seats\":5,\"num_men_registered\":0,\"num_women_registered\":0,\"min_age\":28,\"max_age\":45,\"establishment\":{\"id\":3,\"name\":\"Restaurant Tandem\",\"address\":{\"id\":22,\"street\":\"Avenue des Mousquines\",\"street_number\":1,\"province\":\"Vaud\",\"city\":\"Lausanne\",\"zip_code\":1005,\"country\":\"CH\"},\"phone_number\":\"+41 21 323 32 04\",\"type\":\"restaurant\",\"description\":\"A local restaurant , really calm , and with a delicious variety of meal. The owner is really kind and all the product are fresh.\",\"max_seats\":30},\"discr\":\"speed_dating\"}," +
                    "{\"id\":4,\"name\":\"First Speed Dating Event !\",\"location\":{\"id\":3,\"name\":\"Lausanne\"},\"max_people\":30,\"date_start\":\"2015-11-19T20:00:00+0100\",\"date_end\":\"2015-11-19T22:00:00+0100\",\"base_price\":49.5,\"state\":\"pending\",\"description\":\"Mega long description of an event taking place in King size pub Lausanne. You should not miss that one and come have a beer with us. It's a great opportunity to meet new people and maybe fall in love... Try to see if the app handles this kind of long string descriptions feature.\\r\\nIf that's displayed correctly, we're not that bad doing.\",\"image_path\":\"563c01a709848.jpg\",\"updated_at\":\"2015-11-06T02:25:59+0100\",\"men_seats\":15,\"women_seats\":15,\"num_men_registered\":0,\"num_women_registered\":0,\"min_age\":25,\"max_age\":45,\"establishment\":{\"id\":1,\"name\":\"King Size Pub\",\"address\":{\"id\":20,\"street\":\"Lausanne Flon\",\"street_number\":27,\"province\":\"Vaud\",\"city\":\"Lausanne\",\"zip_code\":1004,\"country\":\"CH\"},\"phone_number\":\"079 000 00 00\",\"type\":\"bar\",\"description\":\"Irish Pub in Lausanne !\",\"url\":\"http:\\/\\/kingsizepub.com\",\"max_seats\":200,\"logo_path\":\"563c0126e5dea.jpg\",\"updated_at\":\"2015-11-06T02:23:50+0100\"},\"discr\":\"speed_dating\"}," +
                    "{\"id\":5,\"name\":\"Speed dating\",\"location\":{\"id\":3,\"name\":\"Lausanne\"},\"max_people\":30,\"date_start\":\"2015-11-13T20:00:00+0100\",\"date_end\":\"2015-11-13T23:00:00+0100\",\"base_price\":50,\"state\":\"pending\",\"description\":\"It'a good day to meet with a beer.\",\"image_path\":\"563cc882884f6.png\",\"updated_at\":\"2015-11-06T16:34:26+0100\",\"men_seats\":15,\"women_seats\":15,\"num_men_registered\":0,\"num_women_registered\":0,\"min_age\":20,\"max_age\":30,\"establishment\":{\"id\":1,\"name\":\"King Size Pub\",\"address\":{\"id\":20,\"street\":\"Lausanne Flon\",\"street_number\":27,\"province\":\"Vaud\",\"city\":\"Lausanne\",\"zip_code\":1004,\"country\":\"CH\"},\"phone_number\":\"079 000 00 00\",\"type\":\"bar\",\"description\":\"Irish Pub in Lausanne !\",\"url\":\"http:\\/\\/kingsizepub.com\",\"max_seats\":200,\"logo_path\":\"563c0126e5dea.jpg\",\"updated_at\":\"2015-11-06T02:23:50+0100\"},\"discr\":\"speed_dating\"}]";

    public static List<Location> LOCATIONS = new ArrayList<>(
            Arrays.asList(
                    new Location(2, "Genève"),
                    new Location(3, "Lausanne"),
                    new Location(4, "Fribourg"),
                    new Location(6, "Zurich"),
                    new Location(7, "Berne"),
                    new Location(8, "Bulle")
            ));

    public static URL testURL() {
        try {
            return new URL("http://urlTest.com");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Address> ADRESSES = new ArrayList<>(
            Arrays.asList(
                    new Address("Suisse", 1200, "Geneve", "GE", 1, "Place du Cirque"),
                    new Address("Suisse", 1200, "Geneve", "GE", 16, "Rue du Port Franc"),
                    new Address("Suisse", 1200, "Geneve", "GE", 14, "Grand-Places"),
                    new Address("Suisse", 1200, "Geneve", "GE", 6, "Widdergasse")
            ));

    public static List<Establishment> ESTABLISHMENTS = new ArrayList<>(
            Arrays.asList(
                    new Establishment(
                            1,
                            "Cafe Cuba",
                            Establishment.Type.BAR,
                            ADRESSES.get(0),
                            "phoneTest",
                            "testDescription",
                            "",
                            100,
                            "logoTest"),
                    new Establishment(
                            2,
                            "King Size Pub",
                            Establishment.Type.BAR,
                            ADRESSES.get(1),
                            "phoneTest",
                            "testDescription",
                            "",
                            100,
                            "logoTest"),
                    new Establishment(
                            3,
                            "Lapart",
                            Establishment.Type.BAR,
                            ADRESSES.get(2),
                            "phoneTest",
                            "testDescription",
                            "",
                            100,
                            "logoTest"),
                    new Establishment(
                            4,
                            "Widder Bar",
                            Establishment.Type.BAR,
                            ADRESSES.get(3),
                            "phoneTest",
                            "testDescription",
                            "",
                            100,
                            "logoTest")

            ));

    public static List<GregorianCalendar> DATES = new ArrayList<>(
            Arrays.asList(
                    new GregorianCalendar(2015, 10, 31, 20, 00),
                    new GregorianCalendar(2015, 10, 31, 23, 59),

                    new GregorianCalendar(2015, 11, 05, 20, 00),
                    new GregorianCalendar(2015, 11, 05, 23, 59),

                    new GregorianCalendar(2015, 12, 31, 20, 00),
                    new GregorianCalendar(2015, 12, 31, 23, 59),

                    new GregorianCalendar(2015, 11, 30, 20, 00),
                    new GregorianCalendar(2015, 11, 30, 23, 59)
            ));

    public static SpeedDatingEvent createSpeedDatingEvent() throws ParserException {

        Date date1 = DateParser.parseFromString("2015-10-31T20:00:00+0100");
        Date date2 = DateParser.parseFromString("2015-10-31T23:59:59+0100");

        SpeedDatingEvent.Builder builder = new SpeedDatingEvent.Builder();

        builder.setId(1);
        builder.setName("Halloween Speed Dating Geneva");
        builder.setLocation(new Location(0, "Genève"));
        builder.setMaxPeople(20);
        builder.setDateBegin(date1).setDateEnd(date2);
        builder.setBasePrice(49.95);
        builder.setState("confirmed");
        builder.setDescription("test description for Halloween");
        builder.setImagePath("image test Path");
        builder.setLastUpdate(new Date());
        builder.setMenSeats(10);
        builder.setWomenSeats(10);
        builder.setMenRegistered(0);
        builder.setWomenRegistered(0);
        builder.setMinAge(20);
        builder.setMaxAge(30);
        builder.setEstablishment(ESTABLISHMENTS.get(1));

        return builder.build();
    }

    public static User createUser() throws MalformedURLException, ParserException {
        Date birthday = DateParser.parseFromString("1983-11-16T16:00:00+0100");
        return new User(
                1,
                2001,
                "testUsername",
                "testEmail",
                "testLastName",
                "testFirstName",
                "testMobilePhone",
                "testHomePhone",
                new Address("Switzerland", 1000, "Lausanne", "Vaud", 1, "Rue du Test"),
                false,
                true,
                User.Gender.MALE,
                birthday,
                "testProfession",
                "http://testUrl.com",
                new ArrayList<Location>(Arrays.asList(LOCATIONS.get(1),LOCATIONS.get(0))),
                new ArrayList<Event>(Arrays.asList(createSpeedDatingEvent())));
    }

    public static String userJSONcontent =
            "{\"id\":1,\"username\":\"testUsername\",\"email\":\"testEmail\"," +
                    "\"enabled\":true,\"locked\":false,\"facebook_id\":2001," +
                    "\"last_name\":\"testLastName\",\"first_name\":\"testFirstName\"," +
                    "\"gender\":\"male\",\"birth_date\":\"1983-11-16T16:00:00+0100\"," +
                    "\"locations_of_interest\":[{\"id\":2,\"name\":\"Gen\\u00e8ve\"}," +
                    "{\"id\":3,\"name\":\"Lausanne\"}],\"profession\":\"testProfession\"," +
                    "\"mobile_phone\":\"testMobilePhone\",\"home_phone\":\"testHomePhone\"," +
                    "\"address\":{\"id\":16,\"street\":\"Rue du Test\",\"street_number\":1," +
                    "\"province\":\"Vaud\",\"city\":\"Lausanne\",\"zip_code\":1000,\"country\":\"Switzerland\"}}";

    public static JSONObject createJSONEvent() {
        try {
            return new JSONObject(event4JSONcontent);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}
