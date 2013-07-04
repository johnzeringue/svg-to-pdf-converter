package com.johnzeringue.SVGToPDFConverter.PDFObjects;

/**
 * 
 *
 * @author John Zeringue
 */
public class IntegerObject implements DirectObject {
    private int _value;
    
    public IntegerObject(int value) {
        _value = value;
    }
    
    public IntegerObject(String value) {
        this(Integer.parseInt(value));
    }
    
    @Override
    public TextLines getTextLines() {
        return new TextLines().appendLine(String.valueOf(_value));
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + this._value;
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
        final IntegerObject other = (IntegerObject) obj;
        if (this._value != other._value) {
            return false;
        }
        return true;
    }
}
