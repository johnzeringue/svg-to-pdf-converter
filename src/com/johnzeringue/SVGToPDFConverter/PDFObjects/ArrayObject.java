package com.johnzeringue.SVGToPDFConverter.PDFObjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 *
 * @author John
 */
public class ArrayObject implements DirectObject {

    private List<DirectObject> _contents;
    private boolean _hasChanged;
    private TextLines _text;

    public ArrayObject() {
        _contents = new ArrayList<>();
        _hasChanged = false;
        _text = new TextLines().appendLine("[ ]");
    }

    public ArrayObject add(DirectObject anObject) {
        _contents.add(anObject);
        _hasChanged = true;

        return this;
    }

    @Override
    public TextLines getTextLines() {
        if (_hasChanged) {
            _text = new TextLines();

            for (DirectObject anObject : _contents) {
                for (TextLine aLine : anObject.getTextLines()) {
                    _text.appendLine(aLine);
                }
            }

            _text.indentTailLinesBy(2);
            _text.getLineAt(0).prepend("[ ");
            _text.getLineAt(_text.size() - 1).append(" ]");

            _hasChanged = false;
        }

        return _text;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this._contents);
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
        final ArrayObject other = (ArrayObject) obj;
        if (!Objects.equals(this._contents, other._contents)) {
            return false;
        }
        return true;
    }
}
