package com.snakegame;

import java.awt.*;
import java.util.Random;

/**
 * Represents the food object in the game.
 * 
 * Food appears at a random location on the grid.
 * When eaten by the snake, it causes the snake to grow and relocates.
 * 
 * Depends on:
 * - Position: to store its location.
 * - GamePanel: for grid boundaries and cell size.
 */
public class Food {
    private Position position;
    private Random random;
    
    /**
     * Constructs the Food and places it at a random location.
     */
    public Food() {
        random = new Random();
        randomizePosition();
    }
    
    /**
     * Sets the food's position to a random valid location on the grid.
     */
    public void randomizePosition() {
        position = new Position(
            random.nextInt(GamePanel.GRID_WIDTH),
            random.nextInt(GamePanel.GRID_HEIGHT)
        );
    }
    
    /**
     * Draws the food on the screen as a red oval.
     */
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(position.x * GamePanel.CELL_SIZE, 
                   position.y * GamePanel.CELL_SIZE, 
                   GamePanel.CELL_SIZE - 1, 
                   GamePanel.CELL_SIZE - 1);
    }
    
    /**
     * Returns the current position of the food.
     */
    public Position getPosition() {
        return position;
    }
}
