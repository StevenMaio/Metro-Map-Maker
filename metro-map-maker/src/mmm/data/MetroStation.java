/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.data;

import java.util.ArrayList;

/**
 * Represents a Metro Station in a project. Contains basic styling options 
 * @author Steven Maio
 */
public class MetroStation {
    // These static variables the positions available for the station's label
    // as well as the positions available for the rotation of the label
    public final static int NUMBER_OF_POSITIONS = 4;
    public final static int NUMBER_OF_ROTATIONS = 4;
    private final static double LABEL_DISPLACEMENT = 30;
    
    // instance variables
    private String name;
    private ArrayList<MetroStation> neighbors;
    
    // Graphical Elements
    private DraggableLabel stationLabel;
    private int labelLocation;
    private int labelRotation;
    private DraggableCircle stationCircle;
    
    /**
     * This is the constructor for the MetroStation object.
     */
    public MetroStation() {
        neighbors = new ArrayList<>();
    }
    
    /**
     * This method adjusts the label location so that it moves clockwise
     */
    public void moveLabelLocationCounterClockwise() {
        labelLocation = (labelLocation + 1) % NUMBER_OF_POSITIONS;
    }
    
    /**
     * This methods adjust the label location so that it moves clockwise
     */
    public void moveLabelLocationClockwise() {
        labelLocation = (labelLocation - 1) % NUMBER_OF_POSITIONS;
    }
    
    /**
     * This method adds a Metro Station to the neighbors list, and then adds
     * this to other.neighbors
     * 
     * @param other The other Metro Station.
     */
    public void addNeighbor(MetroStation other) {
        neighbors.add(other);
        other.neighbors.add(this);
    }

    /**
     * This method adjusts the rotation of the Metro Station's label clockwise
     */
    public void rotateLabelClockwise() {
        labelRotation = (labelRotation - 1) % NUMBER_OF_ROTATIONS;

        double rotation = labelRotation * 360 / NUMBER_OF_ROTATIONS;
        stationLabel.setRotate(-rotation);
    }
    
    /**
     * This method adjusts the rotation of the Metro Station's label counter
     * clockwise
     */
    public void rotateLabelCounterClockwise() {
        labelRotation = (labelRotation + 1) % NUMBER_OF_ROTATIONS;
        
        double rotation = labelRotation * 360 / NUMBER_OF_ROTATIONS;
        stationLabel.setRotate(rotation);
        
    }
    
    /**
     * Returns the name of the instance of Metro Station.
     * 
     * @return 
     *      The value of name
     */
    public String toString() {
       return name; 
    }
    
    public void refreshRotation() {
        double rotation = labelRotation * 360 / NUMBER_OF_ROTATIONS;
        stationLabel.setRotate(-rotation);
        
    }
    
    /**
     * This method binds the station label to the circle
     */
    public void bindLabelToCircle() {
        double xDisplacement;
        double yDisplacement;
        
        double rotation = labelLocation * Math.PI * 2 / NUMBER_OF_POSITIONS;
        
        xDisplacement = LABEL_DISPLACEMENT * Math.sin(-rotation);
        yDisplacement = -LABEL_DISPLACEMENT * Math.cos(-rotation);
        
        stationLabel.yProperty().bind(
                stationCircle.centerYProperty().add(yDisplacement));
        stationLabel.xProperty().bind(
                stationCircle.centerXProperty().add(xDisplacement));
    }
    
    /**
     * The method calculates the distance between two MetroStations.
     * @param other
     * @return 
     */
    public double distance(MetroStation other) {
        double xDisplacement = Math.abs(stationCircle.getX() - other.stationCircle.getX());
        double yDisplacement = Math.abs(stationCircle.getY() - other.stationCircle.getY());
        
        return Math.hypot(xDisplacement, yDisplacement);
    }
    
    //////////////////////////////
    // ACCESSOR/MUTATOR METHODS //
    //////////////////////////////
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public int getLabelLocation() {
        return labelLocation;
    }

    public DraggableLabel getStationLabel() {
        return stationLabel;
    }

    public DraggableCircle getStationCircle() {
        return stationCircle;
    }

    public void setStationCircle(DraggableCircle stationCircle) {
        this.stationCircle = stationCircle;
    }

    public void setStationLabel(DraggableLabel stationLabel) {
        this.stationLabel = stationLabel;
    }

    public int getLabelRotation() {
        return labelRotation;
    }

    public void setLabelRotation(int labelRotation) {
        this.labelRotation = labelRotation;
    }

    public void setLabelLocation(int labelLocation) {
        this.labelLocation = labelLocation;
    }
}