package mmm.data;
import djf.AppTemplate;
import djf.components.AppDataComponent;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import jtps.jTPS;
import mmm.gui.MMMWorkspace;
import mmm.transactions.*;

/**
 * This class handles the data for a project in the Metro Map Maker application.
 *
 * @author Steven Maio
 */
public class MMMData implements AppDataComponent {
    // Some useful constatns
    public static final String WHITE_HEX = "#FFFFFF";
    public static final String BLACK_HEX = "#000000";
    public static final Color DEFAULT_BACKGROUND_COLOR
            = Color.WHITE;

    // instance variables
    private String name;
    private AppTemplate app;
    private ObservableList<Node> shapes;
    private ObservableList<MetroLine> metroLines;
    private ObservableList<MetroStation> metroStations;
    private MMMState state;
    private Shape selectedShape;
    private Shape newShape; // This will be where a new shap will be placed
    private jTPS transactionHistory;
    private Point2D startingPoint;

    // Style things
    private int width;
    private int height;
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
     * This method processes an undo request and then refreshes the workspace.
     */
    public void undo() {
        transactionHistory.undoTransaction();
    }

    /**
     * This method proccesses a redo request and then refreshes the workspace.
     */
    public void redo() {
        transactionHistory.doTransaction();
    }

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
    public Shape getTopShape(int x, int y) {
        // Iterate through shapes to find a node that intersects the coordinates
        // in the argument
        for (Node e: shapes) {
            if (e.contains(x, y))
                if (! e.isDisabled())
                    return (Shape) e;
        }
        
        return null;
    }

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
    
    public void moveStationLabel(MetroStation metroStation) {
        MoveStationLabel_Transaction transaction = new MoveStationLabel_Transaction(this, metroStation);
        
        transactionHistory.addTransaction(transaction);
    }
    
    public void rotateStationLabel(MetroStation metroStation) {
        RotateStationLabel_Transaction transaction = new RotateStationLabel_Transaction(this, metroStation);
        
        transactionHistory.addTransaction(transaction);
    }

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

    TODO: FONT PROPERTIES NEED TO BE ESTABLISHED I GUESS

    */

    /**
     * This method changes the background color
     * @param color The new value for the background color.
     */
    public void changeBackgroundColor(Color color) {
        ChangeBackgroundColor_Transaction transaction = new ChangeBackgroundColor_Transaction(this, color);

        transactionHistory.addTransaction(transaction);
    }

    @Override
    public void resetData() {
        shapes.clear();
        metroLines.clear();
        metroStations.clear();
        selectedShape = null;
    }

    /**
     * Changes the background. Determines whether or whether not the new fill
     * is an image or not.
     */
    public void refreshBackground() {
        if (imageFilepath == null) {
            MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();

            Pane canvas = workspace.getCanvas();
            BackgroundFill fill = new BackgroundFill(backgroundColor, null, null);
            Background background = new Background(fill);
            canvas.setBackground(background);
            workspace.getDecorToolbarColorPicker().setValue(backgroundColor);
        } else;
    }
    
    public void processMovedShape() {
        Draggable draggableShape = (Draggable) selectedShape;
        
        MoveShape_Transaction transaction = new MoveShape_Transaction(this, draggableShape);
        
        transactionHistory.addTransaction(transaction);
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

    public void setShapes(ObservableList<Node> shapes) {
        this.shapes = shapes;
    }

    public void setMetroLines(ObservableList<MetroLine> metroLines) {
        this.metroLines = metroLines;
    }

    public void setMetroStations(ObservableList<MetroStation> metroStations) {
        this.metroStations = metroStations;
    }

    public ObservableList<Node> getShapes() {
        return shapes;
    }

    public ObservableList<MetroLine> getMetroLines() {
        return metroLines;
    }

    public ObservableList<MetroStation> getMetroStations() {
        return metroStations;
    }

    public MMMState getState() {
        return state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public jTPS getTransactionHistory() {
        return transactionHistory;
    }

    public void setState(MMMState state) {
        this.state = state;
    }

    public Shape getNewShape() {
        return newShape;
    }

    public void setNewShape(Shape newShape) {
        this.newShape = newShape;
    }

    public Shape getSelectedShape() {
        return selectedShape;
    }

    public void setSelectedShape(Shape selectedShape) {
        this.selectedShape = selectedShape;
    }

    public Point2D getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(Point2D startingPoint) {
        this.startingPoint = startingPoint;
    }
}