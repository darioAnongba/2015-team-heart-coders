package ch.epfl.sweng.swissaffinity.utilities.network;

import java.util.Collection;
import java.util.List;

import ch.epfl.sweng.swissaffinity.events.Event;
import ch.epfl.sweng.swissaffinity.events.Location;

public interface EventClient {
    List<Event> fetchAll() throws EventClientException;

    List<Event> fetchAllFor(Collection<Location> locations) throws EventClientException;

    Event fetchBy(int id) throws EventClientException;
}
