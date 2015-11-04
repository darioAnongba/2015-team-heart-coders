package ch.epfl.sweng.swissaffinity.events;

import junit.framework.TestCase;

import org.junit.Before;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

/**
 * Created by Max on 26/10/2015.
 */
public class EstablishmentTest extends TestCase {

    private Establishment establishment;


    @Before
    public void setUp() {
        establishment = creatorEstablishment();
    }

    private Establishment creatorEstablishment() {

        String type = "Bar where you drink";
        String address = "Test adresse , 58";
        String phoneNumber ="+1234567890";
        String contactName = "Spartacus";

        return new Establishment(type,address,phoneNumber,contactName);
    }


    public void testGetter() throws Exception {
        assertEquals("type","Bar where you drink",establishment.getType());
        assertEquals("address","Test adresse , 58",establishment.getAddress());
        assertEquals("phoneNumber","+1234567890",establishment.getPhoneNumber());
        assertEquals("conctact name","Spartacus",establishment.getContactName());
    }


    public void testSetType() throws Exception {
        String newType ="Bar where you watch some foot";
        establishment.setType(newType);
        assertEquals("type",newType,establishment.getType());

    }


    public void testSetAddress() throws Exception {
        String newAddress = "Les gosses du québec";
        establishment.setAddress(newAddress);
        assertEquals("address", newAddress, establishment.getAddress());
    }


    public void testSetPhoneNumber() throws Exception {
        String newPhoneNumber = "+9876543210";
        establishment.setPhoneNumber(newPhoneNumber);
        assertEquals("phone number",newPhoneNumber,establishment.getPhoneNumber());
    }

    public void testSetContactName() throws Exception {
        String newContactName = "One-you-shall-not-call";
        establishment.setContactName(newContactName);
        assertEquals("Contact name",newContactName,establishment.getContactName());
    }
}