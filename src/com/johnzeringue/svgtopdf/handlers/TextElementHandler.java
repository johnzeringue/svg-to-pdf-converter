package com.johnzeringue.svgtopdf.handlers;

import Jama.Matrix;
import com.johnzeringue.svgtopdf.Fonts;
import com.johnzeringue.svgtopdf.objects.DirectObject;
import com.johnzeringue.svgtopdf.objects.StreamObject;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * An ElementHandler for Text elements
 *
 * @author John Zeringue
 * @version 05/29/2013
 */
public class TextElementHandler extends ElementHandler {

    private static final String WSP = "\\s";
    private static final String WSPS = WSP + "*";
    private static final String COMMA_WSP = WSPS + "," + WSPS;
    private static final String NUMBER = "(-?\\d+(?:\\.\\d+)?)";
    private static final String TRANSLATE_PATTERN =
            "translate" + WSPS + "(" + WSPS + NUMBER + COMMA_WSP + NUMBER
            + WSPS + ")" + WSPS;
    private static final String ROTATE_PATTERN =
            "rotate" + WSPS + "(" + WSPS + NUMBER + WSPS + ")" + WSPS;
    private GElementHandler _gElementHandler;
    private StreamObject _object;
    private StringBuilder _tagContents;
    private Pattern _translatePattern;
    private Pattern _rotatePattern;
    private Matrix _transformMatrix;

    public TextElementHandler() {
        super();
        _gElementHandler = new GElementHandler();
        _tagContents = new StringBuilder();
        _object = new StreamObject();
        _translatePattern = Pattern.compile(TRANSLATE_PATTERN);
        _rotatePattern = Pattern.compile(ROTATE_PATTERN);
        _transformMatrix = Matrix.identity(3, 3);
    }

    /**
     * Parser calls this for each element in the document. This will process
     * element and either ignore it, store its data, or write it to the PDF
     * file.
     *
     * @param namespaceURI
     * @param localName
     * @param qName
     * @param atts
     * @throws SAXException
     */
    @Override
    public void startElement(String namespaceURI, String localName,
            String qName, Attributes atts) throws SAXException {
        _gElementHandler.startElement(namespaceURI, localName, qName, atts);

        _object.appendLine("BT");

        String fontFamily = docAtts.getValue("font-family").replaceAll("'", "");
        Double fontSize = Double.valueOf(docAtts.getValue("font-size"));

        parseTransform();

        _object.appendLine("0.0 g");
        _object.appendLine(String.format("%s %.1f Tf",
                Fonts.getInstance().getFontTag(fontFamily), fontSize));

        double height = docAtts.getHeight();

        _object.appendLine(String.format("%f %f Td",
                Double.parseDouble(atts.getValue("x")),
                height - Double.parseDouble(atts.getValue("y"))));
    }

    @Override
    public void characters(char ch[], int start, int length) {
        _tagContents.append(ch, start, length);
    }

    /**
     * Parser calls this for each element in the document.
     *
     * @param namespaceURI
     * @param localName
     * @param qName
     * @throws SAXException
     */
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        _object.appendLine(String.format("(%s) Tj", _tagContents));
        _object.appendLine("ET");

        _gElementHandler.endElement(namespaceURI, localName, qName);
    }

    private void parseTransform() {
        Matrix transformMatrix;

        String transform = docAtts.getValue("transform");

        while (transform != null && !transform.equals("")) {
            switch (transform.charAt(0)) {
                case 'r':
                    transform = parseRotate(transform);
                    break;
                case 't':
                    transform = parseTranslate(transform);
                    break;
            }
        }
        
        // Write transform matrix to stream
    }

    private String parseRotate(String s) {
        Matcher m = _rotatePattern.matcher(s);

        if (m.lookingAt()) {
            double tx = Double.parseDouble(m.group(1));
            double ty = -1 * Double.parseDouble(m.group(2));

            return s.replaceFirst(ROTATE_PATTERN, "");
        } else {
            throw new IllegalArgumentException("Unrecognized rotate format");
        }
    }

    private String parseTranslate(String s) {
        Matcher m = _translatePattern.matcher(s);

        if (m.lookingAt()) {
            // parse match

            return s.replaceFirst(TRANSLATE_PATTERN, s);
        } else {
            throw new IllegalArgumentException("Unrecognized translate format");
        }
    }

    @Override
    public DirectObject getDirectObject() {
        return _object;
    }

    @Override
    public boolean hasDirectObject() {
        return true;
    }
}
