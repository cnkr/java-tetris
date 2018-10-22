package com.jenkinson;


class GameScreen extends Screen {
    /**
     * JPanel Representing the game screen. It has 3 canvas: game, cue, report.
     */

    final int N_ROWS = 20;
    final int N_COLS = 10;
    final int HIDDEN_ROWS = 4;
    final int SCALE = 40;
    private final int GAP = SCALE / 10;
    final int STEP = SCALE + GAP;

    // Game, next piece, text
    private Canvas gameCanvas, cueCanvas, reportCanvas;

    public Piece activePiece, nextPiece;
    Slot[][] grid = new Slot[N_ROWS][N_COLS];

    GameScreen() {

        gameCanvas = new GameCanvas(this);
        cueCanvas = new CueCanvas();
        reportCanvas = new ReportCanvas();

        add(gameCanvas);
        //add(cueCanvas);
        //add(reportCanvas);

        initGameComponents();
    }

    private void initGameComponents(){

        // Create blockGrid
        for(int i = 0; i< N_ROWS; i++){
            for(int j= 0; j < N_COLS; j++){
                grid[i][j] = new Slot(i,j);
            }
        }

        // Create Active piece
        activePiece = new Piece(this, true);
    }


}
