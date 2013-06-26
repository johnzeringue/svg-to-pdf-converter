package com.johnzeringue.SVGToPDFConverter.PDFObjects;

/**
 *
 * @author John Zeringue
 */
public class ObjectReference implements DirectObject {
    private final static String OBJECT_REFERENCE_FORMAT = "%d 0 R";
    
    private int _indirectObjectNumber;
    
    public ObjectReference(int indirectObjectNumber) {
        _indirectObjectNumber = indirectObjectNumber;
    }

    @Override
    public TextLines getTextLines() {
        
        return new TextLines().appendLine(
                String.format(OBJECT_REFERENCE_FORMAT, _indirectObjectNumber));
    }
}
