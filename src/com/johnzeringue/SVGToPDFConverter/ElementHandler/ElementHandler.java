package com.johnzeringue.SVGToPDFConverter.ElementHandler;

import com.johnzeringue.SVGToPDFConverter.DocumentAttributes;
import com.johnzeringue.SVGToPDFConverter.Formatter;
import com.johnzeringue.SVGToPDFConverter.PDFObjects.DirectObject;
import org.xml.sax.helpers.DefaultHandler;

/**
 * A dummy class which should be extended by all element handlers that are used
 * by SVGToPDFConverter. It is instantiable in order to be used as a handler for
 * unimplemented tags.
 * 
 * @author  John Zeringue
 */
public class ElementHandler extends DefaultHandler {
    // A local handle for DocumentAttributes
    protected DocumentAttributes docAtts;
    
    public ElementHandler() {
        docAtts = DocumentAttributes.getInstance();
    }
    
    public DirectObject getDirectObject() {
        return null;
    }
    
    public boolean hasDirectObject() {
        return false;
    }
}
