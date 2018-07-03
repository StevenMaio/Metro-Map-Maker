package mmm.gui.draggable;

import javafx.beans.property.DoubleProperty;

/**
 * This interface provides the utility for draggable objects. Such as dragging,
 * and highlighting.
 *
 * @author Steven Maio
 */
public interface Draggable {

    public void start(int x, int y);

    public void drag(int x, int y, boolean snapToGrid);

    public void setX(double x);

    public void setY(double y);

    public double getX();

    public double getY();

    public DoubleProperty xProperty();

    public DoubleProperty yProperty();
}
