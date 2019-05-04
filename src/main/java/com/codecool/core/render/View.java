package com.codecool.core.render;

import java.util.List;

/**
 * Abstracts away the render functionality for app.
 * It is uses an internal state to represent a canvas,
 * where the object will be drawn in {@link #render()}
 * More over it's manages the time between renders,
 * by calling {@link Thread#sleep(long)}
 * and sets the terminal to certain state by {@link #prepareTerminalWindowToApp()}
 */

public class View<T extends Renderable> {

    /**
     * The width of the canvas.
     */
    private int width;

    /**
     * The height of the canvas.
     */
    private int height;

    /**
     * The intended time between renders.
     */
    private int renderSleepTime;

    /**
     * The internal canvas where object and animation will be drawn.
     */
    private char[][] canvas;

    /**
     * Reference of the object in app.
     */
    private List<T> appObjects;

    /**
     * Reference of the animations in app.
     */
    private List<T> animations;

    /**
     * The number of frames since app has been started.
     */
    private int frameCounter = 0;

    /**
     * Constructs a View with the specified params.
     *
     * @param height          is the height of the internal canvas.
     * @param width           is the width of the internal canvas.
     * @param appObjects      are a reference of the appObject from app.
     * @param animations      are a reference of the appObject from app.
     * @param renderSleepTime is the intended time between renders.
     */
    public View(int height, int width, List<T> appObjects, List<T> animations, int renderSleepTime) {
        this.height = height;
        this.width = width;
        this.canvas = new char[height][width];
        this.appObjects = appObjects;
        this.animations = animations;
        this.renderSleepTime = renderSleepTime;
        this.initView();
    }

    /**
     * It's prepares the View for render
     * and the terminal window.
     */
    private void initView() {
        clearCanvas();
        prepareTerminalWindowToApp();
    }

    /**
     * This is where the render takes place.
     */
    public void render() {
        clearTerminalScreen();
        clearCanvas();
        drawOnCanvas(animations);
        drawOnCanvas(appObjects);
        System.out.print(getCanvasContent());

        try {
            Thread.sleep(renderSleepTime);
        } catch (Exception e) {
            System.err.println("Error at View.render()\n" + e);
        }

        frameCounter++;
    }

    /**
     * Drawing renderable objects to canvas.
     */
    private void drawOnCanvas(List<T> renderables) {
        for (T renderable : renderables) {
            RenderData renderData = renderable.getRenderData();

            if (renderData.renderSymbols != null
                    && isOnScreenBasedOnPosition(renderData)) {

                for (int y = 0; y < renderData.renderSymbols.length; y++) {
                    if ((renderData.yPos + y) < this.height) {
                        for (int x = 0; x < renderData.renderSymbols[y].length; x++) {
                            if ((renderData.xPos + x) < this.width) {
                                drawCharacterToCanvas(x, y, renderData);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Draws one character on the screen,
     * relativeX and Y is relative to
     * {@link RenderData#xPos} and {@link RenderData#yPos}
     */
    private void drawCharacterToCanvas(int relativeX, int relativeY, RenderData renderData) {
        int offsettedXPos = renderData.xPos + relativeX;
        int offsettedYPos = renderData.yPos + relativeY;
        char character = renderData.renderSymbols[relativeY][relativeX];
        this.canvas[offsettedYPos][offsettedXPos] = character;
    }

    /**
     * Checks if the given object is on the screen or not.
     */
    private boolean isOnScreenBasedOnPosition(RenderData renderData) {
        return (renderData.xPos >= 0 && renderData.xPos <= this.width)
                && (renderData.yPos >= 0 && renderData.yPos <= this.height);
    }

    /**
     * Iterates over {@link #canvas} and builds a string from it.
     */
    private String getCanvasContent() {
        StringBuilder canvasContent = new StringBuilder();
        for (int i = 0; i < this.height; i++) {
            StringBuilder row = new StringBuilder();
            for (int j = 0; j < this.width; j++) {
                row.append(this.canvas[i][j]);
            }
            canvasContent.append(row.toString()).append("\r\n");
        }
        return canvasContent.toString();
    }

    /**
     * Fills {@link #canvas} with empty spaces.
     */
    private void clearCanvas() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                this.canvas[i][j] = ' ';
            }
        }
    }

    /**
     * Clears the terminal screen.
     */
    private void clearTerminalScreen() {
        System.out.print("\033[H\033[2J");
    }

    /**
     * Hides the cursor from terminal.
     */
    private void hideCursor() {
        System.out.println("\033[?25l");
    }

    /**
     * Shows the terminal cursor.
     */
    private void showCursor() {
        System.out.println("\033[?25h");
    }

    /**
     * Sets the terminal to read input constantly,
     * without the need to push ENTER every time.
     */
    private void setTerminalToRaw() {
        try {
            String[] cmd = {"/bin/sh", "-c", "stty raw </dev/tty"};
            Runtime.getRuntime().exec(cmd);
        } catch (Exception e) {
            System.out.println("Error at View.setTerminalToRaw()\n" + e);
        }
    }

    /**
     * Sets the terminal to edit mode.
     */
    private void setTerminalToEdit() {
        try {
            String[] cmd = {"/bin/sh", "-c", "stty edit </dev/tty"};
            Runtime.getRuntime().exec(cmd);
        } catch (Exception e) {
            System.out.println("Error at View.setTerminalToRaw()\n" + e);
        }
    }

    /**
     * Turns the echo on in terminal.
     */
    private void turnEchoOn() {
        try {
            String[] cmd = {"/bin/sh", "-c", "stty echo </dev/tty"};
            Runtime.getRuntime().exec(cmd);
        } catch (Exception e) {
            System.out.println("Error at View.setTerminalToRaw()\n" + e);
        }
    }

    /**
     * Prepares the terminal window to be able to render flawlessly,
     * and read input properly during the app is running.
     */
    private void prepareTerminalWindowToApp() {
        hideCursor();
        setTerminalToRaw();
        clearTerminalScreen();
    }

    /**
     * Resets the terminal back to normal.
     */
    public void stop() {
        showCursor();
        setTerminalToEdit();
        turnEchoOn();
    }

    /**
     * @return the number of frames since app is running.
     */
    public int getFrameCounter() {
        return this.frameCounter;
    }

    /**
     * @return the width of the canvas.
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * @return the height of the canvas.
     */
    public int getHeight() {
        return this.height;
    }
}