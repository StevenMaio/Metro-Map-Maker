/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.gui;

import djf.AppTemplate;
import djf.ui.AppMessageDialogSingleton;
import java.io.File;
import javafx.scene.Cursor;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import static djf.settings.AppStartupConstants.PATH_EXPORTS;
import static djf.settings.AppStartupConstants.PNG;
import static djf.settings.AppStartupConstants.JSON;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javax.imageio.ImageIO;
import mmm.data.MMMData;
import static mmm.data.MMMState.*;
import properties_manager.PropertiesManager;
import static mmm.gui.BorderedMessageDialogSingleton.DESTINATIONS;
import static mmm.MMMLanguageProperty.*;
import static mmm.file.MMMFiles.METRO_EXTENSION;
import mmm.data.DraggableCircle;
import mmm.data.DraggableLabel;
import mmm.data.MMMState;
import mmm.data.MetroLine;
import mmm.data.MetroStation;
import mmm.file.MMMFiles;

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
    
    public void processExport() {
        // get an image of the canvas
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        AppMessageDialogSingleton singleton = AppMessageDialogSingleton.getSingleton();
        MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
        Pane canvas = workspace.getCanvas();
        
        String name = dataManager.getName();
        
        WritableImage image = canvas.snapshot(new SnapshotParameters(), null);
        File imageFile = new File(PATH_EXPORTS + name + METRO_EXTENSION + PNG);
        try {
	    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", imageFile);
            String jsonFilePath = PATH_EXPORTS + name + METRO_EXTENSION + JSON;
            
            // try exporting as well
            app.getFileComponent().exportData(dataManager, jsonFilePath);
            
            // Display a message if it's a success
            singleton.show(props.getProperty(EXPORT_SUCCESS_TITLE), 
                    props.getProperty(EXPORT_SUCCESS_MESSAGE));
	} catch(IOException ioe) {
	    ioe.printStackTrace();
            // Display a message if something failed
            singleton.show(props.getProperty(EXPORT_FAILURE_TITLE), 
                    props.getProperty(EXPORT_FAILURE_MESSAGE));
	}
    }
    
    public void processSelectMetroLine() {
        MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
        
        MetroLine metroLine = workspace.getMetroLineComboBox().getSelectionModel().getSelectedItem();
        
        // If the selected line is null, do nothing. Otherwise load the settings
        // and change the state of the application
        if (metroLine == null);
        else {
            workspace.loadMetroLineSettings(metroLine);
            
            dataManager.setState(SELECTED_METRO_LINE);
        }
        
        workspace.reloadWorkspace(dataManager);
    }
    
    public void processAddMetroLine() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        MetroLineSettingsDialogSingleton singleton = MetroLineSettingsDialogSingleton.getSingleton();
        
        // create new metroline and init default settings
        MetroLine metroLine = new MetroLine();
        
        singleton.show(metroLine, props.getProperty(ADD_METRO_LINE_TITLE));
        
        // If user clicks ready
        if (singleton.isReady()) {
            String name = singleton.getText();
            Color color = singleton.getColor();
            
            metroLine.setName(name);
            DraggableLabel startLabel = new DraggableLabel(name);
            DraggableLabel endLabel = new DraggableLabel(name);
            metroLine.setStartLabel(startLabel);
            metroLine.setEndLabel(endLabel);
            metroLine.setColor(color);
            
            dataManager.setNewMetroLine(metroLine);
            dataManager.setState(CREATING_METRO_LINE_START_POINT);
            
            reloadWorkspace();
        }
    }
    
    public void processEditMetroLine() {
        MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        // get selected metroLine and the metro line singleton
        MetroLine selectedMetroLine = workspace.getMetroLineComboBox()
                .getSelectionModel().getSelectedItem();
        
        MetroLineSettingsDialogSingleton singleton =
                MetroLineSettingsDialogSingleton.getSingleton();
        
        singleton.show(selectedMetroLine, props.getProperty(EDIT_METRO_LINE_TITLE));
        
        // now determine what to do
        if (singleton.isReady()) {
            String name = singleton.getText();
            
            if ("".equals(name))
                return;
            
            dataManager.setMetroLineSettings(selectedMetroLine, 
                    singleton.getColor(), name);
            
            workspace.reloadWorkspace(dataManager);
        }
    }
  
    public void processDeleteMetroLine() {
        MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
        MetroLine metroLine = workspace.getMetroLineComboBox()
                .getSelectionModel().getSelectedItem();
        
        dataManager.deleteMetroLine(metroLine);
        workspace.getMetroLineComboBox().getSelectionModel().select(null);
        dataManager.setState(SELECTING_SHAPE);
        workspace.reloadWorkspace(dataManager);
    }
    
    public void processAppendStation() {
        // Change the state and the cursor
        dataManager.setState(ADD_STATIONS_MODE);
        app.getGUI().getPrimaryScene().setCursor(Cursor.HAND);
    }
    
    public void processRemoveStation() {
        // Change the state and the crusor
        dataManager.setState(REMOVE_STATIONS_MODE);
        app.getGUI().getPrimaryScene().setCursor(Cursor.CROSSHAIR);
        
    }
    
    public void processMetroLineInfo() {
        MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        MetroLine metroLine = workspace.getMetroLineComboBox()
                .getSelectionModel().getSelectedItem();
        
        if (metroLine == null)
            return;
        
        String lineInfo = metroLine.getLineDestinations();
        
        BorderedMessageDialogSingleton singleton = BorderedMessageDialogSingleton.getSingleton();
        
        singleton.show(props.getProperty(METRO_LINE_INFO), metroLine.getName() + " "
                + DESTINATIONS, lineInfo);
        
    }
    
    public void processChangeMetroLineColor() {
        MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
        MetroLine metroLine = workspace.getMetroLineComboBox()
                .getSelectionModel().getSelectedItem();
        
        if (metroLine == null)
            return;
        
        Color color = workspace.getMetroLineColorPicker().getValue();
        
        dataManager.setMetroLineSettings(metroLine, color, metroLine.getName());
        workspace.reloadWorkspace(dataManager);
    }
    
    public void processChangeMetroLineThickness() {
        MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
        MetroLine metroLine = workspace.getMetroLineComboBox()
                .getSelectionModel().getSelectedItem();
        
        if (metroLine == null)
            return;
        
        double thickness = workspace.getMetroLineThicknessSlider().getValue();
        
        dataManager.SetLineThickness(metroLine, thickness);
        workspace.reloadWorkspace(dataManager);
    }
    
    public void processSelectMetroStation() {
        MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
        MetroStation selectedMetroStation = workspace.getMetroStationComboBox().getSelectionModel().getSelectedItem();
        
        // If the selected station is null, do nothing
        if (selectedMetroStation == null);
        else {
            workspace.loadMetroStationSettings(selectedMetroStation);

            dataManager.setState(SELECTED_METRO_STATION);
        }
        
        workspace.reloadWorkspace(dataManager);
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
            reloadWorkspace();
        }
    }
    
    public void processDeleteMetroStation() {
        MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
        MetroStation selectedMetroStation = workspace.getMetroStationComboBox().getSelectionModel().getSelectedItem();
        
        dataManager.deleteMetroStation(selectedMetroStation);
        workspace.getMetroStationComboBox().getSelectionModel()
                .select(null);
        workspace.reloadWorkspace(dataManager);
    }
    
    /**
     * This method handles move the label of a MetroStation.
     */
    public void processMoveStationLabel() {
        MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
        MetroStation metroStation = workspace.getMetroStationComboBox().getSelectionModel().getSelectedItem();
        
        dataManager.moveStationLabel(metroStation);
        workspace.reloadWorkspace(dataManager);
    }
    
    public void processRotateStationLabel() {
        MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
        MetroStation metroStation = workspace.getMetroStationComboBox().getSelectionModel().getSelectedItem();
        
        dataManager.rotateStationLabel(metroStation);
        workspace.reloadWorkspace(dataManager);
    }
    
    public void processChangeStationColor() {
        // Get selected Station's Circle and selectedColor
        MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
        DraggableCircle selectedStation = workspace.getMetroStationComboBox()
                .getSelectionModel().getSelectedItem().getStationCircle();
        Color selectedColor = workspace.getMetroStationColorPicker().getValue();
        
        dataManager.setFill(selectedStation, selectedColor);
        workspace.reloadWorkspace(dataManager);
    }
    
    public void processChangeStationRadius() {
        // Get the new Radius and circle
        MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
        double newRadius = workspace.getMetroStationRadiusSlider().getValue();
        DraggableCircle selectedCircle = workspace.getMetroStationComboBox()
                .getSelectionModel().getSelectedItem().getStationCircle();
        
        dataManager.changeRadius(selectedCircle, newRadius);
        workspace.reloadWorkspace(dataManager);
    }
    
    public void processFindRoute() {}
    
    public void processBoldFont() {
        MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
        boolean bold = workspace.getBoldFontButton().isSelected();
        MMMState state = dataManager.getState();
        
        switch (state) {
            case SELECTED_LABEL:
                DraggableLabel label = (DraggableLabel) dataManager.getSelectedShape();
                
                dataManager.setBoldFont(bold, label);
                break;
                
            case SELECTED_METRO_LINE:
                MetroLine metroLine = workspace.getMetroLineComboBox()
                        .getSelectionModel().getSelectedItem();
                
                dataManager.setBoldFont(bold, metroLine.getStartLabel(),
                        metroLine.getEndLabel());
                break;
                
            case SELECTED_METRO_STATION:
                MetroStation metroStation = workspace.getMetroStationComboBox()
                        .getSelectionModel().getSelectedItem();
                
                dataManager.setBoldFont(bold, metroStation.getStationLabel());
                break;
                
            default:
                break;
        }
        
        workspace.reloadWorkspace(dataManager);
    }
    
    public void processItalicFont() {
        MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
        boolean italic = workspace.getItalicFontButton().isSelected();
        MMMState state = dataManager.getState();
        
        switch (state) {
            case SELECTED_LABEL:
                DraggableLabel label = (DraggableLabel) dataManager.getSelectedShape();
                
                dataManager.setItalicFont(italic, label);
                break;
                
            case SELECTED_METRO_LINE:
                MetroLine metroLine = workspace.getMetroLineComboBox()
                        .getSelectionModel().getSelectedItem();
                
                dataManager.setItalicFont(italic, metroLine.getEndLabel(),
                        metroLine.getStartLabel());
                break;
                
            case SELECTED_METRO_STATION:
                MetroStation metroStation = workspace.getMetroStationComboBox()
                        .getSelectionModel().getSelectedItem();
                
                dataManager.setItalicFont(italic, metroStation.getStationLabel());
                break;
                
            default:
                break;
        }
        
        workspace.reloadWorkspace(dataManager);
    }
    
    public void processChangeFontSize() {
        MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
        int fontSize = workspace.getFontSizeComboBox().getValue();
        
        MMMState state = dataManager.getState();
        
        switch (state) {
            case SELECTED_LABEL:
                DraggableLabel label = (DraggableLabel) dataManager.getSelectedShape();
                
                dataManager.setFontSize(fontSize, label);
                break;
                
            case SELECTED_METRO_STATION:
                MetroStation metroStation = workspace.getMetroStationComboBox()
                        .getSelectionModel().getSelectedItem();
                
                dataManager.setFontSize(fontSize, metroStation.getStationLabel());
                break;
                
            case SELECTED_METRO_LINE:
                MetroLine metroLine = workspace.getMetroLineComboBox()
                        .getSelectionModel().getSelectedItem();
                
                dataManager.setFontSize(fontSize, metroLine.getStartLabel(),
                        metroLine.getEndLabel());
                break;
                
            default:
                break;
        }
        
        workspace.reloadWorkspace(dataManager);
    }
    
    public void processChangeFontFamily() {
        MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
        String fontFamily = workspace.getFontFamilyComboBox().getValue();
        
        MMMState state = dataManager.getState();
        
        switch (state) {
            case SELECTED_LABEL:
                DraggableLabel label = (DraggableLabel) dataManager.getSelectedShape();
                
                dataManager.setFontFamily(fontFamily, label);
                break;
                
            case SELECTED_METRO_LINE:
                MetroLine metroLine = workspace.getMetroLineComboBox()
                        .getValue();
                
                dataManager.setFontFamily(fontFamily, metroLine.getStartLabel(),
                        metroLine.getEndLabel());
                break;
                
            case SELECTED_METRO_STATION:
                MetroStation metroStation = workspace.getMetroStationComboBox()
                        .getValue();
                
                dataManager.setFontFamily(fontFamily, metroStation.getStationLabel());
                break;
                
            default:
                break;
        }
        
        workspace.reloadWorkspace(dataManager);
    }
    
    public void processChangeFontFill() {
        MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
        Color newColor = workspace.getFontFillColorPicker().getValue();
        
        MMMState state = dataManager.getState();
        
        switch (state) {
            case SELECTED_METRO_LINE:
                MetroLine metroLine = workspace.getMetroLineComboBox()
                        .getSelectionModel().getSelectedItem();
                
                dataManager.setFontFill(newColor, metroLine.getStartLabel(),
                        metroLine.getEndLabel());
                break;
                
            case SELECTED_METRO_STATION:
                MetroStation metroStation = workspace.getMetroStationComboBox()
                        .getSelectionModel().getSelectedItem();
                
                dataManager.setFontFill(newColor, metroStation.getStationLabel());
                break;
                
            case SELECTED_LABEL:
                DraggableLabel label = (DraggableLabel) dataManager.getSelectedShape();
                
                dataManager.setFontFill(newColor, label);
                break;
                
            default:
                break;
        }
        
        workspace.reloadWorkspace(dataManager);
    }
    
    public void processChangeBackgroundColor() {
        MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
            
        Color newColor = workspace.getDecorToolbarColorPicker().getValue();
        dataManager.changeBackgroundColor(newColor);
        
        workspace.reloadWorkspace(dataManager);
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
    
    public void processAddLabel() {
        MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
        EnterTextDialogSingleton singleton = EnterTextDialogSingleton.getSingleton();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        singleton.show(props.getProperty(INIT_LABEL_TITLE), 
                props.getProperty(INIT_LABEL_MESSAGE));
        
        if (singleton.isReady()) {
            String text = singleton.getText();
            
            if (! "".equals(text)) {
                DraggableLabel label = new DraggableLabel(text);
                label.setIndependent(true);
                dataManager.setSelectedShape(label);
                dataManager.setState(CREATING_LABEL);
            }
        }
    }
    
    public void processDeleteMapElement() {
        MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
        MMMState state = dataManager.getState();
        Shape selectedShape = dataManager.getSelectedShape();
        
        switch (state) {
            case SELECTED_LABEL:
                dataManager.removeShape(selectedShape);
                dataManager.setState(SELECTING_SHAPE);
                
                break;
            
            default:
                break;
        }
        
        workspace.reloadWorkspace(dataManager);
    }

    // This helper method just makes the code easier to read
    private void reloadWorkspace() {
        MMMWorkspace workspace = (MMMWorkspace) app.getWorkspaceComponent();
        workspace.reloadWorkspace(dataManager);
    }
}
