package com.codecool.core;

/**
 * Holds the method that used by App
 * to provide flawless input reading.
 */

class Input {

    /**
     * @return input character or null
     */
    Character tryToRead() {
        try {
            if (System.in.available() > 0) {
                return (char) System.in.read();
            }
        } catch (Exception e) {

            System.err.println("Error " + e.getMessage());
        }
        return null;
    }
}