package backend;

public class Location {
    
    private final String strStation;
    private final String strCity;

    public Location(String strStation, String strCity) {
        this.strStation = strStation;
        this.strCity = strCity;
    }

    public String getStation() {
        return strStation;
    }

    public String getCity() {
        return strCity;
    }

}