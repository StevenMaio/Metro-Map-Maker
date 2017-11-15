/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.gui;

import com.sun.javafx.font.FontConstants;
import djf.AppTemplate;
import djf.ui.AppMessageDialogSingleton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import mmm.data.MMMData;
import static mmm.data.MMMState.*;
import properties_manager.PropertiesManager;
import static mmm.MMMLanguageProperty.*;
import mmm.data.DraggableCircle;
import mmm.data.DraggableLabel;
import mmm.data.MetroStation;
import mmm.file.MMMFiles;
import mmm.transactions.MoveStationLabel_Transaction;

/**
 *
 * @author steve
 */
public class MMMEditController {
    private AppTemplate app;
    private MMMData dataManager;
    
    /**
     * Constructor for the MMMEditController Class. Initializes the variables 
     * app and dataManager.
     * 
     * @param app 
     *      The value of app
     */
    public MMMEditController(AppTemplate app) {
        this.app = app;
        dataManager = (MMMData) app.getDataComponent();
    }
    
    /**
     * This method process an undo request.
     */
    public void processUndo() {
        dataManager.undo();
        
        reloadWorkspace();
    }
    
    /**
     * This method processes handling a redo request.
     */
    public void processRedo() {
        dataManager.redo();
        
        reloadWorkspace();
    };
    
    /**
     * This method displays the about window. Here it describes information 
     * about Metro Map Maker.
     */
    public void processAbout() {
        AppMessageDialogSingleton singleton = AppMessageDialogSingleton.getSingleton();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        String aboutMessage = props.getProperty(ABOUT_MESSAGE);
        String aboutTitle = props.getProperty(ABOUT_TITLE);
        
        singleton.show(aboutTitle, aboutMessage);
    }
    
    public void processNewMetroMap() {
        EnterTextDialogSingleton enterTextDialg = 
                EnterTextDialogSingleton.getSingleton();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        enterTextDialg.show(props.getProperty(NEW_METRO_MAP_TITLE),
                props.getProperty(NEW_METRO_MAP_MESSAGE));
        
        // if the user selected ok
        if (enterTextDialg.isReady()) {
            String metroMapName = enterTextDialg.getText();
            
            // exit if metroMapName is null or empty
            if ("".equals(metroMapName))
                return;
            
            MMMFiles fileManager = (MMMFiles) app.getFileComponent();
            fileManager.createNewMetroMap(metroMapName, app);;
        }
    }
    
    public void processExport() {}
    
    public void processSelectMetroLine() {}
    
    public void processAddMetroLine() {}
    
    public void processDeleteMetroLine() {}
    
    public void processAppendStation() {}
    
    public void processRemoveStation() {}
    
    public void processMetroLineInfo() {}
    
    public void processChangeMetroLineColor() {}
    
    public void processChangeMetroLineThickness() {}
    
    public void processSelectMetroStation() {
        MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
        
        MetroStation selectedMetroStation = workspace.getMetroStationComboBox().getSelectionModel().getSelectedItem();
        workspace.loadMetroStationSettings(selectedMetroStation);
        
        dataManager.setState(SELECTED_METRO_STATION);
    }
    
    /**
     * This method handles the task of creating a new Metro Station
     */
    public void processNewMetroStation() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        EnterTextDialogSingleton enterTextDialog = EnterTextDialogSingleton.getSingleton();
        enterTextDialog.show(props.getProperty(ADD_METRO_STATION_TITLE), 
                props.getProperty(ADD_METRO_STATION_MESSAGE));
        
        if (enterTextDialog.isReady()) {
            dataManager.setState(CREATING_METRO_STATION);
            
            String name = enterTextDialog.getText();
            
            // Create Metro Station, Circle and Label
            MetroStation metroStation = new MetroStation();
            metroStation.setName(name);
            
            DraggableCircle stationCirlce = new DraggableCircle();
            metroStation.setStationCircle(stationCirlce);
            stationCirlce.setMetroStation(metroStation);
            
            DraggableLabel stationLabel = new DraggableLabel(name);
            metroStation.setStationLabel(stationLabel);
            stationLabel.setDisable(true); // Make sure we can't do anything with the label
            
            // TODO: Bind Label to the Circle
            metroStation.bindLabelToCircle();
            
            // Set newshape and then this should be it...
            dataManager.setNewShape(stationCirlce);
        }
    }
    
    public void processDeleteMetroStation() {
        MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
        MetroStation selectedMetroStation = workspace.getMetroStationComboBox().getSelectionModel().getSelectedItem();
        
        dataManager.deleteMetroStation(selectedMetroStation);
    }
    
    /**
     * This method handles move the label of a MetroStation.
     */
    public void processMoveStationLabel() {
        MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
        MetroStation metroStation = workspace.getMetroStationComboBox().getSelectionModel().getSelectedItem();
        
        dataManager.moveStationLabel(metroStation);
    }
    
    public void processRotateStationLabel() {
        MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
        MetroStation metroStation = workspace.getMetroStationComboBox().getSelectionModel().getSelectedItem();
        
        dataManager.rotateStationLabel(metroStation);
    }
    
    public void processChangeStationColor() {
        // Get selected Station's Circle and selectedColor
        MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
        DraggableCircle selectedStation = workspace.getMetroStationComboBox()
                .getSelectionModel().getSelectedItem().getStationCircle();
        Color selectedColor = workspace.getMetroStationColorPicker().getValue();
        
        dataManager.setFill(selectedStation, selectedColor);
    }
    
    public void processChangeStationRadius() {
        // Get the new Radius and circle
        MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
        double newRadius = workspace.getMetroStationRadiusSlider().getValue();
        DraggableCircle selectedCircle = workspace.getMetroStationComboBox()
                .getSelectionModel().getSelectedItem().getStationCircle();
        
        dataManager.changeRadius(selectedCircle, newRadius);
    }
    
    public void processFindRoute() {}
    
    public void processBoldFont() {}
    
    public void processItalicFont() {}
    
    public void processChangeFontSize() {}
    
    public void processChangeFontFamily() {}
    
    public void processChangeFontFill() {}
    
    public void processChangeBackgroundColor() {
        MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
            
        Color newColor = workspace.getDecorToolbarColorPicker().getValue();
        dataManager.changeBackgroundColor(newColor);
        
        reloadWorkspace();
    }
    
    public void processToggleShowGrid() {}
    
    public void processZoomIn() {
        MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
        
        Pane canvas = workspace.getCanvas();
        double scale = canvas.getScaleX();
        scale += 0.1;
        
        // determine if scale is above the bounds for how far scaled it can be
        scale = (scale >= 2) ? 2 : scale;
        canvas.setScaleX(scale);
        canvas.setScaleY(scale);
    }
    
    public void processZoomOut() {
        MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
        
        Pane canvas = workspace.getCanvas();
        double scale = canvas.getScaleX();
        scale -= 0.1;
        
        // determine if scale is beyond it's bounds
        scale = (scale <= 0.5) ? 0.5 : scale;
        canvas.setScaleX(scale);
        canvas.setScaleY(scale);
    }
    
    public void processIncreaseMapSize() {}
    
    public void processDecreaseMapSize() {}

    // This helper method just makes the code easier to read
    private void reloadWorkspace() {
        MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
        workspace.reloadWorkspace(dataManager);
    }
}
