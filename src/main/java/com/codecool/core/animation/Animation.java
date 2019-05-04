package com.codecool.core.animation;

import com.codecool.core.App;
import com.codecool.core.AppObject;

/**
 * Implements animation functionality
 * by the {@link #defineFrames()}
 * so every class that extends it
 * has to use the method to define
 * {@link #frames} which will represent
 * the particular frames of the animation.
 * <p>
 * There is two option for an animation
 * to be played in the app.
 * 1. when it plays from start to finish.
 * 2. when it is looping.
 * <p>
 * You can set it with {@link #isLooping}
 * at the instantiation.
 */

public abstract class Animation extends AppObject {

    /**
     * You can think of this as your
     * sequence of pictures which will
     * played when your Animation added to the app.
     */
    protected char[][][] frames;

    /**
     * This is used to keep track of
     * where we are in the sequence.
     */
    private int frameCounter;

    /**
     * It's determines if the animation will be looped or not.
     */
    private boolean isLooping;

    /**
     * Constructs an Animation.
     *
     * @param xPos      x coordinate of the Animation in the app.
     * @param yPos      y coordinate of the Animation in the app.
     * @param app       a reference of the host.
     * @param isLooping for determine if it is a looped animation or not.
     */
    public Animation(int xPos, int yPos, App app, boolean isLooping) {
        super(xPos, yPos, null, app);
        this.isLooping = isLooping;
        this.defineFrames();
        this.initRenderSymbol();
    }

    /**
     * Goes through the frames,
     * it is updating the {@link #frameCounter}
     * in every update, and when reaches the end
     * acts based on {@link #isLooping} and
     * starts over, or removes the animation from the host app.
     */
    public void update() {
        this.frameCounter++;

        if (isLooping) {

            if (frameCounter == frames.length) {
                frameCounter = 0;
            }
            this.renderSymbols = this.frames[this.frameCounter];

        } else {
            if (this.frameCounter < frames.length) {
                this.renderSymbols = this.frames[this.frameCounter];

            } else {
                app.removeAnimation(this);
            }
        }
    }

    /**
     * Sets {@link AppObject#renderSymbols}
     * to the fist frame.
     * <p>
     * When the user not defines {@link #frames}
     * it will throw a custom exception.
     */
    private void initRenderSymbol() {
        try {
            this.renderSymbols = this.frames[0];
        } catch (NullPointerException e) {
            throw new FramesNotDefined();
        }
    }

    /**
     * This where the user of this class
     * have to define {@link #frames}
     * <p>
     * It is done by defining a 3D character array
     * which will represent the frames of the animation.
     */
    public abstract void defineFrames();
}