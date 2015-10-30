package ch.epfl.sweng.swissaffinity.events;

import junit.framework.TestCase;

import org.junit.Before;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;

import ch.epfl.sweng.swissaffinity.users.Contributor;
/**
 * Created by Max on 19/10/2015.
 */
public class SpeedDatingTest extends TestCase {

    private Calendar dateBeginning;
    private Calendar dateEnd;
    private Calendar createdAt;
    private SpeedDating event;
    private URL imageEventURl;
    private Location location;
    private ArrayList<Contributor> animators;
    private Contributor createdBy;
    private String phoneNumber;
    private String address;
    private String contactName;
    private Establishment establishment;

    private Establishment newEstablishment;
    private Event.State newState;
    private Calendar newDateBeginning;
    private Calendar newDateEnd;
    private Calendar newCreatedAt;

    @Before
    public void setUp() {
        try {
            event = creator();
        } catch (UnsupportedEncodingException | MalformedURLException e) {
            e.printStackTrace();
        }
    }



    private SpeedDating creator() throws MalformedURLException, UnsupportedEncodingException {
        int id=25;
        String name = "Test name";
        location = new Location("Fribourg");
        int maxPeople=25;
        this.dateBeginning = Calendar.getInstance();
        dateBeginning.set(2015, 10, 19);
        this.dateEnd = Calendar.getInstance();
        animators=new ArrayList<>();
        createdBy = new Contributor();
        dateEnd.set(2015, 10, 20);
        int basePrice=15;
        Event.State state = Event.State.CANCELLED;
        String description = "This is a test description to see if the getter is working";
        String encodedURL = "http:////"+ URLEncoder.encode("www.test.com/", "UTF-8");
        imageEventURl = new URL(encodedURL);
        this.createdAt = Calendar.getInstance();
        createdAt.set(2015, 10, 5);
        int menSits = 5;
        int womenSits = 5;
        int menSitsAvailable = 3;
        int womenSitsAvailable =2;
        int minAge = 20;
        int maxAge =35;
        String type ="name test of the establisment";
        phoneNumber = "+ 41 078 645 00 66 ";
        address = "42 avenue of test";
        contactName = "Mr.Boulet";
        establishment = new Establishment(type , address, phoneNumber, contactName);

        this.newDateEnd = Calendar.getInstance();
        newDateEnd.set(2016, 10, 20);
        this.newDateBeginning =Calendar.getInstance();
        newDateBeginning.set(2016, 10, 19);
        this.newCreatedAt = Calendar.getInstance();
        newCreatedAt.set(2016,9,20);

        String newType =("name test of the establisment");
        phoneNumber = "+ 41 078 642 350 1 ";
        address = "2 avenue of test";
        contactName = "Mr.Test";
        newEstablishment = new Establishment(newType,address,phoneNumber,contactName);

            return new SpeedDating(id,
        location,
        name,
        maxPeople,
        dateBeginning,
        dateEnd,
        basePrice,
        animators,
        state,
        description,
        imageEventURl,
        createdAt,
        createdBy,
        menSits,
        womenSits,
        menSitsAvailable,
        womenSitsAvailable,
        minAge,
        maxAge,
        establishment);
    }


    public void testGetter() throws Exception {
        assertEquals("Id", 25, event.getId());
        assertEquals("Location",location , event.getLocation());
        assertEquals("Name", "Test name", event.getName());
        assertEquals("Max People", 25,event.getMaxPeople());
        assertEquals("Base Price", 15, event.getBasePrice());
        assertEquals("Created At", createdAt, event.getCreatedAt());
        assertEquals("Date Begining", dateBeginning, event.getDateBeginning());
        assertEquals("Date End", dateEnd, event.getDateEnd());
        assertEquals("Animators", animators, event.getAnimators());
        assertEquals("State", Event.State.CANCELLED, event.isState());
        assertEquals("Description", "This is a test description to see if the getter is working",event.getDescription());
        assertEquals("ImageUrl", imageEventURl, event.getImageEventURl());
        assertEquals("CreatedBy",createdBy , event.getCreatedBy());
        assertEquals("MenSits", 5,event.getMenSits());
        assertEquals("WomentSits", 5, event.getWomenSits());
        assertEquals("Mens sits available",3, event.getMenSitsAvailable());
        assertEquals("Womens Sits available", 2, event.getWomenSitsAvailable());
        assertEquals("min Age", 20, event.getMinAge());
        assertEquals("Max Age", 35, event.getMaxAge());
        assertEquals("Establisment",establishment,event.getEstablishment());

    }

    public void testSetMenSits() throws Exception {
        int newMenSits = 10;
        event.setMenSits(newMenSits);
        assertEquals("MensSits",newMenSits,event.getMenSits());
    }

    public void testSetWomenSits() throws Exception {
        int newWomenSits = 10;
        event.setWomenSits(newWomenSits);
        assertEquals("WomensSits",newWomenSits,event.getWomenSits());
    }

    public void testSetMenSitsAvailable() throws Exception {
        int newMenAvailable = 4;
        event.setMenSitsAvailable(newMenAvailable);
        assertEquals("MensSitsAvailable",newMenAvailable,event.getMenSitsAvailable());
    }

    public void testSetWomenSitsAvailable() throws Exception {
        int newWomenAvailable = 5;
        event.setWomenSitsAvailable(newWomenAvailable);
        assertEquals("WomenSitsAvailable",newWomenAvailable,event.getWomenSitsAvailable());
    }

    public void testSetMinAge() throws Exception {
        int newMinAge = 20;
        event.setMinAge(newMinAge);
        assertEquals("Min age" , newMinAge,event.getMinAge());
    }

    public void testSetMaxAge() throws Exception {
        int newMaxAge = 45;
        event.setMaxAge(newMaxAge);
        assertEquals("Max age",newMaxAge,event.getMaxAge());
    }

    public void testSetEstablishment() throws Exception {
        event.setEstablishment(newEstablishment);
        assertEquals("Establishment",newEstablishment,event.getEstablishment());
    }


    public void testSetCreatedBy() throws Exception {
        Contributor newCreatedBy = new Contributor();
        event.setCreatedBy(newCreatedBy);
        assertEquals("CreatedBy",newCreatedBy,event.getCreatedBy());
    }


    public void testSetLocation() throws Exception {
        Location newLocation = new Location("TouristCity");
        event.setLocation(newLocation);
        assertEquals("Location",newLocation,event.getLocation());
    }

    public void testSetName() throws Exception {
        String newName = "Test du Setter";
        event.setName(newName);
        assertEquals("Name", newName, event.getName());
    }

    public void testSetMaxPeople() throws Exception {
        int newMaxPeople = 60;
        event.setMaxPeople(newMaxPeople);
        assertEquals("Max People",newMaxPeople,event.getMaxPeople());
    }

    public void testSetDateBeginning() throws Exception {
        event.setDateBeginning(newDateBeginning);
        assertEquals("Date Beginning", newDateBeginning, event.getDateBeginning());

    }

    public void testSetDateEnd() throws Exception {
        event.setDateBeginning(newDateEnd);
        assertEquals("Date Beginning",newDateEnd,event.getDateBeginning());

    }

    public void testSetBasePrice() throws Exception {
        int newBasePrice = 30;
        event.setBasePrice(newBasePrice);
        assertEquals("Base Price",newBasePrice,event.getBasePrice());
    }

    public void testSetAnimators() throws Exception {
        ArrayList<Contributor> newAnimators = new ArrayList<>();
        event.setAnimators(newAnimators);
        assertEquals("Animators",newAnimators,event.getAnimators());
    }

    public void testSetState() throws Exception {
        newState = Event.State.CONFIRMED;
        event.setState(newState);
        assertEquals("State",newState,event.isState());
    }

    public void testSetDescription() throws Exception {
        String newDescription ="Test for the setter description";
        event.setDescription(newDescription);
        assertEquals("Description",newDescription,event.getDescription());
    }

    public void testSetImageEventURl() throws Exception {
        String encodedURL = "http:////"+ URLEncoder.encode("www.testsetter.com/", "UTF-8");
        URL newImageEventURl = new URL(encodedURL);
        event.setImageEventURl(newImageEventURl);
        assertEquals("ImageEventUrl",newImageEventURl,event.getImageEventURl());
    }

    public void testSetCreatedAt() throws Exception {
        event.setCreatedAt(newCreatedAt);
        assertEquals("createdAt",newCreatedAt,event.getCreatedAt());
    }

}