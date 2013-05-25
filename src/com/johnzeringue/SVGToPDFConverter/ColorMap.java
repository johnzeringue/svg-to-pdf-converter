package com.johnzeringue.SVGToPDFConverter;

import java.awt.Color;
import java.util.Locale;

/**
 *
 * @author John
 */
public class ColorMap {
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
    
    public static Color get(String colorName) {
        colorName = colorName.toUpperCase(Locale.ENGLISH);
        return ColorValue.valueOf(colorName).color();
    }
    
    public static void main(String[] args) {
        System.out.println("Gray: " + ColorMap.get("gray"));
        System.out.println("Red: " + ColorMap.get("red"));
    }
}
