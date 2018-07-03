package mmm.gui.dialog;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;

import static djf.settings.AppStartupConstants.PATH_IMAGES;
import static djf.settings.AppStartupConstants.FILE_PROTOCOL;
import static mmm.MMMLanguageProperty.*;

/**
 *
 * @author steve
 */
public class WelcomeDialogSingleton extends Stage {

    // Singleton properties
    private static WelcomeDialogSingleton singleton;
    private static final double PREFERRED_HEIGHT = 320;
    private static final double PREFERRED_WIDTH = 500;
    private static final double LOGO_SCALE = 0.75;

    private ListView<String> recentMapsListView;
    private boolean ready;
    private boolean loadRecentMap;
    private String mapName;

    /**
     * Returns the WelcomeDialogSingleton singleton and initializes it if it
     * hasn't been initialized.
     *
     * @return The WelcomeDialogSingleton singleton
     */
    public static WelcomeDialogSingleton getSingleton() {
        if (singleton == null) {
            singleton = new WelcomeDialogSingleton();
        }

        return singleton;
    }

    private WelcomeDialogSingleton() {
    }

    /**
     * This method initializes the window and it's contents.
     *
     * @param primaryStage The owner of this stage.
     */
    public void init(Stage primaryStage) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        String appLogoPath  = props.getProperty(APP_LOGO);
        String newMetroMap = props.getProperty(NEW_METRO_MAP_TITLE);
        String recentMaps = props.getProperty(RECENT_MAPS);
        String welcomeMessage = props.getProperty(WELCOME_MESSAGE);

        // Make it model
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
        ready = false;

        // Get the logo
        String logoFilepath = FILE_PROTOCOL + PATH_IMAGES
                + appLogoPath;
        ImageView appLogo = new ImageView(logoFilepath);
        appLogo.setScaleX(LOGO_SCALE);
        appLogo.setScaleY(LOGO_SCALE);

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
        // TODO: THESE NEED TO BE CHANGED LATER TO LOAD FROM CSS
        Button newMetroMapButton = new Button(newMetroMap);
        centerPane.setAlignment(Pos.CENTER);
        centerPane.setSpacing(50);
        centerPane.getChildren().addAll(appLogo, newMetroMapButton);

        // Left Pane content and settings
        // TODO: THIS NEEDS TO BE CHANGED LATER TO LOAD FROM CSS
        Label rightPaneLabel = new Label(recentMaps);
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
        setTitle(welcomeMessage);
        Scene scene = new Scene(mainPane);
        this.setScene(scene);
    }

    /**
     * Handles when the user presses the New Metro Map button.
     */
    private void processNewMap() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String newMetroMapTitle = props.getProperty(NEW_METRO_MAP_TITLE);
        String newMetroMapMessage = props.getProperty(NEW_METRO_MAP_MESSAGE);

        EnterTextDialogSingleton enterTextDialog
                = EnterTextDialogSingleton.getSingleton();

        enterTextDialog.show(newMetroMapTitle, newMetroMapMessage);

        // Determine if the name is valid
        if (enterTextDialog.isReady()) {
            mapName = enterTextDialog.getText();

            // if the text is empty do nothing, otherwise
            if (mapName.equals("")) {
                return;
            }

            ready = true;
            hide();
        }
    }

    /**
     * Handles when the user selects a recent map to load
     */
    private void processRecentMap() {
        mapName = recentMapsListView.getSelectionModel().getSelectedItem();

        // If mapName isn't null, then do some stuff
        if (mapName != null) {
            ready = true;
            loadRecentMap = true;

            hide();
        }
    }

    /**
     * This method loads the last 5 maps that were edited.
     */
    public void loadRecentMaps() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        // get recent maps
        String[] recentMaps = new String[5];
        recentMaps[0] = props.getProperty(RECENT_FILE_1);
        recentMaps[1] = props.getProperty(RECENT_FILE_2);
        recentMaps[2] = props.getProperty(RECENT_FILE_3);
        recentMaps[3] = props.getProperty(RECENT_FILE_4);
        recentMaps[4] = props.getProperty(RECENT_FILE_5);

        // Add nonempty and nonnull Strings to the listView
        for (String e : recentMaps) {
            if (e == null || "".equals(e)); 
            else {
                recentMapsListView.getItems().add(e);
            }
        }
    }

    //////////////////////////////
    // Accessor/Mutator Methods //
    //////////////////////////////
    public boolean isReady() {
        return this.ready;
    }

    public String getMapName() {
        return this.mapName;
    }

    public boolean getLoadRecentMap() {
        return this.loadRecentMap;
    }
}
