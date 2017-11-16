package mmm.data;

import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import static mmm.data.DraggableLabel.*;

/**
 * This class will represent a Metro Line object. This class will deal with
 * both the responsibilites of handling data as well as gui.
 * 
 * @author Steven Maio
 */
public class MetroLine extends ArrayList<MetroLineNode> {
    public static final Color DEFAULT_METRO_LINE_COLOR = Color.ORANGE;
    public static final double MAX_THICKNESS = 8;
    public static final double MIN_THICKNESS = 2;
    
    // instance variables
    private String name;
    private Color color;
    private double lineThickness;
    private Line firstLine;
    private ArrayList<Line> lines;
    
    // Metro Line labels
    private DraggableLabel startLabel;
    private DraggableLabel endLabel;
    
    
    /**
     * This is the constructor method for the MetroLine class.
     */
    public MetroLine() {
        color = DEFAULT_METRO_LINE_COLOR;
        lineThickness = MIN_THICKNESS;
    }
    
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
    public void initLine() {
    }
    
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
    
    public DraggableLabel initLabel() {
        DraggableLabel label = new DraggableLabel(name);
        label.setBold(DEFAULT_BOLD);
        label.setItalicized(DEFAULT_ITALIC);
        label.setFontFamily(DEFAULT_FONT_FAMILY);
        label.setFontSize(DEFAULT_FONT_SIZE);
        
        return label;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setLineThickness(double lineThickness) {
        this.lineThickness = lineThickness;
    }

    public void setStartLabel(DraggableLabel startLabel) {
        this.startLabel = startLabel;
    }

    public DraggableLabel getStartLabel() {
        return startLabel;
    }
    
    public void setEndLabel(DraggableLabel endLabel) {
        this.endLabel = endLabel;
    }

    public DraggableLabel getEndLabel() {
        return endLabel;
    }

    public void setFirstLine(Line firstLine) {
        this.firstLine = firstLine;
    }
}