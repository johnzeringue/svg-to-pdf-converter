package com.johnzeringue.SVGToPDFConverter.ElementHandler;

import com.johnzeringue.SVGToPDFConverter.DocumentAttributes;
import com.johnzeringue.SVGToPDFConverter.DocumentFonts;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * An ElementHandler for Text elements
 *
 * @author John Zeringue
 * @version 04/02/2013
 */
public class TextElementHandler extends ElementHandler {

    private StringBuilder tagContents;

    public TextElementHandler() {
        super();
        tagContents = new StringBuilder();
    }

    /**
     * Parser calls this for each element in the document. This will process
     * element and either ignore it, store its data, or write it to the PDF
     * file.
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
        /* Build text object */
        pdfObject = "BT\n";
        String fontFamily = atts.getValue("font-family");
        if (fontFamily == null) {
            fontFamily = DocumentAttributes.getInstance().getValue("font-family");
        }
        fontFamily = fontFamily.replaceAll("'", "");
        DocumentFonts.getInstance().getFontNumber(fontFamily);
        Double fontSize;
        if (atts.getValue("font-size") == null) {
            fontSize = Double.valueOf(DocumentAttributes.getInstance().getValue("font-size"));
        } else {
            fontSize = Double.valueOf(atts.getValue("font-size"));
        }
        pdfObject += String.format("  0.0 g\n  /F%d %.1f Tf\n",
                DocumentFonts.getInstance().getFontNumber(fontFamily) + 1,
                fontSize);
        int height = Integer.parseInt(
                DocumentAttributes.getInstance().getValue("height").replaceAll("px", ""));
        pdfObject += String.format("  %d %d Td\n",
                (int) Double.parseDouble(atts.getValue("x")),
                height - (int) Double.parseDouble(atts.getValue("y")));
    }

    @Override
    public void characters(char ch[], int start, int length) {
        tagContents.append(ch, start, length);
    }

    /**
     * Parser calls this for each element in the document.
     *
     * @param namespaceURI
     * @param localName
     * @param qName
     * @throws SAXException
     */
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        pdfObject += String.format("  (%s) Tj\n", tagContents);
        pdfObject += "ET";

        /* Write text object to file */
        pdfObject = Formatter.formatAsStream(pdfObject);
    }
}
