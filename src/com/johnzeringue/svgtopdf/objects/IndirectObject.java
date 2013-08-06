package com.johnzeringue.svgtopdf.objects;

import java.util.Objects;
import com.johnzeringue.svgtopdf.util.Text;

/**
 * A class representing an indirect object. It takes a direct object in its
 * constructor and then can output that object's object reference and its string
 * representation as an indirect object.
 *
 * @author John Zeringue
 */
public class IndirectObject {

    private final static String INDIRECT_OBJECT_FORMAT = ""
            + "%d 0 obj\n"
            + "%s\n"
            + "endobj\n";
    private DirectObject _object;
    private int _indirectObjectNumber;

    /**
     * Creates a new indirect object containing the provided direct object.
     *
     * @param aDirectObject this object's contents
     */
    public IndirectObject(int indirectObjectNumber, DirectObject aDirectObject) {
        _object = aDirectObject;
        _indirectObjectNumber = indirectObjectNumber;
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
                             _indirectObjectNumber,
                             _object.getText());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this._object);
        hash = 59 * hash + this._indirectObjectNumber;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() == obj.getClass()) {
            final IndirectObject other = (IndirectObject) obj;
            return Objects.equals(this._object, other._object);
        }
        if (obj.getClass() == DirectObject.class) {
            final DirectObject other = (DirectObject) obj;
            return Objects.equals(this._object, other);
        }
        return false;
    }
}
