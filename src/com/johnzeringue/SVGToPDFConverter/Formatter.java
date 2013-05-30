package com.johnzeringue.SVGToPDFConverter;

/**
 * A class of static methods that format strings into different PDF features.
 * 
 * @author  John Zeringue
 * @version 4/24/2013
 */
public class Formatter {
    public static String STREAM_FORMAT = ""
            + "<</Length %d>>\n"
            + "stream\n"
            + "%s\n"
            + "endstream";
    
    public static String formatAsStream(String contents) {
        return String.format(STREAM_FORMAT, contents.length(), contents);
    }
}
