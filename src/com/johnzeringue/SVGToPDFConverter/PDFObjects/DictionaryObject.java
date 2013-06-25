package com.johnzeringue.SVGToPDFConverter.PDFObjects;

/**
 *
 *
 * @author John Zeringue
 */
public class DictionaryObject implements DirectObject {

    private Lines entries;

    public DictionaryObject() {
        entries = new Lines();
    }

    public DictionaryObject addEntry(NameObject key, DirectObject value) {
        int keyLength = key.getLines().toString().length();
        
        entries.addLine(String.format("%s %s",
                key.getLines(),
                value.getLines().indentTailBy(keyLength + 1)));

        return this;
    }

    @Override
    public Lines getLines() {
        if (entries.getLength() == 0) {
            return (new Lines()).addLine("<< >>");
        } else if (entries.getLength() == 1) {
            return (new Lines()).addLine("<< " + entries.getLine(0) + " >>");
        } else {
            Lines dictLines = new Lines();
            
            dictLines.addLine("<< " + entries.getLine(0));
            for (int i = 1; i < entries.getLength() - 1; i++) {
                dictLines.addLine("   " + entries.getLine(i));
            }
            dictLines.addLine("   " + entries.getLine(entries.getLength() - 1) + " >>");
            
            return dictLines;
        }
    }
}
