package com.johnzeringue.svgtopdf.handlers.graphics;

/**
 * An element handler for circle SVG tags.
 *
 * @author John Zeringue
 */
public class CircleElementHandler extends GraphicsElementHandler {

    private static final String C_FORMAT = "%f %f %f %f %f %f c";
    private static final String M_FORMAT = "%f %f m";

    @Override
    public void drawPath() {
        double r, cx, cy, x1, y1, x2, y2, offset;

        r = getValueAsDouble("r");
        cx = getValueAsDouble("cx");
        cy = invertY(getValueAsDouble("cy"));
        offset = (4.0 / 3) * (Math.sqrt(2) - 1) * r;

        // Move to the tangent at 0 radians
        x1 = cx + r;
        y1 = cy;
        _object.append(String.format(M_FORMAT, x1, y1));
        // Make the first cubic bezier to the PI/2 tangent
        x2 = cx;
        y2 = cy + r;
        _object.append(String.format(C_FORMAT,
                x1, y1 + offset, x2 + offset, y2, x2, y2));
        // Make the second cubic bezier to the PI tangent
        x1 = x2;
        y1 = y2;
        x2 = cx - r;
        y2 = cy;
        _object.append(String.format(C_FORMAT,
                x1 - offset, y1, x2, y2 + offset, x2, y2));
        // Make the third cubic bezier to the 3 * PI / 2 tangent
        x1 = x2;
        y1 = y2;
        x2 = cx;
        y2 = cy - r;
        _object.append(String.format(C_FORMAT,
                x1, y1 - offset, x2 - offset, y2, x2, y2));
        // Make the fourth cubic bezier to the 2 * PI tangent
        x1 = x2;
        y1 = y2;
        x2 = cx + r;
        y2 = cy;
        _object.append(String.format(C_FORMAT,
                x1 + offset, y1, x2, y2 - offset, x2, y2));
    }
}
