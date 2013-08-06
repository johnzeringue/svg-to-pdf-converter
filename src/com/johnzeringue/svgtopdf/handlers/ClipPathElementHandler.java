package com.johnzeringue.svgtopdf.handlers;

import com.johnzeringue.svgtopdf.ClipPaths;
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
     */
    @Override
    public void startElement() {
        Attributes fillStrokeToNone = new Attributes();
        fillStrokeToNone.putValue("fill", "none");
        fillStrokeToNone.putValue("stroke", "none");
        docAtts.addScope(fillStrokeToNone);
        
        ClipPaths.getInstance().startClipPath(docAtts.getValue("id"));
    }

    /**
     * Parser calls this for each element in the document.
     */
    @Override
    public void endElement() {
        ClipPaths.getInstance().endCurrentClipPath();
        docAtts.removeScope();
    }
}