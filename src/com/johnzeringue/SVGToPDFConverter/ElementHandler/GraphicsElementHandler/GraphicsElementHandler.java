package com.johnzeringue.SVGToPDFConverter.ElementHandler.GraphicsElementHandler;

import com.johnzeringue.SVGToPDFConverter.ElementHandler.ElementHandler;
import com.johnzeringue.SVGToPDFConverter.ElementHandler.GElementHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * An abstract class to be extended by ElementHandlers which generate visual
 * elements in the PDF. Facilitates additional abstraction for attributes and
 * scoping.
 * 
 * @author John Zeringue
 */
public abstract class GraphicsElementHandler extends ElementHandler {
    private GElementHandler gElementHandler;
    
    public GraphicsElementHandler() {
        gElementHandler = new GElementHandler();
    }
    
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
    public final void startElement(String namespaceURI, String localName,
            String qName, Attributes atts) throws SAXException {
        gElementHandler.startElement(namespaceURI, localName, qName, atts);
        
        startElement();
    }
    
    public abstract void startElement();
    
    /**
     * Called at the end of an element.
     * 
     * @param namespaceURI
     * @param localName
     * @param qName
     * @throws SAXException 
     */
    @Override
    public final void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        endElement();
        
        gElementHandler.endElement(namespaceURI, localName, qName);
    }
    
    public void endElement() {
        
    }
    
    protected double invertY(double y) {
        return docAtts.getHeight() - y;
    }
}
