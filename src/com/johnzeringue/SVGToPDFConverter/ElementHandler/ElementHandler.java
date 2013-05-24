package com.johnzeringue.SVGToPDFConverter.ElementHandler;

import org.xml.sax.helpers.DefaultHandler;

/**
 * A dummy class which should be extended by all element handlers that are used
 * by SVGToPDFConverter. It is instantiable in order to be used as a handler for
 * unimplemented tags.
 * 
 * @author  John Zeringue
 * @version 04/02/2013
 */
public class ElementHandler extends DefaultHandler {
    // An unwrapped PDF object
    protected String pdfObject;
    
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
