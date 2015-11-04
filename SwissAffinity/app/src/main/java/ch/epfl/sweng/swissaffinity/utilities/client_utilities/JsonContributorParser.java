package ch.epfl.sweng.swissaffinity.utilities.client_utilities;

import org.json.JSONException;
import org.json.JSONObject;

import ch.epfl.sweng.swissaffinity.users.Contributor;

/**
 * Created by Joel on 10/29/2015.
 */

public class JsonContributorParser implements Parser<Contributor> {
    @Override
    public Contributor parse(String s) throws UserParseException {
        try{
            JSONObject jsonObject = new JSONObject(s);
            return Contributor.parseFromJSON(jsonObject);
        } catch (JSONException e){
            throw new UserParseException(e);
        }
    }
}
