package com.johnzeringue.svgtopdf.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * An object representing a set of zero or more lines of text.
 *
 * @author John Zeringue
 */
public final class Text implements Iterable<StringBuilder> {

    private List<StringBuilder> _lines;

    public Text() {
        _lines = new ArrayList<>();
    }
    
    public Text(CharSequence text) {
        this();
        
        for (CharSequence aLine : text.toString().split("\\n")) {
            _lines.add(new StringBuilder(aLine));
        }
    }
    
    public Text(CharSequence[] lines) {
        this();
        
        for (CharSequence aLine : lines) {
            append(aLine);
        }
    }
    
    public Text append(Text text) {
        for (StringBuilder aLine : text) {
            _lines.add(aLine);
        }
        
        return this;
    }
    
    public Text append(CharSequence text) {
        return append(new Text(text));
    }
    
    public Text insertAtLine(int lineIndex, Text text) {
        for (StringBuilder aLine : text) {
            _lines.add(lineIndex++, aLine);
        }
        
        return this;
    }
    
    public Text insertAtLine(int lineIndex, CharSequence text) {
        return insertAtLine(lineIndex, new Text(text));
    }

    /**
     * Returns the line of text at the given index.
     *
     * @param index the index of the desired line of text
     * @return the requested line of text
     */
    public StringBuilder getLineAt(int index) {
        return _lines.get(index);
    }

    public Text removeLineAt(int index) {
        _lines.remove(index);

        return this;
    }

    /**
     * Returns the size of this set.
     *
     * @return the size of this set
     */
    public int lineCount() {
        return _lines.size();
    }

    /**
     * Indents all lines by the given amount by prepending spaces.
     *
     * @param indentionSize the size of the indention in spaces
     * @return a reference to this object
     */
    public Text indentAllLinesBy(int indentionSize) {
        String indention = buildIndention(indentionSize);

        for (StringBuilder aLine : this) {
            aLine.insert(0, indention);
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
    public Text indentTailLinesBy(int indentionSize) {
        String indention = buildIndention(indentionSize);

        for (int i = 1; i < this.lineCount(); i++) {
            this.getLineAt(i).insert(0, indention);
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
    public Iterator<StringBuilder> iterator() {
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
        final Text other = (Text) obj;
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

        if (this.lineCount() > 0) {
            for (int i = 0; i < this.lineCount() - 1; i++) {
                result.append(this.getLineAt(i).toString());
                result.append("\n");
            }
            result.append(this.getLineAt(this.lineCount() - 1));
        }
        
        return result.toString();
    }
}
