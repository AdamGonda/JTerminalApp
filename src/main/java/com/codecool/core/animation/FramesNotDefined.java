package com.codecool.core.animation;

/**
 * Custom exception in case {@link Animation#defineFrames()} not used properly
 * and {@link Animation#frames} not defined.
 */
public class FramesNotDefined extends RuntimeException {

    FramesNotDefined() {
        super("You forget to define this.frames in your defineFrames() method\n by creating a 3D character array");
    }
}
