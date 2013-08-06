package com.johnzeringue.svgtopdf.handlers.graphics;

import com.johnzeringue.svgtopdf.ClipPaths;
import com.johnzeringue.svgtopdf.handlers.ElementHandler;
import com.johnzeringue.svgtopdf.handlers.GElementHandler;
import com.johnzeringue.svgtopdf.GraphicsStates;
import com.johnzeringue.svgtopdf.objects.DictionaryObject;
import com.johnzeringue.svgtopdf.objects.DirectObject;
import com.johnzeringue.svgtopdf.objects.NameObject;
import com.johnzeringue.svgtopdf.objects.RealObject;
import com.johnzeringue.svgtopdf.objects.StreamObject;
import com.johnzeringue.svgtopdf.util.TextLine;
import com.johnzeringue.svgtopdf.util.TextLines;
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
    private GElementHandler _gElementHandler;
    private boolean _hasFill;
    private boolean _hasStroke;
    private boolean _hasDirectObject;

    public GraphicsElementHandler() {
        _object = new StreamObject();
        _gElementHandler = new GElementHandler();
        _hasDirectObject = true;
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
        _gElementHandler.startElement(namespaceURI, localName, qName, atts);
        
        _hasFill = docAtts.getFill() != null;
        _hasStroke = docAtts.getStroke() != null;

        if (_hasFill || _hasStroke) {
            saveGraphicsState();

            setClipPath();

            drawPath();

            setFill();
            setStroke();
            setOpacity();

            if (_hasStroke) {
                setStrokeWidth();
            }

            closePath();

            restoreGraphicsState();
        } else if (ClipPaths.getInstance().isBuildingNewClipPath()) { // For clipping paths
            _hasDirectObject = false;

            drawPath();

            ClipPaths.getInstance().addClipPathContent(
                    _object.getTextLines()
                    .removeLineAt(0)
                    .removeLineAt(0)
                    .removeLineAt(_object.getTextLines().size() - 3));
        }
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
        _gElementHandler.endElement(namespaceURI, localName, qName);
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
        }
    }

    private void setStrokeWidth() {
        String strokeWidth = docAtts.getValue("stroke-width");

        if (strokeWidth != null) {
            _object.appendLine(String.format("%s w", strokeWidth));
        }
    }

    private void closePath() {
        if (_hasFill && _hasStroke) {
            _object.appendLine("b");
        } else if (_hasFill) {
            _object.appendLine("f n");
        } else if (_hasStroke) {
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
        return _hasDirectObject;
    }

    private void setOpacity() {
        if (docAtts.getValue("opacity") != null) {
            DictionaryObject graphicsState = new DictionaryObject()
                    .addEntry("Type", new NameObject("ExtGState"))
                    .addEntry("ca", new RealObject(docAtts.getValue("opacity")));
            _object.appendLine(GraphicsStates.getInstance().getGraphicStateName(graphicsState) + " gs");
        }
    }

    private void setClipPath() {
        String clipPathID = docAtts.getValue("clip-path");

        if (clipPathID != null) {
            TextLines clipPath = ClipPaths.getInstance().getClipPath(
                    clipPathID.substring(5, clipPathID.length() - 1));

            for (TextLine aLine : clipPath) {
                _object.appendLine(aLine);
            }
        }
    }
}
