package com.jenkinson;


class GameScreen extends Screen {
    /**
     * JPanel Representing the game screen. It has 3 canvas: game, cue, report.
     */

    // Game, next piece, text
    private GridCanvas gameCanvas, cueCanvas;
    GridContext gameGridContext, cueGridContext;

    Piece activePiece;

    GameScreen() {

        gameGridContext = new GridContext(this, 26, 10, 4, 40, 10);
        cueGridContext = new GridContext(this,4,3,0,40,10);

        gameCanvas = new GridCanvas(this, gameGridContext);
        cueCanvas = new GridCanvas(this, cueGridContext);

        add(gameCanvas);
        add(cueCanvas);
    }
}
