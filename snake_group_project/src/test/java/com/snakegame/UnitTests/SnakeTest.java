package com.snakegame.UnitTests;

import com.snakegame.Direction;
import com.snakegame.Position;
import com.snakegame.Snake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SnakeTest {

    private Snake snake;

    @BeforeEach
    void setUp() {
        snake = new Snake();
    }

    @Test
    void testInitialLength() {
        assertEquals(3, snake.getLength(), "Snake should start with 3 segments");
    }

    @Test
    void testInitialHeadPosition() {
        Position head = snake.getHead();
        assertEquals(new Position(20, 15), head, "Initial head should be at (20, 15)");
    }

    @Test
    void testMoveRight() {
        Position oldHead = snake.getHead();
        snake.move();
        Position newHead = snake.getHead();
        assertEquals(oldHead.x + 1, newHead.x, "Snake should move right");
        assertEquals(oldHead.y, newHead.y, "Y should stay the same when moving right");
    }

    @Test
    void testGrowIncreasesLength() {
        int oldLength = snake.getLength();
        snake.grow();
        assertEquals(oldLength + 1, snake.getLength(), "Length should increase after grow");
    }

    @Test
    void testSetAndGetDirection() {
        snake.setDirection(Direction.UP);
        assertEquals(Direction.UP, snake.getDirection(), "Direction should be set to UP");
    }

    @Test
    void testWallCollision() {
        snake.setDirection(Direction.LEFT);
        for (int i = 0; i < 21; i++) { // Move out of bounds
            snake.move();
        }
        assertTrue(snake.checkCollision(), "Should detect wall collision");
    }

    @Test
    void testSelfCollision() {
        // Manually create a self-collision condition
        snake.grow(); snake.grow(); snake.grow(); // Grow longer
        snake.setDirection(Direction.UP);
        snake.move();
        snake.setDirection(Direction.LEFT);
        snake.move();
        snake.setDirection(Direction.DOWN);
        snake.move();
        snake.setDirection(Direction.RIGHT);
        snake.move(); // This should now collide with its own body

        assertTrue(snake.checkCollision(), "Should detect self-collision");
    }
}
