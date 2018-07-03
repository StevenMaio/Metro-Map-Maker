package mmm.gui.draggable;

import javafx.beans.property.DoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import mmm.data.MetroStation;

import static mmm.gui.MMMWorkspace.GRID_ALIGNMENT;

/**
 * This class implements a DraggableCircle. This is borrowed heavily from the
 * goLogoLo project and shares many properties and methods with its analog
 * there.
 *
 * @author Steven Maio
 */
public class DraggableCircle extends Circle implements Draggable {

    // Constants
    public static final double OUTLINE = 3;
    public static final double MIN_RADIUS = 5;
    public static final double MAX_RADIUS = 15;

    // instance variables
    private double startCenterX;
    private double startCenterY;
    private MetroStation metroStation;

    /**
     * Default constructor for DraggableCircle. Sets the value of the radius to
     * the minimum value.
     */
    public DraggableCircle() {
        setRadius(MIN_RADIUS);
        setFill(Color.WHITE);
        setStroke(Color.ORANGE);
        setStrokeWidth(OUTLINE);
    }

    /**
     * This method will be called when an instance of DraggableCircle is
     * selected
     *
     * @param x The X-Coordinate of the mouse click event
     * @param y The Y-Coordinate of the mouse click event
     */
    @Override
    public void start(int x, int y) {
        startCenterX = x;
        startCenterY = y;
    }

    /**
     * Handles the action of dragging the instance of DraggableCircle inside a
     * Pane
     *
     * @param x The X-Coordinate of the mouse drag event
     * @param y The Y-Coordinate of the mouse drag event
     * @param snapToGrid Determines if the new coordinates are truncated.
     */
    @Override
    public void drag(int x, int y, boolean snapToGrid) {
        x = (snapToGrid) ? x - x % GRID_ALIGNMENT : x;
        y = (snapToGrid) ? y - y % GRID_ALIGNMENT : y;
        if (snapToGrid) {
            setCenterX(x);
            setCenterY(y);
        } else {
            double diffX = x - startCenterX;
            double diffY = y - startCenterY;
            double newX = getCenterX() + diffX;
            double newY = getCenterY() + diffY;
            setCenterX(newX);
            setCenterY(newY);
            startCenterX = x;
            startCenterY = y;
        }
    }

    /**
     * Sets the value for the X-Coordinate of the center
     *
     * @param x The value of the X-Coordinate
     */
    @Override
    public void setX(double x) {
        setCenterX(x);
    }

    /**
     * Sets the value for the Y-Coordinate of the center
     *
     * @param y The value of the Y-Coordinate
     */
    @Override
    public void setY(double y) {
        setCenterY(y);
    }

    /**
     * This method returns the value of the center X-Coordinate
     *
     * @return The X-Coordinate of the center
     */
    @Override
    public double getX() {
        return getCenterX();
    }

    /**
     * This method returns the value of the center Y-Coordinate
     *
     * @return The Y-Coordinate of the center
     */
    @Override
    public double getY() {
        return getCenterY();
    }

    @Override
    public DoubleProperty xProperty() {
        return centerXProperty();
    }

    @Override
    public DoubleProperty yProperty() {
        return centerYProperty();
    }

    //////////////////////////////
    // ACCESSOR/MUTATOR METHODS //
    //////////////////////////////
    public MetroStation getMetroStation() {
        return metroStation;
    }

    public void setMetroStation(MetroStation metroStation) {
        this.metroStation = metroStation;
    }
}
