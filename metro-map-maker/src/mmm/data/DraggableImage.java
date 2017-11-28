/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.data;

import javafx.scene.shape.Rectangle;
import static djf.settings.AppStartupConstants.FILE_PROTOCOL;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

/**
 * This class implements a draggable image onto the canvas. This is object is
 * able to be dragged and loads an image from an file path.
 * @author Steven Maio
 */
public class DraggableImage extends Rectangle implements Draggable {
    // instance variables
    private double startX;
    private double startY;
    private String imageFilepath;
    
    /**
     * Called when an instance of DraggableImage is about to be dragged. This
     * method helps to preserve the position of the instance relative to the
     * mouse cursor
     * @param x
     *      The X-Coordinate of the mouse event
     * @param y 
     *      The Y-Coordinate of the mouse event
     */
    @Override
    public void start(int x, int y) {
        startX = x;
	startY = y;
    }

    /**
     * Handles the process of dragging the instance of DraggableImage.
     * @param x
     *      The X-Coordinate of the mouse drag event
     * @param y 
     *      The Y-Coordinate of the mouse drag event
     */
    @Override
    public void drag(int x, int y, boolean snapToGrid) {
        x = (snapToGrid) ? x - x%20 : x;
        y = (snapToGrid) ? y - y%20: y;
        
        if (snapToGrid) {
            setX(x);
            setY(y);
        } else {
            double diffX = x - (startX);
            double diffY = y - (startY);
            double newX = getX() + diffX;
            double newY = getY() + diffY;

            xProperty().set(newX);
            yProperty().set(newY);
            startX = x;
            startY = y;
        }
    }

    /**
     * This method is called to indicate that the instance of DraggableImage 
     * has been selected
     */
    @Override
    public void highlight() {
    }

    /**
     * Called to negate the effect of highlight. Called when an instance has 
     * been deselected
     */
    @Override
    public void unhighlight() {
    }
    
    /**
     * This method is called to refresh (or set) the image fill of the instance of
     * DraggableImage
     */
    public void refreshImage() {
        Image im = new Image(FILE_PROTOCOL + imageFilepath);
        
        setHeight(im.getHeight());
        setWidth(im.getWidth());
        
        setFill(new ImagePattern(im));
    }
    
    //////////////////////////////
    // Accessor/mutator methods //
    //////////////////////////////

    public String getImageFilepath() {
        return imageFilepath;
    }

    public void setImageFilepath(String imageFilepath) {
        this.imageFilepath = imageFilepath;
    }
}