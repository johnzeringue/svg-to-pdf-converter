package com.johnzeringue.SVGToPDFConverter.PDFObjects;

import java.util.ArrayList;
import java.util.List;

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
}
