package com.johnzeringue.svgtopdf.handlers;

import com.johnzeringue.svgtopdf.DocumentAttributes;
import com.johnzeringue.svgtopdf.objects.DirectObject;
import java.awt.Color;
import java.util.jar.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * A dummy class which should be extended by all element handlers that are used
 * by SVGToPDFConverter. It is instantiable in order to be used as a handler for
 * unimplemented tags.
 *
 * @author John Zeringue
 */
public class ElementHandler extends DefaultHandler {

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
    public final void startElement(String namespaceURI, String localName,
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
                // I think this is when the qualified name has a colon
            }
        }

        DocumentAttributes.getInstance().addScope(currentAttributes);
        
        startElement();
    }

    public void startElement() {
        // To be overridden
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
    public final void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        endElement();

        DocumentAttributes.getInstance().removeScope();
    }

    public void endElement() {
        // To be overridden
    }
    
    public String getValue(String name) {
        return DocumentAttributes.getInstance().getValue(name);
    }
    
    public int getValueAsInt(String name) {
        return DocumentAttributes.getInstance().getValueAsInt(name);
    }
    
    public double getValueAsDouble(String name) {
        return DocumentAttributes.getInstance().getValueAsDouble(name);
    }
    
    public Color getValueAsColor(String name) {
        return DocumentAttributes.getInstance().getValueAsColor(name);
    }

    public DirectObject getDirectObject() {
        return null;
    }

    public boolean hasDirectObject() {
        return false;
    }
}
