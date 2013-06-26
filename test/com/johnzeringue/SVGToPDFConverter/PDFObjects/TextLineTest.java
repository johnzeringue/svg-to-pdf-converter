/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnzeringue.SVGToPDFConverter.PDFObjects;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author John
 */
public class TextLineTest {

    /**
     * Test of append method, of class Line.
     */
    @Test
    public void testAppend() {
        CharSequence s = ", World";
        TextLine instance = new TextLine("Hello");
        TextLine expResult = new TextLine("Hello, World");
        TextLine result = instance.append(s);
        assertEquals(expResult, result);
    }

    /**
     * Test of insertAt method, of class Line.
     */
    @Test
    public void testInsertAt() {
        int index = 2;
        CharSequence s = "llo, Wor";
        TextLine instance = new TextLine("Held");
        TextLine expResult = new TextLine("Hello, World");
        TextLine result = instance.insertAt(index, s);
        assertEquals(expResult, result);
    }

    /**
     * Test of prepend method, of class Line.
     */
    @Test
    public void testPrepend() {
        CharSequence s = "Hello, ";
        TextLine instance = new TextLine("World");
        TextLine expResult = new TextLine("Hello, World");
        TextLine result = instance.prepend(s);
        assertEquals(expResult, result);
    }

    /**
     * Test of length method, of class Line.
     */
    @Test
    public void testLength() {
        TextLine instance = new TextLine("Hello, World");
        int expResult = 12;
        int result = instance.length();
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class Line.
     */
    @Test
    public void testEquals() {
        // Comparing two equivalent lines
        Object obj1 = new TextLine("Hello, World");
        TextLine instance1 = new TextLine("Hello, World");
        boolean expResult1 = true;
        boolean result1 = instance1.equals(obj1);
        assertEquals(expResult1, result1);
        
        // Comparing two unequivalent lines
        Object obj2 = new TextLine("Goodbye, World");
        TextLine instance2 = new TextLine("Hello, World");
        boolean expResult2 = false;
        boolean result2 = instance2.equals(obj2);
        assertEquals(expResult2, result2);
    }

    /**
     * Test of toString method, of class Line.
     */
    @Test
    public void testToString() {
        TextLine instance = new TextLine("Hello, World");
        String expResult = "Hello, World";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
}
