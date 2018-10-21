package com.jenkinson;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Game {

    public static void main(String[] args) {
        new Game();
    }

    //===========================
    // Fields
    //===========================

    private enum State {RUNNING, STOPPED}

    private JFrame frame;
    private GameBoard gameBoard;
    private State gameState;

    private final int NUM_ROWS = 22;
    private final int NUM_COLS = 10;
    private final int SCALE = 40; // Number of pixels per cell
    private final int GAP = SCALE / 10; // Space in between cells
    private final int STEP = SCALE + GAP;

    private final long FRAME_PERIOD = 250;

    private Cell[][] grid;

    //===========================
    // Methods
    //===========================

    private Game() {

        initGUI();
        initGrid();
        runGameLoop();
    }

    private void initGUI() {

        frame = new JFrame("Tetris");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gameBoard = new GameBoard();
        gameBoard.setPreferredSize(new Dimension(gameBoard.WIDTH, gameBoard.HEIGHT));

        JPanel boardWrapper = new JPanel();
        boardWrapper.add(gameBoard);

        frame.add(boardWrapper);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void initGrid() {

        grid = new Cell[NUM_ROWS][NUM_COLS];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {

                grid[i][j] = new Cell(i, j);
            }
        }
    }

    private void runGameLoop() {


        gameState = State.RUNNING;

        long tFirst, tLast, tSleep; // time
        tFirst = System.currentTimeMillis();

        while (gameState == State.RUNNING) {

            update();
            render();

            // Control Render Speed
            tLast = System.currentTimeMillis();
            tSleep = FRAME_PERIOD - (tLast - tFirst);

            if (tSleep > 0) {
                try {
                    Thread.sleep(tSleep);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            tFirst = tLast;
        }
    }

    private void update() {
        System.out.println("Updated. " + System.currentTimeMillis());

        Random r = new Random();

        for(Cell[] row : grid){
            for(Cell cell : row){
                cell.color = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
            }
        }

    }

    private void render() {
        frame.repaint();
    }

    //===========================
    // Subclasses
    //===========================

    abstract class Board extends JPanel {
        // Abstract JFrame child to represent 3 main components of the game.

        int WIDTH;
        int HEIGHT;
    }

    class GameBoard extends Board {

        GameBoard() {

            this.WIDTH = NUM_COLS * STEP - GAP;
            this.HEIGHT = NUM_ROWS * STEP - GAP;
        }

        protected void paintComponent(Graphics g) {
            //Graphics2D g2 = (Graphics2D) g;

            // Draw cells
            for (Cell[] row : grid) {
                for (Cell cell : row) {
                    g.setColor(cell.color);
                    g.fillRect(cell.j * STEP, cell.i * STEP, SCALE, SCALE);
                }
            }
        }
    }

    class NextBoard extends Board {

    }

    class ScoreBoard extends Board {

    }

    class Cell {

        final int i;
        final int j;
        Color color;

        Cell(int i, int j) {
            this.i = i;
            this.j = j;

            Random r = new Random();

            color = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
        }

    }

}

