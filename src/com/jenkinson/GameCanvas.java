package com.jenkinson;

import java.awt.*;

class GameCanvas extends Canvas {
    /**
     * JPanel Representing the game canvas
     */

    Screen screen;

    GameCanvas(GameScreen screen) {
        this.screen = screen;

        int width = screen.STEP * screen.N_COLS;
        int height = screen.STEP * (screen.N_ROWS - screen.HIDDEN_ROWS);

        setPreferredSize(new Dimension(width, height));
    }

    public void paintComponent(Graphics g) {

        GameScreen sc = (GameScreen) (this.screen);
        Slot[][] grid = sc.grid;

        // Offset the hidden rows
        for (int i = sc.HIDDEN_ROWS; i < sc.N_ROWS; i++) {
            for (int j = 0; j < sc.N_COLS; j++) {

                g.setColor(grid[i][j].color);
                g.fillRect(j * sc.STEP, (i - sc.HIDDEN_ROWS) * sc.STEP, sc.SCALE, sc.SCALE);

                g.setColor(Color.WHITE);
                g.drawRect(j * sc.STEP, (i - sc.HIDDEN_ROWS) * sc.STEP, sc.SCALE, sc.SCALE);
            }
        }

        g.setColor(Color.BLACK);
        g.drawRect(20, 20, 30, 30);
    }
}
