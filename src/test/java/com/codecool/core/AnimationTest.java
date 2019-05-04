package com.codecool.core;

import com.codecool.core.animation.Animation;
import com.codecool.core.animation.FramesNotDefined;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class AnimationTest {

    @Test
    void doesNotThrowsExceptionIfFramesAreDefined() {

        App mockApp = Mockito.mock(App.class);

        assertDoesNotThrow(() -> {
            new Animation(0, 0, mockApp, true) {
                @Override
                public void defineFrames() {
                    this.frames = new char[][][]{
                            {
                                    {' '},
                            }
                    };
                }
            };
        });
    }

    @Test
    void throwsExceptionIfFramesAreNotDefined() {

        App mockApp = Mockito.mock(App.class);

        assertThrows(FramesNotDefined.class, () -> new Animation(0, 0, mockApp, true) {
            @Override
            public void defineFrames() {
            }
        });
    }

    @Test
    void animatesFramesCorrectlyHorizontallyLoop() {
        char[][] expected1 = new char[][]{{'x', ' ', ' '}};
        char[][] expected2 = new char[][]{{' ', 'x', ' '}};
        char[][] expected3 = new char[][]{{' ', ' ', 'x'}};


        App mockApp = Mockito.mock(App.class);
        Animation animation = new Animation(0, 0, mockApp, true) {
            @Override
            public void defineFrames() {
                this.frames = new char[][][]{
                        {
                                {'x', ' ', ' '},
                        },
                        {
                                {' ', 'x', ' '},
                        },
                        {
                                {' ', ' ', 'x'},
                        }
                };
            }
        };

        assertArrayEquals(expected1, animation.getRenderData().renderSymbols);
        animation.update();
        assertArrayEquals(expected2, animation.getRenderData().renderSymbols);
        animation.update();
        assertArrayEquals(expected3, animation.getRenderData().renderSymbols);
        animation.update();
        assertArrayEquals(expected1, animation.getRenderData().renderSymbols);
    }

    @Test
    void animatesFramesCorrectlyVerticallyLoop() {
        char[][] expected1 = new char[][]{
                {'x', ' ', ' '},
                {' ', ' ', ' '},
                {' ', ' ', ' '}
        };
        char[][] expected2 = new char[][]{
                {' ', ' ', ' '},
                {'x', ' ', ' '},
                {' ', ' ', ' '}
        };
        char[][] expected3 = new char[][]{
                {' ', ' ', ' '},
                {' ', ' ', ' '},
                {'x', ' ', ' '}
        };


        App mockApp = Mockito.mock(App.class);
        Animation animation = new Animation(0, 0, mockApp, true) {
            @Override
            public void defineFrames() {
                this.frames = new char[][][]{
                        {
                                {'x', ' ', ' '},
                                {' ', ' ', ' '},
                                {' ', ' ', ' '}
                        },
                        {
                                {' ', ' ', ' '},
                                {'x', ' ', ' '},
                                {' ', ' ', ' '}
                        },
                        {
                                {' ', ' ', ' '},
                                {' ', ' ', ' '},
                                {'x', ' ', ' '}
                        }
                };
            }
        };

        assertArrayEquals(expected1, animation.getRenderData().renderSymbols);
        animation.update();
        assertArrayEquals(expected2, animation.getRenderData().renderSymbols);
        animation.update();
        assertArrayEquals(expected3, animation.getRenderData().renderSymbols);
        animation.update();
        assertArrayEquals(expected1, animation.getRenderData().renderSymbols);
    }

    @Test
    void animatesFramesCorrectlyVerticallyFixedLength() {
        char[][] expected1 = new char[][]{
                {'x', ' ', ' '},
                {' ', ' ', ' '},
                {' ', ' ', ' '}
        };
        char[][] expected2 = new char[][]{
                {' ', ' ', ' '},
                {'x', ' ', ' '},
                {' ', ' ', ' '}
        };
        char[][] expected3 = new char[][]{
                {' ', ' ', ' '},
                {' ', ' ', ' '},
                {'x', ' ', ' '}
        };


        App mockApp = Mockito.mock(App.class);
        Animation animation = new Animation(0, 0, mockApp, false) {
            @Override
            public void defineFrames() {
                this.frames = new char[][][]{
                        {
                                {'x', ' ', ' '},
                                {' ', ' ', ' '},
                                {' ', ' ', ' '}
                        },
                        {
                                {' ', ' ', ' '},
                                {'x', ' ', ' '},
                                {' ', ' ', ' '}
                        },
                        {
                                {' ', ' ', ' '},
                                {' ', ' ', ' '},
                                {'x', ' ', ' '}
                        }
                };
            }
        };

        assertArrayEquals(expected1, animation.getRenderData().renderSymbols);
        animation.update();
        assertArrayEquals(expected2, animation.getRenderData().renderSymbols);
        animation.update();
        assertArrayEquals(expected3, animation.getRenderData().renderSymbols);
        animation.update();
    }

    @Test
    void animatesFramesCorrectlyHorizontallyFixedLength() {
        char[][] expected1 = new char[][]{{'x', ' ', ' '}};
        char[][] expected2 = new char[][]{{' ', 'x', ' '}};
        char[][] expected3 = new char[][]{{' ', ' ', 'x'}};


        App mockApp = Mockito.mock(App.class);
        Animation animation = new Animation(0, 0, mockApp, true) {
            @Override
            public void defineFrames() {
                this.frames = new char[][][]{
                        {
                                {'x', ' ', ' '},
                        },
                        {
                                {' ', 'x', ' '},
                        },
                        {
                                {' ', ' ', 'x'},
                        }
                };
            }
        };

        assertArrayEquals(expected1, animation.getRenderData().renderSymbols);
        animation.update();
        assertArrayEquals(expected2, animation.getRenderData().renderSymbols);
        animation.update();
        assertArrayEquals(expected3, animation.getRenderData().renderSymbols);
        animation.update();
    }

    @Test
    void removesAnimationAfterIsOver() {
        App mockApp = Mockito.mock(App.class);
        Animation animation = new Animation(0, 0, mockApp, false) {
            @Override
            public void defineFrames() {
                this.frames = new char[][][]{
                        {
                                {'x', ' ', ' '}
                        },
                        {
                                {' ', 'x', ' '}
                        },
                        {
                                {' ', ' ', 'x'}
                        }
                };
            }
        };
        animation.update();
        animation.update();
        animation.update();
        Mockito.verify(mockApp).removeAnimation(animation);
    }
}