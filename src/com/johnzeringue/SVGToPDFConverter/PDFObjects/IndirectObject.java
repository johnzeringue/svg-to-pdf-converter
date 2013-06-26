package com.johnzeringue.SVGToPDFConverter.PDFObjects;

/**
 * A class representing an indirect object. It takes a direct object in its
 * constructor and then can output that object's object reference and its
 * string representation as an indirect object.
 * 
 * @author John Zeringue
 */
public class IndirectObject {
    private final static String INDIRECT_OBJECT_FORMAT = "" +
            "%d 0 obj\n" +
            "%s\n" +
            "endobj\n";
    
    private static int indirectObjectCount = 0;
    
    private DirectObject _object;
    private int _indirectObjectNumber;
    
    /**
     * Creates a new indirect object containing the provided direct object.
     * 
     * @param aDirectObject this object's contents
     */
    public IndirectObject(DirectObject aDirectObject) {
        _object = aDirectObject;
        _indirectObjectNumber = ++indirectObjectCount;
    }
    
    /**
     * Returns this object's object reference.
     * 
     * @return this object's object reference
     */
    public ObjectReference getObjectReference() {
        return new ObjectReference(_indirectObjectNumber);
    }
    
    /**
     * Returns a string representing this indirect object as it should appear
     * according to the PDF standard.
     * 
     * @return this object as a string
     */
    @Override
    public String toString() {
        return String.format(INDIRECT_OBJECT_FORMAT, 
                indirectObjectCount,
                _object.getTextLines().indentAllLinesBy(2));
    }
}
