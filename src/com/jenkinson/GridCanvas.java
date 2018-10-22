package com.jenkinson;

import java.awt.*;
import javax.swing.*;

class GridCanvas extends JPanel {
    /**
     * JPanel Representing the game canvas
     */

    GameScreen screen;
    GridContext gridContext;

    GridCanvas(GameScreen screen, GridContext gridContext) {
        this.screen = screen;
        this.gridContext = gridContext;

        GridContext c = this.gridContext;
        int width = c.STEP * c.N_COLS;
        int height = c.STEP * (c.N_ROWS - c.HIDDEN_ROWS);

        setPreferredSize(new Dimension(width, height));
    }

    public void paintComponent(Graphics g) {

        GridContext cx = this.gridContext;

        // Offset the hidden rows
        for (int i = cx.HIDDEN_ROWS; i < cx.N_ROWS; i++) {
            for (int j = 0; j < cx.N_COLS; j++) {

                g.setColor(cx.grid[i][j].color);
                g.fillRect(j * cx.STEP, (i - cx.HIDDEN_ROWS) * cx.STEP, cx.SCALE, cx.SCALE);

                g.setColor(Color.WHITE);
                g.drawRect(j * cx.STEP, (i - cx.HIDDEN_ROWS) * cx.STEP, cx.SCALE, cx.SCALE);
            }
        }
    }
}
