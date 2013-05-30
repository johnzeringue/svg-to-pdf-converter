package com.johnzeringue.SVGToPDFConverter.ElementHandler;

import com.johnzeringue.SVGToPDFConverter.DocumentAttributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 * A dummy class which should be extended by all element handlers that are used
 * by SVGToPDFConverter. It is instantiable in order to be used as a handler for
 * unimplemented tags.
 * 
 * @author  John Zeringue
 */
public class ElementHandler extends DefaultHandler {
    // An unwrapped PDF object
    protected String pdfObject;
    // A local handle for DocumentAttributes
    protected DocumentAttributes docAtts;
    
    public ElementHandler() {
        docAtts = DocumentAttributes.getInstance();
    }
    
    /**
     * Returns an "unwrapped" PDF object (no index or obj/endobj tags) or null
     * if this is not appropriate for this object.
     * 
     * @return 
     */
    public String getPDFObject() {
        return pdfObject;
    }
}
