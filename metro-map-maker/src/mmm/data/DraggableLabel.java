package mmm.data;

import javafx.scene.text.Text;
import static mmm.css.MMMStyle.STYLE_BOLD_FONT;
import static mmm.css.MMMStyle.STYLE_FONT_FAMILY;
import static mmm.css.MMMStyle.STYLE_FONT_SIZE;
import static mmm.css.MMMStyle.STYLE_ITALICIZED_FONT;

/**
 * This class represents a label of text that can be dragged across the canvas.
 * Supports some basic styling options, such as font family, font size, bold,
 * italicized font, and custom fill colors.
 * 
 * @author Steven Maio
 */
public class DraggableLabel extends Text implements Draggable {
    // Default Style properties
    private final boolean DEFAULT_BOLD = false;
    private final boolean DEFAULT_ITALIC = false;
    private final String DEFAULT_FONT_FAMILY = "Calibri";
    private final int DEFAULT_FONT_SIZE = 14;
    
    // style properties
    private String fontFamily;
    private int fontSize;
    private boolean italicized;
    private boolean bold;
    
    // Location properties
    private double startX;
    private double startY;
    
    /**
     * Default constructor for an instance of DraggableLabel. Sets the values
     * of all the style properties to their default values.
     */
    public DraggableLabel(String text) {
        setText(text);
        fontFamily = DEFAULT_FONT_FAMILY;
        fontSize = DEFAULT_FONT_SIZE;
        bold = DEFAULT_BOLD;
        italicized = DEFAULT_ITALIC;
        
        resetStyle();
    }
    
    /**
     * This method is called when an instance of DraggableLabel is about to 
     * moved. This method helps keep the DraggableLabel's position consistent
     * with respect to the cursor
     * 
     * @param x
     *      The X-Coordinate of the mouse
     * 
     * @param y 
     *      The Y-Coordinate of the mouse
     */
    @Override
    public void start(int x, int y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * This method will handle the process of moving the instance of 
     * DraggableLabel
     * 
     * @param x
     *      The X-Coordinate of the mouse drag event.
     * 
     * @param y 
     *      The Y-Coordinaate of the mosue drag event.
     */
    @Override
    public void drag(int x, int y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * This method will be called to indicate when an instance of 
     * DraggableLabel is selected.
     */
    @Override
    public void highlight() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * This method will refresh the style of the current instance of 
     * DraggableLabel
     */
    public void resetStyle() {
       String style = getStyleString();
        setStyle(style);
    }
    
    /**
     * Returns a string with the style of the label
     * 
     * @return 
     *      The current style of the instance of DraggableLabel
     */
    public String getStyleString() {
        String style = String.format("%s; %s; %s%d; %s%s;",
               (italicized) ? STYLE_ITALICIZED_FONT : "",
               (bold) ? STYLE_BOLD_FONT : "",
               STYLE_FONT_SIZE, fontSize,
               STYLE_FONT_FAMILY, fontFamily);
        
        return style;
    }
    
    //////////////////////////////
    // ACCESSOR/MUTATOR METHODS //
    //////////////////////////////

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public boolean isItalicized() {
        return italicized;
    }

    public void setItalicized(boolean italicized) {
        this.italicized = italicized;
    }

    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    @Override
    public void unhighlight() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
