package ch.epfl.sweng.swissaffinity.utilities.parsers;

import org.json.JSONObject;

import ch.epfl.sweng.swissaffinity.utilities.Address;

import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.CITY;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.COUNTRY;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.PROVINCE;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.STREET;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.STREET_NUMBER;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.ZIP_CODE;

/**
 * Parser for the Address class.
 */
public class AddressParser extends Parser<Address> {

    public AddressParser(JSONObject jsonObject) {
        super(jsonObject);
    }

    @Override
    public Address parse() throws ParserException {

        String street = mJsonObject.get(STREET.get(), "");
        int streetNum = mJsonObject.get(STREET_NUMBER.get(), -1);
        int zipCode = mJsonObject.get(ZIP_CODE.get(), -1);
        String city = mJsonObject.get(CITY.get(), "");
        String province = mJsonObject.get(PROVINCE.get(), "");
        String country = mJsonObject.get(COUNTRY.get(), "");

        return new Address(country, zipCode, city, province, streetNum, street);
    }
}
