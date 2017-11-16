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
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import mmm.data.MMMData;
import mmm.data.MetroLine;
import mmm.data.MetroStation;
import properties_manager.PropertiesManager;
import static mmm.MMMLanguageProperty.*;
import static mmm.data.MetroLine.MAX_THICKNESS;
import static mmm.data.MetroLine.MIN_THICKNESS;
import static mmm.data.DraggableCircle.MIN_RADIUS;
import static mmm.data.DraggableCircle.MAX_RADIUS;
import static mmm.css.MMMStyle.*;
import mmm.data.DraggableCircle;
import mmm.data.DraggableLabel;

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
    
    // Controlers
    private MMMEditController editController;
    private MMMCanvasController canvasController;
    
    // Top Toolbar stuff
    private FlowPane otherTopBar;
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
        
        // Other top bar stuff
        otherTopBar = new FlowPane();
        saveAsButton = gui.initChildButton(otherTopBar, SAVE_AS_ICON.toString(), 
                SAVE_AS_TOOLTIP.toString(), true);
        exportButton = gui.initChildButton(otherTopBar, EXPORT_ICON.toString(), 
                EXPORT_TOOLTIP.toString(), false);
        undoButton = gui.initChildButton(otherTopBar, UNDO_ICON.toString(), 
                UNDO_TOOLTIP.toString(), true);
        redoButton = gui.initChildButton(otherTopBar, REDO_ICON.toString(), 
                REDO_TOOLTIP.toString(), true);
        aboutButton = gui.initChildButton(otherTopBar, ABOUT_ICON.toString(),
                ABOUT_TOOLTIP.toString(), false);
        
        gui.getTopToolbarPane().getChildren().add(otherTopBar);
        otherTopBar.getStyleClass().add(CLASS_BORDERED_PANE);
        
        // Metro Lines Toolbar
        metroLineToolbar = new VBox();
        metroLineToolbarRow1 = new HBox();
        metroLineToolbarRow2 = new HBox();
        Label metroLineToolbarLabel = new Label(
                props.getProperty(METRO_LINES_TOOLBAR_TITLE));
        metroLineComboBox = new ComboBox<>();
        metroLineColorPicker = new ColorPicker();
        // TODO: THESE NEED TO BE CHANGED
        addMetroLineButton = gui.initChildButton(metroLineToolbarRow2, 
                ADD_ICON.toString(), ADD_LINE_TOOLTIP.toString(), false);
        deleteMetroLineButton = gui.initChildButton(metroLineToolbarRow2, 
                REMOVE_ICON.toString(), REMOVE_LINE_TOOLTIP.toString(), false);

        appendStationButton = new Button(
                props.getProperty(APPEND_STATION_TEXT));
        removeStationButton = new Button(
                props.getProperty(REMOVE_STATION_TEXT));
        metroLineThicknessSlider = new Slider(MIN_THICKNESS, MAX_THICKNESS, 1);
        
        // Put everything together
        metroLineToolbarRow1.getChildren().addAll(metroLineToolbarLabel, 
                metroLineComboBox, metroLineColorPicker);
        metroLineToolbarRow2.getChildren().addAll(appendStationButton, 
                removeStationButton);
        metroLineToolbar.getChildren().addAll(metroLineToolbarRow1, 
                metroLineToolbarRow2, metroLineThicknessSlider);
        
        metroLineInfoButton = gui.initChildButton(metroLineToolbarRow2, 
                METRO_LINE_INFO_ICON.toString(), METRO_LINE_INFO_TOOLTIP.toString(), false);
        
        // Metro Stations Toolbar
        metroStationToolbar = new VBox();
        metroStationToolbarRow1 = new HBox();
        metroStationToolbarRow2 = new HBox();
        Label metroStationToolbarLabel = new Label(
                props.getProperty(METRO_STATIONS_TOOLBAR_TITLE));
        metroStationsComboBox = new ComboBox<>();
        metroStationColorPicker = new ColorPicker();
        
        // TODO: ADD BUTTONS LATER
        newMetroStationButton = gui.initChildButton(metroStationToolbarRow2, 
                ADD_ICON.toString(), ADD_METRO_STATION_TOOLTIP.toString(), false);
        deleteMetroStationButton = gui.initChildButton(metroStationToolbarRow2, 
                REMOVE_ICON.toString(), DELETE_METRO_STATION_TOOLTIP.toString(), false);
        snapToGridCheckBox = new CheckBox(props.getProperty(SNAP_TEXT));
        
        moveStationLabelButton = new Button(
                props.getProperty(MOVE_LABEL_TEXT));
        metroStationRadiusSlider = new Slider(MIN_RADIUS, MAX_RADIUS, 5);
        metroStationToolbarRow1.getChildren().addAll(metroStationToolbarLabel, 
                metroStationsComboBox, metroStationColorPicker);
        metroStationToolbarRow2.getChildren().addAll(snapToGridCheckBox,
                moveStationLabelButton);
        metroStationToolbar.getChildren().addAll(metroStationToolbarRow1, 
                metroStationToolbarRow2, metroStationRadiusSlider);
        
        rotateStationLabelButton = gui.initChildButton(metroStationToolbarRow2, 
                ROTATE_ICON.toString(), ROTATE_LABEL_TOOLTIP.toString(), false);
        
        // Route finder toolbar
        routeFinderToolbar = new HBox();
        routeFinderToolbarLeftPane = new VBox();
        startingStationComboBox = new ComboBox<>();
        destinationStationComboBox = new ComboBox<>();
        routeFinderToolbarLeftPane.getChildren().addAll(startingStationComboBox,
                destinationStationComboBox);
        routeFinderToolbar.getChildren().addAll(routeFinderToolbarLeftPane);
        findRouteButton = gui.initChildButton(routeFinderToolbar, 
                FIND_ROUTE_ICON.toString(), FIND_ROUTE_TOOLTIP.toString(), false);
        
        // Decor toolbar
        decorToolbar = new VBox();
        decorToolbarRow1 = new HBox();
        decorToolbarRow2 = new HBox();
        Label decorLabel = new Label(
            props.getProperty(DECOR_TOOLBAR_TITLE));
        decorToolbarColorPicker = new ColorPicker();
        decorToolbarRow1.getChildren().addAll(decorLabel, decorToolbarColorPicker);
        decorToolbar.getChildren().addAll(decorToolbarRow1, decorToolbarRow2);
        setBackgroundImageButton = gui.initChildButton(decorToolbarRow2, 
                SET_IMAGE_BACKGROUND_ICON.toString(), SET_IMAGE_BACKGROUND_TOOLTIP.toString()
                , false);
        addImageButton = gui.initChildButton(decorToolbarRow2, ADD_IMAGE_ICON.toString(),
                ADD_LINE_TOOLTIP.toString(), false);
        addLabelButton = gui.initChildButton(decorToolbarRow2, TEXT_ICON.toString(), 
                ADD_TEXT_TOOLTIP.toString(), false);
        removeElementButton = gui.initChildButton(decorToolbarRow2, REMOVE_ELEMENT_ICON.toString(),
                REMOVE_ELEMENT_TOOLTIP.toString(), false);
        
        // Font toolbar
        fontEditorToolbar = new VBox();
        fontEditorToolbarRow1 = new HBox();
        fontEditorToolbarRow2 = new HBox();
        Label fontLabel = new Label(
                props.getProperty(FONT_TOOLBAR_TITLE));
        fontFillColorPicker = new ColorPicker();
        // TODO: PICTURES
        boldFontButton = gui.initChildButton(fontEditorToolbarRow2, BOLD_ICON.toString(),
                BOLD_TOOLTIP.toString(), false);
        italicFontButton = gui.initChildButton(fontEditorToolbarRow2, ITALICS_ICON.toString(),
                ITALIC_TOOLTIP.toString(), false);
        fontSizeComboBox = new ComboBox<>();
        fontFamilyComboBox = new ComboBox<>();
        fontEditorToolbarRow1.getChildren().addAll(fontLabel, fontFillColorPicker);
        fontEditorToolbarRow2.getChildren().addAll(fontSizeComboBox, 
                fontFamilyComboBox);
        fontEditorToolbar.getChildren().addAll(fontEditorToolbarRow1, 
                fontEditorToolbarRow2);
        
        // Navigation TOolbar
        navigationToolbar = new VBox();
        navigationToolbarRow1 = new HBox();
        navigationToolbarRow2 = new HBox();
        Label navigationLabel = new Label(
                props.getProperty(NAVIGATION_TOOLBAR_TITLE));
        showGridCheckBox = new CheckBox(
                props.getProperty(SHOW_GRID_TEXT));
        // TODO: PICUTRES
        zoomOutButton = gui.initChildButton(navigationToolbarRow2, ZOOM_OUT_ICON.toString(),
                ZOOM_OUT_TOOLTIP.toString(), false);
        zoomInButton = gui.initChildButton(navigationToolbarRow2, ZOOM_IN_ICON.toString(),
                ZOOM_IN_TOOLTIP.toString(), false);
        increaseMapSizeButton = gui.initChildButton(navigationToolbarRow2, 
                LARGER_ICON.toString(), LARGER_TOOLTIP.toString(), false);
        decreaseMapSizeButton = gui.initChildButton(navigationToolbarRow2, 
                SMALLER_ICON.toString(), SMALLER_TOOLTIP.toString(), false);
        navigationToolbarRow1.getChildren().addAll(navigationLabel, showGridCheckBox);
        navigationToolbar.getChildren().addAll(navigationToolbarRow1, 
                navigationToolbarRow2);
        
        // Add everything to the editToolbar
        editToolbar = new VBox();
        ObservableList<Node> editToolbarChildren = editToolbar.getChildren();
        editToolbarChildren.add(metroLineToolbar);
        editToolbarChildren.add(metroStationToolbar);
        editToolbarChildren.add(routeFinderToolbar);
        editToolbarChildren.add(decorToolbar);
        editToolbarChildren.add(fontEditorToolbar);
        editToolbarChildren.add(navigationToolbar);
        
//        add(metroLineToolbar, metroStationToolbar, 
//                routeFinderToolbar, decorToolbar, fontEditorToolbar, 
//                navigationToolbar);
        
        // This goes at the end
        canvas = new Pane();
        
        // Link data with the ObservableLists here/
        MMMData data = (MMMData) app.getDataComponent();
        data.setShapes(canvas.getChildren());
        data.setMetroLines(metroLineComboBox.getItems());
        data.setMetroStations(metroStationsComboBox.getItems());
        startingStationComboBox.setItems(metroStationsComboBox.getItems());
        destinationStationComboBox.setItems(metroStationsComboBox.getItems());
        
        // Set up the workspaces
        workspace = new BorderPane();
        ((BorderPane)workspace).setLeft(editToolbar);
	((BorderPane)workspace).setCenter(canvas);
    }
    
    private void initControllers() {
        /* This private method is used to set all of the controls and action events
       for the controls inside the application */
    
        editController = new MMMEditController(app);
        canvasController = new MMMCanvasController(app);
        
        //canvas stuff
        canvas.setOnMouseDragged(e -> {
            canvasController.processCanvasMouseDragged((int) e.getX(), (int) e.getY());
        });
        
        canvas.setOnMousePressed(e -> {
            canvasController.processCanvasMousePressDown((int) e.getX(), (int) e.getY());
        });
        
        canvas.setOnMouseReleased(e -> {    
            canvasController.processCanvasMouseRelease((int) e.getX(), (int) e.getY());
        });
        
        canvas.setOnMouseClicked(e -> {
            canvasController.processCanvasMouseClick((int) e.getX(), (int)e.getY());
        });
        
        // overrtte new
        app.getGUI().getNewButton().setOnAction(e -> {
            editController.processNewMetroMap();
        });
        
        // Other top toolbar stuff
        undoButton.setOnAction(e -> {
            editController.processUndo();
        });
        redoButton.setOnAction(e -> {
            editController.processRedo();
        });
        aboutButton.setOnAction(e -> {
            editController.processAbout();
        });
        
        // Metro Line Toolbar
        addMetroLineButton.setOnAction(e -> {
            editController.processAddMetroLine();
        });
        
        // Metro Station Toolbar
        newMetroStationButton.setOnAction(e -> {
            editController.processNewMetroStation();
        });
        moveStationLabelButton.setOnAction(e -> {
            editController.processMoveStationLabel();
        });
        rotateStationLabelButton.setOnAction(e -> {
            editController.processRotateStationLabel();
        });
        metroStationsComboBox.setOnAction(e -> {
            editController.processSelectMetroStation();
        });
        deleteMetroStationButton.setOnAction(e -> {
            editController.processDeleteMetroStation();
        });
        metroStationColorPicker.setOnAction(e -> {
            editController.processChangeStationColor();
        });
        metroStationRadiusSlider.setOnMouseDragged(e -> {
            editController.processChangeStationRadius();
        });
        
        // Route Finder
        
        // Decor TOolbar
        decorToolbarColorPicker.setOnAction(e -> {
            editController.processChangeBackgroundColor();
        });
        
        // Font Toolbar
        
        // Navigation Toolbar
        zoomInButton.setOnAction(e -> {
            editController.processZoomIn();
        });
        zoomOutButton.setOnAction(e -> {
            editController.processZoomOut();
        });
    }
    
    private void initStyle() {
    /* This method initializes the style for the application */
        editToolbar.getStyleClass().add(CLASS_EDIT_TOOLBAR);
        
        // make the color pickers and combo boxes buttons
        fontFillColorPicker.getStyleClass().add(CLASS_BUTTON);
        metroLineColorPicker.getStyleClass().add(CLASS_BUTTON);
        metroStationColorPicker.getStyleClass().add(CLASS_BUTTON);
        decorToolbarColorPicker.getStyleClass().add(CLASS_BUTTON);
        
        // Color picker settings
        fontFillColorPicker.getStyleClass().add(CLASS_COLOR_PICKER);
        decorToolbarColorPicker.getStyleClass().add(CLASS_COLOR_PICKER);
        metroLineColorPicker.getStyleClass().add(CLASS_COLOR_PICKER);
        metroStationColorPicker.getStyleClass().add(CLASS_COLOR_PICKER);
        
        // Style for the toolbars
        metroLineToolbar.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        metroLineToolbarRow1.getStyleClass().add(CLASS_EDIT_TOOLBAR_SUB_ROW);
        metroLineToolbarRow2.getStyleClass().add(CLASS_EDIT_TOOLBAR_SUB_ROW);
        
        metroStationToolbar.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        metroStationToolbarRow1.getStyleClass().add(CLASS_EDIT_TOOLBAR_SUB_ROW);
        metroStationToolbarRow2.getStyleClass().add(CLASS_EDIT_TOOLBAR_SUB_ROW);
        
        routeFinderToolbar.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        routeFinderToolbarLeftPane.getStyleClass().add(CLASS_EDIT_TOOLBAR_SUB_ROW);
        
        decorToolbar.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        decorToolbarRow1.getStyleClass().add(CLASS_EDIT_TOOLBAR_SUB_ROW);
        decorToolbarRow2.getStyleClass().add(CLASS_EDIT_TOOLBAR_SUB_ROW);
        
        fontEditorToolbar.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        fontEditorToolbarRow1.getStyleClass().add(CLASS_EDIT_TOOLBAR_SUB_ROW);
        fontEditorToolbarRow2.getStyleClass().add(CLASS_EDIT_TOOLBAR_SUB_ROW);
        
        navigationToolbar.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        navigationToolbarRow1.getStyleClass().add(CLASS_EDIT_TOOLBAR_SUB_ROW);
        navigationToolbarRow2.getStyleClass().add(CLASS_EDIT_TOOLBAR_SUB_ROW);
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
    public void reloadWorkspace(AppDataComponent dataComponent) {
        MMMData dataManager = (MMMData) dataComponent;
        gui.updateToolbarControls(false);
        
        reloadTransactionsToolbar(dataManager);
    }
    
    private void reloadTransactionsToolbar(MMMData dataManager) {
        boolean canUndo = dataManager.getTransactionHistory().canUndo();
        boolean canRedo = dataManager.getTransactionHistory().canRedo();
        
        undoButton.setDisable(! canUndo);
        redoButton.setDisable(! canRedo);
    }
    
    private void loadSelectedShapeSettings(Shape shape) {
    /*
        This private method will load the settings of the selected shape into
        the toolbars where the object can be edited.
    */
    }
    
    // THis method loads the style settings of some kind of text object
    private void loadTextSettings(DraggableLabel text) {}
    
    /**
     * This method will load the values of into a few of the editors.
     * @param metroStation The Metro Station we are loading the settings from
     */
    public void loadMetroStationSettings(MetroStation metroStation) {
        DraggableCircle stationCircle = metroStation.getStationCircle();
        DraggableLabel stationLabel = metroStation.getStationLabel();
        
        metroStationsComboBox.getSelectionModel().select(metroStation);
        
        // Load Circle setttings
        metroStationColorPicker.setValue((Color) stationCircle.getFill());
        metroStationRadiusSlider.setValue(stationCircle.getRadius());
        
        // Load Text Settings
        fontSizeComboBox.getSelectionModel().select(new Integer(stationLabel.getFontSize()));
        fontFamilyComboBox.getSelectionModel().select(stationLabel.getFontFamily());
    }
    
    /**
     * This method loads the style settings of a MetroLine into the controls
     * @param metroLine The Metro line we are loading the sttings from
     */
    public void loadMetroLineSettings(MetroLine metroLine) {}
    
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

    // TODO: This might not be necessary
    public CheckBox getShowGridCheckBox() {
        return showGridCheckBox;
    }

    public Pane getCanvas() {
        return canvas;
    }

    public ColorPicker getDecorToolbarColorPicker() {
        return decorToolbarColorPicker;
    }

    public Slider getMetroStationRadiusSlider() {
        return metroStationRadiusSlider;
    }
}
