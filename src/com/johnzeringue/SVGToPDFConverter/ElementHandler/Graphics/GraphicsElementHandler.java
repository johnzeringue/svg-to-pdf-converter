package com.johnzeringue.SVGToPDFConverter.ElementHandler.Graphics;

import com.johnzeringue.SVGToPDFConverter.ElementHandler.ElementHandler;
import com.johnzeringue.SVGToPDFConverter.ElementHandler.GElementHandler;
import java.awt.Color;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * An abstract class to be extended by ElementHandlers which generate visual
 * elements in the PDF. Facilitates additional abstraction for attributes and
 * scoping.
 *
 * @author John Zeringue
 */
public abstract class GraphicsElementHandler extends ElementHandler {

    private GElementHandler gElementHandler;
    private boolean hasFill;
    private boolean hasStroke;

    public GraphicsElementHandler() {
        gElementHandler = new GElementHandler();

        hasFill = false;
        hasStroke = false;
    }

    /**
     * The parser calls this for each element in a document.
     *
     * @param namespaceURI
     * @param localName
     * @param qName
     * @param atts
     * @throws SAXException
     */
    @Override
    public final void startElement(String namespaceURI, String localName,
            String qName, Attributes atts) throws SAXException {
        gElementHandler.startElement(namespaceURI, localName, qName, atts);

        saveGraphicsState();
        
        if ("0.35".equals(docAtts.getValue("opacity"))) {
            appendToPDFObjectContents("  /Trans gs\n");
        }
        
        drawPath();
        
        setFill();
        setStroke();
        
        if (hasStroke) {
            setStrokeWidth();
        }
        
        closePath();
        
        restoreGraphicsState();
        
        formatPDFObjectContentsAsStream();
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
        gElementHandler.endElement(namespaceURI, localName, qName);
    }

    public abstract void drawPath();

    protected double invertY(double y) {
        return docAtts.getHeight() - y;
    }

    private void setFill() {
        Color fillColor = docAtts.getFill();

        if (fillColor != null) {
            appendToPDFObjectContents(String.format(
                    "  %.2f %.2f %.2f rg\n",
                    fillColor.getRed() / 255.0,
                    fillColor.getGreen() / 255.0,
                    fillColor.getBlue() / 255.0));

            hasFill = true;
        }
    }

    private void setStroke() {
        Color strokeColor = docAtts.getStroke();

        if (strokeColor != null) {
            appendToPDFObjectContents(String.format(
                    "  %.2f %.2f %.2f RG\n",
                    strokeColor.getRed() / 255.0,
                    strokeColor.getGreen() / 255.0,
                    strokeColor.getBlue() / 255.0));
            
            hasStroke = true;
        }
    }

    private void setStrokeWidth() {
        String strokeWidth = docAtts.getValue("stroke-width");

        if (strokeWidth != null) {
            appendToPDFObjectContents(String.format("  %s w\n", strokeWidth));
        }
    }
    
    private void closePath() {
        if (hasFill && hasStroke) {
            appendToPDFObjectContents("  b\n");
        } else if (hasFill) {
            appendToPDFObjectContents("  f n\n");
        } else if (hasStroke) {
            appendToPDFObjectContents("  h s\n");
        } else {
            appendToPDFObjectContents("  n\n");
        }
    }

    private void saveGraphicsState() {
        appendToPDFObjectContents("  q\n");
    }

    private void restoreGraphicsState() {
        appendToPDFObjectContents("  Q\n");
    }
}
