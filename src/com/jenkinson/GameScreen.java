package com.jenkinson;


import javax.swing.*;
import java.awt.*;

class GameScreen extends Screen {
    /**
     * JPanel Representing the game screen. It has 3 canvas: game, cue, report.
     */

    // Game, next piece, text
    private GridCanvas gameCanvas, cueCanvas;
    GridContext gameGridContext, cueGridContext;

    boolean quitRequested = false;
    boolean gameOver = false;


    GameScreen() {

        gameGridContext = new GridContext(this, 26, 10, 4, 40, 10);
        cueGridContext = new GridContext(this, 4, 2, 0, 40, 10);

        gameCanvas = new GridCanvas(this, gameGridContext, GridCanvas.CanvasType.GAME);
        cueCanvas = new GridCanvas(this, cueGridContext, GridCanvas.CanvasType.CUE);

        add(gameCanvas);
        add(cueCanvas);

        this.setBackground(new Color(0,0,0));
    }

    Piece updatePieces() {

        gameGridContext.piece = cueGridContext.piece;
        gameGridContext.piece.cx = gameGridContext;

        cueGridContext.clearGrid();
        cueGridContext.piece = new Piece(cueGridContext);

        //Translate piece to center. If failure, it means game over.
        gameOver = !gameGridContext.movePieceToCenter();

        return gameGridContext.piece;
    }

    void runGameLoop() {

        long advancePeriod = 1000; //ms

        while (!quitRequested && !gameOver) {

            try {
                Thread.sleep(advancePeriod);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            boolean advanceAttempt = gameGridContext.advancePiece();

            if (!advanceAttempt) {
                gameGridContext.clearCompletedRows();
                gameGridContext.fillOpenedGaps();
                updatePieces(); // must update after clear & fill.
            }

            repaint();
        }


    }
}
