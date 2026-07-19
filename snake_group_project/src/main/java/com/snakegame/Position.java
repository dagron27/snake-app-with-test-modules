package com.snakegame;

/**
 * Represents a position on the game grid using x and y coordinates.
 * 
 * Used by:
 * - Snake: to track the positions of each body segment.
 * - Food: to define where the food appears.
 */
public class Position {
    public int x;
    public int y;
    
    /**
     * Constructs a Position with specified x and y coordinates.
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Checks if another object is equal to this Position (based on x and y).
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Position) {
            Position other = (Position) obj;
            return this.x == other.x && this.y == other.y;
        }
        return false;
    }
}
