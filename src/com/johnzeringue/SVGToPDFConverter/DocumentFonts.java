/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnzeringue.SVGToPDFConverter;

/**
 * A singleton class for holding font information as it moves from the SVG to
 * the PDF.
 * 
 * @author  John Zeringue
 * @version 04/03/2013
 */
public class DocumentFonts {
    private static DocumentFonts instance;
    
    // Need to implement
    
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
