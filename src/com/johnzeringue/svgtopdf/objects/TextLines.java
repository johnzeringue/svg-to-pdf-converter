package com.johnzeringue.svgtopdf.objects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * An object representing  a set of zero or more lines of text.
 *
 * @author John Zeringue
 */
public class TextLines implements Iterable<TextLine> {
    private List<TextLine> _lines;
    
    public TextLines() {
        _lines = new ArrayList<>();
    }
    
    /**
     * Appends the given line of text
     * 
     * @param aLine a line of text to append to this set
     * @return a reference to this object
     */
    public TextLines appendLine(TextLine aLine) {
        _lines.add(aLine);
        
        return this;
    }
    
    /**
     * Appends the given line of text
     * 
     * @param aLine a line of text to append to this set
     * @return a reference to this object
     */
    public TextLines appendLine(String aLine) {
        return this.appendLine(new TextLine(aLine));
    }
    
    /**
     * Inserts the given line of text at the specified index.
     * 
     * @param index the index at which to insert
     * @param aLine the line of text to insert
     * @return a reference to this object
     */
    public TextLines insertLineAt(int index, TextLine aLine) {
        _lines.add(index, aLine);
        
        return this;
    }
    
    /**
     * Inserts the given line of text at the specified index.
     * 
     * @param index the index at which to insert
     * @param aLine the line of text to insert
     * @return a reference to this object
     */
    public TextLines insertLineAt(int index, String aLine) {
        return this.insertLineAt(index, new TextLine(aLine));
    }
    
    /**
     * Prepends the given line of text to this set.
     * 
     * @param aLine a line of text to prepend
     * @return a reference to this object
     */
    public TextLines prependLine(TextLine aLine) {
        return this.insertLineAt(0, aLine);
    }
    
    /**
     * Prepends the given line of text to this set.
     * 
     * @param aLine a line of text to prepend
     * @return a reference to this object
     */
    public TextLines prependLine(String aLine) {
        return this.insertLineAt(0, new TextLine(aLine));
    }
    
    /**
     * Returns the line of text at the given index.
     * 
     * @param index the index of the desired line of text
     * @return the requested line of text
     */
    public TextLine getLineAt(int index) {
        return _lines.get(index);
    }
    
    /**
     * Returns the size of this set.
     * 
     * @return the size of this set
     */
    public int size() {
        return _lines.size();
    }
    
    /**
     * Indents all lines by the given amount by prepending spaces.
     * 
     * @param indentionSize the size of the indention in spaces
     * @return a reference to this object
     */
    public TextLines indentAllLinesBy(int indentionSize) {
        String indention = buildIndention(indentionSize);
        
        for (TextLine aLine : this) {
            aLine.prepend(indention);
        }
        
        return this;
    }
    
    /**
     * Indents the "tail" lines by the given amount by prepending spaces. The
     * tail lines are all but the first line.
     * 
     * @param indentionSize the size of the indention in spaces
     * @return a reference to this object
     */
    public TextLines indentTailLinesBy(int indentionSize) {
        String indention = buildIndention(indentionSize);
        
        for (int i = 1; i < this.size(); i++) {
            this.getLineAt(i).prepend(indention);
        }
        
        return this;
    }

    /**
     * An internal method for building indention strings.
     * 
     * @param indentionSize the size of the indention
     * @return the indention as a string
     */
    private String buildIndention(int indentionSize) {
        String indention = "";
        
        for (int i = 0; i < indentionSize; i++) {
            indention += " ";
        }
        
        return indention;
    }

    /**
     * Returns this object's iterator, which cycles through all its lines in
     * order.
     * 
     * @return this object's iterator
     */
    @Override
    public Iterator<TextLine> iterator() {
        return _lines.iterator();
    }

    /**
     * Netbeans' default hashCode function.
     * 
     * @return a hash representing this object
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + Objects.hashCode(this._lines);
        return hash;
    }

    /**
     * Returns true if the two sets of lines contain equivalent lines in the
     * same order.
     * 
     * @param obj the object to compare to
     * @return whether the two objects are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TextLines other = (TextLines) obj;
        if (!Objects.equals(this._lines, other._lines)) {
            return false;
        }
        return true;
    }

    /**
     * Converts this object to a string by concatenating the string
     * representations of its lines with newlines in order. Note that the result
     * should not end with a newline.
     * 
     * @return 
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < this.size() - 1; i++) {
            result.append(this.getLineAt(i).toString());
            result.append("\n");
        }
        result.append(this.getLineAt(this.size() - 1));
        
        return result.toString();
    }
}
