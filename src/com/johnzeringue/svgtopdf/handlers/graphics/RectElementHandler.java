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
        x = getValueAsDouble("x");
        y = getValueAsDouble("y");
        width = getValueAsDouble("width");
        height = getValueAsDouble("height");
        y = invertY(y) - height;
        _object.append(String.format("%.0f %.0f %.0f %.0f re",
                x, y, width, height));
    }
}
