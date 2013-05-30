package com.johnzeringue.SVGToPDFConverter.ElementHandler.GraphicsElementHandler;

import com.johnzeringue.SVGToPDFConverter.Formatter;
import java.awt.Color;

/**
 * An ElementHandler for Rect elements
 *
 * @author John Zeringue
 */
public class RectElementHandler extends GraphicsElementHandler {

    @Override
    public void startElement() {
        String textStream = "";

        textStream += "  q\n"; // Saves the current graphics state
        
        // Create the rectangle path
        double x, y, width, height;
        x = Double.parseDouble(docAtts.getValue("x"));
        y = Double.parseDouble(docAtts.getValue("y"));
        width = Double.parseDouble(docAtts.getValue("width"));
        height = Double.parseDouble(docAtts.getValue("height"));
        y = docAtts.getHeight() - y - height;
        textStream += String.format("  %.0f %.0f %.0f %.0f re\n",
                x, y, width, height);

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
