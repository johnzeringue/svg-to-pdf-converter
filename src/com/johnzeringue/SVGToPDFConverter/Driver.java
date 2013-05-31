package com.johnzeringue.SVGToPDFConverter;

import java.io.File;
import java.io.FileInputStream;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * A driver to test SVGToPDFConverter
 *
 * @author John Zeringue
 * @version 04/03/2013
 */
public class Driver {

    /**
     * The test driver
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
        // Initialize SAX components
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser saxParser = spf.newSAXParser();

        // Set System L&F
        UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName());

        // Create a new file chooser
        JFileChooser fileChooser = new JFileChooser("./samples");
        fileChooser.setFileFilter(
                new FileNameExtensionFilter("SVG file", "svg"));

        // Let the user choose an SVG file and convert it
        if (fileChooser.showDialog(
                null, "Convert") == JFileChooser.APPROVE_OPTION) {
            File svgInput = fileChooser.getSelectedFile();
            File pdfOutput =
                    new File(svgInput.getPath().replace(".svg", ".pdf"));
            
            System.out.println("Working...");

            // Parse the file
            saxParser.parse(new FileInputStream(svgInput),
                    new SVGToPDFConverter(pdfOutput));

            System.out.println("Done!");
        }
    }
}