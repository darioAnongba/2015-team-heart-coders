package ch.epfl.sweng.swissaffinity;

import org.json.JSONException;
import org.json.JSONObject;

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
import ch.epfl.sweng.swissaffinity.utilities.parsers.DateParser;
import ch.epfl.sweng.swissaffinity.utilities.parsers.ParserException;

/**
 * Class created to test events display and eventParser
 */
public class DataForTesting {

    public static List<Location> LOCATIONS = new ArrayList<>(
            Arrays.asList(
                    new Location(0, "Genève"),
                    new Location(1, "Lausanne"),
                    new Location(2, "Fribourg"),
                    new Location(3, "Zurich")
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

    public static SpeedDatingEvent speedDatingEventCreator() throws ParserException {

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
        builder.setDescrition("test description for Halloween");
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

    public static User userCreator() throws MalformedURLException, ParserException {
        Date birthday = DateParser.parseFromString("1983-11-16T16:00:00+0100");
        return new User(
                1,
                2001,
                "testUsername",
                "testEmail",
                "testLastName",
                "testFirstName",
                "testPhone",
                "testHomePhone",
                new Address("Switzerland", 1000, "Lausanne", "Vaud", 1, "Rue du Test"),
                false,
                true,
                User.Gender.MALE,
                birthday,
                "testProfession",
                "http://testUrl.com",
                new ArrayList<>(Arrays.asList(LOCATIONS.get(0), LOCATIONS.get(1))),
                new ArrayList<Event>(Arrays.asList(speedDatingEventCreator())));
    }

    public static JSONObject createJSONEvent() {

        String content =
                "{\"id\":4,\"name\":\"First Speed Dating Event !\",\"location\":{\"id\":3,\"name\":\"Lausanne\"}," +
                "\"max_people\":30,\"date_start\":\"2015-11-19T20:00:00+0100\"," +
                "\"date_end\":\"2015-11-19T22:00:00+0100\",\"base_price\":49.5," +
                "\"state\":\"pending\",\"description\":\"The super description\"," +
                "\"image_path\":\"563c01a709848.jpg\",\"updated_at\":\"2015-11-06T02:25:59+0100\"," +
                "\"men_seats\":15,\"women_seats\":15,\"num_men_registered\":0," +
                "\"num_women_registered\":0,\"min_age\":25,\"max_age\":45," +
                "\"establishment\":{\"id\":1,\"name\":\"King Size Pub\"," +
                "\"address\":{\"id\":20,\"street\":\"Lausanne Flon\"," +
                "\"street_number\":27,\"province\":\"Vaud\",\"city\":\"Lausanne\"," +
                "\"zip_code\":1004,\"country\":\"CH\"}," +
                "\"phone_number\":\"079 000 00 00\",\"type\":\"bar\"," +
                "\"description\":\"Irish Pub in Lausanne !\",\"url\":\"http:\\/\\/kingsizepub.com\"," +
                "\"max_seats\":200,\"logo_path\":\"563c0126e5dea.jpg\"," +
                "\"updated_at\":\"2015-11-06T02:23:50+0100\"},\"discr\":\"speed_dating\"}";

        try {
            return new JSONObject(content);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
