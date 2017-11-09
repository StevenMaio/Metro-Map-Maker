/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.data;

import java.util.ArrayList;
import javafx.scene.paint.Color;

/**
 * Represents a Metro Station in a project. Contains basic styling options 
 * @author Steven Maio
 */
public class MetroStation {
    // These static variables the positions available for the station's label
    // as well as the positions available for the rotation of the label
    public final static int NUMBER_OF_POSITIONS = 4;
    
    // instance variables
    private String name;
    private ArrayList<MetroLineNode> nodes;
    
    // Graphical Elements
    // Label properties
    private DraggableLabel stationLabel;
    private int labelLocation;
    
    // station circle properties
    private DraggableCircle stationCircle;
    
    /**
     * This is the constructor for the MetroStation object.
     */
    public MetroStation() {}
    
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
     * This method adjusts the rotation of the Metro Station's label clockwise
     */
    public void rotateLabelClockwise() {}
    
    /**
     * This method adjusts the rotation of the Metro Station's label counter
     * clockwise
     */
    public void rotateLabelCounterClockwise() {}
    
    /**
     * This method initializes the label, that is it updates the values of the label
     * to reflect a change in the Metro Station
     */
    public void initLabel() {}
    
    /**
     * This method refreshes the stationLabel variable to update any changes 
     * that are made to the label's properties.
     */
    public void refreshLabel() {}
    
    /**
     * This method resets the Station's stationCircle variable
     */
    public void initStationCircle() {}
    
    /**
     * This method will refresh the station circle so that any changes to the
     * station circle will be visible to the user.
     */
    public void refreshStationCircle() {}
    
    /**
     * Returns the name of the instance of Metro Station.
     * 
     * @return 
     *      The value of name
     */
    public String toString() {
       return name; 
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

    public ArrayList<MetroLineNode> getNodes() {
        return nodes;
    }
}