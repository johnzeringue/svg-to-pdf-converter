package com.johnzeringue.svgtopdf.objects;

import com.johnzeringue.svgtopdf.util.TextLines;

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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + this._indirectObjectNumber;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ObjectReference other = (ObjectReference) obj;
        if (this._indirectObjectNumber != other._indirectObjectNumber) {
            return false;
        }
        return true;
    }
}
