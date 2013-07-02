package com.johnzeringue.SVGToPDFConverter.PDFObjects;

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
    private TextLines _textValue;

    public DictionaryObject() {
        _map = new HashMap<>();
        _hasChanged = false;
        _textValue = new TextLines().appendLine("<< >>");
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
    public TextLines getTextLines() {
        if (_hasChanged) {
            _textValue = new TextLines();

            int keyLength;
            for (Map.Entry<NameObject, DirectObject> anEntry : _map.entrySet()) {
                keyLength = anEntry.getKey().getTextLines().getLineAt(0).length();

                _textValue.appendLine(
                        String.format("%s %s",
                                      anEntry.getKey().getTextLines(),
                                      anEntry.getValue().getTextLines().getLineAt(0)));

                for (int i = 1; i < anEntry.getValue().getTextLines().size(); i++) {
                    _textValue.appendLine(
                            anEntry.getValue().getTextLines()
                            .indentTailLinesBy(keyLength + 1).getLineAt(i));
                }
            }

            _textValue.indentTailLinesBy(3);
            _textValue.getLineAt(0).prepend("<< ");
            _textValue.getLineAt(_textValue.size() - 1).append(" >>");

            _hasChanged = false;
        }

        return _textValue;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + (this._hasChanged ? 1 : 0);
        hash = 13 * hash + Objects.hashCode(this._map);
        hash = 13 * hash + Objects.hashCode(this._textValue);
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
        if (!Objects.equals(this._map, other._map)) {
            return false;
        }
        return true;
    }
}
