/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.data;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 *
 * @author steve
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
     * This method refreshes the line instance variable
     */
    public void resetLine() {}
    
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
}
