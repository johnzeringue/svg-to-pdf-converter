/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnzeringue.SVGToPDFConverter.PDFObjects;

/**
 *
 * @author john
 */
public class NameObject implements DirectObject {
    private String _value;
    
    public NameObject(String value) {
        _value = value;
    }

    @Override
    public TextLines getTextLines() {
        return new TextLines().appendLine("/" + _value);
    }
    
}
