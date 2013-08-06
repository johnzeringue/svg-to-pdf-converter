package com.johnzeringue.svgtopdf.handlers.graphics;

import com.johnzeringue.svgtopdf.ClipPaths;
import com.johnzeringue.svgtopdf.handlers.ElementHandler;
import com.johnzeringue.svgtopdf.GraphicsStates;
import com.johnzeringue.svgtopdf.objects.DictionaryObject;
import com.johnzeringue.svgtopdf.objects.DirectObject;
import com.johnzeringue.svgtopdf.objects.NameObject;
import com.johnzeringue.svgtopdf.objects.RealObject;
import com.johnzeringue.svgtopdf.objects.StreamObject;
import com.johnzeringue.svgtopdf.util.Text;
import java.awt.Color;

/**
 * An abstract class to be extended by ElementHandlers which generate visual
 * elements in the PDF. Facilitates additional abstraction for attributes and
 * scoping.
 *
 * @author John Zeringue
 */
public abstract class GraphicsElementHandler extends ElementHandler {

    protected StreamObject _object = new StreamObject();
    private boolean _hasFill;
    private boolean _hasStroke;
    private boolean _hasDirectObject;

    public GraphicsElementHandler() {
        _object = new StreamObject();
        _hasDirectObject = true;
    }

    /**
     * The parser calls this for each element in a document.
     */
    @Override
    public final void startElement() {

        _hasFill = getValueAsColor("fill") != null;
        _hasStroke = getValueAsColor("stroke") != null;

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
                    _object.getText()
                    .removeLineAt(0)
                    .removeLineAt(0)
                    .removeLineAt(_object.getText().lineCount() - 3));
        }
    }

    public abstract void drawPath();

    protected double invertY(double y) {
        return getValueAsDouble("pageHeight") - y;
    }

    private void setFill() {
        Color fillColor = getValueAsColor("fill");

        if (fillColor != null) {
            _object.append(String.format(
                    "%.2f %.2f %.2f rg",
                    fillColor.getRed() / 255.0,
                    fillColor.getGreen() / 255.0,
                    fillColor.getBlue() / 255.0));
        }
    }

    private void setStroke() {
        Color strokeColor = getValueAsColor("stroke");

        if (strokeColor != null) {
            _object.append(String.format(
                    "%.2f %.2f %.2f RG",
                    strokeColor.getRed() / 255.0,
                    strokeColor.getGreen() / 255.0,
                    strokeColor.getBlue() / 255.0));
        }
    }

    private void setStrokeWidth() {
        String strokeWidth = getValue("stroke-width");

        if (strokeWidth != null) {
            _object.append(String.format("%s w", strokeWidth));
        }
    }

    private void closePath() {
        if (_hasFill && _hasStroke) {
            _object.append("b");
        } else if (_hasFill) {
            _object.append("f n");
        } else if (_hasStroke) {
            _object.append("h s");
        } else {
            _object.append("n");
        }
    }

    private void saveGraphicsState() {
        _object.append("q");
    }

    private void restoreGraphicsState() {
        _object.append("Q");
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
        if (getValue("opacity") != null) {
            DictionaryObject graphicsState = new DictionaryObject()
                    .addEntry("Type", new NameObject("ExtGState"))
                    .addEntry("ca", new RealObject(getValue("opacity")));
            _object.append(GraphicsStates.getInstance().getGraphicStateName(graphicsState) + " gs");
        }
    }

    private void setClipPath() {
        String clipPathID = getValue("clip-path");

        if (clipPathID != null) {
            Text clipPath = ClipPaths.getInstance().getClipPath(
                    clipPathID.substring(5, clipPathID.length() - 1));

            _object.append(clipPath);
        }
    }
}
