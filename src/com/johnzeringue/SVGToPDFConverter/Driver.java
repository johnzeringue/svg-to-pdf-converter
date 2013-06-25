package com.johnzeringue.SVGToPDFConverter;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
                    new SVGToPDFConverter(pdfOutput, getMaxAndMin(svgInput)));

            System.out.println("Done!");
        }
    }

    public static Point2D.Double getMaxAndMin(File file) throws Exception {
        double maxX = 0;
        double maxY = 0;
        Scanner scan = new Scanner(file);
        String line;

        Pattern px = Pattern.compile("x\\d?\\w*=\\w*\"(\\d+(:?\\.\\d+)?)\"");
        Pattern py = Pattern.compile("y\\d?\\w*=\\w*\"(\\d+(:?\\.\\d+)?)\"");
        Matcher m;
        while (scan.hasNextLine()) {
            line = scan.nextLine();
            m = px.matcher(line);

            while (m.find()) {
                maxX = Math.max(maxX, Double.parseDouble(m.group(1)));
            }

            m = py.matcher(line);

            while (m.find()) {
                maxY = Math.max(maxY, Double.parseDouble(m.group(1)));
            }
        }

        return new Point2D.Double(maxX, maxY + 20);
    }
}