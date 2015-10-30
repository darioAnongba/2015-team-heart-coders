package ch.epfl.sweng.swissaffinity.events;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

import ch.epfl.sweng.swissaffinity.users.Contributor;
import ch.epfl.sweng.swissaffinity.utilities.Confirmation;

/**
 * Created by Max on 16/10/2015.
 */
public class SpeedDating extends Event {
    private int menSits;
    private int womenSits;
    private int menSitsAvailable;
    private int womenSitsAvailable;




    public SpeedDating(int id, Location location, String name, int maxPeople, Calendar dateBeginning, Calendar dateEnd, int basePrice, ArrayList<Contributor> animators, boolean state, String description, URL imageEventURl, Calendar createdAt, Confirmation confirmation, Contributor createdby) {
        super(id, location, name, maxPeople, dateBeginning, dateEnd, basePrice, animators, state, description, imageEventURl, createdAt, confirmation, createdby);
    }


}
