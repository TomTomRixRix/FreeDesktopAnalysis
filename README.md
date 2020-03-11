# FreeDesktopAnalysis
Exercise for INST0002 lecture at UCL in which data from the UCL API is analysed to determine free desktops.

## Setup, required software and how to run it
You need a JDK installed on your computer to compile and run the Java files.
```
javac LocationManager.java
java LocationManager
```

To generate a suitable comma separated values (csv) file from a json file you can use `python jsontocsv.py` which will turn a file called `desktops.txt` into `desktops.csv`. You might want to adjust the filenames.

To get the current data from the [UCL API](https://uclapi.com) you can use `python requestcsv.py` which will return a `desktops.csv` file. Note that you have to place a UCL API token in the code.

## Data
The data comes from [UCL API](https://uclapi.com). It is stored in a csv file and read into the Java programme. The [desktops.csv](desktops.csv) gives an example of how the data is structured. One example entry is shown here:

```
Basement-B115A,51.5241,-0.1346,Cruciform Building,Gower Street,WC1E 6BT,17,22
```

So the order is
```
Room Name, Latitude, Longitude, Building Name, Address, Postcode, Free Seats, Total Seats
```

## Tasks

Using the provided `LocationManager` the following tasks can be solved:
1. get the total absolute amount of free seats
2. get the total relative amount (percentage) of free seats
3. get the percentage of free seats per location
4. which location currently has the most absolute/relative free seats?
5. what is the distance (in metres) from your current position (in latitude/longitude) to the location with the most free seats?
6. what is the closest building with available free desktops?
7. in which area (by postcode) are the most available free seats?
8. is there a building that has multiple rooms with desktops? which ones?

Further questions/tasks might be created based on the data.

## Learning Objectives
* read in csv files
* create a data class (Location.java) with member variables, getter methods, constructor each with appropriate data type and return types
* testing class (LocationManager.java) with static methods that solve the tasks
* calculating distances between coordinates in Java
* finding the maximum in a list with respect to one property
* using ArrayLists and List methods
* creating a set of unique items from a list
* exception handling

## Open Tasks (for students who finish the other ones earlier)
1. implement a JSON parser which directly parses the received API request into Java Location objects
2. do the web request from your code to fetch the current data whenever the programme is executed
3. improve the visualisation of the results; perhaps with tables
