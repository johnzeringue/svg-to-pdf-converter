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
        x1 = Double.parseDouble(docAtts.getValue("x1"));
        y1 = Double.parseDouble(docAtts.getValue("y1"));
        x2 = Double.parseDouble(docAtts.getValue("x2"));
        y2 = Double.parseDouble(docAtts.getValue("y2"));
        y1 = docAtts.getHeight() - y1;
        y2 = docAtts.getHeight() - y2;
        _object.appendLine(String.format("%.0f %.0f m %.0f %.0f l",
                x1, y1, x2, y2));
    }
}
