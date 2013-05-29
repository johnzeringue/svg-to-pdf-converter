package com.johnzeringue.SVGToPDFConverter.ElementHandler;

import com.johnzeringue.SVGToPDFConverter.Colors;
import com.johnzeringue.SVGToPDFConverter.DocumentAttributes;
import java.awt.Color;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * An ElementHandler for Rect elements
 *
 * @author John Zeringue
 * @version 05/29/2013
 */
public class RectElementHandler extends ElementHandler {

    /**
     * NOTES 04/16/2013:
     *
     * % To draw a rectangle: > q % Save the graphics state (I might not do this
     * if > % everything's explicit) > r g b rg % Set the fill color > r g b RG
     * % Set the stroke color if applicable > x y width height re % Actually add
     * the rectangle > b % Or "B n" (Depends on if there's a stroke color) > Q %
     * Restore the graphics state (Only if I'm doing q)
     *
     * Relevant SVG variables: - stroke (either a color or none) - fill (either
     * a color or none) - stroke-width - width - height - x - y
     */
    /**
     * The parser calls this for each element in a document.
     *
     * @param namespaceURI
     * @param localName
     * @param qName
     * @param atts
     * @throws SAXException
     */
    @Override
    public void startElement(String namespaceURI, String localName,
            String qName, Attributes atts) throws SAXException {
        String textStream = "";

        textStream += "  q\n"; // Saves the current graphics state

        // Set the fill color
        if (atts.getValue("style") != null
                && atts.getValue("style").contains("fill")) { // If there's a style attribute
            Color fillColor = Colors.parseRGB(
                    atts.getValue("style").replaceAll("fill: *", ""));
            textStream += String.format("  %.2f %.2f %.2f rg\n",
                    fillColor.getRed() / 255.0,
                    fillColor.getGreen() / 255.0,
                    fillColor.getBlue() / 255.0);
        } else {
            String fillColorName;
            if ((fillColorName = atts.getValue("fill")) == null) {
                fillColorName = DocumentAttributes.getInstance().getValue("fill");
            }
            Color fillColor = Colors.get(fillColorName);
            textStream += String.format("  %.2f %.2f %.2f rg\n",
                    fillColor.getRed() / 255.0,
                    fillColor.getGreen() / 255.0,
                    fillColor.getBlue() / 255.0);
        }

        double x, y, width, height, pageWidth, pageHeight;
        x = Double.parseDouble(atts.getValue("x"));
        y = Double.parseDouble(atts.getValue("y"));
        width = Double.parseDouble(atts.getValue("width"));
        height = Double.parseDouble(atts.getValue("height"));
        pageHeight = Double.parseDouble(
                DocumentAttributes.getInstance().getValue("height").replace("px", ""));
        y = pageHeight - y - height;
        textStream += String.format("  %.0f %.0f %.0f %.0f re\n",
                x, y, width, height);

        // Set the stroke color
        String strokeValue = "";
        if (atts.getValue("style") != null
                && atts.getValue("style").contains("stroke")) { // If there's a style attribute
            strokeValue = atts.getValue("style").replaceAll(" *stroke: *", "");
            if (!strokeValue.matches("none.*")) {
                Color strokeColor = Colors.parseRGB(
                        atts.getValue("style").replaceAll(" *stroke: *", ""));
                textStream += String.format("  %.2f %.2f %.2f RG\n",
                        strokeColor.getRed() / 255.0,
                        strokeColor.getGreen() / 255.0,
                        strokeColor.getBlue() / 255.0);
            }
        } else {
            String strokeColorName;
            if ((strokeColorName = atts.getValue("stroke")) == null) {
                strokeColorName = DocumentAttributes.getInstance().getValue("stroke");
            }
            Color strokeColor = Colors.get(strokeColorName);
            textStream += String.format("  %.2f %.2f %.2f RG\n",
                    strokeColor.getRed() / 255.0,
                    strokeColor.getGreen() / 255.0,
                    strokeColor.getBlue() / 255.0);
        }

        if (strokeValue.matches("none.*")) {
            textStream += "  f n\n";
        } else {
            textStream += "  b\n"; // Draw Rectangle and finish path
        }

        textStream += "  Q\n"; // Restore the previous graphics state

        pdfObject = Formatter.formatAsStream(textStream);
    }
}
