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
    
    public TextLines getTextLines() {
        return new TextLines().appendLine(String.valueOf(_value));
    }
}
