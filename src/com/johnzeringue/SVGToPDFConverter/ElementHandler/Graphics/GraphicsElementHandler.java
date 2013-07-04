package com.johnzeringue.SVGToPDFConverter.ElementHandler.Graphics;

import com.johnzeringue.SVGToPDFConverter.ElementHandler.ElementHandler;
import com.johnzeringue.SVGToPDFConverter.ElementHandler.GElementHandler;
import com.johnzeringue.SVGToPDFConverter.GraphicsStates;
import com.johnzeringue.SVGToPDFConverter.PDFObjects.DictionaryObject;
import com.johnzeringue.SVGToPDFConverter.PDFObjects.DirectObject;
import com.johnzeringue.SVGToPDFConverter.PDFObjects.NameObject;
import com.johnzeringue.SVGToPDFConverter.PDFObjects.RealObject;
import com.johnzeringue.SVGToPDFConverter.PDFObjects.StreamObject;
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

    protected StreamObject _object = new StreamObject();
    private GElementHandler gElementHandler;
    private boolean hasFill;
    private boolean hasStroke;

    public GraphicsElementHandler() {
        _object = new StreamObject();
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

        drawPath();

        setFill();
        setStroke();
        setOpacity();

        if (hasStroke) {
            setStrokeWidth();
        }

        closePath();

        restoreGraphicsState();
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
            _object.appendLine(String.format(
                    "%.2f %.2f %.2f rg",
                    fillColor.getRed() / 255.0,
                    fillColor.getGreen() / 255.0,
                    fillColor.getBlue() / 255.0));

            hasFill = true;
        }
    }

    private void setStroke() {
        Color strokeColor = docAtts.getStroke();

        if (strokeColor != null) {
            _object.appendLine(String.format(
                    "%.2f %.2f %.2f RG",
                    strokeColor.getRed() / 255.0,
                    strokeColor.getGreen() / 255.0,
                    strokeColor.getBlue() / 255.0));

            hasStroke = true;
        }
    }

    private void setStrokeWidth() {
        String strokeWidth = docAtts.getValue("stroke-width");

        if (strokeWidth != null) {
            _object.appendLine(String.format("%s w", strokeWidth));
        }
    }

    private void closePath() {
        if (hasFill && hasStroke) {
            _object.appendLine("b");
        } else if (hasFill) {
            _object.appendLine("f n");
        } else if (hasStroke) {
            _object.appendLine("h s");
        } else {
            _object.appendLine("n");
        }
    }

    private void saveGraphicsState() {
        _object.appendLine("q");
    }

    private void restoreGraphicsState() {
        _object.appendLine("Q");
    }

    @Override
    public DirectObject getDirectObject() {
        return _object;
    }

    @Override
    public boolean hasDirectObject() {
        return true;
    }

    private void setOpacity() {
        if (docAtts.getValue("opacity") != null) {
            DictionaryObject graphicsState = new DictionaryObject()
                    .addEntry("Type", new NameObject("ExtGState"))
                    .addEntry("ca", new RealObject(docAtts.getValue("opacity")));
            _object.appendLine(GraphicsStates.getInstance().getGraphicStateName(graphicsState) + " gs");
        }
    }
}
