package com.johnzeringue.svgtopdf.objects;

import java.util.Objects;

/**
 * 
 *
 * @author John Zeringue
 */
public class StreamObject implements DirectObject {
    
    private DictionaryObject _dictionary;
    private TextLines _contents;
    
    public StreamObject() {
        _dictionary = new DictionaryObject();
        _contents = new TextLines();
    }
    
    public StreamObject appendLine(String aLine) {
        _contents.appendLine(new TextLine(aLine));
        
        return this;
    }
    
    public StreamObject appendLine(TextLine aLine) {
        _contents.appendLine(aLine);
        
        return this;
    }

    @Override
    public TextLines getTextLines() {
        TextLines result = new TextLines();
        
        _dictionary.addEntry(new NameObject("Length"),
                new IntegerObject(_contents.toString().length()));
        
        for (TextLine aLine : _dictionary.getTextLines()) {
            result.appendLine(aLine);
        }
        
        result.appendLine("stream");
        
        for (TextLine aLine : _contents) {
            result.appendLine(aLine);
        }
        
        result.appendLine("endstream");
        
        return result;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this._dictionary);
        hash = 41 * hash + Objects.hashCode(this._contents);
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
        final StreamObject other = (StreamObject) obj;
        if (!Objects.equals(this._dictionary, other._dictionary)) {
            return false;
        }
        if (!Objects.equals(this._contents, other._contents)) {
            return false;
        }
        return true;
    }

}
