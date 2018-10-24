package com.jenkinson;

import java.awt.*;
import java.util.ArrayList;

public class GridContext {

    GameScreen screen;

    final int N_ROWS; //26
    final int N_COLS; //10
    final int HIDDEN_ROWS; //4
    final int VISIBLE_COUNT;
    final int BASE_INDEX;
    final int SCALE; //40
    private final int GAP; //10
    final int STEP;

    Slot[][] grid;
    Piece piece;

    GridContext(GameScreen screen, int nRows, int nCols, int hiddenRows, int scale, int scaleGapRatio) {

        this.screen = screen;

        N_ROWS = nRows;
        N_COLS = nCols;

        HIDDEN_ROWS = hiddenRows;
        VISIBLE_COUNT = N_ROWS - HIDDEN_ROWS;
        BASE_INDEX = HIDDEN_ROWS;

        SCALE = scale;
        GAP = SCALE / scaleGapRatio;
        STEP = SCALE + GAP;

        grid = new Slot[N_ROWS][N_COLS];

        for (int i = 0; i < N_ROWS; i++) {
            for (int j = 0; j < N_COLS; j++) {
                grid[i][j] = new Slot(i, j);
            }
        }

        piece = new Piece(this);
        movePieceToCenter();
    }

    void clearGrid() {
        for (Slot[] row : grid) {
            for (Slot slot : row) {
                slot.purge();
            }
        }
    }

    boolean movePieceToCenter() {
        boolean success = piece.translate(1, N_COLS / 2 - 1); // also move 1 slot down
        return success;
    }

    boolean advancePiece() {
        return piece.translate(1, 0);
    }

    private boolean testRowEmpty(int i) {
        for (Slot slot : grid[i])
            if (slot.solid)
                return false;
        return true;
    }

    private boolean testRowComplete(int i) {
        for (Slot slot : grid[i])
            if (!slot.solid)
                return false;
        return true;
    }

    private void clearRow(int i) {
        for (Slot slot : grid[i]) {
            slot.purge();
        }
    }

    void clearCompletedRowsOld() {
        for (int i = BASE_INDEX; i < N_ROWS; i++) {
            if (testRowComplete(i)) {
                clearRow(i);
            }
        }
    }

    void clearCompletedRows() {
        ArrayList<Integer> fullRows = new ArrayList<Integer>();

        // Detect rows to be cleared
        for (int i = BASE_INDEX; i < N_ROWS; i++) {
            if (testRowComplete(i)) {
                fullRows.add(i);
            }
        }

        // Blink row 4 times before clearing

        for (int j = 0; j < 4; j++) {
            for (Integer i : fullRows) {
                for (Slot slot : grid[i]) {
                    if (j % 2 == 0) {
                        slot.color = Color.BLACK;
                    } else {
                        slot.color = Color.WHITE;
                    }
                }
            }

            screen.repaint();

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        clearCompletedRowsOld();

    }

    void fillOpenedGaps() {

        int src;
        int dst = N_ROWS - 1;

        while (dst > BASE_INDEX) {
            //Stay in loop if dst is not empty. find the first empty line
            while (dst >= BASE_INDEX && testRowEmpty(dst) == false) {
                dst--;
            }

            src = dst;

            // Stay in loop if src is empty. find the first non-empty line above dst
            while (src >= BASE_INDEX && testRowEmpty(src) == true) {
                src--;
            }

            if (src >= BASE_INDEX && dst >= BASE_INDEX) {
                migrateRow(src, dst);
            } else {
                break;
            }

            dst--;
        }

    }

    void migrateRow(int iSrc, int iDst) {
        for (int j = 0; j < grid[iSrc].length; j++) {
            if (grid[iSrc][j].solid) {
                grid[iDst][j].fill(grid[iSrc][j].color);
                grid[iSrc][j].purge();
            }
        }
    }
}
