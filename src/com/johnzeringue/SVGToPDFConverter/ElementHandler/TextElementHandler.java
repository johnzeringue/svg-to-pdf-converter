package com.johnzeringue.SVGToPDFConverter.ElementHandler;

import com.johnzeringue.SVGToPDFConverter.DocumentFonts;
import com.johnzeringue.SVGToPDFConverter.PDFObjects.DirectObject;
import com.johnzeringue.SVGToPDFConverter.PDFObjects.StreamObject;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * An ElementHandler for Text elements
 *
 * @author John Zeringue
 * @version 05/29/2013
 */
public class TextElementHandler extends ElementHandler {
    private StreamObject _object;
    private StringBuilder _tagContents;

    public TextElementHandler() {
        super();
        _tagContents = new StringBuilder();
        _object = new StreamObject();
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
        _object.appendLine("BT");
        String fontFamily = atts.getValue("font-family");
        if (fontFamily == null) {
            fontFamily = docAtts.getValue("font-family");
        }
        fontFamily = fontFamily.replaceAll("'", "");
        DocumentFonts.getInstance().getFontNumber(fontFamily);
        Double fontSize;
        if (atts.getValue("font-size") == null) {
            fontSize = Double.valueOf(docAtts.getValue("font-size"));
        } else {
            fontSize = Double.valueOf(atts.getValue("font-size"));
        }
        _object.appendLine(String.format("0.0 g\n/F%d %.1f Tf",
                DocumentFonts.getInstance().getFontNumber(fontFamily) + 1,
                fontSize));
        double height = docAtts.getHeight();
        _object.appendLine(String.format("%f %f Td",
                Double.parseDouble(atts.getValue("x")),
                height - Double.parseDouble(atts.getValue("y"))));
    }

    @Override
    public void characters(char ch[], int start, int length) {
        _tagContents.append(ch, start, length);
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
        _object.appendLine(String.format("(%s) Tj", _tagContents));
        _object.appendLine("ET");
    }
    
    @Override
    public DirectObject getDirectObject() {
        return _object;
    }
    
    @Override
    public boolean hasDirectObject() {
        return true;
    }
}
