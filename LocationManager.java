import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LocationManager{

    private static List<Location> locations = new ArrayList<Location>();

    public static void main(String[] args){
        //read in data
        readCSV("desktops.csv");

        //perform one task after another and print results
        System.out.println(findLocationWithHighestAbsoluteAvailability().toString());
    }

    private static void readCSV(String filename){
        String line = "";
        
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] loc = line.split(",");
                Location location = new Location(loc[0],Double.parseDouble(loc[1]),Double.parseDouble(loc[2]),loc[3],loc[4],loc[5],Integer.parseInt(loc[6]),Integer.parseInt(loc[7]));
                locations.add(location);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*private void readJSON(String filename){
        try{
            String content = new String(Files.readAllBytes(Paths.get(filename)));
            
            JSONObject obj = new JSONObject(content);
            JSONArray data = obj.getJSONArray("data");
            
            for (int i = 0; i < data.length(); i++){
                String roomname = data.getJSONObject(i).getJSONObject("location").getString("roomname");
                String buildingName = data.getJSONObject(i).getJSONObject("location").getString("building_name");
                String address = data.getJSONObject(i).getJSONObject("location").getString("address");
                String postcode = data.getJSONObject(i).getJSONObject("location").getString("postcode");
                double latitude = data.getJSONObject(i).getJSONObject("location").getDouble("latitude");
                double longitude = data.getJSONObject(i).getJSONObject("location").getDouble("longitude");
                int freeSeats = data.getJSONObject(i).getInt("free_seats");
                int totalSeats = data.getJSONObject(i).getInt("total_seats");
                
                Location location = new Location(roomname, latitude, longitude, buildingName, address, postcode, freeSeats, totalSeats);
                this.locations.add(location);
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }*/

    private static Location findLocationWithHighestAbsoluteAvailability(){
        Location maximumLocation = locations.get(0);
        for(Location location : locations){
            if(location.getFreeSeats() > maximumLocation.getFreeSeats()){
                maximumLocation = location;
            }
        }
        return maximumLocation;
    }

    private static Location findLocationWithHighestRelativeAvailability(){
        Location maximumLocation = locations.get(0);
        for(Location location : locations){
            if(location.getFreeSeatsPercentage() > maximumLocation.getFreeSeatsPercentage()){
                maximumLocation = location;
            }
        }
        return maximumLocation;
    }

    /*check this */
    private static double getEuclideanDistance(double myLatitude, double myLongitude, Location location){
        double degreeLength = 110.25;
        double x = myLatitude - location.getLatitude();
        double y = (myLongitude - location.getLongitude()) * Math.cos(myLatitude);
        return degreeLength*Math.sqrt(x*x + y*y);
    }

    private static Location findClosestLocation(double myLatitude, double myLongitude){
        Location closestLocation = locations.get(0);
        double closestDistance = getEuclideanDistance(myLatitude, myLongitude, closestLocation);
        for(Location location: locations){
            double distance = getEuclideanDistance(myLatitude, myLongitude, location);
            if(distance < closestDistance){
                closestDistance = distance;
                closestLocation = location;
            }
        }
        return closestLocation;
    }

    private static Location findClosestLocationWithFreeSeats(double myLatitude, double myLongitude){
        Location closestLocation = null;
        double closestDistance = Double.POSITIVE_INFINITY;
        for(Location location:locations){
            double distance = getEuclideanDistance(myLatitude, myLongitude, location);
            if(distance < closestDistance && location.getFreeSeats() > 0){
                closestDistance = distance;
                closestLocation = location;
            }
        }
        return closestLocation;
    }

    private static String getAreaWithMostFreeSeats(){
        //create set of unique postcodes
        List<String> areas = new ArrayList<String>();
        for(Location location : locations){
            String postcode = location.getPostcode();
            if(!areas.contains(postcode)){
                areas.add(postcode);
            }
        }

        //find the one with most free seats
        String bestArea = null;
        int maximalFreeSeats = 0;
        for(String area : areas){
            //gets number of free seats in this area
            int freeSeatsInArea = 0;
            for(Location location: locations){
                if(location.getPostcode().equals(area)){
                    freeSeatsInArea += location.getFreeSeats();
                }
            }

            //update optimal area
            if(freeSeatsInArea > maximalFreeSeats){
                maximalFreeSeats = freeSeatsInArea;
                bestArea = area;
            }

        }

        return bestArea;
    }

    private static boolean multipleRoomsInBuilding(String building){
        int roomsInBuilding = 0;
        for(Location location : locations){
            if(location.getBuilding().equals(building)){
                roomsInBuilding += 1;
            }
        }
        return roomsInBuilding>1;
    }
}