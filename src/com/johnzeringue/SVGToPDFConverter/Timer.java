package com.johnzeringue.SVGToPDFConverter;

/**
 * A simple timer representation that can start a timer at 0 milliseconds and
 * check the timer.
 *
 * @author johnzeringue
 */
public class Timer {

    private long _startTime;
    private boolean _isRunning;

    public Timer() {
        _isRunning = false;
    }

    /**
     * Starts the timer.
     */
    public void start() {
        _isRunning = true;
        _startTime = System.currentTimeMillis();
    }

    /**
     * Checks the timer and returns the current time in milliseconds.
     *
     * @return the time in milliseconds
     */
    public long check() {
        if (_isRunning) {
            return System.currentTimeMillis() - _startTime;
        }

        return 0;
    }
}
