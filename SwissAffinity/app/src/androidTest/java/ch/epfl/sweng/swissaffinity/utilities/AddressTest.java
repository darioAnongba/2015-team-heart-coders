package ch.epfl.sweng.swissaffinity.utilities;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Objects;

import ch.epfl.sweng.swissaffinity.DataForTesting;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by sahinfurkan on 04/12/15.
 */
public class AddressTest {
    List<Address> addresses;
    List<Address> addresses4comparison;

    @Before
    public void setUp(){
        addresses = DataForTesting.ADRESSES;
        addresses4comparison = DataForTesting.ADRESSES;
    }
    @Test(expected = IllegalArgumentException.class)
    public void constructorCountryTest(){
        Address address = new Address(null, 0, "test", "test", 1052, "test");
    }
    @Test(expected = IllegalArgumentException.class)
    public void constructorZipCodeTest(){
        Address address = new Address("test", -1, "test", "test", 1052, "test");
    }
    @Test(expected = IllegalArgumentException.class)
    public void constructorCityTest(){
        Address address = new Address("test", 0, null, "test", 1052, "test");
    }
    @Test(expected = IllegalArgumentException.class)
    public void constructorProvinceTest(){
        Address address = new Address("test", 0, "test", null, 1052, "test");
    }
    @Test(expected = IllegalArgumentException.class)
    public void constructorStreetNumTest(){
        Address address = new Address("test", 0, "test", "test", -1, "test");
    }
    @Test(expected = IllegalArgumentException.class)
    public void constructorStreetTest(){
        Address address = new Address("test", 0, "test", "test", 1052, null);
    }

    @Test
    public void equalsEqualTest(){
        for (int i = 0; i < addresses.size(); i++){
            assertEquals(addresses.get(i).equals(addresses4comparison.get(i)), true);
        }
    }
    @Test
    public void equalsNotEqualTest(){
        for (int i = 0; i < addresses.size(); i++){
            assertEquals(addresses.get(i).equals(addresses4comparison.get(addresses.size() - i - 1)), false);
        }
    }

    @Test
    public void equalsNullTest(){
        assertEquals(addresses.get(0).equals(null), false);
    }

    @Test
    public void equalsDifferentObject(){
        String test = "";
        assertEquals(addresses.get(0).equals(test), false);
    }

    @Test
    public void getHashCodeTest(){
        for (Address address : addresses)
            assertNotEquals(address.hashCode(), 0);
    }

    @Test
    public void getStreetTest(){
        for (Address address : addresses)
            assertNotEquals(address.getCity(), "");
    }

    @Test
    public void getZipCodeTest(){
        for (Address address : addresses)
            assertEquals(address.getZipCode(), 1200);
    }

    @Test
    public void getStreetNumberTest(){
        for (Address address : addresses)
            assertNotEquals(address.getStreetNumber(), 0);
    }

    @Test
    public void getCityTest(){
        for (Address address : addresses)
            assertNotEquals(address.getCity(), "");
    }

    @Test
    public void getCountryTest(){
        for (Address address : addresses)
            assertNotEquals(address.getCountry(), "");
    }
    @Test
    public void getProvinceTest(){
        for (Address address : addresses)
            assertNotEquals(address.getProvince(), "");
    }
}
