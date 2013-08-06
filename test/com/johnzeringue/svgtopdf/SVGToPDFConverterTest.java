/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnzeringue.svgtopdf;

import java.io.File;
import java.io.FileFilter;
import org.junit.Test;

/**
 *
 * @author john
 */
public class SVGToPDFConverterTest {

    /**
     * Test of convert method, of class SVGToPDFConverter.
     */
    @Test
    public void testConvert() throws Exception {
        Timer t = new Timer();
        SVGToPDFConverter svgToPDF = new SVGToPDFConverter();

        File[] testFiles = new File("./samples").listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().matches(".*\\.svg");
            }
        });

        for (File svgInput : testFiles) {
            File pdfOutput =
                    new File(svgInput.getPath().replace(".svg", ".pdf"));

            System.out.printf("Converting %s...\n", svgInput.getName());
            
            t.start();

            // Parse the file
            svgToPDF.convert(svgInput);
            
            System.out.printf("Generated %s in %d ms.\n",
                    pdfOutput.getName(), t.check());
        }

        System.out.println("All files have been converted.");
    }
}