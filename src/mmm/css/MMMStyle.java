/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.css;

/**
 *
 * @author steve
 */
public class MMMStyle {
    public static final String STYLE_ITALICIZED_FONT = "-fx-font-style: italic";
    public static final String STYLE_BOLD_FONT = "-fx-font-weight: bold";
    public static final String STYLE_FONT_SIZE = "-fx-font-size:";
    public static final String STYLE_FONT_FAMILY = "-fx-font-family:";
    public static final String CLASS_MAX_PANE = "max_pane";
    public static final String CLASS_RENDER_CANVAS = "render_canvas";
    public static final String CLASS_BUTTON = "button";
    public static final String CLASS_EDIT_TOOLBAR = "edit_toolbar";
    public static final String CLASS_EDIT_TOOLBAR_ROW = "edit_toolbar_row";
    public static final String CLASS_EDIT_TOOLBAR_SUB_ROW = "edit_toolbar_sub_row";
    public static final String CLASS_COLOR_CHOOSER_PANE = "color_chooser_pane";
    public static final String CLASS_COLOR_CHOOSER_CONTROL = "color_chooser_control";
    public static final String CLASS_BORDERED_PANE = "bordered_pane";
    public static final String CLASS_COLOR_PICKER = "color_picker";
    public static final String CLASS_WINDOW_PANE = "external_pane";
    
    // Font Sizes available
    public static final int[] FONT_SIZES = {
        6, 8, 9, 10, 12, 
        14, 16, 18, 20, 24,
        28, 32, 40, 48, 56,
        64, 72
    };
    
    // Font families available
    public static final String[] FONT_FAMILIES = {
        "Calibri", "Cambria", "Chiller", "Times New Roman", "Arial",
        "Algerian", "Century Gothic", "Papyrus", "System", "Serif",
    };
}