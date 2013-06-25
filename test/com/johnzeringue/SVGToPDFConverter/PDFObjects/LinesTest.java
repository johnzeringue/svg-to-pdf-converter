/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnzeringue.SVGToPDFConverter.PDFObjects;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author john
 */
public class LinesTest {
    
    public LinesTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addLine method, of class Lines.
     */
    @org.junit.Test
    public void testAddLine() {
        System.out.println("addLine");
        String aLine = "";
        Lines instance = new Lines();
        Lines expResult = null;
        Lines result = instance.addLine(aLine);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLine method, of class Lines.
     */
    @org.junit.Test
    public void testGetLine() {
        System.out.println("getLine");
        int lineNumber = 0;
        Lines instance = new Lines();
        String expResult = "";
        String result = instance.getLine(lineNumber);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLength method, of class Lines.
     */
    @org.junit.Test
    public void testGetLength() {
        System.out.println("getLength");
        Lines instance = new Lines();
        int expResult = 0;
        int result = instance.getLength();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of indentBy method, of class Lines.
     */
    @org.junit.Test
    public void testIndentBy() {
        System.out.println("indentBy");
        int indentionSize = 0;
        Lines instance = new Lines();
        Lines expResult = null;
        Lines result = instance.indentBy(indentionSize);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of indentTailBy method, of class Lines.
     */
    @org.junit.Test
    public void testIndentTailBy() {
        System.out.println("indentTailBy");
        int indentionSize = 0;
        Lines instance = new Lines();
        Lines expResult = null;
        Lines result = instance.indentTailBy(indentionSize);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Lines.
     */
    @org.junit.Test
    public void testToString() {
        System.out.println("toString");
        Lines instance = new Lines();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}