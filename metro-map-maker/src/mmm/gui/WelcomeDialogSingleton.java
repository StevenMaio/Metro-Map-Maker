package mmm.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author steve
 */
public class WelcomeDialogSingleton extends Stage {
    // Singleton
    private static WelcomeDialogSingleton singleton;
    private static final double PREFERRED_HEIGHT = 320;
    private static final double PREFERRED_WIDTH = 500;
    
    private ListView<String> recentMapsListView;
    private boolean ready;
    private String mapName;
    
    // Text constants
    private static final String RECENT_MAPS = "Recent Maps";
    private static final String METRO_MAP_MAKER = "Metro Map Maker";
    private static final String NEW_METRO_MAP = "New Metro Map";
    private static final String WELCOME_MESSAGE = "Welcome to the Metro Map Maker";
    private static final String ENTER_METRO_MAP_NAME = "Enter Metro Map name";
    
    /**
     * Returns the WelcomeDialogSingleton singleton and initializes it if it hasn't been
     * initialized.
     * 
     * @return 
     *      The WelcomeDialogSingleton singleton
     */
    public static WelcomeDialogSingleton getSingleton() {
        if (singleton == null)
            singleton = new WelcomeDialogSingleton();
        
        return  singleton;
    }
    
    private WelcomeDialogSingleton() {}
    
    /**
     * This method initializes the window and it's contents.
     * 
     * @param primaryStage 
     *      The owner of this stage.
     */
    public void init(Stage primaryStage) {
        // Make it model
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
        ready = false;
        
        // Create the containers
        BorderPane mainPane = new BorderPane();
        VBox centerPane = new VBox();
        VBox leftPane = new VBox();
        
        // mainPane content and settings
        mainPane.setPadding(new Insets(5));
        mainPane.setLeft(leftPane);
        mainPane.setCenter(centerPane);
        mainPane.setPrefWidth(PREFERRED_WIDTH);
        mainPane.setPrefHeight(PREFERRED_HEIGHT);
        
        // Center Pane content and settings
        // TODO: THESE NEED TO BE CHANGED LATER TO LOAD FROM LANGUAGE PROPERTIES
        Label centerPaneLabel = new Label(METRO_MAP_MAKER);
        Button newMetroMapButton = new Button(NEW_METRO_MAP);
        centerPane.setAlignment(Pos.CENTER);
        centerPane.setSpacing(50);
        centerPane.getChildren().addAll(centerPaneLabel, newMetroMapButton);
        
        // Left Pane content and settings
        // TODO: THIS NEEDS TO BE CHANGED LATER TO LOAD FROM LAGNUAGE PROPERTIES
        Label rightPaneLabel = new Label(RECENT_MAPS);
        recentMapsListView = new ListView<>();
        leftPane.setAlignment(Pos.CENTER_LEFT);
        leftPane.setSpacing(5);
        leftPane.setPadding(new Insets(5));
        leftPane.getChildren().addAll(rightPaneLabel, recentMapsListView);
        
        // Set Event Handlers
        newMetroMapButton.setOnAction(e -> {
            processNewMap();
        });
        recentMapsListView.setOnMouseClicked(e -> {
            processRecentMap();
        });
        
        // Create a scene and put it in the window
        // THIS NEEDS TO BE CHANGED LATER TO LOAD FROM LAGNUAGE PROPERTIES
        setTitle(WELCOME_MESSAGE);
        Scene scene = new Scene(mainPane);
        this.setScene(scene);
    }
    
    /**
     * Handles when the user presses the New Metro Map button.
     */
    public void processNewMap() {
        EnterTextDialogSingleton enterTextDialog = 
                EnterTextDialogSingleton.getSingleton();
        
        enterTextDialog.show(NEW_METRO_MAP, ENTER_METRO_MAP_NAME);
        
        // DO CRAP
        if (enterTextDialog.isReady()) {
            mapName = enterTextDialog.getText();
                    
            // if the text is empty do nothing, otherwise
            if (mapName.equals(""))
                return;
            
            ready = true;
            // Try to create a new file
//            try {
//                metroMapFile = new File(PATH_WORK + cityName + FILE_EXTENSION);
//
//                if (metroMapFile.createNewFile())
//                    ready = true;
//                else
//                    ready = false;
//            } catch (IOException e) {
//                ready = false;
//            }
            // Close at the end
            hide();
        }
    }
    
    /**
     * Handles when the user selects a recent map to load
     */
    public void processRecentMap() {}
    
    //////////////////////////////
    // Accessor/Mutator Methods //
    //////////////////////////////

    public boolean isReady() {
        return ready;
    }

    public String getMapName() {
        return mapName;
    }
}