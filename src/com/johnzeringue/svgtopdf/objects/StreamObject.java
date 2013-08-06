package com.johnzeringue.svgtopdf.objects;

import com.johnzeringue.svgtopdf.util.Text;
import java.util.Objects;

/**
 * 
 *
 * @author John Zeringue
 */
public class StreamObject implements DirectObject {
    
    private DictionaryObject _dictionary;
    private Text _contents;
    
    public StreamObject() {
        _dictionary = new DictionaryObject();
        _contents = new Text();
    }
    
    public StreamObject append(String text) {
        _contents.append(text);
        
        return this;
    }
    
    public StreamObject append(Text text) {
        _contents.append(text);
        
        return this;
    }

    @Override
    public Text getText() {
        Text result = new Text();
        
        _dictionary.addEntry(new NameObject("Length"),
                new IntegerObject(_contents.toString().length()));
        
        result.append(_dictionary.getText());
        result.append("stream");
        result.append(_contents);
        result.append("endstream");
        
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
