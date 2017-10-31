/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.data;

import javafx.scene.paint.Color;

/**
 *
 * @author steve
 */
public class MetroStation {
    // These static variables the positions available for the station's label
    // as well as the positions available for the rotation of the label
    public final static int POSITION_0 = 0;
    public final static int POSITION_1 = 1;
    public final static int POSITION_2 = 2;
    public final static int POSITION_3 = 3;
    public final static int NUMBER_OF_POSITIONS = 4;
    
    // instance variables
    private String name;
    private int labelLocation;
    private int labelRotation;
    
    // Label properties
    private DraggableLabel stationLabel;
    private int labelXCoordinate;
    private int labelYCoordinate;
    private boolean labelBoldFont;
    private boolean labelItalicFont;
    private String labelFontFamily;
    private int labelFontSize;
    
    // station circle properties
    private DraggableCircle stationCircle;
    private int stationXCoordinate;
    private int stationYCoordinate;
    private double stationRadius;
    private Color stationFillColor;
    
    /**
     * This is the constructor for the MetroStation object.
     */
    public MetroStation() {}
    
    /**
     * This method adjusts the label location so that it moves clockwise
     */
    public void moveLabelLocationCounterClockwise() {
        labelLocation++;
    }
    
    /**
     * This methods adjust the label location so that it moves clockwise
     */
    public void moveLabelLocationClockwise() {
        labelLocation--;
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
     * This method resets the label, that is it updates the values of the label
     * to reflect a change in the Metro Station
     */
    public void resetLabel() {}
    
    /**
     * This method refreshes the stationLabel variable to update any changes 
     * that are made to the label's properties.
     */
    public void refreshLabel() {}
    
    /**
     * This method resets the Station's stationCircle variable
     */
    public void resetStationCircle() {}
    
    /**
     * This method will refresh the station circle so that any changes to the
     * station circle will be visible to the user.
     */
    public void refreshStationCircle() {}
    
    
    /* ACCESSOR/MUTATOR METHODS */
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

    public int getLabelRotation() {
        return labelRotation;
    }

    public DraggableCircle getStationCircle() {
        return stationCircle;
    }

    public int getLabelXCoordinate() {
        return labelXCoordinate;
    }

    public void setLabelXCoordinate(int labelXCoordinate) {
        this.labelXCoordinate = labelXCoordinate;
    }

    public int getLabelYCoordinate() {
        return labelYCoordinate;
    }

    public void setLabelYCoordinate(int labelYCoordinate) {
        this.labelYCoordinate = labelYCoordinate;
    }

    public boolean isLabelBoldFont() {
        return labelBoldFont;
    }

    public void setLabelBoldFont(boolean labelBoldFont) {
        this.labelBoldFont = labelBoldFont;
    }

    public boolean isLabelItalicFont() {
        return labelItalicFont;
    }

    public void setLabelItalicFont(boolean labelItalicFont) {
        this.labelItalicFont = labelItalicFont;
    }

    public String getLabelFontFamily() {
        return labelFontFamily;
    }

    public void setLabelFontFamily(String labelFontFamily) {
        this.labelFontFamily = labelFontFamily;
    }

    public int getLabelFontSize() {
        return labelFontSize;
    }

    public void setLabelFontSize(int labelFontSize) {
        this.labelFontSize = labelFontSize;
    }

    public int getStationXCoordinate() {
        return stationXCoordinate;
    }

    public void setStationXCoordinate(int stationXCoordinate) {
        this.stationXCoordinate = stationXCoordinate;
    }

    public int getStationYCoordinate() {
        return stationYCoordinate;
    }

    public void setStationYCoordinate(int stationYCoordinate) {
        this.stationYCoordinate = stationYCoordinate;
    }

    public double getStationRadius() {
        return stationRadius;
    }

    public void setStationRadius(double stationRadius) {
        this.stationRadius = stationRadius;
    }

    public Color getStationFillColor() {
        return stationFillColor;
    }

    public void setStationFillColor(Color stationFillColor) {
        this.stationFillColor = stationFillColor;
    }
}