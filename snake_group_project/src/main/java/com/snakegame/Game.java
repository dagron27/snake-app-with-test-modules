package com.snakegame;

import javax.swing.JFrame;

/**
 * Main class for the Snake game.
 * 
 * Sets up the JFrame and starts the game loop by launching the GamePanel.
 * 
 * Depends on:
 * - GamePanel: the core game logic and rendering.
 */
public class Game {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    
    /**
     * Entry point for the game. Creates a window and starts the game.
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        
        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);
        frame.setVisible(true);
        
        gamePanel.startGame();
    }
}
