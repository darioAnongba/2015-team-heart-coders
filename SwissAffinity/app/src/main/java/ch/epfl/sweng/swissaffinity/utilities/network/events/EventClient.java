package ch.epfl.sweng.swissaffinity.utilities.network.events;

import android.graphics.Bitmap;

import java.util.Collection;
import java.util.List;

import ch.epfl.sweng.swissaffinity.events.Event;
import ch.epfl.sweng.swissaffinity.utilities.Location;

public interface EventClient {
    List<Event> fetchAll() throws EventClientException;

    List<Event> fetchAllFor(Collection<Location> locations) throws EventClientException;

    Event fetchBy(int id) throws EventClientException;

    Bitmap imageFor(Event event) throws EventClientException;
}
