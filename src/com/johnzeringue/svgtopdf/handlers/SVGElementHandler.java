package com.johnzeringue.svgtopdf.handlers;

import com.johnzeringue.svgtopdf.DocumentAttributes;

/**
 *
 * @author John
 */
public class SVGElementHandler extends ElementHandler {
    public void startElement() {
        String pageHeight = DocumentAttributes.getInstance().getValue("height");
        String pageWidth = DocumentAttributes.getInstance().getValue("width");
        
        if (pageHeight != null) {
            DocumentAttributes.getInstance().putValue("pageHeight", pageHeight);
        }
        if (pageWidth != null) {
            DocumentAttributes.getInstance().putValue("pageWidth", pageWidth);
        }
    }
}
