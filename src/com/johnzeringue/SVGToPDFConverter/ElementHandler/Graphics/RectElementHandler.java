package com.johnzeringue.SVGToPDFConverter.ElementHandler.Graphics;

/**
 * An ElementHandler for Rect elements
 *
 * @author John Zeringue
 */
public class RectElementHandler extends GraphicsElementHandler {

    @Override
    public void drawPath() {// Create the rectangle path
        double x, y, width, height;
        x = Double.parseDouble(docAtts.getValue("x"));
        y = Double.parseDouble(docAtts.getValue("y"));
        width = Double.parseDouble(docAtts.getValue("width"));
        height = Double.parseDouble(docAtts.getValue("height"));
        y = docAtts.getHeight() - y - height;
        _object.appendLine(String.format("%.0f %.0f %.0f %.0f re",
                x, y, width, height));
    }
}
