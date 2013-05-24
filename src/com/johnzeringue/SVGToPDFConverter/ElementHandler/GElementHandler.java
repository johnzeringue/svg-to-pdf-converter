package com.johnzeringue.SVGToPDFConverter.ElementHandler;

import com.johnzeringue.SVGToPDFConverter.DocumentAttributes;
import java.util.jar.Attributes;
import org.xml.sax.SAXException;

/**
 * An ElementHandler for G elements
 * 
 * @author  John Zeringue
 * @version 04/02/2013
 */
public class GElementHandler extends ElementHandler {
    /**
     * Parses the style string and adds it to the global document attributes.
     * 
     * @param namespaceURI
     * @param localName
     * @param qName
     * @param atts
     * @throws SAXException 
     */
    @Override
    public void startElement(String namespaceURI, String localName,
            String qName, org.xml.sax.Attributes atts) throws SAXException {
        // Note that these Attributes are of a different class than the ones in
        // the parameters.
        Attributes styleAttributes = new Attributes();
        String styleString = atts.getValue("style");
        
        // Parse the styleString into Attributes
        if (styleString != null) {
            // Separate the variables
            for (String entry : styleString.split(";( )?")) {
                // Split into key/value pairs
                String[] splitEntry = entry.split("( )?:( )?");
                
                // Add to the new Attributes object
                styleAttributes.putValue(splitEntry[0], splitEntry[1]);
            }
        }
        
        // Add the new Attributes object to DocumentAttributes
        DocumentAttributes.getInstance().addScope(styleAttributes);
    }
    
    /**
     * Removes the bottommost scope from DocumentAttributes.
     * 
     * @param namespaceURI
     * @param localName
     * @param qName
     * @throws SAXException 
     */
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        DocumentAttributes.getInstance().removeScope();
    }
}
