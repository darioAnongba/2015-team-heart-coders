package ch.epfl.sweng.swissaffinity.events;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.swissaffinity.utilities.Address;
import ch.epfl.sweng.swissaffinity.utilities.DeepCopyFactory;
import ch.epfl.sweng.swissaffinity.utilities.DeepCopyNotSupportedException;

/**
 * Created by Joel on 10/27/2015.
 * This class represents people in charge of Establishments(s) where
 * SwissAffinity organizes events. e.g. business collaborators.
 */
public final class Collaborator implements DeepCopyFactory.AllowsDeepCopy {

    public enum Status {AVAILABLE, BUSSY};
    private final String name;
    private final Address address;
    private final Collaborator.Status status;
    private final String businessPhone;
    private final String personalPhone;
    private final List<Event> currentEvents = new ArrayList<>();
    private final List<Establishment> establishments = new ArrayList<>();

    /**
     *
     * @param name
     * @param address
     * @param status for automatic notifications to collaborators
     * @param businessPhone
     * @param personalPhone
     */
    public Collaborator(String name, Address address, Status status, String businessPhone,
                        String personalPhone) {
        if(name==null || address == null || status == null || businessPhone==null
                || personalPhone == null ){
            throw new IllegalArgumentException("Invalid format for Collaborator");
        }

        this.name = name;
        this.address = address;
        this.status = status;
        this.businessPhone = businessPhone;
        this.personalPhone = personalPhone;
    }
    @Override
    public Object copy() throws DeepCopyNotSupportedException {
        return null;
    }


    public String getName() {
        return name;
    }

    public Status getStatus() {
        return status;
    }

    public String getBusinessPhone() {
        return businessPhone;
    }

    public String getPersonalPhone() {
        return personalPhone;
    }

    public List<Event> getCurrentEvents() {
        return currentEvents;
    }
    public void addEvent(Event event){
        //TODO
    }

    public List<Establishment> getEstablishments() {
        return establishments;
    }
    public void addEstablishment(Establishment establishment){
        //TODO
    }


    public Address getAddress() {

        return address;
    }
}
