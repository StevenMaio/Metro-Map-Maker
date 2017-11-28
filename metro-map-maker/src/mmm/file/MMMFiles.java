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
import static mmm.MMMLanguageProperty.RECENT_FILE_1;
import static mmm.MMMLanguageProperty.RECENT_FILE_2;
import static mmm.MMMLanguageProperty.RECENT_FILE_3;
import static mmm.MMMLanguageProperty.RECENT_FILE_4;
import static mmm.MMMLanguageProperty.RECENT_FILE_5;
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
import javax.json.JsonArray;
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
import java.util.Hashtable;
import java.util.Scanner;
import javafx.scene.shape.Shape;
import mmm.data.DraggableCircle;
import mmm.data.MMMState;

/**
 * Handles saving and loading projects in the Metro Map Maker.
 * @author Steven Maio
 */
public class MMMFiles implements AppFileComponent {
    // constants and other crap
    private static final String JSON_BACKGROUND = "background";
    private static final String JSON_BACKGROUND_IMAGE = "background_inage";
    private static final String JSON_IMAGE = "image";
    private static final String JSON_SHAPES = "shapes";
    private static final String JSON_RED = "red";
    private static final String JSON_GREEN = "green";
    private static final String JSON_BLUE = "blue";
    private static final String JSON_ALPHA = "alpha";
    private static final String JSON_HEIGHT = "height";
    private static final String JSON_WIDTH = "width";
    private static final String JSON_X = "x";
    private static final String JSON_Y = "y";
    private static final String JSON_STOPS = "stops";
    private static final String JSON_RADIUS = "radius";
    private static final String JSON_COLOR = "fill_color";
    private static final String JSON_COLOR_2 = "color";
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
    private static final String JSON_THICKNESS = "thickness";
    private static final String JSON_METRO_STATIONS = "stations";
    private static final String JSON_METRO_LINES = "lines";
    private static final String METRO_STATION = "METRO_STATION";
    private static final String METRO_LINE = "METRO_LINE";
    private static final String JSON_CIRCLE = "circle";
    private static final String JSON_CIRCULAR = "circular";
    private static final String JSON_STATION_NAMES = "station_names";
    public static final String FILE_EXTENSION = ".mmm";
    public static final String METRO_EXTENSION = " Metro";
    
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
        int height = dataManager.getHeight();
        int width = dataManager.getWidth();
        JsonObject backgroundJson;
        
        // Background color
        if (imageFilePath == null) {
            Color bgColor = dataManager.getBackgroundColor();
            backgroundJson = makeJsonColorObject(bgColor);
        } else
            backgroundJson = Json.createObjectBuilder()
                    .add(JSON_FILE_PATH, imageFilePath).build();
        
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        ObservableList<Node> shapes = dataManager.getShapes();
        
        // TODO: First we do the metro lines
        for (MetroLine metroLine: dataManager.getMetroLines())
            arrayBuilder.add(saveMetroLine(metroLine));
        
        JsonArray metroLinesJson = arrayBuilder.build();
        arrayBuilder = Json.createArrayBuilder(); // clear the array builder
        
        // Now get Json metro stations
        for (MetroStation metroStation: dataManager.getMetroStations())
            arrayBuilder.add(saveMetroStation(metroStation));
        
        JsonArray metroStationsJson = arrayBuilder.build();
        
        arrayBuilder = Json.createArrayBuilder(); // clear array builder
        
        // now we iterate through shapes to save labels and text
        for (Node e: shapes) {
            if (e instanceof DraggableLabel) {
                if (((DraggableLabel) e).isIndependent())
                    arrayBuilder.add(saveDraggableLabel((DraggableLabel) e));
            } else if (e instanceof DraggableImage)
                arrayBuilder.add(saveDraggableImage((DraggableImage) e));
        }
        
        JsonArray shapesJson = arrayBuilder.build();
        
        JsonObject dataManagerJSO = Json.createObjectBuilder()
                .add(JSON_BACKGROUND, backgroundJson)
                .add(JSON_NAME, name)
                .add(JSON_HEIGHT, height)
                .add(JSON_WIDTH, width)
                .add(JSON_METRO_STATIONS, metroStationsJson)
                .add(JSON_METRO_LINES, metroLinesJson)
                .add(JSON_SHAPES, shapesJson)
                .build();
        
        
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
        ObservableList<Node> shapes = dataManager.getShapes();
        Hashtable<String, MetroStation> metroStationHash = new Hashtable<>();
        
        // Load the JSON file
        JsonObject json = loadJSONFile(filePath);
        
        Color backgroundColor = loadColor(json.getJsonObject(JSON_BACKGROUND));
        dataManager.setBackgroundColor(backgroundColor);
        String name = json.getString(JSON_NAME);
        dataManager.setName(name);
        
        int height = json.getInt(JSON_HEIGHT);
        int width = json.getInt(JSON_WIDTH);
        
        dataManager.setHeight(height);
        dataManager.setWidth(width);
        
        // Load the Metro Stations
        JsonArray jsonMetroStationsArray = json.getJsonArray(JSON_METRO_STATIONS);
        for (int i = 0; i < jsonMetroStationsArray.size(); i++) {
            MetroStation metroStation = loadMetroStation(jsonMetroStationsArray.getJsonObject(i));
            
            metroStationHash.put(metroStation.getName(), metroStation);
            shapes.add(metroStation.getStationCircle());
            shapes.add(metroStation.getStationLabel());
            dataManager.getMetroStations().add(metroStation);
        }
        
        // Load MetroLines
        JsonArray jsonMetroLinesArray = json.getJsonArray(JSON_METRO_LINES);
        for (int i = 0; i < jsonMetroLinesArray.size(); i++) {
            JsonObject metroLineJson = jsonMetroLinesArray.getJsonObject(i);
            Scanner stops = new Scanner(metroLineJson.getString(JSON_STOPS));
            
            MetroLine metroLine = loadMetroLine(metroLineJson);
            
            while (stops.hasNextLine()) {
                MetroStation nextStop = metroStationHash.get(stops.nextLine());
                metroLine.add(nextStop);
                nextStop.getMetroLines().add(metroLine);
            }
            
            dataManager.getMetroLines().add(metroLine);
            shapes.add(metroLine.getStartLabel());
            shapes.add(metroLine.getEndLabel());
            metroLine.resetLine(dataManager);
            shapes.addAll(metroLine.getLines());
        }
        
        JsonArray jsonShapesArray = json.getJsonArray(JSON_SHAPES);
        if (jsonShapesArray == null);   // THIS LETS US LOAD OLD FILES
        else 
            for (int i = 0; i < jsonShapesArray.size(); i++) {
                JsonObject shapeJson = jsonShapesArray.getJsonObject(i);

                String type = shapeJson.getString(JSON_TYPE);
                Shape shape = null;

                if (type.equals(JSON_LABEL)) {
                    shape = loadDraggableLabel(shapeJson);
                    ((DraggableLabel) shape).setIndependent(true);
                } else if (type.equals(JSON_IMAGE)) {
                    shape = loadDraggableImage(shapeJson);
                }

                shapes.add(shape);
        }
        
        // Put the currentmap in the title
        resetRecentMaps(name);
        dataManager.refreshBackground();
        dataManager.setState(MMMState.SELECTING_SHAPE);
    }

    /**
     * TODO: This needs to be changed to match the format in the HW handout.
     * @param data
     * @param filePath
     * @throws IOException 
     */
    @Override
    public void exportData(AppDataComponent data, String filePath) throws IOException {
        // Json builder and data Manager
        MMMData dataManager = (MMMData) data;
        String name = dataManager.getName();
        JsonObject backgroundJson;
        
        // TODO: Background stuff
        
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        ObservableList<Node> shapes = dataManager.getShapes();
        
        // Create the JsonArray for Metro Lines
        for (MetroLine metroLine: dataManager.getMetroLines()){
            JsonObject color = makeJsonColorObject(metroLine.getColor());
            String metroLineName = metroLine.getName();
            
            JsonArrayBuilder stopsArrayJson = Json.createArrayBuilder();
            Scanner stopsInput = new Scanner(metroLine.getLineDestinations());
            
            while(stopsInput.hasNextLine())
                stopsArrayJson.add(stopsInput.nextLine());
            
            // TODO: Circular eventually
            JsonObject metroLineJson = Json.createObjectBuilder()
                    .add(JSON_NAME, metroLineName)
                    .add(JSON_CIRCULAR, false)
                    .add(JSON_COLOR_2, color)
                    .add(JSON_STATION_NAMES, stopsArrayJson.build())
                    .build();
            
            arrayBuilder.add(metroLineJson);
        }
        
        JsonArray metroLinesJson = arrayBuilder.build();
        arrayBuilder = Json.createArrayBuilder(); // clear the array builder
        
        // Now get JsonArray for Metro Stations
        for (MetroStation metroStation: dataManager.getMetroStations()) {
            String metroStationName = metroStation.getName();
            int x = (int) metroStation.getStationCircle().getX();
            int y = (int) metroStation.getStationCircle().getY();
            
            JsonObject metroStationJson = Json.createObjectBuilder()
                    .add(JSON_NAME, metroStationName)
                    .add(JSON_X, x)
                    .add(JSON_Y, y)
                    .build();
            
            arrayBuilder.add(metroStationJson);
        }
        
        JsonArray metroStationsJson = arrayBuilder.build();
        
        // Put everything together into one Json object
        JsonObject dataManagerJSO = Json.createObjectBuilder()
                .add(JSON_NAME, name)
                .add(JSON_METRO_LINES, metroLinesJson)
                .add(JSON_METRO_STATIONS, metroStationsJson)
                .build();
        
        
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
        // Get all the properties we're saving
        JsonObject colorJson = makeJsonColorObject(metroLine.getColor());
        JsonObject startLabelJson = saveDraggableLabel(metroLine.getStartLabel());
        JsonObject endLabelJson = saveDraggableLabel(metroLine.getEndLabel());
        String name = metroLine.getName();
        String stops = metroLine.getLineDestinations();
        double thickness = metroLine.getLineThickness();
        
        // Put them into a Json object
        JsonObject metroLineJson = Json.createObjectBuilder()
                .add(JSON_NAME, name)
                .add(JSON_COLOR, colorJson)
                .add(JSON_THICKNESS, thickness)
                .add(JSON_STOPS, stops)
                .add(JSON_START_LABEL, startLabelJson)
                .add(JSON_END_LABEL, endLabelJson).build();
        
        return metroLineJson;
    }
    
    
    private JsonObject saveMetroStation(MetroStation metroStation) {
        JsonObject circleJson = saveDraggableCircle(metroStation.getStationCircle());
        JsonObject labelJson = saveDraggableLabel(metroStation.getStationLabel());
        
        String name = metroStation.getName();
        int labelLocation = metroStation.getLabelLocation();
        int labelRotation = metroStation.getLabelRotation();
        
        JsonObject stationJson = Json.createObjectBuilder()
                .add(JSON_NAME, name)
                .add(JSON_LABEL, labelJson)
                .add(JSON_CIRCLE, circleJson)
                .add(JSON_LABEL_LOCATION, labelLocation)
                .add(JSON_LABEL_ROTATION, labelRotation).build();
        
        return stationJson;
    }
    
    private JsonObject saveDraggableCircle(DraggableCircle circle) {
        JsonObject fillColor = makeJsonColorObject((Color) circle.getStroke());
        double radius = circle.getRadius();
        double x = circle.getX();
        double y = circle.getY();
        
        JsonObject circleJson = Json.createObjectBuilder()
                .add(JSON_RADIUS, radius)
                .add(JSON_COLOR, fillColor)
                .add(JSON_X, x)
                .add(JSON_Y, y).build();
        
        return circleJson;
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
                .add(JSON_TYPE, JSON_LABEL)
                .add(JSON_TEXT, text)
                .add(JSON_FONT_FAMILY, fontFamily)
                .add(JSON_FONT_SIZE, fontSize)
                .add(JSON_ITALIC_FONT, italized)
                .add(JSON_BOLD_FONT, bold)
                .add(JSON_COLOR, fillColor)
                .add(JSON_X, x)
                .add(JSON_Y, y).build();
        
        return draggableLabelJson;
    }
    
    private JsonObject saveDraggableImage(DraggableImage draggableImage) {
        double x = draggableImage.getX();
        double y = draggableImage.getY();
        String imageFilePath = draggableImage.getImageFilepath();
        
        JsonObject draggableImageJson = Json.createObjectBuilder()
                .add(JSON_TYPE, JSON_IMAGE)
                .add(JSON_X, x)
                .add(JSON_Y, y)
                .add(JSON_FILE_PATH, imageFilePath).build();
        
        return draggableImageJson;
    }
    
    // Creates a MetroLine object from a JsonObject everything except for the stops
    private MetroLine loadMetroLine(JsonObject jsonMetroLine) {
        String name = jsonMetroLine.getString(JSON_NAME);
        Color color = loadColor(jsonMetroLine.getJsonObject(JSON_COLOR));
        double thickness = getDataAsDouble(jsonMetroLine, JSON_THICKNESS);
        DraggableLabel startLabel = loadDraggableLabel(jsonMetroLine.getJsonObject(JSON_START_LABEL));
        DraggableLabel endLabel = loadDraggableLabel(jsonMetroLine.getJsonObject(JSON_END_LABEL));
        
        MetroLine metroLine = new MetroLine();
        metroLine.setName(name);
        metroLine.setColor(color);
        metroLine.setLineThickness(thickness);
        metroLine.setStartLabel(startLabel);
        metroLine.setEndLabel(endLabel);
        
        return metroLine;
    }
    
    // Creates a MetroStation from a JsonObject and binds everything appropriatesly
    private MetroStation loadMetroStation(JsonObject jsonMetroStation) {
        MetroStation metroStation = new MetroStation();
        String name = jsonMetroStation.getString(JSON_NAME);
        DraggableLabel label = 
                loadDraggableLabel(jsonMetroStation.getJsonObject(JSON_LABEL));
        DraggableCircle circle =
                loadDraggableCircle(jsonMetroStation.getJsonObject(JSON_CIRCLE));
        
        int labelLocation = jsonMetroStation.getInt(JSON_LABEL_LOCATION);
        int labelRotation = jsonMetroStation.getInt(JSON_LABEL_ROTATION);
        
        label.setDisable(true);
        metroStation.setLabelRotation(labelRotation);
        metroStation.setLabelLocation(labelLocation);
        metroStation.setName(name);
        metroStation.setStationCircle(circle);
        metroStation.setStationLabel(label);
        metroStation.refreshRotation();
        metroStation.bindLabelToCircle();
        circle.setMetroStation(metroStation);
        
        return metroStation;
    }
    
    // Create a draggable label from a JsonObject
    private DraggableLabel loadDraggableLabel(JsonObject jsonDraggableLabel) {
        String text = jsonDraggableLabel.getString(JSON_TEXT);
        boolean bold = jsonDraggableLabel.getBoolean(JSON_BOLD_FONT);
        boolean italic = jsonDraggableLabel.getBoolean(JSON_ITALIC_FONT);
        int fontSize = jsonDraggableLabel.getInt(JSON_FONT_SIZE);
        String fontFamily = jsonDraggableLabel.getString(JSON_FONT_FAMILY);
        int x = jsonDraggableLabel.getInt(JSON_X);
        int y = jsonDraggableLabel.getInt(JSON_Y);
        
        DraggableLabel label = new DraggableLabel(text);
        label.setX(x);
        label.setY(y);
        label.setFontFamily(fontFamily);
        label.setText(text);
        label.setFontSize(fontSize);
        label.setBold(bold);
        label.setItalicized(italic);
        label.resetStyle();
        
        return label;
    }
    
    // Creates an instace of DraggableImage from a JsonObject
    private DraggableImage loadDraggableImage(JsonObject jsonDraggableImage) {
        DraggableImage image = new DraggableImage();
        int x = jsonDraggableImage.getInt(JSON_X);
        int y = jsonDraggableImage.getInt(JSON_Y);
        String filePath = jsonDraggableImage.getString(JSON_FILE_PATH);
        
        image.setX(x);
        image.setY(y);
        image.setImageFilepath(filePath);
        image.refreshImage();
        
        return image;
    }
    
    private DraggableCircle loadDraggableCircle(JsonObject jsonCircle) {
        double radius = getDataAsDouble(jsonCircle, JSON_RADIUS);
        int x = jsonCircle.getInt(JSON_X);
        int y = jsonCircle.getInt(JSON_Y);
        Color fill = loadColor(jsonCircle.getJsonObject(JSON_COLOR));
        
        DraggableCircle circle = new DraggableCircle();
        circle.setX(x);
        circle.setY(y);
        circle.setRadius(radius);
        circle.setStroke(fill);
        
        return circle;
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
                
                resetRecentMaps(mapName);
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
    
    private Color loadColor(JsonObject json) {
	double red = getDataAsDouble(json, JSON_RED);
	double green = getDataAsDouble(json, JSON_GREEN);
	double blue = getDataAsDouble(json, JSON_BLUE);
	double alpha = getDataAsDouble(json, JSON_ALPHA);
	Color loadedColor = new Color(red, green, blue, alpha);
	return loadedColor;
    }
    
    // The method will do something to reset the loaded files
    private void resetRecentMaps(String fileName) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        // get recent maps
        String[] recentMaps = new String[5];
        recentMaps[0] = props.getProperty(RECENT_FILE_1);
        recentMaps[1] = props.getProperty(RECENT_FILE_2);
        recentMaps[2] = props.getProperty(RECENT_FILE_3);
        recentMaps[3] = props.getProperty(RECENT_FILE_4);
        recentMaps[4] = props.getProperty(RECENT_FILE_5);
        
        int currentIndex = 2;
        props.setProperty(RECENT_FILE_1, fileName);
        
        for (int i = 0; i < 5; i++) {
            if (currentIndex > 5)
                return;
            
            if (! fileName.equals(recentMaps[i]))
                props.setProperty("RECENT_FILE_" + currentIndex++, recentMaps[i]);
        }
    }
}