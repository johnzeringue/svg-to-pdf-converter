package com.johnzeringue.svgtopdf.handlers;

import java.util.jar.Attributes;
import org.xml.sax.SAXException;

/**
 * An element handler for clipPath tags. Makes the inner path a clip path.
 *
 * @author John Zeringue
 */
public class ClipPathElementHandler extends ElementHandler {

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
            String qName, org.xml.sax.Attributes atts) throws SAXException {
        Attributes fillStrokeToNone = new Attributes();
        fillStrokeToNone.putValue("fill", "none");
        fillStrokeToNone.putValue("stroke", "none");
        docAtts.addScope(fillStrokeToNone);
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
        docAtts.removeScope();
    }
}