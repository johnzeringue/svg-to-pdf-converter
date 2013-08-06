package com.johnzeringue.svgtopdf;

import com.johnzeringue.svgtopdf.objects.DictionaryObject;
import com.johnzeringue.svgtopdf.objects.IndirectObject;
import com.johnzeringue.svgtopdf.objects.IntegerObject;
import com.johnzeringue.svgtopdf.objects.ObjectReference;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import com.johnzeringue.svgtopdf.util.TextLines;

/**
 * 
 *
 * @author John Zeringue
 */
public class PDFWriter {
    private final static String VERSION_FORMAT = "%%PDF-%1.1f\n";
    private final static String XREF_TABLE_ENTRY_FORMAT = "%010d %05d %c \n";
    
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
    
    public IndirectObject writeIndirectObject(IndirectObject anObject) {
        _objectIndices.add(_index);
        writeln(anObject.toString());
        
        return anObject;
    }
    
    public void writeCrossReferenceTable() {
        _xrefIndex = _index;
        
        writeln("xref");
        writeln("0 " + (_objectIndices.size() + 1));
        write(String.format(XREF_TABLE_ENTRY_FORMAT, 0, 65535, 'f'));
        
        for (Integer i : _objectIndices) {
            write(String.format(XREF_TABLE_ENTRY_FORMAT, i, 0, 'n'));
        }
        
        writeln();
    }
    
    public void writeTrailer(ObjectReference root) {
        DictionaryObject trailerDictionary = new DictionaryObject();
        
        writeln("trailer");
        trailerDictionary
                .addEntry("Size", new IntegerObject(_objectIndices.size() + 1))
                .addEntry("Root", root);
        writeln(trailerDictionary
                .getTextLines().indentAllLinesBy(2).toString());
        
        writeln("startxref");
        writeln(String.valueOf(_xrefIndex));
        write("%%EOF");
        
        _writer.close();
    }
}
