package mmm.file;

import djf.AppTemplate;
import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import static djf.settings.AppPropertyType.NEW_ERROR_MESSAGE;
import static djf.settings.AppPropertyType.NEW_ERROR_TITLE;
import static djf.settings.AppPropertyType.NEW_COMPLETED_MESSAGE;
import static djf.settings.AppPropertyType.NEW_COMPLETED_TITLE;
import static mmm.MMMLanguageProperty.NAME_TAKEN_MESSAGE;
import static mmm.MMMLanguageProperty.NAME_TAKEN_TITLE;
import djf.ui.AppMessageDialogSingleton;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import mmm.data.DraggableImage;
import mmm.data.DraggableLabel;
import mmm.data.MMMData;
import mmm.data.MetroLine;
import mmm.data.MetroStation;
import properties_manager.PropertiesManager;
import static djf.settings.AppStartupConstants.PATH_WORK;

/**
 * Handles saving and loading projects in the Metro Map Maker.
 * @author Steven Maio
 */
public class MMMFiles implements AppFileComponent {
    // constants and other crap
    private static final String JSON_BACKGROUND = "background";
    private static final String JSON_RED = "red";
    private static final String JSON_GREEN = "green";
    private static final String JSON_BLUE = "blue";
    private static final String JSON_ALPHA = "alpha";
    private static final String JSON_X = "x";
    private static final String JSON_Y = "y";
    private static final String JSON_RADIUS = "radius";
    private static final String JSON_COLOR = "fill_color";
    private static final String JSON_FONT_FAMILY = "font_family";
    private static final String JSON_TEXT = "text";
    private static final String JSON_FONT_SIZE = "font_size";
    private static final String JSON_BOLD_FONT = "bold";
    private static final String JSON_ITALIC_FONT = "italic";
    private static final String JSON_FILE_PATH = "file_path";
    private static final String JSON_NAME = "name";
    private static final String JSON_LABEL_LOCATION = "label_location";
    private static final String JSON_LABEL_ROTATION = "label_rotation";
    private static final String JSON_TYPE = "type";
    private static final String JSON_START_LABEL = "start_label";
    private static final String JSON_END_LABEL = "end-label";
    private static final String JSON_LABEL = "label";
    private static final String METRO_STATION = "METRO_STATION";
    private static final String METRO_LINE = "METRO_LINE";
    private static final String DRAGGABLE_IMAGE = "DRAGGABLE_IMAGE";
    private static final String DRAGGABLE_LABEL = "DRAGGABLE_LABEL";
    public static final String FILE_EXTENSION = ".mmm";
    
    /**
     * This method saves the users work.
     * 
     * @param data The data management component for this application.
     * @param filePath The path to the file where the work is being saved.
     * @throws IOException Indicates there was en error writing out to the file.
     */
    @Override
    public void saveData(AppDataComponent data, String filePath) throws IOException {
        // Json builder and data Manager
        MMMData dataManager = (MMMData) data;
        String imageFilePath = dataManager.getImageFilepath();
        String name = dataManager.getName();
        JsonObject backgroundJson;
        
        // Background color
        if (imageFilePath == null) {
            Color bgColor = dataManager.getBackgroundColor();
            backgroundJson = makeJsonColorObject(bgColor);
        } else
            backgroundJson = Json.createObjectBuilder()
                    .add(JSON_FILE_PATH, imageFilePath).build();
        
        // TODO: This is for creating the shapes. This needs to be implemented
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        ObservableList<Node> shapes = dataManager.getShapes();
        
        
        JsonObject dataManagerJSO = Json.createObjectBuilder()
//                .add(JSON_NAME, name)
                .add(JSON_BACKGROUND, backgroundJson)
                .build();
        
        // TODO: Make it so that shapes are saved -- but this isn't important
        
        // Place all the jazz into a JSON file with nice formating
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(dataManagerJSO);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(dataManagerJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
    }

    @Override
    public void loadData(AppDataComponent data, String filePath) throws IOException {
        // Clear old data
        MMMData dataManager = (MMMData) data;
        dataManager.resetData();
        
        // Load the JSON file
        JsonObject json = loadJSONFile(filePath);
        
        Color backgroundColor = loadColor(json, JSON_BACKGROUND);
        dataManager.setBackgroundColor(backgroundColor);
    }

    @Override
    public void exportData(AppDataComponent data, String filePath) throws IOException {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void importData(AppDataComponent data, String filePath) throws IOException {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    // CreateNewFIle thing
    
    // Gets a double value of data from something
    private double getDataAsDouble(JsonObject json, String dataName) {
        JsonValue value = json.get(dataName);
        JsonNumber number = (JsonNumber)value;
        return number.bigDecimalValue().doubleValue();
    }
    
    // 
    private JsonObject saveMetroLine(MetroLine metroLine) {
        return null;
    }
    
    
    private JsonObject saveMetroStation(MetroStation metroStation) {
        return null;
    }
    
    private JsonObject saveDraggableLabel(DraggableLabel draggableLabel) {
        String fontFamily = draggableLabel.getFontFamily();
        int fontSize = draggableLabel.getFontSize();
        boolean italized = draggableLabel.isItalicized();
        boolean bold = draggableLabel.isBold();
        String text = draggableLabel.getText();
        JsonObject fillColor = makeJsonColorObject((Color) draggableLabel.getFill());
        double x = draggableLabel.getX();
        double y = draggableLabel.getY();
        
        JsonObject draggableLabelJson = Json.createObjectBuilder()
                .add(JSON_TYPE, DRAGGABLE_LABEL)
                .add(JSON_TEXT, text)
                .add(JSON_FONT_FAMILY, fontFamily)
                .add(JSON_FONT_SIZE, fontSize)
                .add(JSON_ITALIC_FONT, italized)
                .add(JSON_BOLD_FONT, bold)
                .add(JSON_X, x)
                .add(JSON_Y, y).build();
        
        return draggableLabelJson;
    }
    
    private JsonObject saveDraggableImage(DraggableImage draggableImage) {
        double x = draggableImage.getX();
        double y = draggableImage.getY();
        String imageFilePath = draggableImage.getImageFilepath();
        
        JsonObject draggableImageJson = Json.createObjectBuilder()
                .add(JSON_TYPE, DRAGGABLE_IMAGE)
                .add(JSON_X, x)
                .add(JSON_Y, y)
                .add(JSON_FILE_PATH, imageFilePath).build();
        
        return draggableImageJson;
    }
    
    // Creates a MetroLine object from a JsonObject
    private MetroLine loadMetroLine(JsonObject jsonMetroLine) {
        return null;
    }
    
    // Creates a MetroStation from a JsonObject
    private MetroStation loadMetroStation(JsonObject jsonMetroStation) {
        return null;
    }
    
    // Create a draggable label from a JsonObject
    private DraggableLabel loadDraggableLabel(JsonObject jsonDraggableLabel) {
        return null;
    }
    
    // Creates an instace of DraggableImage from a JsonObject
    private DraggableImage loadDraggableImage(JsonObject jsonDraggableImage) {
        return null;
    }
    
    private JsonObject makeJsonColorObject(Color color) {
	JsonObject colorJson = Json.createObjectBuilder()
		.add(JSON_RED, color.getRed())
		.add(JSON_GREEN, color.getGreen())
		.add(JSON_BLUE, color.getBlue())
		.add(JSON_ALPHA, color.getOpacity()).build();
	return colorJson;
    }
    
    // HELPER METHOD FOR LOADING DATA FROM A JSON FORMAT
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
	InputStream is = new FileInputStream(jsonFilePath);
	JsonReader jsonReader = Json.createReader(is);
	JsonObject json = jsonReader.readObject();
	jsonReader.close();
	is.close();
	return json;
    }
    
    /**
     * This method will create the default map
     */
    public void createNewMetroMap(String mapName, AppTemplate app) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        MMMData dataManager = (MMMData) app.getDataComponent();
        AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
        
        try {
            String filePath = PATH_WORK + mapName + FILE_EXTENSION;
            File selectedFile = new File(filePath);
            
            if (selectedFile.createNewFile()) {
                // RESET THE WORKSPACE
                app.getWorkspaceComponent().resetWorkspace();

                // RESET THE DATA
                app.getDataComponent().resetData();

//                // LOAD THE FILE INTO THE DATA
//                app.getFileComponent().loadData(app.getDataComponent(), selectedFile.getAbsolutePath());

                // MAKE SURE THE WORKSPACE IS ACTIVATED
                app.getWorkspaceComponent().activateWorkspace(app.getGUI().getAppPane());

                // AND MAKE SURE TH E FILE BUTTONS ARE PROPERLY ENABLED
                app.getGUI().updateToolbarControls(true);

                // This way we won't have to reselect it
                app.getGUI().getFileController().setCurrentWorkFile(selectedFile);
                dataManager.setBackgroundColor(MMMData.DEFAULT_BACKGROUND_COLOR);
                dataManager.setName(mapName);
                
                // Save the map so it can be loaded again and then display a
                // success message
                saveData(dataManager, filePath);
                dialog.show(props.getProperty(NEW_COMPLETED_TITLE), 
                        props.getProperty(NEW_COMPLETED_MESSAGE));
            } 
            // Name is already taken maybe
            else {
                dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(NAME_TAKEN_TITLE), props.getProperty(NAME_TAKEN_MESSAGE));
            }
        } catch (Exception e) {
                dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(NEW_ERROR_TITLE), props.getProperty(NEW_ERROR_MESSAGE));
        }
    }
    
    private Color loadColor(JsonObject json, String colorToGet) {
	JsonObject jsonColor = json.getJsonObject(colorToGet);
	double red = getDataAsDouble(jsonColor, JSON_RED);
	double green = getDataAsDouble(jsonColor, JSON_GREEN);
	double blue = getDataAsDouble(jsonColor, JSON_BLUE);
	double alpha = getDataAsDouble(jsonColor, JSON_ALPHA);
	Color loadedColor = new Color(red, green, blue, alpha);
	return loadedColor;
    }
}
