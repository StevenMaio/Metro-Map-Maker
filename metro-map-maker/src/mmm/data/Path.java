package mmm.data;

import java.util.ArrayList;

/**
 *
 * @author steve
 */
public class Path {
    private MetroStation startStation;
    private MetroStation endStation;
    private final int STATION_COST = 3;
    private final int TRANSFER_COST = 10;
    private ArrayList<MetroStation> stops;

    /**
     * Constructor for this class
     * @param startStation
     * @param endStation 
     */
    public Path(MetroStation startStation, MetroStation endStation) {
        this.startStation = startStation;
        this.endStation = endStation;
        stops = new ArrayList<>();
    }
    
    // TODO: there's a problem here, and that's that it messes with other paths
    public boolean findPath(MetroStation metroStation) {
        // Base condition
        if (metroStation == endStation) {
            stops.add(0, metroStation);
            return true;
        }
        
        for (MetroStation neighbor: metroStation.getNeighbors()) {
            neighbor.getNeighbors().remove(metroStation);
            
            if (findPath(neighbor)) {
                stops.add(0, metroStation);
                return true;
            }
            
            // This may or may not be important
            neighbor.getNeighbors().add(metroStation);
        }
         
        return false;
    }
    
    public boolean findPath() {
        return findPath(startStation);
    }
    
    public String getPaths() {
        String stopString = "";
        
        for (MetroStation e: stops)
            stopString += e.getName() + "\n";
        
        return stopString;
    }

    //////////////////////////////
    // Accessor/Mutator Methods //
    //////////////////////////////
    
    public MetroStation getStartStation() {
        return startStation;
    }

    public MetroStation getEndStation() {
        return endStation;
    }
}