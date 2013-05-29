package com.johnzeringue.SVGToPDFConverter;

import java.awt.Color;
import java.util.Locale;

/**
 * A String to Color map for basic SVG color names
 * 
 * @author John Zeringue
 * @version 05/28/2013
 */
public class Colors {
    private static enum ColorValue {
        WHITE (new Color(255, 255, 255)),
        GRAY  (new Color(127, 127, 127)),
        RED   (new Color(255,   0,   0));
        
        private final Color color;
        ColorValue(Color c) {
            color = c;
        }
        public Color color() {
            return color;
        }
    }
    
    /**
     * Converts an SVG color name to a Color object containing the RGB data.
     * 
     * @param colorName the name of the color, per the SVG spec
     * @return the color as a Color object
     */
    public static Color get(String colorName) {
        colorName = colorName.toUpperCase(Locale.ENGLISH);
        return ColorValue.valueOf(colorName).color();
    }
    
    /**
     * Parses an RGB string (ex. "rgb(1,2,3)") into a Color object.
     * 
     * @param rgb an RGB string
     * @return the specified color as a Color object
     */
    public static Color parseRGB(String rgb) {
        System.out.println(rgb);
        
        // Perhaps there's a cleaner way to do this
        String[] rgbSplit = rgb.split("rgb\\(|, *|\\)");
        
        return new Color(Integer.parseInt(rgbSplit[1]),
                Integer.parseInt(rgbSplit[2]),
                Integer.parseInt(rgbSplit[3]));
    }
    
    public static void main(String[] args) {
        System.out.println(parseRGB("rgb(1,2, 3)"));
    }
}
