/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnzeringue.svgtopdf;

import java.awt.geom.Point2D;
import java.io.File;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 *
 *
 * @author John Zeringue
 */
public class SVGToPDFConverter {

    private SAXParserFactory spf;
    private SAXParser saxParser;

    public SVGToPDFConverter() throws Exception {
        spf = SAXParserFactory.newInstance();
        saxParser = spf.newSAXParser();
    }

    public void convert(File svgInput, ConverterOption... flags) throws Exception {
        convert(svgInput, new File(svgInput.getPath().replace(".*\\.svg", ".pdf")), flags);
    }

    public void convert(String svgInput, ConverterOption... flags) throws Exception {
        convert(new File(svgInput), flags);
    }

    public void convert(File svgInput, File pdfOutput, ConverterOption... flags) throws Exception {
        saxParser.parse(svgInput,
                new SVGToPDFHandler(pdfOutput, getMaxAndMin(svgInput)));
    }

    public void convert(String svgInput, String pdfOutput, ConverterOption... flags) throws Exception {
        convert(new File(svgInput), new File(pdfOutput), flags);
    }

    private static Point2D.Double getMaxAndMin(File file) throws Exception {
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
