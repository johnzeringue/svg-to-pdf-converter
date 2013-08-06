package com.johnzeringue.svgtopdf;

import com.johnzeringue.svgtopdf.handlers.TextElementHandler;
import com.johnzeringue.svgtopdf.handlers.ClipPathElementHandler;
import com.johnzeringue.svgtopdf.handlers.GElementHandler;
import com.johnzeringue.svgtopdf.handlers.ElementHandler;
import com.johnzeringue.svgtopdf.handlers.SVGElementHandler;
import com.johnzeringue.svgtopdf.objects.RealObject;
import com.johnzeringue.svgtopdf.handlers.graphics.CircleElementHandler;
import com.johnzeringue.svgtopdf.handlers.graphics.LineElementHandler;
import com.johnzeringue.svgtopdf.handlers.graphics.PathElementHandler;
import com.johnzeringue.svgtopdf.handlers.graphics.RectElementHandler;
import com.johnzeringue.svgtopdf.objects.ArrayObject;
import com.johnzeringue.svgtopdf.objects.DictionaryObject;
import com.johnzeringue.svgtopdf.objects.DirectObject;
import com.johnzeringue.svgtopdf.objects.IndirectObject;
import com.johnzeringue.svgtopdf.objects.IntegerObject;
import com.johnzeringue.svgtopdf.objects.NameObject;
import com.johnzeringue.svgtopdf.objects.ObjectReference;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Stack;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * The DefaultHandler that is actually used by the XMLReader for converting SVGs
 * to PDFs. However, this functionality is not yet complete.
 *
 * @author John Zeringue
 * @version 05/29/2013
 */
public class SVGToPDFHandler extends DefaultHandler {

    private final static double DEFAULT_WIDTH = 1200;
    private final static double DEFAULT_HEIGHT = 800;
    // The stack of all active ElementHandlers
    private Stack<ElementHandler> elementHandlers;
    // The number of objects that have been written by this converter
    private int pdfObjectCount;
    private PDFWriter _writer;

    /**
     * Creates a new SVGToPDFConverter for the specified output file.
     *
     * @param file
     * @throws FileNotFoundException
     */
    public SVGToPDFHandler(File file)
            throws FileNotFoundException {
        this(file, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    /**
     * Creates a new SVGToPDFConverter for the specified output file and page
     * dimensions.
     *
     * @param file
     * @throws FileNotFoundException
     */
    public SVGToPDFHandler(File file, java.awt.geom.Point2D maxPoint)
            throws FileNotFoundException {
        this(file, maxPoint.getX(), maxPoint.getY());
    }

    /**
     * Creates a new SVGToPDFConverter for the specified output file and page
     * dimensions.
     *
     * @param file
     * @throws FileNotFoundException
     */
    public SVGToPDFHandler(File file, double width, double height)
            throws FileNotFoundException {
        // Reset the singleton classes
        Fonts.getNewInstance();
        GraphicsStates.getNewInstance();
        DocumentAttributes.getNewInstance();
        ClipPaths.getNewInstance();

        elementHandlers = new Stack<>();
        pdfObjectCount = 0;
        DocumentAttributes.getInstance().putValue("width", width + "px");
        DocumentAttributes.getInstance().putValue("height", height + "px");
        _writer = new PDFWriter(file);
    }

    /**
     * Keeps the program from going online for the SVG 1.0 DTD file. The local
     * copy is at ../resources/svg10.dtd. This improves performance drastically.
     *
     * @param publicId
     * @param systemId
     * @return
     * @throws IOException
     * @throws SAXException
     */
    @Override
    public InputSource resolveEntity(String publicId, String systemId)
            throws IOException, SAXException {
        if (systemId.contains("svg10.dtd")) {
            return new InputSource(new FileInputStream("resources/svg10.dtd"));
        } else {
            // Should ignore other DTDs
            return new InputSource(new FileInputStream(""));
        }
    }

    /**
     * The parser calls this once at the beginning of a document. This simply
     * writes the PDF version to the new file.
     *
     * @throws SAXException
     */
    @Override
    public void startDocument() throws SAXException {
        writeHeader(1.7);
    }

    /**
     * The parser calls this for each element in a document.
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
        // Push the appropriate ElementHandler to the stack
        if (qName.equalsIgnoreCase("SVG")) {
            elementHandlers.push(new SVGElementHandler(
                    DocumentAttributes.getInstance().getWidth(),
                    DocumentAttributes.getInstance().getHeight()));
        } else if (qName.equalsIgnoreCase("G")) {
            elementHandlers.push(new GElementHandler());
        } else if (qName.equalsIgnoreCase("Text")) {
            elementHandlers.push(new TextElementHandler());
        } else if (qName.equalsIgnoreCase("Rect")) {
            elementHandlers.push(new RectElementHandler());
        } else if (qName.equalsIgnoreCase("Circle")) {
            elementHandlers.push(new CircleElementHandler());
        } else if (qName.equalsIgnoreCase("Path")) {
            elementHandlers.push(new PathElementHandler());
        } else if (qName.equalsIgnoreCase("Line")) {
            elementHandlers.push(new LineElementHandler());
        } else if (qName.equalsIgnoreCase("ClipPath")) {
            elementHandlers.push(new ClipPathElementHandler());
        } else {
            elementHandlers.push(new ElementHandler());
        }

        // Call startElement for the top element on the stack
        elementHandlers.peek()
                .startElement(namespaceURI, localName, qName, atts);
    }

    /**
     * The parser calls this for each element in a document.
     *
     * @param ch
     * @param start
     * @param length
     * @throws SAXException
     */
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        // Call characters for the top element on the stack
        elementHandlers.peek().characters(ch, start, length);
    }

    /**
     * The parser calls this for each element in a document.
     *
     * @param namespaceURI
     * @param localName
     * @param qName
     * @throws SAXException
     */
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        ElementHandler topElementHandler = elementHandlers.peek();

        // Call endElement for the top element on the stack.
        topElementHandler.endElement(qName, localName, qName);

        // See if this ElementHandler has PDF object contents and write it if so.
        if (topElementHandler.hasDirectObject()) {
            writeIndirectObject(
                    new IndirectObject(++pdfObjectCount, topElementHandler.getDirectObject()));
        }

        // Pop the top ElementHandler off of the stack.
        elementHandlers.pop();
    }

    /**
     * Parser calls this once at the end of a document
     *
     * @throws SAXException
     */
    @Override
    public void endDocument() throws SAXException {
        IndirectObject contentsObject, pageObject, pagesObject, catalogObject;
        DictionaryObject pageDictionary, pagesDictionary, catalogDictionary;
        ObjectReference contentsReference, pageReference, pagesReference,
                catalogReference;

        // Write the contents object
        ArrayObject contentsArray = new ArrayObject();
        for (int i = 1; i <= pdfObjectCount; i++) {
            contentsArray.add(new ObjectReference(i));
        }
        contentsObject = new IndirectObject(++pdfObjectCount, contentsArray);
        contentsReference = contentsObject.getObjectReference();

        writeIndirectObject(contentsObject);

        // Write the page object
        pageDictionary = new DictionaryObject()
                .addEntry("Type", new NameObject("Page"))
                .addEntry("Contents", contentsReference)
                .addEntry("MediaBox", new ArrayObject()
                .add(new IntegerObject(0))
                .add(new IntegerObject(0))
                .add(new RealObject(DocumentAttributes.getInstance().getWidth()))
                .add(new RealObject(DocumentAttributes.getInstance().getHeight())))
                .addEntry("Resources", writeResourceObject());
        pageObject = new IndirectObject(++pdfObjectCount, pageDictionary);
        pageReference = pageObject.getObjectReference();

        pagesDictionary = new DictionaryObject()
                .addEntry("Type", new NameObject("Pages"))
                .addEntry("Kids", new ArrayObject().add(pageReference))
                .addEntry("Count", new IntegerObject(1));
        pagesObject = new IndirectObject(++pdfObjectCount, pagesDictionary);
        pagesReference = pagesObject.getObjectReference();

        catalogDictionary = new DictionaryObject()
                .addEntry("Type", new NameObject("Catalog"))
                .addEntry("Pages", pagesReference);
        catalogObject = new IndirectObject(++pdfObjectCount, catalogDictionary);
        catalogReference = catalogObject.getObjectReference();

        pageDictionary.addEntry("Parent", pagesReference);

        writeIndirectObject(pageObject);
        writeIndirectObject(pagesObject);
        writeIndirectObject(catalogObject);

        writeCrossReferenceTable();
        writeTrailer(catalogReference);
    }

    private void writeHeader(double version) {
        _writer.writeHeader(version);
    }

    private IndirectObject writeIndirectObject(IndirectObject anObject) {
        return _writer.writeIndirectObject(anObject);
    }

    private IndirectObject writeIndirectObject(DirectObject anObject) {
        return _writer.writeIndirectObject(new IndirectObject(++pdfObjectCount, anObject));
    }

    private ObjectReference writeResourceObject() {
        DictionaryObject resourceObject = new DictionaryObject();

        resourceObject.addEntry("Font",
                Fonts.getInstance().getFontsDictionary());

        // Write graphics states
        resourceObject.addEntry("ExtGState",
                GraphicsStates.getInstance().getGraphicStatesDictionary());

        return writeIndirectObject(resourceObject).getObjectReference();
    }

    private void writeCrossReferenceTable() {
        _writer.writeCrossReferenceTable();
    }

    private void writeTrailer(ObjectReference root) {
        _writer.writeTrailer(root);
    }
}
