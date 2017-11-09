/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.data;

import javafx.scene.shape.Rectangle;

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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Handles the process of dragging the instance of DraggableImage.
     * @param x
     *      The X-Coordinate of the mouse drag event
     * @param y 
     *      The Y-Coordinate of the mouse drag event
     */
    @Override
    public void drag(int x, int y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * This method is called to indicate that the instance of DraggableImage 
     * has been selected
     */
    @Override
    public void highlight() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Called to negate the effect of highlight. Called when an instance has 
     * been deselected
     */
    @Override
    public void unhighlight() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * This method is called to refresh (or set) the image fill of the instance of
     * DraggableImage
     */
    public void refreshImage() {}
    
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