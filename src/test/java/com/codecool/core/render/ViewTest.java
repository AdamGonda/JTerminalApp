package com.codecool.core.render;

import com.codecool.core.AppObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ViewTest {

    private View<AppObject> view;

    @BeforeEach
    void init() {
        view = new View<>(5, 5, new ArrayList<>(), new ArrayList<>(), 100);
    }

    private String drawOnCanvasAndGetCanvasContent(int xPos, int yPos, char[][] symbol) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        RenderData renderDataMock = new RenderData(xPos, yPos, symbol);
        AppObject objectMock = Mockito.mock(AppObject.class);
        Mockito.when(objectMock.getRenderData()).thenReturn(renderDataMock);

        Method drawOnCanvas = View.class.getDeclaredMethod("drawOnCanvas", List.class);
        drawOnCanvas.setAccessible(true);
        drawOnCanvas.invoke(view, Arrays.asList(objectMock));


        Method getCanvasContent = View.class.getDeclaredMethod("getCanvasContent", null);
        getCanvasContent.setAccessible(true);
        return (String) getCanvasContent.invoke(view, null);
    }

    // Render Symbol Length -> RSL

    // 1X1 RSL tests
    @Test
    void renderObjectOffScreen1X1RSL() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String expected =
                "     \r\n" +
                        "     \r\n" +
                        "     \r\n" +
                        "     \r\n" +
                        "     \r\n";

        int xPos = 13;
        int yPos = 13;
        char[][] symbol = new char[][]{{'x'}};


        String canvasContent = drawOnCanvasAndGetCanvasContent(xPos, yPos, symbol);
        assertEquals(expected, canvasContent);
    }

    @Test
    void renderObjectAtUpperRightCorner1X1RSL() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String expected =
                "x    \r\n" +
                        "     \r\n" +
                        "     \r\n" +
                        "     \r\n" +
                        "     \r\n";

        int xPos = 0;
        int yPos = 0;
        char[][] symbol = new char[][]{{'x'}};


        String canvasContent = drawOnCanvasAndGetCanvasContent(xPos, yPos, symbol);
        assertEquals(expected, canvasContent);
    }

    @Test
    void renderObjectAtBottomRightCorner1X1RSL() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String expected =
                "     \r\n" +
                        "     \r\n" +
                        "     \r\n" +
                        "     \r\n" +
                        "x    \r\n";

        int xPos = 0;
        int yPos = 4;
        char[][] symbol = new char[][]{{'x'}};


        String canvasContent = drawOnCanvasAndGetCanvasContent(xPos, yPos, symbol);
        assertEquals(expected, canvasContent);
    }

    @Test
    void renderObjectAtUpperLeftCorner1X1RSL() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String expected =
                "    x\r\n" +
                        "     \r\n" +
                        "     \r\n" +
                        "     \r\n" +
                        "     \r\n";

        int xPos = 4;
        int yPos = 0;
        char[][] symbol = new char[][]{{'x'}};


        String canvasContent = drawOnCanvasAndGetCanvasContent(xPos, yPos, symbol);
        assertEquals(expected, canvasContent);
    }

    @Test
    void renderObjectAtBottomLeftCorner1X1RSL() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String expected =
                "     \r\n" +
                        "     \r\n" +
                        "     \r\n" +
                        "     \r\n" +
                        "    x\r\n";

        int xPos = 4;
        int yPos = 4;
        char[][] symbol = new char[][]{{'x'}};


        String canvasContent = drawOnCanvasAndGetCanvasContent(xPos, yPos, symbol);
        assertEquals(expected, canvasContent);
    }

    @Test
    void renderObjectAtTheMiddle1X1RSL() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String expected =
                "     \r\n" +
                        "     \r\n" +
                        "  x  \r\n" +
                        "     \r\n" +
                        "     \r\n";

        int xPos = 2;
        int yPos = 2;
        char[][] symbol = new char[][]{{'x'}};


        String canvasContent = drawOnCanvasAndGetCanvasContent(xPos, yPos, symbol);
        assertEquals(expected, canvasContent);
    }


    // 2X2 RSL tests
    @Test
    void renderObjectAtTheMiddle2X2RSL() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String expected =
                "     \r\n" +
                        "     \r\n" +
                        "  x> \r\n" +
                        "  v. \r\n" +
                        "     \r\n";

        int xPos = 2;
        int yPos = 2;
        char[][] symbol = new char[][]{
                {'x', '>'},
                {'v', '.'}
        };


        String canvasContent = drawOnCanvasAndGetCanvasContent(xPos, yPos, symbol);
        assertEquals(expected, canvasContent);
    }

    @Test
    void renderObjectAtUpperLeftCorner2X2RSL() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String expected =
                "x>   \r\n" +
                        "v.   \r\n" +
                        "     \r\n" +
                        "     \r\n" +
                        "     \r\n";

        int xPos = 0;
        int yPos = 0;
        char[][] symbol = new char[][]{
                {'x', '>'},
                {'v', '.'}
        };


        String canvasContent = drawOnCanvasAndGetCanvasContent(xPos, yPos, symbol);
        assertEquals(expected, canvasContent);
    }

    @Test
    void renderObjectAtUpperRightCorner2X2RSL() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String expected =
                "   x>\r\n" +
                        "   v.\r\n" +
                        "     \r\n" +
                        "     \r\n" +
                        "     \r\n";

        int xPos = 3;
        int yPos = 0;
        char[][] symbol = new char[][]{
                {'x', '>'},
                {'v', '.'}
        };


        String canvasContent = drawOnCanvasAndGetCanvasContent(xPos, yPos, symbol);
        assertEquals(expected, canvasContent);
    }

    @Test
    void renderObjectAtBottomRightCorner2X2RSL() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String expected =
                "     \r\n" +
                        "     \r\n" +
                        "     \r\n" +
                        "   x>\r\n" +
                        "   v.\r\n";

        int xPos = 3;
        int yPos = 3;
        char[][] symbol = new char[][]{
                {'x', '>'},
                {'v', '.'}
        };


        String canvasContent = drawOnCanvasAndGetCanvasContent(xPos, yPos, symbol);
        assertEquals(expected, canvasContent);
    }

    @Test
    void renderPartsOfTheObjectOffScreenOnXAxis2X2RSL() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String expected =
                "     \r\n" +
                        "     \r\n" +
                        "     \r\n" +
                        "    x\r\n" +
                        "    v\r\n";

        int xPos = 4;
        int yPos = 3;
        char[][] symbol = new char[][]{
                {'x', '>'},
                {'v', '.'}
        };


        String canvasContent = drawOnCanvasAndGetCanvasContent(xPos, yPos, symbol);
        assertEquals(expected, canvasContent);
    }

    @Test
    void renderPartsOfTheObjectOffScreenOnYAxis2X2RSL() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String expected =
                "     \r\n" +
                        "     \r\n" +
                        "     \r\n" +
                        "     \r\n" +
                        "   x>\r\n";

        int xPos = 3;
        int yPos = 4;
        char[][] symbol = new char[][]{
                {'x', '>'},
                {'v', '.'}
        };


        String canvasContent = drawOnCanvasAndGetCanvasContent(xPos, yPos, symbol);
        assertEquals(expected, canvasContent);
    }

}