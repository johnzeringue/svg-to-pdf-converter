package com.johnzeringue.svgtopdf;

import com.johnzeringue.svgtopdf.objects.DictionaryObject;
import com.johnzeringue.svgtopdf.objects.NameObject;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 * @author John Zeringue
 */
public class GraphicsStates {

    private static GraphicsStates instance;
    private Map<DictionaryObject, NameObject> _graphicStates;
    private int _graphicsStateCount;

    public GraphicsStates() {
        _graphicStates = new HashMap<>();
        _graphicsStateCount = 0;
    }
    
    public NameObject getGraphicStateName(DictionaryObject graphicState) {
        if (_graphicStates.containsKey(graphicState)) {
            return _graphicStates.get(graphicState);
        } else {
            NameObject value = new NameObject("GS" + (++_graphicsStateCount));
            _graphicStates.put(graphicState, value);
            return value;
        }
    }

    public DictionaryObject getGraphicStatesDictionary() {
        DictionaryObject result = new DictionaryObject();

        for (Map.Entry<DictionaryObject, NameObject> anEntry : _graphicStates.entrySet()) {
            result.addEntry(anEntry.getValue(), anEntry.getKey());
        }
        
        return result;
    }

    /**
     * Returns this class's only instance, creating one if it did not yet exist.
     *
     * @return
     */
    public static GraphicsStates getInstance() {
        // If the instance of this class doesn't exist, create it.
        if (instance == null) {
            instance = new GraphicsStates();
        }

        return instance;
    }

    /**
     * Returns a new instance of this class, replacing the old one if present.
     *
     * @return
     */
    public static GraphicsStates getNewInstance() {
        // Create a new instance and return it
        instance = new GraphicsStates();
        return instance;
    }
}
