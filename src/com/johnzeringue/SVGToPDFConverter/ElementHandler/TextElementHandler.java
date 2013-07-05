package com.johnzeringue.SVGToPDFConverter.ElementHandler;

import com.johnzeringue.SVGToPDFConverter.Fonts;
import com.johnzeringue.SVGToPDFConverter.PDFObjects.DirectObject;
import com.johnzeringue.SVGToPDFConverter.PDFObjects.StreamObject;
import java.util.regex.Pattern;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * An ElementHandler for Text elements
 *
 * @author John Zeringue
 * @version 05/29/2013
 */
public class TextElementHandler extends ElementHandler {
    private static final Pattern TRANSLATE_PATTERN = null;
    private static final Pattern ROTATE_PATTERN = null;
    
    private GElementHandler _gElementHandler;
    private StreamObject _object;
    private StringBuilder _tagContents;

    public TextElementHandler() {
        super();
        _gElementHandler = new GElementHandler();
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
        _gElementHandler.startElement(namespaceURI, localName, qName, atts);
        
        _object.appendLine("BT");
        
        String fontFamily = docAtts.getValue("font-family").replaceAll("'", "");
        Double fontSize = Double.valueOf(docAtts.getValue("font-size"));
        
        parseTransform();
        
        _object.appendLine("0.0 g");
        _object.appendLine(String.format("%s %.1f Tf",
                Fonts.getInstance().getFontTag(fontFamily), fontSize));
        
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
        
        _gElementHandler.endElement(namespaceURI, localName, qName);
    }
    
    private void parseTransform() {
        String transform = docAtts.getValue("transform");
        
        if (transform != null) {
            
        }
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
