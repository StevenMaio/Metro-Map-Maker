/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.data;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

/**
 * THIS IS NO LONGER BEING USED
 * 
 * Represents a node in an instance of Metro Line. Contains a reference to a 
 * Metro Station and a reference to the Metro Line an instance is a part of.
 * 
 * @author Steven Maio
 */
public class MetroLineNode {
    private MetroStation value;
    private MetroLineNode nextNode;
    private MetroLineNode previousNode;
    private MetroLine metroLine;
    private Line line;      // This is the graphical object that will connect
                            // this MetroLineNode to the next MetroLineNode
    
    /**
     * Default Constructor for the MetroLine Node class. Initializes everything
     * to null.
     */
    public MetroLineNode() {}
    
    /**
     * This method initializes the line instance variable
     * @param thickness The value of the stroke width of the line
     * @param color The value of the stroke fill of the line
     */
    public void initLine(double thickness, Color fillColor) {
        // init line and set style
        line = new Line();
        line.setStroke(fillColor);
        line.setStrokeWidth(thickness);
        
        // bind the starting point to the Metro Station
        Circle stationCircle = value.getStationCircle();
        line.startXProperty().bind(stationCircle.centerXProperty());
        line.startYProperty().bind(stationCircle.centerYProperty());
        
        // If this is the last node, bind it to the end label of Metro Line
        if (nextNode == null) {
            DraggableLabel endLabel = metroLine.getEndLabel();
            
            line.endXProperty().bind(endLabel.xProperty());
            line.endYProperty().bind(endLabel.yProperty());
        } else {
            Circle nextCircle = nextNode.value.getStationCircle();
            
            line.endXProperty().bind(nextCircle.centerXProperty());
            line.endYProperty().bind(nextCircle.centerYProperty());
        }
    }
    
    /**
     * This method resets all of the lines in a Metro Line.
     * @param thickness The value of the stroke width
     * @param fillColor The value of the stroke color
     */
    public void resetLine(double thickness, Color fillColor) {
        // If this is null, then do nothing
        if (this == null)
            return;
        
        initLine(thickness, fillColor);
        nextNode.resetLine(thickness, fillColor);
    }
    
    /**
     * This method updates the line's style
     * 
     * @param thickness
     *      The new stroke width for line
     * 
     * @param color
     *      The new fill color for line
     */
    public void setStyle(double thickness, Color color) {
        // Do nothing if this is null
        if (this == null)
            return;
        
        line.setStrokeWidth(thickness);
        line.setStroke(color);
        
        // Update the style of nextNode
        nextNode.setStyle(thickness, color);
    }
    
    //////////////////////////////
    // ACCESSOR/MUTATOR METHODS //
    //////////////////////////////

    public MetroStation getValue() {
        return value;
    }

    public void setValue(MetroStation value) {
        this.value = value;
    }

    public MetroLineNode getNextNode() {
        return nextNode;
    }

    public void setNextNode(MetroLineNode nextNode) {
        this.nextNode = nextNode;
    }

    public MetroLineNode getPreviousNode() {
        return previousNode;
    }

    public void setPreviousNode(MetroLineNode previousNode) {
        this.previousNode = previousNode;
    }

    public MetroLine getMetroLine() {
        return metroLine;
    }

    public void setMetroLine(MetroLine metroLine) {
        this.metroLine = metroLine;
    }

    public Line getLine() {
        return line;
    }
}