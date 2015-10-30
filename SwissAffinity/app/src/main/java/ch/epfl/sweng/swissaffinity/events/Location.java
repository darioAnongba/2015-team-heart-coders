package ch.epfl.sweng.swissaffinity.events;

/**
 * The class that represent a city
 */
public class Location {

    private String city;

    /**
     * The constructor for the class Location
     * @param city the name of the city
     * @throws IllegalArgumentException if the name is empty
     */
    public Location(String city) throws IllegalArgumentException{
        if(city.isEmpty()){
            throw new IllegalArgumentException("The city name is invalid" + city);
        }
        this.city=city;
    }

    /**
     * The getter of the city name
     * @return the city name
     */
    public String getCity(){
        return city;
    }

    /**
     * The setter for the city name
     * @param city  the new name of the city
     * @throws IllegalArgumentException if the new name is empty
     */
    public void setCity(String city)throws IllegalArgumentException{
        if(city.isEmpty()) {
            throw new IllegalArgumentException("The city name is invalid" + city);
        }
        this.city = city;
    }
}
