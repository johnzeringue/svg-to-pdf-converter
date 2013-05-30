package com.johnzeringue.SVGToPDFConverter.ElementHandler.GraphicsElementHandler;

import com.johnzeringue.SVGToPDFConverter.Formatter;
import java.awt.Color;

/**
 * An element handler for the line SVG tag
 *
 * @author John Zeringue
 */
public class LineElementHandler extends GraphicsElementHandler {

    @Override
    public void startElement() {
        String textStream = "";

        textStream += "  q\n"; // Saves the current graphics state

        // Draw the line
        double x1, y1, x2, y2;
        x1 = Double.parseDouble(docAtts.getValue("x1"));
        y1 = Double.parseDouble(docAtts.getValue("y1"));
        x2 = Double.parseDouble(docAtts.getValue("x2"));
        y2 = Double.parseDouble(docAtts.getValue("y2"));
        y1 = docAtts.getHeight() - y1;
        y2 = docAtts.getHeight() - y2;
        textStream += String.format("  %.0f %.0f m %.0f %.0f l\n",
                x1, y1, x2, y2);

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
            textStream += "f n\n";
        } else { // Assume strokeColor != null
            // Set the stroke color
            textStream += String.format("  %.2f %.2f %.2f RG\n",
                    strokeColor.getRed() / 255.0,
                    strokeColor.getGreen() / 255.0,
                    strokeColor.getBlue() / 255.0);

            // Stroke and close the path
            textStream += "s\n";
        }

        textStream += "  Q"; // Restore the previous graphics state

        pdfObject = Formatter.formatAsStream(textStream);
    }
}
