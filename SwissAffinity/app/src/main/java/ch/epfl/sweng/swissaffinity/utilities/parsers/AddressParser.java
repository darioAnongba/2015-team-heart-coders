package ch.epfl.sweng.swissaffinity.utilities.parsers;

import org.json.JSONObject;

import ch.epfl.sweng.swissaffinity.utilities.Address;

/**
 * Created by sahinfurkan on 13/11/15.
 */


public class AddressParser implements Parsable<Address> {
    @Override
    public Address parseFromJSON(JSONObject jsonObject) throws ParserException {
        try {
            String street = jsonObject.getString("street");
            int streetNum = jsonObject.getInt("street_number");
            int zipCode = jsonObject.getInt("zip_code");
            String city = jsonObject.getString("city");
            String province = jsonObject.getString("province");
            String country = jsonObject.getString("country");
            return new Address(country, zipCode, city, province, streetNum, street);
        }
        catch (Exception e) {
            new ParserException();
        }
        return null;
    }
}
