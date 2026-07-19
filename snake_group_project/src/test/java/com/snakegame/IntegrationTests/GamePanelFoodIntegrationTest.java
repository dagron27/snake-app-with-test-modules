/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.snakegame.IntegrationTests;

import com.snakegame.GamePanel;
import com.snakegame.Snake;
import com.snakegame.Food;
import com.snakegame.Position;
import com.snakegame.Direction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class GamePanelFoodIntegrationTest {

    private GamePanel panel;
    private Snake snake;

    @BeforeEach
    void setUp() {
        panel = new GamePanel();
        try {
            snake = getPrivateSnake(panel);
        } catch (Exception e) {
            fail("Reflection setup failed: " + e.getMessage());
        }
    }

    @Test
    void testSnakeEatsFoodAndGrows() throws Exception {
        Position head = snake.getHead();
        Position foodPos = new Position(head.x + 1, head.y); // snake is moving RIGHT

        // Fixed food location
        Food fixedFood = new Food() {
            @Override
            public void randomizePosition() {
                // Do nothing — prevent relocation
            }
        };

        setPrivateFoodPosition(fixedFood, foodPos);
        setPrivateFood(panel, fixedFood);

        panel.startGame();
        panel.actionPerformed(null);

        assertEquals(4, snake.getLength(), "Snake should grow by 1 after eating food");
    }

    @Test
    void testFoodRelocatesAfterBeingEaten() throws Exception {
        Position head = snake.getHead();
        Position foodPos = new Position(head.x + 1, head.y);  // place in front
        Position relocatedPos = new Position(0, 0);           // force new location

        // Override food with forced relocation behavior
        Food relocatingFood = new Food() {
            @Override
            public void randomizePosition() {
                try {
                    Field posField = Food.class.getDeclaredField("position");
                    posField.setAccessible(true);
                    posField.set(this, relocatedPos);
                } catch (Exception e) {
                    fail("Failed to relocate food.");
                }
            }
        };

        setPrivateFoodPosition(relocatingFood, foodPos);
        setPrivateFood(panel, relocatingFood);

        Position before = relocatingFood.getPosition();

        panel.startGame();
        panel.actionPerformed(null);  // snake moves into food

        Position after = relocatingFood.getPosition();
        assertNotEquals(before, after, "Food should change position after being eaten");
    }

    // ======== Reflection Helpers ========

    private Snake getPrivateSnake(GamePanel panel) throws Exception {
        Field field = GamePanel.class.getDeclaredField("snake");
        field.setAccessible(true);
        return (Snake) field.get(panel);
    }

    private void setPrivateFood(GamePanel panel, Food food) throws Exception {
        Field field = GamePanel.class.getDeclaredField("food");
        field.setAccessible(true);
        field.set(panel, food);
    }

    private void setPrivateFoodPosition(Food food, Position pos) throws Exception {
        Field posField = Food.class.getDeclaredField("position");
        posField.setAccessible(true);
        posField.set(food, pos);
    }
}
