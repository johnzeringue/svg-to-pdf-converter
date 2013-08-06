package com.johnzeringue.svgtopdf.objects;

import com.johnzeringue.svgtopdf.util.Text;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * An object representing a dictionary object in a PDF file.
 *
 * @author John Zeringue
 */
public class DictionaryObject implements DirectObject {

    private boolean _hasChanged;
    private Map<NameObject, DirectObject> _map;
    private Text _textValue;

    public DictionaryObject() {
        _map = new HashMap<>();
        _hasChanged = false;
        _textValue = new Text().append("<< >>");
    }

    public DictionaryObject addEntry(String key, DirectObject value) {
        return addEntry(new NameObject(key), value);
    }

    public DictionaryObject addEntry(NameObject key, DirectObject value) {
        _map.put(key, value);
        _hasChanged = true;

        return this;
    }
    
    public DirectObject getValue(String key) {
        return getValue(new NameObject(key));
    }
    
    public DirectObject getValue(NameObject key) {
        return _map.get(key);
    }
    
    public boolean containsKey(String key) {
        return containsKey(new NameObject(key));
    }
    
    public boolean containsKey(NameObject key) {
        return _map.containsKey(key);
    }

    @Override
    public Text getText() {
        if (_hasChanged) {
            _textValue = new Text();

            int keyLength;
            for (Map.Entry<NameObject, DirectObject> anEntry : _map.entrySet()) {
                keyLength = anEntry.getKey().getText().getLineAt(0).length();

                _textValue.append(
                        String.format("%s %s",
                                      anEntry.getKey().getText(),
                                      anEntry.getValue().getText().getLineAt(0)));
                
                anEntry.getValue().getText().indentTailLinesBy(keyLength + 1);

                for (int i = 1; i < anEntry.getValue().getText().lineCount(); i++) {
                    _textValue.append(
                            anEntry.getValue().getText().getLineAt(i));
                }
            }

            _textValue.indentTailLinesBy(3);
            _textValue.getLineAt(0).insert(0, "<< ");
            _textValue.getLineAt(_textValue.lineCount() - 1).append(" >>");

            _hasChanged = false;
        }

        return _textValue;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this._map);
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
        final DictionaryObject other = (DictionaryObject) obj;
        return Objects.equals(this._map, other._map);
    }
}
