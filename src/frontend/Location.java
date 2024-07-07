package frontend;

public class Location {
    
    private final String strStation;
    private final String strCity;

     /**
     * Constructor to initialize a Location object with station name and city.
     *
     * @param strStation    The name of the station.
     * @param strCity       The name of the city.
     */
    public Location(String strStation, String strCity) {
        this.strStation = strStation;
        this.strCity = strCity;
    }

    // Getter for the station name
    public String getStation() {
        return strStation;
    }

    // Getter for the city name
    public String getCity() {
        return strCity;
    }

}