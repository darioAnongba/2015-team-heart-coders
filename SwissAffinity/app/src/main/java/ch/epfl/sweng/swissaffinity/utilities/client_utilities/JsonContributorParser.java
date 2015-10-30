package ch.epfl.sweng.swissaffinity.utilities.client_utilities;

import org.json.JSONException;
import org.json.JSONObject;

import ch.epfl.sweng.swissaffinity.users.Contributor;
import ch.epfl.sweng.swissaffinity.utilities.DeepCopyNotSupportedException;
import ch.epfl.sweng.swissaffinity.utilities.Parseable;

/**
 * Created by Joel on 10/29/2015.
 */

public class JsonContributorParser extends Parseable<Contributor> {

    @Override
    public Contributor parseFromJSON(JSONObject jsonObject) throws JSONException {
        return null;
    }

    @Override
    public void verify() throws SAParseException {

    }

    @Override
    public Object copy() throws DeepCopyNotSupportedException {
        return null;
    }
}
