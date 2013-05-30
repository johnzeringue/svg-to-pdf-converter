package com.johnzeringue.SVGToPDFConverter;

import java.util.ArrayList;

/**
 * A singleton class for holding font information as it moves from the SVG to
 * the PDF.
 * 
 * @author  John Zeringue
 */
public class DocumentFonts {
    private static DocumentFonts instance;
    
    private ArrayList<String> fonts;
    
    public DocumentFonts() {
        fonts = new ArrayList<>();
    }
    
    public int getFontNumber(String font) {
        int index = fonts.indexOf(font);
        
        if (index != -1) {
            return index;
        } else {
            fonts.add(font);
            return fonts.size() - 1;
        }
    }
    
    public ArrayList<String> getFonts() {
        return fonts;
    }
    
    /**
     * Returns this class's only instance, creating one if it did not yet exist.
     * 
     * @return 
     */
    public static DocumentFonts getInstance() {
        // If the instance of this class doesn't exist, create it.
        if (instance == null) {
            instance = new DocumentFonts();
        }

        return instance;
    }

    /**
     * Returns a new instance of this class, replacing the old one if present.
     * 
     * @return 
     */
    public static DocumentFonts getNewInstance() {
        // Create a new instance and return it
        instance = new DocumentFonts();
        return instance;
    }
}
