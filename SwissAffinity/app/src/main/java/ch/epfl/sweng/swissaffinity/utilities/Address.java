package ch.epfl.sweng.swissaffinity.utilities;

import java.io.Serializable;

/**
 * Represents a Postal Address
 * Created by dario on 16.10.2015.
 */
public class Address implements Serializable {
    private String street;
    private int streetNumber;
    private int zipCode;
    private String city;
    private String province;
    private String country;

    /**
     * Create a new postal address
     *
     * @param country the country code  (fr, ch, en, de, ...)
     * @param zipCode the zipCode
     * @param city the city name
     * @param province the area or province
     * @param streetNumber the street number
     * @param street the street name
     */
    public Address(String country, int zipCode, String city, String province, int streetNumber, String street) {
        this.country = country;
        this.zipCode = zipCode;
        this.city = city;
        this.province = province;
        this.streetNumber = streetNumber;
        this.street = street;
    }

    /**
     * Get the street name
     *
     * @return street
     */
    public String getStreet() {
        return street;
    }

    /**
     * Set the street name
     *
     * @param street the street name
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * get the zip code
     *
     * @return zip code
     */
    public int getZipCode() {
        return zipCode;
    }

    /**
     * set zipCode
     *
     * @param zipCode the zip code
     */
    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * Get the street number
     *
     * @return streetNumber
     */
    public int getStreetNumber() {
        return streetNumber;
    }

    /**
     * Set the street name
     *
     * @param streetNumber the street number
     */
    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    /**
     * Get the city name
     *
     * @return city
     */
    public String getCity() {
        return city;
    }

    /**
     * Set city name
     *
     * @param city the city name
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * get province
     *
     * @return province
     */
    public String getProvince() {
        return province;
    }

    /**
     * Set province
     *
     * @param province the area or province
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * Get country code
     *
     * @return country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Set country code
     * @param country The country code (fr, de, ch, etc...)
     */
    public void setCountry(String country) {
        this.country = country;
    }
}
