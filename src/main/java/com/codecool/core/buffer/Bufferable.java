package com.codecool.core.buffer;

/**
 * It's provide necessary methods for Buffer's
 * applyFor*() and process() methods
 * */

public interface Bufferable {

    /**
     * @return BufferTag
     * */
    BufferTag getBufferTag();

    /**
     * @param tag which can be ADD or REMOVE
     * */
    void setBufferTag(BufferTag tag);
}
