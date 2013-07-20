package com.johnzeringue.svgtopdf;

import java.awt.geom.Point2D;
import java.io.File;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.FileFilter;
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
        Timer t = new Timer();

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
            saxParser.parse(svgInput,
                    new SVGToPDFHandler(pdfOutput, getMaxAndMin(svgInput)));
            
            System.out.printf("Generated %s in %d milliseconds.\n",
                    pdfOutput.getName(), t.check());
        }

        System.out.println("All files have been converted.");
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