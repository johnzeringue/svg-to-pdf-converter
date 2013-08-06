package com.johnzeringue.svgtopdf.handlers;

import com.johnzeringue.svgtopdf.Fonts;
import com.johnzeringue.svgtopdf.objects.DirectObject;
import com.johnzeringue.svgtopdf.objects.StreamObject;
import java.nio.charset.Charset;
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
            "translate" + WSPS + "\\(" + WSPS + NUMBER + COMMA_WSP + NUMBER
            + WSPS + "\\)" + WSPS;
    private static final String ROTATE_PATTERN =
            "rotate" + WSPS + "\\(" + WSPS + NUMBER + WSPS + "\\)" + WSPS;
    private GElementHandler _gElementHandler;
    private StreamObject _object;
    private StringBuilder _tagContents;
    private Pattern _translatePattern;
    private Pattern _rotatePattern;

    public TextElementHandler() {
        super();
        _gElementHandler = new GElementHandler();
        _tagContents = new StringBuilder();
        _object = new StreamObject();
        _translatePattern = Pattern.compile(TRANSLATE_PATTERN);
        _rotatePattern = Pattern.compile(ROTATE_PATTERN);
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

        _object.append("q");

        _object.append("BT");

        parseTransform();

        String fontFamily = docAtts.getValue("font-family").replaceAll("'", "");
        Double fontSize = Double.valueOf(docAtts.getValue("font-size"));

        _object.append("0.0 g");
        _object.append(String.format("%s %.1f Tf",
                Fonts.getInstance().getFontTag(fontFamily), fontSize));

        double height = docAtts.getHeight();

        _object.append(String.format("%f %f Td",
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
        int start = 0;
        for (int i = 0; i < _tagContents.length(); i++) {
            if (_tagContents.charAt(i) > 127) {
                _object.append(String.format("(%s) Tj", _tagContents.substring(start, i)));

                _object.append("q");

                Double fontSize = Double.valueOf(docAtts.getValue("font-size"));
                _object.append(String.format("%s %.1f Tf",
                        Fonts.getInstance().getFontTag("Symbol"), fontSize));

                if ((int) _tagContents.charAt(i) == 955) { // lambda
                    _object.append(String.format("(%s) Tj", (char) 108));
                } else if ((int) _tagContents.charAt(i) == 963) { // sigma
                    _object.append(String.format("(%s) Tj", (char) 115));
                } else { // plus minus
                    _object.append("(\\261) Tj");
                }
                
                start = i + 1;

                _object.append("Q");
            }
        }

        _object.append(String.format("(%s) Tj", _tagContents.substring(start)));

        _object.append("ET");

        _object.append("Q");

        _gElementHandler.endElement(namespaceURI, localName, qName);
    }

    private void parseTransform() {
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
    }

    private String parseRotate(String s) {
        Matcher m = _rotatePattern.matcher(s);

        if (m.lookingAt()) {
            double rotateAngle = -1 * Double.parseDouble(m.group(1)) * Math.PI / 180;

            _object.append(
                    String.format("%f %f %f %f %f %f cm",
                    1.0, 0.0, 0.0, 1.0, 0.0, docAtts.getHeight()));
            _object.append(
                    String.format("%f %f %f %f %f %f cm",
                    Math.cos(rotateAngle), Math.sin(rotateAngle),
                    -1 * Math.sin(rotateAngle), Math.cos(rotateAngle),
                    0.0, 0.0));
            _object.append(
                    String.format("%f %f %f %f %f %f cm",
                    1.0, 0.0, 0.0, 1.0, 0.0, -1 * docAtts.getHeight()));

            return s.substring(m.end());
        } else {
            throw new IllegalArgumentException("Unrecognized rotate format");
        }
    }

    private String parseTranslate(String s) {
        Matcher m = _translatePattern.matcher(s);

        if (m.lookingAt()) {
            double tx = Double.parseDouble(m.group(1));
            double ty = -1 * Double.parseDouble(m.group(2));

            _object.append(
                    String.format("%f %f %f %f %f %f cm",
                    1.0, 0.0, 0.0, 1.0, tx, ty));

            return s.substring(m.end());
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
