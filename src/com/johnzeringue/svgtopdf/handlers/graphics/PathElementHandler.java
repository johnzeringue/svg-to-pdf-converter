package com.johnzeringue.svgtopdf.handlers.graphics;

import java.awt.geom.Point2D;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An ElementHandler for Path elements
 *
 * @author John Zeringue
 */
public class PathElementHandler extends GraphicsElementHandler {

    private static final String movetoRegex =
            "[Mm]\\s*(-?\\d+(?:\\.\\d+)?)\\s*,?\\s*(-?\\d+(?:\\.\\d+)?)\\s*";
    private static final String linetoRegex =
            "[Ll]\\s*(-?\\d+(?:\\.\\d+)?)\\s*,?\\s*(-?\\d+(?:\\.\\d+)?)\\s*";
    private static final String curvetoRegex =
            "[Cc]\\s*(-?\\d+(?:\\.\\d+)?)\\s*,?\\s*(-?\\d+(?:\\.\\d+)?)\\s*"
            + "(-?\\d+(?:\\.\\d+)?)\\s*,?\\s*(-?\\d+(?:\\.\\d+)?)\\s*"
            + "(-?\\d+(?:\\.\\d+)?)\\s*,?\\s*(-?\\d+(?:\\.\\d+)?)\\s*";
    private Point2D currentPoint, lastControlPoint;
    private Pattern p;
    private Matcher m;

    @Override
    public void drawPath() {
        String path = docAtts.getValue("d");
        currentPoint = new Point2D.Double();

        while (path != null && !"".equals(path)) {
            switch (path.charAt(0)) {
                case 'M':
                    double x,
                     y;
                    p = Pattern.compile(movetoRegex);
                    m = p.matcher(path);
                    if (m.lookingAt()) {
                        x = Double.parseDouble(m.group(1));
                        y = invertY(Double.parseDouble(m.group(2)));

                        _object.append(
                                String.format("%f %f m", x, y));

                        currentPoint.setLocation(x, y);

                        path = path.replaceFirst(movetoRegex, "");
                    } else {
                        return;
                    }
                    break;
                case 'm':
                    p = Pattern.compile(movetoRegex);
                    m = p.matcher(path);
                    if (m.lookingAt()) {
                        x = currentPoint.getX() + Double.parseDouble(m.group(1));
                        y = currentPoint.getY() - Double.parseDouble(m.group(2));

                        _object.append(
                                String.format("%f %f m", x, y));

                        currentPoint.setLocation(x, y);

                        path = path.replaceFirst(movetoRegex, "");
                    } else {
                        return;
                    }
                    break;
                case 'C':
                    double x1,
                     y1,
                     x2,
                     y2;
                    p = Pattern.compile(curvetoRegex);
                    m = p.matcher(path);
                    if (m.lookingAt()) {
                        x1 = Double.parseDouble(m.group(1));
                        y1 = invertY(Double.parseDouble(m.group(2)));
                        x2 = Double.parseDouble(m.group(3));
                        y2 = invertY(Double.parseDouble(m.group(4)));
                        x = Double.parseDouble(m.group(5));
                        y = invertY(Double.parseDouble(m.group(6)));

                        _object.append(
                                String.format("%f %f %f %f %f %f c",
                                x1, y1, x2, y2, x, y));

                        currentPoint.setLocation(x, y);

                        path = path.replaceFirst(curvetoRegex, "");
                    } else {
                        return;
                    }
                    break;
                case 'c':
                    p = Pattern.compile(curvetoRegex);
                    m = p.matcher(path);
                    if (m.lookingAt()) {
                        x1 = currentPoint.getX() + Double.parseDouble(m.group(1));
                        y1 = currentPoint.getY() - Double.parseDouble(m.group(2));
                        x2 = currentPoint.getX() + Double.parseDouble(m.group(3));
                        y2 = currentPoint.getY() - Double.parseDouble(m.group(4));
                        x = currentPoint.getX() + Double.parseDouble(m.group(5));
                        y = currentPoint.getY() - Double.parseDouble(m.group(6));

                        _object.append(
                                String.format("%f %f %f %f %f %f c",
                                x1, y1, x2, y2, x, y));

                        currentPoint.setLocation(x, y);

                        path = path.replaceFirst(curvetoRegex, "");
                    } else {
                        return;
                    }
                    break;
                case 'L':
                    p = Pattern.compile(linetoRegex);
                    m = p.matcher(path);
                    if (m.lookingAt()) {
                        x = Double.parseDouble(m.group(1));
                        y = invertY(Double.parseDouble(m.group(2)));

                        _object.append(
                                String.format("%f %f l", x, y));

                        currentPoint.setLocation(x, y);

                        path = path.replaceFirst(linetoRegex, "");
                    } else {
                        return;
                    }
                    break;
                case 'l':
                    p = Pattern.compile(linetoRegex);
                    m = p.matcher(path);
                    if (m.lookingAt()) {
                        x = currentPoint.getX() + Double.parseDouble(m.group(1));
                        y = currentPoint.getY() - Double.parseDouble(m.group(2));

                        _object.append(
                                String.format("%f %f l", x, y));

                        currentPoint.setLocation(x, y);

                        path = path.replaceFirst(linetoRegex, "");
                    } else {
                        return;
                    }
                    break;
                case 'Z': // The same as for 'z'
                case 'z':
                    _object.append("h");

                    path = "";
                    break;
                default:
                    break;
            }
        }
    }
}