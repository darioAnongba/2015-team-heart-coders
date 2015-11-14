package ch.epfl.sweng.swissaffinity.utilities.parsers;

import org.json.JSONObject;

import ch.epfl.sweng.swissaffinity.utilities.Address;

/**
 * Created by sahinfurkan on 13/11/15.
 */


public class AddressParser extends Parser<Address> {

    public AddressParser(JSONObject jsonObject) {
        super(jsonObject);
    }

    @Override
    public Address parse() throws ParserException {
        try {
            String street = mJsonObject.getString("street");
            int streetNum = mJsonObject.getInt("street_number");
            int zipCode = mJsonObject.getInt("zip_code");
            String city = mJsonObject.getString("city");
            String province = mJsonObject.getString("province");
            String country = mJsonObject.getString("country");
            return new Address(country, zipCode, city, province, streetNum, street);
        } catch (Exception e) {
            new ParserException();
        }
        return null;
    }
}
