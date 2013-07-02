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

    public ArrayObject() {
        _contents = new ArrayList<>();
    }

    public ArrayObject add(DirectObject anObject) {
        _contents.add(anObject);

        return this;
    }

    @Override
    public TextLines getTextLines() {
        TextLines result = new TextLines();

        for (DirectObject anObject : _contents) {
            for (TextLine aLine : anObject.getTextLines()) {
                result.appendLine(aLine);
            }
        }

        result.indentTailLinesBy(2);
        result.getLineAt(0).prepend("[ ");
        result.getLineAt(result.size() - 1).append(" ]");

        return result;
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
