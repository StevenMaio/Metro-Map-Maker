/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.gui;

import djf.AppTemplate;
import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import djf.ui.AppGUI;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Shape;
import mmm.data.MMMData;
import mmm.data.MetroLine;
import mmm.data.MetroStation;
import properties_manager.PropertiesManager;
import mmm.MMMLanguageProperty;

/**
 *
 * @author steve
 */
public class MMMWorkspace extends AppWorkspaceComponent {
    // The main components of the application
    private AppTemplate app;
    private AppGUI gui;
    private Pane canvas;
    private BorderPane mainPane;
    private VBox editToolbar;
    
    private boolean snapToGrid; // This might be taken out
    
    // Controlers
    private MMMEditController editController;
    private MMMCanvasController canvasController;
    
    // Top Toolbar stuff
    private Button saveAsButton;
    private Button exportButton;
    private Button undoButton;
    private Button redoButton;
    private Button aboutButton;
    
    // Metro Line Toolbar
    private VBox metroLineToolbar;
    private HBox metroLineToolbarRow1;
    private HBox metroLineToolbarRow2;
    private ComboBox<MetroLine> metroLineComboBox;
    private Button addMetroLineButton;
    private Button deleteMetroLineButton;
    private Button appendStationButton;
    private Button removeStationButton;
    private Button metroLineInfoButton;
    private Button editMetroLineButton;
    private ColorPicker metroLineColorPicker;
    private Slider metroLineThicknessSlider;
    
    // Metro Stations Toolbar
    private VBox metroStationToolbar;
    private HBox metroStationToolbarRow1;
    private HBox metroStationToolbarRow2;
    private ComboBox<MetroStation> metroStationsComboBox;
    private Button newMetroStationButton;
    private Button deleteMetroStationButton;
    private CheckBox snapToGridCheckBox;
    private Button moveStationLabelButton;
    private Button rotateStationLabelButton;
    private ColorPicker metroStationColorPicker;
    private Slider metroStationRadiusSlider;
    
    // Route Finder tool bar
    private HBox routeFinderToolbar;
    private VBox routeFinderToolbarLeftPane;
    private ComboBox<MetroStation> startingStationComboBox;
    private ComboBox<MetroStation> destinationStationComboBox;
    private Button findRouteButton;
    
    // Decor Toolbar
    private VBox decorToolbar;
    private HBox decorToolbarRow1;
    private HBox decorToolbarRow2;
    private ColorPicker decorToolbarColorPicker;
    private Button setBackgroundImageButton;
    private Button addImageButton;
    private Button addLabelButton;
    private Button removeElementButton;
    
    // Font Edit toolbar
    private VBox fontEditorToolbar;
    private HBox fontEditorToolbarRow1;
    private HBox fontEditorToolbarRow2;
    private Button boldFontButton;
    private Button italicFontButton;
    private ComboBox<String> fontFamilyComboBox;
    private ComboBox<Integer> fontSizeComboBox;
    private ColorPicker fontFillColorPicker;
    
    // Navigator Toolbar
    private VBox navigationToolbar;
    private HBox navigationToolbarRow1;
    private HBox navigationToolbarRow2;
    private CheckBox showGridCheckBox;
    private Button zoomInButton;
    private Button zoomOutButton;
    private Button increaseMapSizeButton;
    private Button decreaseMapSizeButton;
    
    /**
     * The constructor for this class. This method will call all of the init 
     * methods as well as set app to the value of initApp and gui to app's gui
     * 
     * @param initApp
     *      The value that the instance variable app will be set to. Also, the
     *      value of initApp.gui will be set to the value of this.gui   
     */
    public MMMWorkspace(AppTemplate initApp) {
        app = initApp;
        gui = app.getGUI();
        
        // Init all the crap
        initLayout();
        initControllers();
        initStyle();
    }
    
    private void initLayout() {
    // This method will initialize all of the containers and controls, and then
    // place them inside the appropriate containers
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        // Metro Lines Toolbar
        metroLineToolbar = new VBox();
        metroLineToolbarRow1 = new HBox();
        metroLineToolbarRow2 = new HBox();
        Label metroLineToolbarLabel = new Label(
                props.getProperty(MMMLanguageProperty.METRO_LINES_TOOLBAR_TITLE));
        metroLineComboBox = new ComboBox<MetroLine>();
        
        // This goes at the end
        canvas = new Pane();
        
        // Link data with the ObservableLists here
        MMMData data = (MMMData) app.getDataComponent();
        data.setShapes(canvas.getChildren());
        data.setMetroLines(metroLineComboBox.getItems());
        data.setMetroStations(metroStationsComboBox.getItems());
        
    }
    
    private void initControllers() {
        /* This private method is used to set all of the controls and action events
       for the controls inside the application */
    
        editController = new MMMEditController(app);
        canvasController = new MMMCanvasController(app);
    }
    
    private void initStyle() {
    /* This method initializes the style for the application */
    }
    
    /**
     * This method may or may noth ave a place so far.
     */
    @Override
    public void resetWorkspace() {}

    /**
     * This method adjusts the toolbars to reflect whether or whether not a 
     * certain object can be edited in such a way.
     * 
     * @param dataComponent 
     *      This object contains values which will determine the state of the
     *      MMMWorkspace.
     */
    @Override
    public void reloadWorkspace(AppDataComponent dataComponent) {}
    
    private void loadSelectedShapeSettings(Shape shape) {
    /*
        This private method will load the settings of the selected shape into
        the toolbars where the object can be edited.
    */
    }
    
    
    //////////////////////////////
    // ACCESSOR/MUTATOR METHODS //
    //////////////////////////////

    public ComboBox<MetroLine> getMetroLineComboBox() {
        return metroLineComboBox;
    }

    public ColorPicker getMetroLineColorPicker() {
        return metroLineColorPicker;
    }

    public ComboBox<MetroStation> getMetroStationComboBox() {
        return metroStationsComboBox;
    }

    public ColorPicker getMetroStationColorPicker() {
        return metroStationColorPicker;
    }

    public ComboBox<MetroStation> getStartingStationComboBox() {
        return startingStationComboBox;
    }

    public ComboBox<MetroStation> getDestinationStationComboBox() {
        return destinationStationComboBox;
    }

    public CheckBox getSnapToGridCheckBox() {
        return snapToGridCheckBox;
    }

    public ComboBox<String> getFontFamilyComboBox() {
        return fontFamilyComboBox;
    }

    public ComboBox<Integer> getFontSizeComboBox() {
        return fontSizeComboBox;
    }

    public CheckBox getShowGridCheckBox() {
        return showGridCheckBox;
    }
}
