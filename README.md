svg-to-pdf-converter
====================

A simple SVG to PDF converter in Java

This library is in the final stages of development, and should provide better font support in the next couple of weeks. In the mean time, it has proven to be robust when handling graphics using my test files. Note that input SVGs MUST have both height and width specified in their SVG tag, or the conversion will not work correctly/fail.

Example usage:

SVGToPDFConverter myConverter = new SVGToPDFConverter();
myConverter.convert(new File("test.svg"));

The above code will create a new file, test.pdf, in the same directory as test.svg, assuming that the SVG file exists.
