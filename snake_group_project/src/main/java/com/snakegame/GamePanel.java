package com.snakegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * The main game panel where all game logic and rendering happens.
 * 
 * Contains:
 * - Snake and Food objects
 * - Game timer
 * - Keyboard input handling
 * 
 * Handles:
 * - Game loop
 * - Movement, collision, scoring
 * - Drawing everything on the screen
 * 
 * Depends on:
 * - Snake, Food, Direction, Position
 */
public class GamePanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
    public static final int CELL_SIZE = 20;
    public static final int GRID_WIDTH = 40;
    public static final int GRID_HEIGHT = 30;
    
    private Snake snake;
    private Food food;
    private boolean isRunning;
    private Timer timer;
    
    /**
     * Initializes the panel, creates snake and food, sets up key listener and timer.
     */
    public GamePanel() {
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }
        });
        
        snake = new Snake();
        food = new Food();
        timer = new Timer(100, this);
    }
    
    /**
     * Starts the game loop.
     */
    public void startGame() {
        isRunning = true;
        timer.start();
    }
    
    /**
     * Handles direction input from keyboard.
     */
    private void handleKeyPress(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                if (snake.getDirection() != Direction.DOWN)
                    snake.setDirection(Direction.UP);
                break;
            case KeyEvent.VK_DOWN:
                if (snake.getDirection() != Direction.UP)
                    snake.setDirection(Direction.DOWN);
                break;
            case KeyEvent.VK_LEFT:
                if (snake.getDirection() != Direction.RIGHT)
                    snake.setDirection(Direction.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                if (snake.getDirection() != Direction.LEFT)
                    snake.setDirection(Direction.RIGHT);
                break;
        }
    }
    
    /**
     * Renders the game grid, snake, food, and score.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Optional grid
        g.setColor(Color.DARK_GRAY);
        for (int i = 0; i < GRID_WIDTH; i++) {
            for (int j = 0; j < GRID_HEIGHT; j++) {
                g.drawRect(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
        
        snake.draw(g);
        food.draw(g);
        
        g.setColor(Color.WHITE);
        g.drawString("Score: " + (snake.getLength() - 3), 10, 20);
    }
    
    /**
     * Called by the timer every 100ms; updates game state.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (isRunning) {
            snake.move();
            
            // Eat food
            if (snake.getHead().equals(food.getPosition())) {
                snake.grow();
                food.randomizePosition();
            }
            
            // Collision detection
            if (snake.checkCollision()) {
                isRunning = false;
                timer.stop();
                JOptionPane.showMessageDialog(this, "Game Over! Score: " + (snake.getLength() - 3));
            }
            
            repaint();
        }
    }
}
