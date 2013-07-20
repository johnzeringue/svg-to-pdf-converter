package com.johnzeringue.svgtopdf;

import java.io.File;
import java.io.FileFilter;
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
public class SVGToPDFConverterTest {

    /**
     * Test of convert method, of class SVGToPDFConverter.
     */
    @Test
    public void testConvert_File_ConverterOptionArr() throws Exception {
        Timer t = new Timer();
        SVGToPDFConverter svgToPDF = new SVGToPDFConverter();

        File[] testFiles = new File("samples").listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().matches(".*\\.svg");
            }
        });

        for (File svgInput : testFiles) {
            String pdfOutput = svgInput.getPath().replace(".svg", ".pdf");

            System.out.printf("Converting %s...\n", svgInput.getName());

            t.start();
            
            svgToPDF.convert(svgInput);

            System.out.printf("Generated %s in %d milliseconds.\n",
                    pdfOutput, t.check());
        }
    }
}