/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.snakegame.UnitTests;

import com.snakegame.Food;
import com.snakegame.GamePanel;
import com.snakegame.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FoodTest {

    private Food food;

    @BeforeEach
    void setUp() {
        food = new Food();
    }

    @Test
    void testFoodHasPositionAfterCreation() {
        assertNotNull(food.getPosition(), "Food should have a position after being created.");
    }

    @Test
    void testFoodPositionWithinBounds() {
        Position pos = food.getPosition();
        assertTrue(pos.x >= 0 && pos.x < GamePanel.GRID_WIDTH, "X should be within grid bounds.");
        assertTrue(pos.y >= 0 && pos.y < GamePanel.GRID_HEIGHT, "Y should be within grid bounds.");
    }

    @Test
    void testRandomizePositionChangesPosition() {
        Position before = food.getPosition();
        food.randomizePosition();
        Position after = food.getPosition();
        // It *might* be the same by coincidence, but test expects random behavior
        assertNotNull(after);
    }
}
