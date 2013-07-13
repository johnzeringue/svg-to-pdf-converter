package com.johnzeringue.svgtopdf;

import com.johnzeringue.svgtopdf.objects.DictionaryObject;
import com.johnzeringue.svgtopdf.objects.NameObject;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A singleton class for holding font information as it moves from the SVG to
 * the PDF.
 *
 * @author John Zeringue
 */
public class Fonts {

    private static Fonts instance;
    private Map<NameObject, DictionaryObject> _fonts;
    private Map<String, NameObject> _fontNames;
    private static final Set<String> STANDARD_14_FONTS = new HashSet<>(
            Arrays.asList("Times-Roman", "Helvetica", "Courier",
            "Symbol", "Times-Bold", "Helvetica-Bold", "Courier-Bold",
            "ZapfDingbats", "Times-Italic", "Helvetica-Oblique",
            "Courier-Oblique", "Times-BoldItalic", "Helvetica-BoldOblique",
            "Courier-BoldOblique"));
    private int _fontCount;

    public Fonts() {
        _fonts = new HashMap<>();
        _fontNames = new HashMap<>();
        _fontCount = 0;
    }

    public NameObject getFontTag(String font) {
        if (!STANDARD_14_FONTS.contains(font)) {
            font = "Helvetica";
        }
        if (!_fontNames.containsKey(font)) {

            addFont(font);
        }

        return _fontNames.get(font);
    }

    public DictionaryObject getFontsDictionary() {
        DictionaryObject result = new DictionaryObject();

        for (Map.Entry<NameObject, DictionaryObject> anEntry : _fonts.entrySet()) {
            result.addEntry(anEntry.getKey(), anEntry.getValue());
        }

        return result;
    }

    /**
     * Returns this class's only instance, creating one if it did not yet exist.
     *
     * @return
     */
    public static Fonts getInstance() {
        // If the instance of this class doesn't exist, create it.
        if (instance == null) {
            instance = new Fonts();
        }

        return instance;
    }

    /**
     * Returns a new instance of this class, replacing the old one if present.
     *
     * @return
     */
    public static Fonts getNewInstance() {
        // Create a new instance and return it
        instance = new Fonts();
        return instance;
    }

    private void addFont(String font) {
        DictionaryObject fontDictionary = new DictionaryObject()
                .addEntry("Type", new NameObject("Font"))
                .addEntry("Subtype", new NameObject("Type1"));

        if (STANDARD_14_FONTS.contains(font)) {
            fontDictionary.addEntry("BaseFont", new NameObject(font));
        } else {
            fontDictionary.addEntry("BaseFont", new NameObject("Helvetica"));
        }

        _fonts.put(new NameObject("F" + (++_fontCount)), fontDictionary);
        _fontNames.put(font, new NameObject("F" + _fontCount));
    }
}
