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
public class MetroLine extends ArrayList<MetroStation> {
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
        lines = new ArrayList<>();
    }
    
    /**
     * This method will handle the process of adding a Metro Station to the 
     * Metro Line instance
     * @param metroStation
     */
    public void addMetroStation(MetroStation metroStation) {
        
        // If stations is empty, simply add the station to it, otherwise
        if (contains(metroStation))
            return ;
        else if (isEmpty())
            add(metroStation);
        else {
            // Get the first stop and calculate the distance
            MetroStation firstStop = get(0);
            int minIndex = 0;
            double minDistance = metroStation.distance(firstStop);
            
            // Iterate through to find the closest station to metroStation
            for (int i = 1; i < size(); i ++) {
                MetroStation currentStation = get(i);
                double distance = metroStation.distance(currentStation);
                
                if (distance < minDistance) {
                    minIndex = i;
                    minDistance = distance;
                }
            }
            
            // FUCK -- Okay. First, let's figure out what is the 'previous' thing
            Draggable previousNode;
            Draggable nextNode;
            
            // If minIndex == 0, get the startLabel, otherwise. Get what is at minIndex - 1
            if (minIndex == 0)
                previousNode = (Draggable) startLabel;
            else
                previousNode = (Draggable) get(minIndex - 1).getStationCircle();
            
            if (minIndex == size() - 1)
                nextNode = (Draggable) endLabel;
            else
                nextNode = (Draggable) get(minIndex + 1).getStationCircle();
            
            DraggableCircle stationCircle = metroStation.getStationCircle();

            // calculate distances between the two
            double prevXDisplacement = Math.abs(stationCircle.getX() - previousNode.getX());
            double prevYDisplacement = Math.abs(stationCircle.getY() - previousNode.getY());
            
            double nextXDisplacement = Math.abs(stationCircle.getX() - nextNode.getX());
            double nextYDisplacement = Math.abs(stationCircle.getY() - nextNode.getY());
            
            if (Math.hypot(prevXDisplacement, prevYDisplacement) < 
                    Math.hypot(nextXDisplacement, nextYDisplacement))
                add(minIndex, metroStation);
            else
                add(minIndex + 1, metroStation);
        }
    }
    
    /**
     * This method will handle removing a Metro Station from the Metro Line 
     * instance
     * 
     * @param metroStation 
     *      The Metro Station that is being removed from the Metro Line
     */
    public void removeMetroStation(MetroStation metroStation) {
        if (contains(metroStation))
            remove(metroStation);
    }
    
    /**
     * This method returns a string of information that lists of all of the 
     * destinations in the MetroLine
     * 
     * @return 
     *      The string listing off all of the Metro Stations visited by this
     */
    public String getLineDestinations() {
        String destinations = "";
        for (MetroStation e: this) {
            destinations += String.format("-%s\n", e.getName());
        }
        
        return destinations;
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
    public void resetLine(MMMData data) {
        // Clear lines
        clearLines(data);
        
        // Now loop through the stations
        for (int i = -1; i < size(); i++) {
            Line line = new Line();
            Draggable firstObject;
            Draggable secondObject;
            
            line.setStroke(color);
            line.setStrokeWidth(lineThickness);

            if (i == -1)
                firstObject = (Draggable) startLabel;
            else
                firstObject = (Draggable) get(i).getStationCircle();

            if (i == size() - 1)
                secondObject = (Draggable) endLabel;
            else
                secondObject = (Draggable) get(i + 1).getStationCircle();

            // bind the properties and add them to line
            line.startXProperty().bind(firstObject.xProperty());
            line.startYProperty().bind(firstObject.yProperty());
            line.endXProperty().bind(secondObject.xProperty());
            line.endYProperty().bind(secondObject.yProperty());

            // disable the line and all it to the thing
            line.setDisable(true);
            lines.add(line);
        }
    }
    
    public void clearLines(MMMData data) {
        data.getShapes().removeAll(lines);
        lines.clear();
    }

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

    public ArrayList<Line> getLines() {
        return lines;
    }
}