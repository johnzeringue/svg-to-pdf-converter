package com.johnzeringue.SVGToPDFConverter.ElementHandler;

import com.johnzeringue.SVGToPDFConverter.DocumentAttributes;
import com.johnzeringue.SVGToPDFConverter.Formatter;
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
    // An unwrapped PDF object
    private String pdfObjectContents;
    
    public ElementHandler() {
        docAtts = DocumentAttributes.getInstance();
        
        pdfObjectContents = "";
    }
    
    /**
     * Returns an "unwrapped" PDF object (no index or obj/endobj tags) or null
     * if this is not appropriate for this object.
     * 
     * @return 
     */
    public final String getPDFObjectContents() {
        if (hasPDFObjectContents()) {
            return pdfObjectContents.toString();
        } else {
            return null;
        }
    }
    
    public boolean hasPDFObjectContents() {
        return pdfObjectContents.length() != 0;
    }
    
    protected void appendToPDFObjectContents(String s) {
        pdfObjectContents += s;
    }
    
    protected void formatPDFObjectContentsAsStream() {
        pdfObjectContents = Formatter.formatAsStream(pdfObjectContents);
    }
}
