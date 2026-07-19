/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.snakegame.UnitTests;

import com.snakegame.Direction;
import com.snakegame.GamePanel;
import com.snakegame.Snake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

public class GamePanelTest {

    private GamePanel panel;

    @BeforeEach
    void setUp() {
        panel = new GamePanel();
    }

    @Test
    void testStartGameSetsRunningTrue() throws Exception {
        panel.startGame();
        Field isRunningField = GamePanel.class.getDeclaredField("isRunning");
        isRunningField.setAccessible(true);
        assertTrue(isRunningField.getBoolean(panel), "Game should be running after startGame()");
    }

    @Test
    void testDirectionChangeFromKeyPress() throws Exception {
        // Simulate UP arrow key press using reflection
        KeyEvent upKey = new KeyEvent(panel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_UP, 'U');
        invokeHandleKeyPress(panel, upKey);

        Snake snake = getPrivateSnake(panel);
        assertEquals(Direction.UP, snake.getDirection(), "Snake direction should change to UP");
    }

    @Test
    void testNoReverseDirection() throws Exception {
        // Snake starts facing RIGHT. Try to reverse to LEFT (which should not be allowed)
        KeyEvent leftKey = new KeyEvent(panel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, 'L');
        invokeHandleKeyPress(panel, leftKey);

        Snake snake = getPrivateSnake(panel);
        assertEquals(Direction.RIGHT, snake.getDirection(), "Snake should not reverse to LEFT from RIGHT");
    }

    // Helper to reflectively access the snake field
    private Snake getPrivateSnake(GamePanel panel) throws Exception {
        Field snakeField = GamePanel.class.getDeclaredField("snake");
        snakeField.setAccessible(true);
        return (Snake) snakeField.get(panel);
    }

    // Helper to reflectively invoke the private handleKeyPress method
    private void invokeHandleKeyPress(GamePanel panel, KeyEvent event) throws Exception {
        Method method = GamePanel.class.getDeclaredMethod("handleKeyPress", KeyEvent.class);
        method.setAccessible(true);
        method.invoke(panel, event);
    }
}
