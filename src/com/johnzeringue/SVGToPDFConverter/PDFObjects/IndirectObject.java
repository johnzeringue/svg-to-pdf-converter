package com.johnzeringue.SVGToPDFConverter.PDFObjects;

/**
 *
 * 
 * @author John Zeringue
 */
public class IndirectObject {
    private final static String INDIRECT_OBJECT_FORMAT = "" +
            "%d 0 obj\n" +
            "%s\n" +
            "endobj\n";
    
    private static int indirectObjectCount = 0;
    
    private DirectObject object;
    private int indirectObjectNumber;
    
    public IndirectObject(DirectObject aDirectObject) {
        object = aDirectObject;
        indirectObjectNumber = ++indirectObjectCount;
    }
    
    public ObjectReference getObjectReference() {
        return new ObjectReference(indirectObjectNumber);
    }
    
    public String getString() {
        return String.format(INDIRECT_OBJECT_FORMAT, 
                indirectObjectCount, object.getLines().indentBy(2).toString());
    }
}
