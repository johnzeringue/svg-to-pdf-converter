package com.johnzeringue.SVGToPDFConverter.PDFObjects;

/**
 * An object representing a dictionary object in a PDF file.
 *
 * @author John Zeringue
 */
public class DictionaryObject implements DirectObject {

    private TextLines _entries;

    public DictionaryObject() {
        _entries = new TextLines();
    }
    
    public DictionaryObject addEntry(String key, DirectObject value) {
        return addEntry(new NameObject(key), value);
    }

    public DictionaryObject addEntry(NameObject key, DirectObject value) {
        int keyLength = key.getTextLines().toString().length();
        
        value.getTextLines().indentTailLinesBy(keyLength + 1);
        
        // Create the first line by adding the key to the first line of the
        // value
        _entries.appendLine(String.format("%s %s",
                key.getTextLines(),
                value.getTextLines().getLineAt(0)));
        
        // Add the rest of value onto the entries
        for (int i = 1; i < value.getTextLines().size(); i++) {
            _entries.appendLine(value.getTextLines().getLineAt(i));
        }

        return this;
    }

    @Override
    public TextLines getTextLines() {
        _entries.indentTailLinesBy(3);
        _entries.getLineAt(0).prepend("<< ");
        _entries.getLineAt(_entries.size() - 1).append(" >>");
        
        return _entries;
    }
}
