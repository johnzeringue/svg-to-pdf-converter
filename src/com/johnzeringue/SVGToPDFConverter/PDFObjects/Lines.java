package com.johnzeringue.SVGToPDFConverter.PDFObjects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author John Zeringue
 */
public class Lines {
    private List<String> lines;
    
    public Lines() {
        lines = new ArrayList<>();
    }
    
    public Lines addLine(String aLine) {
        lines.add(aLine);
        
        return this;
    }
    
    public String getLine(int lineNumber) {
        return lines.get(lineNumber);
    }
    
    public int getLength() {
        return lines.size();
    }
    
    public Lines indentBy(int indentionSize) {
        Lines indentedLines = new Lines();
        String indention = "";
        
        // Build indention string
        for (int i = 0; i < indentionSize; i++) {
            indention += " ";
        } 
        
        for (String aLine : lines) {
            indentedLines.addLine(indention + aLine);
        }
        
        return indentedLines;
    }
    
    public Lines indentTailBy(int indentionSize) {
        Lines indentedLines = new Lines();
        String indention = "";
        
        // Build indention string
        for (int i = 0; i < indentionSize; i++) {
            indention += " ";
        }
        
        indentedLines.addLine(lines.get(0));
        
        Iterator<String> linesIter = lines.iterator();
        // Skip first element
        if (linesIter.hasNext()) {
            linesIter.next();
        }
        while (linesIter.hasNext()) {
            indentedLines.addLine(indention + linesIter.next());
        }
        
        return indentedLines;
    }
    
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        
        for (String aLine : lines) {
            stringBuilder.append(aLine);
            stringBuilder.append("\n");
        }
        
        // Cut off the last newline character
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }
}
