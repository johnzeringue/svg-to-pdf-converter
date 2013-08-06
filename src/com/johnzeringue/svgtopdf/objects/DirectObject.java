package com.johnzeringue.svgtopdf.objects;

import com.johnzeringue.svgtopdf.util.TextLines;

/**
 * A common interface for direct objects.
 * 
 * @author John Zeringue
 */
public interface DirectObject {
    /**
     * Returns the lines of text for this object
     * 
     * @return this objects text
     */
    public TextLines getTextLines();
}
