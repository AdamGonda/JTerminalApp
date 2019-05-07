package com.codecool.core;

import com.codecool.core.buffer.Buffer;
import com.codecool.core.render.View;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implements an app functionality,
 * updates app objects then
 * renders them on the terminal screen.
 * <br>
 * It's sets the terminal to raw mode
 * to read input flawlessly, hides the cursor
 * and refreshes the screen in every frame.
 * <br>
 * More over it provides {@link #getInput()}
 * <br>
 * Use {@link #isDebug} == true
 * to stop the execution of the app on any keystroke.
 * <br>
 * After instantiation or at runtime you can use:
 * <br>
 * {@link #addAppObject(AppObject)}
 * {@link #addAnimation(AppObject)}
 * {@link #removeAppObject(AppObject)}
 * {@link #removeAnimation(AppObject)}
 * <br>
 * to add/remove app object(s) to/from the app.
 * <br>
 * App object can be rendered to the screen from 1 to the given screen[dimension] - 1.
 */
public class App {

    /**
     * Represents the view of the app where objects are rendered.
     */
    private View<AppObject> view;

    /**
     * Provides input reading from terminal.
     */
    private Input input = new Input();

    /**
     * These are the animations inside the app.
     */
    private List<AppObject> animations = new ArrayList<>();

    /**
     * These are the app objects inside the app.
     */
    private List<AppObject> appObjects = new ArrayList<>();

    /**
     * Provides a buffer for app to hold animations.
     */
    private Buffer<AppObject> animationBuffer = new Buffer<>(animations);

    /**
     * Provides a buffer for app to hold app objects.
     */
    private Buffer<AppObject> appObjectsBuffer = new Buffer<>(appObjects);

    /**
     * If it's true you can stop the app at any keystroke.
     */
    private boolean isDebug;

    /**
     * For internal use.
     */
    private boolean isAppRunning = true;

    /**
     * If it't true app starts to count frames
     * and at {@link #stopDelay} will stop the app.
     */
    private boolean isAppStopping;

    /**
     * Marks how many frames will be rendered
     * after the {@link #isAppStopping} was set to true.
     */
    private int stopDelay;

    /**
     * Counts frames after the {@link #isAppStopping} was set to true.
     */
    private int framesSinceIsStopping = 0;

    /**
     * Constructs an App with the specified params.
     *
     * @param screenWidth  the width of the screen.
     * @param screenHeight the height of the screen.
     * @param isDebug      if it's true you can exit with any key stroke.
     */
    public App(int screenWidth, int screenHeight, boolean isDebug) {
        this(screenWidth, screenHeight, isDebug, 5);
    }

    /**
     * Constructs an App with the specified params.
     *
     * @param screenWidth  the width of the screen.
     * @param screenHeight the height of the screen.
     * @param isDebug      if it's true you can exit with any key stroke.
     * @param stopDelay    how many frames are rendered after {@link #stop()} called.
     */
    public App(int screenWidth, int screenHeight, boolean isDebug, int stopDelay) {
        this.view = new View<>(screenHeight, screenWidth, this.appObjects, this.animations, 100);
        this.isDebug = isDebug;
        this.stopDelay = stopDelay;
    }

    /**
     * Starts the main loop of the app.
     */
    public void start() {
        while (isAppRunning) {
            if (isAppStopping) {
                framesSinceIsStopping++;
            }
            if (framesSinceIsStopping >= stopDelay) {
                isAppRunning = false;
                view.stop();
            }

            update();
            view.render();

            if (isDebug) {
                Character userInput = input.tryToRead();
                if (userInput != null) {
                    isAppRunning = false;
                    view.stop();
                }
            }
        }
    }

    /**
     * Updates {@link #appObjects} and {@link #animations}
     */
    private void update() {
        appObjectsBuffer.process();
        animationBuffer.process();

        appObjects.forEach(AppObject::update);
        animations.forEach(AppObject::update);
    }

    /**
     * Stops the app.
     */
    public void stop() {
        isAppStopping = true;
    }

    /**
     * Adds animation to app.
     *
     * @param animation that will be added to the app
     */
    public void addAnimation(AppObject animation) {
        animationBuffer.applyForAddition(animation);
    }

    /**
     * Adds animations to app.
     *
     * @param animations that will be added to the app
     */
    public void addAnimations(List<AppObject> animations) {
        animationBuffer.applyForRemoval(animations);
    }

    /**
     * Removes animation from app.
     *
     * @param animation that will be removed from the app
     */
    public void removeAnimation(AppObject animation) {
        animationBuffer.applyForRemoval(animation);
    }

    /**
     * Removes animations from app.
     *
     * @param animations that will  beremoved from the app
     */
    public void removeAnimations(List<AppObject> animations) {
        animationBuffer.applyForRemoval(animations);
    }

    /**
     * Adds appObject to app.
     *
     * @param appObject that will be added to the app
     */
    public void addAppObject(AppObject appObject) {
        appObjectsBuffer.applyForAddition(appObject);
    }

    /**
     * Adds appObjects to app.
     *
     * @param appObjects that will be added to the app
     */
    public void addAppObjects(List<AppObject> appObjects) {
        appObjectsBuffer.applyForAddition(appObjects);
    }

    /**
     * Removes appObject from app.
     *
     * @param appObject that will be removed from the app
     */
    public void removeAppObject(AppObject appObject) {
        appObjectsBuffer.applyForRemoval(appObject);
    }

    /**
     * Removes appObjects from app.
     *
     * @param appObjects that will be removed from the app
     */
    public void removeAppObjects(List<AppObject> appObjects) {
        appObjectsBuffer.applyForRemoval(appObjects);
    }

    /**
     * @return Pressed key.
     */
    public Character getInput() {
        return input.tryToRead();
    }

    public void setInput(Input input) {
        this.input = input;
    }

    /**
     * @return The width of the app's view.
     */
    public int getScreenWidth() {
        return view.getWidth();
    }

    /**
     * @return The height of the app's view.
     */
    public int getScreenHeight() {
        return view.getHeight();
    }

    /**
     * @param tagName the name of the objects
     * @return app objects by tag name.
     */
    public List<AppObject> getAppObjectsByTagName(String tagName) {
        return appObjects.stream()
                .filter(x -> x.getTagName().equals(tagName))
                .collect(Collectors.toList());
    }

    public void setView(View<AppObject> view) {
        this.view = view;
    }

    public void setAnimations(List<AppObject> animations) {
        this.animations = animations;
    }

    public void setAppObjects(List<AppObject> appObjects) {
        this.appObjects = appObjects;
    }

    public void setAnimationBuffer(Buffer<AppObject> animationBuffer) {
        this.animationBuffer = animationBuffer;
    }

    public void setAppObjectsBuffer(Buffer<AppObject> appObjectsBuffer) {
        this.appObjectsBuffer = appObjectsBuffer;
    }
}