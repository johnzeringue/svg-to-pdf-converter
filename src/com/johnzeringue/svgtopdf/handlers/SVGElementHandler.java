package com.johnzeringue.svgtopdf.handlers;

import com.johnzeringue.svgtopdf.DocumentAttributes;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * An ElementHandler for SVG elements. Currently creates a GElementHandler and
 * uses that code for the style element, then handles page dimensions itself
 *
 * @author John Zeringue
 */
public class SVGElementHandler extends ElementHandler {

    // The view box formatting string (<min-x> <min-y> width height)
    private final static String VIEW_BOX_FORMAT = "0 0 %s %s";
    // The child GElementHandler
    private GElementHandler gElementHandler;
    private double pageWidth;
    private double pageHeight;

    /**
     * Creates a new SVGElementHandler with a GElementHandler to do all the
     * work.
     */
    public SVGElementHandler(double pageWidth, double pageHeight) {
        gElementHandler = new GElementHandler();
        this.pageWidth = pageWidth;
        this.pageHeight = pageHeight;
    }

    /**
     * Uses the GElementHandler implementation to parse and process the style
     * property, but then checks for and ensures that the file has a view box.
     *
     * @param namespaceURI
     * @param localName
     * @param qName
     * @param atts
     * @throws SAXException
     */
    @Override
    public void startElement(String namespaceURI, String localName,
            String qName, Attributes atts) throws SAXException {
        // Take care of style
        gElementHandler.startElement(qName, localName, qName, atts);

        // See what the SVG tag has as far as page size
        String viewBox = atts.getValue("viewBox");
        String width = atts.getValue("width");
        String height = atts.getValue("height");

        // Check for a view box
        if (viewBox == null) {
            // Check for height and width
            if (width == null || height == null) {
                // If one's missing, replace them both with defaults
                width = pageWidth + "px";
                height = pageHeight + "px";
            }

            viewBox = String.format(VIEW_BOX_FORMAT,
                    width.replace("px", ""), height.replace("px", ""));
        } else {
            // Check for height and width
            if (width == null || height == null) {
                // If one's missing, replace them both view box data
                String[] splitViewBox = viewBox.split(" ");
                width = splitViewBox[2] + "px";
                height = splitViewBox[3] + "px";
            }
        }

        // Add all of this information to the document attributes
        DocumentAttributes.getInstance().putValue("viewBox", viewBox);
        DocumentAttributes.getInstance().putValue("width", width);
        DocumentAttributes.getInstance().putValue("height", height);
    }

    /**
     * Uses the GElementHandler implementation for now.
     *
     * @param namespaceURI
     * @param localName
     * @param qName
     * @throws SAXException
     */
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        gElementHandler.endElement(qName, localName, qName);
    }
}
