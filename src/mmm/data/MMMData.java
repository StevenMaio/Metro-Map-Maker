/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.data;
import djf.AppTemplate;
import djf.components.AppDataComponent;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import jtps.jTPS;

/**
 *
 * @author steve
 */
public class MMMData implements AppDataComponent {
    // Some useful constatns
    public static final String WHITE_HEX = "#FFFFFF";
    public static final String BLACK_HEX = "#000000";
    private static final Paint DEFAULT_BACKGROUND_COLOR 
            = Paint.valueOf(WHITE_HEX);
    
    // instance variables
    private AppTemplate app;
    private ObservableList<Node> shapes;
    private ObservableList<MetroLine> metroLines;
    private ObservableList<MetroStation> metroStations;
    private MMMData state;
    private Shape selectedShape;
    private jTPS transactionHistory;
    
    // Style things
    private Color backgroundColor;
    private Color outlineColor;
    private String imageFilepath;
    private int mapSideLength;
    
    /**
     * Constructor. Initializes app and a few other things.
     * 
     * @param app 
     *      The initialized value of app
     */
    public MMMData(AppTemplate app) {
        this.app = app;
        selectedShape = null;
        transactionHistory = new jTPS();
        backgroundColor = Color.web(WHITE_HEX);
        outlineColor = Color.web(BLACK_HEX);
        
    }
    
    /**
     * This method processes an undo request
     */
    public void undo() {}
    
    /**
     * This method proccesses a redo request
     */
    public void redo() {}
    
    /**
     * This method handles adding a new shape into the canvas
     * 
     * @param shape 
     *      The shape being added to the canvas
     */
    public void addShape(Shape shape) {}
    
    /**
     * This method handles removing a shape from the canvas
     * 
     * @param shape 
     *      The shape being removed
     */
    public void removeShape(Shape shape) {}
    
    /**
     * Gets the first shape containing the point (x, y)
     * @param x
     *      X-Coordinate we are trying to find an shape in
     * @param y
     *      Y-Coordinate we are trying to find a shape in
     * @return 
     *      The Shape containing the point (x,y)
     */
    public Shape getTopShape(int x, int y) {return null;}
    
    /**
     * Sets the new thickness of a MetroLine object.
     * @param metroLine
     *      The Metro Line who's thickness is being changed
     * @param thickness 
     *      The new thickness of the Metro Line
     */
    public void SetLineThickness(MetroLine metroLine, double thickness) {}
    
    /**
     * Sets the fill color and name of the Metro Line instance metroLine
     * @param metroLine
     *      The instance of Metro Line having it's fill color changed
     * @param color 
     *      The new fill color
     * @param name
     *      The name of the metroStation
     */
    public void setMetroLineSettings(MetroLine metroLine, Color color, 
            String name) {}
    
    /**
     * Handles adding a Metro Station to a Metro Line
     * @param metroLine
     *      The Metro Line having a Metro Station added to it
     * @param metroStation
     *      The Metro Station being added to the Metro Line
     */
    public void addStationToLine(MetroLine metroLine, 
            MetroStation metroStation) {}
    
    /**
     * Sets the radius of the selected Metro Station
     * @param metroStation
     *      The selected Metro Station
     * @param radius 
     *      The new value of the radius
     */
    public void setStationRadius(MetroStation metroStation, double radius) {}
    
    /**
     * Changes the background. Determines whether or whether not the new fill 
     * is an image or not.
     */
    public void refreshBackground() {}
    
    /**
     * Adds a DraggableLabel instance to the canvas
     * @param label 
     *      The DraggableLabel instance being added to the canvas
     */
    public void addLabel(DraggableLabel label) {}
    
    /**
     * Adds an instance of DraggableImage to the canvas
     * @param image 
     *      The DraggableImage instance being added to the canvas
     */
    public void addImage(DraggableImage image) {}
    
    /* 
    
        FONT PROPERTIES NEED TO BE ESTABLISHED I GUESS
    
    */
    
    @Override
    public void resetData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    //////////////////////////////
    // Accessor/Mutator Methods //
    //////////////////////////////

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getImageFilepath() {
        return imageFilepath;
    }

    public void setImageFilepath(String imageFilepath) {
        this.imageFilepath = imageFilepath;
    }
}
