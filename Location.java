public class Location{

    private String roomName;
    private double latitude;
    private double longitude;
    private String buildingName;
    private String address;
    private String postcode;
    private int freeSeats;
    private final int totalSeats;

    public Location(String roomName, double latitude, double longitude, String buildingName, String address, String postcode, int freeSeats, int totalSeats){
        this.roomName = roomName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.buildingName = buildingName;
        this.address = address;
        this.postcode = postcode;
        this.freeSeats = freeSeats;
        this.totalSeats = totalSeats;
    }

    public String toString(){
        String locationString = this.roomName + ": (" + this.latitude + ", "+ this.longitude + "). " + this.buildingName + ", " + this.address + ", " + this.postcode + ". "+ this.freeSeats + " from " + this.totalSeats + " seats are free.";
        return locationString;
    }

    public int getFreeSeats(){
        return this.freeSeats;
    }

    public double getFreeSeatsPercentage(){
        return ((double) this.freeSeats)/this.totalSeats;
    }

    public int getTotalSeats(){
        return this.totalSeats;
    }

    public double getLatitude(){
        return this.latitude;
    }

    public double getLongitude(){
        return this.longitude;
    }

    public String getPostcode(){
        return this.postcode;
    }

    public String getBuilding(){
        return this.buildingName;
    }

}