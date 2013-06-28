package com.johnzeringue.SVGToPDFConverter;

import com.johnzeringue.SVGToPDFConverter.PDFObjects.DictionaryObject;
import com.johnzeringue.SVGToPDFConverter.PDFObjects.IndirectObject;
import com.johnzeringue.SVGToPDFConverter.PDFObjects.IntegerObject;
import com.johnzeringue.SVGToPDFConverter.PDFObjects.ObjectReference;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 *
 * @author John Zeringue
 */
public class PDFWriter {
    private final static String VERSION_FORMAT = "%010d %05d %c\r\n";
    private final static String XREF_TABLE_ENTRY_FORMAT = "%010d %05d %c\r\n";
    
    private PrintWriter _writer;
    private int _index;
    private List<Integer> _objectIndices;
    private int _xrefIndex;
    
    public PDFWriter(File file) throws FileNotFoundException {
        _writer = new PrintWriter(file);
        _index = 0;
        _objectIndices = new ArrayList<>();
        _xrefIndex = 0;
    }

    /**
     * Writes the given string to the new PDF file.
     *
     * @param s the string to be written
     */
    private void write(String s) {
        _writer.print(s);
        _writer.flush(); // This is need to actually write the data.

        _index += s.length();
    }
    
    private void writeln() {
        write("\n");
    }
    
    private void writeln(String s) {
        write(s + "\n");
    }
    
    public void writeHeader(double version) {
        writeln(String.format(VERSION_FORMAT, version));
    }
    
    public void writeIndirectObject(IndirectObject anObject) {
        _objectIndices.add(_index);
        writeln(anObject.toString());
    }
    
    public void writeCrossReferenceTable() {
        _xrefIndex = _index;
        
        writeln("xref");
        writeln("0 " + _objectIndices.size());
        write(String.format(XREF_TABLE_ENTRY_FORMAT, 0, 65535, 'f'));
        
        for (Integer i : _objectIndices) {
            write(String.format(XREF_TABLE_ENTRY_FORMAT, i, 0, 'n'));
        }
        
        writeln();
    }
    
    public void writeFooter(ObjectReference root) {
        DictionaryObject trailerDictionary = new DictionaryObject();
        
        writeln("trailer");
        trailerDictionary
                .addEntry("Size", new IntegerObject(_objectIndices.size()))
                .addEntry("Root", root);
        writeln(trailerDictionary
                .getTextLines().indentAllLinesBy(2).toString());
        
        writeln("startxref");
        writeln(String.valueOf(_xrefIndex));
        write("%%EOF");
    }
}
