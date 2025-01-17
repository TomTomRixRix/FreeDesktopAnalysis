import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LocationManager{

    private static List<Location> locations = new ArrayList<Location>();

    public static void main(String[] args){
        //read in data
        readCSV("desktops.csv");

        /*** perform one task after another and print results ***/

        //Task 1: total absolute amount of free seats
        int freeSeats = 0;
        for(Location location: locations){
            freeSeats += location.getFreeSeats();
        } 
        System.out.println("Total number of free seats: "+freeSeats);

        //Task 2: total percentage
        int total = 0;
        for(Location location: locations){
            total += location.getTotalSeats();
        } 
        System.out.printf("Total percentage of free seats: %.2f %% \n",((double) freeSeats)/total);

        //Task 3: percentage of each room
        for(Location location: locations){
            System.out.printf("Room %s in %s has %.2f %% free seats.\n", location.getRoomName(), location.getBuilding(),location.getFreeSeatsPercentage());
        } 

        //Task 4: maximum free seats
        Location maximumFree = findLocationWithHighestAbsoluteAvailability();
        System.out.printf("Room %s in building %s has currently the most free absolute seats: %d seat(s).\n",maximumFree.getRoomName(), maximumFree.getBuilding(),maximumFree.getFreeSeats() );
        maximumFree = findLocationWithHighestRelativeAvailability();
        System.out.printf("Room %s in building %s has currently the hightest percentage free seats: %.2f %%.\n",maximumFree.getRoomName(), maximumFree.getBuilding(),maximumFree.getFreeSeatsPercentage() );

        //Task 5: distance to maximum location
        double distance = getEuclideanDistance(51.523553, -0.132521, maximumFree);
        System.out.printf("The distance to the location with maximum free seats is %.2f metres.\n",distance);

        //Task 6: closest building with free seats
        Location closest = findClosestLocationWithFreeSeats(51.523553, -0.132521);
        System.out.printf("The closest building with free seats is %s.\n",closest.getBuilding());
        
        //Task 7: best area
        System.out.printf("In the area with postcode %s there are the most free seats.\n",getAreaWithMostFreeSeats());

        //Task 8: multiple rooms per building 
        
        //create set of unique buildings
        List<String> buildings = new ArrayList<String>();
        for(Location location : locations){
            String building = location.getBuilding();
            if(!buildings.contains(building)){
                buildings.add(building);
            }
        }
        for(String building: buildings){
            if(multipleRoomsInBuilding(building)){
                System.out.printf("%s has multiple rooms with desktops seats.\n",building);
            }
        }
        
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
        double degreeLength = 110.25*1000;
        double x = myLatitude - location.getLatitude();
        double y = (myLongitude - location.getLongitude()) * Math.cos(myLatitude);
        return degreeLength*Math.sqrt(x*x + y*y);
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