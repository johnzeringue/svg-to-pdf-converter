package com.johnzeringue.svgtopdf.handlers.graphics;

/**
 * An ElementHandler for Rect elements
 *
 * @author John Zeringue
 */
public class RectElementHandler extends GraphicsElementHandler {

    @Override
    public void drawPath() { // Create the rectangle path
        double x, y, width, height;
        x = Double.parseDouble(getValue("x"));
        y = Double.parseDouble(getValue("y"));
        width = Double.parseDouble(getValue("width"));
        height = Double.parseDouble(getValue("height"));
        y = invertY(y) - height;
        _object.append(String.format("%.0f %.0f %.0f %.0f re",
                x, y, width, height));
    }
}
