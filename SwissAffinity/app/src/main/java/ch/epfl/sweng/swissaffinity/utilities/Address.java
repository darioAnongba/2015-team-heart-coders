package ch.epfl.sweng.swissaffinity.utilities;

import java.io.Serializable;
import java.util.Objects;

/**
 * Representation of a postal address.
 */
public class Address implements Serializable {

    private final String mStreet;
    private final int mStreetNumber;
    private final int mZipCode;
    private final String mCity;
    private final String mProvince;
    private final String mCountry;

    /**
     * Create a new postal address
     *
     * @param country      the country code  (fr, ch, en, de, ...)
     * @param zipCode      the zipCode
     * @param city         the city name
     * @param province     the area or province
     * @param streetNumber the street number
     * @param street       the street name
     */
    public Address(
        String country,
        int zipCode,
        String city,
        String province,
        int streetNumber,
        String street)
    {
        if (country == null || zipCode < 0 || city == null || province == null ||
            streetNumber < 0 || street == null)
        {
            throw new IllegalArgumentException();
        }
        mCountry = country;
        mZipCode = zipCode;
        mCity = city;
        mProvince = province;
        mStreetNumber = streetNumber;
        mStreet = street;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Address address = (Address) o;
        return Objects.equals(mStreetNumber, address.mStreetNumber) &&
               Objects.equals(mZipCode, address.mZipCode) &&
               Objects.equals(mStreet, address.mStreet) &&
               Objects.equals(mCity, address.mCity) &&
               Objects.equals(mProvince, address.mProvince) &&
               Objects.equals(mCountry, address.mCountry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mStreet, mStreetNumber, mZipCode, mCity, mProvince, mCountry);
    }

    @Override
    public String toString() {
        return String.format("%s %d\n%d %s",mStreet,mStreetNumber,mZipCode,mCity);
    }

    /**
     * Get the street name
     *
     * @return street
     */
    public String getStreet() {
        return mStreet;
    }

    /**
     * get the zip code
     *
     * @return zip code
     */
    public int getZipCode() {
        return mZipCode;
    }

    /**
     * Get the street number
     *
     * @return streetNumber
     */
    public int getStreetNumber() {
        return mStreetNumber;
    }

    /**
     * Get the city name
     *
     * @return city
     */
    public String getCity() {
        return mCity;
    }

    /**
     * get province
     *
     * @return province
     */
    public String getProvince() {
        return mProvince;
    }

    /**
     * Get country code
     *
     * @return country
     */
    public String getCountry() {
        return mCountry;
    }
}
