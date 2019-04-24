package com.codecool.core.buffer;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements a buffer
 * what can be used in cases when
 * it's not possible to add/remove objects
 * to/from a list immediately.
 * */

public class Buffer<E extends Bufferable> {

    /**
     * An internal container for the buffered objects
     * */
    private List<E> elements = new ArrayList<>();

    /**
     * Reference of the list
     * where the Buffer adds/removes objects.
     * */
    private List<E> target;

    /**
     * Constructs a Buffer with the specified target.
     *
     * @param  target  where the Buffer adds/removes objects.
     */
    public Buffer(List<E> target){
        this.target = target;
    }

    /**
     * Adds/Removes objects found in {@link #elements}
     * from/to {@link #target} based on them BufferTag,
     * and then clears the Buffer.
     */
    public void process(){
        for (E element : elements) {

            if (element.getBufferTag() != null) {
                if (element.getBufferTag() == BufferTag.ADD) {
                    target.add(element);
                } else if (element.getBufferTag() == BufferTag.REMOVE) {
                    target.remove(element);
                }
            }
        }
        elements.clear();
    }

    /**
     * Adds object to the Buffer, with the tag ADD
     *
     * @param element that will be added to the target
     */
    public void applyForAddition(E element){
        element.setBufferTag(BufferTag.ADD);
        elements.add(element);
    }

    /**
     * Adds multiple objects to the Buffer, with the tag ADD
     *
     * @param elements that will be added to the target
     */
    public void applyForAddition(List<E> elements){
        for (E element : elements) {
            element.setBufferTag(BufferTag.ADD);
            this.elements.add(element);
        }
    }

    /**
     * Adds object to the Buffer, with the tag REMOVE
     *
     * @param element that will be removed from the target
     */
    public void applyForRemoval(E element){
        element.setBufferTag(BufferTag.REMOVE);
        elements.add(element);
    }

    /**
     * Adds multiple objects to the Buffer, with the tag REMOVE
     *
     * @param elements that will be removed from the target
     */
    public void applyForRemoval(List<E> elements){
        for (E element : elements) {
            element.setBufferTag(BufferTag.REMOVE);
            this.elements.add(element);
        }
    }
}
