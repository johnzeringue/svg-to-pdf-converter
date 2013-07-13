/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnzeringue.SVGToPDFConverter.PDFObjects;

import com.johnzeringue.svgtopdf.objects.TextLines;
import com.johnzeringue.svgtopdf.objects.TextLine;
import java.util.Iterator;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author John
 */
public class TextLinesTest {

    /**
     * Test of insertLineAt method, of class TextLines.
     */
    @Test
    public void testInsertLineAt() {
        int index = 1;
        TextLine aLine = new TextLine("Second");
        TextLines instance = (new TextLines())
                .appendLine("First")
                .appendLine("Third");
        TextLines expResult = (new TextLines())
                .appendLine("First")
                .appendLine("Second")
                .appendLine("Third");
        TextLines result = instance.insertLineAt(index, aLine);
        assertEquals(expResult, result);
    }

    /**
     * Test of prependLine method, of class TextLines.
     */
    @Test
    public void testPrependLine() {
        TextLine aLine = new TextLine("First");
        TextLines instance = (new TextLines())
                .appendLine("Second")
                .appendLine("Third");
        TextLines expResult = (new TextLines())
                .appendLine("First")
                .appendLine("Second")
                .appendLine("Third");
        TextLines result = instance.prependLine(aLine);
        assertEquals(expResult, result);
    }

    /**
     * Test of getLineAt method, of class TextLines.
     */
    @Test
    public void testGetLineAt() {
        int index = 2;
        TextLines instance = (new TextLines())
                .appendLine("First")
                .appendLine("Second")
                .appendLine("Third");
        TextLine expResult = new TextLine("Third");
        TextLine result = instance.getLineAt(index);
        assertEquals(expResult, result);
    }

    /**
     * Test of size method, of class TextLines.
     */
    @Test
    public void testSize() {
        TextLines instance = (new TextLines())
                .appendLine("First")
                .appendLine("Second")
                .appendLine("Third");
        int expResult = 3;
        int result = instance.size();
        assertEquals(expResult, result);
    }

    /**
     * Test of indentAllLinesBy method, of class TextLines.
     */
    @Test
    public void testIndentAllLinesBy() {
        int indentionSize = 3;
        TextLines instance = (new TextLines())
                .appendLine("First")
                .appendLine("Second")
                .appendLine("Third");
        TextLines expResult = (new TextLines())
                .appendLine("   First")
                .appendLine("   Second")
                .appendLine("   Third");
        TextLines result = instance.indentAllLinesBy(indentionSize);
        assertEquals(expResult, result);
    }

    /**
     * Test of indentTailLinesBy method, of class TextLines.
     */
    @Test
    public void testIndentTailLinesBy() {
        int indentionSize = 2;
        TextLines instance = (new TextLines())
                .appendLine("First")
                .appendLine("Second")
                .appendLine("Third");
        TextLines expResult = (new TextLines())
                .appendLine("First")
                .appendLine("  Second")
                .appendLine("  Third");
        TextLines result = instance.indentTailLinesBy(indentionSize);
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class TextLines.
     */
    @Test
    public void testEquals() {
        // Equal
        Object obj1 = (new TextLines())
                .appendLine("First")
                .appendLine("Second")
                .appendLine("Third");
        TextLines instance1 = (new TextLines())
                .appendLine("First")
                .appendLine("Second")
                .appendLine("Third");
        boolean expResult1 = true;
        boolean result1 = instance1.equals(obj1);
        assertEquals(expResult1, result1);

        // Unequal due to ordering
        Object obj2 = (new TextLines())
                .appendLine("First")
                .appendLine("Second")
                .appendLine("Third");
        TextLines instance2 = (new TextLines())
                .appendLine("First")
                .appendLine("Third")
                .appendLine("Second");
        boolean expResult2 = false;
        boolean result2 = instance2.equals(obj2);
        assertEquals(expResult2, result2);
    }

    /**
     * Test of toString method, of class TextLines.
     */
    @Test
    public void testToString() {
        TextLines instance = (new TextLines())
                .appendLine("First")
                .appendLine("Second")
                .appendLine("Third");
        String expResult = "First\nSecond\nThird";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
}
