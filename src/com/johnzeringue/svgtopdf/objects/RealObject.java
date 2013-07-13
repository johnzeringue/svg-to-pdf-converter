package com.johnzeringue.svgtopdf.objects;

/**
 * From the 1.7 PDF spec:
 *
 * A real value shall be written as one or more decimal digits with an optional
 * sign and a leading, trailing, or embedded PERIOD (2Eh) (decimal point). The
 * value shall be interpreted as a real number and shall be converted to a real
 * object.
 *
 * EXAMPLE 2 Real objects
 *
 * 34.5 -3.62 +123.6 4. -.002 0.0
 *
 * NOTE 1
 *
 * A conforming writer shall not use the PostScript syntax for numbers with
 * non-decimal radices (such as 16#FFFE) or in exponential format (such as
 * 6.02E23).
 *
 * NOTE 2
 *
 * Throughout this standard, the term number refers to an object whose type may
 * be either integer or real. Wherever a real number is expected, an integer may
 * be used instead. For example, it is not necessary to write the number 1.0 in
 * real format; the integer 1 is sufficient.
 *
 * @author John Zeringue
 */
public class RealObject implements DirectObject {

    private double _value;

    public RealObject(double value) {
        this._value = value;
    }
    
    public RealObject(String value) {
        this(Double.parseDouble(value));
    }

    @Override
    public TextLines getTextLines() {
        return new TextLines().appendLine(String.valueOf(_value));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (int) (Double.doubleToLongBits(this._value) ^ (Double.doubleToLongBits(this._value) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RealObject other = (RealObject) obj;
        if (Double.doubleToLongBits(this._value) != Double.doubleToLongBits(other._value)) {
            return false;
        }
        return true;
    }
}
