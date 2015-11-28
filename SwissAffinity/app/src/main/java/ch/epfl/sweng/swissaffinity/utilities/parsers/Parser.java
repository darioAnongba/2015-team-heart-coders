package ch.epfl.sweng.swissaffinity.utilities.parsers;

/**
 * Representation of a parser.
 *
 * @param <A> generic type to parse out.
 */
public abstract class Parser<A> {

    /**
     * Allows to parse to get an instance of type A.
     *
     * @return an instance of type A.
     * @throws ParserException in case a problem occurs while parsing.
     */
    public abstract A parse(SafeJSONObject jsonObject) throws ParserException;
}
