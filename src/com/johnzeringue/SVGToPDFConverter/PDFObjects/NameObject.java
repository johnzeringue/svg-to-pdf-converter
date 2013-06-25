/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnzeringue.SVGToPDFConverter.PDFObjects;

/**
 *
 * @author john
 */
class NameObject implements DirectObject {
    private String value;
    
    public NameObject(String value) {
        this.value = value;
    }

    @Override
    public Lines getLines() {
        return (new Lines()).addLine("/" + value);
    }
    
}
