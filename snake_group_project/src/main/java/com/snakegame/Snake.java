package com.snakegame;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the snake in the game.
 * 
 * Maintains a list of body segments, handles movement, growth, drawing, 
 * and collision detection.
 * 
 * Depends on:
 * - Position: for segment locations.
 * - Direction: for movement.
 * - GamePanel: for grid bounds and cell size.
 */
public class Snake {
    private List<Position> body;
    private Direction direction;
    
    /**
     * Constructs the Snake with 3 initial segments moving to the right.
     */
    public Snake() {
        body = new ArrayList<>();
        body.add(new Position(20, 15));
        body.add(new Position(19, 15));
        body.add(new Position(18, 15));
        direction = Direction.RIGHT;
    }
    
    /**
     * Moves the snake forward in the current direction.
     */
    public void move() {
        Position head = getHead();
        Position newHead = new Position(head.x, head.y);
        
        switch (direction) {
            case UP:    newHead.y--; break;
            case DOWN:  newHead.y++; break;
            case LEFT:  newHead.x--; break;
            case RIGHT: newHead.x++; break;
        }
        
        body.add(0, newHead);
        body.remove(body.size() - 1);
    }
    
    /**
     * Grows the snake by adding an additional segment at the tail.
     */
    public void grow() {
        Position tail = body.get(body.size() - 1);
        body.add(new Position(tail.x, tail.y));
    }
    
    /**
     * Checks if the snake has collided with a wall or itself.
     */
    public boolean checkCollision() {
        Position head = getHead();
        
        // Wall collision
        if (head.x < 0 || head.x >= GamePanel.GRID_WIDTH ||
            head.y < 0 || head.y >= GamePanel.GRID_HEIGHT) {
            return true;
        }
        
        // Self collision
        for (int i = 1; i < body.size(); i++) {
            if (head.equals(body.get(i))) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Draws each segment of the snake on the grid.
     */
    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        for (Position p : body) {
            g.fillRect(p.x * GamePanel.CELL_SIZE, 
                       p.y * GamePanel.CELL_SIZE, 
                       GamePanel.CELL_SIZE - 1, 
                       GamePanel.CELL_SIZE - 1);
        }
    }
    
    /**
     * Returns the position of the snake's head.
     */
    public Position getHead() {
        return body.get(0);
    }
    
    /**
     * Returns the current length of the snake.
     */
    public int getLength() {
        return body.size();
    }
    
    /**
     * Returns the current movement direction of the snake.
     */
    public Direction getDirection() {
        return direction;
    }
    
    /**
     * Sets the snake's direction.
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
