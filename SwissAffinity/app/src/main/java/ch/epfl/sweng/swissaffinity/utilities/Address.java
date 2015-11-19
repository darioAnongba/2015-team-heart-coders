package ch.epfl.sweng.swissaffinity.utilities;

import java.io.Serializable;

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
    public boolean equals(Object obj) {
        if (obj == null){
            return false;
        }
        if (getClass() != obj.getClass()){
            return false;
        }
        final Address other = (Address) obj;
        if (this.mStreetNumber != other.getStreetNumber() ||
                this.mZipCode != other.getZipCode()){
            return false;
        }
        if ((this.mStreet == null) ? (other.getStreet() != null) :
                (! this.mStreet.equals(other.getStreet()))){
            return false;
        }
        if ((this.mCity == null) ? (other.getCity() != null) :
                (! this.mCity.equals(other.getCity()))){
            return false;
        }
        if ((this.mProvince == null) ? (other.getProvince() != null) :
                (! this.mProvince.equals(other.getProvince()))){
            return false;
        }
        if ((this.mCountry == null) ? (other.getCountry() != null) :
                (! this.mCountry.equals(other.getCountry()))){
            return false;
        }
        return true;
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
