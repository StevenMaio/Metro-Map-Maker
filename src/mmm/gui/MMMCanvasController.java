/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.gui;

import djf.AppTemplate;

/**
 *
 * @author steve
 */
public class MMMCanvasController {
    private AppTemplate app;
    
    /**
     * Constructor for the MMMCanvasController. Initializes the variable app
     * 
     * @param app
     *      The value of app
     */
    public MMMCanvasController(AppTemplate app) {
        this.app = app;
    }
    
    /**
     * This method will process what to do when a mouse press down happens on 
     * the canvas
     * 
     * @param x
     *      The X-Coordinate of the mouse click.
     * 
     * @param y
     *      The Y-Coordinate of the mouse click.
     */
    public void processCanvasMousePressDown(int x, int y) {}
    
    /**
     * This method will process what to do when a mouse drag happens on 
     * the canvas
     * 
     * @param x
     *      The X-Coordinate of the mouse click.
     * 
     * @param y
     *      The Y-Coordinate of the mouse click.
     */
    public void processCanvasMouseDragged(int x, int y) {}
    
    /**
     * This method will process what to do when a mouse press release happens on 
     * the canvas
     * 
     * @param x
     *      The X-Coordinate of the mouse click.
     * 
     * @param y
     *      The Y-Coordinate of the mouse click.
     */
    public void processCanvasMouseRelease(int x, int y) {}
}
