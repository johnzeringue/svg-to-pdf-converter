package com.johnzeringue.SVGToPDFConverter.PDFObjects;

/**
 *
 * @author John Zeringue
 */
public class ObjectReference implements DirectObject {
    private final static String OBJECT_REFERENCE_FORMAT = "%d 0 R";
    
    private int indirectObjectNumber;
    
    public ObjectReference(int indirectObjectNumber) {
        this.indirectObjectNumber = indirectObjectNumber;
    }

    @Override
    public Lines getLines() {
        return (new Lines()).addLine(
                String.format(OBJECT_REFERENCE_FORMAT, indirectObjectNumber));
    }
}
