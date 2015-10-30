package ch.epfl.sweng.swissaffinity.utilities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Represents a Postal Address
 * Created by dario on 16.10.2015.
 *
 */
public final class Address implements DeepCopyFactory.AllowsDeepCopy {
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
     * TODO: verification over field values
     */

    private Address(String country, int zipCode, String city, String province, int streetNumber, String street) {
        if(country == null){
            throw new NullPointerException("country is null");
        }
        if(city == null){
            throw new NullPointerException("city is null");
        }
        if(province==null){
            throw new NullPointerException("province is null");
        }
        if(street == null){
            throw new NullPointerException("street is null");
        }
        this.country = country;
        this.zipCode = zipCode;
        this.city = city;
        this.province = province;
        this.streetNumber = streetNumber;
        this.street = street;
    }

    public static Address makeAddress(JSONObject jsonAddress) throws JSONException, IllegalArgumentException{
        if (!(jsonAddress.get("country") instanceof String)){
            throw new JSONException("country must be a String");
        }
        if (!(jsonAddress.get("city") instanceof String)){
            throw new JSONException("city must be a String");
        }
        if(!(jsonAddress.get("province") instanceof String)){
            throw new JSONException("province must be a String");
        }
        if(!(jsonAddress.get("street") instanceof String)){
            throw new JSONException("street must be a String");
        }
        Address formedAddress = null ;
        try {
            formedAddress = new Address(jsonAddress.getString("country"),
                    jsonAddress.getInt("zip_code"),
                    jsonAddress.getString("city"),
                    jsonAddress.getString("province"),
                    jsonAddress.getInt("street_number"),
                    jsonAddress.getString("street"));
        } catch (NullPointerException e){
            throw new JSONException(e.getMessage());
        }
        return formedAddress;
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

    @Override
    public Object copy() throws DeepCopyNotSupportedException {
        return null;
    }
}
