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

    @Override
    public Address parse(SafeJSONObject jsonObject) throws ParserException {

        String street = jsonObject.get(STREET.get(), "");
        int streetNum = jsonObject.get(STREET_NUMBER.get(), -1);
        int zipCode = jsonObject.get(ZIP_CODE.get(), -1);
        String city = jsonObject.get(CITY.get(), "");
        String province = jsonObject.get(PROVINCE.get(), "");
        String country = jsonObject.get(COUNTRY.get(), "");

        return new Address(country, zipCode, city, province, streetNum, street);
    }
}
