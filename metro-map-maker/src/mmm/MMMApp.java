/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm;
import djf.AppTemplate;
import static djf.settings.AppPropertyType.APP_TITLE;
import static djf.settings.AppPropertyType.PROPERTIES_LOAD_ERROR_MESSAGE;
import static djf.settings.AppPropertyType.PROPERTIES_LOAD_ERROR_TITLE;
import static djf.settings.AppStartupConstants.APP_PROPERTIES_FILE_NAME;
import static djf.settings.AppStartupConstants.PATH_WORK;
import djf.ui.AppGUI;
import djf.ui.AppMessageDialogSingleton;
import djf.ui.AppYesNoCancelDialogSingleton;
import java.io.File;
import java.util.Locale;
import javafx.stage.Stage;
import mmm.data.MMMData;
import mmm.file.MMMFiles;
import static mmm.file.MMMFiles.FILE_EXTENSION;
import mmm.gui.BorderedMessageDialogSingleton;
import mmm.gui.EnterTextDialogSingleton;
import mmm.gui.InitImageWindow;
import mmm.gui.MMMWorkspace;
import mmm.gui.MetroLineSettingsDialogSingleton;
import mmm.gui.WelcomeDialogSingleton;
import properties_manager.PropertiesManager;

/**
 * 
 * @author steve
 */
public class MMMApp extends AppTemplate {
    /**
     * This method is where the execution begins. This method calls launch 
     * since it is a JavaFX application.
     */
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        launch(args);
    }

    /**
     * This method initializes all three of the application components: the
     * workspace component, the data component, and the file component.
     */
    @Override
    public void buildAppComponentsHook() {
        fileComponent = new MMMFiles();
        dataComponent = new MMMData(this);
        workspaceComponent = new MMMWorkspace(this);
    }
    
    /**
     * This initializes the application.
     *
     * @param primaryStage This application's window.
     */
    @Override
    public void start(Stage primaryStage) {
	// LET'S START BY INITIALIZING OUR DIALOGS
	AppMessageDialogSingleton messageDialog = AppMessageDialogSingleton.getSingleton();
	messageDialog.init(primaryStage);
	AppYesNoCancelDialogSingleton yesNoDialog = AppYesNoCancelDialogSingleton.getSingleton();
	yesNoDialog.init(primaryStage);
	PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        // App specific dialogs
        WelcomeDialogSingleton welcomeDialog;
        welcomeDialog = WelcomeDialogSingleton.getSingleton();
        welcomeDialog.init(primaryStage);
        
        MetroLineSettingsDialogSingleton metroLineSettingsDialog;
        metroLineSettingsDialog = MetroLineSettingsDialogSingleton.getSingleton();
        metroLineSettingsDialog.init(primaryStage);
        
        EnterTextDialogSingleton enterTextDialog;
        enterTextDialog = EnterTextDialogSingleton.getSingleton();
        enterTextDialog.init(primaryStage);
        
        BorderedMessageDialogSingleton borderedMessageDialog;
        borderedMessageDialog = BorderedMessageDialogSingleton.getSingleton();
        borderedMessageDialog.init(primaryStage);
        
        InitImageWindow imageWindow;
        imageWindow = InitImageWindow.getSingleton();
        imageWindow.init(primaryStage);
        
        imageWindow.showAndWait();
        
	try {
	    // LOAD APP PROPERTIES, BOTH THE BASIC UI STUFF FOR THE FRAMEWORK
	    // AND THE CUSTOM UI STUFF FOR THE WORKSPACE
            boolean success = loadProperties(APP_PROPERTIES_FILE_NAME);
	    
	    if (success) {
                
                // GET THE TITLE FROM THE XML FILE
		String appTitle = props.getProperty(APP_TITLE);
                
                // BUILD THE BASIC APP GUI OBJECT FIRST
		gui = new AppGUI(primaryStage, appTitle, this);

                // THIS BUILDS ALL OF THE COMPONENTS, NOTE THAT
                // IT WOULD BE DEFINED IN AN APPLICATION-SPECIFIC
                // CHILD CLASS
		buildAppComponentsHook();
                
                // Show the welcome dialog, we'll process some crap 
                // after this i think
                welcomeDialog.loadRecentMaps();
                welcomeDialog.showAndWait();
                
                try {
                    // If the user created a map
                    if (welcomeDialog.isReady()) {
                        String mapName = welcomeDialog.getMapName();

                        // If the user didn't select load recent map
                        if (! welcomeDialog.getLoadRecentMap()) {
                            MMMFiles files = (MMMFiles) fileComponent;
                            files.createNewMetroMap(mapName, this);
                        } else {
                            // Load all of the stuff
                            String filepath = PATH_WORK + mapName + FILE_EXTENSION;
                            MMMFiles files = (MMMFiles) fileComponent;

                            workspaceComponent.resetWorkspace();
                            dataComponent.resetData();

                            files.loadData(dataComponent, filepath);
                            workspaceComponent.activateWorkspace(gui.getAppPane());

                            gui.getFileController().setSaved(true);
                            gui.updateToolbarControls(gui.getFileController().isSaved());

                            File file = new File(filepath);

                            gui.getFileController().setCurrentWorkFile(file);
                        }
                    }
                } catch (Exception e) {
                    // Display a message if an error happens while loading a file
                    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                    dialog.show(props.getProperty(PROPERTIES_LOAD_ERROR_TITLE), props.getProperty(PROPERTIES_LOAD_ERROR_MESSAGE));
                }
                
                // NOW OPEN UP THE WINDOW
                primaryStage.show();
	    } 
	}catch (Exception e) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(PROPERTIES_LOAD_ERROR_TITLE), props.getProperty(PROPERTIES_LOAD_ERROR_MESSAGE));
	}
    }
}
