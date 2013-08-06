package com.johnzeringue.svgtopdf;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.jar.Attributes;

/**
 * A singleton class for storing the document attributes for the file currently
 * being parsed.
 *
 * @author John Zeringue
 */
public class DocumentAttributes {

    // The lone instance of this class
    private static DocumentAttributes instance;
    // Non-scoped attributes
    private Attributes attributes;
    // An attributes "stack" for different scopes. A deque is used in this
    // instance becuase it provides functionality that the default stack
    // implementation does not.
    private Deque<Attributes> scopes;
    
    private double height;
    private double width;

    private DocumentAttributes() {
        attributes = new Attributes();
        scopes = new ArrayDeque<>();
        
        height = 0;
        width = 0;
    }
    
    /**
     * Returns the lowest level occurrence of the given attribute name or null
     * if it is not found. The attribute name is not case sensitive.
     * 
     * @param name
     * @return 
     */
    public String getValue(String name) {
        Iterator<Attributes> iter = scopes.descendingIterator();
        String value;
        
        // Check the scoped attributes
        while (iter.hasNext()) {
            // Check for the key in the next scope
            value = (iter.next()).getValue(name);
            
            // If found, return it
            if (value != null) {
                return value;
            }
        }
        
        // Return the unscoped value or null if the key could not be found
        return attributes.getValue(name);
    }
    
    public int getValueAsInt(String name) {
        return Integer.parseInt(getValue(name));
    }
    
    public double getValueAsDouble(String name) {
        return Double.parseDouble(getValue(name));
    }
    
    public Color getValueAsColor(String name) {
        return Colors.get(getValue(name));
    }
    
    /**
     * Sets the unscoped attribute value referred to by name to the given value.
     * Note that this setting does not take precedent over scoped settings.
     * 
     * @param name
     * @param value 
     */
    public void putValue(String name, String value) {
        attributes.putValue(name, value);
    }
    
    /**
     * Add (or "push") a new scope.
     * 
     * @param scope 
     */
    public void addScope(Attributes scope) {
        scopes.addLast(scope);
    }
    
    /**
     * Remove (or "pop") the lowest level scope.
     */
    public void removeScope() {
        scopes.removeLast();
    }
    
    public Color getStroke() {
        return Colors.get(getValue("stroke"));
    }
    
    public Color getFill() {
        return Colors.get(getValue("fill"));
    }
    
    /**
     * Returns this class's only instance, creating one if it did not yet exist.
     * 
     * @return 
     */
    public static DocumentAttributes getInstance() {
        // If the instance of this class doesn't exist, create it.
        if (instance == null) {
            instance = new DocumentAttributes();
        }

        return instance;
    }

    /**
     * Returns a new instance of this class, replacing the old one if present.
     * 
     * @return 
     */
    public static DocumentAttributes getNewInstance() {
        // Create a new instance and return it
        instance = new DocumentAttributes();
        return instance;
    }
}
