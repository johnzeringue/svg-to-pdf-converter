package com.johnzeringue.SVGToPDFConverter.ElementHandler;

import java.util.jar.Attributes;
import org.xml.sax.SAXException;

/**
 * An ElementHandler for G elements
 *
 * @author John Zeringue
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
        Attributes currentAttributes = new Attributes();

        for (int i = 0; i < atts.getLength(); i++) {
            if ("style".equals(atts.getQName(i))) {
                String styleString = atts.getValue(i);

                // Parse the style String into Attributes
                if (styleString != null) {
                    // Separate the variables
                    for (String entry : styleString.split(";\\s*")) {
                        // Split into key/value pairs
                        String[] splitEntry = entry.split("\\s*:\\s*");

                        // Add to the new Attributes object
                        currentAttributes.putValue(splitEntry[0], splitEntry[1]);
                    }
                }
                
                continue;
            } 
            
            try {
                currentAttributes.putValue(atts.getQName(i), atts.getValue(i));
            } catch (IllegalArgumentException ex) {
                
            }
        }


        // Add the new Attributes object to DocumentAttributes
        docAtts.addScope(currentAttributes);
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
        docAtts.removeScope();
    }
}
