package com.codecool.core.render;

/**
 * Represents the data that is needed
 * to an object to be rendered by the App.
 */

public class RenderData {

    /**
     * x position of an AppObject.
     */
    public int xPos;

    /**
     * y position of an AppObject.
     */
    public int yPos;

    /**
     * sybols that represents an AppObject on the screen.
     */
    public char[][] renderSymbols;

    /**
     * Constructs a RenderData.
     *
     * @param xPos          of an AppObject
     * @param yPos          of an AppObject
     * @param renderSymbols of an AppObject
     */
    public RenderData(int xPos, int yPos, char[][] renderSymbols) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.renderSymbols = renderSymbols;
    }
}