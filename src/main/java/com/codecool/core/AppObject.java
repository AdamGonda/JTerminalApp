package com.codecool.core;

import com.codecool.core.buffer.BufferTag;
import com.codecool.core.buffer.Bufferable;
import com.codecool.core.render.RenderData;
import com.codecool.core.render.Renderable;

/**
 * It's represents an object in the app,
 * which can be drawn in the terminal and
 * updated in every frame.
 * <p>
 * Mostly you have to override
 * {@link #update()} to implement a uniq
 * behavior.
 */

public abstract class AppObject implements Bufferable, Renderable {

    /**
     * Determines the x position of the object.
     */
    protected int xPos;

    /**
     * Determines the y position of the object.
     */
    protected int yPos;

    /**
     * Characters that will be represent the object on the screen.
     */
    protected char[][] renderSymbols;

    /**
     * A reference of the application host
     */
    protected App app;

    /**
     * It can be used to get AppObject(s)
     * from the host by the
     * App.getAppObject(s)ByTagName()
     * It is not forced to be uniq.
     */
    protected String tagName = "defaultTagName";

    /**
     * It is used by the Buffer via the
     * {@link #setBufferTag(BufferTag)}
     * and
     * {@link #setBufferTag(BufferTag)}
     * methods.
     */
    private BufferTag bufferTag;

    /**
     * Constructs an AppObject.
     *
     * @param xPos          x coordinate of the object in the app.
     * @param yPos          y coordinate of the object in the app.
     * @param renderSymbols characters that will be drawn on the screen.
     * @param app           a reference of the host.
     */
    public AppObject(int xPos, int yPos, char[][] renderSymbols, App app) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.renderSymbols = renderSymbols;
        this.app = app;
    }

    /**
     * This is an important method.
     * When you inherit from AppObject and you
     * like to do some dynamic motion on the screen
     * or do a calculation in every frame,
     * this is the place to implement that.
     * This method will be called in every frame by the host app.
     */
    public void update() {
        //Override if needed
    }

    /**
     * @return the x position of the object.
     */
    public int getXPos() {
        return this.xPos;
    }

    /**
     * @return the y position of the object.
     */
    public int getYPos() {
        return this.yPos;
    }

    /**
     * This is a crucially important method for
     * the object to be rendered on the screen.
     *
     * @return RenderData object.
     */
    public RenderData getRenderData() {
        return new RenderData(this.xPos, this.yPos, this.renderSymbols);
    }

    /**
     * @return the tag name of the object
     */
    String getTagName() {
        return this.tagName;
    }

    /**
     * @return the BufferTag
     */
    public BufferTag getBufferTag() {
        return this.bufferTag;
    }

    /**
     * @param tag which can be ADD or REMOVE
     */
    public void setBufferTag(BufferTag tag) {
        this.bufferTag = tag;
    }
}