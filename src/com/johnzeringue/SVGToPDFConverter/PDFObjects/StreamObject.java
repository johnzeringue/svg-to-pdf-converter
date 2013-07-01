package com.johnzeringue.SVGToPDFConverter.PDFObjects;

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

}
