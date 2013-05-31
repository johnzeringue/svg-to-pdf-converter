package com.johnzeringue.SVGToPDFConverter;

import java.awt.Color;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A String to Color map for basic SVG color names
 *
 * @author John Zeringue
 */
public class Colors {

    private static enum ColorValue {

        WHITE(new Color(255, 255, 255)),
        GRAY(new Color(127, 127, 127)),
        BLUE(new Color(0, 0, 255)),
        RED(new Color(255, 0, 0)),
        BLACK(new Color(0, 0, 0)),
        NONE(null);
        private final Color color;

        ColorValue(Color c) {
            color = c;
        }

        public Color color() {
            return color;
        }
    }

    /**
     * Converts an SVG color name or an RGB string to a Color object containing
     * the RGB data.
     *
     * @param colorName the name of the color, per the SVG spec
     * @return the color as a Color object
     */
    public static Color get(String s) {
        Pattern c = Pattern.compile(
                "\\Argb\\(\\s*(\\d{1,3})\\s*,\\s*(\\d{1,3})\\s*,\\s*(\\d{1,3})\\s*\\)\\z");
        Matcher m;

        if (s.matches("\\A\\p{Lower}+\\z")) {
            return ColorValue.valueOf(s.toUpperCase(Locale.ENGLISH)).color();
        } else if ((m = c.matcher(s)).matches()) {
            return new Color(Integer.valueOf(m.group(1)), // r
                    Integer.valueOf(m.group(2)), // g
                    Integer.valueOf(m.group(3))); // b
        } else {
            return null;
        }
    }
}
