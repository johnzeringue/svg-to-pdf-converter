package com.johnzeringue.SVGToPDFConverter.ElementHandler;

import com.johnzeringue.SVGToPDFConverter.Colors;
import com.johnzeringue.SVGToPDFConverter.DocumentAttributes;
import java.awt.Color;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * An element handler for the line SVG tag
 *
 * @author John Zeringue
 * @version 05/25/2013
 */
public class LineElementHandler extends ElementHandler {

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

        double x1, y1, x2, y2, pageWidth, pageHeight;
        x1 = Double.parseDouble(atts.getValue("x1"));
        y1 = Double.parseDouble(atts.getValue("y1"));
        x2 = Double.parseDouble(atts.getValue("x2"));
        y2 = Double.parseDouble(atts.getValue("y2"));
        pageHeight = Double.parseDouble(
                DocumentAttributes.getInstance().getValue("height").replace("px", ""));
        y1 = pageHeight - y1;
        y2 = pageHeight - y2;
        textStream += String.format("  %.0f %.0f m %.0f %.0f l\n",
                x1, y1, x2, y2);

        // Set the stroke color
        String strokeColorName;
        if ((strokeColorName = atts.getValue("stroke")) == null) {
            strokeColorName = DocumentAttributes.getInstance().getValue("stroke");
        }
        Color strokeColor = Colors.get(strokeColorName);
        textStream += String.format("  %.2f %.2f %.2f RG\n",
                strokeColor.getRed() / 255.0,
                strokeColor.getGreen() / 255.0,
                strokeColor.getBlue() / 255.0);

        textStream += "  b\n"; // Draw line and finish path

        textStream += "  Q"; // Restore the previous graphics state

        pdfObject = Formatter.formatAsStream(textStream);
    }
}
