package mmm.data;

/**
 * This interface provides the utility for draggable objects. Such as dragging,
 * and highlighting
 * 
 * @author Steven Maio
 */
public interface Draggable {
    public void start(int x, int y);
    public void drag(int x, int y);
    public void setX(double x);
    public void setY(double y);
    public double getX();
    public double getY();
    public void highlight();
}
