package com.johnzeringue.SVGToPDFConverter.ElementHandler.GraphicsElementHandler;

import com.johnzeringue.SVGToPDFConverter.Formatter;
import java.awt.Color;

/**
 * An element handler for circle SVG tags.
 *
 * @author John Zeringue
 */
public class CircleElementHandler extends GraphicsElementHandler {
    private static final String C_FORMAT = "  %f %f %f %f %f %f c\n";
    private static final String M_FORMAT = "  %f %f m\n";

    @Override
    public void startElement() {
        
        double r, cx, cy, x1, y1, x2, y2, offset;
        String textStream = "";

        textStream += "  q\n"; // Saves the current graphics state
        
        r = Double.parseDouble(docAtts.getValue("r"));
        cx = Double.parseDouble(docAtts.getValue("cx"));
        cy = invertY(Double.parseDouble(docAtts.getValue("cy")));
        offset = (4.0 / 3) * (Math.sqrt(2) - 1) * r;
        
        // Move to the tangent at 0 radians
        x1 = cx + r;
        y1 = cy;
        textStream += String.format(M_FORMAT, x1, y1);
        // Make the first cubic bezier to the PI/2 tangent
        x2 = cx;
        y2 = cy + r;
        textStream += String.format(C_FORMAT, 
                x1, y1 + offset, x2 + offset, y2, x2, y2);
        // Make the second cubic bezier to the PI tangent
        x1 = x2;
        y1 = y2;
        x2 = cx - r;
        y2 = cy;
        textStream += String.format(C_FORMAT, 
                x1 - offset, y1, x2, y2 + offset, x2, y2);
        // Make the third cubic bezier to the 3 * PI / 2 tangent
        x1 = x2;
        y1 = y2;
        x2 = cx;
        y2 = cy - r;
        textStream += String.format(C_FORMAT, 
                x1, y1 - offset, x2 - offset, y2, x2, y2);
        // Make the fourth cubic bezier to the 2 * PI tangent
        x1 = x2;
        y1 = y2;
        x2 = cx + r;
        y2 = cy;
        textStream += String.format(C_FORMAT, 
                x1 + offset, y1, x2, y2 - offset, x2, y2);

        Color fillColor = docAtts.getFill();
        Color strokeColor = docAtts.getStroke();

        if (fillColor != null && strokeColor != null) {
            // Set the fill color
            textStream += String.format("  %.2f %.2f %.2f rg\n",
                    fillColor.getRed() / 255.0,
                    fillColor.getGreen() / 255.0,
                    fillColor.getBlue() / 255.0);

            // Set the stroke color
            textStream += String.format("  %.2f %.2f %.2f RG\n",
                    strokeColor.getRed() / 255.0,
                    strokeColor.getGreen() / 255.0,
                    strokeColor.getBlue() / 255.0);
            
            // Fill and stroke and close the path
            textStream += "  b\n";
        } else if (fillColor != null) {
            // Set the fill color
            textStream += String.format("  %.2f %.2f %.2f rg\n",
                    fillColor.getRed() / 255.0,
                    fillColor.getGreen() / 255.0,
                    fillColor.getBlue() / 255.0);
            
            // Fill and close the path
            textStream += "  f n\n";
        } else { // Assume strokeColor != null
            // Set the stroke color
            textStream += String.format("  %.2f %.2f %.2f RG\n",
                    strokeColor.getRed() / 255.0,
                    strokeColor.getGreen() / 255.0,
                    strokeColor.getBlue() / 255.0);
            
            // Stroke and close the path
            textStream += "  s\n";
        }

        textStream += "  Q"; // Restore the previous graphics state

        pdfObject = Formatter.formatAsStream(textStream);
    }
}
