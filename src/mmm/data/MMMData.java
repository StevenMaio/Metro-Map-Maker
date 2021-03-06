package mmm.data;
import djf.AppTemplate;
import djf.components.AppDataComponent;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import jtps.jTPS;
import mmm.gui.MMMWorkspace;
import static mmm.data.MMMState.SELECTING_SHAPE;

import mmm.gui.draggable.Draggable;
import mmm.gui.draggable.DraggableCircle;
import mmm.gui.draggable.DraggableImage;
import mmm.gui.draggable.DraggableLabel;
import mmm.transactions.*;

/**
 * This class handles the data for a project in the Metro Map Maker application.
 *
 * @author Steven Maio
 */
public class MMMData implements AppDataComponent {
    // Constatns
    public static final Color DEFAULT_BACKGROUND_COLOR
            = Color.WHITE;
    private static final int DEFAULT_WIDTH = 1200;
    private static final int DEFAULT_HEIGHT = 800;
    
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
    private Point2D startingPoint;  // This makes it easy to undo moving shapes
    private MetroLine newMetroLine;
    
    // Style things
    private int width;
    private int height;
    private Color backgroundColor;
    private String imageFilepath;
    private boolean imageBackground;

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
        backgroundColor = DEFAULT_BACKGROUND_COLOR;

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
    public void setThickness(MetroLine metroLine, double thickness) {
        SetLineThickness_Transaction transaction =
                new SetLineThickness_Transaction(this, metroLine, thickness);
        
        transactionHistory.addTransaction(transaction);
    }
        
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
            String name) {
        SetMetroLineSettings_Transaction transaction = new 
                SetMetroLineSettings_Transaction(this, metroLine, name, color);
        
        transactionHistory.addTransaction(transaction);
    }

    /**
     * Handles adding a Metro Station to a Metro Line
     * @param metroLine
     *      The Metro Line having a Metro Station added to it
     * @param metroStation
     *      The Metro Station being added to the Metro Line
     */
    public void addStationToLine(MetroLine metroLine,
            MetroStation metroStation) {
        AddStationToLine_Transaction transaction = new 
        AddStationToLine_Transaction(this, metroLine, metroStation);
        
        transactionHistory.addTransaction(transaction);
    }

    public void deleteMetroStation(MetroStation metroStation) {
        DeleteMetroStation_Transaction transaction = new DeleteMetroStation_Transaction(this, metroStation);
        
        transactionHistory.addTransaction(transaction);
    }
    
    public void deleteMetroLine(MetroLine metroLine) {
        DeleteMetroLine_Transaction transaction = new DeleteMetroLine_Transaction(this, metroLine);
        
        transactionHistory.addTransaction(transaction);
    }
    
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
    public void addLabel(DraggableLabel label) {
        AddLabel_Transaction transaction = 
                new AddLabel_Transaction(this, label);
        
        transactionHistory.addTransaction(transaction);
    }

    /**
     * Adds an instance of DraggableImage to the canvas
     * @param image
     *      The DraggableImage instance being added to the canvas
     */
    public void addImage(DraggableImage image) {
        AddImage_Transaction transaction = new AddImage_Transaction(this, image);
        
        transactionHistory.addTransaction(transaction);
    }

    public void setBoldFont(boolean bold, DraggableLabel... labels) {
        SetBoldFont_Transaction transaction = new SetBoldFont_Transaction(this, bold, labels);
        
        transactionHistory.addTransaction(transaction);
    }
    
    public void setItalicFont(boolean italic, DraggableLabel... labels) {
        SetItalicFont_Transaction transaction = new
            SetItalicFont_Transaction(this, italic, labels);
        
        transactionHistory.addTransaction(transaction);
    }
    
    public void setFontSize(int fontSize, DraggableLabel... labels) {
        SetFontSize_Transaction transaction = new
            SetFontSize_Transaction(this, fontSize, labels);
        
        transactionHistory.addTransaction(transaction);
    }
    
    public void removeShape(Shape shape) {
        RemoveItem_Trasnaction trasnaction = new RemoveItem_Trasnaction(this, shape);
        
        transactionHistory.addTransaction(trasnaction);
    }
    
    public void setFontFamily(String fontFamily, DraggableLabel... labels) {
        SetFontFamily_Transaction transaction = new
            SetFontFamily_Transaction(this, fontFamily, labels);
        
        transactionHistory.addTransaction(transaction);
    }
    
    public void setFontFill(Color color, DraggableLabel... labels) {
        SetFontFill_Transaction transaction =
                new SetFontFill_Transaction(this, color, labels);
        
        transactionHistory.addTransaction(transaction);
    }

    @Override
    public void resetData() {
        shapes.clear();
        metroLines.clear();
        metroStations.clear();
        selectedShape = null;
        state = SELECTING_SHAPE;
        app.getWorkspaceComponent().reloadWorkspace(this);
    }
    
    public void processMovedShape() {
        Draggable draggableShape = (Draggable) selectedShape;
        
        MoveShape_Transaction transaction = new MoveShape_Transaction(this, draggableShape);
        
        transactionHistory.addTransaction(transaction);
    }
    
    public void setShapeFill(Shape shape, Color color) {
        SetFill_Transaction transaction = new SetFill_Transaction(this, shape, color);
        
        transactionHistory.addTransaction(transaction);
    }
    
    public void setRadius(DraggableCircle circle, double newRadius) {
        SetRadius_Transaction transaction = new SetRadius_Transaction(this, circle, newRadius);
        
        transactionHistory.addTransaction(transaction);
    }
        
    public void addMetroLine() {
        AddMetroLine_Transaction transaction = new AddMetroLine_Transaction(this, newMetroLine);
        
        transactionHistory.addTransaction(transaction);
    }
    
    public void removeStationFromLine(MetroStation metroStation, MetroLine metroLine) {
        RemoveStationFromLine_Transaction transaction = new RemoveStationFromLine_Transaction(this, metroLine, metroStation);
        
        transactionHistory.addTransaction(transaction);
    }
    
    public void setImageBackground(String filePath) {
        BackgroundImage image = new BackgroundImage(new Image("file:" + filePath), 
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, 
                    BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        
        Background background = new Background(image);
        
        SetBackground_Transaction transaction = new
                SetBackground_Transaction(this, filePath);
        
        transactionHistory.addTransaction(transaction);
        
        imageFilepath = filePath;
    }
    
    public void setBackgroundFill(Color backgroundColor) {
        SetBackground_Transaction transaction = new SetBackground_Transaction(this, backgroundColor);
        
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

    public MetroLine getNewMetroLine() {
        return newMetroLine;
    }

    public void setNewMetroLine(MetroLine newMetroLine) {
        this.newMetroLine = newMetroLine;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
        
        MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
        workspace.getCanvas().setMaxWidth(width);
    }

    public void setHeight(int height) {
        this.height = height;
        
        MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
        workspace.getCanvas().setMaxHeight(height);
    }

    public AppTemplate getApp() {
        return app;
    }

    public boolean isImageBackground() {
        return imageBackground;
    }

    public void setImageBackground(boolean imageBackground) {
        this.imageBackground = imageBackground;
    }
}