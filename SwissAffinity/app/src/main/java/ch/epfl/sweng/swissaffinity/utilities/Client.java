package ch.epfl.sweng.swissaffinity.utilities;

import java.util.List;

/**
 * Created by Joel on 10/26/2015.
 */
public interface Client<E> {
    /**
     *
     * @param id event id. It can be obtained from server beecrative.ch/api/events.json.
     * @throws ClientException
     */
    E fetchById(int id) throws ClientException;
    E fetchByName(String name) throws ClientException;
    List<E> fetchAll() throws ClientException;
}
