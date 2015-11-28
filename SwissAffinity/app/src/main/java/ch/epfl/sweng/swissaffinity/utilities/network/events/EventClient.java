package ch.epfl.sweng.swissaffinity.utilities.network.events;

import android.graphics.Bitmap;

import java.util.Collection;
import java.util.List;

import ch.epfl.sweng.swissaffinity.events.Event;
import ch.epfl.sweng.swissaffinity.users.Registration;
import ch.epfl.sweng.swissaffinity.utilities.Location;

/**
 * Representation of an event client.
 */
public interface EventClient {

    /**
     * Fetch all the events
     *
     * @return the list of all the events
     * @throws EventClientException
     */
    List<Event> fetchAll() throws EventClientException;

    /**
     * Fetch the events a user has registered.
     *
     * @param userName the user name
     * @return all the registrations for a user
     * @throws EventClientException
     */
    List<Registration> fetchRegistrationsForUser(String userName) throws EventClientException;

    /**
     * Fetch the events for a given collection of locations
     *
     * @param locations a collection of locations
     * @return all the events according the given locations
     * @throws EventClientException
     */
    List<Event> fetchAllFor(Collection<Location> locations) throws EventClientException;

    /**
     * Fetch a single event given its ID
     *
     * @param id the event ID
     * @return the desired event
     * @throws EventClientException
     */
    Event fetchBy(int id) throws EventClientException;

    /**
     * Fetch the image for the given event
     *
     * @param imagePath the path of the image of the event
     * @return the image {@link Bitmap}
     * @throws EventClientException
     */
    Bitmap imageFor(String imagePath) throws EventClientException;
}
