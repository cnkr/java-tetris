package com.jenkinson;

public class GridContext {

    GameScreen screen;

    final int N_ROWS; //26
    final int N_COLS; //10
    final int HIDDEN_ROWS; //4
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
        SCALE = scale;
        GAP = SCALE / scaleGapRatio;
        STEP = SCALE + GAP;

        grid = new Slot[N_ROWS][N_COLS];

        for(int i = 0; i< N_ROWS; i++){
            for(int j= 0; j < N_COLS; j++){
                grid[i][j] = new Slot(i,j);
            }
        }

        piece = new Piece(this);
    }


}
