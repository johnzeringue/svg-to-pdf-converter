/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnzeringue.svgtopdf.objects;

import com.johnzeringue.svgtopdf.util.Text;
import java.util.Objects;

/**
 *
 * @author john
 */
public class NameObject implements DirectObject {
    private String _value;
    
    public NameObject(String value) {
        _value = value;
    }

    @Override
    public Text getText() {
        return new Text("/" + _value);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this._value);
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
        final NameObject other = (NameObject) obj;
        if (!Objects.equals(this._value, other._value)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "/" + _value;
    }
    
}
