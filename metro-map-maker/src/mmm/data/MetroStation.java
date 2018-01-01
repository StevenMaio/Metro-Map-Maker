package mmm.data;

import java.util.ArrayList;

/**
 * Represents a Metro Station in a project. Contains basic styling options 
 * @author Steven Maio
 */
public class MetroStation {
    // These static variables the positions available for the station's label
    // as well as the positions available for the rotation of the label
    public final static int NUMBER_OF_POSITIONS = 8;
    public final static int NUMBER_OF_ROTATIONS = 4;
    private final static double LABEL_DISPLACEMENT = 30;
    
    // instance variables
    private String name;
    private ArrayList<MetroStation> neighbors;
    private ArrayList<MetroLine> metroLines;
    
    // Graphical Elements
    private DraggableLabel label;
    private int labelPosition;
    private int labelRotation;
    private DraggableCircle circle;
    
    /**
     * This is the constructor for the MetroStation object.
     */
    public MetroStation() {
        metroLines = new ArrayList<>();
        neighbors = new ArrayList<>();
    }
    
    /**
     * This method adjusts the label location so that it moves clockwise
     */
    public void moveLabelLocationCounterClockwise() {
        labelPosition = (labelPosition + 1) % NUMBER_OF_POSITIONS;
    }
    
    /**
     * This methods adjust the label location so that it moves clockwise
     */
    public void moveLabelLocationClockwise() {
        labelPosition = (labelPosition - 1) % NUMBER_OF_POSITIONS;
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
        label.setRotate(-rotation);
    }
    
    /**
     * This method adjusts the rotation of the Metro Station's label counter
     * clockwise
     */
    public void rotateLabelCounterClockwise() {
        labelRotation = (labelRotation + 1) % NUMBER_OF_ROTATIONS;
        
        double rotation = labelRotation * 360 / NUMBER_OF_ROTATIONS;
        label.setRotate(rotation);
        
    }
    
    /**
     * Returns the name of the instance of Metro Station.
     * 
     * @return 
     *      The value of name
     */
    @Override
    public String toString() {
       return name; 
    }
    
    public void refreshRotation() {
        double rotation = labelRotation * 360 / NUMBER_OF_ROTATIONS;
        label.setRotate(-rotation);
        
    }
    
    /**
     * This method binds the station label to the circle
     */
    public void bindLabelToCircle() {
        double xDisplacement;
        double yDisplacement;
        
        double rotation = labelPosition * Math.PI * 2 / NUMBER_OF_POSITIONS;
        
        xDisplacement = LABEL_DISPLACEMENT * Math.sin(-rotation);
        yDisplacement = -LABEL_DISPLACEMENT * Math.cos(-rotation);
        
        label.yProperty().bind(circle.centerYProperty().add(yDisplacement));
        label.xProperty().bind(circle.centerXProperty().add(xDisplacement));
    }
    
    /**
     * The method calculates the distance between two MetroStations.
     * @param other
     * @return 
     */
    public double distance(MetroStation other) {
        double xDisplacement = Math.abs(circle.getX() - other.circle.getX());
        double yDisplacement = Math.abs(circle.getY() - other.circle.getY());
        
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

    public int getLabelPosition() {
        return labelPosition;
    }

    public DraggableLabel getLabel() {
        return label;
    }

    public DraggableCircle getCircle() {
        return circle;
    }

    public void setCircle(DraggableCircle circle) {
        this.circle = circle;
    }

    public void setLabel(DraggableLabel label) {
        this.label = label;
    }

    public int getLabelRotation() {
        return labelRotation;
    }

    public void setLabelRotation(int labelRotation) {
        this.labelRotation = labelRotation;
    }

    public void setLabelPosition(int position) {
        this.labelPosition = position;
    }

    public ArrayList<MetroLine> getMetroLines() {
        return metroLines;
    }

    public ArrayList<MetroStation> getNeighbors() {
        return neighbors;
    }
}