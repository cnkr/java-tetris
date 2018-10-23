package com.jenkinson;


class GameScreen extends Screen {
    /**
     * JPanel Representing the game screen. It has 3 canvas: game, cue, report.
     */

    // Game, next piece, text
    private GridCanvas gameCanvas, cueCanvas;
    GridContext gameGridContext, cueGridContext;

    boolean quitRequested = false;

    GameScreen() {

        gameGridContext = new GridContext(this, 26, 10, 4, 40, 10);
        cueGridContext = new GridContext(this, 4, 2, 0, 40, 10);

        gameCanvas = new GridCanvas(this, gameGridContext);
        cueCanvas = new GridCanvas(this, cueGridContext);

        add(gameCanvas);
        add(cueCanvas);
    }

    Piece updatePieces() {

        gameGridContext.piece = cueGridContext.piece;
        gameGridContext.piece.cx = gameGridContext;

        cueGridContext.clearGrid();
        cueGridContext.piece = new Piece(cueGridContext);

        //Translate piece to center
        gameGridContext.centerPiece();

        return gameGridContext.piece;
    }

    void runGameLoop(){
        System.out.println("running");
        long gamePeriod = 1000;

        while(!quitRequested){

            try {
                Thread.sleep(gamePeriod);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("here");
            boolean advanceSucceeded = gameGridContext.advancePiece();

            if (!advanceSucceeded) {
                updatePieces();
            }

            repaint();
        }


    }
}
