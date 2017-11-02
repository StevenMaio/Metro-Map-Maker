/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.gui;

import djf.AppTemplate;
import mmm.data.MMMData;

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
    
    public void processUndo() {}
    
    public void processRedo() {};
    
    public void processAbout() {}
    
    public void processExport() {}
    
    public void processSelectMetroLine() {}
    
    public void processAddMetroLine() {}
    
    public void processDeleteMetroLine() {}
    
    public void processAppendStation() {}
    
    public void processRemoveStation() {}
    
    public void processMetroLineInfo() {}
    
    public void processChangeMetroLineColor() {}
    
    public void processChangeMetroLineThickness() {}
    
    public void processSelectMetroStation() {}
    
    public void processNewMetroStation() {}
    
    public void processDeleteMetroStation() {}
    
    public void processMoveStationLabel() {}
    
    public void processRotateStationLabel() {}
    
    public void processChangeStationColor() {}
    
    public void processChangeStationRadius() {}
    
    public void processFindRoute() {}
    
    public void processBoldFont() {}
    
    public void processItalicFont() {}
    
    public void processChangeFontSize() {}
    
    public void processChangeFontFamily() {}
    
    public void processChangeFontFill() {}
    
    public void processToggleShowGrid() {}
    
    public void processZoomIn() {}
    
    public void processZoomOut() {}
    
    public void processIncreaseMapSize() {}
    
    public void processDecreaseMapSize() {}
}
