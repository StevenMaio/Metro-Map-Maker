/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.data;

import java.util.LinkedList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * This class will represent a Metro Line object. This class will deal with
 * both the responsibilites of handling data as well as gui.
 * 
 * @author Steven Maio
 */
public class MetroLine extends LinkedList<MetroLineNode> {
    // instance variables
    private String name;
    private Color color;
    private double lineThickness;
    private Line firstLine;
    
    // Label Font Properties
    private boolean labelBoldFont;
    private boolean labelItalicFont;
    private String labelFontFamily;
    private int labelFontSize;
    
    // startLabel & Properties
    private DraggableLabel startLabel;
    
    // endLabel & Properties
    private DraggableLabel endLabel;
    
    /**
     * This is the constructor method for the MetroLine class.
     */
    public MetroLine() {}
    
    /**
     * This method will handle the process of adding a Metro Station to the 
     * Metro Line instance
     */
    public void addMetroStation(MetroStation metroStation) {}
    
    /**
     * This method will handle removing a Metro Station from the Metro Line 
     * instance
     * 
     * @param metroStation 
     *      The Metro Station that is being removed from the Metro Line
     */
    public void removeMetroStation(MetroStation metroStation) {}
    
    /**
     * This method returns a string of information that lists of all of the 
     * destinations in the MetroLine
     * 
     * @return 
     *      The string listing off all of the Metro Stations visited by this
     */
    public String getLineDestinations() {
        return null;
    }
    
    /**
     * This method will initialize the firstLine variable
     */
    public void initLine() {}
    
    /**
     * This method will refresh the initLine, that is set what it's connected
     * to and maybe refresh the style... I'm not sure
     */
    public void refreshLine() {}

    /**
     * Returns a string of info that describes the MetroLine
     * 
     * @return 
     *      The value of this.name
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

    public Color getColor() {
        return color;
    }

    public double getLineThickness() {
        return lineThickness;
    }

    public Line getFirstLine() {
        return firstLine;
    }

    public boolean isLabelBoldFont() {
        return labelBoldFont;
    }

    public boolean isLabelItalicFont() {
        return labelItalicFont;
    }

    public String getLabelFontFamily() {
        return labelFontFamily;
    }

    public int getLabelFontSize() {
        return labelFontSize;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setLineThickness(double lineThickness) {
        this.lineThickness = lineThickness;
    }

    public void setLabelBoldFont(boolean labelBoldFont) {
        this.labelBoldFont = labelBoldFont;
    }

    public void setLabelItalicFont(boolean labelItalicFont) {
        this.labelItalicFont = labelItalicFont;
    }

    public void setLabelFontFamily(String labelFontFamily) {
        this.labelFontFamily = labelFontFamily;
    }

    public void setLabelFontSize(int labelFontSize) {
        this.labelFontSize = labelFontSize;
    }
    
    
}
