package com.johnzeringue.svgtopdf.handlers.graphics;

/**
 * An element handler for the line SVG tag
 *
 * @author John Zeringue
 */
public class LineElementHandler extends GraphicsElementHandler {

    @Override
    public void drawPath() {
        // Draw the line
        double x1, y1, x2, y2;
        x1 = getValueAsDouble("x1");
        y1 = invertY(getValueAsDouble("y1"));
        x2 = getValueAsDouble("x2");
        y2 = invertY(getValueAsDouble("y2"));
        
        _object.append(String.format("%.0f %.0f m %.0f %.0f l",
                x1, y1, x2, y2));
    }
}
