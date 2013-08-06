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

    private static final String WSP = "\\s";
    private static final String WSPS = WSP + "*";
    private static final String COMMA_WSP = WSPS + ",?" + WSPS;
    private static final String NUMBER = "(-?\\d+(?:\\.\\d+)?)";
    private static final String MOVE_TO_REGEX =
            "[Mm]" + WSPS + NUMBER + COMMA_WSP + NUMBER + WSPS;
    private static final String LINE_TO_REGEX =
            "[Ll]" + WSPS + NUMBER + COMMA_WSP + NUMBER + WSPS;
    private static final String CURVE_TO_REGEX = "[Cc]" + WSPS
            + NUMBER + COMMA_WSP + NUMBER + COMMA_WSP + NUMBER + COMMA_WSP
            + NUMBER + COMMA_WSP + NUMBER + COMMA_WSP + NUMBER + WSPS;
    private Point2D _currentPoint;

    @Override
    public void drawPath() {
        String path = getValue("d");
        _currentPoint = new Point2D.Double();

        while (path != null && !"".equals(path)) {
            switch (path.charAt(0)) {
                case 'M':
                    path = parseAbsoluteMoveTo(path);
                    break;
                case 'm':
                    path = parseRelativeMoveTo(path);
                    break;
                case 'C':
                    path = parseAbsoluteCurveTo(path);
                    break;
                case 'c':
                    path = parseRelativeCurveTo(path);
                    break;
                case 'L':
                    path = parseAbsoluteLineTo(path);
                    break;
                case 'l':
                    path = parseRelativeLineTo(path);
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

    private String parseAbsoluteMoveTo(String path) {
        double x;
        double y;
        Pattern p = Pattern.compile(MOVE_TO_REGEX);
        Matcher m = p.matcher(path);
        if (m.lookingAt()) {
            x = Double.parseDouble(m.group(1));
            y = invertY(Double.parseDouble(m.group(2)));

            _object.append(
                    String.format("%f %f m", x, y));

            _currentPoint.setLocation(x, y);

            path = path.replaceFirst(MOVE_TO_REGEX, "");
        }
        return path;
    }

    private String parseRelativeMoveTo(String path) {
        double x;
        double y;
        Pattern p = Pattern.compile(MOVE_TO_REGEX);
        Matcher m = p.matcher(path);
        if (m.lookingAt()) {
            x = _currentPoint.getX() + Double.parseDouble(m.group(1));
            y = _currentPoint.getY() - Double.parseDouble(m.group(2));

            _object.append(
                    String.format("%f %f m", x, y));

            _currentPoint.setLocation(x, y);

            path = path.replaceFirst(MOVE_TO_REGEX, "");
        }
        return path;
    }

    private String parseAbsoluteCurveTo(String path) {
        double x1, y1, x2, y2, x, y;
        Pattern p = Pattern.compile(CURVE_TO_REGEX);
        Matcher m = p.matcher(path);
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

            _currentPoint.setLocation(x, y);

            path = path.replaceFirst(CURVE_TO_REGEX, "");
        }
        return path;
    }

    private String parseRelativeCurveTo(String path) {
        double x1, y1, x2, y2, x, y;
        Pattern p = Pattern.compile(CURVE_TO_REGEX);
        Matcher m = p.matcher(path);
        if (m.lookingAt()) {
            x1 = _currentPoint.getX() + Double.parseDouble(m.group(1));
            y1 = _currentPoint.getY() - Double.parseDouble(m.group(2));
            x2 = _currentPoint.getX() + Double.parseDouble(m.group(3));
            y2 = _currentPoint.getY() - Double.parseDouble(m.group(4));
            x = _currentPoint.getX() + Double.parseDouble(m.group(5));
            y = _currentPoint.getY() - Double.parseDouble(m.group(6));

            _object.append(
                    String.format("%f %f %f %f %f %f c",
                    x1, y1, x2, y2, x, y));

            _currentPoint.setLocation(x, y);

            path = path.replaceFirst(CURVE_TO_REGEX, "");
        }
        return path;
    }

    private String parseAbsoluteLineTo(String path) {
        double x, y;
        Pattern p = Pattern.compile(LINE_TO_REGEX);
        Matcher m = p.matcher(path);
        if (m.lookingAt()) {
            x = _currentPoint.getX();
            y = invertY(Double.parseDouble(m.group(2)));

            _object.append(
                    String.format("%f %f l", x, y));

            _currentPoint.setLocation(x, y);

            path = path.replaceFirst(LINE_TO_REGEX, "");
        }
        return path;
    }

    private String parseRelativeLineTo(String path) {
        double x, y;
        Pattern p = Pattern.compile(LINE_TO_REGEX);
        Matcher m = p.matcher(path);
        if (m.lookingAt()) {
            x = _currentPoint.getX() + Double.parseDouble(m.group(1));
            y = _currentPoint.getY() - Double.parseDouble(m.group(2));

            _object.append(
                    String.format("%f %f l", x, y));

            _currentPoint.setLocation(x, y);

            path = path.replaceFirst(LINE_TO_REGEX, "");
        }
        return path;
    }
}