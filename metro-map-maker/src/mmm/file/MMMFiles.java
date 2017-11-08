/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.file;

import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import java.io.IOException;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonValue;
import mmm.data.DraggableImage;
import mmm.data.DraggableLabel;
import mmm.data.MetroLine;
import mmm.data.MetroStation;

/**
 *
 * @author steve
 */
public class MMMFiles implements AppFileComponent {
    
    @Override
    public void saveData(AppDataComponent data, String filePath) throws IOException {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void loadData(AppDataComponent data, String filePath) throws IOException {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
}
