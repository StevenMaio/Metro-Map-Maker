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
import mmm.data.MetroLine;
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
        MetroLine metroLine;
        Draggable selectedShape;
        MetroStation metroStation;
        
        // Determine what to do
        switch (dataManager.getState()) {
            case SELECTING_SHAPE:
                selectedShape = (Draggable) dataManager.getTopShape(x, y);
                
                
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
                
                metroStation = ((DraggableCircle) stationCircle).getMetroStation();
                AddMetroStation_Transaction transaction = new AddMetroStation_Transaction(dataManager, metroStation);
                
                dataManager.getTransactionHistory().addTransaction(transaction);
                
                dataManager.setNewShape(null);
                dataManager.setState(SELECTING_SHAPE);
                break;
            case CREATING_METRO_LINE_END_POINT:
                metroLine = dataManager.getNewMetroLine();
                metroLine.getEndLabel().setX(x);
                metroLine.getEndLabel().setY(y);
                metroLine.resetLine(dataManager);
                
                dataManager.setState(SELECTING_SHAPE);
                dataManager.addMetroLine();
                
                break;
                
            case CREATING_METRO_LINE_START_POINT:
                metroLine = dataManager.getNewMetroLine();
                metroLine.getStartLabel().setX(x);
                metroLine.getStartLabel().setY(y);
                
                dataManager.setState(CREATING_METRO_LINE_END_POINT);
                break;
                
            case ADD_STATIONS_MODE:
                // Make sure that a metroline is selected.
                if (workspace.getMetroLineComboBox().getSelectionModel().getSelectedItem() == null){
                        dataManager.setState(SELECTING_SHAPE);
                        app.getGUI().getPrimaryScene().setCursor(Cursor.DEFAULT);
                }
                else {
                    selectedShape = (Draggable) dataManager.getTopShape(x, y);
                    if (selectedShape instanceof DraggableCircle) {
                        DraggableCircle circle = (DraggableCircle) selectedShape;
                        MetroStation selectedStation = circle.getMetroStation();

                        metroLine = workspace.getMetroLineComboBox().getSelectionModel().getSelectedItem();

                        dataManager.addStationToLine(metroLine, selectedStation);
                    } else {
                        dataManager.setState(SELECTING_SHAPE);
                        app.getGUI().getPrimaryScene().setCursor(Cursor.DEFAULT);
                    }
                }
                break;
                
            case REMOVE_STATIONS_MODE:
                // Check to see if a metroLine is selected
                metroLine = workspace.getMetroLineComboBox().getSelectionModel().getSelectedItem();
                
                // check to see if the selected line is null
                if (metroLine == null) {
                        dataManager.setState(SELECTING_SHAPE);
                        app.getGUI().getPrimaryScene().setCursor(Cursor.DEFAULT);
                } else {
                    selectedShape = (Draggable) dataManager.getTopShape(x, y);
                    
                    // check to see if selected shape is a DraggableCircle
                    if (selectedShape instanceof DraggableCircle) {
                        metroStation = (MetroStation)((DraggableCircle) selectedShape).getMetroStation();
                        
                        dataManager.removeStationFromLine(metroStation, metroLine);
                    } else {
                        dataManager.setState(SELECTING_SHAPE);
                        app.getGUI().getPrimaryScene().setCursor(Cursor.DEFAULT);
                    }
                }
                break;
                
            default:
                // Set State to normal and change the cursor
                dataManager.setState(SELECTING_SHAPE);
                app.getGUI().getPrimaryScene().setCursor(Cursor.DEFAULT);
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
        MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
        
        if (dataManager.getState() == DRAGGING_SHAPE) {
            Draggable selectedDraggableShape = (Draggable) dataManager.getSelectedShape();
            
            // If snap to grid is selected, then make x, y multiples of 10
            if (workspace.getSnapToGridCheckBox().isSelected()) {
                x = x - (x%20);
                y = y - (y%20);
            }
            
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
