package com.codecool.core.buffer;

import com.codecool.core.AppObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BufferTest {

    private List<AppObject> testTarget;
    private Buffer<AppObject> buffer;
    private AppObject stub1;
    private AppObject stub2;

    @BeforeEach
    void init(){
        testTarget = new ArrayList<>();
        stub1 = Mockito.mock(AppObject.class);
        stub2 = Mockito.mock(AppObject.class);
        buffer = new Buffer<>(testTarget);
    }

    @Test
    void addOneElement(){
        Mockito.when(stub1.getBufferTag()).thenReturn(BufferTag.ADD);
        buffer.applyForAddition(stub1);
        buffer.process();

        assertEquals(1, testTarget.size());
    }

    @Test
    void removeOneElement(){
        testTarget.add(stub1);
        Mockito.when(stub1.getBufferTag()).thenReturn(BufferTag.REMOVE);
        buffer.applyForRemoval(stub1);
        buffer.process();

        assertEquals(0, testTarget.size());
    }

    @Test
    void addMoreElements(){
        Mockito.when(stub1.getBufferTag()).thenReturn(BufferTag.ADD);
        Mockito.when(stub2.getBufferTag()).thenReturn(BufferTag.ADD);
        buffer.applyForAddition(Arrays.asList(stub1, stub2));
        buffer.process();

        assertEquals(2, testTarget.size());
    }

    @Test
    void removeMoreElements(){
        testTarget.add(stub1);
        testTarget.add(stub2);
        Mockito.when(stub1.getBufferTag()).thenReturn(BufferTag.REMOVE);
        Mockito.when(stub2.getBufferTag()).thenReturn(BufferTag.REMOVE);
        buffer.applyForRemoval(Arrays.asList(stub1, stub2));
        buffer.process();

        assertEquals(0, testTarget.size());
    }
}