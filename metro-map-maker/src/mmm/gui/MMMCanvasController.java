/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.gui;

import djf.AppTemplate;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.shape.Shape;
import mmm.data.Draggable;
import mmm.data.DraggableCircle;
import mmm.data.MMMData;
import mmm.data.MMMState;
import static mmm.data.MMMState.*;
import mmm.data.MetroStation;
import mmm.transactions.AddMetroStation_Transaction;

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
    public void processCanvasMousePressDown(int x, int y) {
        MMMData dataManager = (MMMData) app.getDataComponent();
        Scene scene = app.getGUI().getPrimaryScene();
        MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
        
        // Determine what to do
        switch (dataManager.getState()) {
            case SELECTING_SHAPE:
                Draggable selectedShape = (Draggable) dataManager.getTopShape(x, y);
                
                
                if (selectedShape == null) {
                    scene.setCursor(Cursor.DEFAULT);
                    app.getWorkspaceComponent().reloadWorkspace(dataManager);
                }
                else {
                    if (selectedShape instanceof DraggableCircle) {
                        workspace.loadMetroStationSettings(
                                ((DraggableCircle) selectedShape).getMetroStation());
                    }
                    Point2D startingPoint = new Point2D(selectedShape.getX(), selectedShape.getY());
                    selectedShape.start(x, y);
                    
                    dataManager.setStartingPoint(startingPoint);
                    scene.setCursor(Cursor.MOVE);
                    dataManager.setState(DRAGGING_SHAPE);
                    dataManager.setSelectedShape((Shape)selectedShape);
                }
                break;
                
            case CREATING_METRO_STATION:
                Draggable stationCircle =  (Draggable) dataManager.getNewShape();
                stationCircle.setX(x);
                stationCircle.setY(y);
                
                MetroStation metroStation = ((DraggableCircle) stationCircle).getMetroStation();
                AddMetroStation_Transaction transaction = new AddMetroStation_Transaction(dataManager, metroStation);
                
                dataManager.getTransactionHistory().addTransaction(transaction);
                
                dataManager.setNewShape(null);
                dataManager.setState(SELECTING_SHAPE);
                break;
                
            default:
                dataManager.setState(SELECTING_SHAPE);
                break;
        }
        
        // Reload the workspace
        app.getWorkspaceComponent().reloadWorkspace(dataManager);
    }
    
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
    public void processCanvasMouseDragged(int x, int y) {
        MMMData dataManager = (MMMData) app.getDataComponent();
        if (dataManager.getState() == DRAGGING_SHAPE) {
            Draggable selectedDraggableShape = (Draggable) dataManager.getSelectedShape();
            selectedDraggableShape.drag(x, y);
            app.getGUI().updateToolbarControls(false);
        }
    }
    
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
    public void processCanvasMouseRelease(int x, int y) {
        MMMData dataManager = (MMMData) app.getDataComponent();
        MMMState state = dataManager.getState();
        Scene scene = app.getGUI().getPrimaryScene();
        
        switch (state) {
            case DRAGGING_SHAPE:
                dataManager.setState(SELECTING_SHAPE);
                scene.setCursor(Cursor.DEFAULT);
                app.getGUI().updateToolbarControls(false);
                
                dataManager.processMovedShape();
                dataManager.setStartingPoint(null);
                break;
                
            default:
                break;
        }
    }
    
    /**
     * This method processes what to do when a mouse click happens
     * @param x The X-Coordinate of the mouse click.
     * @param y The Y-Coordinate of the mouse click.
     */
    public void processCanvasMouseClick(int x, int y) {}
}
