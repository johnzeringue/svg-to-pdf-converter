package com.johnzeringue.svgtopdf;

import com.johnzeringue.svgtopdf.util.Text;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * A singleton class to keep track of clip paths.
 *
 * @author John Zeringue
 */
public class ClipPaths {

    private static ClipPaths instance;
    private Map<String, Text> _clipPaths;
    private String _currentClipPath;
    private boolean _isBuilding;

    private ClipPaths() {
        _clipPaths = new HashMap<>();
        _isBuilding = false;
    }

    public void startClipPath(String clipPathID) {
        _clipPaths.put(clipPathID, new Text());
        _currentClipPath = clipPathID;
        _isBuilding = true;
    }

    public void addClipPathContent(Text content) {
        _clipPaths.get(_currentClipPath).append(content);
    }
    
    public void endCurrentClipPath() {
        _clipPaths.get(_currentClipPath).append("W n");
        _currentClipPath = null;
        _isBuilding = false;
    }
    
    public Text getClipPath(String clipPathID) {
        return _clipPaths.get(clipPathID);
    }
    
    public void printClipPaths() {
        for (Entry<String, Text> anEntry : _clipPaths.entrySet()) {
            System.out.println(anEntry.getKey());
            System.out.println(anEntry.getValue());
        }
    }

    /**
     * Returns this class's only instance, creating one if it did not yet exist.
     *
     * @return
     */
    public static ClipPaths getInstance() {
        // If the instance of this class doesn't exist, create it.
        if (instance == null) {
            instance = new ClipPaths();
        }

        return instance;
    }

    /**
     * Returns a new instance of this class, replacing the old one if present.
     *
     * @return
     */
    public static ClipPaths getNewInstance() {
        // Create a new instance and return it
        instance = new ClipPaths();
        return instance;
    }

    public boolean isBuildingNewClipPath() {
        return _isBuilding;
    }
}