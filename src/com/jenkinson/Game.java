package com.jenkinson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Game extends JFrame implements Runnable {

    public static void main(String[] args) {
        new Game();
    }

    // Fields

    private enum State {RUNNING, STOPPED}

    private enum PieceKind {I, J, L, O, S, T, Z}

    private enum Direction {LEFT, RIGHT, UP, DOWN}

    private GameBoard gameBoard;
    private State gameState;

    private final int HIDDEN_ROWS = 4;
    private final int NUM_ROWS = 20 + HIDDEN_ROWS;
    private final int NUM_COLS = 10;
    private final int SCALE = 40; // Number of pixels per cell
    private final int GAP = SCALE / 10; // Space in between cells
    private final int STEP = SCALE + GAP;  // Used to estimate cell pixel locations.

    private final long MS_PER_FRAME = 1000 / 60;

    private Cell[][] grid;

    private Piece activePiece;
    private Piece nextPiece;
    private Color bgColor = new Color(0, 255, 255);

    // Methods

    private Game() {

        setTitle("Tetris");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        gameBoard = new GameBoard();
        gameBoard.setPreferredSize(new Dimension(gameBoard.WIDTH, gameBoard.HEIGHT));

        JPanel boardWrapper = new JPanel();
        boardWrapper.add(gameBoard);
        add(boardWrapper);

        addKeyListener(new ConcreteKeyListener());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        createCellGrid();
        createActivePiece();

        Thread gameLoop = new Thread(this);
        gameLoop.start();
    }

    private void createCellGrid() {
        grid = new Cell[NUM_ROWS][NUM_COLS];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = new Cell(i, j);
            }
        }
    }

    private void createActivePiece() {
        activePiece = new Piece();
    }

    public void run() {

        gameState = State.RUNNING;

        long t0, t1, tSleep;

        while (gameState == State.RUNNING) {
            t0 = System.currentTimeMillis();

            // UPDATE
            //activePiece.translatePiece(1, 1);

            // RENDER
            repaint();


            // SLEEP
            t1 = System.currentTimeMillis();
            tSleep = MS_PER_FRAME - (t1 - t0);

            if (tSleep > 0) {
                try {
                    Thread.sleep(tSleep);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void update() {
        System.out.println("Updated. " + System.currentTimeMillis());

        Random r = new Random();

        for (Cell[] row : grid) {
            for (Cell cell : row) {
                cell.color = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
            }
        }

    }

    // Subclasses

    class Block {
        // Movable

        int i;
        int j;

        Block(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }

    class Cell {
        // Immovable

        final int i;
        final int j;
        Color color;
        boolean solid = false;

        Cell(int i, int j) {
            this.i = i;
            this.j = j;

            //Random rand = new Random();
            //color = new Color(rand.nextInt(255), rand.nextInt(1), rand.nextInt(1));

            color = bgColor;
        }
    }

    class Piece {

        PieceKind kind;
        Color color;
        Block[] blocks;

        Piece() {
            Random r = new Random();
            PieceKind[] allValues = PieceKind.values();
            PieceKind selectedKind = allValues[r.nextInt(allValues.length)];

            selectedKind = PieceKind.I; //!!!

            kind = selectedKind;

            createBlocks();
        }

        void createBlocks() {
            blocks = new Block[4];

            switch (kind) {
                case I:
                    blocks[0] = new Block(0, 0);
                    blocks[1] = new Block(1, 0);
                    blocks[2] = new Block(2, 0);
                    blocks[3] = new Block(3, 0);
                    color = Color.BLUE;
                    break;
                case J:
                    break;
                case L:
                    break;
                case O:
                    break;
                case S:
                    break;
                case T:
                    break;
                case Z:
                    break;
                default:
                    break;
            }

            translatePiece(0, 7);
        }

        void translatePiece(int x, int y) {

            for (Block block : blocks) {
                grid[block.i][block.j].solid = false;
                grid[block.i][block.j].color = bgColor;
            }

            for (Block block : blocks) {
                block.i += y;
                block.j += x;
                grid[block.i][block.j].solid = true;
                grid[block.i][block.j].color = color;
            }
        }

        void movePiece(Direction dir) {

        }
    }

    abstract class Board extends JPanel {
        // Abstract JFrame child to represent 3 main components of the game.
        int WIDTH;
        int HEIGHT;
    }

    class GameBoard extends Board {

        GameBoard() {

            this.WIDTH = NUM_COLS * STEP - GAP;
            this.HEIGHT = (NUM_ROWS - HIDDEN_ROWS) * STEP - GAP;
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

    class ConcreteKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
            System.out.println("Key typed.");
        }

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                case KeyEvent.VK_LEFT:
                    activePiece.translatePiece(-1, 0);
                    break;
                case KeyEvent.VK_D:
                case KeyEvent.VK_RIGHT:
                    activePiece.translatePiece(1, 0);
                    break;
                case KeyEvent.VK_W:
                case KeyEvent.VK_UP:
                    activePiece.translatePiece(0, -1);
                    break;
                case KeyEvent.VK_S:
                case KeyEvent.VK_DOWN:
                    activePiece.translatePiece(0, 1);
                    break;
                default:
                    activePiece.translatePiece(1, 1);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            System.out.println("Key released.");
        }
    }
}

